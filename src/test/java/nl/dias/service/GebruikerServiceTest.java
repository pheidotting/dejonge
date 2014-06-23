package nl.dias.service;

import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;
import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import nl.dias.domein.Gebruiker;
import nl.dias.domein.Kantoor;
import nl.dias.domein.Medewerker;
import nl.dias.domein.RekeningNummer;
import nl.dias.domein.Relatie;
import nl.dias.domein.Telefoonnummer;
import nl.dias.repository.GebruikerRepository;

import org.easymock.EasyMockSupport;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class GebruikerServiceTest extends EasyMockSupport {
    private GebruikerRepository repository;
    private GebruikerService service;

    @Before
    public void setUp() throws Exception {
        service = new GebruikerService();

        repository = createMock(GebruikerRepository.class);
        service.setGebruikerRepository(repository);
    }

    @After
    public void teardown() {
        verifyAll();
    }

    @Test
    public void testLees() {
        Gebruiker gebruiker = new Relatie();

        expect(repository.lees(1L)).andReturn(gebruiker);

        replayAll();

        assertEquals(gebruiker, service.lees(1L));
    }

    @Test
    public void testAlleRelaties() {
        Kantoor kantoor = new Kantoor();
        Relatie relatie = new Relatie();
        List<Relatie> relaties = new ArrayList<>();
        relaties.add(relatie);

        expect(repository.alleRelaties(kantoor)).andReturn(relaties);

        replayAll();

        assertEquals(relaties, service.alleRelaties(kantoor));
    }

    @Test
    public void testOpslaan() {
        Relatie relatie = new Relatie();

        repository.opslaan(relatie);
        expectLastCall();

        replayAll();

        service.opslaan(relatie);
    }

    @Test
    public void testOpslaanMetRekeningNummer() {
        Relatie relatie = new Relatie();
        relatie.getRekeningnummers().add(new RekeningNummer());

        repository.opslaan(relatie);
        expectLastCall();

        replayAll();

        service.opslaan(relatie);
    }

    @Test
    public void testOpslaanMetTelefoonNummer() {
        Relatie relatie = new Relatie();
        relatie.getTelefoonnummers().add(new Telefoonnummer());

        repository.opslaan(relatie);
        expectLastCall();

        replayAll();

        service.opslaan(relatie);
    }

    @Test
    public void testOpslaanMedewerker() {
        Medewerker medewerker = new Medewerker();

        repository.opslaan(medewerker);
        expectLastCall();

        replayAll();

        service.opslaan(medewerker);
    }

    @Test
    public void testVerwijder() {
        Relatie relatie = new Relatie();

        expect(repository.lees(1L)).andReturn(relatie);
        repository.verwijder(relatie);
        expectLastCall();

        replayAll();

        service.verwijder(1L);
    }
}
