package nl.dias.dias_web;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import nl.dias.domein.json.JsonRelatie;

import com.google.gson.Gson;

@Path("/gebruiker")
public class GebruikerController {// implements GebruikerControllerInterface {
    private Gson gson = new Gson();

    @GET
    @Path("/verwijder")
    @Produces(MediaType.TEXT_PLAIN)
    public String verwijder(@QueryParam("id") String id) {
        return null;
    }

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
    public JsonRelatie lees(@QueryParam("id") String id) {
        String json = "{\"id\":3,\"identificatie\":\"p@h.nl\",\"voornaam\":\"Patrick\",\"tussenvoegsel\":\"van der\",\"achternaam\":\"Heidotting\",\"straat\":\"Eemslandweg\",\"huisnummer\":41,\"toevoeging\":JJ,\"postcode\":7894AB,\"plaats\":\"Zwartemeer\",\"adresOpgemaakt\":\"Eemslandweg 41 Zwartemeer \",\"telefoonnummers\":[],\"bsn\":103127586,\"rekeningnummers\":[],\"kantoor\":1,\"opmerkingen\":[],\"geboorteDatum\":{\"dayOfMonth\":6,\"dayOfWeek\":4,\"era\":1,\"dayOfYear\":249,\"year\":1979,\"chronology\":{\"zone\":{\"fixed\":true,\"id\":\"UTC\"}},\"centuryOfEra\":19,\"yearOfEra\":1979,\"yearOfCentury\":79,\"weekyear\":1979,\"monthOfYear\":9,\"weekOfWeekyear\":36,\"fields\":[{\"rangeDurationField\":null,\"lenient\":false,\"leapDurationField\":{\"unitMillis\":86400000,\"precise\":true,\"name\":\"days\",\"type\":{\"name\":\"days\"},\"supported\":true},\"minimumValue\":-292275054,\"maximumValue\":292278993,\"durationField\":{\"unitMillis\":31556952000,\"precise\":false,\"name\":\"years\",\"type\":{\"name\":\"years\"},\"supported\":true},\"name\":\"year\",\"type\":{\"durationType\":{\"name\":\"years\"},\"rangeDurationType\":null,\"name\":\"year\"},\"supported\":true},{\"rangeDurationField\":{\"unitMillis\":31556952000,\"precise\":false,\"name\":\"years\",\"type\":{\"name\":\"years\"},\"supported\":true},\"lenient\":false,\"leapDurationField\":{\"unitMillis\":86400000,\"precise\":true,\"name\":\"days\",\"type\":{\"name\":\"days\"},\"supported\":true},\"minimumValue\":1,\"maximumValue\":12,\"durationField\":{\"unitMillis\":2629746000,\"precise\":false,\"name\":\"months\",\"type\":{\"name\":\"months\"},\"supported\":true},\"name\":\"monthOfYear\",\"type\":{\"durationType\":{\"name\":\"months\"},\"rangeDurationType\":{\"name\":\"years\"},\"name\":\"monthOfYear\"},\"supported\":true},{\"rangeDurationField\":{\"unitMillis\":2629746000,\"precise\":false,\"name\":\"months\",\"type\":{\"name\":\"months\"},\"supported\":true},\"minimumValue\":1,\"maximumValue\":31,\"lenient\":false,\"unitMillis\":86400000,\"durationField\":{\"unitMillis\":86400000,\"precise\":true,\"name\":\"days\",\"type\":{\"name\":\"days\"},\"supported\":true},\"name\":\"dayOfMonth\",\"type\":{\"durationType\":{\"name\":\"days\"},\"rangeDurationType\":{\"name\":\"months\"},\"name\":\"dayOfMonth\"},\"supported\":true,\"leapDurationField\":null}],\"values\":[1979,9,6],\"fieldTypes\":[{\"durationType\":{\"name\":\"years\"},\"rangeDurationType\":null,\"name\":\"year\"},{\"durationType\":{\"name\":\"months\"},\"rangeDurationType\":{\"name\":\"years\"},\"name\":\"monthOfYear\"},{\"durationType\":{\"name\":\"days\"},\"rangeDurationType\":{\"name\":\"months\"},\"name\":\"dayOfMonth\"}]},\"geboorteDatumOpgemaakt\":\"06-09-1979\",\"overlijdensdatum\":{\"dayOfMonth\":12,\"dayOfWeek\":1,\"era\":1,\"dayOfYear\":132,\"year\":2014,\"chronology\":{\"zone\":{\"fixed\":true,\"id\":\"UTC\"}},\"centuryOfEra\":20,\"yearOfEra\":2014,\"yearOfCentury\":14,\"weekyear\":2014,\"monthOfYear\":5,\"weekOfWeekyear\":20,\"fields\":[{\"rangeDurationField\":null,\"lenient\":false,\"leapDurationField\":{\"unitMillis\":86400000,\"precise\":true,\"name\":\"days\",\"type\":{\"name\":\"days\"},\"supported\":true},\"minimumValue\":-292275054,\"maximumValue\":292278993,\"durationField\":{\"unitMillis\":31556952000,\"precise\":false,\"name\":\"years\",\"type\":{\"name\":\"years\"},\"supported\":true},\"name\":\"year\",\"type\":{\"durationType\":{\"name\":\"years\"},\"rangeDurationType\":null,\"name\":\"year\"},\"supported\":true},{\"rangeDurationField\":{\"unitMillis\":31556952000,\"precise\":false,\"name\":\"years\",\"type\":{\"name\":\"years\"},\"supported\":true},\"lenient\":false,\"leapDurationField\":{\"unitMillis\":86400000,\"precise\":true,\"name\":\"days\",\"type\":{\"name\":\"days\"},\"supported\":true},\"minimumValue\":1,\"maximumValue\":12,\"durationField\":{\"unitMillis\":2629746000,\"precise\":false,\"name\":\"months\",\"type\":{\"name\":\"months\"},\"supported\":true},\"name\":\"monthOfYear\",\"type\":{\"durationType\":{\"name\":\"months\"},\"rangeDurationType\":{\"name\":\"years\"},\"name\":\"monthOfYear\"},\"supported\":true},{\"rangeDurationField\":{\"unitMillis\":2629746000,\"precise\":false,\"name\":\"months\",\"type\":{\"name\":\"months\"},\"supported\":true},\"minimumValue\":1,\"maximumValue\":31,\"lenient\":false,\"unitMillis\":86400000,\"durationField\":{\"unitMillis\":86400000,\"precise\":true,\"name\":\"days\",\"type\":{\"name\":\"days\"},\"supported\":true},\"name\":\"dayOfMonth\",\"type\":{\"durationType\":{\"name\":\"days\"},\"rangeDurationType\":{\"name\":\"months\"},\"name\":\"dayOfMonth\"},\"supported\":true,\"leapDurationField\":null}],\"values\":[2014,5,12],\"fieldTypes\":[{\"durationType\":{\"name\":\"years\"},\"rangeDurationType\":null,\"name\":\"year\"},{\"durationType\":{\"name\":\"months\"},\"rangeDurationType\":{\"name\":\"years\"},\"name\":\"monthOfYear\"},{\"durationType\":{\"name\":\"days\"},\"rangeDurationType\":{\"name\":\"months\"},\"name\":\"dayOfMonth\"}]},\"geslacht\":Vrouw,\"burgerlijkeStaat\":Gehuwd,\"onderlingeRelaties\":null,\"bedrijven\":null}";
        Gson gson = new Gson();
        JsonRelatie jsonR = gson.fromJson(json, JsonRelatie.class);
        return jsonR;
        //
        // RelatieJson relatieJson = FinaalStatisch.relatieJson;
        //
        // if (relatieJson == null) {
        // relatieJson = new RelatieJson(new Relatie());
        // relatieJson.setId(0L);
        // relatieJson.setAchternaam("Heidotting");
        // relatieJson.setVoornaam("Patrick");
        // relatieJson.setTussenvoegsel("van der");
        // relatieJson.setHuisnummer(41L);
        // relatieJson.setPlaats("Zwartemeer");
        // relatieJson.setPostcode("7894AB");
        // relatieJson.setStraat("Eemslandweg");
        // relatieJson.setToevoeging("A");
        // relatieJson.setBsn("103127586");
        // relatieJson.setBurgerlijkeStaat(BurgerlijkeStaat.O);
        // relatieJson.setGeboorteDatum(new LocalDate(1979, 9, 6));
        // relatieJson.setGeslacht(Geslacht.M);
        // relatieJson.setIdentificatie("patrick@heidotting.nl");
        // relatieJson.setZakelijkeKlant(true);
        //
        // RekeningNummer rekeningNummer = new RekeningNummer();
        // rekeningNummer.setBic("NLSNS");
        // rekeningNummer.setRekeningnummer("NL96SNSB0907007406");
        //
        // relatieJson.getRekeningnummers().add(rekeningNummer);
        //
        // Telefoonnummer telefoonnummer = new Telefoonnummer();
        // telefoonnummer.setSoort(TelefoonnummerSoort.Mobiel);
        // telefoonnummer.setTelefoonnummer("06-21564744");
        //
        // relatieJson.getTelefoonnummers().add(telefoonnummer);
        //
        // Bedrijf bedrijf = new Bedrijf();
        // bedrijf.setKvk("303834848");
        // bedrijf.setNaam("Fa. List & Bedrog");
        //
        // relatieJson.getBedrijven().add(bedrijf);
        //
        // Bedrijf bedrijf2 = new Bedrijf();
        // bedrijf2.setKvk("339994994");
        // bedrijf2.setNaam("naamBedrijf2");
        //
        // relatieJson.getBedrijven().add(bedrijf2);
        //
        // AutoVerzekering autoVerzekering = new AutoVerzekering();
        // autoVerzekering.setId(46L);
        // autoVerzekering.setIngangsDatum(new LocalDate(2013, 9, 6));
        // autoVerzekering.setKenteken("46-NLV-5");
        // autoVerzekering.setPolisNummer("polisNummer");
        // autoVerzekering.setSoortAutoVerzekering(SoortAutoVerzekering.Auto);
        // autoVerzekering.setPremie(null);
        //
        // VerzekeringsMaatschappij maatschappij = new
        // VerzekeringsMaatschappij();
        // maatschappij.setNaam("Dit is een verzekeringsmaatschappij");
        // maatschappij.setTonen(true);
        // autoVerzekering.setMaatschappij(maatschappij);
        //
        // relatieJson.getPolissen().add(autoVerzekering);
        //
        // OngevallenVerzekering ongevallenVerzekering = new
        // OngevallenVerzekering();
        // ongevallenVerzekering.setIngangsDatum(new LocalDate(2011, 5, 4));
        // ongevallenVerzekering.setMaatschappij(maatschappij);
        // ongevallenVerzekering.setPolisNummer("29084844488");
        // ongevallenVerzekering.setPremie(new Bedrag(123L));
        //
        // Bijlage bijlage1 = new Bijlage();
        // bijlage1.setBestandsNaam("1.pdf");
        // Bijlage bijlage2 = new Bijlage();
        // bijlage2.setBestandsNaam("2.pdf");
        //
        // ongevallenVerzekering.getBijlages().add(bijlage1);
        // ongevallenVerzekering.getBijlages().add(bijlage2);
        //
        // relatieJson.getPolissen().add(ongevallenVerzekering);
        // }
        // GsonBuilder gsonBilder = new GsonBuilder();
        // gsonBilder.registerTypeAdapter(Polis.class, new
        // AbstractElementAdapter());
        // gson = gsonBilder.create();
        //
        // return gson.toJson(relatieJson);
    }

    @GET
    @Path("/lijstRelaties")
    @Produces(MediaType.TEXT_PLAIN)
    public String lijstRelaties(@QueryParam("weglaten") String weglaten) {
        return null;
    }

    @GET
    @Path("/opslaan")
    @Produces(MediaType.TEXT_PLAIN)
    public String opslaan(@QueryParam("medewerker") String relatie) {
        return null;
    }

    @GET
    @Path("/verwijderen")
    @Produces(MediaType.TEXT_PLAIN)
    public String verwijderen(@QueryParam("medewerker") String relatie) {
        return null;
    }

    @GET
    @Path("/toevoegenRelatieRelatie")
    @Produces(MediaType.TEXT_PLAIN)
    public String toevoegenRelatieRelatie(@QueryParam("idAanToevoegen") String sidAanToevoegen, @QueryParam("idToeTeVoegen") String sidToeTeVoegen, @QueryParam("soortRelatie") String soortRelatie) {
        return null;
    }

    // @GET
    // @Path("/isIngelogd")
    // @Produces(MediaType.TEXT_PLAIN)
    // public String isIngelogd(@Context HttpServletRequest request) {
    // return gson.toJson(true);
    // }
    //
    // @GET
    // @Path("/uitloggen")
    // @Produces(MediaType.TEXT_PLAIN)
    // public String loguit(@Context HttpServletRequest request) {
    // return null;
    // }

}
