package nl.dias.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

import nl.dias.domein.Beheerder;
import nl.dias.domein.Kantoor;
import nl.dias.domein.Medewerker;
import nl.dias.domein.Relatie;
import nl.dias.domein.Sessie;
import nl.lakedigital.loginsystem.exception.NietGevondenException;

import org.joda.time.LocalDate;
import org.junit.Before;
import org.junit.Test;

public class GebruikerRepositoryTest {
    private GebruikerRepository gebruikerRepository;

    @Before
    public void setUp() throws Exception {
        gebruikerRepository = new GebruikerRepository();
        gebruikerRepository.setPersistenceContext("unittest");
    }

    @Test
    public void zoek() throws UnsupportedEncodingException, NoSuchAlgorithmException {
        Kantoor kantoor = new Kantoor();
        kantoor.setDatumOprichting(new LocalDate());

        gebruikerRepository.getEm().persist(kantoor);

        Medewerker medewerker = new Medewerker();
        medewerker.setIdentificatie("id");
        medewerker.setHashWachtwoord("ww");

        medewerker.setKantoor(kantoor);
        kantoor.getMedewerkers().add(medewerker);

        gebruikerRepository.opslaan(medewerker);

        try {
            assertEquals(medewerker, gebruikerRepository.zoek("id"));
        } catch (NietGevondenException e) {
            fail("Zou gevonden moeten worden");
        }

        try {
            assertEquals(medewerker, gebruikerRepository.zoek("i"));
            fail("Zou niet gevonden mogen worden");
        } catch (NietGevondenException e) {
        }
    }

    @Test
    public void beheerder() throws NietGevondenException, UnsupportedEncodingException, NoSuchAlgorithmException {
        Beheerder beheerder = new Beheerder();
        beheerder.setHashWachtwoord("ww");
        beheerder.setIdentificatie("p@h.n");

        gebruikerRepository.opslaan(beheerder);

        assertEquals(1, gebruikerRepository.alles().size());
        assertEquals(beheerder, gebruikerRepository.zoek("p@h.n"));
    }

    @Test
    public void opSessieEnIp() throws UnsupportedEncodingException, NoSuchAlgorithmException {
        Medewerker medewerker = new Medewerker();
        medewerker.setIdentificatie("identificatie");

        Kantoor kantoor = new Kantoor();
        gebruikerRepository.getEm().persist(kantoor);

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

        gebruikerRepository.opslaan(medewerker);

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

        gebruikerRepository.opslaan(medewerker1);

        try {
            assertEquals(medewerker.getId(), gebruikerRepository.zoekOpSessieEnIpadres("abcde", "12345").getId());
        } catch (NietGevondenException e) {
            fail("niet gevonden");
        }
        try {
            gebruikerRepository.zoekOpSessieEnIpadres("abcdef", "12345");
            fail("zou niet gevonden mogen worden");
        } catch (NietGevondenException e) {
        }
        try {
            assertEquals(medewerker.getId(), gebruikerRepository.zoekOpSessieEnIpadres("abcde", "123456").getId());
            fail("zou niet gevonden mogen worden");
        } catch (NietGevondenException e) {
        }
        try {
            assertEquals(medewerker.getId(), gebruikerRepository.zoekOpSessieEnIpadres("cdefg", "3456").getId());
        } catch (NietGevondenException e) {
            fail("niet gevonden");
        }
    }

    @Test
    public void testAlleRelaties() {
        Kantoor kantoor1 = new Kantoor();
        Kantoor kantoor2 = new Kantoor();

        opslaan(kantoor1);
        opslaan(kantoor2);

        Relatie relatie1 = new Relatie();
        Relatie relatie2 = new Relatie();
        Relatie relatie3 = new Relatie();

        kantoor1.getRelaties().add(relatie1);
        kantoor1.getRelaties().add(relatie2);
        kantoor2.getRelaties().add(relatie3);
        relatie1.setKantoor(kantoor1);
        relatie2.setKantoor(kantoor1);
        relatie3.setKantoor(kantoor2);

        opslaan(relatie1);
        opslaan(relatie2);
        opslaan(relatie3);

        assertEquals(2, gebruikerRepository.alleRelaties(kantoor1).size());
        assertEquals(1, gebruikerRepository.alleRelaties(kantoor2).size());
        assertEquals(3, gebruikerRepository.alleRelaties().size());
    }

    // @Test
    // @Ignore
    // public void opCookie() {
    // Medewerker medewerker = new Medewerker();
    // medewerker.setIdentificatie("identificatie");
    //
    // Kantoor kantoor = new Kantoor();
    // gebruikerRepository.getEm().persist(kantoor);
    // medewerker.setKantoor(kantoor);
    //
    // Sessie sessie = new Sessie();
    // sessie.setGebruiker(medewerker);
    // sessie.setCookieCode("abc");
    // medewerker.getSessies().add(sessie);
    //
    // Sessie sessie1 = new Sessie();
    // sessie1.setGebruiker(medewerker);
    // sessie1.setCookieCode("def");
    // medewerker.getSessies().add(sessie1);
    //
    // gebruikerRepository.opslaan(medewerker);
    //
    // Medewerker medewerker1 = new Medewerker();
    // medewerker1.setIdentificatie("identificatie1");
    //
    // medewerker1.setKantoor(kantoor);
    //
    // Sessie sessie2 = new Sessie();
    // sessie2.setGebruiker(medewerker1);
    // sessie2.setCookieCode("ghi");
    // medewerker1.getSessies().add(sessie2);
    //
    // Sessie sessie3 = new Sessie();
    // sessie3.setGebruiker(medewerker1);
    // sessie3.setCookieCode("jkl");
    // medewerker1.getSessies().add(sessie3);
    //
    // gebruikerRepository.opslaan(medewerker1);
    //
    // try {
    // assertEquals(medewerker.getId(),
    // gebruikerRepository.zoekOpCookieCode("abc").getId());
    // } catch (NietGevondenException e) {
    // fail("niet gevonden");
    // }
    // try {
    // assertEquals(medewerker.getId(),
    // gebruikerRepository.zoekOpCookieCode("cde").getId());
    // fail("zou niet gevonden mogen worden");
    // } catch (NietGevondenException e) {
    // }
    // try {
    // assertEquals(medewerker.getId(),
    // gebruikerRepository.zoekOpCookieCode("def").getId());
    // } catch (NietGevondenException e) {
    // fail("niet gevonden");
    // }
    //
    // }
    //
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

    private void opslaan(Object object) {
        gebruikerRepository.getEm().getTransaction().begin();
        gebruikerRepository.getEm().persist(object);
        gebruikerRepository.getEm().getTransaction().commit();
    }

    private void update(Object object) {
        gebruikerRepository.getEm().getTransaction().begin();
        gebruikerRepository.getEm().merge(object);
        gebruikerRepository.getEm().getTransaction().commit();
    }
}
