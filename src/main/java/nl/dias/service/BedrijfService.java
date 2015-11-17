package nl.dias.service;

import nl.dias.domein.*;
import nl.dias.repository.BedrijfRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

@Service
public class BedrijfService {
    private final static Logger LOGGER = LoggerFactory.getLogger(BedrijfService.class);

    @Inject
    private BedrijfRepository bedrijfRepository;

    public void opslaan(Bedrijf bedrijf) {
        bedrijfRepository.opslaan(bedrijf);
    }

    public Bedrijf lees(Long id) {
        return bedrijfRepository.lees(id);
    }

    public void verwijder(Long id) {
        bedrijfRepository.verwijder(lees(id));
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

    public void opslaanBijlage(String bedrijfId, Bijlage bijlage) {
        LOGGER.info("Opslaan bijlage met id {}, bij Bedrijf met id {}", bijlage.getId(), bedrijfId);

        Bedrijf bedrijf = bedrijfRepository.lees(Long.valueOf(bedrijfId));

        bedrijf.getBijlages().add(bijlage);
        bijlage.setBedrijf(bedrijf);
        bijlage.setSoortBijlage(SoortBijlage.BEDRIJF);

        LOGGER.debug(org.apache.commons.lang3.builder.ReflectionToStringBuilder.toString(bijlage));

        bedrijfRepository.opslaan(bedrijf);
    }

}
