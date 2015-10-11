package nl.dias.service;

import nl.dias.domein.*;
import nl.dias.domein.polis.Polis;
import nl.dias.repository.SchadeRepository;
import org.easymock.EasyMockRunner;
import org.easymock.EasyMockSupport;
import org.easymock.Mock;
import org.easymock.TestSubject;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;
import static org.junit.Assert.assertEquals;

@RunWith(EasyMockRunner.class)
public class SchadeServiceTest extends EasyMockSupport {
    @TestSubject
    private SchadeService service = new SchadeService();
    @Mock
    private SchadeRepository schadeRepository;
    @Mock
    private PolisService polisService;

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

        expect(schade.getId()).andReturn(58L);
        expect(schadeRepository.lees(58L)).andReturn(schade);
        expect(schade.getOpmerkingen()).andReturn(new HashSet<Opmerking>());
        schade.setOpmerkingen(new HashSet<Opmerking>());
        expectLastCall();

        Set<Bijlage> bijlageSet = new HashSet<>();
        List<Bijlage> bijlages = new ArrayList<>();
        expect(schadeRepository.zoekBijlagesBijSchade(schade)).andReturn(bijlages);
        expect(schade.getBijlages()).andReturn(bijlageSet);

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

        expect(schade.getId()).andReturn(58L);
        expect(schadeRepository.lees(58L)).andReturn(schade);
        expect(schade.getOpmerkingen()).andReturn(new HashSet<Opmerking>());
        schade.setOpmerkingen(new HashSet<Opmerking>());
        expectLastCall();

        Set<Bijlage> bijlageSet = new HashSet<>();
        List<Bijlage> bijlages = new ArrayList<>();
        expect(schadeRepository.zoekBijlagesBijSchade(schade)).andReturn(bijlages);
        expect(schade.getBijlages()).andReturn(bijlageSet);

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
        bijlage.setOmschrijving("Omschrijving");

        expect(schadeRepository.lees(schadeId)).andReturn(schade);

        schadeRepository.opslaanBijlage(bijlage);
        expectLastCall();

        replayAll();

        service.slaBijlageOp(schadeId, bijlage, "Omschrijving");

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
