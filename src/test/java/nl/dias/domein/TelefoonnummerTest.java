package nl.dias.domein;

import static org.junit.Assert.fail;
import nl.dias.exception.TelefoonnummerNietGoedException;

import org.junit.Test;

public class TelefoonnummerTest {

    @Test
    public void validateTeLang() {
        Telefoonnummer telefoonnummer = new Telefoonnummer();
        telefoonnummer.setSoort(TelefoonnummerSoort.MOBIEL);
        telefoonnummer.setTelefoonnummer("11111111111");

        try {
            telefoonnummer.validate();
            fail("exception verwacht");
        } catch (TelefoonnummerNietGoedException e) {
            // correct
        }
    }

    @Test
    public void validateTeKort() {
        Telefoonnummer telefoonnummer = new Telefoonnummer();
        telefoonnummer.setSoort(TelefoonnummerSoort.MOBIEL);
        telefoonnummer.setTelefoonnummer("111111111");

        try {
            telefoonnummer.validate();
            fail("exception verwacht");
        } catch (TelefoonnummerNietGoedException e) {
            // correct
        }
    }

    @Test
    public void validateAlfanumeriek() {
        Telefoonnummer telefoonnummer = new Telefoonnummer();
        telefoonnummer.setSoort(TelefoonnummerSoort.MOBIEL);
        telefoonnummer.setTelefoonnummer("11111a1111");

        try {
            telefoonnummer.validate();
            fail("exception verwacht");
        } catch (TelefoonnummerNietGoedException e) {
            // correct
        }
    }

    @Test
    public void validateLegeSoort() {
        Telefoonnummer telefoonnummer = new Telefoonnummer();
        telefoonnummer.setTelefoonnummer("1111111111");

        try {
            telefoonnummer.validate();
            fail("exception verwacht");
        } catch (TelefoonnummerNietGoedException e) {
        }
    }

    @Test
    public void validate() {
        Telefoonnummer telefoonnummer = new Telefoonnummer();
        telefoonnummer.setSoort(TelefoonnummerSoort.MOBIEL);
        telefoonnummer.setTelefoonnummer("1111111111");

        try {
            telefoonnummer.validate();
        } catch (TelefoonnummerNietGoedException e) {
            fail("geen exception verwacht");
        }
    }

}
