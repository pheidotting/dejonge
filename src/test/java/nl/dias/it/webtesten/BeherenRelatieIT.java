package nl.dias.it.webtesten;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import nl.dias.dias_web.hulp.Hulp;
import nl.dias.domein.json.JsonRekeningNummer;
import nl.dias.domein.json.JsonRelatie;
import nl.dias.domein.json.JsonTelefoonnummer;
import nl.dias.it.webtesten.util.StringGeneratieUtil;
import nl.dias.web.pagina.BeherenRelatie;
import nl.dias.web.pagina.BeherenRelatieRekeningnummer;
import nl.dias.web.pagina.BeherenRelatieTelefoonnummer;
import nl.dias.web.pagina.InlogScherm;
import nl.dias.web.pagina.LijstRelaties;

import org.joda.time.LocalDate;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.server.SeleniumServer;
import org.openqa.selenium.support.PageFactory;

public class BeherenRelatieIT {
    private SeleniumServer seleniumServer;
    private WebDriver driver;
    private StringGeneratieUtil stringGeneratieUtil;

    @Before
    public void setUp() throws Exception {
        seleniumServer = new SeleniumServer();
        seleniumServer.start();

        driver = new FirefoxDriver();
        stringGeneratieUtil = new StringGeneratieUtil();
    }

    @After
    public void afsluiten() {
        driver.close();
        seleniumServer.stop();
    }

    @Test
    public void test() {
        Hulp.naarAdres(driver, "http://localhost:8080/dejonge/index.html#inloggen");

        InlogScherm inlogScherm = PageFactory.initElements(driver, InlogScherm.class);
        inlogScherm.inloggen("gerben@dejongefinancieelconsult.nla", "");
        assertEquals("Er is een fout opgetreden : gerben@dejongefinancieelconsult.nla werd niet gevonden.", inlogScherm.leesFoutmelding());
        inlogScherm.inloggen("gerben@dejongefinancieelconsult.nl", "g");
        assertEquals("Er is een fout opgetreden : Het ingevoerde wachtwoord is onjuist", inlogScherm.leesFoutmelding());
        inlogScherm.inloggen("gerben@dejongefinancieelconsult.nl", "gerben");

        Hulp.wachtFf();

        LijstRelaties lijstRelaties = PageFactory.initElements(driver, LijstRelaties.class);
        lijstRelaties.toevoegenNieuweRelatie();

        BeherenRelatie beherenRelatieScherm = PageFactory.initElements(driver, BeherenRelatie.class);

        JsonRelatie jsonRelatie = maakJsonRelatie();

        beherenRelatieScherm.vulVeldenEnDrukOpOpslaan(jsonRelatie.getVoornaam(), jsonRelatie.getAchternaam(), jsonRelatie.getTussenvoegsel(), jsonRelatie.getStraat(), jsonRelatie.getHuisnummer()
                .toString(), jsonRelatie.getToevoeging(), jsonRelatie.getPostcode(), jsonRelatie.getPlaats(), jsonRelatie.getBsn(), jsonRelatie.getIdentificatie(), jsonRelatie.getGeboorteDatum(),
                jsonRelatie.getOverlijdensdatum(), jsonRelatie.getGeslacht(), jsonRelatie.getBurgerlijkeStaat(), allJsonRekeningNummerToBeherenRelatieRekeningnummer(jsonRelatie.getRekeningnummers()),
                allJsonTelefoonnummerToBeherenRelatieTelefoonnummer(jsonRelatie.getTelefoonnummers()));

        assertEquals("De gegevens zijn opgeslagen", beherenRelatieScherm.leesmelding());

    }

    private JsonRelatie maakJsonRelatie() {
        JsonRelatie jsonRelatie = new JsonRelatie();

        jsonRelatie.setAchternaam(stringGeneratieUtil.genereerAchternaam());
        jsonRelatie.setBsn(stringGeneratieUtil.genereerBsn());
        jsonRelatie.setIdentificatie(stringGeneratieUtil.genereerEmailAdres());
        jsonRelatie.setTussenvoegsel(stringGeneratieUtil.genereerTussenvoegsel());
        jsonRelatie.setVoornaam(stringGeneratieUtil.genereerVoornaam());
        LocalDate geboorteDatum = stringGeneratieUtil.genereerDatum();
        jsonRelatie.setGeboorteDatum(geboorteDatum.toString("dd-MM-yyyy"));
        jsonRelatie.setOverlijdensdatum(stringGeneratieUtil.genereerDatum(geboorteDatum).toString("dd-MM-yyyy"));

        jsonRelatie.setGeslacht((String) stringGeneratieUtil.kiesUitItems("Man", "Vrouw"));
        jsonRelatie.setBurgerlijkeStaat((String) stringGeneratieUtil.kiesUitItems("Gehuwd", "Ongehuwd"));

        jsonRelatie.setStraat(stringGeneratieUtil.genereerStraatnaam());
        jsonRelatie.setHuisnummer(((Integer) stringGeneratieUtil.randomGetal(200)).toString());
        jsonRelatie.setToevoeging(stringGeneratieUtil.genereerToevoeging());
        jsonRelatie.setPostcode(stringGeneratieUtil.genereerPostcode());
        jsonRelatie.setPlaats(stringGeneratieUtil.genereerPlaatsnaam());
        jsonRelatie.setOnderlingeRelaties(new ArrayList<Long>());
        jsonRelatie.setRekeningnummers(new ArrayList<JsonRekeningNummer>());
        jsonRelatie.getRekeningnummers().add(new JsonRekeningNummer(null, stringGeneratieUtil.genereerBic(), stringGeneratieUtil.genereerIban()));

        jsonRelatie.setTelefoonnummers(new ArrayList<JsonTelefoonnummer>());
        jsonRelatie.getTelefoonnummers().add(new JsonTelefoonnummer(null, stringGeneratieUtil.genereerTelefoonnummer(), (String) stringGeneratieUtil.kiesUitItems("Vast", "Mobiel", "Werk")));

        return jsonRelatie;
    }

    private List<BeherenRelatieRekeningnummer> allJsonRekeningNummerToBeherenRelatieRekeningnummer(List<JsonRekeningNummer> jsonRekeningNummers) {
        List<BeherenRelatieRekeningnummer> beherenRelatieRekeningnummers = new ArrayList<>();

        for (JsonRekeningNummer jsonRekeningNummer : jsonRekeningNummers) {
            beherenRelatieRekeningnummers.add(jsonRekeningNummerToBeherenRelatieRekeningnummer(jsonRekeningNummer));
        }

        return beherenRelatieRekeningnummers;
    }

    private BeherenRelatieRekeningnummer jsonRekeningNummerToBeherenRelatieRekeningnummer(JsonRekeningNummer jsonRekeningNummer) {
        BeherenRelatieRekeningnummer beherenRelatieRekeningnummer = new BeherenRelatieRekeningnummer();

        beherenRelatieRekeningnummer.setBic(jsonRekeningNummer.getBic());
        if (jsonRekeningNummer.getId() != null) {
            beherenRelatieRekeningnummer.setId(jsonRekeningNummer.getId().toString());
        }
        beherenRelatieRekeningnummer.setRekeninnummer(jsonRekeningNummer.getRekeningnummer());

        return beherenRelatieRekeningnummer;
    }

    private List<BeherenRelatieTelefoonnummer> allJsonTelefoonnummerToBeherenRelatieTelefoonnummer(List<JsonTelefoonnummer> jsonTelefoonnummers) {
        List<BeherenRelatieTelefoonnummer> beherenRelatieTelefoonnummers = new ArrayList<>();

        for (JsonTelefoonnummer jsonTelefoonnummer : jsonTelefoonnummers) {
            beherenRelatieTelefoonnummers.add(jsonTelefoonnummerToBeherenRelatieTelefoonnummer(jsonTelefoonnummer));
        }

        return beherenRelatieTelefoonnummers;
    }

    private BeherenRelatieTelefoonnummer jsonTelefoonnummerToBeherenRelatieTelefoonnummer(JsonTelefoonnummer jsonTelefoonnummer) {
        BeherenRelatieTelefoonnummer beherenRelatieTelefoonnummer = new BeherenRelatieTelefoonnummer();

        if (jsonTelefoonnummer.getId() != null) {
            beherenRelatieTelefoonnummer.setId(jsonTelefoonnummer.getId().toString());
        }
        beherenRelatieTelefoonnummer.setSoortTelefoonnummer(jsonTelefoonnummer.getSoort());
        beherenRelatieTelefoonnummer.setTelefoonnummer(jsonTelefoonnummer.getTelefoonnummer());

        return beherenRelatieTelefoonnummer;
    }
}
