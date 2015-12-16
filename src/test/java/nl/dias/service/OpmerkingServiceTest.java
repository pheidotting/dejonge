package nl.dias.service;

import com.google.common.collect.Lists;
import nl.dias.domein.*;
import nl.dias.domein.polis.FietsVerzekering;
import nl.dias.domein.polis.Polis;
import nl.dias.repository.OpmerkingRepository;
import nl.dias.repository.PolisRepository;
import org.easymock.*;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import static org.easymock.EasyMock.*;
import static org.junit.Assert.assertEquals;

@RunWith(EasyMockRunner.class)
public class OpmerkingServiceTest extends EasyMockSupport {
    @TestSubject
    private OpmerkingService service = new OpmerkingService();

    @Mock
    private AuthorisatieService authorisatieService;
    @Mock
    private OpmerkingRepository repository;
    @Mock
    private SchadeService schadeService;
    @Mock
    private HypotheekService hypotheekService;
    @Mock
    private GebruikerService gebruikerService;
    @Mock
    private PolisRepository polisRepository;
    @Mock
    private BedrijfService bedrijfService;
    @Mock
    private AangifteService aangifteService;


    @After
    public void tearDown() throws Exception {
        verifyAll();
    }

    private Medewerker maakMedewerker() {
        Medewerker medewerker = new Medewerker();
        medewerker.setVoornaam("Jason");
        medewerker.setAchternaam("Voorhees");

        return medewerker;
    }

    @Test
    public void testOpslaanOpmerkingBijRelatie() {
        Opmerking opmerking = new Opmerking();
        Relatie relatie = new Relatie();
        relatie.setId(46L);
        Medewerker medewerker = maakMedewerker();
        opmerking.setMedewerker(medewerker);

        opmerking.setRelatie(relatie);

        repository.opslaan(opmerking);
        expectLastCall();

        expect(gebruikerService.lees(relatie.getId())).andReturn(relatie);

        Capture<Relatie> relatieCapture = newCapture();
        gebruikerService.opslaan(capture(relatieCapture));
        expectLastCall();

        replayAll();

        service.opslaan(opmerking);

        assertEquals(1, relatieCapture.getValue().getOpmerkingen().size());
    }

    @Test
    public void testOpslaanOpmerkingBijSchade() {
        Opmerking opmerking = new Opmerking();
        Schade schade = new Schade();
        schade.setId(46L);
        Medewerker medewerker = maakMedewerker();
        opmerking.setMedewerker(medewerker);

        opmerking.setSchade(schade);

        repository.opslaan(opmerking);
        expectLastCall();

        expect(schadeService.lees(schade.getId())).andReturn(schade);

        Capture<Schade> schadeCapture = newCapture();
        schadeService.opslaan(capture(schadeCapture));
        expectLastCall();

        replayAll();

        service.opslaan(opmerking);

        assertEquals(1, schadeCapture.getValue().getOpmerkingen().size());
    }

    @Test
    public void testOpslaanOpmerkingBijHypotheek() {
        Opmerking opmerking = new Opmerking();
        Hypotheek hypotheek = new Hypotheek();
        hypotheek.setId(46L);
        Medewerker medewerker = maakMedewerker();
        opmerking.setMedewerker(medewerker);

        opmerking.setHypotheek(hypotheek);

        repository.opslaan(opmerking);
        expectLastCall();

        expect(hypotheekService.leesHypotheek(hypotheek.getId())).andReturn(hypotheek);

        Capture<Hypotheek> hypotheekCapture = newCapture();
        hypotheekService.opslaan(capture(hypotheekCapture));
        expectLastCall();

        replayAll();

        service.opslaan(opmerking);

        assertEquals(1, hypotheekCapture.getValue().getOpmerkingen().size());
    }

    @Test
    public void testOpslaanOpmerkingBijPolis() {
        Opmerking opmerking = new Opmerking();
        Polis polis = new FietsVerzekering();
        polis.setId(46L);
        Medewerker medewerker = maakMedewerker();
        opmerking.setMedewerker(medewerker);

        opmerking.setPolis(polis);

        repository.opslaan(opmerking);
        expectLastCall();

        expect(polisRepository.lees(polis.getId())).andReturn(polis);

        Capture<Polis> polisCapture = newCapture();
        polisRepository.opslaan(capture(polisCapture));
        expectLastCall();

        replayAll();

        service.opslaan(opmerking);

        assertEquals(1, polisCapture.getValue().getOpmerkingen().size());
    }

    @Test
    public void testOpslaanOpmerkingBijBedrijf() {
        Opmerking opmerking = new Opmerking();
        Bedrijf bedrijf = new Bedrijf();
        bedrijf.setId(46L);
        Medewerker medewerker = maakMedewerker();
        opmerking.setMedewerker(medewerker);

        opmerking.setBedrijf(bedrijf);

        repository.opslaan(opmerking);
        expectLastCall();

        expect(bedrijfService.lees(bedrijf.getId())).andReturn(bedrijf);

        Capture<Bedrijf> bedrijfCapture = newCapture();
        bedrijfService.opslaan(capture(bedrijfCapture));
        expectLastCall();

        replayAll();

        service.opslaan(opmerking);

        assertEquals(1, bedrijfCapture.getValue().getOpmerkingen().size());
    }

    @Test
    public void testOpslaanOpmerkingBijAangifte() {
        Opmerking opmerking = new Opmerking();
        Aangifte aangifte = new Aangifte();
        aangifte.setId(46L);
        Medewerker medewerker = maakMedewerker();
        opmerking.setMedewerker(medewerker);

        opmerking.setAangifte(aangifte);

        repository.opslaan(opmerking);
        expectLastCall();

        expect(aangifteService.lees(aangifte.getId())).andReturn(aangifte);

        Capture<Aangifte> aangifteCapture = newCapture();
        aangifteService.opslaan(capture(aangifteCapture));
        expectLastCall();

        replayAll();

        service.opslaan(opmerking);

        assertEquals(1, aangifteCapture.getValue().getOpmerkingen().size());
    }

    @Test
    public void testVerwijder(){
        Opmerking opmerking=new Opmerking();
        Long id = 46L;

        expect(repository.lees(id)).andReturn(opmerking);

        repository.verwijder(opmerking);expectLastCall();

        replayAll();

        service.verwijder(id);
    }

    @Test
    public void testAlleOpmerkingenVoorRelatie() {
        Long relatieId = 58L;

        Relatie relatie = new Relatie();
        Opmerking opmerking = new Opmerking();
        List<Opmerking> opmerkingen = Lists.newArrayList(opmerking);

        expect(gebruikerService.lees(relatieId)).andReturn(relatie);
        expect(repository.alleOpmerkingenVoorRelatie(relatie)).andReturn(opmerkingen);

        replayAll();

        assertEquals(opmerkingen, service.alleOpmerkingenVoorRelatie(relatieId));
    }
}
