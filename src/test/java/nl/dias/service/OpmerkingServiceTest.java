package nl.dias.service;

import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;

import java.util.HashSet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import nl.dias.domein.Medewerker;
import nl.dias.domein.Opmerking;
import nl.dias.domein.Schade;
import nl.dias.repository.OpmerkingRepository;

import org.easymock.EasyMockSupport;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class OpmerkingServiceTest extends EasyMockSupport {
    private OpmerkingService service;
    private OpmerkingRepository repository;
    private AuthorisatieService authorisatieService;
    private SchadeService schadeService;
    private HttpServletRequest httpServletRequest;

    @Before
    public void setUp() throws Exception {
        service = new OpmerkingService();

        repository = createMock(OpmerkingRepository.class);
        service.setOpmerkingRepository(repository);

        authorisatieService = createMock(AuthorisatieService.class);
        service.setAuthorisatieService(authorisatieService);

        schadeService = createMock(SchadeService.class);
        service.setSchadeService(schadeService);

        httpServletRequest = createMock(HttpServletRequest.class);
        service.setHttpServletRequest(httpServletRequest);
    }

    @After
    public void tearDown() throws Exception {
        verifyAll();
    }

    @Test
    public void test() {
        Opmerking opmerking = createMock(Opmerking.class);
        HttpSession httpSession = createMock(HttpSession.class);
        Medewerker medewerker = createMock(Medewerker.class);
        Schade schade = createMock(Schade.class);

        expect(httpServletRequest.getSession()).andReturn(httpSession).times(3);

        expect(httpSession.getAttribute("sessie")).andReturn("sessie").times(3);
        expect(httpServletRequest.getRemoteAddr()).andReturn("remoteAddr");
        expect(authorisatieService.getIngelogdeGebruiker(httpServletRequest, "sessie", "remoteAddr")).andReturn(medewerker);

        opmerking.setMedewerker(medewerker);
        expectLastCall();

        expect(opmerking.getSchade()).andReturn(schade);
        expect(schade.getId()).andReturn(46L);

        expect(schadeService.lees(46L)).andReturn(schade);
        expect(schade.getOpmerkingen()).andReturn(new HashSet<Opmerking>());

        repository.opslaan(opmerking);
        expectLastCall();

        schadeService.opslaan(schade);
        expectLastCall();

        replayAll();

        service.opslaan(opmerking);
    }
}