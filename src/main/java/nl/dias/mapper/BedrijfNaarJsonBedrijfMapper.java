package nl.dias.mapper;

import nl.dias.domein.Adres;
import nl.dias.domein.Bedrijf;
import nl.dias.domein.json.JsonBedrijf;
import org.springframework.stereotype.Component;

import javax.inject.Inject;

@Component
public class BedrijfNaarJsonBedrijfMapper extends AbstractMapper<Bedrijf, JsonBedrijf> {
    @Inject
    private AdresNaarJsonAdresMapper adresNaarJsonAdresMapper;

    @Override
    public JsonBedrijf map(Bedrijf bedrijf) {
        JsonBedrijf jsonBedrijf = new JsonBedrijf();

        jsonBedrijf.setId(bedrijf.getId().toString());
        jsonBedrijf.setKvk(bedrijf.getKvk());
        jsonBedrijf.setNaam(bedrijf.getNaam());

        for (Adres adres : bedrijf.getAdressen()) {
            jsonBedrijf.getAdressen().add(adresNaarJsonAdresMapper.map(adres));
        }

        return jsonBedrijf;
    }

    @Override
    boolean isVoorMij(Object object) {
        return object instanceof Bedrijf;
    }
}
