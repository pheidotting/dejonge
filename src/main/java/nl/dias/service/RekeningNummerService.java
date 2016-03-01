package nl.dias.service;

import com.google.common.base.Predicate;
import nl.dias.domein.RekeningNummer;
import nl.dias.repository.RekeningNummerRepository;
import nl.dias.web.SoortEntiteit;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

import static com.google.common.collect.Iterables.filter;

@Service
public class RekeningNummerService {
    private static final Logger LOGGER = LoggerFactory.getLogger(RekeningNummerService.class);

    @Inject
    private RekeningNummerRepository rekeningNummerRepository;

    public List<RekeningNummer> alles(SoortEntiteit soortEntiteit, Long parentId) {
        LOGGER.debug("alles soortEntiteit {} parentId {}", soortEntiteit, parentId);

        return rekeningNummerRepository.alles(soortEntiteit, parentId);
    }

    public void opslaan(final List<RekeningNummer> rekeningNummers, SoortEntiteit soortEntiteit, Long entiteitId) {
        LOGGER.debug("Opslaan {} rekeningNummers", rekeningNummers.size());
        List<RekeningNummer> lijstBestaandeNummer = rekeningNummerRepository.alles(soortEntiteit, entiteitId);

        LOGGER.debug("Aanwezig {} rekeningNummers", rekeningNummers.size());

        //Verwijderen wat niet (meer) voorkomt
        Iterable<RekeningNummer> teVerwijderen = filter(lijstBestaandeNummer, new Predicate<RekeningNummer>() {
            @Override
            public boolean apply(RekeningNummer rekeningNummer) {
                return !rekeningNummers.contains(rekeningNummer);
            }
        });

        for (RekeningNummer rekeningNummer : teVerwijderen) {
            LOGGER.debug("Verwijderen : {}", ReflectionToStringBuilder.toString(rekeningNummer, ToStringStyle.SHORT_PREFIX_STYLE));
            rekeningNummerRepository.verwijder(rekeningNummer);
        }

        for (RekeningNummer rekeningNummer : rekeningNummers) {
            LOGGER.debug("Opslaan : {}", ReflectionToStringBuilder.toString(rekeningNummer, ToStringStyle.SHORT_PREFIX_STYLE));
            rekeningNummerRepository.opslaan(rekeningNummer);
        }
    }

    public void verwijderen(SoortEntiteit soortEntiteit, Long entiteitId) {
        List<RekeningNummer> teVerwijderen = rekeningNummerRepository.alles(soortEntiteit, entiteitId);

        for (RekeningNummer rekeningNummer : teVerwijderen) {
            rekeningNummerRepository.verwijder(rekeningNummer);
        }
    }

}
