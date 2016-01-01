package nl.dias.web.mapper;

import nl.dias.domein.Adres;
import nl.lakedigital.djfc.commons.json.JsonAdres;
import org.dozer.DozerBeanMapper;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class AdresMapper extends Mapper<Adres, JsonAdres> {
    @Override
    public Adres mapVanJson(JsonAdres json) {
        List<String> myMappingFiles = new ArrayList<>();
        myMappingFiles.add("dozer/adres.xml");

        DozerBeanMapper mapper = new DozerBeanMapper(myMappingFiles);

        return mapper.map(json, Adres.class);
    }

    @Override
    public JsonAdres mapNaarJson(Adres object) {
        List<String> myMappingFiles = new ArrayList<>();
        myMappingFiles.add("dozer/adres.xml");

        DozerBeanMapper mapper = new DozerBeanMapper(myMappingFiles);

        return mapper.map(object, JsonAdres.class);
    }
}
