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

    public void slaBijlageOp(String bestandsNaam, Long polisId, SoortBijlage soortBijlage, String s3Identificatie) {
        LOGGER.debug("Opslaan Bijlage bestandsNaam " + bestandsNaam + " polisId " + polisId + " soortBijlage " + soortBijlage + " s3Identificatie " + s3Identificatie);

        Bijlage bijlage = new Bijlage();
        bijlage.setBestandsNaam(bestandsNaam);
        bijlage.setPolis(polisRepository.lees(polisId));
        bijlage.setSoortBijlage(soortBijlage);
        bijlage.setS3Identificatie(s3Identificatie);

        LOGGER.debug("Bijlage naar repository " + bijlage);

        polisRepository.opslaanBijlage(bijlage);
    }

    public Bijlage leesBijlage(Long id) {
        return polisRepository.leesBijlage(id);
    }
}
