package nl.dias.web.mapper;

import nl.dias.domein.Adres;
import nl.dias.domein.Relatie;
import nl.dias.domein.json.JsonAdres;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class AdresMapperTest {

    private Adres adres;
    private JsonAdres jsonAdres;
    private AdresMapper adresMapper;

    @Before
    public void setUp() throws Exception {
        adres = new Adres();

        Relatie relatie = new Relatie();
        relatie.setId(3L);

        adres.setRelatie(relatie);
        adres.setId(2L);
        adres.setHuisnummer(4L);
        adres.setPlaats("plaats");
        adres.setPostcode("postcode");
        adres.setSoortAdres(Adres.SoortAdres.POSTADRES);
        adres.setStraat("straat");
        adres.setToevoeging("toevoeging");

        jsonAdres = new JsonAdres();
        jsonAdres.setRelatie("3");
        jsonAdres.setId(2L);
        jsonAdres.setHuisnummer(4L);
        jsonAdres.setPlaats("plaats");
        jsonAdres.setPostcode("postcode");
        jsonAdres.setSoortAdres("POSTADRES");
        jsonAdres.setStraat("straat");
        jsonAdres.setToevoeging("toevoeging");

        adresMapper = new AdresMapper();
    }

    @Test
    public void testMapVanJson() throws Exception {
        assertEquals(adres, adresMapper.mapVanJson(jsonAdres));
    }

    @Test
    public void testMapNaarJson() throws Exception {
        assertEquals(jsonAdres, adresMapper.mapNaarJson(adres));
    }
}