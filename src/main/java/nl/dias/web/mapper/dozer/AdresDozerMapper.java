package nl.dias.web.mapper.dozer;

import nl.dias.domein.Adres;
import nl.dias.domein.Bedrijf;
import nl.dias.domein.Relatie;
import nl.dias.domein.json.JsonBedrijf;
import org.dozer.DozerBeanMapper;
import org.dozer.DozerConverter;

import static com.google.common.collect.Iterables.getFirst;

public class AdresDozerMapper extends DozerConverter<Bedrijf, JsonBedrijf> {

    public AdresDozerMapper() {
        super(Bedrijf.class, JsonBedrijf.class);
    }

    @Override
    public JsonBedrijf convertTo(Bedrijf bedrijf, JsonBedrijf jsonBedrijf) {
        DozerBeanMapper mapper = new DozerBeanMapper();

        JsonBedrijf result = mapper.map(bedrijf, JsonBedrijf.class);

        Adres adres = getFirst(bedrijf.getAdressen(), new Adres());
        result.setToevoeging(adres.getToevoeging());
        result.setHuisnummer(adres.getHuisnummer().toString());
        result.setPlaats(adres.getPlaats());
        result.setPostcode(adres.getPostcode());
        result.setStraat(adres.getStraat());

        result.setRelatie(bedrijf.getRelatie().getId().toString());

        return result;
    }

    @Override
    public Bedrijf convertFrom(JsonBedrijf jsonBedrijf, Bedrijf bedrijf) {
        DozerBeanMapper mapper = new DozerBeanMapper();

        Relatie relatie = new Relatie();
        relatie.setId(Long.valueOf(jsonBedrijf.getRelatie()));

        Bedrijf result = new Bedrijf();
        result.setKvk(jsonBedrijf.getKvk());
        result.setNaam(jsonBedrijf.getNaam());
        result.setRelatie(relatie);
        result.setId(Long.valueOf(jsonBedrijf.getId()));

        Adres adres = new Adres();
        adres.setToevoeging(jsonBedrijf.getToevoeging());
        adres.setHuisnummer(Long.valueOf(jsonBedrijf.getHuisnummer()));
        adres.setPlaats(jsonBedrijf.getPlaats());
        adres.setPostcode(jsonBedrijf.getPostcode());
        adres.setStraat(jsonBedrijf.getStraat());
        result.getAdressen().add(adres);

        return result;
    }
}