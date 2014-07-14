package nl.dias.web.mapper;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Named;

import nl.dias.domein.SoortSchade;
import nl.dias.domein.json.JsonSoortSchade;

@Named
public class SoortSchadeMapper extends Mapper<SoortSchade, JsonSoortSchade> {

    @Override
    public SoortSchade mapVanJson(JsonSoortSchade json) {
        return null;
    }

    @Override
    public JsonSoortSchade mapNaarJson(SoortSchade object) {
        JsonSoortSchade jsonSoortSchade = new JsonSoortSchade(object.getOmschrijving());
        return jsonSoortSchade;
    }

    public List<JsonSoortSchade> mapAllNaarJson(List<SoortSchade> objecten) {
        if (objecten != null) {
            List<JsonSoortSchade> ret = new ArrayList<>();
            for (SoortSchade obj : objecten) {
                ret.add(mapNaarJson(obj));
            }
            return ret;
        } else {
            return null;
        }
    }

}
