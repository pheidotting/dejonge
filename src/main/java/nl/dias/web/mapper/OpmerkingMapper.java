package nl.dias.web.mapper;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.inject.Named;

import nl.dias.domein.Opmerking;
import nl.dias.domein.json.JsonOpmerking;

@Named
public class OpmerkingMapper implements Mapper<Opmerking, JsonOpmerking> {
    @Override
    public Opmerking mapVanJson(JsonOpmerking jsonOpmerking) {
        Opmerking opmerking = new Opmerking();

        opmerking.setId(jsonOpmerking.getId());
        opmerking.setOpmerking(jsonOpmerking.getOpmerking());
        // opmerking.setTijd(jsonOpmerking.getTijd());

        return opmerking;
    }

    @Override
    public Set<Opmerking> mapAllVanJson(List<JsonOpmerking> jsonOpmerkingen) {
        if (jsonOpmerkingen != null) {
            Set<Opmerking> ret = new HashSet<>();
            for (JsonOpmerking jsonOpmerking : jsonOpmerkingen) {
                ret.add(mapVanJson(jsonOpmerking));
            }
            return ret;
        } else {
            return null;
        }
    }

    @Override
    public JsonOpmerking mapNaarJson(Opmerking opmerking) {
        JsonOpmerking jsonOpmerking = new JsonOpmerking();

        jsonOpmerking.setId(opmerking.getId());
        jsonOpmerking.setOpmerking(opmerking.getOpmerking());
        // jsonOpmerking.setTijd(opmerking.getTijd());

        return jsonOpmerking;
    }

    @Override
    public List<JsonOpmerking> mapAllNaarJson(Set<Opmerking> opmerkingen) {
        List<JsonOpmerking> ret = new ArrayList<>();
        for (Opmerking opmerking : opmerkingen) {
            ret.add(mapNaarJson(opmerking));
        }
        return ret;
    }
}
