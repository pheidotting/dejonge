package nl.dias.service;

import java.util.List;

import javax.inject.Named;
import javax.persistence.NoResultException;

import nl.dias.domein.Bedrijf;
import nl.dias.domein.Bijlage;
import nl.dias.domein.Relatie;
import nl.dias.domein.SoortBijlage;
import nl.dias.domein.polis.Polis;
import nl.dias.repository.KantoorRepository;
import nl.dias.repository.PolisRepository;
import nl.lakedigital.archief.service.ArchiefService;

import org.apache.log4j.Logger;

import com.sun.jersey.api.core.InjectParam;

@Named
public class PolisService {
    private final static Logger LOGGER = Logger.getLogger(PolisService.class);

    @InjectParam
    private PolisRepository polisRepository;
    @InjectParam
    private ArchiefService archiefService;
    @InjectParam
    private GebruikerService gebruikerService;
    @InjectParam
    private KantoorRepository kantoorRepository;

    public List<Polis> allePolissenVanRelatieEnZijnBedrijf(Relatie relatie) {
        return polisRepository.allePolissenVanRelatieEnZijnBedrijf(relatie);
    }

    public void opslaan(Polis polis) {
        polisRepository.opslaan(polis);
    }

    public Polis zoekOpPolisNummer(String polisNummer) {
        try {
            return polisRepository.zoekOpPolisNummer(polisNummer, kantoorRepository.lees(1L));
        } catch (NoResultException e) {
            LOGGER.debug("Niks gevonden " + e.getMessage());
            return null;
        }
    }

    public void slaBijlageOp(Long polisId, String s3Identificatie) {
        LOGGER.debug("Opslaan Bijlage bij Polis, polisId " + polisId + " s3Identificatie " + s3Identificatie);

        Bijlage bijlage = new Bijlage();
        bijlage.setPolis(polisRepository.lees(polisId));
        bijlage.setSoortBijlage(SoortBijlage.POLIS);
        bijlage.setS3Identificatie(s3Identificatie);

        LOGGER.debug("Bijlage naar repository " + bijlage);

        polisRepository.opslaanBijlage(bijlage);
    }

    public Bijlage leesBijlage(Long id) {
        return polisRepository.leesBijlage(id);
    }

    public void verwijder(Long id) throws IllegalArgumentException {
        archiefService.setBucketName("dias");

        LOGGER.debug("Ophalen Polis");
        Polis polis = polisRepository.lees(id);

        if (polis == null) {
            throw new IllegalArgumentException("Geen Polis gevonden met id " + id);
        }
        LOGGER.debug("Polis gevonden : " + polis);

        LOGGER.debug("Ophalen Relatie");
        Relatie relatie = (Relatie) gebruikerService.lees(polis.getRelatie().getId());

        LOGGER.debug("Verwijderen Polis bij Relatie");
        relatie.getPolissen().remove(polis);
        LOGGER.debug("Kijken of de Polis nog bij een bedrijf zit");
        for (Bedrijf bedrijf : relatie.getBedrijven()) {
            LOGGER.debug("Bedrijf met id " + bedrijf.getId());
            bedrijf.getPolissen().remove(polis);
        }

        gebruikerService.opslaan(relatie);

        for (Bijlage bijlage : polis.getBijlages()) {
            archiefService.verwijderen(bijlage.getS3Identificatie());
        }

        polisRepository.verwijder(polis);
    }
}
