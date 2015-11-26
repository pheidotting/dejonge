package nl.dias.mapper;

import nl.dias.domein.RisicoAnalyse;
import nl.dias.domein.json.JsonRisicoAnalyse;
import org.springframework.stereotype.Component;

@Component
public class RisicoAnalyseNaarJsonRisicoAnalyseMapper extends AbstractMapper<RisicoAnalyse, JsonRisicoAnalyse> {
    @Override
    public JsonRisicoAnalyse map(RisicoAnalyse object) {
        JsonRisicoAnalyse jsonRisicoAnalyse = new JsonRisicoAnalyse();
        jsonRisicoAnalyse.setId(object.getId());
        jsonRisicoAnalyse.setBedrijf(object.getBedrijf().getId());

        return jsonRisicoAnalyse;
    }

    @Override
    boolean isVoorMij(Object object) {
        return object instanceof RisicoAnalyse;
    }
}