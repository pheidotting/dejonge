package nl.dias.web.mapper;

import nl.dias.domein.Adres;
import nl.lakedigital.djfc.commons.json.JsonAdres;
import org.dozer.DozerBeanMapper;
import org.springframework.stereotype.Component;

@Component
public class AdresMapper extends Mapper<Adres, JsonAdres> {
    @Override
    public Adres mapVanJson(JsonAdres json) {
        DozerBeanMapper mapper = new DozerBeanMapper();

        return mapper.map(json, Adres.class);
    }

    @Override
    public JsonAdres mapNaarJson(Adres object) {
        DozerBeanMapper mapper = new DozerBeanMapper();

        return mapper.map(object, JsonAdres.class);
    }
}
