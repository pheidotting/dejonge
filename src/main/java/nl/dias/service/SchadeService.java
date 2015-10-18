package nl.dias.service;

import com.sun.jersey.api.core.InjectParam;
import nl.dias.domein.*;
import nl.dias.domein.polis.Polis;
import nl.dias.repository.SchadeRepository;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Named;
import java.util.List;

@Named
public class SchadeService {
    private final static Logger LOGGER = LoggerFactory.getLogger(SchadeService.class);

    @InjectParam
    private SchadeRepository schadeRepository;
    @InjectParam
    private PolisService polisService;

    public List<SoortSchade> soortenSchade() {
        return schadeRepository.soortenSchade();
    }

    public List<SoortSchade> soortenSchade(String omschrijving) {
        return schadeRepository.soortenSchade(omschrijving);
    }

    public StatusSchade getStatussen(String status) {
        return schadeRepository.getStatussen(status);
    }

    public List<StatusSchade> getStatussen() {
        return schadeRepository.getStatussen();
    }

    public Schade zoekOpSchadeNummerMaatschappij(String schadeNummer) {
        return schadeRepository.zoekOpSchadeNummerMaatschappij(schadeNummer);
    }

    public void verwijder(Long id) {
        Schade schade = lees(id);
        schadeRepository.verwijder(schade);
    }

    public void opslaanBijlage(String schadeId, Bijlage bijlage) {
        LOGGER.info("Opslaan bijlage met id {}, bij Schade met id {}", bijlage.getId(), schadeId);

        Schade schade = schadeRepository.lees(Long.valueOf(schadeId));

        schade.getBijlages().add(bijlage);
        bijlage.setSchade(schade);
        bijlage.setSoortBijlage(SoortBijlage.SCHADE);

        LOGGER.debug(ReflectionToStringBuilder.toString(bijlage));

        schadeRepository.opslaan(schade);
    }

    public void slaBijlageOp(Long schadeId, Bijlage bijlage, String omschrijving) {
        LOGGER.debug("Opslaan Bijlage bij Schade, schadeId " + schadeId);

        bijlage.setSchade(schadeRepository.lees(schadeId));
        bijlage.setSoortBijlage(SoortBijlage.SCHADE);
        bijlage.setOmschrijving(omschrijving);

        LOGGER.debug("Bijlage naar repository " + bijlage);

        schadeRepository.opslaanBijlage(bijlage);
    }

    public void opslaan(Schade schade) {
        schadeRepository.opslaan(schade);
    }

    public void opslaan(Schade schadeIn, String soortSchade, String polisId, String statusSchade) {
        LOGGER.debug("Opslaan schade");
        LOGGER.debug("{}", schadeIn);

        Schade schade = schadeIn;

        LOGGER.debug("Soort schade opzoeken " + soortSchade);
        List<SoortSchade> soorten = schadeRepository.soortenSchade(soortSchade);

        LOGGER.debug("Status schade opzoeken " + statusSchade);
        StatusSchade status = schadeRepository.getStatussen(statusSchade);

        schade.setStatusSchade(status);

        if (!soorten.isEmpty()) {
            LOGGER.debug("Soort schade gevonden in database (" + soorten.size() + ")");
            schade.setSoortSchade(soorten.get(0));
        } else {
            LOGGER.debug("Geen soort schade gevonden in database, tekst dus opslaan");
            schade.setSoortSchadeOngedefinieerd(soortSchade);
        }

        LOGGER.debug("Polis opzoeken, id : " + polisId);
        Polis polis = polisService.lees(Long.valueOf(polisId));
        schade.setPolis(polis);

        // Bijlages er bij zoeken
        List<Bijlage> bijlages = schadeRepository.zoekBijlagesBijSchade(schade);
        schade.getBijlages().addAll(bijlages);

        LOGGER.debug("Schade opslaan");
        schadeRepository.opslaan(schade);

        LOGGER.debug("Opmerkingen bij de schade zoeken en er weer bij plaatsen");
        Schade schadeOorspronkelijk = schadeRepository.lees(schade.getId());
        schade.setOpmerkingen(schadeOorspronkelijk.getOpmerkingen());
    }

    public List<Schade> alleSchadesBijRelatie(Relatie relatie) {
        return schadeRepository.alleSchadesBijRelatie(relatie);
    }

    public Schade lees(Long id) {
        return schadeRepository.lees(id);
    }

    public void setSchadeRepository(SchadeRepository schadeRepository) {
        this.schadeRepository = schadeRepository;
    }

    public void setPolisService(PolisService polisService) {
        this.polisService = polisService;
    }
}
