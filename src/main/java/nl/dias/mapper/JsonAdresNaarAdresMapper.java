package nl.dias.mapper;

import nl.dias.domein.Adres;
import nl.dias.service.AdresService;
import nl.dias.service.GebruikerService;
import nl.dias.web.SoortEntiteit;
import nl.lakedigital.djfc.commons.json.JsonAdres;
import org.springframework.stereotype.Component;

import javax.inject.Inject;

@Component
public class JsonAdresNaarAdresMapper extends AbstractMapper<JsonAdres, Adres> {
    @Inject
    private GebruikerService gebruikerService;
    @Inject
    private AdresService adresService;

    @Override
    public Adres map(JsonAdres jsonAdres, Object parent, Object bestaandObject) {
        Adres adres = new Adres();
        if (jsonAdres.getId() != null) {
            adres = adresService.lees(jsonAdres.getId());
        }

        adres.setHuisnummer(jsonAdres.getHuisnummer());
        adres.setId(jsonAdres.getId());
        adres.setHuisnummer(jsonAdres.getHuisnummer());
        adres.setPlaats(jsonAdres.getPlaats());
        adres.setPostcode(jsonAdres.getPostcode());
        adres.setStraat(jsonAdres.getStraat());
        adres.setToevoeging(jsonAdres.getToevoeging());
        adres.setSoortAdres(Adres.SoortAdres.valueOf(jsonAdres.getSoortAdres()));

        adres.setEntiteitId(jsonAdres.getEntiteitId());
        adres.setSoortEntiteit(SoortEntiteit.BEDRIJF);

        return adres;
    }

    @Override
    boolean isVoorMij(Object object) {
        return object instanceof JsonAdres;
    }
}
