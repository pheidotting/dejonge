package nl.dias.web.mapper;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import nl.dias.domein.Bijlage;
import nl.dias.domein.SoortBijlage;
import nl.dias.domein.json.JsonBijlage;
import nl.dias.domein.polis.AutoVerzekering;
import nl.lakedigital.archief.domain.ArchiefBestand;
import nl.lakedigital.archief.service.ArchiefService;

import org.easymock.EasyMock;
import org.easymock.EasyMockSupport;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class BijlageMapperTest extends EasyMockSupport {
    private BijlageMapper mapper;
    private ArchiefService archiefService;

    private Bijlage bijlage;
    private JsonBijlage jsonBijlage;
    private Set<Bijlage> bijlages;
    private List<JsonBijlage> jsonBijlages;

    @Before
    public void setUp() throws Exception {
        mapper = new BijlageMapper();

        archiefService = createMock(ArchiefService.class);
        mapper.setArchiefService(archiefService);

        bijlage = new Bijlage();
        bijlage.setId(1L);
        bijlage.setPolis(new AutoVerzekering());
        bijlage.setS3Identificatie(null);
        bijlage.setSoortBijlage(SoortBijlage.POLIS);

        jsonBijlage = new JsonBijlage();
        jsonBijlage.setBestandsNaam(null);
        jsonBijlage.setId("1");
        jsonBijlage.setSoortBijlage("Polis");
        jsonBijlage.setUrl(null);

        bijlages = new HashSet<Bijlage>();
        jsonBijlages = new ArrayList<JsonBijlage>();

        bijlages.add(bijlage);
        jsonBijlages.add(jsonBijlage);
    }

    @After
    public void tearDown() throws Exception {
        verifyAll();
    }

    @Test
    public void testMapVanJson() {
        replayAll();

        assertEquals(bijlage, mapper.mapVanJson(jsonBijlage));
    }

    @Test
    public void testMapAllVanJson() {
        replayAll();

        assertEquals(bijlages, mapper.mapAllVanJson(jsonBijlages));
    }

    @Test
    public void testMapNaarJson() {
        ArchiefBestand archiefBestand = new ArchiefBestand();

        EasyMock.expect(archiefService.ophalen(null, true)).andReturn(archiefBestand);

        replayAll();

        assertEquals(jsonBijlage, mapper.mapNaarJson(bijlage));
    }

    @Test
    public void testMapAllNaarJson() {
        ArchiefBestand archiefBestand = new ArchiefBestand();

        EasyMock.expect(archiefService.ophalen(null, true)).andReturn(archiefBestand);

        replayAll();

        assertEquals(jsonBijlages, mapper.mapAllNaarJson(bijlages));
    }

}
