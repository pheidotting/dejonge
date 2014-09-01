package nl.dias.it.webtesten;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import nl.dias.dias_web.hulp.Hulp;
import nl.dias.domein.json.JsonBedrijf;
import nl.dias.domein.json.JsonPolis;
import nl.dias.domein.json.JsonRekeningNummer;
import nl.dias.domein.json.JsonRelatie;
import nl.dias.domein.json.JsonTelefoonnummer;
import nl.dias.domein.polis.Betaalfrequentie;
import nl.dias.it.webtesten.util.StringGeneratieUtil;
import nl.dias.web.pagina.BedrijfBewerken;
import nl.dias.web.pagina.BeherenRelatie;
import nl.dias.web.pagina.BeherenRelatieRekeningnummer;
import nl.dias.web.pagina.BeherenRelatieTelefoonnummer;
import nl.dias.web.pagina.InlogScherm;
import nl.dias.web.pagina.LijstRelaties;
import nl.dias.web.pagina.PaginaMetMenuBalk.MenuItem;
import nl.dias.web.pagina.PolisBewerken;

import org.apache.commons.lang3.StringUtils;
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

    private final String BASIS_URL = "http://46.17.3.242:57525/dejonge/index.html#";

    private boolean doorgaan() {
        boolean doorgaan = true;
        if (System.getenv("webtesten") != null) {
            if (System.getenv("webtesten").equals("false")) {
                doorgaan = false;
            }
        }

        return doorgaan;
    }

    @Before
    public void setUp() throws Exception {
        if (doorgaan()) {
            seleniumServer = new SeleniumServer();
            seleniumServer.start();

            driver = new FirefoxDriver();
            stringGeneratieUtil = new StringGeneratieUtil();
        }
    }

    @After
    public void afsluiten() {
        if (doorgaan()) {
            driver.close();
            seleniumServer.stop();
        }
    }

    @Test
    public void test() {
        if (doorgaan()) {
            Hulp.naarAdres(driver, BASIS_URL + "inloggen");

            InlogScherm inlogScherm = PageFactory.initElements(driver, InlogScherm.class);

            // fout bij inloggen, controleren op foute gebruikersnaam
            inlogScherm.inloggen("gerben@dejongefinancieelconsult.nla", "");

            if (StringUtils.isEmpty(inlogScherm.leesmelding()) && StringUtils.isEmpty(inlogScherm.leesFoutmelding())) {
                driver.navigate().refresh();
                inlogScherm.inloggen("gerben@dejongefinancieelconsult.nla", "");
            }

            assertEquals("Er is een fout opgetreden : gerben@dejongefinancieelconsult.nla werd niet gevonden.", inlogScherm.leesFoutmelding());

            // Testen op fout wachtwoord
            inlogScherm.inloggen("gerben@dejongefinancieelconsult.nl", "g");

            assertEquals("Er is een fout opgetreden : Het ingevoerde wachtwoord is onjuist", inlogScherm.leesFoutmelding());

            // Nu echt inloggen
            inlogScherm.inloggen("gerben@dejongefinancieelconsult.nl", "gerben");

            Hulp.wachtFf();

            LijstRelaties lijstRelaties = PageFactory.initElements(driver, LijstRelaties.class);
            Hulp.wachtFf();
            lijstRelaties.toevoegenNieuweRelatie();
            Hulp.wachtFf();

            BeherenRelatie beherenRelatieScherm = PageFactory.initElements(driver, BeherenRelatie.class);

            JsonRelatie jsonRelatie = maakJsonRelatie();

            beherenRelatieScherm.vulVeldenEnDrukOpOpslaan(jsonRelatie.getVoornaam(), jsonRelatie.getAchternaam(), jsonRelatie.getTussenvoegsel(), jsonRelatie.getStraat(), jsonRelatie.getHuisnummer()
                    .toString(), jsonRelatie.getToevoeging(), jsonRelatie.getPostcode(), jsonRelatie.getPlaats(), jsonRelatie.getBsn(), jsonRelatie.getIdentificatie(), jsonRelatie.getGeboorteDatum(),
                    jsonRelatie.getOverlijdensdatum(), jsonRelatie.getGeslacht(), jsonRelatie.getBurgerlijkeStaat(), allJsonRekeningNummerToBeherenRelatieRekeningnummer(jsonRelatie
                            .getRekeningnummers()), allJsonTelefoonnummerToBeherenRelatieTelefoonnummer(jsonRelatie.getTelefoonnummers()));

            checkOpgeslagenMelding(beherenRelatieScherm);

            // Opgeslagen Relatie weer aanklikken op overzichtsscherm
            assertTrue(lijstRelaties.zoekRelatieOpEnKlikDezeAan(jsonRelatie));

            Hulp.wachtFf(2000);

            // Checken of velden correct worden weergegeven (en dus of ze
            // correct zijn opgeslagen)
            assertEquals("", beherenRelatieScherm.checkVelden(jsonRelatie));

            beherenRelatieScherm.klikMenuItemAan(MenuItem.POLIS);

            JsonPolis polis = maakJsonPolis(null);

            PolisBewerken polisScherm = PageFactory.initElements(driver, PolisBewerken.class);
            assertFalse(polisScherm.isBedrijfBijPolisZichtbaar());

            polisScherm.vulVeldenEnDrukOpOpslaan(polis);
            checkOpgeslagenMelding(beherenRelatieScherm);

            assertTrue(driver.getCurrentUrl().endsWith("/polissen"));
            checkOpgeslagenMelding(beherenRelatieScherm);

            // We gaan een bedrijf invoeren
            beherenRelatieScherm.klikMenuItemAan(MenuItem.BEDRIJF);
            BedrijfBewerken bedrijfScherm = PageFactory.initElements(driver, BedrijfBewerken.class);
            JsonBedrijf jsonBedrijf = maakJsonBedrijf();

            bedrijfScherm.vulVeldenEnDrukOpOpslaan(jsonBedrijf);

            checkOpgeslagenMelding(beherenRelatieScherm);
            assertTrue(driver.getCurrentUrl().endsWith("/bedrijven"));

            // En een polis invoeren bij dit bedrijf
            beherenRelatieScherm.klikMenuItemAan(MenuItem.POLIS);
            assertTrue(polisScherm.isBedrijfBijPolisZichtbaar());
            JsonPolis polis2 = maakJsonPolis(jsonBedrijf.getNaam());
            polisScherm.vulVeldenEnDrukOpOpslaan(polis2);

            // Als afsluiter de Relatie verwijderen
            beherenRelatieScherm.klikMenuItemAan(MenuItem.BEHERENRELATIE);
            beherenRelatieScherm.drukOpVerwijderen();
            Hulp.wachtFf();
        }
    }

    private void checkOpgeslagenMelding(BeherenRelatie beherenRelatieScherm) {
        assertEquals("De gegevens zijn opgeslagen", beherenRelatieScherm.leesmelding());
    }

    private JsonBedrijf maakJsonBedrijf() {
        JsonBedrijf bedrijf = new JsonBedrijf();

        bedrijf.setHuisnummer(((Integer) stringGeneratieUtil.randomGetal(200)).toString());
        bedrijf.setKvk(((Integer) stringGeneratieUtil.randomGetal(99999999)).toString());
        bedrijf.setNaam(stringGeneratieUtil.genereerAchternaam());
        bedrijf.setPlaats(stringGeneratieUtil.genereerPlaatsnaam());
        bedrijf.setPostcode(stringGeneratieUtil.genereerPostcode());
        bedrijf.setStraat(stringGeneratieUtil.genereerStraatnaam());
        bedrijf.setToevoeging(stringGeneratieUtil.genereerToevoeging());

        return bedrijf;
    }

    private JsonPolis maakJsonPolis(String bedrijf) {
        JsonPolis jsonPolis = new JsonPolis();

        if (bedrijf != null) {
            jsonPolis.setBedrijf(bedrijf);
        }
        jsonPolis.setBetaalfrequentie((String) stringGeneratieUtil.kiesUitItems(Betaalfrequentie.J.getOmschrijving(), Betaalfrequentie.K.getOmschrijving(), Betaalfrequentie.M.getOmschrijving(),
                Betaalfrequentie.H.getOmschrijving()));
        jsonPolis.setIngangsDatum(stringGeneratieUtil.genereerDatum().toString("dd-MM-yyyy"));
        jsonPolis.setMaatschappij("Aegon");
        jsonPolis.setPolisNummer(((Integer) stringGeneratieUtil.randomGetal(1000000)).toString());
        jsonPolis.setPremie(((Integer) stringGeneratieUtil.randomGetal(1000)).toString());
        jsonPolis.setProlongatieDatum(stringGeneratieUtil.genereerDatum().toString("dd-MM-yyyy"));
        jsonPolis.setSoort("Fiets");
        jsonPolis.setWijzigingsDatum(stringGeneratieUtil.genereerDatum().toString("dd-MM-yyyy"));

        return jsonPolis;
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
        jsonRelatie.setOverlijdensdatum((String) stringGeneratieUtil.kiesUitItems("", stringGeneratieUtil.kiesUitItems("", stringGeneratieUtil.genereerDatum(geboorteDatum).toString("dd-MM-yyyy"))));

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
