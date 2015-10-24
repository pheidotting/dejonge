package nl.dias.web.mapper;

import com.sun.jersey.api.core.InjectParam;
import nl.dias.domein.*;
import nl.dias.domein.json.JsonOpmerking;
import nl.dias.domein.polis.Polis;
import nl.dias.service.GebruikerService;
import nl.dias.service.HypotheekService;
import nl.dias.service.PolisService;
import nl.dias.service.SchadeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Named;

@Named
public class OpmerkingMapper extends Mapper<Opmerking, JsonOpmerking> {
    private final static Logger LOGGER = LoggerFactory.getLogger(OpmerkingMapper.class);

    @InjectParam
    private SchadeService schadeService;
    @InjectParam
    private HypotheekService hypotheekService;
    @InjectParam
    private PolisService polisService;
    @InjectParam
    private GebruikerService gebruikerService;

    @Override
    public Opmerking mapVanJson(JsonOpmerking jsonOpmerking) {
        Opmerking opmerking = new Opmerking();

        opmerking.setId(jsonOpmerking.getId());
        opmerking.setOpmerking(jsonOpmerking.getOpmerking());

        if (jsonOpmerking.getSchade() != null) {
            Schade schade = schadeService.lees(Long.valueOf(jsonOpmerking.getSchade()));
            opmerking.setSchade(schade);
        }
        if (jsonOpmerking.getHypotheek() != null) {
            Hypotheek hypotheek = hypotheekService.leesHypotheek(Long.valueOf(jsonOpmerking.getHypotheek()));
            opmerking.setHypotheek(hypotheek);
        }
        if (jsonOpmerking.getPolis() != null) {
            Polis polis = polisService.lees(Long.valueOf(jsonOpmerking.getPolis()));
            opmerking.setPolis(polis);
        }

        if (jsonOpmerking.getRelatie() != null) {
            Relatie relatie = gebruikerService.leesRelatie(Long.valueOf(jsonOpmerking.getRelatie()));
            opmerking.setRelatie(relatie);
        }
        LOGGER.debug("Opzoeken Medewerker met id {}",jsonOpmerking.getMedewerkerId());
        opmerking.setMedewerker((Medewerker)gebruikerService.lees(Long.valueOf(jsonOpmerking.getMedewerkerId())));

        LOGGER.debug("gemapte opmerking {}",opmerking);

        return opmerking;
    }

    @Override
    public JsonOpmerking mapNaarJson(Opmerking opmerking) {
        JsonOpmerking jsonOpmerking = new JsonOpmerking();

        jsonOpmerking.setId(opmerking.getId());
        jsonOpmerking.setOpmerking(opmerking.getOpmerking());
        jsonOpmerking.setTijd(opmerking.getTijd().toString("dd-MM-yyyy HH:mm"));
        jsonOpmerking.setMedewerker(opmerking.getMedewerker().getNaam());
        jsonOpmerking.setMedewerkerId(opmerking.getMedewerker().getId().toString());
        if (opmerking.getSchade() != null) {
            jsonOpmerking.setSchade(opmerking.getSchade().getId().toString());
        }
        if (opmerking.getPolis() != null) {
            jsonOpmerking.setPolis(opmerking.getPolis().getId().toString());
        }
        if (opmerking.getHypotheek() != null) {
            jsonOpmerking.setHypotheek(opmerking.getHypotheek().getId().toString());
        }

        return jsonOpmerking;
    }

    public void setSchadeService(SchadeService schadeService) {
        this.schadeService = schadeService;
    }

}
