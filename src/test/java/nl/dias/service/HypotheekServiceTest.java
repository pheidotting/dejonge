package nl.dias.service;

import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;
import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import nl.dias.domein.Hypotheek;
import nl.dias.domein.Relatie;
import nl.dias.domein.SoortHypotheek;
import nl.dias.repository.HypotheekRepository;

import org.easymock.EasyMockSupport;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class HypotheekServiceTest extends EasyMockSupport {
    private HypotheekService service;
    private HypotheekRepository repository;
    private GebruikerService gebruikerService;

    @Before
    public void setUp() throws Exception {
        service = new HypotheekService();

        repository = createMock(HypotheekRepository.class);
        service.setHypotheekRepository(repository);

        gebruikerService = createMock(GebruikerService.class);
        service.setGebruikerService(gebruikerService);
    }

    @After
    public void tearDown() throws Exception {
        verifyAll();
    }

    @Test
    public void testOpslaan() {
        String hypotheekVorm = "2";
        Long relatieId = 58L;
        Relatie relatie = createMock(Relatie.class);
        SoortHypotheek soortHypotheek = createMock(SoortHypotheek.class);

        expect(gebruikerService.lees(relatieId)).andReturn(relatie);
        expect(repository.leesSoortHypotheek(Long.valueOf(hypotheekVorm))).andReturn(soortHypotheek);

        Hypotheek hypotheek = createMock(Hypotheek.class);
        hypotheek.setRelatie(relatie);
        expectLastCall();
        hypotheek.setHypotheekVorm(soortHypotheek);
        expectLastCall();

        repository.opslaan(hypotheek);
        expectLastCall();

        replayAll();

        service.opslaan(hypotheek, hypotheekVorm, relatieId);
    }

    @Test
    public void testLees() {
        Long id = 46L;
        Hypotheek hypotheek = createMock(Hypotheek.class);

        expect(repository.lees(id)).andReturn(hypotheek);

        replayAll();

        assertEquals(hypotheek, service.lees(id));
    }

    @Test
    public void testAlleSoortenHypotheekInGebruik() {
        List<SoortHypotheek> soorten = new ArrayList<>();

        expect(repository.alleSoortenHypotheekInGebruik()).andReturn(soorten);

        replayAll();

        assertEquals(soorten, service.alleSoortenHypotheekInGebruik());
    }

    @Test
    public void alles() {
        List<Hypotheek> lijst = new ArrayList<Hypotheek>();

        expect(repository.alles()).andReturn(lijst);

        replayAll();

        assertEquals(lijst, service.alles());
    }

}
