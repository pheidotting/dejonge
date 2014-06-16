package nl.dias.service;

import java.util.List;

import javax.inject.Named;

import nl.dias.domein.Bijlage;
import nl.dias.domein.Relatie;
import nl.dias.domein.SoortBijlage;
import nl.dias.domein.polis.Polis;
import nl.dias.repository.PolisRepository;

import org.apache.log4j.Logger;

import com.sun.jersey.api.core.InjectParam;

@Named
public class PolisService {
    private final static Logger LOGGER = Logger.getLogger(PolisService.class);

    @InjectParam
    private PolisRepository polisRepository;

    public List<Polis> allePolissenVanRelatieEnZijnBedrijf(Relatie relatie) {
        return polisRepository.allePolissenVanRelatieEnZijnBedrijf(relatie);
    }

    public void opslaan(Polis polis) {
        polisRepository.opslaan(polis);
    }

    public Polis zoekOpPolisNummer(String PolisNummer) {
        return polisRepository.zoekOpPolisNummer(PolisNummer);
    }

    public void slaBijlageOp(Long polisId, SoortBijlage soortBijlage, String s3Identificatie) {
        LOGGER.debug("Opslaan Bijlage polisId " + polisId + " soortBijlage " + soortBijlage + " s3Identificatie " + s3Identificatie);

        Bijlage bijlage = new Bijlage();
        bijlage.setPolis(polisRepository.lees(polisId));
        bijlage.setSoortBijlage(soortBijlage);
        bijlage.setS3Identificatie(s3Identificatie);

        LOGGER.debug("Bijlage naar repository " + bijlage);

        polisRepository.opslaanBijlage(bijlage);
    }

    public Bijlage leesBijlage(Long id) {
        return polisRepository.leesBijlage(id);
    }

    public void verwijder(Long id) throws IllegalArgumentException {
        Polis polis = polisRepository.lees(id);
        if (polis == null) {
            throw new IllegalArgumentException("Geen Polis gevonden met id " + id);
        }

        polisRepository.verwijder(polis);
    }
}
