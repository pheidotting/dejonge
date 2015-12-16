package nl.dias.mapper;

import nl.dias.domein.Adres;
import nl.dias.domein.Relatie;
import nl.dias.domein.json.JsonAdres;
import nl.dias.service.BedrijfService;
import nl.dias.service.GebruikerService;
import org.springframework.stereotype.Component;

import javax.inject.Inject;

@Component
public class JsonAdresNaarAdresMapper extends AbstractMapper<JsonAdres, Adres> {
    @Inject
    private GebruikerService gebruikerService;
    @Inject
    private BedrijfService bedrijfService;

    @Override
    public Adres map(JsonAdres jsonAdres, Object parent) {
        Adres adres = new Adres();

        adres.setHuisnummer(jsonAdres.getHuisnummer());
        adres.setId(jsonAdres.getId());
        adres.setHuisnummer(jsonAdres.getHuisnummer());
        adres.setPlaats(jsonAdres.getPlaats());
        adres.setPostcode(jsonAdres.getPostcode());
        adres.setStraat(jsonAdres.getStraat());
        adres.setToevoeging(jsonAdres.getToevoeging());
        adres.setSoortAdres(Adres.SoortAdres.valueOf(jsonAdres.getSoortAdres()));

        if (jsonAdres.getBedrijf() != null) {
            adres.setBedrijf(bedrijfService.lees(Long.valueOf(jsonAdres.getBedrijf())));
        }
        if (jsonAdres.getRelatie() != null) {
            adres.setRelatie((Relatie) gebruikerService.lees(Long.valueOf(jsonAdres.getRelatie())));
        }

        return adres;
    }

    @Override
    boolean isVoorMij(Object object) {
        return object instanceof JsonAdres;
    }
}