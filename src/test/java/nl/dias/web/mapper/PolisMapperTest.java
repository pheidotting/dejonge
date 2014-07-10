package nl.dias.web.mapper;

import static org.easymock.EasyMock.expect;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import nl.dias.domein.Bedrag;
import nl.dias.domein.Bedrijf;
import nl.dias.domein.VerzekeringsMaatschappij;
import nl.dias.domein.json.JsonBijlage;
import nl.dias.domein.json.JsonOpmerking;
import nl.dias.domein.json.JsonPolis;
import nl.dias.domein.json.JsonSchade;
import nl.dias.domein.polis.Betaalfrequentie;
import nl.dias.domein.polis.FietsVerzekering;
import nl.dias.domein.polis.Polis;

import org.easymock.EasyMockSupport;
import org.joda.time.LocalDate;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class PolisMapperTest extends EasyMockSupport {
    private PolisMapper mapper;
    private OpmerkingMapper opmerkingMapper;
    private BijlageMapper bijlageMapper;
    private SchadeMapper schadeMapper;

    private Polis polis;
    private JsonPolis jsonPolis;
    private Set<Polis> polissen;
    private List<JsonPolis> jsonPolissen;

    @Before
    public void setUp() throws Exception {
        mapper = new PolisMapper();

        opmerkingMapper = createMock(OpmerkingMapper.class);
        mapper.setOpmerkingMapper(opmerkingMapper);

        bijlageMapper = createMock(BijlageMapper.class);
        mapper.setBijlageMapper(bijlageMapper);

        schadeMapper = createMock(SchadeMapper.class);
        mapper.setSchadeMapper(schadeMapper);

        VerzekeringsMaatschappij maatschappij = new VerzekeringsMaatschappij();
        maatschappij.setNaam("Fa. List & Bedrog");

        Bedrijf bedrijf = new Bedrijf();
        bedrijf.setNaam("TestBedrijf");

        polis = new FietsVerzekering();
        polis.setPremie(new Bedrag("12345"));
        polis.setMaatschappij(maatschappij);
        polis.setIngangsDatum(new LocalDate(2014, 6, 23));
        polis.setWijzigingsDatum(new LocalDate(2014, 6, 23));
        polis.setProlongatieDatum(new LocalDate(2014, 6, 23));
        polis.setBetaalfrequentie(Betaalfrequentie.H);
        polis.getBijlages();
        polis.setBedrijf(bedrijf);
        polis.getSchades();

        jsonPolis = new JsonPolis();
        jsonPolis.setPremie("12345.00 euro");
        jsonPolis.setMaatschappij("Fa. List & Bedrog");
        jsonPolis.setIngangsDatum("2014-06-23");
        jsonPolis.setWijzigingsDatum("2014-06-23");
        jsonPolis.setProlongatieDatum("2014-06-23");
        jsonPolis.setSoort("FietsVerzekering");
        jsonPolis.setBetaalfrequentie("Half jaar");
        jsonPolis.getBijlages();
        jsonPolis.getOpmerkingen();
        jsonPolis.setBedrijf("TestBedrijf");
        jsonPolis.getSchades();

        polissen = new HashSet<Polis>();
        polissen.add(polis);
        jsonPolissen = new ArrayList<JsonPolis>();
        jsonPolissen.add(jsonPolis);
    }

    @After
    public void verify() {
        verifyAll();
    }

    @Test
    public void testMapVanJson() {
        replayAll();

        assertNull(mapper.mapVanJson(jsonPolis));
    }

    @Test
    public void testMapNaarJson() {
        List<JsonBijlage> bijlages = new ArrayList<JsonBijlage>();
        List<JsonOpmerking> opmerkingen = new ArrayList<JsonOpmerking>();
        List<JsonSchade> schades = new ArrayList<JsonSchade>();

        expect(bijlageMapper.mapAllNaarJson(polis.getBijlages())).andReturn(bijlages);
        expect(opmerkingMapper.mapAllNaarJson(polis.getOpmerkingen())).andReturn(opmerkingen);
        expect(schadeMapper.mapAllNaarJson(polis.getSchades())).andReturn(schades);

        replayAll();

        assertEquals(jsonPolis, mapper.mapNaarJson(polis));
    }
}
