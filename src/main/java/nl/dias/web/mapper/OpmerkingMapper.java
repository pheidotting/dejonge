package nl.dias.web.mapper;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.inject.Named;

import nl.dias.domein.Opmerking;
import nl.dias.domein.json.JsonOpmerking;

@Named
public class OpmerkingMapper {
    public Opmerking mapVanJson(JsonOpmerking jsonOpmerking) {
        Opmerking opmerking = new Opmerking();

        opmerking.setId(jsonOpmerking.getId());
        opmerking.setOpmerking(jsonOpmerking.getOpmerking());
        opmerking.setTijd(jsonOpmerking.getTijd());

        return opmerking;
    }

    public Set<Opmerking> mapAllVanJson(List<JsonOpmerking> jsonOpmerkingen) {
        Set<Opmerking> ret = new HashSet<>();
        for (JsonOpmerking jsonOpmerking : jsonOpmerkingen) {
            ret.add(mapVanJson(jsonOpmerking));
        }
        return ret;
    }

    public JsonOpmerking mapNaarJson(Opmerking opmerking) {
        JsonOpmerking jsonOpmerking = new JsonOpmerking();

        jsonOpmerking.setId(opmerking.getId());
        jsonOpmerking.setOpmerking(opmerking.getOpmerking());
        jsonOpmerking.setTijd(opmerking.getTijd());

        return jsonOpmerking;
    }

    public List<JsonOpmerking> mapAllNaarJson(Set<Opmerking> opmerkingen) {
        List<JsonOpmerking> ret = new ArrayList<>();
        for (Opmerking opmerking : opmerkingen) {
            ret.add(mapNaarJson(opmerking));
        }
        return ret;
    }
}
