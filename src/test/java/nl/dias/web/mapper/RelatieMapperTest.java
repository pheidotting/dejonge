package nl.dias.web.mapper;

import static org.easymock.EasyMock.expect;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import nl.dias.domein.Bedrijf;
import nl.dias.domein.BurgerlijkeStaat;
import nl.dias.domein.Geslacht;
import nl.dias.domein.Opmerking;
import nl.dias.domein.RekeningNummer;
import nl.dias.domein.Relatie;
import nl.dias.domein.Telefoonnummer;
import nl.dias.domein.json.JsonBedrijf;
import nl.dias.domein.json.JsonBijlage;
import nl.dias.domein.json.JsonOpmerking;
import nl.dias.domein.json.JsonPolis;
import nl.dias.domein.json.JsonRekeningNummer;
import nl.dias.domein.json.JsonRelatie;
import nl.dias.domein.json.JsonTelefoonnummer;
import nl.dias.domein.polis.Polis;

import org.easymock.EasyMockSupport;
import org.joda.time.LocalDate;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class RelatieMapperTest extends EasyMockSupport {
    private RelatieMapper mapper;

    private TelefoonnummerMapper telefoonnummerMapper;
    private RekeningnummerMapper rekeningnummerMapper;
    private OpmerkingMapper opmerkingMapper;
    private PolisMapper polisMapper;
    private BedrijfMapper bedrijfMapper;
    private BijlageMapper bijlageMapper;

    private Relatie relatie;
    private JsonRelatie jsonRelatie;
    private Set<Relatie> relaties;
    private List<JsonRelatie> jsonRelaties;

    @Before
    public void setUp() throws Exception {
        mapper = new RelatieMapper();

        telefoonnummerMapper = createMock(TelefoonnummerMapper.class);
        rekeningnummerMapper = createMock(RekeningnummerMapper.class);
        opmerkingMapper = createMock(OpmerkingMapper.class);
        polisMapper = createMock(PolisMapper.class);
        bedrijfMapper = createMock(BedrijfMapper.class);
        bijlageMapper = createMock(BijlageMapper.class);

        mapper.setBedrijfMapper(bedrijfMapper);
        mapper.setBijlageMapper(bijlageMapper);
        mapper.setOpmerkingMapper(opmerkingMapper);
        mapper.setPolisMapper(polisMapper);
        mapper.setRekeningnummerMapper(rekeningnummerMapper);
        mapper.setTelefoonnummerMapper(telefoonnummerMapper);

        relatie = new Relatie();
        relatie.setAchternaam("achternaam");
        relatie.getAdres().setHuisnummer(41L);
        relatie.setGeslacht(Geslacht.M);
        relatie.setBurgerlijkeStaat(BurgerlijkeStaat.O);
        relatie.setGeboorteDatum(new LocalDate(2014, 2, 3));
        relatie.setOverlijdensdatum(new LocalDate(2014, 6, 7));
        relatie.setTelefoonnummers(new HashSet<Telefoonnummer>());
        relatie.setPolissen(new HashSet<Polis>());
        relatie.setRekeningnummers(new HashSet<RekeningNummer>());
        relatie.setOpmerkingen(new HashSet<Opmerking>());
        relatie.setBedrijven(new HashSet<Bedrijf>());

        relaties = new HashSet<Relatie>();
        relaties.add(relatie);

        jsonRelatie = new JsonRelatie();
        jsonRelatie.setAchternaam("achternaam");
        jsonRelatie.setHuisnummer("41");
        jsonRelatie.setGeslacht("Man");
        jsonRelatie.setBurgerlijkeStaat("Ongehuwd");
        jsonRelatie.setGeboorteDatum("03-03-2014");
        jsonRelatie.setOverlijdensdatum("07-06-2014");
        jsonRelatie.setTelefoonnummers(new ArrayList<JsonTelefoonnummer>());
        jsonRelatie.setPolissen(new ArrayList<JsonPolis>());
        jsonRelatie.setRekeningnummers(new ArrayList<JsonRekeningNummer>());
        jsonRelatie.setOpmerkingen(new ArrayList<JsonOpmerking>());
        jsonRelatie.setBedrijven(new ArrayList<JsonBedrijf>());
        jsonRelatie.setLijstBijlages(new ArrayList<JsonBijlage>());

        jsonRelaties = new ArrayList<JsonRelatie>();
        jsonRelaties.add(jsonRelatie);
    }

    @After
    public void tearDown() throws Exception {
        verifyAll();
    }

    @Test
    public void testMapVanJson() {
        replayAll();

        assertNull(mapper.mapVanJson(jsonRelatie));
    }

    @Test
    public void testMapAllVanJson() {
        replayAll();

        assertNull(mapper.mapAllVanJson(jsonRelaties));
    }

    @Test
    public void testMapAllNaarJson() {
        expect(telefoonnummerMapper.mapAllNaarJson(new HashSet<Telefoonnummer>())).andReturn(new ArrayList<JsonTelefoonnummer>());
        expect(rekeningnummerMapper.mapAllNaarJson(new HashSet<RekeningNummer>())).andReturn(new ArrayList<JsonRekeningNummer>());
        expect(opmerkingMapper.mapAllNaarJson(new HashSet<Opmerking>())).andReturn(new ArrayList<JsonOpmerking>());
        expect(bedrijfMapper.mapAllNaarJson(new HashSet<Bedrijf>())).andReturn(new ArrayList<JsonBedrijf>());
        expect(polisMapper.mapAllNaarJson(new HashSet<Polis>())).andReturn(new ArrayList<JsonPolis>());

        replayAll();

        assertEquals(jsonRelaties, mapper.mapAllNaarJson(relaties));
    }

    @Test
    public void testMapNaarJson() {
        expect(telefoonnummerMapper.mapAllNaarJson(new HashSet<Telefoonnummer>())).andReturn(new ArrayList<JsonTelefoonnummer>());
        expect(rekeningnummerMapper.mapAllNaarJson(new HashSet<RekeningNummer>())).andReturn(new ArrayList<JsonRekeningNummer>());
        expect(opmerkingMapper.mapAllNaarJson(new HashSet<Opmerking>())).andReturn(new ArrayList<JsonOpmerking>());
        expect(bedrijfMapper.mapAllNaarJson(new HashSet<Bedrijf>())).andReturn(new ArrayList<JsonBedrijf>());
        expect(polisMapper.mapAllNaarJson(new HashSet<Polis>())).andReturn(new ArrayList<JsonPolis>());

        replayAll();

        assertEquals(jsonRelatie, mapper.mapNaarJson(relatie));
    }

}
