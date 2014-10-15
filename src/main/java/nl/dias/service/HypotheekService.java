package nl.dias.service;

import java.util.List;

import javax.inject.Named;

import nl.dias.domein.Bijlage;
import nl.dias.domein.Hypotheek;
import nl.dias.domein.HypotheekPakket;
import nl.dias.domein.Relatie;
import nl.dias.domein.SoortBijlage;
import nl.dias.domein.SoortHypotheek;
import nl.dias.repository.HypotheekPakketRepository;
import nl.dias.repository.HypotheekRepository;

import org.apache.log4j.Logger;

import com.sun.jersey.api.core.InjectParam;

@Named
public class HypotheekService {
    private final static Logger LOGGER = Logger.getLogger(HypotheekService.class);

    @InjectParam
    private HypotheekRepository hypotheekRepository;
    @InjectParam
    private HypotheekPakketRepository hypotheekPakketRepository;
    @InjectParam
    private GebruikerService gebruikerService;

    public void opslaan(Hypotheek hypotheek) {
        hypotheekRepository.opslaan(hypotheek);
    }

    public void opslaan(Hypotheek hypotheek, String hypotheekVorm, Long relatieId, Long gekoppeldeHypotheekId) {
        Relatie relatie = (Relatie) gebruikerService.lees(relatieId);
        SoortHypotheek soortHypotheek = hypotheekRepository.leesSoortHypotheek(Long.valueOf(hypotheekVorm));

        hypotheek.setRelatie(relatie);
        hypotheek.setHypotheekVorm(soortHypotheek);

        HypotheekPakket pakket = null;

        if (gekoppeldeHypotheekId != null) {
            Hypotheek gekoppeldeHypotheek = hypotheekRepository.lees(gekoppeldeHypotheekId);

            if (gekoppeldeHypotheek.getHypotheekPakket() == null) {
                pakket = new HypotheekPakket();
                pakket.getHypotheken().add(gekoppeldeHypotheek);
                hypotheekPakketRepository.opslaan(pakket);
            } else {
                pakket = gekoppeldeHypotheek.getHypotheekPakket();
            }
            pakket.getHypotheken().add(hypotheek);
            hypotheek.setHypotheekPakket(pakket);

        }

        hypotheekRepository.opslaan(hypotheek);
        if (pakket != null) {
            hypotheekPakketRepository.opslaan(pakket);
        }
    }

    public Hypotheek leesHypotheek(Long id) {
        return hypotheekRepository.lees(id);
    }

    public HypotheekPakket leesHypotheekPakket(Long id) {
        return hypotheekPakketRepository.lees(id);
    }

    public SoortHypotheek leesSoortHypotheek(Long id) {
        return hypotheekRepository.leesSoortHypotheek(id);
    }

    public List<SoortHypotheek> alleSoortenHypotheekInGebruik() {
        return hypotheekRepository.alleSoortenHypotheekInGebruik();
    }

    public List<Hypotheek> allesVanRelatie(Long relatieId) {
        Relatie relatie = (Relatie) gebruikerService.lees(relatieId);

        return hypotheekRepository.allesVanRelatie(relatie);
    }

    public List<Hypotheek> allesVanRelatieInclDePakketten(Long relatieId) {
        Relatie relatie = (Relatie) gebruikerService.lees(relatieId);

        return hypotheekRepository.allesVanRelatieInclDePakketten(relatie);
    }

    public List<HypotheekPakket> allePakketenVanRelatie(Long relatieId) {
        Relatie relatie = (Relatie) gebruikerService.lees(relatieId);

        return hypotheekPakketRepository.allesVanRelatie(relatie);
    }

    public void slaBijlageOp(Long hypotheekId, String s3Identificatie) {
        LOGGER.debug("Opslaan Bijlage bij Hypotheek, hypotheekId " + hypotheekId + " s3Identificatie " + s3Identificatie);

        Bijlage bijlage = new Bijlage();
        bijlage.setHypotheek(leesHypotheek(hypotheekId));
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

    public void setHypotheekPakketRepository(HypotheekPakketRepository hypotheekPakketRepository) {
        this.hypotheekPakketRepository = hypotheekPakketRepository;
    }
}