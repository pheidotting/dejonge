package nl.dias.it.webtesten;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import nl.dias.domein.json.JsonFoutmelding;
import nl.dias.domein.json.JsonRekeningNummer;
import nl.dias.domein.json.JsonRelatie;
import nl.dias.domein.json.JsonTelefoonnummer;
import nl.dias.it.webtesten.util.StringGeneratieUtil;
import nl.dias.web.pagina.BeherenRelatieRekeningnummer;
import nl.dias.web.pagina.BeherenRelatieTelefoonnummer;

import org.joda.time.LocalDate;
import org.junit.Before;
import org.junit.Test;

import com.google.gson.Gson;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

public class BeherenRelatieITJson {
    private StringGeneratieUtil stringGeneratieUtil;
    private Gson gson;
    private Client client;

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
            stringGeneratieUtil = new StringGeneratieUtil();

            gson = new Gson();

            client = Client.create();
        }
    }

    private void checkMelding(String verwacht, JsonFoutmelding melding) {
        assertEquals(verwacht, melding.getFoutmelding());
    }

    private String stuurJson(String adres, String input) {
        WebResource webResource = client.resource(adres);

        ClientResponse response = webResource.type("application/json").post(ClientResponse.class, input);

        return response.getEntity(String.class);
    }

    @Test
    public void test() {
        if (doorgaan()) {
            String output = stuurJson("http://46.17.3.242:57525/dejonge/rest/authorisatie/authorisatie/inloggen",
                    "{\"identificatie\":\"gerben@dejongefinancieelconsult.nla\",\"wachtwoord\":\"\",\"onthouden\":\"false\"}");
            JsonFoutmelding melding = gson.fromJson(output, JsonFoutmelding.class);

            checkMelding("gerben@dejongefinancieelconsult.nla werd niet gevonden.", melding);

            output = stuurJson("http://46.17.3.242:57525/dejonge/rest/authorisatie/authorisatie/inloggen",
                    "{\"identificatie\":\"gerben@dejongefinancieelconsult.nl\",\"wachtwoord\":\"onjuistwachtwoord\",\"onthouden\":\"false\"}");
            melding = gson.fromJson(output, JsonFoutmelding.class);

            checkMelding("Het ingevoerde wachtwoord is onjuist", melding);

            output = stuurJson("http://46.17.3.242:57525/dejonge/rest/authorisatie/authorisatie/inloggen",
                    "{\"identificatie\":\"gerben@dejongefinancieelconsult.nl\",\"wachtwoord\":\"gerben\",\"onthouden\":\"false\"}");
            melding = gson.fromJson(output, JsonFoutmelding.class);

            checkMelding(null, melding);

            JsonRelatie jsonRelatie = maakJsonRelatie();

            output = stuurJson("http://46.17.3.242:57525/dejonge/rest/medewerker/gebruiker/opslaan", gson.toJson(jsonRelatie));
            System.out.println(output);
            melding = gson.fromJson(output, JsonFoutmelding.class);

            checkMelding(null, melding);

            // Hulp.naarAdres(driver,
            // "http://46.17.3.242:57525/dejonge/index.html#inloggen");
            //
            // InlogScherm inlogScherm = PageFactory.initElements(driver,
            // InlogScherm.class);
            // inlogScherm.inloggen("gerben@dejongefinancieelconsult.nla", "");
            // assertEquals("Er is een fout opgetreden : gerben@dejongefinancieelconsult.nla werd niet gevonden.",
            // inlogScherm.leesFoutmelding());
            // inlogScherm.inloggen("gerben@dejongefinancieelconsult.nl", "g");
            // assertEquals("Er is een fout opgetreden : Het ingevoerde wachtwoord is onjuist",
            // inlogScherm.leesFoutmelding());
            // inlogScherm.inloggen("gerben@dejongefinancieelconsult.nl",
            // "gerben");
            //
            // Hulp.wachtFf();
            //
            // LijstRelaties lijstRelaties = PageFactory.initElements(driver,
            // LijstRelaties.class);
            // lijstRelaties.toevoegenNieuweRelatie();
            //
            // BeherenRelatie beherenRelatieScherm =
            // PageFactory.initElements(driver, BeherenRelatie.class);
            //
            // JsonRelatie jsonRelatie = maakJsonRelatie();
            //
            // beherenRelatieScherm.vulVeldenEnDrukOpOpslaan(jsonRelatie.getVoornaam(),
            // jsonRelatie.getAchternaam(), jsonRelatie.getTussenvoegsel(),
            // jsonRelatie.getStraat(), jsonRelatie.getHuisnummer()
            // .toString(), jsonRelatie.getToevoeging(),
            // jsonRelatie.getPostcode(), jsonRelatie.getPlaats(),
            // jsonRelatie.getBsn(), jsonRelatie.getIdentificatie(),
            // jsonRelatie.getGeboorteDatum(),
            // jsonRelatie.getOverlijdensdatum(), jsonRelatie.getGeslacht(),
            // jsonRelatie.getBurgerlijkeStaat(),
            // allJsonRekeningNummerToBeherenRelatieRekeningnummer(jsonRelatie
            // .getRekeningnummers()),
            // allJsonTelefoonnummerToBeherenRelatieTelefoonnummer(jsonRelatie.getTelefoonnummers()));
            //
            // assertEquals("De gegevens zijn opgeslagen",
            // beherenRelatieScherm.leesmelding());

        }
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
        jsonRelatie.setOverlijdensdatum((String) stringGeneratieUtil.kiesUitItems("", stringGeneratieUtil.genereerDatum(geboorteDatum).toString("dd-MM-yyyy")));

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
