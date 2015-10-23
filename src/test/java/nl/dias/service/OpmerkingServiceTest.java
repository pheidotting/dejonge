package nl.dias.service;

import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;

import java.util.HashSet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import nl.dias.domein.Hypotheek;
import nl.dias.domein.Medewerker;
import nl.dias.domein.Opmerking;
import nl.dias.domein.Schade;
import nl.dias.repository.OpmerkingRepository;

import org.easymock.EasyMockSupport;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
@Ignore
public class OpmerkingServiceTest extends EasyMockSupport {
    private OpmerkingService service;
    private OpmerkingRepository repository;
    private AuthorisatieService authorisatieService;
    private SchadeService schadeService;
    private HypotheekService hypotheekService;

    @Before
    public void setUp() throws Exception {
        service = new OpmerkingService();

        repository = createMock(OpmerkingRepository.class);
        service.setOpmerkingRepository(repository);

        authorisatieService = createMock(AuthorisatieService.class);
        service.setAuthorisatieService(authorisatieService);

        schadeService = createMock(SchadeService.class);
        service.setSchadeService(schadeService);

        hypotheekService = createMock(HypotheekService.class);
        service.setHypotheekService(hypotheekService);
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
        Hypotheek hypotheek = createMock(Hypotheek.class);

        opmerking.setMedewerker(medewerker);
        expectLastCall();

        expect(opmerking.getSchade()).andReturn(schade).times(2);
        expect(schade.getId()).andReturn(46L);
        expect(opmerking.getHypotheek()).andReturn(hypotheek).times(2);
        expect(hypotheek.getId()).andReturn(58L);

        expect(schadeService.lees(46L)).andReturn(schade);
        expect(schade.getOpmerkingen()).andReturn(new HashSet<Opmerking>());

        expect(hypotheekService.leesHypotheek(58L)).andReturn(hypotheek);
        expect(hypotheek.getOpmerkingen()).andReturn(new HashSet<Opmerking>());

        repository.opslaan(opmerking);
        expectLastCall();

        schadeService.opslaan(schade);
        expectLastCall();

        hypotheekService.opslaan(hypotheek);
        expectLastCall();

        replayAll();

        service.opslaan(opmerking);
    }
}
