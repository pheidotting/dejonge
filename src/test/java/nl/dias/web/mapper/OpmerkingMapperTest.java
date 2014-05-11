package nl.dias.web.mapper;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import nl.dias.domein.Opmerking;
import nl.dias.domein.json.JsonOpmerking;

import org.joda.time.LocalDateTime;
import org.junit.Before;
import org.junit.Test;

public class OpmerkingMapperTest {
    private OpmerkingMapper mapper;

    @Before
    public void setUp() throws Exception {
        mapper = new OpmerkingMapper();
    }

    @Test
    public void testMapAllVanJson() {
        List<JsonOpmerking> lijst = new ArrayList<>();
        lijst.add(maakJsonOpmerking());

        Set<Opmerking> verwacht = new HashSet<>();
        verwacht.add(maakOpmerking());

        assertEquals(verwacht, mapper.mapAllVanJson(lijst));
    }

    @Test
    public void testMapAllNaarJson() {
        Set<Opmerking> lijst = new HashSet<>();
        lijst.add(maakOpmerking());

        List<JsonOpmerking> verwacht = new ArrayList<>();
        verwacht.add(maakJsonOpmerking());

        assertEquals(verwacht, mapper.mapAllNaarJson(lijst));
    }

    private Opmerking maakOpmerking() {
        Opmerking opmerking = new Opmerking();
        opmerking.setId(1L);
        opmerking.setOpmerking("opmerking");
        opmerking.setTijd(new LocalDateTime(2014, 5, 3, 7, 5));

        return opmerking;
    }

    private JsonOpmerking maakJsonOpmerking() {
        JsonOpmerking jsonOpmerking = new JsonOpmerking();

        jsonOpmerking.setId(1L);
        jsonOpmerking.setOpmerking("opmerking");
        jsonOpmerking.setTijd(new LocalDateTime(2014, 5, 3, 7, 5));

        return jsonOpmerking;
    }

}
