package nl.dias.service;

import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;
import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import nl.dias.domein.Schade;
import nl.dias.domein.SoortSchade;
import nl.dias.domein.StatusSchade;
import nl.dias.domein.polis.Polis;
import nl.dias.repository.SchadeRepository;

import org.easymock.EasyMockSupport;
import org.junit.Before;
import org.junit.Test;

public class SchadeServiceTest extends EasyMockSupport {
    private SchadeService service;
    private SchadeRepository schadeRepository;
    private PolisService polisService;

    @Before
    public void setUp() throws Exception {
        service = new SchadeService();

        schadeRepository = createMock(SchadeRepository.class);
        service.setSchadeRepository(schadeRepository);

        polisService = createMock(PolisService.class);
        service.setPolisService(polisService);
    }

    @Test
    public void testSoortenSchade() {
        SoortSchade soortSchade = createMock(SoortSchade.class);
        List<SoortSchade> lijst = new ArrayList<SoortSchade>();
        lijst.add(soortSchade);

        expect(schadeRepository.soortenSchade()).andReturn(lijst);

        replayAll();

        assertEquals(lijst, service.soortenSchade());

        verifyAll();
    }

    @Test
    public void testSoortenSchadeString() {
        SoortSchade soortSchade = createMock(SoortSchade.class);
        List<SoortSchade> lijst = new ArrayList<SoortSchade>();
        lijst.add(soortSchade);

        expect(schadeRepository.soortenSchade("omschr")).andReturn(lijst);

        replayAll();

        assertEquals(lijst, service.soortenSchade("omschr"));

        verifyAll();
    }

    @Test
    public void opslaanMetEnum() {
        String soortSchadeString = "soortSchade";
        Long polisId = 46L;
        String statusSchadeString = "statusSchade";

        Schade schade = createMock(Schade.class);
        SoortSchade soortSchade = createMock(SoortSchade.class);
        List<SoortSchade> soortSchades = new ArrayList<>();
        soortSchades.add(soortSchade);
        Polis polis = createMock(Polis.class);
        StatusSchade statusSchade = createMock(StatusSchade.class);

        expect(schadeRepository.soortenSchade(soortSchadeString)).andReturn(soortSchades);
        schade.setSoortSchade(soortSchade);
        expectLastCall();

        expect(schadeRepository.getStatussen(statusSchadeString)).andReturn(statusSchade);
        schade.setStatusSchade(statusSchade);
        expectLastCall();

        expect(polisService.lees(polisId)).andReturn(polis);
        schade.setPolis(polis);
        expectLastCall();

        schadeRepository.opslaan(schade);
        expectLastCall();

        replayAll();

        service.opslaan(schade, soortSchadeString, polisId, statusSchadeString);

        verifyAll();
    }

    @Test
    public void opslaanZonderEnum() {
        String soortSchadeString = "soortSchade";
        Long polisId = 46L;
        String statusSchadeString = "statusSchade";

        Schade schade = createMock(Schade.class);
        List<SoortSchade> soortSchades = new ArrayList<>();
        Polis polis = createMock(Polis.class);
        StatusSchade statusSchade = createMock(StatusSchade.class);

        expect(schadeRepository.soortenSchade(soortSchadeString)).andReturn(soortSchades);
        schade.setSoortSchadeOngedefinieerd(soortSchadeString);
        expectLastCall();

        expect(schadeRepository.getStatussen(statusSchadeString)).andReturn(statusSchade);
        schade.setStatusSchade(statusSchade);
        expectLastCall();

        expect(polisService.lees(polisId)).andReturn(polis);
        schade.setPolis(polis);
        expectLastCall();

        schadeRepository.opslaan(schade);
        expectLastCall();

        replayAll();

        service.opslaan(schade, soortSchadeString, polisId, statusSchadeString);

        verifyAll();
    }

}
