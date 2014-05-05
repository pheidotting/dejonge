//package nl.dias.web;
//
//import static org.junit.Assert.assertEquals;
//import static org.junit.Assert.assertNull;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import nl.dias.domein.Bedrag;
//import nl.dias.domein.Bedrijf;
//import nl.dias.domein.Relatie;
//import nl.dias.domein.VerzekeringsMaatschappij;
//import nl.dias.domein.polis.AutoVerzekering;
//import nl.dias.domein.polis.MotorVerzekering;
//import nl.dias.domein.polis.OngevallenVerzekering;
//import nl.dias.domein.polis.Polis;
//import nl.dias.domein.polis.SoortAutoVerzekering;
//import nl.dias.domein.polis.WoonhuisVerzekering;
//import nl.dias.service.GebruikerService;
//import nl.dias.service.PolisService;
//import nl.dias.service.VerzekeringsMaatschappijService;
//
//import org.easymock.EasyMock;
//import org.joda.time.LocalDate;
//import org.junit.Before;
//import org.junit.Test;
//
//public class PolisControllerTest {
//    private PolisController controller;
//    // private HttpServletRequest request;
//
//    private PolisService polisService;
//    private VerzekeringsMaatschappijService verzekeringsMaatschappijService;
//    private GebruikerService gebruikerService;
//
//    @Before
//    public void setUp() throws Exception {
//        controller = new PolisController();
//
//        polisService = EasyMock.createMock(PolisService.class);
//        controller.setPolisService(polisService);
//
//        verzekeringsMaatschappijService = EasyMock.createMock(VerzekeringsMaatschappijService.class);
//        controller.setVerzekeringsMaatschappijService(verzekeringsMaatschappijService);
//
//        gebruikerService = EasyMock.createMock(GebruikerService.class);
//        controller.setGebruikerService(gebruikerService);
//
//        // request = EasyMock.createMock(HttpServletRequest.class);
//    }
//
//    private void replayAll() {
//        // EasyMock.replay(request, polisService,
//        // verzekeringsMaatschappijService, gebruikerService);
//        EasyMock.replay(polisService, verzekeringsMaatschappijService, gebruikerService);
//    }
//
//    private void verifyAll() {
//        // EasyMock.verify(request, polisService,
//        // verzekeringsMaatschappijService, gebruikerService);
//        EasyMock.verify(polisService, verzekeringsMaatschappijService, gebruikerService);
//    }
//
//    @Test
//    public void testAutoVerzekering() {
//        VerzekeringsMaatschappij verzekeringsMaatschappij = new VerzekeringsMaatschappij();
//        verzekeringsMaatschappij.setNaam("naamVerzekeringsMaatschappij");
//        EasyMock.expect(verzekeringsMaatschappijService.zoekOpNaam("bestaandeMaatschappij")).andReturn(verzekeringsMaatschappij);
//
//        Relatie relatie = new Relatie();
//
//        Polis polis = new AutoVerzekering();
//        polis.setPolisNummer("polisNummer");
//        ((AutoVerzekering) polis).setSoortAutoVerzekering(SoortAutoVerzekering.Oldtimer);
//        ((AutoVerzekering) polis).setKenteken("kenteken");
//        polis.setIngangsDatum(new LocalDate(2013, 2, 1));
//        polis.setPremie(new Bedrag("100"));
//        polis.setMaatschappij(verzekeringsMaatschappij);
//        polis.setRelatie(relatie);
//
//        polisService.opslaan(polis);
//        EasyMock.expectLastCall();
//
//        EasyMock.expect(gebruikerService.lees(2L)).andReturn(relatie);
//
//        replayAll();
//
//        assertEquals("\"ok\"", controller.opslaan("bestaandeMaatschappij", "Auto", "polisNummer", "Oldtimer", "kenteken", null, "01-02-2013", "100", "2"));
//
//        verifyAll();
//    }
//
//    @Test
//    public void testMotorVerzekering() {
//        VerzekeringsMaatschappij verzekeringsMaatschappij = new VerzekeringsMaatschappij();
//        verzekeringsMaatschappij.setNaam("naamVerzekeringsMaatschappij");
//        EasyMock.expect(verzekeringsMaatschappijService.zoekOpNaam("bestaandeMaatschappij")).andReturn(verzekeringsMaatschappij);
//
//        Relatie relatie = new Relatie();
//
//        Polis polis = new MotorVerzekering();
//        polis.setPolisNummer("polisNummer");
//        ((MotorVerzekering) polis).setOldtimer(true);
//        ((MotorVerzekering) polis).setKenteken("kenteken");
//        polis.setIngangsDatum(new LocalDate(2013, 2, 1));
//        polis.setMaatschappij(verzekeringsMaatschappij);
//        polis.setRelatie(relatie);
//
//        polisService.opslaan(polis);
//        EasyMock.expectLastCall();
//
//        EasyMock.expect(gebruikerService.lees(2L)).andReturn(relatie);
//
//        replayAll();
//
//        assertEquals("\"ok\"", controller.opslaan("bestaandeMaatschappij", "Motor", "polisNummer", null, "kenteken", "true", "01-02-2013", null, "2"));
//
//        verifyAll();
//    }
//
//    @Test
//    public void testAllePolissen() {
//        VerzekeringsMaatschappij maatschappij = new VerzekeringsMaatschappij();
//        maatschappij.setNaam("Naam");
//
//        Relatie relatie = new Relatie();
//
//        Bedrijf bedrijf1 = new Bedrijf();
//        bedrijf1.setRelatie(relatie);
//        Bedrijf bedrijf2 = new Bedrijf();
//        bedrijf2.setRelatie(relatie);
//
//        relatie.getBedrijven().add(bedrijf1);
//        relatie.getBedrijven().add(bedrijf2);
//
//        AutoVerzekering autoVerzekering = new AutoVerzekering();
//        autoVerzekering.setRelatie(relatie);
//        autoVerzekering.setMaatschappij(maatschappij);
//        WoonhuisVerzekering woonhuisVerzekering = new WoonhuisVerzekering();
//        woonhuisVerzekering.setBedrijf(bedrijf1);
//        woonhuisVerzekering.setMaatschappij(maatschappij);
//        OngevallenVerzekering ongevallenVerzekering = new OngevallenVerzekering();
//        ongevallenVerzekering.setBedrijf(bedrijf2);
//        ongevallenVerzekering.setMaatschappij(maatschappij);
//
//        relatie.getPolissen().add(autoVerzekering);
//        bedrijf1.getPolissen().add(woonhuisVerzekering);
//        bedrijf2.getPolissen().add(ongevallenVerzekering);
//
//        EasyMock.expect(gebruikerService.lees(1L)).andReturn(relatie);
//
//        List<Polis> lijst = new ArrayList<>();
//        lijst.add(autoVerzekering);
//        lijst.add(woonhuisVerzekering);
//        lijst.add(ongevallenVerzekering);
//
//        EasyMock.expect(polisService.allePolissenVanRelatieEnZijnBedrijf(relatie)).andReturn(lijst);
//        lijst.get(0).setRelatie(null);
//        lijst.get(1).setBedrijf(null);
//        lijst.get(2).setBedrijf(null);
//
//        // Gson gson = new Gson();
//
//        replayAll();
//
//        // assertEquals(gson.toJson(lijst), controller.allePolissen("1"));
//        assertNull(controller.allePolissen("1"));
//
//        verifyAll();
//    }
// }
