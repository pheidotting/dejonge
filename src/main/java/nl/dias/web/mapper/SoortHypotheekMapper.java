package nl.dias.web.mapper;

import javax.inject.Named;

import nl.dias.domein.SoortHypotheek;
import nl.dias.domein.json.JsonSoortHypotheek;

@Named
public class SoortHypotheekMapper extends Mapper<SoortHypotheek, JsonSoortHypotheek> {

    @Override
    public SoortHypotheek mapVanJson(JsonSoortHypotheek json) {
        return null;
    }

    @Override
    public JsonSoortHypotheek mapNaarJson(SoortHypotheek object) {
        JsonSoortHypotheek jsonSoortHypotheek = new JsonSoortHypotheek();

        jsonSoortHypotheek.setId(object.getId());
        jsonSoortHypotheek.setIngebruik(object.isIngebruik());
        jsonSoortHypotheek.setOmschrijving(object.getOmschrijving());

        return jsonSoortHypotheek;
    }

}