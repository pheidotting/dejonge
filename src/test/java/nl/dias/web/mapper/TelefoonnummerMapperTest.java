package nl.dias.web.mapper;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import nl.dias.domein.Telefoonnummer;
import nl.dias.domein.TelefoonnummerSoort;
import nl.dias.domein.json.JsonTelefoonnummer;

import org.junit.Before;
import org.junit.Test;

public class TelefoonnummerMapperTest {
    private TelefoonnummerMapper mapper;

    @Before
    public void setUp() throws Exception {
        mapper = new TelefoonnummerMapper();
    }

    @Test
    public void testMapVanJson() {
        List<JsonTelefoonnummer> lijst = new ArrayList<>();

        lijst.add(maakJsonTelefoonnummer());

        Set<Telefoonnummer> verwacht = new HashSet<>();
        verwacht.add(maakTelefoonnummer());

        Set<Telefoonnummer> terug = mapper.mapAllVanJson(lijst);
        assertEquals(1, terug.size());

        Telefoonnummer telefoonnummer = terug.iterator().next();
        assertEquals(maakTelefoonnummer(), telefoonnummer);
    }

    @Test
    public void testMapNaarJson() {
        Set<Telefoonnummer> lijst = new HashSet<>();
        lijst.add(maakTelefoonnummer());

        List<JsonTelefoonnummer> verwacht = new ArrayList<>();
        verwacht.add(maakJsonTelefoonnummer());

        List<JsonTelefoonnummer> terug = mapper.mapAllNaarJson(lijst);
        assertEquals(1, terug.size());

        JsonTelefoonnummer jsonTelefoonnummer = terug.get(0);
        assertEquals(maakJsonTelefoonnummer(), jsonTelefoonnummer);
    }

    private Telefoonnummer maakTelefoonnummer() {
        Telefoonnummer telefoonnummer = new Telefoonnummer();

        telefoonnummer.setId(46L);
        telefoonnummer.setTelefoonnummer("0123456789");
        telefoonnummer.setSoort(TelefoonnummerSoort.MOBIEL);

        return telefoonnummer;
    }

    private JsonTelefoonnummer maakJsonTelefoonnummer() {
        JsonTelefoonnummer telefoonnummer = new JsonTelefoonnummer();

        telefoonnummer.setId(46L);
        telefoonnummer.setTelefoonnummer("0123456789");
        telefoonnummer.setSoort("MOBIEL");

        return telefoonnummer;
    }
}
