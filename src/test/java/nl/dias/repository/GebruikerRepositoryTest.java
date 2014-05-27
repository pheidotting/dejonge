package nl.dias.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import nl.dias.domein.Beheerder;
import nl.dias.domein.Kantoor;
import nl.dias.domein.Medewerker;
import nl.dias.domein.Relatie;
import nl.dias.domein.Sessie;
import nl.lakedigital.loginsystem.exception.NietGevondenException;

import org.joda.time.LocalDate;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

public class GebruikerRepositoryTest {
    private GebruikerRepository gebruikerService;
    private KantoorRepository kantoorService;

    @Before
    public void setUp() throws Exception {
        gebruikerService = new GebruikerRepository();
        gebruikerService.zetPersistenceContext("unittest");

        kantoorService = new KantoorRepository();
        kantoorService.zetPersistenceContext("unittest");
    }

    @Test
    public void zoek() {
        Kantoor kantoor = new Kantoor();
        kantoor.setDatumOprichting(new LocalDate());

        gebruikerService.getEm().persist(kantoor);

        Medewerker medewerker = new Medewerker();
        medewerker.setIdentificatie("id");
        medewerker.setHashWachtwoord("ww");

        medewerker.setKantoor(kantoor);
        kantoor.getMedewerkers().add(medewerker);

        gebruikerService.opslaan(medewerker);

        try {
            assertEquals(medewerker, gebruikerService.zoek("id"));
        } catch (NietGevondenException e) {
            fail("Zou gevonden moeten worden");
        }

        try {
            assertEquals(medewerker, gebruikerService.zoek("i"));
            fail("Zou niet gevonden mogen worden");
        } catch (NietGevondenException e) {
        }
    }

    @Test
    public void alleRelaties() {
        Medewerker medewerker = new Medewerker();
        Relatie relatie = new Relatie();
        Beheerder beheerder = new Beheerder();

        gebruikerService.opslaan(medewerker);
        gebruikerService.opslaan(relatie);
        gebruikerService.opslaan(beheerder);

        assertEquals(1, gebruikerService.alleRelaties().size());
        assertTrue(gebruikerService.alleRelaties().get(0) instanceof Relatie);
    }

    @Test
    public void beheerder() throws NietGevondenException {
        Beheerder beheerder = new Beheerder();
        beheerder.setHashWachtwoord("ww");
        beheerder.setIdentificatie("p@h.n");

        gebruikerService.opslaan(beheerder);

        assertEquals(1, gebruikerService.alles().size());
        assertEquals(beheerder, gebruikerService.zoek("p@h.n"));
    }

    @Test
    public void opSessieEnIp() {
        Medewerker medewerker = new Medewerker();
        medewerker.setIdentificatie("identificatie");

        Kantoor kantoor = new Kantoor();
        gebruikerService.getEm().persist(kantoor);

        medewerker.setKantoor(kantoor);

        Sessie sessie = new Sessie();
        sessie.setGebruiker(medewerker);
        sessie.setIpadres("12345");
        sessie.setSessie("abcde");
        medewerker.getSessies().add(sessie);

        Sessie sessie1 = new Sessie();
        sessie1.setGebruiker(medewerker);
        sessie1.setIpadres("3456");
        sessie1.setSessie("cdefg");
        medewerker.getSessies().add(sessie1);

        gebruikerService.opslaan(medewerker);

        Medewerker medewerker1 = new Medewerker();
        medewerker1.setIdentificatie("identificatie1");

        medewerker1.setKantoor(kantoor);

        Sessie sessie2 = new Sessie();
        sessie2.setGebruiker(medewerker1);
        sessie2.setIpadres("67890");
        sessie2.setSessie("efghi");
        medewerker1.getSessies().add(sessie2);

        Sessie sessie3 = new Sessie();
        sessie3.setGebruiker(medewerker1);
        sessie3.setIpadres("789012");
        sessie3.setSessie("jklmn");
        medewerker1.getSessies().add(sessie3);

        gebruikerService.opslaan(medewerker1);

        try {
            assertEquals(medewerker.getId(), gebruikerService.zoekOpSessieEnIpadres("abcde", "12345").getId());
        } catch (NietGevondenException e) {
            fail("niet gevonden");
        }
        try {
            gebruikerService.zoekOpSessieEnIpadres("abcdef", "12345");
            fail("zou niet gevonden mogen worden");
        } catch (NietGevondenException e) {
        }
        try {
            assertEquals(medewerker.getId(), gebruikerService.zoekOpSessieEnIpadres("abcde", "123456").getId());
            fail("zou niet gevonden mogen worden");
        } catch (NietGevondenException e) {
        }
        try {
            assertEquals(medewerker.getId(), gebruikerService.zoekOpSessieEnIpadres("cdefg", "3456").getId());
        } catch (NietGevondenException e) {
            fail("niet gevonden");
        }
    }

    @Test
    @Ignore
    public void opCookie() {
        Medewerker medewerker = new Medewerker();
        medewerker.setIdentificatie("identificatie");

        Kantoor kantoor = new Kantoor();
        gebruikerService.getEm().persist(kantoor);
        medewerker.setKantoor(kantoor);

        Sessie sessie = new Sessie();
        sessie.setGebruiker(medewerker);
        sessie.setCookieCode("abc");
        medewerker.getSessies().add(sessie);

        Sessie sessie1 = new Sessie();
        sessie1.setGebruiker(medewerker);
        sessie1.setCookieCode("def");
        medewerker.getSessies().add(sessie1);

        gebruikerService.opslaan(medewerker);

        Medewerker medewerker1 = new Medewerker();
        medewerker1.setIdentificatie("identificatie1");

        medewerker1.setKantoor(kantoor);

        Sessie sessie2 = new Sessie();
        sessie2.setGebruiker(medewerker1);
        sessie2.setCookieCode("ghi");
        medewerker1.getSessies().add(sessie2);

        Sessie sessie3 = new Sessie();
        sessie3.setGebruiker(medewerker1);
        sessie3.setCookieCode("jkl");
        medewerker1.getSessies().add(sessie3);

        gebruikerService.opslaan(medewerker1);

        try {
            assertEquals(medewerker.getId(), gebruikerService.zoekOpCookieCode("abc").getId());
        } catch (NietGevondenException e) {
            fail("niet gevonden");
        }
        try {
            assertEquals(medewerker.getId(), gebruikerService.zoekOpCookieCode("cde").getId());
            fail("zou niet gevonden mogen worden");
        } catch (NietGevondenException e) {
        }
        try {
            assertEquals(medewerker.getId(), gebruikerService.zoekOpCookieCode("def").getId());
        } catch (NietGevondenException e) {
            fail("niet gevonden");
        }

    }

    // @Test
    // public void patrick() {
    // Beheerder beheerder = new Beheerder();
    // beheerder.setAchternaam("Jonge");
    // beheerder.setVoornaam("Bene");
    // beheerder.setTussenvoegsel("de");
    // beheerder.setIdentificatie("Bene@dejongefinancieelconsult.nl");
    // beheerder.setHashWachtwoord("welkom");
    //
    // gebruikerService.opslaan(beheerder);
    // }
}
