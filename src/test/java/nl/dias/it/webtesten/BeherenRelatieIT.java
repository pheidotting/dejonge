package nl.dias.it.webtesten;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import nl.dias.dias_web.hulp.Hulp;
import nl.dias.domein.json.JsonBedrijf;
import nl.dias.domein.json.JsonOpmerking;
import nl.dias.domein.json.JsonPolis;
import nl.dias.domein.json.JsonRekeningNummer;
import nl.dias.domein.json.JsonRelatie;
import nl.dias.domein.json.JsonSchade;
import nl.dias.domein.json.JsonTelefoonnummer;
import nl.dias.domein.polis.Betaalfrequentie;
import nl.dias.it.webtesten.util.StringGeneratieUtil;
import nl.dias.web.pagina.BedrijfBewerken;
import nl.dias.web.pagina.BedrijvenOverzicht;
import nl.dias.web.pagina.BeherenRelatie;
import nl.dias.web.pagina.BeherenRelatieRekeningnummer;
import nl.dias.web.pagina.BeherenRelatieTelefoonnummer;
import nl.dias.web.pagina.InlogScherm;
import nl.dias.web.pagina.LijstRelaties;
import nl.dias.web.pagina.OpmerkingBewerken;
import nl.dias.web.pagina.PaginaMetMenuBalk.MenuItem;
import nl.dias.web.pagina.PolisBewerken;
import nl.dias.web.pagina.PolisOverzicht;
import nl.dias.web.pagina.SchadeBewerken;
import nl.dias.web.pagina.SchadeOverzicht;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.junit.After;
import org.junit.Before;
import org.junit.ComparisonFailure;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.server.SeleniumServer;
import org.openqa.selenium.support.PageFactory;

import com.saucelabs.common.SauceOnDemandAuthentication;
import com.saucelabs.common.SauceOnDemandSessionIdProvider;
import com.saucelabs.junit.ConcurrentParameterized;
import com.saucelabs.junit.SauceOnDemandTestWatcher;

@RunWith(ConcurrentParameterized.class)
public class BeherenRelatieIT implements SauceOnDemandSessionIdProvider {
    private SeleniumServer seleniumServer;
    private WebDriver driver;
    private StringGeneratieUtil stringGeneratieUtil;
    // schermen
    private InlogScherm inlogScherm;
    private LijstRelaties lijstRelaties;
    private BeherenRelatie beherenRelatieScherm;
    // testgegevens
    private List<JsonRelatie> jsonRelaties;

    // private final String BASIS_URL =
    // "http://46.17.3.242:57525/dejonge/index.html#";
    private final String BASIS_URL = "http://localhost:8080/dejonge/index.html#";

    public SauceOnDemandAuthentication authentication = new SauceOnDemandAuthentication("lakedigital", "b778ded8-21ad-4a54-b701-647d3521ea53");

    @Rule
    public SauceOnDemandTestWatcher resultReportingTestWatcher = new SauceOnDemandTestWatcher(this, authentication);
    private final String browser;
    private final String os;
    private final String version;
    private String sessionId;

    @ConcurrentParameterized.Parameters
    public static LinkedList browsersStrings() {
        LinkedList browsers = new LinkedList();
        // browsers.add(new String[] { "Windows 8.1", "11", "internet explorer"
        // });
        browsers.add(new String[] { "OSX 10.8", "38", "chrome" });
        // browsers.add(new String[] { "Windows 7", "10", "iehta" });
        return browsers;
    }

    private boolean doorgaan() {
        boolean doorgaan = true;
        if (System.getenv("webtesten") != null) {
            if (System.getenv("webtesten").equals("false")) {
                doorgaan = false;
            }
        }

        return doorgaan;
    }

    // public BeherenRelatieIT() {
    // // TODO Auto-generated constructor stub
    // }

    public BeherenRelatieIT(String os, String version, String browser) {
        super();
        this.os = os;
        this.version = version;
        this.browser = browser;
    }

    @Before
    public void setUp() throws Exception {
        if (doorgaan()) {
            // DesiredCapabilities capabilities = new DesiredCapabilities();
            // capabilities.setCapability(CapabilityType.BROWSER_NAME, browser);
            // if (version != null) {
            // capabilities.setCapability(CapabilityType.VERSION, version);
            // }
            // capabilities.setCapability(CapabilityType.PLATFORM, os);
            // capabilities.setCapability("name",
            // this.getClass().getSimpleName());
            // this.driver = new RemoteWebDriver(new URL("http://" +
            // authentication.getUsername() + ":" +
            // authentication.getAccessKey() +
            // "@ondemand.saucelabs.com:80/wd/hub"), capabilities);

            seleniumServer = new SeleniumServer();
            seleniumServer.start();

            driver = new FirefoxDriver();
            stringGeneratieUtil = new StringGeneratieUtil();
        }

        jsonRelaties = new ArrayList<>();
    }

    @After
    public void afsluiten() {
        if (doorgaan()) {
            driver.quit();
            if (seleniumServer != null) {
                seleniumServer.stop();
            }
        }
    }

    private void inloggen() {

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

        LijstRelaties lijstRelaties = PageFactory.initElements(driver, LijstRelaties.class);
        Hulp.wachtOpElement(lijstRelaties.getToevoegenNieuweRelatie());
    }

    public void opvoerenRelatie() {
        lijstRelaties = PageFactory.initElements(driver, LijstRelaties.class);
        Hulp.wachtFf();
        lijstRelaties.toevoegenNieuweRelatie();
        Hulp.wachtFf();

        beherenRelatieScherm = PageFactory.initElements(driver, BeherenRelatie.class);
        beherenRelatieScherm.drukOpOpslaan();
        // aantal 'vul dit veld in' meldingen checken
        beherenRelatieScherm = PageFactory.initElements(driver, BeherenRelatie.class);
        assertEquals(6, beherenRelatieScherm.aantalFouten());

        JsonRelatie jsonRelatie = maakJsonRelatie();
        jsonRelaties.add(jsonRelatie);

        beherenRelatieScherm.vulVelden(jsonRelatie.getVoornaam(), jsonRelatie.getAchternaam(), null, jsonRelatie.getStraat(), "a", null, null, null, null, jsonRelatie.getIdentificatie(),
                jsonRelatie.getGeboorteDatum(), null, jsonRelatie.getGeslacht(), jsonRelatie.getBurgerlijkeStaat(), null, null);

        beherenRelatieScherm = PageFactory.initElements(driver, BeherenRelatie.class);
        assertEquals(1, beherenRelatieScherm.aantalFouten());
        assertEquals("Vul een getal in.", beherenRelatieScherm.getValidatieFouten().get(0).getText());

        beherenRelatieScherm.vulVeldenEnDrukOpOpslaan(null, null, null, null, jsonRelatie.getHuisnummer(), null, null, null, null, null, null, null, null, null, null, null);

        checkOpgeslagenMelding(beherenRelatieScherm);

        JsonRelatie r = new JsonRelatie();
        r.setVoornaam(jsonRelatie.getVoornaam());
        r.setAchternaam(jsonRelatie.getAchternaam());
        r.setTussenvoegsel("");
        r.setStraat(jsonRelatie.getStraat());
        r.setHuisnummer(jsonRelatie.getHuisnummer());
        r.setGeboorteDatum(jsonRelatie.getGeboorteDatum());

        assertTrue(lijstRelaties.zoekRelatieOpEnKlikDezeAan(r));

        beherenRelatieScherm.vulVeldenEnDrukOpOpslaan(null, null, jsonRelatie.getTussenvoegsel(), null, null, jsonRelatie.getToevoeging(), jsonRelatie.getPostcode(), jsonRelatie.getPlaats(),
                jsonRelatie.getBsn(), null, null, jsonRelatie.getOverlijdensdatum(), null, null, allJsonRekeningNummerToBeherenRelatieRekeningnummer(jsonRelatie.getRekeningnummers()),
                allJsonTelefoonnummerToBeherenRelatieTelefoonnummer(jsonRelatie.getTelefoonnummers()));

        checkOpgeslagenMelding(beherenRelatieScherm);
    }

    @Test
    public void test() {
        if (doorgaan()) {
            Hulp.naarAdres(driver, BASIS_URL + "inloggen");

            inlogScherm = PageFactory.initElements(driver, InlogScherm.class);
            inloggen();

            opvoerenRelatie();

            // Opgeslagen Relatie weer aanklikken op overzichtsscherm
            assertTrue(lijstRelaties.zoekRelatieOpEnKlikDezeAan(jsonRelaties.get(0)));

            Hulp.wachtFf(2000);

            // Checken of velden correct worden weergegeven (en dus of ze
            // correct zijn opgeslagen)
            assertEquals("", beherenRelatieScherm.checkVelden(jsonRelaties.get(0)));

            beherenRelatieScherm.klikMenuItemAan(MenuItem.POLIS, driver);
            Hulp.wachtFf();

            JsonPolis polis = maakJsonPolis(null);

            PolisBewerken polisScherm = PageFactory.initElements(driver, PolisBewerken.class);
            assertFalse(polisScherm.isBedrijfBijPolisZichtbaar());

            polisScherm.vulVeldenEnDrukOpOpslaan(polis);
            checkOpgeslagenMelding(beherenRelatieScherm);

            assertTrue(driver.getCurrentUrl().endsWith("/polissen"));
            checkOpgeslagenMelding(beherenRelatieScherm);

            // beherenRelatieScherm.klikMenuItemAan(MenuItem.POLISSEN, driver);
            PolisOverzicht polissen = PageFactory.initElements(driver, PolisOverzicht.class);
            Hulp.wachtFf();
            assertEquals("", polissen.controleerPolissen(polis));

            // # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # #
            //
            // B E D R I J F
            //
            // # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # #

            // We gaan een bedrijf invoeren
            beherenRelatieScherm.klikMenuItemAan(MenuItem.BEDRIJF, driver);
            BedrijfBewerken bedrijfScherm = PageFactory.initElements(driver, BedrijfBewerken.class);
            JsonBedrijf jsonBedrijf = maakJsonBedrijf();

            bedrijfScherm.vulVeldenEnDrukOpOpslaan(jsonBedrijf);

            checkOpgeslagenMelding(beherenRelatieScherm);
            assertTrue(driver.getCurrentUrl().endsWith("/bedrijven"));

            // # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # #
            //
            // B E D R I J F C O N T R O L E R EN
            //
            // # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # #

            BedrijvenOverzicht bedrijvenOverzicht = PageFactory.initElements(driver, BedrijvenOverzicht.class);
            assertEquals("", bedrijvenOverzicht.controleerBedrijf(jsonBedrijf));

            // # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # #
            //
            // P O L I S
            //
            // # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # #

            // En een polis invoeren bij dit bedrijf
            beherenRelatieScherm.klikMenuItemAan(MenuItem.POLIS, driver);
            assertTrue(polisScherm.isBedrijfBijPolisZichtbaar());
            JsonPolis polis2 = maakJsonPolis(jsonBedrijf.getNaam());
            polisScherm.vulVeldenEnDrukOpOpslaan(polis2);

            polissen = PageFactory.initElements(driver, PolisOverzicht.class);
            assertEquals("", polissen.controleerPolissen(polis, polis2));

            // # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # #
            //
            // S C H A D E
            //
            // # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # #
            // Schade invoeren
            JsonSchade schade = maakJsonSchade();
            schade.setPolis(polis.getSoort() + " (" + polis.getPolisNummer() + ")");
            beherenRelatieScherm.klikMenuItemAan(MenuItem.SCHADE, driver);
            SchadeBewerken schadeScherm = PageFactory.initElements(driver, SchadeBewerken.class);
            schadeScherm.vulVeldenEnDrukOpOpslaan(schade);
            Hulp.wachtFf();
            checkOpgeslagenMelding(beherenRelatieScherm);
            assertTrue(driver.getCurrentUrl().endsWith("/schades"));

            // # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # #
            //
            // S C H A D E C O N T R O L E R E N
            //
            // # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # #
            SchadeOverzicht schadeOverzicht = PageFactory.initElements(driver, SchadeOverzicht.class);
            schadeOverzicht.controleerSchades(schade);

            // # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # #
            //
            // P O L I S W I J Z I G E N
            //
            // # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # #

            beherenRelatieScherm.klikMenuItemAan(MenuItem.POLISSEN, driver);
            polissen = PageFactory.initElements(driver, PolisOverzicht.class);
            assertEquals("", polissen.controleerPolissen(polis, polis2));

            polissen.bewerkPolis(polissen.zoekPolis(polis));

            polisScherm = PageFactory.initElements(driver, PolisBewerken.class);
            String betaalfrequentie = polis.getBetaalfrequentie();
            String nieuweBetaalFrequentie = null;

            do {
                nieuweBetaalFrequentie = (String) stringGeneratieUtil.kiesUitItems(Betaalfrequentie.J.getOmschrijving(), Betaalfrequentie.K.getOmschrijving(), Betaalfrequentie.M.getOmschrijving(),
                        Betaalfrequentie.H.getOmschrijving());
            } while (betaalfrequentie.equals(nieuweBetaalFrequentie));
            polis.setBetaalfrequentie(nieuweBetaalFrequentie);
            polisScherm.vulVeldenEnDrukOpOpslaan(polis);

            polissen = PageFactory.initElements(driver, PolisOverzicht.class);
            assertEquals("", polissen.controleerPolissen(polis, polis2));

            // # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # #
            //
            // O P M E R K I N G P L A A T S E N B I J S C H A D E
            //
            // # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # #

            beherenRelatieScherm.klikMenuItemAan(MenuItem.SCHADES, driver);
            schadeOverzicht = PageFactory.initElements(driver, SchadeOverzicht.class);
            Hulp.wachtFf();
            schadeOverzicht.opmerkingBijSchade(schade);
            Hulp.wachtFf(2000);

            OpmerkingBewerken opmerkingBewerken = PageFactory.initElements(driver, OpmerkingBewerken.class);
            JsonOpmerking jsonOpmerking = maakJsonOpmerking();
            opmerkingBewerken.vulVeldenEnDrukOpOpslaan(jsonOpmerking);
            checkOpgeslagenMelding(beherenRelatieScherm);

            schadeOverzicht = PageFactory.initElements(driver, SchadeOverzicht.class);
            schadeOverzicht.controleerSchades(schade);

            // # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # #
            //
            // S C H A D E W I J Z I G E N
            //
            // # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # #
            String nieuweStatus = null;
            do {
                nieuweStatus = (String) stringGeneratieUtil.kiesUitItems("In behandeling maatschappij", "Afgehandeld maatschappij", "In behandeling tussenpersoon", "Afgehandeld tussenpersoon");
            } while (nieuweStatus.equals(schade.getStatusSchade()));
            schade.setStatusSchade(nieuweStatus);
            Hulp.wachtFf();
            schadeOverzicht.bewerkSchade(schade);

            schadeScherm = PageFactory.initElements(driver, SchadeBewerken.class);
            schadeScherm.vulVeldenEnDrukOpOpslaan(schade);
            checkOpgeslagenMelding(beherenRelatieScherm);

            schadeOverzicht = PageFactory.initElements(driver, SchadeOverzicht.class);
            schadeOverzicht.controleerSchades(schade);

            // # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # #
            //
            // R E L A T I E W I J Z I G E N
            //
            // # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # #
            String nieuweStraat = null;
            do {
                nieuweStraat = stringGeneratieUtil.genereerStraatnaam();
            } while (nieuweStraat.equals(jsonRelaties.get(0).getStraat()));
            jsonRelaties.get(0).setStraat(nieuweStraat);
            jsonRelaties.get(0).setAdresOpgemaakt(null);
            beherenRelatieScherm.klikMenuItemAan(MenuItem.BEHERENRELATIE, driver);
            beherenRelatieScherm.vulVeldenEnDrukOpOpslaan(null, null, null, nieuweStraat, null, null, null, null, null, null, null, null, null, null, null, null);
            checkOpgeslagenMelding(beherenRelatieScherm);

            // Opgeslagen Relatie weer aanklikken op overzichtsscherm
            assertTrue(lijstRelaties.zoekRelatieOpEnKlikDezeAan(jsonRelaties.get(0)));

            Hulp.wachtFf(2000);

            // Checken of velden correct worden weergegeven (en dus of ze
            // correct zijn opgeslagen)
            assertEquals("", beherenRelatieScherm.checkVelden(jsonRelaties.get(0)));
            beherenRelatieScherm.klikMenuItemAan(MenuItem.POLISSEN, driver);

            // # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # #
            //
            // A F S L U I T E N , R E L A T I E V E R W I J D E R E N
            //
            // # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # #

            // Als afsluiter de Relatie verwijderen
            beherenRelatieScherm.klikMenuItemAan(MenuItem.BEHERENRELATIE, driver);
            beherenRelatieScherm.drukOpVerwijderen();
            Hulp.wachtFf();

            beherenRelatieScherm.uitloggen();
        }
    }

    private void checkOpgeslagenMelding(BeherenRelatie beherenRelatieScherm) {
        String melding = beherenRelatieScherm.leesmelding();
        if (melding == null || melding.equals("")) {
            LocalDateTime timeOut = new LocalDateTime().plusSeconds(10);
            while (melding == null || melding.equals("") && LocalDateTime.now().isBefore(timeOut)) {
                Hulp.wachtFf(500);
                melding = beherenRelatieScherm.leesmelding();
            }
        }
        try {
            assertEquals("De gegevens zijn opgeslagen", beherenRelatieScherm.leesmelding());
        } catch (ComparisonFailure cf) {
            if (beherenRelatieScherm.leesFoutmelding().equals("")) {
                throw new ComparisonFailure("Fout", "Goedmelding : De gegevens zijn opgeslagen", null);
            } else {
                throw new ComparisonFailure("Fout", "Goedmelding : De gegevens zijn opgeslagen", "Foutmelding : " + beherenRelatieScherm.leesFoutmelding());
            }
        }
    }

    private void checkFoutmelding(BeherenRelatie beherenRelatieScherm, String verwacht) {
        assertEquals(verwacht, beherenRelatieScherm.leesFoutmelding());
    }

    private JsonOpmerking maakJsonOpmerking() {
        JsonOpmerking opmerking = new JsonOpmerking();
        opmerking.setOpmerking("abcdefghijklmnopqrstuvwxyz");

        return opmerking;
    }

    private JsonSchade maakJsonSchade() {
        JsonSchade jsonSchade = new JsonSchade();

        jsonSchade.setDatumAfgehandeld(stringGeneratieUtil.genereerDatum().toString("dd-MM-yyyy"));
        jsonSchade.setDatumTijdMelding(stringGeneratieUtil.genereerDatumTijd().toString("dd-MM-yyyy HH:mm"));
        jsonSchade.setDatumTijdSchade(stringGeneratieUtil.genereerDatumTijd().toString("dd-MM-yyyy HH:mm"));
        jsonSchade.setEigenRisico(((Integer) stringGeneratieUtil.randomGetal(999)).toString());
        jsonSchade.setLocatie(stringGeneratieUtil.genereerPlaatsnaam());
        jsonSchade.setOmschrijving("Omschrijving");
        jsonSchade.setSchadeNummerMaatschappij(((Integer) stringGeneratieUtil.randomGetal(9999999)).toString());
        jsonSchade.setSchadeNummerTussenPersoon(((Integer) stringGeneratieUtil.randomGetal(9999999)).toString());
        jsonSchade.setSoortSchade((String) stringGeneratieUtil.kiesUitItems("Aanrijding met wild", "Aanrijding schuldschade", "Aanrijding verhaalschade", "Aansprakelijkheidschade", "Diefstalschade",
                "Inbraak zonder braakschade", "Inbraak met braakschade", "Rechtsbijstanddekking soc/werk", "Ruitbreuk", "Stormschade", "Vandalismeschade"));
        jsonSchade.setStatusSchade((String) stringGeneratieUtil.kiesUitItems("In behandeling maatschappij", "Afgehandeld maatschappij", "In behandeling tussenpersoon", "Afgehandeld tussenpersoon"));

        return jsonSchade;
    }

    private JsonBedrijf maakJsonBedrijf() {
        JsonBedrijf bedrijf = new JsonBedrijf();

        bedrijf.setHuisnummer(((Integer) stringGeneratieUtil.randomGetal(200)).toString());
        bedrijf.setKvk(((Integer) stringGeneratieUtil.randomGetal(99999999)).toString());
        bedrijf.setNaam(stringGeneratieUtil.genereerAchternaam() + " B.V.");
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
        jsonPolis.setMaatschappij((String) stringGeneratieUtil.kiesUitItems("Achmea", "Aegon", "Agis", "Allianz Nederland", "AllSecur", "Amersfoortse (de)", "ASR Verzekeringen", "Avero Achmea",
                "Crisper", "Delta Lloyd", "Ditzo", "Generali", "Goudse (de)", "Kilometerverzekering (de)", "Klaverblad", "Kruidvat", "London Verzekeringen", "Nationale Nederlanden", "OHRA",
                "Polis Direct", "SNS Bank", "Turien & Co", "Unive", "Verzekeruzelf.nl", "Zelf.nl", "Unigarant", "Voogd en Voogd", "VKG", "DAS", "Europeesche", "Erasmus", "Monuta", "Onderlinge",
                "Reaal"));
        jsonPolis.setPolisNummer(((Integer) stringGeneratieUtil.randomGetal(1000000)).toString());
        jsonPolis.setPremie(((Integer) stringGeneratieUtil.randomGetal(1000)).toString());
        jsonPolis.setProlongatieDatum(stringGeneratieUtil.genereerDatum().toString("dd-MM-yyyy"));
        // jsonPolis.setSoort((String)
        // stringGeneratieUtil.kiesUitItems("Aansprakelijkheid", "Auto",
        // "Brom-/Snorfiets", "Camper", "Annulerings", "Reis", "Fiets",
        // "Inboedel", "Leven",
        // "Mobiele apparatuur", "Motor", "Ongevallen", "Pleziervaartuig",
        // "Recreatie", "Rechtsbijstand", "Reis", "Woonhuis", "Zorg"));
        jsonPolis.setSoort((String) stringGeneratieUtil.kiesUitItems("Aansprakelijkheid", "Auto", "Camper", "Reis", "Fiets", "Inboedel", "Motor", "Ongevallen", "Pleziervaartuig", "Recreatie",
                "Rechtsbijstand", "Reis", "Woonhuis", "Zorg"));
        jsonPolis.setWijzigingsDatum(stringGeneratieUtil.genereerDatum().toString("dd-MM-yyyy"));

        jsonPolis.setTitel(jsonPolis.getSoort() + " (" + jsonPolis.getPolisNummer() + ")");

        return jsonPolis;
    }

    private JsonRelatie maakJsonRelatie() {
        JsonRelatie jsonRelatie = new JsonRelatie();

        jsonRelatie.setAchternaam(stringGeneratieUtil.genereerAchternaam());
        jsonRelatie.setBsn(stringGeneratieUtil.genereerBsn());
        jsonRelatie.setTussenvoegsel(stringGeneratieUtil.genereerTussenvoegsel());
        jsonRelatie.setVoornaam(stringGeneratieUtil.genereerVoornaam());
        jsonRelatie.setIdentificatie(stringGeneratieUtil.genereerEmailAdres(jsonRelatie.getVoornaam(), jsonRelatie.getTussenvoegsel(), jsonRelatie.getAchternaam()));
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

    @Override
    public String getSessionId() {
        return sessionId;
    }
}
