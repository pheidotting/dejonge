package nl.dias.mapper;

import nl.dias.domein.Adres;
import nl.dias.domein.Relatie;
import nl.dias.domein.json.JsonAdres;
import org.junit.Test;

import static org.junit.Assert.*;

public class AdresNaarJsonAdresMapperTest {

    private AdresNaarJsonAdresMapper mapper = new AdresNaarJsonAdresMapper();

    @Test
    public void testMap() throws Exception {
        JsonAdres jsonAdres = new JsonAdres();
        jsonAdres.setSoortAdres("WOONADRES");
        jsonAdres.setToevoeging("toevoeging");
        jsonAdres.setStraat("straat");
        jsonAdres.setPostcode("postcode");
        jsonAdres.setHuisnummer(45L);
        jsonAdres.setId(3L);
        jsonAdres.setPlaats("Plaats");

        Adres adres = new Adres();
        adres.setSoortAdres(Adres.SoortAdres.WOONADRES);
        adres.setToevoeging("toevoeging");
        adres.setStraat("straat");
        adres.setPostcode("postcode");
        adres.setHuisnummer(45L);
        adres.setId(3L);
        adres.setPlaats("Plaats");

        assertEquals(jsonAdres, mapper.map(adres));
    }

    @Test
    public void testIsVoorMij() throws Exception {
        assertTrue(mapper.isVoorMij(new Adres()));
        assertFalse(mapper.isVoorMij(new Relatie()));
    }
}