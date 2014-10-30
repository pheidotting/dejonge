package nl.dias.web.mapper;

import javax.inject.Named;

import nl.dias.domein.Bedrijf;
import nl.dias.domein.json.JsonBedrijf;

@Named
public class BedrijfMapper extends Mapper<Bedrijf, JsonBedrijf> {

    @Override
    public Bedrijf mapVanJson(JsonBedrijf json) {
        Bedrijf bedrijf = new Bedrijf();

        if (json.getHuisnummer() != null) {
            try {
                bedrijf.getAdres().setHuisnummer(Long.parseLong(json.getHuisnummer()));
            } catch (NumberFormatException e) {
                throw new NumberFormatException("Huisnummer bevat een ongeldig teken.");
            }
        }
        bedrijf.getAdres().setPlaats(json.getPlaats());
        bedrijf.getAdres().setPostcode(json.getPostcode());
        bedrijf.getAdres().setStraat(json.getStraat());
        bedrijf.getAdres().setToevoeging(json.getToevoeging());

        if (json.getId() != null) {
            bedrijf.setId(Long.parseLong(json.getId()));
        }
        bedrijf.setKvk(json.getKvk());
        bedrijf.setNaam(json.getNaam());

        return bedrijf;
    }

    @Override
    public JsonBedrijf mapNaarJson(Bedrijf object) {
        JsonBedrijf json = new JsonBedrijf();

        if (object.getId() != null) {
            json.setId(object.getId().toString());
        }
        json.setKvk(object.getKvk());
        json.setNaam(object.getNaam());
        if (object.getAdres().getHuisnummer() != null) {
            json.setHuisnummer(object.getAdres().getHuisnummer().toString());
        }
        json.setPlaats(object.getAdres().getPlaats());
        json.setPostcode(object.getAdres().getPostcode());
        if (object.getRelatie() != null && object.getRelatie().getId() != null) {
            json.setRelatie(object.getRelatie().getId().toString());
        }
        json.setStraat(object.getAdres().getStraat());
        json.setToevoeging(object.getAdres().getToevoeging());
        if (object.getRelatie() != null) {
            json.setRelatie(object.getRelatie().getId().toString());
        }

        return json;
    }

}
