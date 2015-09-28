package nl.dias.service;

import nl.dias.domein.*;
import nl.dias.repository.HypotheekPakketRepository;
import nl.dias.repository.HypotheekRepository;
import nl.dias.web.mapper.HypotheekMapper;
import org.easymock.EasyMockRunner;
import org.easymock.EasyMockSupport;
import org.easymock.Mock;
import org.easymock.TestSubject;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;
import static org.junit.Assert.assertEquals;

@RunWith(EasyMockRunner.class)
public class HypotheekServiceTest extends EasyMockSupport {
    @TestSubject
    private HypotheekService service = new HypotheekService();
    @Mock
    private HypotheekRepository repository;
    @Mock
    private GebruikerService gebruikerService;
    @Mock
    private HypotheekPakketRepository hypotheekPakketRepository;
    @Mock
    private HypotheekMapper hypotheekMapper;


    @After
    public void tearDown() throws Exception {
        verifyAll();
    }

    @Test
    public void testOpslaan() {
        Hypotheek hypotheek = createMock(Hypotheek.class);

        repository.opslaan(hypotheek);
        expectLastCall();

        replayAll();

        service.opslaan(hypotheek);
    }

    // @Test
    // public void testOpslaanMetGekoppeldeHypotheekNieuw() {
    // String hypotheekVorm = "2";
    // Long relatieId = 58L;
    // Long gekoppeldeHypotheekId = 46L;
    // Long bankId = 69L;
    // Hypotheek gekoppeldeHypotheek = createMock(Hypotheek.class);
    // HypotheekPakket pakket = createMock(HypotheekPakket.class);
    // Relatie relatie = createMock(Relatie.class);
    // SoortHypotheek soortHypotheek = createMock(SoortHypotheek.class);
    // Bank bank = createMock(Bank.class);
    //
    // expect(bankService.lees(bankId)).andReturn(bank);
    // expect(gebruikerService.lees(relatieId)).andReturn(relatie);
    // expect(repository.leesSoortHypotheek(Long.valueOf(hypotheekVorm))).andReturn(soortHypotheek);
    //
    // Hypotheek hypotheek = createMock(Hypotheek.class);
    // JsonHypotheek jsonHypotheek = createMock(JsonHypotheek.class);
    // expect(hypotheekMapper.mapVanJson(jsonHypotheek,
    // hypotheek)).andReturn(hypotheek);
    // expect(jsonHypotheek.getId()).andReturn(46L).times(2);
    // hypotheek.setRelatie(relatie);
    // expectLastCall();
    // hypotheek.setHypotheekVorm(soortHypotheek);
    // expectLastCall();
    // hypotheek.setHypotheekPakket(pakket);
    // expectLastCall();
    // hypotheek.setBank(bank);
    // expectLastCall();
    //
    // expect(repository.lees(gekoppeldeHypotheekId)).andReturn(gekoppeldeHypotheek);
    // expect(gekoppeldeHypotheek.getHypotheekPakket()).andReturn(pakket).times(2);
    // expect(pakket.getHypotheken()).andReturn(new HashSet<Hypotheek>());
    //
    // hypotheekPakketRepository.opslaan(pakket);
    // expectLastCall();
    //
    // repository.opslaan(hypotheek);
    // expectLastCall();
    //
    // replayAll();
    //
    // service.opslaan(jsonHypotheek, hypotheekVorm, relatieId,
    // gekoppeldeHypotheekId, bankId);
    // }

    // @Test
    // public void testOpslaanMetGekoppeldeHypotheekAlGekoppeld() {
    // String hypotheekVorm = "2";
    // Long relatieId = 58L;
    // Long gekoppeldeHypotheekId = 46L;
    // Long bankId = 69L;
    // Hypotheek gekoppeldeHypotheek = createMock(Hypotheek.class);
    // HypotheekPakket pakket = createMock(HypotheekPakket.class);
    // Relatie relatie = createMock(Relatie.class);
    // SoortHypotheek soortHypotheek = createMock(SoortHypotheek.class);
    // Bank bank = createMock(Bank.class);
    //
    // expect(bankService.lees(bankId)).andReturn(bank);
    // expect(gebruikerService.lees(relatieId)).andReturn(relatie);
    // expect(repository.leesSoortHypotheek(Long.valueOf(hypotheekVorm))).andReturn(soortHypotheek);
    //
    // Hypotheek hypotheek = createMock(Hypotheek.class);
    // JsonHypotheek jsonHypotheek = createMock(JsonHypotheek.class);
    // hypotheek.setRelatie(relatie);
    // expectLastCall();
    // hypotheek.setHypotheekVorm(soortHypotheek);
    // expectLastCall();
    // hypotheek.setHypotheekPakket(pakket);
    // expectLastCall();
    // hypotheek.setBank(bank);
    // expectLastCall();
    //
    // expect(repository.lees(gekoppeldeHypotheekId)).andReturn(gekoppeldeHypotheek);
    // expect(gekoppeldeHypotheek.getHypotheekPakket()).andReturn(pakket).times(2);
    // Set<Hypotheek> hypotheken = new HashSet<Hypotheek>();
    // hypotheken.add(gekoppeldeHypotheek);
    // expect(pakket.getHypotheken()).andReturn(hypotheken);
    //
    // hypotheekPakketRepository.opslaan(pakket);
    // expectLastCall();
    //
    // repository.opslaan(hypotheek);
    // expectLastCall();
    //
    // replayAll();
    //
    // service.opslaan(jsonHypotheek, hypotheekVorm, relatieId,
    // gekoppeldeHypotheekId, bankId);
    // }

    @Test
    public void testLeesHypotheek() {
        Long id = 46L;
        Hypotheek hypotheek = createMock(Hypotheek.class);

        expect(repository.lees(id)).andReturn(hypotheek);

        replayAll();

        assertEquals(hypotheek, service.leesHypotheek(id));
    }

    @Test
    public void testLeesHypotheekPakket() {
        Long id = 46L;
        HypotheekPakket hypotheekPakket = createMock(HypotheekPakket.class);

        expect(hypotheekPakketRepository.lees(id)).andReturn(hypotheekPakket);

        replayAll();

        assertEquals(hypotheekPakket, service.leesHypotheekPakket(id));
    }

    @Test
    public void testAlleSoortenHypotheekInGebruik() {
        List<SoortHypotheek> soorten = new ArrayList<>();

        expect(repository.alleSoortenHypotheekInGebruik()).andReturn(soorten);

        replayAll();

        assertEquals(soorten, service.alleSoortenHypotheekInGebruik());
    }

    @Test
    public void allesVanRelatie() {
        Long relatieId = 58L;

        List<Hypotheek> lijst = new ArrayList<Hypotheek>();

        Relatie relatie = createMock(Relatie.class);
        expect(gebruikerService.lees(relatieId)).andReturn(relatie);

        expect(repository.allesVanRelatie(relatie)).andReturn(lijst);

        replayAll();

        assertEquals(lijst, service.allesVanRelatie(relatieId));
    }

    @Test
    public void allesVanRelatieInclDePakketten() {
        Long relatieId = 58L;

        List<Hypotheek> lijst = new ArrayList<Hypotheek>();

        Relatie relatie = createMock(Relatie.class);
        expect(gebruikerService.lees(relatieId)).andReturn(relatie);

        expect(repository.allesVanRelatieInclDePakketten(relatie)).andReturn(lijst);

        replayAll();

        assertEquals(lijst, service.allesVanRelatieInclDePakketten(relatieId));
    }

    @Test
    public void allePakketenVanRelatie() {
        Long relatieId = 58L;

        List<HypotheekPakket> lijst = new ArrayList<HypotheekPakket>();

        Relatie relatie = createMock(Relatie.class);
        expect(gebruikerService.lees(relatieId)).andReturn(relatie);

        expect(hypotheekPakketRepository.allesVanRelatie(relatie)).andReturn(lijst);

        replayAll();

        assertEquals(lijst, service.allePakketenVanRelatie(relatieId));
    }

    @Test
    public void testLeesSoortHypotheek() {
        SoortHypotheek soortHypotheek = createMock(SoortHypotheek.class);

        expect(repository.leesSoortHypotheek(2L)).andReturn(soortHypotheek);

        replayAll();

        assertEquals(soortHypotheek, service.leesSoortHypotheek(2L));
    }

    @Test
    public void testSlaBijlageOp() {
        // Bijlage bijlage = createMock(Bijlage.class);
        Hypotheek hypotheek = createMock(Hypotheek.class);

        expect(repository.lees(3L)).andReturn(hypotheek);

        expect(hypotheek.getId()).andReturn(4L);

        Bijlage bijlage = new Bijlage();
        bijlage.setHypotheek(hypotheek);
        bijlage.setSoortBijlage(SoortBijlage.HYPOTHEEK);
        bijlage.setS3Identificatie("s3Identificatie");
        bijlage.setOmschrijving("omschrijving");

        repository.opslaanBijlage(bijlage);
        expectLastCall();

        replayAll();

        service.slaBijlageOp(3L, "s3Identificatie", "omschrijving");
    }

}
