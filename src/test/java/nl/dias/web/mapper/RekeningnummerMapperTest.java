package nl.dias.web.mapper;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import nl.dias.domein.RekeningNummer;
import nl.dias.domein.json.JsonRekeningNummer;

import org.junit.Before;
import org.junit.Test;

public class RekeningnummerMapperTest {
    private RekeningnummerMapper mapper;

    @Before
    public void setUp() throws Exception {
        mapper = new RekeningnummerMapper();
    }

    @Test
    public void testMapAllVanJson() {
        List<JsonRekeningNummer> lijst = new ArrayList<>();
        lijst.add(maakJsonRekeningNummer());

        Set<RekeningNummer> verwacht = new HashSet<>();
        verwacht.add(maakRekeningNummer());

        Set<RekeningNummer> terug = mapper.mapAllVanJson(lijst);
        assertEquals(verwacht, terug);

    }

    @Test
    public void testMapAllNaarJson() {
        Set<RekeningNummer> lijst = new HashSet<>();
        lijst.add(maakRekeningNummer());

        List<JsonRekeningNummer> verwacht = new ArrayList<>();
        verwacht.add(maakJsonRekeningNummer());

        List<JsonRekeningNummer> terug = mapper.mapAllNaarJson(lijst);
        assertEquals(verwacht, terug);
    }

    private RekeningNummer maakRekeningNummer() {
        RekeningNummer rekeningNummer = new RekeningNummer();
        rekeningNummer.setBic("bic");
        rekeningNummer.setId(1L);
        rekeningNummer.setRekeningnummer("rekeningnummer");

        return rekeningNummer;
    }

    private JsonRekeningNummer maakJsonRekeningNummer() {
        JsonRekeningNummer jsonRekeningNummer = new JsonRekeningNummer();
        jsonRekeningNummer.setBic("bic");
        jsonRekeningNummer.setId(1L);
        jsonRekeningNummer.setRekeningnummer("rekeningnummer");

        return jsonRekeningNummer;
    }
}
