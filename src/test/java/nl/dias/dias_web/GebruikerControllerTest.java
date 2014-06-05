package nl.dias.dias_web;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import nl.dias.domein.json.JsonBedrijf;
import nl.dias.domein.json.JsonBijlage;
import nl.dias.domein.json.JsonLijstRelaties;
import nl.dias.domein.json.JsonPolis;
import nl.dias.domein.json.JsonRekeningNummer;
import nl.dias.domein.json.JsonRelatie;
import nl.dias.domein.json.JsonTelefoonnummer;
import nl.dias.web.InterfaceGebruikerController;

import org.joda.time.LocalDate;

import com.google.gson.Gson;

@Path("/gebruiker")
public class GebruikerControllerTest implements InterfaceGebruikerController {
    public static Long id;
    public static JsonRelatie jsonRelatie;
    public static JsonLijstRelaties jsonLijstRelaties;

    // @GET
    // @Path("/inloggen")
    // @Produces(MediaType.TEXT_PLAIN)
    // public String inloggen(@QueryParam("emailadres") String emailadres,
    // @QueryParam("wachtwoord") String wachtwoord, @QueryParam("strOnthouden")
    // String strOnthouden,
    // @Context HttpServletRequest request) {
    // return null;
    // }

    @GET
    @Path("/lees")
    @Produces("application/json")
    public JsonRelatie lees(@QueryParam("id") Long id) {
        if (GebruikerControllerTest.jsonRelatie == null) {
            // String json =
            // "{\"id\":3,\"identificatie\":\"p@h.nl\",\"voornaam\":\"Patrick\",\"tussenvoegsel\":\"van der\",\"achternaam\":\"Heidotting\",\"straat\":\"Eemslandweg\",\"huisnummer\":41,\"toevoeging\":JJ,\"postcode\":7894AB,\"plaats\":\"Zwartemeer\",\"adresOpgemaakt\":\"Eemslandweg 41 Zwartemeer \",\"telefoonnummers\":[],\"bsn\":103127586,\"rekeningnummers\":[],\"kantoor\":1,\"opmerkingen\":[],\"geboorteDatum\":{\"dayOfMonth\":6,\"dayOfWeek\":4,\"era\":1,\"dayOfYear\":249,\"year\":1979,\"chronology\":{\"zone\":{\"fixed\":true,\"id\":\"UTC\"}},\"centuryOfEra\":19,\"yearOfEra\":1979,\"yearOfCentury\":79,\"weekyear\":1979,\"monthOfYear\":9,\"weekOfWeekyear\":36,\"fields\":[{\"rangeDurationField\":null,\"lenient\":false,\"leapDurationField\":{\"unitMillis\":86400000,\"precise\":true,\"name\":\"days\",\"type\":{\"name\":\"days\"},\"supported\":true},\"minimumValue\":-292275054,\"maximumValue\":292278993,\"durationField\":{\"unitMillis\":31556952000,\"precise\":false,\"name\":\"years\",\"type\":{\"name\":\"years\"},\"supported\":true},\"name\":\"year\",\"type\":{\"durationType\":{\"name\":\"years\"},\"rangeDurationType\":null,\"name\":\"year\"},\"supported\":true},{\"rangeDurationField\":{\"unitMillis\":31556952000,\"precise\":false,\"name\":\"years\",\"type\":{\"name\":\"years\"},\"supported\":true},\"lenient\":false,\"leapDurationField\":{\"unitMillis\":86400000,\"precise\":true,\"name\":\"days\",\"type\":{\"name\":\"days\"},\"supported\":true},\"minimumValue\":1,\"maximumValue\":12,\"durationField\":{\"unitMillis\":2629746000,\"precise\":false,\"name\":\"months\",\"type\":{\"name\":\"months\"},\"supported\":true},\"name\":\"monthOfYear\",\"type\":{\"durationType\":{\"name\":\"months\"},\"rangeDurationType\":{\"name\":\"years\"},\"name\":\"monthOfYear\"},\"supported\":true},{\"rangeDurationField\":{\"unitMillis\":2629746000,\"precise\":false,\"name\":\"months\",\"type\":{\"name\":\"months\"},\"supported\":true},\"minimumValue\":1,\"maximumValue\":31,\"lenient\":false,\"unitMillis\":86400000,\"durationField\":{\"unitMillis\":86400000,\"precise\":true,\"name\":\"days\",\"type\":{\"name\":\"days\"},\"supported\":true},\"name\":\"dayOfMonth\",\"type\":{\"durationType\":{\"name\":\"days\"},\"rangeDurationType\":{\"name\":\"months\"},\"name\":\"dayOfMonth\"},\"supported\":true,\"leapDurationField\":null}],\"values\":[1979,9,6],\"fieldTypes\":[{\"durationType\":{\"name\":\"years\"},\"rangeDurationType\":null,\"name\":\"year\"},{\"durationType\":{\"name\":\"months\"},\"rangeDurationType\":{\"name\":\"years\"},\"name\":\"monthOfYear\"},{\"durationType\":{\"name\":\"days\"},\"rangeDurationType\":{\"name\":\"months\"},\"name\":\"dayOfMonth\"}]},\"geboorteDatumOpgemaakt\":\"06-09-1979\",\"overlijdensdatum\":{\"dayOfMonth\":12,\"dayOfWeek\":1,\"era\":1,\"dayOfYear\":132,\"year\":2014,\"chronology\":{\"zone\":{\"fixed\":true,\"id\":\"UTC\"}},\"centuryOfEra\":20,\"yearOfEra\":2014,\"yearOfCentury\":14,\"weekyear\":2014,\"monthOfYear\":5,\"weekOfWeekyear\":20,\"fields\":[{\"rangeDurationField\":null,\"lenient\":false,\"leapDurationField\":{\"unitMillis\":86400000,\"precise\":true,\"name\":\"days\",\"type\":{\"name\":\"days\"},\"supported\":true},\"minimumValue\":-292275054,\"maximumValue\":292278993,\"durationField\":{\"unitMillis\":31556952000,\"precise\":false,\"name\":\"years\",\"type\":{\"name\":\"years\"},\"supported\":true},\"name\":\"year\",\"type\":{\"durationType\":{\"name\":\"years\"},\"rangeDurationType\":null,\"name\":\"year\"},\"supported\":true},{\"rangeDurationField\":{\"unitMillis\":31556952000,\"precise\":false,\"name\":\"years\",\"type\":{\"name\":\"years\"},\"supported\":true},\"lenient\":false,\"leapDurationField\":{\"unitMillis\":86400000,\"precise\":true,\"name\":\"days\",\"type\":{\"name\":\"days\"},\"supported\":true},\"minimumValue\":1,\"maximumValue\":12,\"durationField\":{\"unitMillis\":2629746000,\"precise\":false,\"name\":\"months\",\"type\":{\"name\":\"months\"},\"supported\":true},\"name\":\"monthOfYear\",\"type\":{\"durationType\":{\"name\":\"months\"},\"rangeDurationType\":{\"name\":\"years\"},\"name\":\"monthOfYear\"},\"supported\":true},{\"rangeDurationField\":{\"unitMillis\":2629746000,\"precise\":false,\"name\":\"months\",\"type\":{\"name\":\"months\"},\"supported\":true},\"minimumValue\":1,\"maximumValue\":31,\"lenient\":false,\"unitMillis\":86400000,\"durationField\":{\"unitMillis\":86400000,\"precise\":true,\"name\":\"days\",\"type\":{\"name\":\"days\"},\"supported\":true},\"name\":\"dayOfMonth\",\"type\":{\"durationType\":{\"name\":\"days\"},\"rangeDurationType\":{\"name\":\"months\"},\"name\":\"dayOfMonth\"},\"supported\":true,\"leapDurationField\":null}],\"values\":[2014,5,12],\"fieldTypes\":[{\"durationType\":{\"name\":\"years\"},\"rangeDurationType\":null,\"name\":\"year\"},{\"durationType\":{\"name\":\"months\"},\"rangeDurationType\":{\"name\":\"years\"},\"name\":\"monthOfYear\"},{\"durationType\":{\"name\":\"days\"},\"rangeDurationType\":{\"name\":\"months\"},\"name\":\"dayOfMonth\"}]},\"geslacht\":Vrouw,\"burgerlijkeStaat\":Gehuwd,\"onderlingeRelaties\":null,\"bedrijven\":null}";
            // Gson gson = new Gson();
            //
            // JsonRelatie jsonRelatie = gson.fromJson(json, JsonRelatie.class);
            JsonRelatie jsonRelatie = new JsonRelatie();
            jsonRelatie.setZakelijkeKlant(true);

            JsonRekeningNummer jsonRekeningNummer = new JsonRekeningNummer();
            jsonRekeningNummer.setBic("bic");
            jsonRekeningNummer.setRekeningnummer("rekeningnummer");
            jsonRelatie.getRekeningnummers().add(jsonRekeningNummer);

            JsonRekeningNummer jsonRekeningNummer2 = new JsonRekeningNummer();
            jsonRekeningNummer2.setBic("bic2");
            jsonRekeningNummer2.setRekeningnummer("rekeningnummer2");
            jsonRelatie.getRekeningnummers().add(jsonRekeningNummer2);

            JsonTelefoonnummer jsonTelefoonnummer1 = new JsonTelefoonnummer();
            jsonTelefoonnummer1.setSoort("Werk");
            jsonTelefoonnummer1.setTelefoonnummer("telefoonnummer1");
            jsonRelatie.getTelefoonnummers().add(jsonTelefoonnummer1);

            JsonTelefoonnummer jsonTelefoonnummer2 = new JsonTelefoonnummer();
            jsonTelefoonnummer2.setSoort("Vast");
            jsonTelefoonnummer2.setTelefoonnummer("telefoonnummer2");
            jsonRelatie.getTelefoonnummers().add(jsonTelefoonnummer2);

            JsonPolis jsonPolis1 = new JsonPolis();
            jsonPolis1.setId(1L);
            jsonPolis1.setBetaalfrequentie("Maandelijks");
            jsonPolis1.setPolisNummer("12345");
            jsonPolis1.setIngangsDatum(new LocalDate().toString("yyyy-MM-dd"));
            jsonPolis1.setMaatschappij("Fa. List & Bedrog");
            jsonPolis1.setPremie("100 euro");
            jsonPolis1.setProlongatieDatum(new LocalDate().toString("yyyy-MM-dd"));
            jsonPolis1.setSoort("Autoverzekering");
            jsonPolis1.setWijzigingsDatum(new LocalDate().toString("yyyy-MM-dd"));

            jsonRelatie.getPolissen().add(jsonPolis1);

            JsonPolis jsonPolis2 = new JsonPolis();
            jsonPolis2.setId(1L);
            jsonPolis2.setBetaalfrequentie("Maandelijks");
            jsonPolis2.setPolisNummer("12345");
            jsonPolis2.setIngangsDatum(new LocalDate().toString("yyyy-MM-dd"));
            jsonPolis2.setMaatschappij("Fa. List & Bedrog");
            jsonPolis2.setPremie("100 euro");
            jsonPolis2.setProlongatieDatum(new LocalDate().toString("yyyy-MM-dd"));
            jsonPolis2.setSoort("Woonhuisverzekering");
            jsonPolis2.setWijzigingsDatum(new LocalDate().toString("yyyy-MM-dd"));

            JsonBijlage jsonBijlage = new JsonBijlage();
            // jsonBijlage.setBestandsNaam("Polis-5408096516-2.pdf");
            jsonPolis2.getBijlages().add(jsonBijlage);

            jsonRelatie.getPolissen().add(jsonPolis2);

            JsonBedrijf jsonBedrijf1 = new JsonBedrijf();
            // jsonBedrijf1.setId(1L);
            // jsonBedrijf1.setHuisnummer(33L);
            jsonBedrijf1.setKvk("kvknummer");
            jsonBedrijf1.setNaam("NaamBedrijf");
            jsonBedrijf1.setPlaats("PlaatsBedrijf");
            jsonBedrijf1.setPostcode("1234AA");
            jsonBedrijf1.setStraat("StraatBedrijf");
            jsonBedrijf1.setToevoeging("A");

            jsonRelatie.getBedrijven().add(jsonBedrijf1);

            GebruikerControllerTest.jsonRelatie = jsonRelatie;
        }
        return GebruikerControllerTest.jsonRelatie;
    }

    @Override
    @GET
    @Path("/lijstRelaties")
    @Produces(MediaType.APPLICATION_JSON)
    public JsonLijstRelaties lijstRelaties(@QueryParam("weglaten") String weglaten) {
        if (GebruikerControllerTest.jsonLijstRelaties == null) {
            JsonRelatie jsonRelatie = new JsonRelatie();
            jsonRelatie.setId(1L);
            jsonRelatie.setVoornaam("voornaam1");
            jsonRelatie.setTussenvoegsel("tussenvoegsel1");
            jsonRelatie.setAchternaam("achternaam1");
            jsonRelatie.setGeboorteDatum("01-01-2001");
            jsonRelatie.setAdresOpgemaakt("adresOpgemaakt1");
            GebruikerControllerTest.jsonLijstRelaties = new JsonLijstRelaties();
            GebruikerControllerTest.jsonLijstRelaties.getJsonRelaties().add(jsonRelatie);
        }
        System.out.println(GebruikerControllerTest.jsonLijstRelaties);
        return GebruikerControllerTest.jsonLijstRelaties;
    }

    @Override
    @POST
    @Path("/opslaan")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response opslaan(JsonRelatie jsonRelatie) {
        GebruikerControllerTest.jsonRelatie = jsonRelatie;
        return null;
    }

    @Override
    @POST
    @Path("/opslaanBedrijf")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response opslaanBedrijf(JsonBedrijf jsonBedrijf) {
        System.out.println(jsonBedrijf);
        return null;
    }

    @Override
    @GET
    @Path("/verwijderen")
    @Produces(MediaType.TEXT_PLAIN)
    public Response verwijderen(@QueryParam("id") Long id) {
        GebruikerControllerTest.id = id;

        return null;
    }

    @Override
    @GET
    @Path("/toevoegenRelatieRelatie")
    @Produces(MediaType.TEXT_PLAIN)
    public String toevoegenRelatieRelatie(@QueryParam("idAanToevoegen") String sidAanToevoegen, @QueryParam("idToeTeVoegen") String sidToeTeVoegen, @QueryParam("soortRelatie") String soortRelatie) {
        return null;
    }

    @Override
    @GET
    @Path("/isIngelogd")
    @Produces(MediaType.TEXT_PLAIN)
    public String isIngelogd() {
        Gson gson = new Gson();
        return gson.toJson(true);
    }

    @Override
    public JsonRelatie lees(String id) {
        // TODO Auto-generated method stub
        return null;
    }
    //
    // @GET
    // @Path("/uitloggen")
    // @Produces(MediaType.TEXT_PLAIN)
    // public String loguit(@Context HttpServletRequest request) {
    // return null;
    // }

}
