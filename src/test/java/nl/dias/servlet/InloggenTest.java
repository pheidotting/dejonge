//package nl.dias.servlet;
//
//import java.io.IOException;
//import java.io.PrintWriter;
//
//import javax.servlet.ServletException;
//import javax.servlet.http.Cookie;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import javax.servlet.http.HttpSession;
//
//import nl.dias.domein.Medewerker;
//import nl.dias.exception.BsnNietGoedException;
//import nl.dias.exception.IbanNietGoedException;
//import nl.dias.exception.PostcodeNietGoedException;
//import nl.dias.exception.TelefoonnummerNietGoedException;
//import nl.dias.service.GebruikerService;
//import nl.lakedigital.loginsystem.exception.LeegVeldException;
//import nl.lakedigital.loginsystem.exception.NietGevondenException;
//import nl.lakedigital.loginsystem.exception.OnjuistWachtwoordException;
//import nl.lakedigital.loginsystem.inloggen.domein.Onderwerp;
//import nl.lakedigital.loginsystem.service.InlogUtil;
//
//import org.easymock.EasyMock;
//import org.junit.Before;
//import org.junit.Ignore;
//import org.junit.Test;
//
//@Ignore
//public class InloggenTest {
//	// private Inloggen inloggen;
//	private GebruikerService gebruikerService;
//	private GebruikerService medewerkerService;
//	private InlogUtil inlogUtil;
//	private HttpServletRequest request;
//	private HttpServletResponse response;
//	private PrintWriter out;
//
//	@Before
//	public void setUp() throws Exception {
//		// inloggen = new Inloggen();
//		//
//		// gebruikerService = EasyMock.createMock(GebruikerService.class);
//		// inloggen.setGebruikerService(gebruikerService);
//		//
//		// medewerkerService = EasyMock.createMock(GebruikerService.class);
//		// inloggen.setMedewerkerService(medewerkerService);
//		//
//		// inlogUtil = EasyMock.createMock(InlogUtil.class);
//		// inloggen.setInlogUtil(inlogUtil);
//
//		request = EasyMock.createMock(HttpServletRequest.class);
//		response = EasyMock.createMock(HttpServletResponse.class);
//
//		out = EasyMock.createMock(PrintWriter.class);
//	}
//
//	@Test
//	public void test() throws ServletException, IOException, PostcodeNietGoedException, TelefoonnummerNietGoedException, BsnNietGoedException, IbanNietGoedException, LeegVeldException, NietGevondenException, OnjuistWachtwoordException {
//		response.setContentType("text/html;charset=UTF-8");
//		EasyMock.expectLastCall();
//
//		EasyMock.expect(response.getWriter()).andReturn(out);
//
//		EasyMock.expect(request.getParameter("emailadres")).andReturn("a@b.c");
//		EasyMock.expect(request.getParameter("wachtwoord")).andReturn("wachtwoord");
//		EasyMock.expect(request.getParameter("onthouden")).andReturn("true");
//		EasyMock.expect(request.getRemoteAddr()).andReturn("ipadres");
//		EasyMock.expect(request.getHeader("user-agent")).andReturn("agent");
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
//		EasyMock.expect(medewerkerService.zoek("a@b.c")).andReturn(medewerker);
//
//		// inloggen.setCookieCode("cookieCode");
//		Cookie cookie = new Cookie("inloggen", "cookieCode");
//		cookie.setDomain("dias");
//		// inloggen.setCookie(cookie);
//		response.addCookie(cookie);
//		EasyMock.expectLastCall();
//
//		inlogUtil.inloggen(onderwerp, null, medewerker);
//		EasyMock.expectLastCall();
//
//		HttpSession sessie = EasyMock.createMock(HttpSession.class);
//		EasyMock.expect(request.getSession()).andReturn(sessie);
//
//		sessie.setAttribute("ingelogdeGebruiker", 58);
//		EasyMock.expectLastCall();
//
//		medewerkerService.opslaan(medewerker);
//		EasyMock.expectLastCall();
//
//		// out.println("{\"kantoornaam\":\"B\",\"voornaam\":\"A\",\"idKantoor\":\"58\",\"idMedewerker\":\"46\"}");
//		// EasyMock.expectLastCall();
//
//		out.close();
//		EasyMock.expectLastCall();
//
//		EasyMock.replay(request, response, medewerkerService, gebruikerService, out, inlogUtil);
//
//		// inloggen.doGet(request, response);
//
//		EasyMock.verify(request, response, medewerkerService, gebruikerService, out, inlogUtil);
//	}
//
// }
