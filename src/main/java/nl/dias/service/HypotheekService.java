package nl.dias.service;

import com.sun.jersey.api.core.InjectParam;
import nl.dias.domein.*;
import nl.dias.domein.json.JsonHypotheek;
import nl.dias.repository.HypotheekPakketRepository;
import nl.dias.repository.HypotheekRepository;
import nl.dias.web.mapper.HypotheekMapper;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.log4j.Logger;

import javax.inject.Named;
import java.util.List;

@Named
public class HypotheekService {
    private final static Logger LOGGER = Logger.getLogger(HypotheekService.class);

    @InjectParam
    private HypotheekRepository hypotheekRepository;
    @InjectParam
    private HypotheekPakketRepository hypotheekPakketRepository;
    @InjectParam
    private GebruikerService gebruikerService;
    private HypotheekMapper hypotheekMapper;

    public void opslaan(Hypotheek hypotheek) {
        hypotheekRepository.opslaan(hypotheek);
    }

    public Hypotheek opslaan(JsonHypotheek jsonHypotheek, String hypotheekVorm, Long relatieId, Long gekoppeldeHypotheekId) {
        Relatie relatie = (Relatie) gebruikerService.lees(relatieId);
        SoortHypotheek soortHypotheek = hypotheekRepository.leesSoortHypotheek(Long.valueOf(hypotheekVorm));

        Hypotheek hypotheek = null;
        if (jsonHypotheek.getId() == null) {
            hypotheek = new Hypotheek();
        } else {
            hypotheek = leesHypotheek(jsonHypotheek.getId());
        }
        if (hypotheekMapper == null) {
            hypotheekMapper = new HypotheekMapper();
        }
        hypotheek = hypotheekMapper.mapVanJson(jsonHypotheek, hypotheek);

        if (hypotheek.getRelatie() == null) {
            hypotheek.setRelatie(relatie);
            hypotheek.setHypotheekVorm(soortHypotheek);
        }

        HypotheekPakket pakket = null;

        LOGGER.debug("gekoppeldeHypotheekId " + gekoppeldeHypotheekId);
        Hypotheek gekoppeldeHypotheek = null;
        if (gekoppeldeHypotheekId != null) {
            LOGGER.debug("Opzoeken te koppelen Hypotheek");
            gekoppeldeHypotheek = hypotheekRepository.lees(gekoppeldeHypotheekId);

            LOGGER.debug("Gevonden : " + ReflectionToStringBuilder.toString(gekoppeldeHypotheek).replace(Hypotheek.class.getPackage().getName(), ""));

            if (gekoppeldeHypotheek.getHypotheekPakket() == null) {
                LOGGER.debug("Nieuw pakket aanmaken");
                pakket = new HypotheekPakket();
                pakket.setRelatie(relatie);
                LOGGER.debug("en opslaan " + pakket);
                hypotheekPakketRepository.opslaan(pakket);

                pakket.getHypotheken().add(gekoppeldeHypotheek);
                gekoppeldeHypotheek.setHypotheekPakket(pakket);
                LOGGER.debug("Opslaan gekoppeldeHypotheek " + gekoppeldeHypotheek);
                hypotheekRepository.opslaan(gekoppeldeHypotheek);
            } else {
                pakket = gekoppeldeHypotheek.getHypotheekPakket();
                LOGGER.debug("Koppelen aan bestaand pakket  " + ReflectionToStringBuilder.toString(pakket).replace(HypotheekPakket.class.getPackage().getName(), ""));
            }
        } else {
            pakket = new HypotheekPakket();
            pakket.setRelatie(relatie);
            hypotheekPakketRepository.opslaan(pakket);
        }

        LOGGER.debug("En opslaan " + hypotheek);
        hypotheekRepository.opslaan(hypotheek);

        LOGGER.debug("Toevoegen aan pakket");
        LOGGER.debug(ReflectionToStringBuilder.toString(pakket));
        pakket.getHypotheken().add(hypotheek);
        hypotheek.setHypotheekPakket(pakket);
        hypotheekRepository.opslaan(hypotheek);

        if (gekoppeldeHypotheek != null) {
            gekoppeldeHypotheek.setHypotheekPakket(pakket);
            pakket.getHypotheken().add(gekoppeldeHypotheek);

            hypotheekRepository.opslaan(gekoppeldeHypotheek);
        }

        LOGGER.debug("pakket nog ff weer opslaan " + pakket);
        hypotheekPakketRepository.opslaan(pakket);

        return hypotheek;
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

    public Long slaBijlageOp(Long hypotheekId, Bijlage bijlage, String omschrijving) {
        LOGGER.debug("Opslaan Bijlage bij Hypotheek, hypotheekId " + hypotheekId);

        bijlage.setHypotheek(leesHypotheek(hypotheekId));
        bijlage.setSoortBijlage(SoortBijlage.HYPOTHEEK);
        bijlage.setOmschrijving(omschrijving);

        LOGGER.debug("Bijlage naar repository " + bijlage);

        hypotheekRepository.opslaanBijlage(bijlage);

        return bijlage.getId();
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

    public void setHypotheekMapper(HypotheekMapper hypotheekMapper) {
        this.hypotheekMapper = hypotheekMapper;
    }
}