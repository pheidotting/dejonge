package nl.dias.web.mapper.dozer;

import nl.dias.domein.Bedrijf;
import nl.lakedigital.djfc.commons.json.JsonBedrijf;
import org.dozer.DozerBeanMapper;
import org.dozer.DozerConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AdresDozerMapper extends DozerConverter<Bedrijf, JsonBedrijf> {
    private static final Logger LOGGER = LoggerFactory.getLogger(AdresDozerMapper.class);


    public AdresDozerMapper() {
        super(Bedrijf.class, JsonBedrijf.class);
    }

    @Override
    public JsonBedrijf convertTo(Bedrijf bedrijf, JsonBedrijf jsonBedrijf) {
        DozerBeanMapper mapper = new DozerBeanMapper();

        JsonBedrijf result = mapper.map(bedrijf, JsonBedrijf.class);

        //        Adres adres = getFirst(bedrijf.getAdressen(), new Adres());
        //        result.setToevoeging(adres.getToevoeging());
        //        if (adres.getHuisnummer() != null) {
        //            result.setHuisnummer(adres.getHuisnummer().toString());
        //        }
        //        result.setPlaats(adres.getPlaats());
        //        result.setPostcode(adres.getPostcode());
        //        result.setStraat(adres.getStraat());
        //
        //        if (bedrijf != null && bedrijf.getRelatie() != null && bedrijf.getRelatie().getId() != null) {
        //            result.setRelatie(bedrijf.getRelatie().getId().toString());
        //        }

        return result;
    }

    @Override
    public Bedrijf convertFrom(JsonBedrijf jsonBedrijf, Bedrijf bedrijf) {
        DozerBeanMapper mapper = new DozerBeanMapper();

        Bedrijf result = new Bedrijf();
        result.setKvk(jsonBedrijf.getKvk());
        result.setNaam(jsonBedrijf.getNaam());
        if (jsonBedrijf != null && jsonBedrijf.getId() != null) {
            result.setId(Long.valueOf(jsonBedrijf.getId()));
        }

        //        Adres adres = new Adres();
        //        adres.setToevoeging(jsonBedrijf.getToevoeging());
        //        adres.setHuisnummer(Long.valueOf(jsonBedrijf.getHuisnummer()));
        //        adres.setPlaats(jsonBedrijf.getPlaats());
        //        adres.setPostcode(jsonBedrijf.getPostcode());
        //        adres.setStraat(jsonBedrijf.getStraat());
        //        result.getAdressen().add(adres);

        return result;
    }
}
