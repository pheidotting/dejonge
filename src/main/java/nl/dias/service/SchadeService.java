package nl.dias.service;

import java.util.List;

import javax.inject.Named;

import nl.dias.domein.Bijlage;
import nl.dias.domein.Relatie;
import nl.dias.domein.Schade;
import nl.dias.domein.SoortBijlage;
import nl.dias.domein.SoortSchade;
import nl.dias.domein.StatusSchade;
import nl.dias.domein.polis.Polis;
import nl.dias.repository.SchadeRepository;

import org.apache.log4j.Logger;

import com.sun.jersey.api.core.InjectParam;

@Named
public class SchadeService {
    private final static Logger LOGGER = Logger.getLogger(SchadeService.class);

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

    public void slaBijlageOp(Long schadeId, String s3Identificatie) {
        LOGGER.debug("Opslaan Bijlage bij Schade, schadeId " + schadeId + " s3Identificatie " + s3Identificatie);

        Bijlage bijlage = new Bijlage();
        bijlage.setSchade(schadeRepository.lees(schadeId));
        bijlage.setSoortBijlage(SoortBijlage.SCHADE);
        bijlage.setS3Identificatie(s3Identificatie);

        LOGGER.debug("Bijlage naar repository " + bijlage);

        schadeRepository.opslaanBijlage(bijlage);
    }

    public void opslaan(Schade schade) {
        schadeRepository.opslaan(schade);
    }

    public void opslaan(Schade schadeIn, String soortSchade, String polisId, String statusSchade) {
        LOGGER.debug("Opslaan schade");

        Schade schade = schadeIn;

        LOGGER.debug("Soort schade opzoeken " + soortSchade);
        List<SoortSchade> soorten = schadeRepository.soortenSchade(soortSchade);

        LOGGER.debug("Status schade opzoeken " + statusSchade);
        StatusSchade status = schadeRepository.getStatussen(statusSchade);

        schade.setStatusSchade(status);

        if (soorten.size() > 0) {
            LOGGER.debug("Soort schade gevonden in database (" + soorten.size() + ")");
            schade.setSoortSchade(soorten.get(0));
        } else {
            LOGGER.debug("Geen soort schade gevonden in database, tekst dus opslaan");
            schade.setSoortSchadeOngedefinieerd(soortSchade);
        }

        LOGGER.debug("Polis opzoeken, id : " + polisId);
        Polis polis = polisService.lees(Long.valueOf(polisId));
        schade.setPolis(polis);

        LOGGER.debug("Schade opslaan");
        schadeRepository.opslaan(schade);
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
