package nl.dias.mapper;

import nl.dias.domein.JaarCijfers;
import nl.dias.domein.json.JsonJaarCijfers;
import org.springframework.stereotype.Component;

@Component
public class JaarCijfersNaarJsonJaarCijfersMapper extends AbstractMapper<JaarCijfers, JsonJaarCijfers> {
    @Override
    public JsonJaarCijfers map(JaarCijfers object, Object parent) {
        JsonJaarCijfers json = new JsonJaarCijfers();

        json.setJaar(object.getJaar());
        if (object.getBedrijf() != null) {
            json.setBedrijf(object.getBedrijf().getId());
        }
        json.setId(object.getId());

        return json;
    }

    @Override
    boolean isVoorMij(Object object) {
        return object instanceof JaarCijfers;
    }
}
