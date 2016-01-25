package nl.dias.mapper;

import nl.dias.domein.Adres;
import nl.lakedigital.djfc.commons.json.JsonAdres;
import org.springframework.stereotype.Component;

@Component
public class AdresNaarJsonAdresMapper extends AbstractMapper<Adres, JsonAdres> {
    @Override
    public JsonAdres map(Adres adres, Object parent, Object bestaandObject) {
        JsonAdres jsonAdres = new JsonAdres();

        jsonAdres.setHuisnummer(adres.getHuisnummer());
        jsonAdres.setId(adres.getId());
        jsonAdres.setHuisnummer(adres.getHuisnummer());
        jsonAdres.setPlaats(adres.getPlaats());
        jsonAdres.setPostcode(adres.getPostcode());
        jsonAdres.setStraat(adres.getStraat());
        jsonAdres.setToevoeging(adres.getToevoeging());
        jsonAdres.setSoortAdres(adres.getSoortAdres().name());

        return jsonAdres;

    }

    @Override
    boolean isVoorMij(Object object) {
        return object instanceof Adres;
    }
}
