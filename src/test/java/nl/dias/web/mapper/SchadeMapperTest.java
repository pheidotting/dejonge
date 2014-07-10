package nl.dias.web.mapper;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.ArrayList;
import java.util.List;

import nl.dias.domein.Bedrag;
import nl.dias.domein.Schade;
import nl.dias.domein.SoortSchade;
import nl.dias.domein.StatusSchade;
import nl.dias.domein.json.JsonBijlage;
import nl.dias.domein.json.JsonOpmerking;
import nl.dias.domein.json.JsonSchade;
import nl.dias.domein.polis.AutoVerzekering;

import org.easymock.EasyMock;
import org.easymock.EasyMockSupport;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.junit.Before;
import org.junit.Test;

public class SchadeMapperTest extends EasyMockSupport {
    private SchadeMapper mapper;
    private OpmerkingMapper opmerkingMapper;
    private BijlageMapper bijlageMapper;
    private Schade schade;
    private JsonSchade jsonSchade;

    @Before
    public void setUp() throws Exception {
        mapper = new SchadeMapper();

        opmerkingMapper = createMock(OpmerkingMapper.class);
        mapper.setOpmerkingMapper(opmerkingMapper);

        bijlageMapper = createMock(BijlageMapper.class);
        mapper.setBijlageMapper(bijlageMapper);

        schade = new Schade();
        schade.setDatumAfgehandeld(new LocalDate(2014, 7, 1));
        schade.setDatumTijdMelding(new LocalDateTime(2014, 6, 30, 9, 12));
        schade.setDatumTijdSchade(new LocalDateTime(2014, 6, 29, 10, 23));
        schade.setEigenRisico(new Bedrag(100.0));
        schade.setLocatie("Ergens tussen de weg en de straat");
        schade.setOmschrijving("Tja, toen was het ineens boem!");
        schade.setPolis(new AutoVerzekering());
        schade.setSchadeNummerMaatschappij("schadeNummerMaatschappij");
        schade.setSchadeNummerTussenPersoon("schadeNummerTussenPersoon");
        schade.getBijlages();
        schade.getOpmerkingen();

        SoortSchade soortSchade = new SoortSchade();
        soortSchade.setOmschrijving("Diefstal");
        schade.setSoortSchade(soortSchade);

        StatusSchade statusSchade = new StatusSchade();
        statusSchade.setStatus("statusSchade");
        schade.setStatusSchade(statusSchade);

        jsonSchade = new JsonSchade();
        jsonSchade.setDatumAfgehandeld("01-07-2014");
        jsonSchade.setDatumTijdMelding("30-06-2014 09:12");
        jsonSchade.setDatumTijdSchade("29-06-2014 10:23");
        jsonSchade.setEigenRisico("100.0");
        jsonSchade.setLocatie("Ergens tussen de weg en de straat");
        jsonSchade.setOmschrijving("Tja, toen was het ineens boem!");
        jsonSchade.setPolis(1L);
        jsonSchade.setSchadeNummerMaatschappij("schadeNummerMaatschappij");
        jsonSchade.setSchadeNummerTussenPersoon("schadeNummerTussenPersoon");
        jsonSchade.setSoortSchade("Diefstal");
        jsonSchade.setStatusSchade("statusSchade");
        jsonSchade.getBijlages();
        jsonSchade.getOpmerkingen();

    }

    @Test
    public void testMapVanJsonJsonSchade() {
        assertNull(mapper.mapVanJson(null));
    }

    @Test
    public void testMapNaarJsonSchade() {
        List<JsonBijlage> bijlages = new ArrayList<>();
        List<JsonOpmerking> opmerkingen = new ArrayList<>();

        EasyMock.expect(bijlageMapper.mapAllNaarJson(schade.getBijlages())).andReturn(bijlages);
        EasyMock.expect(opmerkingMapper.mapAllNaarJson(schade.getOpmerkingen())).andReturn(opmerkingen);

        replayAll();

        assertEquals(jsonSchade, mapper.mapNaarJson(schade));

        verifyAll();
    }

}
