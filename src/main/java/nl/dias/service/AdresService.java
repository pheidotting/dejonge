package nl.dias.service;

import com.google.common.base.Predicate;
import nl.dias.domein.Adres;
import nl.dias.repository.AdresRepository;
import nl.dias.web.SoortEntiteit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

import static com.google.common.collect.Iterables.filter;

@Service
public class AdresService {
    private static final Logger LOGGER = LoggerFactory.getLogger(AdresService.class);

    @Inject
    private AdresRepository adresRepository;

    public List<Adres> alles(SoortEntiteit soortEntiteit, Long parentId) {
        LOGGER.debug("alles soortEntiteit {} parentId {}", soortEntiteit, parentId);

        return adresRepository.alles(soortEntiteit, parentId);
    }

    public Adres lees(Long id) {
        return adresRepository.lees(id);
    }

    public void opslaan(Adres adres) {
        adresRepository.opslaan(adres);
    }

    public void opslaan(final List<Adres> adressen, Long bedrijfId) {
        List<Adres> lijstBestaandeNummer = adresRepository.alles(SoortEntiteit.BEDRIJF, bedrijfId);

        //Verwijderen wat niet (meer) voorkomt
        Iterable<Adres> teVerwijderen = filter(lijstBestaandeNummer, new Predicate<Adres>() {
            @Override
            public boolean apply(Adres adres) {
                return !adressen.contains(adres);
            }
        });

        for (Adres adres : teVerwijderen) {
            adresRepository.verwijder(adres);
        }

        for (Adres adres : adressen) {
            adresRepository.opslaan(adres);
        }
    }

}
