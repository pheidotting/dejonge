package nl.dias.service;

import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;
import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import nl.dias.domein.Bijlage;
import nl.dias.domein.Relatie;
import nl.dias.domein.Schade;
import nl.dias.domein.SoortBijlage;
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
        String polisId = "46";
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

        expect(polisService.lees(Long.valueOf(polisId))).andReturn(polis);
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
        String polisId = "46";
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

        expect(polisService.lees(Long.valueOf(polisId))).andReturn(polis);
        schade.setPolis(polis);
        expectLastCall();

        schadeRepository.opslaan(schade);
        expectLastCall();

        replayAll();

        service.opslaan(schade, soortSchadeString, polisId, statusSchadeString);

        verifyAll();
    }

    @Test
    public void testGetStatussen() {
        StatusSchade statusSchade = new StatusSchade();
        String zoekTerm = "zoekStatus";

        expect(schadeRepository.getStatussen(zoekTerm)).andReturn(statusSchade);

        replayAll();

        assertEquals(statusSchade, service.getStatussen(zoekTerm));

        verifyAll();
    }

    @Test
    public void testGetStatussenString() {
        List<StatusSchade> statussenSchade = new ArrayList<>();

        expect(schadeRepository.getStatussen()).andReturn(statussenSchade);

        replayAll();

        assertEquals(statussenSchade, service.getStatussen());

        verifyAll();
    }

    @Test
    public void testZoekOpSchadeNummerMaatschappij() {
        Schade schade = new Schade();
        String schadeNummerMaatschappij = "schadeNummerMaatschappij";

        expect(schadeRepository.zoekOpSchadeNummerMaatschappij(schadeNummerMaatschappij)).andReturn(schade);

        replayAll();

        assertEquals(schade, service.zoekOpSchadeNummerMaatschappij(schadeNummerMaatschappij));

        verifyAll();
    }

    @Test
    public void testVerwijder() {
        Schade schade = new Schade();
        Long id = 46L;

        expect(schadeRepository.lees(id)).andReturn(schade);
        schadeRepository.verwijder(schade);
        expectLastCall();

        replayAll();

        service.verwijder(id);

        verifyAll();
    }

    @Test
    public void testSlaBijlageOp() {
        Long schadeId = 58L;
        String s3Identificatie = "s3";
        Schade schade = new Schade();
        Bijlage bijlage = new Bijlage();
        bijlage.setSoortBijlage(SoortBijlage.SCHADE);
        bijlage.setS3Identificatie(s3Identificatie);

        expect(schadeRepository.lees(schadeId)).andReturn(schade);

        schadeRepository.opslaanBijlage(bijlage);
        expectLastCall();

        replayAll();

        service.slaBijlageOp(schadeId, s3Identificatie);

        verifyAll();
    }

    @Test
    public void testAlleSchadesBijRelatie() {
        List<Schade> lijst = new ArrayList<>();
        Relatie relatie = new Relatie();

        expect(schadeRepository.alleSchadesBijRelatie(relatie)).andReturn(lijst);

        replayAll();

        service.alleSchadesBijRelatie(relatie);

        verifyAll();
    }
}
