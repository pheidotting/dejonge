package nl.dias.web.mapper;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.inject.Named;

import nl.dias.domein.Bedrijf;
import nl.dias.domein.json.JsonBedrijf;

@Named
public class BedrijfMapper implements Mapper<Bedrijf, JsonBedrijf> {

    @Override
    public Bedrijf mapVanJson(JsonBedrijf json) {
        Bedrijf bedrijf = new Bedrijf();

        bedrijf.getAdres().setHuisnummer(json.getHuisnummer());
        bedrijf.getAdres().setPlaats(json.getPlaats());
        bedrijf.getAdres().setPostcode(json.getPostcode());
        bedrijf.getAdres().setStraat(json.getStraat());
        bedrijf.getAdres().setToevoeging(json.getToevoeging());

        bedrijf.setId(json.getId());
        bedrijf.setKvk(json.getKvk());
        bedrijf.setNaam(json.getKvk());

        return bedrijf;
    }

    @Override
    public Set<Bedrijf> mapAllVanJson(List<JsonBedrijf> jsons) {
        Set<Bedrijf> lijst = new HashSet<>();

        for (JsonBedrijf json : jsons) {
            lijst.add(mapVanJson(json));
        }

        return lijst;
    }

    @Override
    public JsonBedrijf mapNaarJson(Bedrijf object) {
        JsonBedrijf json = new JsonBedrijf();

        json.setHuisnummer(object.getAdres().getHuisnummer());
        json.setId(object.getId());
        json.setKvk(object.getKvk());
        json.setNaam(object.getNaam());
        json.setPlaats(object.getAdres().getPlaats());
        json.setPostcode(object.getAdres().getPostcode());
        json.setRelatie(object.getRelatie().getId());
        json.setStraat(object.getAdres().getStraat());
        json.setToevoeging(object.getAdres().getToevoeging());

        return json;
    }

    @Override
    public List<JsonBedrijf> mapAllNaarJson(Set<Bedrijf> objecten) {
        List<JsonBedrijf> lijst = new ArrayList<>();

        for (Bedrijf bedrijf : objecten) {
            lijst.add(mapNaarJson(bedrijf));
        }

        return lijst;
    }

}
