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
import nl.dias.web.pagina.PaginaMetMenuBalk.MenuItem;
import nl.dias.web.pagina.PolisBewerken;
import nl.dias.web.pagina.PolisOverzicht;
import nl.dias.web.pagina.SchadeBewerken;
import nl.dias.web.pagina.SchadeOverzicht;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.LocalDate;
import org.junit.After;
import org.junit.Before;
import org.junit.ComparisonFailure;
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

    // private final String BASIS_URL =
    // "http://localhost:8080/dejonge/index.html#";

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
            String huisnummer = jsonRelatie.getHuisnummer();

            jsonRelatie.setHuisnummer("a");

            beherenRelatieScherm.vulVeldenEnDrukOpOpslaan(jsonRelatie.getVoornaam(), jsonRelatie.getAchternaam(), jsonRelatie.getTussenvoegsel(), jsonRelatie.getStraat(), jsonRelatie.getHuisnummer(),
                    jsonRelatie.getToevoeging(), jsonRelatie.getPostcode(), jsonRelatie.getPlaats(), jsonRelatie.getBsn(), jsonRelatie.getIdentificatie(), jsonRelatie.getGeboorteDatum(),
                    jsonRelatie.getOverlijdensdatum(), jsonRelatie.getGeslacht(), jsonRelatie.getBurgerlijkeStaat(),
                    allJsonRekeningNummerToBeherenRelatieRekeningnummer(jsonRelatie.getRekeningnummers()), allJsonTelefoonnummerToBeherenRelatieTelefoonnummer(jsonRelatie.getTelefoonnummers()));

            checkFoutmelding(beherenRelatieScherm, "Er is een fout opgetreden : Huisnummer mag alleen cijfers bevatten");

            jsonRelatie.setHuisnummer(huisnummer);

            beherenRelatieScherm.vulVeldenEnDrukOpOpslaan(null, null, null, null, jsonRelatie.getHuisnummer(), null, null, null, null, null, null, null, null, null, null, null);

            checkOpgeslagenMelding(beherenRelatieScherm);

            // JsonRelatie jsonRelatie = new JsonRelatie();
            // jsonRelatie.setAchternaam("Jansen");
            // jsonRelatie.setTussenvoegsel("");
            // jsonRelatie.setVoornaam("Patrick");
            // jsonRelatie.setGeboorteDatum("05-11-1970");
            // jsonRelatie.setAdresOpgemaakt("Langestraat 75 , Groningen");

            // Opgeslagen Relatie weer aanklikken op overzichtsscherm
            assertTrue(lijstRelaties.zoekRelatieOpEnKlikDezeAan(jsonRelatie));

            Hulp.wachtFf(2000);

            // Checken of velden correct worden weergegeven (en dus of ze
            // correct zijn opgeslagen)
            assertEquals("", beherenRelatieScherm.checkVelden(jsonRelatie));

            beherenRelatieScherm.klikMenuItemAan(MenuItem.POLIS, driver);

            JsonPolis polis = maakJsonPolis(null);

            PolisBewerken polisScherm = PageFactory.initElements(driver, PolisBewerken.class);
            assertFalse(polisScherm.isBedrijfBijPolisZichtbaar());

            polisScherm.vulVeldenEnDrukOpOpslaan(polis);
            checkOpgeslagenMelding(beherenRelatieScherm);

            assertTrue(driver.getCurrentUrl().endsWith("/polissen"));
            checkOpgeslagenMelding(beherenRelatieScherm);

            // beherenRelatieScherm.klikMenuItemAan(MenuItem.POLISSEN, driver);
            PolisOverzicht polissen = PageFactory.initElements(driver, PolisOverzicht.class);
            assertEquals("", polissen.controleerPolis(polis));

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

            // JsonPolis polis = new JsonPolis();
            // polis.setTitel("Annulerings (511973)");
            // JsonPolis polis2 = new JsonPolis();
            // polis2.setTitel("Fiets (65050)");
            // JsonPolis polis3 = new JsonPolis();
            // polis3.setTitel("Fiets (650501)");
            //
            // assertEquals(1, polissen.zoekPolis(polis));
            // assertEquals(2, polissen.zoekPolis(polis2));
            // assertEquals(-1, polissen.zoekPolis(polis3));
            polissen = PageFactory.initElements(driver, PolisOverzicht.class);
            assertEquals("", polissen.controleerPolis(polis));
            assertEquals("", polissen.controleerPolis(polis2));

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
            checkOpgeslagenMelding(beherenRelatieScherm);
            assertTrue(driver.getCurrentUrl().endsWith("/schades"));

            // # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # #
            //
            // S C H A D E C O N T R O L E R E N
            //
            // # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # #
            SchadeOverzicht schadeOverzicht = PageFactory.initElements(driver, SchadeOverzicht.class);
            schadeOverzicht.controleerSchade(schade);

            // # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # #
            //
            // P O L I S W I J Z I G E N
            //
            // # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # #

            beherenRelatieScherm.klikMenuItemAan(MenuItem.POLISSEN, driver);
            polissen = PageFactory.initElements(driver, PolisOverzicht.class);
            assertEquals("", polissen.controleerPolis(polis));
            assertEquals("", polissen.controleerPolis(polis2));

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
            assertEquals("", polissen.controleerPolis(polis));
            assertEquals("", polissen.controleerPolis(polis2));

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
            Hulp.wachtFf(5000);
            melding = beherenRelatieScherm.leesmelding();
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
        jsonPolis.setSoort((String) stringGeneratieUtil.kiesUitItems("Aansprakelijkheid", "Auto", "Brom-/Snorfiets", "Camper", "Reis", "Fiets", "Inboedel", "Motor", "Ongevallen", "Pleziervaartuig",
                "Recreatie", "Rechtsbijstand", "Reis", "Woonhuis", "Zorg"));
        jsonPolis.setWijzigingsDatum(stringGeneratieUtil.genereerDatum().toString("dd-MM-yyyy"));

        jsonPolis.setTitel(jsonPolis.getSoort() + " (" + jsonPolis.getPolisNummer() + ")");

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
