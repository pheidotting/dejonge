package nl.dias.web.mapper;

import javax.inject.Named;

import nl.dias.domein.Hypotheek;
import nl.dias.domein.HypotheekPakket;
import nl.dias.domein.json.JsonHypotheekPakket;

import com.ibm.icu.math.BigDecimal;
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

        pakket.setId(object.getId());
        pakket.setHypotheken(hypotheekMapper.mapAllNaarJson(object.getHypotheken()));

        BigDecimal totaalBedrag = BigDecimal.ZERO;
        for (Hypotheek h : object.getHypotheken()) {
            BigDecimal toeTeVoegen = new BigDecimal(h.getHypotheekBedrag().getBedrag());
            totaalBedrag = totaalBedrag.add(toeTeVoegen);
        }
        pakket.setTotaalBedrag(totaalBedrag.setScale(2, BigDecimal.ROUND_HALF_UP).toString());

        return pakket;
    }

    public void setHypotheekMapper(HypotheekMapper hypotheekMapper) {
        this.hypotheekMapper = hypotheekMapper;
    }
}
