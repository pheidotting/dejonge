package nl.dias.service;

import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import javax.persistence.NoResultException;

import nl.dias.domein.Bedrag;
import nl.dias.domein.Bijlage;
import nl.dias.domein.Kantoor;
import nl.dias.domein.Relatie;
import nl.dias.domein.SoortBijlage;
import nl.dias.domein.VerzekeringsMaatschappij;
import nl.dias.domein.json.JsonPolis;
import nl.dias.domein.polis.AutoVerzekering;
import nl.dias.domein.polis.Betaalfrequentie;
import nl.dias.domein.polis.CamperVerzekering;
import nl.dias.domein.polis.FietsVerzekering;
import nl.dias.domein.polis.MobieleApparatuurVerzekering;
import nl.dias.domein.polis.Polis;
import nl.dias.repository.KantoorRepository;
import nl.dias.repository.PolisRepository;
import nl.lakedigital.archief.service.ArchiefService;

import org.easymock.EasyMockSupport;
import org.joda.time.LocalDate;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class PolisServiceTest extends EasyMockSupport {
    private PolisService polisService;

    private PolisRepository polisRepository;
    private ArchiefService archiefService;
    private GebruikerService gebruikerService;
    private KantoorRepository kantoorRepository;
    private BedrijfService bedrijfService;
    private VerzekeringsMaatschappijService verzekeringsMaatschappijService;

    @Before
    public void setUp() throws Exception {
        polisService = new PolisService();

        polisRepository = createMock(PolisRepository.class);
        polisService.setPolisRepository(polisRepository);

        archiefService = createMock(ArchiefService.class);
        polisService.setArchiefService(archiefService);

        gebruikerService = createMock(GebruikerService.class);
        polisService.setGebruikerService(gebruikerService);

        kantoorRepository = createMock(KantoorRepository.class);
        polisService.setKantoorRepository(kantoorRepository);

        bedrijfService = createMock(BedrijfService.class);
        polisService.setBedrijfService(bedrijfService);

        verzekeringsMaatschappijService = createMock(VerzekeringsMaatschappijService.class);
        polisService.setVerzekeringsMaatschappijService(verzekeringsMaatschappijService);
    }

    @After
    public void after() {
        verifyAll();
    }

    @Test
    public void testAllePolissenVanRelatieEnZijnBedrijf() {
        Relatie relatie = new Relatie();
        List<Polis> polissen = new ArrayList<Polis>();

        expect(polisRepository.allePolissenVanRelatieEnZijnBedrijf(relatie)).andReturn(polissen);

        replayAll();

        assertEquals(polissen, polisService.allePolissenVanRelatieEnZijnBedrijf(relatie));
    }

    @Test
    public void testOpslaanPolis() {
        AutoVerzekering polis = new AutoVerzekering();

        polisRepository.opslaan(polis);
        expectLastCall();

        replayAll();

        polisService.opslaan(polis);
    }

    @Test
    public void testZoekOpPolisNummer() {
        CamperVerzekering camperVerzekering = new CamperVerzekering();
        Kantoor kantoor = new Kantoor();

        expect(kantoorRepository.lees(1L)).andReturn(kantoor);
        expect(polisRepository.zoekOpPolisNummer("1234", kantoor)).andReturn(camperVerzekering);

        replayAll();

        assertEquals(camperVerzekering, polisService.zoekOpPolisNummer("1234"));
    }

    @Test
    public void testZoekOpPolisNummerMetException() {
        Kantoor kantoor = new Kantoor();

        expect(kantoorRepository.lees(1L)).andReturn(kantoor);
        expect(polisRepository.zoekOpPolisNummer("1234", kantoor)).andThrow(new NoResultException());

        replayAll();

        assertNull(polisService.zoekOpPolisNummer("1234"));
    }

    @Test
    public void testSlaBijlageOp() {
        FietsVerzekering fietsVerzekering = new FietsVerzekering();

        Bijlage bijlage = new Bijlage();
        bijlage.setPolis(fietsVerzekering);
        bijlage.setSoortBijlage(SoortBijlage.POLIS);
        bijlage.setS3Identificatie("s3Identificatie");

        expect(polisRepository.lees(1L)).andReturn(fietsVerzekering);

        polisRepository.opslaanBijlage(bijlage);
        expectLastCall();

        replayAll();

        polisService.slaBijlageOp(1L, "s3Identificatie");
    }

    @Test
    public void testLeesBijlage() {
        Bijlage bijlage = new Bijlage();

        expect(polisRepository.leesBijlage(1L)).andReturn(bijlage);

        replayAll();

        assertEquals(bijlage, polisService.leesBijlage(1L));
    }

    @Test
    public void testVerwijder() {
        archiefService.setBucketName("dias");
        expectLastCall();

        MobieleApparatuurVerzekering verzekering = new MobieleApparatuurVerzekering();
        expect(polisRepository.lees(1L)).andReturn(verzekering);

        Relatie relatie = new Relatie();
        relatie.setId(2L);
        verzekering.setRelatie(relatie);
        relatie.getPolissen().add(verzekering);
        expect(gebruikerService.lees(2L)).andReturn(relatie);

        relatie.setPolissen(new HashSet<Polis>());

        gebruikerService.opslaan(relatie);
        expectLastCall();

        polisRepository.verwijder(verzekering);
        expectLastCall();

        replayAll();

        polisService.verwijder(1L);

    }

    @Test
    public void testOpslaanOpslaanPolis() {
        JsonPolis opslaanPolis = new JsonPolis();
        opslaanPolis.setSoort("Auto");
        opslaanPolis.setPolisNummer("1234");
        opslaanPolis.setMaatschappij("maatschappij");
        opslaanPolis.setRelatie("46");
        opslaanPolis.setIngangsDatum("01-02-2014");
        opslaanPolis.setProlongatieDatum("02-03-2014");
        opslaanPolis.setWijzigingsDatum("03-04-2014");
        opslaanPolis.setBetaalfrequentie("jaar");
        opslaanPolis.setPremie("12");

        Kantoor kantoor = new Kantoor();
        expect(kantoorRepository.lees(1L)).andReturn(kantoor);

        expect(polisRepository.zoekOpPolisNummer("1234", kantoor)).andThrow(new NoResultException());

        VerzekeringsMaatschappij maatschappij = new VerzekeringsMaatschappij();
        expect(verzekeringsMaatschappijService.zoekOpNaam("maatschappij")).andReturn(maatschappij);

        Relatie relatie = new Relatie();
        expect(gebruikerService.lees(46L)).andReturn(relatie);

        AutoVerzekering polis = new AutoVerzekering();
        polis.setPolisNummer("1234");
        polis.setMaatschappij(maatschappij);
        polis.setRelatie(relatie);
        polis.setIngangsDatum(new LocalDate(2014, 2, 1));
        polis.setProlongatieDatum(new LocalDate(2014, 3, 2));
        polis.setWijzigingsDatum(new LocalDate(2014, 4, 3));
        polis.setBetaalfrequentie(Betaalfrequentie.J);
        polis.setPremie(new Bedrag("12"));

        polisRepository.opslaan(polis);
        expectLastCall();

        relatie.getPolissen().add(polis);
        gebruikerService.opslaan(relatie);
        expectLastCall();

        replayAll();

        polisService.opslaan(opslaanPolis);
    }

}
