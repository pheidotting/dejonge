package nl.dias.web.mapper;

import javax.inject.Named;

import nl.dias.domein.HypotheekPakket;
import nl.dias.domein.json.JsonHypotheekPakket;

import com.sun.jersey.api.core.InjectParam;

@Named
public class HypotheekPakketMapper extends Mapper<HypotheekPakket, JsonHypotheekPakket> {
    @InjectParam
    private HypotheekMapper hypotheekMapper;

    @Override
    public HypotheekPakket mapVanJson(JsonHypotheekPakket json) {
        return null;
    }

    @Override
    public JsonHypotheekPakket mapNaarJson(HypotheekPakket object) {
        JsonHypotheekPakket pakket = new JsonHypotheekPakket();

        pakket.setHypotheken(hypotheekMapper.mapAllNaarJson(object.getHypotheken()));

        return pakket;
    }

    public void setHypotheekMapper(HypotheekMapper hypotheekMapper) {
        this.hypotheekMapper = hypotheekMapper;
    }
}
