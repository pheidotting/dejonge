package nl.dias.web.mapper;

import javax.inject.Named;

import nl.dias.domein.Opmerking;
import nl.dias.domein.json.JsonOpmerking;
import nl.dias.service.HypotheekService;
import nl.dias.service.SchadeService;

import com.sun.jersey.api.core.InjectParam;

@Named
public class OpmerkingMapper extends Mapper<Opmerking, JsonOpmerking> {
    @InjectParam
    private SchadeService schadeService;
    @InjectParam
    private HypotheekService hypotheekService;

    @Override
    public Opmerking mapVanJson(JsonOpmerking jsonOpmerking) {
        Opmerking opmerking = new Opmerking();

        opmerking.setId(jsonOpmerking.getId());
        opmerking.setOpmerking(jsonOpmerking.getOpmerking());
        try {
            opmerking.setSchade(schadeService.lees(Long.valueOf(jsonOpmerking.getSchade())));
        } catch (NumberFormatException nfe) {
        }
        try {
            opmerking.setHypotheek(hypotheekService.leesHypotheek(Long.valueOf(jsonOpmerking.getHypotheek())));
        } catch (NumberFormatException nfe) {
        }

        return opmerking;
    }

    @Override
    public JsonOpmerking mapNaarJson(Opmerking opmerking) {
        JsonOpmerking jsonOpmerking = new JsonOpmerking();

        jsonOpmerking.setId(opmerking.getId());
        jsonOpmerking.setOpmerking(opmerking.getOpmerking());
        jsonOpmerking.setTijd(opmerking.getTijd().toString("dd-MM-yyyy HH:mm"));
        jsonOpmerking.setMedewerker(opmerking.getMedewerker().getNaam());

        return jsonOpmerking;
    }

    public void setSchadeService(SchadeService schadeService) {
        this.schadeService = schadeService;
    }

}
