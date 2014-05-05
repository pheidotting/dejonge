package nl.dias.servlet;

//
//import java.io.PrintWriter;
//import java.util.HashSet;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import nl.dias.domein.Kantoor;
//import nl.dias.domein.Medewerker;
//import nl.dias.domein.Relatie;
//import nl.dias.service.GebruikerService;
//import nl.dias.service.RelatieService;
//
//import org.easymock.EasyMock;
//import org.joda.time.LocalDate;
//import org.junit.Before;
//import org.junit.Test;
//import org.powermock.api.easymock.PowerMock;
//
public class LeesRelatieTest {
	// private LeesRelatie leesRelatie;
	// private RelatieService relatieService;
	// private GebruikerService medewerkerService;
	// private HttpServletRequest request;
	// private HttpServletResponse response;
	// private PrintWriter out;
	//
	// @Before
	// public void setUp() throws Exception {
	// leesRelatie = PowerMock.createPartialMock(LeesRelatie.class,
	// "getIngelogdeGebruiker");
	//
	// relatieService = EasyMock.createMock(RelatieService.class);
	// leesRelatie.setRelatieService(relatieService);
	//
	// medewerkerService = EasyMock.createMock(GebruikerService.class);
	// leesRelatie.setMedewerkerService(medewerkerService);
	//
	// request = EasyMock.createMock(HttpServletRequest.class);
	// response = EasyMock.createMock(HttpServletResponse.class);
	//
	// out = EasyMock.createMock(PrintWriter.class);
	// }
	//
	// @Test
	// public void testDoGet() throws Exception {
	// response.setContentType("text/html;charset=UTF-8");
	// EasyMock.expectLastCall();
	//
	// Medewerker medewerker = new Medewerker();
	// medewerker.setId(2L);
	// medewerker.setKantoor(new Kantoor());
	// PowerMock.expectPrivate(leesRelatie, "getIngelogdeGebruiker",
	// request).andReturn(medewerker);
	//
	// EasyMock.expect(response.getWriter()).andReturn(out);
	//
	// EasyMock.expect(request.getParameter("id")).andReturn("3");
	//
	// // lege set met relaties, zodat de opgevraagde relatie niet onder dit
	// // kantoor valt en dus niet getoond mag worden
	// medewerker.getKantoor().setRelaties(new HashSet<Relatie>());
	// EasyMock.expect(medewerkerService.lees(2L)).andReturn(medewerker);
	//
	// Relatie relatie = new Relatie();
	// relatie.setId(3L);
	// relatie.setGeboorteDatum(new LocalDate(2013, 9, 18));
	// relatie.setOverlijdensdatum(new LocalDate(2013, 9, 18));
	// EasyMock.expect(relatieService.lees(3L)).andReturn(relatie);
	//
	// out.println("{\"geboorteDatumString\":\"" + new
	// LocalDate().toString("dd-MM-yyyy") + "\",\"overlijdensdatumString\":\"" +
	// new LocalDate().toString("dd-MM-yyyy")
	// +
	// "\",\"telefoonnummers\":[],\"rekeningnummers\":[],\"opmerkingen\":[],\"onderlingeRelaties\":[]}");
	// EasyMock.expectLastCall();
	//
	// out.close();
	// EasyMock.expectLastCall();
	//
	// PowerMock.replayAll();
	// EasyMock.replay(request, response, relatieService, medewerkerService,
	// out);
	//
	// leesRelatie.doGet(request, response);
	//
	// PowerMock.verifyAll();
	// EasyMock.verify(request, response, relatieService, medewerkerService,
	// out);
	// }
	//
	// @Test
	// public void testDoGetGoed() throws Exception {
	// response.setContentType("text/html;charset=UTF-8");
	// EasyMock.expectLastCall();
	//
	// Relatie relatie = new Relatie();
	// relatie.setId(3L);
	// relatie.setGeboorteDatum(new LocalDate(2013, 9, 15));
	// relatie.setOverlijdensdatum(new LocalDate(2013, 9, 15));
	//
	// Medewerker medewerker = new Medewerker();
	// medewerker.setId(2L);
	// medewerker.setKantoor(new Kantoor());
	// medewerker.getKantoor().setRelaties(new HashSet<Relatie>());
	// medewerker.getKantoor().getRelaties().add(relatie);
	// PowerMock.expectPrivate(leesRelatie, "getIngelogdeGebruiker",
	// request).andReturn(medewerker);
	//
	// EasyMock.expect(response.getWriter()).andReturn(out);
	//
	// EasyMock.expect(request.getParameter("id")).andReturn("3");
	//
	// EasyMock.expect(medewerkerService.lees(2L)).andReturn(medewerker);
	//
	// EasyMock.expect(relatieService.lees(3L)).andReturn(relatie);
	//
	// out.println("{\"geboorteDatumString\":\"15-09-2013\",\"overlijdensdatumString\":\"15-09-2013\",\"telefoonnummers\":[],\"rekeningnummers\":[],\"opmerkingen\":[],\"onderlingeRelaties\":[],\"id\":3}");
	// EasyMock.expectLastCall();
	//
	// out.close();
	// EasyMock.expectLastCall();
	//
	// PowerMock.replayAll();
	// EasyMock.replay(request, response, relatieService, medewerkerService,
	// out);
	//
	// leesRelatie.doGet(request, response);
	//
	// PowerMock.verifyAll();
	// EasyMock.verify(request, response, relatieService, medewerkerService,
	// out);
	// }
}
