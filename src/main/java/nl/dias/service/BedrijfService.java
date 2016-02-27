package nl.dias.service;

import nl.dias.domein.Bedrijf;
import nl.dias.domein.Relatie;
import nl.dias.repository.BedrijfRepository;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

@Service
public class BedrijfService {
    private static final Logger LOGGER = LoggerFactory.getLogger(BedrijfService.class);

    @Inject
    private BedrijfRepository bedrijfRepository;

    public void opslaan(Bedrijf bedrijf) {
        bedrijfRepository.opslaan(bedrijf);
    }

    public Bedrijf lees(Long id) {
        return bedrijfRepository.lees(id);
    }

    public void verwijder(Long id) {
        LOGGER.debug("Verwijderen Bedrijf met id {}", id);

        Bedrijf bedrijf = lees(id);

        LOGGER.debug("Verwijderen : {}", ReflectionToStringBuilder.toString(bedrijf, ToStringStyle.SHORT_PREFIX_STYLE));

        bedrijfRepository.verwijder(bedrijf);
    }

    public List<Bedrijf> alleBedrijvenBijRelatie(Relatie relatie) {
        return bedrijfRepository.alleBedrijvenBijRelatie(relatie);
    }

    public List<Bedrijf> alles(){
        return bedrijfRepository.alles();
    }

    public List<Bedrijf> zoekOpNaam(String zoekTerm){
        return bedrijfRepository.zoekOpNaam(zoekTerm);
    }

    public void setBedrijfRepository(BedrijfRepository bedrijfRepository) {
        this.bedrijfRepository = bedrijfRepository;
    }
}
