//package nl.dias.web;
//
//import static org.junit.Assert.assertEquals;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpSession;
//
//import nl.dias.domein.Kantoor;
//import nl.dias.domein.Medewerker;
//import nl.dias.domein.OnderlingeRelatie;
//import nl.dias.domein.OnderlingeRelatieSoort;
//import nl.dias.domein.Relatie;
//import nl.dias.service.GebruikerService;
//import nl.lakedigital.loginsystem.exception.LeegVeldException;
//import nl.lakedigital.loginsystem.exception.NietGevondenException;
//import nl.lakedigital.loginsystem.exception.OnjuistWachtwoordException;
//import nl.lakedigital.loginsystem.inloggen.domein.Onderwerp;
//import nl.lakedigital.loginsystem.service.InlogUtil;
//
//import org.easymock.EasyMock;
//import org.joda.time.LocalDate;
//import org.junit.Before;
//import org.junit.Ignore;
//import org.junit.Test;
//import org.powermock.api.easymock.PowerMock;
//
//import com.google.gson.Gson;
//
//@Ignore
//public class GebruikerControllerTest {
//	private GebruikerService gebruikerService;
//	private GebruikerController controller;
//	private InlogUtil inlogUtil;
//	private HttpServletRequest request;
//
//	@Before
//	public void setUp() throws Exception {
//		controller = new GebruikerController();
//
//		gebruikerService = EasyMock.createMock(GebruikerService.class);
//		controller.setGebruikerService(gebruikerService);
//
//		request = EasyMock.createMock(HttpServletRequest.class);
//
//		inlogUtil = EasyMock.createMock(InlogUtil.class);
//		controller.setInlogUtil(inlogUtil);
//
//		request = EasyMock.createMock(HttpServletRequest.class);
//	}
//
//	@Test
//	public void verwijder() {
//		Medewerker medewerker = new Medewerker();
//
//		EasyMock.expect(gebruikerService.lees(58L)).andReturn(medewerker);
//
//		gebruikerService.verwijder(medewerker);
//		EasyMock.expectLastCall();
//
//		EasyMock.replay(gebruikerService);
//
//		controller.verwijder("58");
//
//		EasyMock.verify(gebruikerService);
//	}
//
//	@Test
//	public void lees() {
//		Relatie relatie = new Relatie();
//		relatie.setGeboorteDatum(new LocalDate(2013, 11, 18));
//		relatie.setOverlijdensdatum(new LocalDate(2013, 11, 18));
//
//		EasyMock.expect(gebruikerService.lees(46L)).andReturn(relatie);
//
//		EasyMock.replay(gebruikerService);
//
//		assertEquals("{\"geboorteDatumString\":\"18-11-2013\",\"overlijdensdatumString\":\"18-11-2013\",\"telefoonNummers\":[],\"rekeningnummers\":[],\"opmerkingen\":[],\"polissen\":[]}",
//				controller.lees("46"));
//
//		EasyMock.verify(gebruikerService);
//	}
//
//	@Test
//	public void lijst() throws CloneNotSupportedException {
//		Relatie relatie = new Relatie();
//		relatie.setGeboorteDatum(new LocalDate(2013, 11, 19));
//		relatie.setOverlijdensdatum(new LocalDate(2013, 11, 19));
//		Relatie relatie2 = new Relatie();
//		relatie2.setGeboorteDatum(new LocalDate(2013, 11, 19));
//		relatie2.setOverlijdensdatum(new LocalDate(2013, 11, 19));
//		List<Relatie> lijst = new ArrayList<>();
//		lijst.add(relatie.clone());
//		lijst.add(relatie2.clone());
//
//		EasyMock.expect(gebruikerService.alleRelaties(null)).andReturn(lijst);
//
//		EasyMock.replay(gebruikerService);
//
//		String verwacht = "[{\"geboorteDatumString\":\"19-11-2013\",\"overlijdensdatumString\":\"19-11-2013\",\"telefoonNummers\":[],\"rekeningnummers\":[],\"opmerkingen\":[],\"polissen\":[]},{\"geboorteDatumString\":\"19-11-2013\",\"overlijdensdatumString\":\"19-11-2013\",\"telefoonNummers\":[],\"rekeningnummers\":[],\"opmerkingen\":[],\"polissen\":[]}]";
//		assertEquals(verwacht, controller.lijstRelaties(null));
//
//		EasyMock.verify(gebruikerService);
//	}
//
//	@Test
//	public void opslaan() {
//		Relatie relatie = new Relatie();
//		relatie.setGeboorteDatum(new LocalDate(2013, 11, 19));
//		relatie.setOverlijdensdatum(new LocalDate(2013, 11, 19));
//
//		gebruikerService.opslaan(relatie);
//		EasyMock.expectLastCall();
//
//		Gson gson = new Gson();
//
//		EasyMock.replay(gebruikerService);
//
//		assertEquals("{\"geboorteDatumString\":\"19-11-2013\",\"overlijdensdatumString\":\"19-11-2013\",\"telefoonNummers\":[],\"rekeningnummers\":[],\"opmerkingen\":[],\"polissen\":[]}",
//				controller.opslaan(gson.toJson(relatie)));
//
//		EasyMock.verify(gebruikerService);
//	}
//
//	@Test
//	public void verwijderen() {
//		Relatie relatie = new Relatie();
//		relatie.setId(2L);
//
//		EasyMock.expect(gebruikerService.lees(relatie.getId())).andReturn(relatie);
//
//		gebruikerService.verwijder(relatie);
//		EasyMock.expectLastCall();
//
//		EasyMock.replay(gebruikerService);
//
//		controller.verwijderen(relatie.getId().toString());
//
//		EasyMock.verify(gebruikerService);
//	}
//
//	@Test
//	public void toevoegenRelatieRelatie() {
//		Medewerker medewerker = new Medewerker();
//		medewerker.setId(2L);
//		medewerker.setKantoor(new Kantoor());
//
//		Relatie relatieAanToevoegen = new Relatie();
//		relatieAanToevoegen.setGeboorteDatum(new LocalDate(2013, 11, 3));
//		relatieAanToevoegen.setOverlijdensdatum(new LocalDate(2013, 11, 3));
//		Relatie relatieToeTeVoegen = new Relatie();
//		relatieToeTeVoegen.setGeboorteDatum(new LocalDate(2013, 11, 3));
//		relatieToeTeVoegen.setOverlijdensdatum(new LocalDate(2013, 11, 3));
//
//		EasyMock.expect(gebruikerService.lees(2L)).andReturn(relatieAanToevoegen).times(2);
//		EasyMock.expect(gebruikerService.lees(3L)).andReturn(relatieToeTeVoegen);
//
//		relatieAanToevoegen.getOnderlingeRelaties().add(new OnderlingeRelatie(relatieAanToevoegen, relatieToeTeVoegen, OnderlingeRelatieSoort.K));
//
//		gebruikerService.opslaan(relatieAanToevoegen);
//		EasyMock.expectLastCall();
//
//		EasyMock.replay(gebruikerService);
//
//		String verwacht = "{\"geboorteDatumString\":\"03-11-2013\",\"overlijdensdatumString\":\"03-11-2013\",\"onderlingeRelaties\":[{\"soort\":\"Kind\",\"metWie\":\"null null\"},{\"soort\":\"Ouder\",\"metWie\":\"null null\"}],\"telefoonNummers\":[],\"rekeningnummers\":[],\"opmerkingen\":[],\"polissen\":[]}";
//		String gekregen = controller.toevoegenRelatieRelatie("2", "3", "O");
//
//		if (!verwacht.equals(gekregen)) {
//			verwacht = "{\"geboorteDatumString\":\"03-11-2013\",\"overlijdensdatumString\":\"03-11-2013\",\"onderlingeRelaties\":[{\"soort\":\"Ouder\",\"metWie\":\"null null\"},{\"soort\":\"Kind\",\"metWie\":\"null null\"}],\"telefoonNummers\":[],\"rekeningnummers\":[],\"opmerkingen\":[],\"polissen\":[]}";
//		}
//		assertEquals(verwacht, gekregen);
//
//		EasyMock.verify(gebruikerService);
//	}
//
//	@Test
//	public void toevoegenRelatieRelatieNumeriekFoutEen() {
//		assertEquals("\"Numeriek veld verwacht\"", controller.toevoegenRelatieRelatie("a", "3", "O"));
//	}
//
//	@Test
//	public void toevoegenRelatieRelatieNumeriekFoutTwee() {
//		assertEquals("\"Numeriek veld verwacht\"", controller.toevoegenRelatieRelatie("1", "b", "O"));
//	}
//
//	@Test
//	public void toevoegenRelatieRelatieSoortFoutEen() {
//		assertEquals("\"De soort Relatie moet worden ingevuld.\"", controller.toevoegenRelatieRelatie("1", "2", null));
//	}
//
//	@Test
//	public void toevoegenRelatieRelatieSoortFoutTwee() {
//		assertEquals("\"De soort Relatie moet worden ingevuld.\"", controller.toevoegenRelatieRelatie("1", "2", ""));
//	}
//
//	@Test
//	public void inloggen() {
//		//
//		// EasyMock.expect(response.getWriter()).andReturn(out);
//		//
//		// EasyMock.expect(request.getParameter("emailadres")).andReturn("a@b.c");
//		// EasyMock.expect(request.getParameter("wachtwoord")).andReturn("wachtwoord");
//		// EasyMock.expect(request.getParameter("onthouden")).andReturn("true");
//		// EasyMock.expect(request.getRemoteAddr()).andReturn("ipadres");
//		// EasyMock.expect(request.getHeader("user-agent")).andReturn("agent");
//
//		Medewerker medewerker = new Medewerker();
//		medewerker.setId(2L);
//		medewerker.setIdentificatie("a@b.c");
//		medewerker.setVoornaam("A");
//		medewerker.setHashWachtwoord("wachtwoord");
//
//		@SuppressWarnings("serial")
//		Onderwerp onderwerp = new Onderwerp() {
//		};
//		onderwerp.setIdentificatie("a@b.c");
//		onderwerp.setHashWachtwoord("wachtwoord");
//
//		try {
//			EasyMock.expect(gebruikerService.zoek("a@b.c")).andReturn(medewerker);
//		} catch (NietGevondenException e) {
//		}
//
//		// inloggen.setCookieCode("cookieCode");
//		// Cookie cookie = new Cookie("inloggen", "cookieCode");
//		// cookie.setDomain("dias");
//		// inloggen.setCookie(cookie);
//		// response.addCookie(cookie);
//		// EasyMock.expectLastCall();
//
//		try {
//			inlogUtil.inloggen(onderwerp, null, medewerker);
//			EasyMock.expectLastCall();
//		} catch (LeegVeldException | NietGevondenException | OnjuistWachtwoordException e) {
//		}
//
//		HttpSession sessie = EasyMock.createMock(HttpSession.class);
//		EasyMock.expect(request.getSession()).andReturn(sessie).times(2);
//
//		EasyMock.expect(request.getHeader("user-agent")).andReturn("agent");
//
//		EasyMock.expect(sessie.getId()).andReturn("11").times(2);
//
//		EasyMock.expect(request.getRemoteAddr()).andReturn("12345").times(2);
//
//		// sessie.setAttribute("ingelogdeGebruiker", 58);
//		// EasyMock.expectLastCall();
//
//		gebruikerService.opslaan(medewerker);
//		EasyMock.expectLastCall();
//
//		// out.println("{\"kantoornaam\":\"B\",\"voornaam\":\"A\",\"idKantoor\":\"58\",\"idMedewerker\":\"46\"}");
//		// EasyMock.expectLastCall();
//
//		// out.close();
//		// EasyMock.expectLastCall();
//
//		EasyMock.replay(gebruikerService, request, sessie);
//
//		assertEquals("\"ok\"", controller.inloggen("a@b.c", "wachtwoord", "false", request));
//
//		EasyMock.verify(gebruikerService, request, sessie);
//	}
//
//	@Test
//	public void isIngelogd() throws Exception {
//		controller = PowerMock.createPartialMock(GebruikerController.class, "checkIngelogd");
//		controller.setGebruikerService(gebruikerService);
//
//		PowerMock.expectPrivate(controller, "checkIngelogd", request);
//
//		EasyMock.replay(request);
//		PowerMock.replayAll();
//
//		controller.isIngelogd(request);
//
//		EasyMock.verify(request);
//		PowerMock.verifyAll();
//	}
//
// }
