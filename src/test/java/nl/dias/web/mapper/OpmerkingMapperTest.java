package nl.dias.web.mapper;

import nl.dias.domein.Medewerker;
import nl.dias.domein.Opmerking;
import nl.dias.domein.json.JsonOpmerking;
import nl.dias.service.SchadeService;
import org.easymock.EasyMockSupport;
import org.joda.time.LocalDateTime;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

@Ignore
public class OpmerkingMapperTest extends EasyMockSupport {
    private OpmerkingMapper mapper;
    private SchadeService schadeService;

    @Before
    public void setUp() throws Exception {
        mapper = new OpmerkingMapper();

        schadeService = createMock(SchadeService.class);
        mapper.setSchadeService(schadeService);
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
    public void testMapAllVanJsonNull() {
        assertNull(mapper.mapAllVanJson(null));
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

        Medewerker medewerker = new Medewerker();
        medewerker.setVoornaam("voornaam");
        medewerker.setAchternaam("achternaam");

        opmerking.setMedewerker(medewerker);

        return opmerking;
    }

    private JsonOpmerking maakJsonOpmerking() {
        JsonOpmerking jsonOpmerking = new JsonOpmerking();

        jsonOpmerking.setId(1L);
        jsonOpmerking.setOpmerking("opmerking");
        jsonOpmerking.setTijd("03-05-2014 07:05");
        jsonOpmerking.setMedewerker("voornaam achternaam");
        jsonOpmerking.setSchade("2");

        return jsonOpmerking;
    }

}
