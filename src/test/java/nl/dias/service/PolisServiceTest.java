//package nl.dias.service;
//
//import nl.dias.domein.*;
//import nl.dias.domein.json.JsonPolis;
//import nl.dias.domein.polis.*;
//import nl.dias.repository.KantoorRepository;
//import nl.dias.repository.PolisRepository;
//import org.easymock.EasyMockRunner;
//import org.easymock.EasyMockSupport;
//import org.easymock.Mock;
//import org.easymock.TestSubject;
//import org.joda.time.LocalDate;
//import org.junit.After;
//import org.junit.Ignore;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//
//import javax.persistence.NoResultException;
//import java.util.ArrayList;
//import java.util.HashSet;
//import java.util.List;
//import java.util.Set;
//
//import static org.easymock.EasyMock.expect;
//import static org.easymock.EasyMock.expectLastCall;
//import static org.junit.Assert.assertEquals;
//import static org.junit.Assert.assertNull;
//
//
//@Ignore
//@RunWith(EasyMockRunner.class)
//public class PolisServiceTest extends EasyMockSupport {
//    @TestSubject
//    private PolisService polisService = new PolisService();
//
//    @Mock
//    private PolisRepository polisRepository;
//    @Mock
//    private GebruikerService gebruikerService;
//    @Mock
//    private KantoorRepository kantoorRepository;
//    @Mock
//    private BedrijfService bedrijfService;
//    @Mock
//    private VerzekeringsMaatschappijService verzekeringsMaatschappijService;
//
//    @After
//    public void after() {
//        verifyAll();
//    }
//
//    @Test
//    public void testBeeindigen() {
//        Polis polis = createMock(Polis.class);
//
//        expect(polisRepository.lees(2L)).andReturn(polis);
//        polis.setEindDatum(LocalDate.now());
//        expectLastCall();
//
//        polisRepository.opslaan(polis);
//        expectLastCall();
//
//        replayAll();
//
//        polisService.beeindigen(2L);
//
//        verifyAll();
//    }
//
//    @Test
//    public void testAllePolissenVanRelatieEnZijnBedrijf() {
//        Relatie relatie = new Relatie();
//        List<Polis> polissen = new ArrayList<Polis>();
//
//        expect(polisRepository.allePolissenVanRelatieEnZijnBedrijf(relatie)).andReturn(polissen);
//
//        replayAll();
//
//        assertEquals(polissen, polisService.allePolissenVanRelatieEnZijnBedrijf(relatie));
//    }
//
//    @Test
//    public void testOpslaanPolis() {
//        AutoVerzekering polis = createMock(AutoVerzekering.class);
//        Relatie relatie = createMock(Relatie.class);
//
//        List<Bijlage> bijlages = new ArrayList<>();
//        Set<Bijlage> bijlagesSet = new HashSet<>();
//
//        expect(polisRepository.zoekBijlagesBijPolis(polis)).andReturn(bijlages);
//        expect(polis.getBijlages()).andReturn(bijlagesSet);
//
//        polisRepository.opslaan(polis);
//        expectLastCall();
//
//        expect(polis.getRelatie()).andReturn(relatie);
//        expect(polis.getId()).andReturn(1L);
//
//        expect(polisRepository.lees(1L)).andReturn(polis);
//
//        expect(relatie.getPolissen()).andReturn(new HashSet<Polis>());
//
//        gebruikerService.opslaan(relatie);
//        expectLastCall();
//
//        replayAll();
//
//        polisService.opslaan(polis);
//    }
//
//    @Test
//    public void testZoekOpPolisNummer() {
//        CamperVerzekering camperVerzekering = new CamperVerzekering();
//        Kantoor kantoor = new Kantoor();
//
//        expect(kantoorRepository.lees(1L)).andReturn(kantoor);
//        expect(polisRepository.zoekOpPolisNummer("1234", kantoor)).andReturn(camperVerzekering);
//
//        replayAll();
//
//        assertEquals(camperVerzekering, polisService.zoekOpPolisNummer("1234"));
//    }
//
//    @Test
//    public void testZoekOpPolisNummerMetException() {
//        Kantoor kantoor = new Kantoor();
//
//        expect(kantoorRepository.lees(1L)).andReturn(kantoor);
//        expect(polisRepository.zoekOpPolisNummer("1234", kantoor)).andThrow(new NoResultException());
//
//        replayAll();
//
//        assertNull(polisService.zoekOpPolisNummer("1234"));
//    }
//
//    @Test
//    public void testSlaBijlageOp() {
//        String S3IDENTIFICATIE = "s3Identificatie";
//        String OMSCHRIJVING = "omschrijvingBijlage";
//
//        FietsVerzekering fietsVerzekering = new FietsVerzekering();
//
//        Bijlage bijlage = new Bijlage();
//        bijlage.setPolis(fietsVerzekering);
//        bijlage.setSoortBijlage(SoortBijlage.POLIS);
//        bijlage.setS3Identificatie(S3IDENTIFICATIE);
//        bijlage.setOmschrijving(OMSCHRIJVING);
//
//        expect(polisRepository.lees(1L)).andReturn(fietsVerzekering);
//
//        polisRepository.opslaanBijlage(bijlage);
//        expectLastCall();
//
//        replayAll();
//
//        polisService.slaBijlageOp(1L, bijlage, OMSCHRIJVING);
//    }
//
//    @Test
//    public void testLeesBijlage() {
//        Bijlage bijlage = new Bijlage();
//
//        expect(polisRepository.leesBijlage(1L)).andReturn(bijlage);
//
//        replayAll();
//
//        assertEquals(bijlage, polisService.leesBijlage(1L));
//    }
//
//    @Test
//    public void testVerwijder() {
//        MobieleApparatuurVerzekering verzekering = new MobieleApparatuurVerzekering();
//        expect(polisRepository.lees(1L)).andReturn(verzekering);
//
//        Relatie relatie = new Relatie();
//        relatie.setId(2L);
//        verzekering.setRelatie(relatie);
//        relatie.getPolissen().add(verzekering);
//        expect(gebruikerService.lees(2L)).andReturn(relatie);
//
//        relatie.setPolissen(new HashSet<Polis>());
//
//        gebruikerService.opslaan(relatie);
//        expectLastCall();
//
//        polisRepository.verwijder(verzekering);
//        expectLastCall();
//
//        replayAll();
//
//        polisService.verwijder(1L);
//
//    }
//
//    @Test
//    public void testOpslaanOpslaanPolis() {
//        JsonPolis opslaanPolis = new JsonPolis();
//        opslaanPolis.setSoort("Auto");
//        opslaanPolis.setPolisNummer("1234");
//        opslaanPolis.setMaatschappij("maatschappij");
//        opslaanPolis.setRelatie("46");
//        opslaanPolis.setIngangsDatum("01-02-2014");
//        opslaanPolis.setProlongatieDatum("02-03-2014");
//        opslaanPolis.setWijzigingsDatum("03-04-2014");
//        opslaanPolis.setBetaalfrequentie("jaar");
//        opslaanPolis.setPremie("12");
//
//        Bedrijf bedrijf = createMock(Bedrijf.class);
//        opslaanPolis.setBedrijf("46");
//
//        Kantoor kantoor = new Kantoor();
//        expect(kantoorRepository.lees(1L)).andReturn(kantoor);
//
//        expect(polisRepository.zoekOpPolisNummer("1234", kantoor)).andThrow(new NoResultException());
//
//        VerzekeringsMaatschappij maatschappij = new VerzekeringsMaatschappij();
//        expect(verzekeringsMaatschappijService.zoekOpNaam("maatschappij")).andReturn(maatschappij);
//
//        Relatie relatie = new Relatie();
//        expect(gebruikerService.lees(46L)).andReturn(relatie);
//
//        AutoVerzekering polis = new AutoVerzekering();
//        polis.setPolisNummer("1234");
//        polis.setMaatschappij(maatschappij);
//        polis.setRelatie(relatie);
//        polis.setIngangsDatum(new LocalDate(2014, 2, 1));
//        polis.setProlongatieDatum(new LocalDate(2014, 3, 2));
//        polis.setWijzigingsDatum(new LocalDate(2014, 4, 3));
//        polis.setBetaalfrequentie(Betaalfrequentie.J);
//        polis.setPremie(new Bedrag("12"));
//
//        expect(bedrijfService.lees(46L)).andReturn(bedrijf);
//        expect(bedrijf.getPolissen()).andReturn(new HashSet<Polis>());
//
//        polisRepository.opslaan(polis);
//        expectLastCall();
//
//        relatie.getPolissen().add(polis);
//        gebruikerService.opslaan(relatie);
//        expectLastCall();
//
//        replayAll();
//
//        polisService.opslaan(opslaanPolis);
//    }
//
//}
