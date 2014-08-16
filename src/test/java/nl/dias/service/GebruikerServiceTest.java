package nl.dias.service;

import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.ArrayList;
import java.util.List;

import nl.dias.domein.Gebruiker;
import nl.dias.domein.Kantoor;
import nl.dias.domein.Medewerker;
import nl.dias.domein.RekeningNummer;
import nl.dias.domein.Relatie;
import nl.dias.domein.Sessie;
import nl.dias.domein.Telefoonnummer;
import nl.dias.repository.GebruikerRepository;
import nl.lakedigital.loginsystem.exception.NietGevondenException;

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

    @Test
    public void testZoek() throws NietGevondenException {
        Medewerker medewerker = new Medewerker();

        expect(repository.zoek("e")).andReturn(medewerker);

        replayAll();

        assertEquals(medewerker, service.zoek("e"));
    }

    @Test
    public void testZoekOpSessieEnIpAdres() throws NietGevondenException {
        String sessie = "sessie";
        String ipadres = "ipadres";

        Medewerker medewerker = new Medewerker();

        expect(repository.zoekOpSessieEnIpadres(sessie, ipadres)).andReturn(medewerker);

        replayAll();

        assertEquals(medewerker, service.zoekOpSessieEnIpAdres(sessie, ipadres));
    }

    @Test
    public void testZoekSessieOpOpSessieEnIpadres() {
        Sessie sessie1 = new Sessie();
        Sessie sessie2 = new Sessie();
        Sessie sessie3 = new Sessie();

        sessie1.setSessie("sessie1");
        sessie1.setIpadres("ipadres1");
        sessie2.setSessie("sessie2");
        sessie2.setIpadres("ipadres2");
        sessie3.setSessie("sessie3");
        sessie3.setIpadres("ipadres3");

        Relatie relatie = new Relatie();
        relatie.getSessies().add(sessie1);
        relatie.getSessies().add(sessie2);
        relatie.getSessies().add(sessie3);

        replayAll();

        assertEquals(sessie1, service.zoekSessieOp("sessie1", "ipadres1", relatie.getSessies()));
        assertEquals(sessie2, service.zoekSessieOp("sessie2", "ipadres2", relatie.getSessies()));
        assertEquals(sessie3, service.zoekSessieOp("sessie3", "ipadres3", relatie.getSessies()));
        assertNull(service.zoekSessieOp("sessie", "ipadres1", relatie.getSessies()));
        assertNull(service.zoekSessieOp("sessie1", "ipadres", relatie.getSessies()));
    }

    @Test
    public void testZoekSessieOpCookie() {
        Sessie sessie1 = new Sessie();
        Sessie sessie2 = new Sessie();
        Sessie sessie3 = new Sessie();

        sessie1.setCookieCode("cookieCode1");
        sessie2.setCookieCode("cookieCode2");
        sessie3.setCookieCode("cookieCode3");

        Relatie relatie = new Relatie();
        relatie.getSessies().add(sessie1);
        relatie.getSessies().add(sessie2);
        relatie.getSessies().add(sessie3);

        replayAll();

        assertEquals(sessie1, service.zoekSessieOp("cookieCode1", relatie.getSessies()));
        assertEquals(sessie2, service.zoekSessieOp("cookieCode2", relatie.getSessies()));
        assertEquals(sessie3, service.zoekSessieOp("cookieCode3", relatie.getSessies()));
        assertNull(service.zoekSessieOp("cookieCode", relatie.getSessies()));
    }
}
