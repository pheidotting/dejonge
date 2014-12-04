package nl.dias.domein;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class AdresTest {

    @Test
    public void isCompleet() {
        Adres adres = new Adres();

        assertFalse(adres.isCompleet());

        adres.setHuisnummer(1L);

        assertFalse(adres.isCompleet());

        adres.setPlaats("plaats");

        assertFalse(adres.isCompleet());

        adres.setPostcode("7894AB");

        assertFalse(adres.isCompleet());

        adres.setStraat("Straat");

        assertTrue(adres.isCompleet());
    }
}