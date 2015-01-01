package nl.dias.web.mapper;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Named;

import nl.dias.domein.Bedrijf;
import nl.dias.domein.json.JsonBedrijf;

import org.dozer.DozerBeanMapper;

@Named
public class BedrijfMapper extends Mapper<Bedrijf, JsonBedrijf> {

    @Override
    public Bedrijf mapVanJson(JsonBedrijf json) {
        List<String> myMappingFiles = new ArrayList<>();
        myMappingFiles.add("dozer/bedrijf.xml");

        DozerBeanMapper mapper = new DozerBeanMapper(myMappingFiles);

        return mapper.map(json, Bedrijf.class);
    }

    @Override
    public JsonBedrijf mapNaarJson(Bedrijf object) {
        List<String> myMappingFiles = new ArrayList<>();
        myMappingFiles.add("dozer/bedrijf.xml");

        DozerBeanMapper mapper = new DozerBeanMapper(myMappingFiles);

        return mapper.map(object, JsonBedrijf.class);
    }
}