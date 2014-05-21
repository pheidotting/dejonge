package nl.dias.web;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import nl.dias.domein.Relatie;
import nl.dias.domein.json.JsonRelatie;
import nl.dias.service.GebruikerService;
import nl.dias.web.mapper.RelatieMapper;

import org.easymock.EasyMock;
import org.easymock.EasyMockSupport;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

public class GebruikerControllerTest extends EasyMockSupport {
    private GebruikerService gebruikerService;
    private GebruikerController controller;
    private RelatieMapper mapper;

    @Before
    public void setUp() throws Exception {
        controller = new GebruikerController();

        gebruikerService = createMock(GebruikerService.class);
        controller.setGebruikerService(gebruikerService);

        mapper = createMock(RelatieMapper.class);
        controller.setRelatieMapper(mapper);
    }

    @After
    public void teardown() {
        verifyAll();
    }

    @Test
    public void verwijder() {
        gebruikerService.verwijder(58L);
        EasyMock.expectLastCall();

        replayAll();

        controller.verwijderen(58L);
    }

    @Test
    public void lees() {
        Relatie relatie = new Relatie();
        JsonRelatie jsonRelatie = new JsonRelatie();

        EasyMock.expect(gebruikerService.lees(46L)).andReturn(relatie);
        EasyMock.expect(mapper.mapNaarJson(relatie)).andReturn(jsonRelatie);

        replayAll();

        assertEquals(jsonRelatie, controller.lees("46"));

        EasyMock.verify(gebruikerService);
    }

    @Test
    @Ignore
    public void lijst() throws CloneNotSupportedException {
        Relatie relatie = new Relatie();
        List<Relatie> lijst = new ArrayList<>();
        Set<Relatie> lijst1 = new HashSet<>();
        lijst.add(relatie.clone());

        JsonRelatie jsonRelatie = new JsonRelatie();
        List<JsonRelatie> jsonRelaties = new ArrayList<>();
        jsonRelaties.add(jsonRelatie);

        EasyMock.expect(gebruikerService.alleRelaties(null)).andReturn(lijst);
        EasyMock.expect(mapper.mapAllNaarJson(lijst1)).andReturn(jsonRelaties);

        replayAll();

        assertEquals(jsonRelaties, controller.lijstRelaties(null));
    }

    @Test
    public void opslaan() {
        Relatie relatie = new Relatie();
        JsonRelatie jsonRelatie = new JsonRelatie();

        EasyMock.expect(mapper.mapVanJson(jsonRelatie)).andReturn(relatie);
        gebruikerService.opslaan(relatie);
        EasyMock.expectLastCall();

        replayAll();

        controller.opslaan(jsonRelatie);
    }

    // @Test
    // public void toevoegenRelatieRelatie() {
    // Medewerker medewerker = new Medewerker();
    // medewerker.setId(2L);
    // medewerker.setKantoor(new Kantoor());
    //
    // Relatie relatieAanToevoegen = new Relatie();
    // relatieAanToevoegen.setGeboorteDatum(new LocalDate(2013, 11, 3));
    // relatieAanToevoegen.setOverlijdensdatum(new LocalDate(2013, 11, 3));
    // Relatie relatieToeTeVoegen = new Relatie();
    // relatieToeTeVoegen.setGeboorteDatum(new LocalDate(2013, 11, 3));
    // relatieToeTeVoegen.setOverlijdensdatum(new LocalDate(2013, 11, 3));
    //
    // EasyMock.expect(gebruikerService.lees(2L)).andReturn(relatieAanToevoegen).times(2);
    // EasyMock.expect(gebruikerService.lees(3L)).andReturn(relatieToeTeVoegen);
    //
    // relatieAanToevoegen.getOnderlingeRelaties().add(new
    // OnderlingeRelatie(relatieAanToevoegen, relatieToeTeVoegen,
    // OnderlingeRelatieSoort.K));
    //
    // gebruikerService.opslaan(relatieAanToevoegen);
    // EasyMock.expectLastCall();
    //
    // EasyMock.replay(gebruikerService);
    //
    // String verwacht =
    // "{\"geboorteDatumString\":\"03-11-2013\",\"overlijdensdatumString\":\"03-11-2013\",\"onderlingeRelaties\":[{\"soort\":\"Kind\",\"metWie\":\"null null\"},{\"soort\":\"Ouder\",\"metWie\":\"null null\"}],\"telefoonNummers\":[],\"rekeningnummers\":[],\"opmerkingen\":[],\"polissen\":[]}";
    // String gekregen = controller.toevoegenRelatieRelatie("2", "3", "O");
    //
    // if (!verwacht.equals(gekregen)) {
    // verwacht =
    // "{\"geboorteDatumString\":\"03-11-2013\",\"overlijdensdatumString\":\"03-11-2013\",\"onderlingeRelaties\":[{\"soort\":\"Ouder\",\"metWie\":\"null null\"},{\"soort\":\"Kind\",\"metWie\":\"null null\"}],\"telefoonNummers\":[],\"rekeningnummers\":[],\"opmerkingen\":[],\"polissen\":[]}";
    // }
    // assertEquals(verwacht, gekregen);
    //
    // EasyMock.verify(gebruikerService);
    // }
    //
    // @Test
    // public void toevoegenRelatieRelatieNumeriekFoutEen() {
    // assertEquals("\"Numeriek veld verwacht\"",
    // controller.toevoegenRelatieRelatie("a", "3", "O"));
    // }
    //
    // @Test
    // public void toevoegenRelatieRelatieNumeriekFoutTwee() {
    // assertEquals("\"Numeriek veld verwacht\"",
    // controller.toevoegenRelatieRelatie("1", "b", "O"));
    // }
    //
    // @Test
    // public void toevoegenRelatieRelatieSoortFoutEen() {
    // assertEquals("\"De soort Relatie moet worden ingevuld.\"",
    // controller.toevoegenRelatieRelatie("1", "2", null));
    // }
    //
    // @Test
    // public void toevoegenRelatieRelatieSoortFoutTwee() {
    // assertEquals("\"De soort Relatie moet worden ingevuld.\"",
    // controller.toevoegenRelatieRelatie("1", "2", ""));
    // }
    //
    // @Test
    // public void inloggen() {
    // //
    // // EasyMock.expect(response.getWriter()).andReturn(out);
    // //
    // //
    // EasyMock.expect(request.getParameter("emailadres")).andReturn("a@b.c");
    // //
    // EasyMock.expect(request.getParameter("wachtwoord")).andReturn("wachtwoord");
    // // EasyMock.expect(request.getParameter("onthouden")).andReturn("true");
    // // EasyMock.expect(request.getRemoteAddr()).andReturn("ipadres");
    // // EasyMock.expect(request.getHeader("user-agent")).andReturn("agent");
    //
    // Medewerker medewerker = new Medewerker();
    // medewerker.setId(2L);
    // medewerker.setIdentificatie("a@b.c");
    // medewerker.setVoornaam("A");
    // medewerker.setHashWachtwoord("wachtwoord");
    //
    // @SuppressWarnings("serial")
    // Onderwerp onderwerp = new Onderwerp() {
    // };
    // onderwerp.setIdentificatie("a@b.c");
    // onderwerp.setHashWachtwoord("wachtwoord");
    //
    // try {
    // EasyMock.expect(gebruikerService.zoek("a@b.c")).andReturn(medewerker);
    // } catch (NietGevondenException e) {
    // }
    //
    // // inloggen.setCookieCode("cookieCode");
    // // Cookie cookie = new Cookie("inloggen", "cookieCode");
    // // cookie.setDomain("dias");
    // // inloggen.setCookie(cookie);
    // // response.addCookie(cookie);
    // // EasyMock.expectLastCall();
    //
    // try {
    // inlogUtil.inloggen(onderwerp, null, medewerker);
    // EasyMock.expectLastCall();
    // } catch (LeegVeldException | NietGevondenException |
    // OnjuistWachtwoordException e) {
    // }
    //
    // HttpSession sessie = EasyMock.createMock(HttpSession.class);
    // EasyMock.expect(request.getSession()).andReturn(sessie).times(2);
    //
    // EasyMock.expect(request.getHeader("user-agent")).andReturn("agent");
    //
    // EasyMock.expect(sessie.getId()).andReturn("11").times(2);
    //
    // EasyMock.expect(request.getRemoteAddr()).andReturn("12345").times(2);
    //
    // // sessie.setAttribute("ingelogdeGebruiker", 58);
    // // EasyMock.expectLastCall();
    //
    // gebruikerService.opslaan(medewerker);
    // EasyMock.expectLastCall();
    //
    // //
    // out.println("{\"kantoornaam\":\"B\",\"voornaam\":\"A\",\"idKantoor\":\"58\",\"idMedewerker\":\"46\"}");
    // // EasyMock.expectLastCall();
    //
    // // out.close();
    // // EasyMock.expectLastCall();
    //
    // EasyMock.replay(gebruikerService, request, sessie);
    //
    // assertEquals("\"ok\"", controller.inloggen("a@b.c", "wachtwoord",
    // "false", request));
    //
    // EasyMock.verify(gebruikerService, request, sessie);
    // }
    //
    // @Test
    // public void isIngelogd() throws Exception {
    // controller = PowerMock.createPartialMock(GebruikerController.class,
    // "checkIngelogd");
    // controller.setGebruikerService(gebruikerService);
    //
    // PowerMock.expectPrivate(controller, "checkIngelogd", request);
    //
    // EasyMock.replay(request);
    // PowerMock.replayAll();
    //
    // controller.isIngelogd(request);
    //
    // EasyMock.verify(request);
    // PowerMock.verifyAll();
    // }

}
