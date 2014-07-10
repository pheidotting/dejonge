package nl.dias.web.mapper;

import javax.inject.Named;

import nl.dias.domein.Opmerking;
import nl.dias.domein.json.JsonOpmerking;

@Named
public class OpmerkingMapper extends Mapper<Opmerking, JsonOpmerking> {
    @Override
    public Opmerking mapVanJson(JsonOpmerking jsonOpmerking) {
        Opmerking opmerking = new Opmerking();

        opmerking.setId(jsonOpmerking.getId());
        opmerking.setOpmerking(jsonOpmerking.getOpmerking());
        // opmerking.setTijd(jsonOpmerking.getTijd());

        return opmerking;
    }

    @Override
    public JsonOpmerking mapNaarJson(Opmerking opmerking) {
        JsonOpmerking jsonOpmerking = new JsonOpmerking();

        jsonOpmerking.setId(opmerking.getId());
        jsonOpmerking.setOpmerking(opmerking.getOpmerking());
        jsonOpmerking.setTijd(opmerking.getTijd().toString("dd-MM-yyyy"));
        jsonOpmerking.setMedewerker(opmerking.getMedewerker().getNaam());

        return jsonOpmerking;
    }
}
