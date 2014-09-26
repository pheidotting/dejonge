package nl.dias.service;

import java.util.List;

import javax.inject.Named;

import nl.dias.domein.Bijlage;
import nl.dias.domein.Hypotheek;
import nl.dias.domein.Relatie;
import nl.dias.domein.SoortBijlage;
import nl.dias.domein.SoortHypotheek;
import nl.dias.repository.HypotheekRepository;

import org.apache.log4j.Logger;

import com.sun.jersey.api.core.InjectParam;

@Named
public class HypotheekService {
    private final static Logger LOGGER = Logger.getLogger(HypotheekService.class);

    @InjectParam
    private HypotheekRepository hypotheekRepository;
    @InjectParam
    private GebruikerService gebruikerService;

    public void opslaan(Hypotheek hypotheek) {
        hypotheekRepository.opslaan(hypotheek);
    }

    public void opslaan(Hypotheek hypotheek, String hypotheekVorm, Long relatieId) {
        Relatie relatie = (Relatie) gebruikerService.lees(relatieId);
        SoortHypotheek soortHypotheek = hypotheekRepository.leesSoortHypotheek(Long.valueOf(hypotheekVorm));

        hypotheek.setRelatie(relatie);
        hypotheek.setHypotheekVorm(soortHypotheek);

        hypotheekRepository.opslaan(hypotheek);
    }

    public Hypotheek lees(Long id) {
        return hypotheekRepository.lees(id);
    }

    public List<SoortHypotheek> alleSoortenHypotheekInGebruik() {
        return hypotheekRepository.alleSoortenHypotheekInGebruik();
    }

    public List<Hypotheek> alles() {
        return hypotheekRepository.alles();
    }

    public void slaBijlageOp(Long hypotheekId, String s3Identificatie) {
        LOGGER.debug("Opslaan Bijlage bij Hypotheek, hypotheekId " + hypotheekId + " s3Identificatie " + s3Identificatie);

        Bijlage bijlage = new Bijlage();
        bijlage.setHypotheek(lees(hypotheekId));
        bijlage.setSoortBijlage(SoortBijlage.HYPOTHEEK);
        bijlage.setS3Identificatie(s3Identificatie);

        LOGGER.debug("Bijlage naar repository " + bijlage);

        hypotheekRepository.opslaanBijlage(bijlage);
    }

    public void setHypotheekRepository(HypotheekRepository hypotheekRepository) {
        this.hypotheekRepository = hypotheekRepository;
    }

    public void setGebruikerService(GebruikerService gebruikerService) {
        this.gebruikerService = gebruikerService;
    }
}