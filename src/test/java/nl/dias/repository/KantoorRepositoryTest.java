package nl.dias.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import nl.dias.domein.Kantoor;
import nl.dias.domein.Medewerker;
import nl.dias.domein.Rechtsvorm;
import nl.dias.domein.RekeningNummer;
import nl.dias.domein.SoortKantoor;
import nl.dias.exception.BsnNietGoedException;
import nl.dias.exception.IbanNietGoedException;
import nl.dias.exception.PostcodeNietGoedException;
import nl.dias.exception.TelefoonnummerNietGoedException;
import nl.dias.repository.KantoorRepository;

import org.joda.time.LocalDate;
import org.junit.Before;
import org.junit.Test;

public class KantoorRepositoryTest {
    private KantoorRepository kantoorService;

    @Before
    public void init() {
        kantoorService = new KantoorRepository();
        kantoorService.zetPersistenceContext("unittest");
    }

    @Test
    public void test() {
        Kantoor kantoor = new Kantoor();
        kantoor.setNaam("Patrick's mooie kantoortje");
        kantoor.getAdres().setHuisnummer(46L);
        kantoor.getAdres().setPlaats("Plaaaaaaaaaaaaaats");
        kantoor.getAdres().setPostcode("1234BB");
        kantoor.getAdres().setStraat("Straaaaaaaaaaaaaaaaat");
        kantoor.getAdres().setToevoeging("toevoeging");
        kantoor.setKvk(138383838L);
        kantoor.setBtwNummer("btwNummer");
        kantoor.setDatumOprichting(new LocalDate());
        kantoor.setEmailadres("patrick@heidotting.nl");
        kantoor.setRechtsvorm(Rechtsvorm.EENM);
        kantoor.setSoortKantoor(SoortKantoor.HYP);

        kantoor.getFactuurAdres().setHuisnummer(58L);
        kantoor.getFactuurAdres().setPlaats("Ploats");
        kantoor.getFactuurAdres().setPostcode("2345JJ");
        kantoor.getFactuurAdres().setStraat("Stroate");
        kantoor.getFactuurAdres().setToevoeging("toevoeg");

        Medewerker medewerker = new Medewerker();
        medewerker.setIdentificatie("pp");
        medewerker.setHashWachtwoord("hh");
        medewerker.setKantoor(kantoor);

        kantoor.getMedewerkers().add(medewerker);

        RekeningNummer rekeningNummer = new RekeningNummer();
        rekeningNummer.setBic("bic");
        rekeningNummer.setRekeningnummer("NL96SNSB0907007406");

        kantoor.getRekeningnummers().add(rekeningNummer);

        RekeningNummer rekeningNummer1 = new RekeningNummer();
        rekeningNummer1.setBic("bic");
        rekeningNummer1.setRekeningnummer("NL96SNSB0907007406");

        kantoor.getRekeningnummers().add(rekeningNummer1);

        try {
            kantoorService.opslaanKantoor(kantoor);
        } catch (PostcodeNietGoedException | TelefoonnummerNietGoedException | BsnNietGoedException | IbanNietGoedException e) {
            fail();
        }

        assertEquals(1, kantoorService.alles().size());

        kantoor.setNaam("Toch niet zo heel mooi dit kantoor");

        try {
            kantoorService.opslaanKantoor(kantoor);
        } catch (PostcodeNietGoedException | TelefoonnummerNietGoedException | BsnNietGoedException | IbanNietGoedException e) {
            fail();
        }

        assertEquals(1, kantoorService.alles().size());

        kantoorService.verwijder(kantoor);

        assertEquals(0, kantoorService.alles().size());
    }
}
