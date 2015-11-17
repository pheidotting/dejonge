package nl.dias.mapper;

import nl.dias.domein.Adres;
import nl.dias.domein.Bedrijf;
import nl.dias.domein.Bijlage;
import nl.dias.domein.Opmerking;
import nl.dias.domein.json.JsonBedrijf;
import org.springframework.stereotype.Component;

import javax.inject.Inject;

@Component
public class BedrijfNaarJsonBedrijfMapper extends AbstractMapper<Bedrijf, JsonBedrijf> {
    @Inject
    private AdresNaarJsonAdresMapper adresNaarJsonAdresMapper;
    @Inject
    private BijlageNaarJsonBijlageMapper bijlageNaarJsonBijlageMapper;
    @Inject
    private OpmerkingNaarJsonOpmerkingMapper opmerkingNaarJsonOpmerkingMapper;

    @Override
    public JsonBedrijf map(Bedrijf bedrijf) {
        JsonBedrijf jsonBedrijf = new JsonBedrijf();

        jsonBedrijf.setId(bedrijf.getId().toString());
        jsonBedrijf.setKvk(bedrijf.getKvk());
        jsonBedrijf.setNaam(bedrijf.getNaam());

        if(bedrijf.getAdressen().size()>0){
            Adres adres=bedrijf.getAdressen().iterator().next();

            jsonBedrijf.setPlaats(adres.getPlaats());
            jsonBedrijf.setHuisnummer(adres.getHuisnummer().toString());
            jsonBedrijf.setToevoeging(adres.getToevoeging());
            jsonBedrijf.setStraat(adres.getStraat());
            jsonBedrijf.setPostcode(adres.getPostcode());
        }

        for (Adres adres : bedrijf.getAdressen()) {
            jsonBedrijf.getAdressen().add(adresNaarJsonAdresMapper.map(adres));
        }

        for (Bijlage bijlage : bedrijf.getBijlages()) {
            jsonBedrijf.getBijlages().add(bijlageNaarJsonBijlageMapper.map(bijlage));
        }

        for (Opmerking opmerking : bedrijf.getOpmerkingen()) {
            jsonBedrijf.getOpmerkingen().add(opmerkingNaarJsonOpmerkingMapper.map(opmerking));
        }

        return jsonBedrijf;
    }

    @Override
    boolean isVoorMij(Object object) {
        return object instanceof Bedrijf;
    }
}
