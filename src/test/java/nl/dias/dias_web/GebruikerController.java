package nl.dias.dias_web;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import nl.dias.domein.json.JsonRelatie;

import com.google.gson.Gson;

@Path("/gebruiker")
public class GebruikerController {// implements GebruikerControllerInterface {
    public static Long id;
    public static JsonRelatie jsonRelatie;

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
        if (GebruikerController.jsonRelatie == null) {
            String json = "{\"id\":3,\"identificatie\":\"p@h.nl\",\"voornaam\":\"Patrick\",\"tussenvoegsel\":\"van der\",\"achternaam\":\"Heidotting\",\"straat\":\"Eemslandweg\",\"huisnummer\":41,\"toevoeging\":JJ,\"postcode\":7894AB,\"plaats\":\"Zwartemeer\",\"adresOpgemaakt\":\"Eemslandweg 41 Zwartemeer \",\"telefoonnummers\":[],\"bsn\":103127586,\"rekeningnummers\":[],\"kantoor\":1,\"opmerkingen\":[],\"geboorteDatum\":{\"dayOfMonth\":6,\"dayOfWeek\":4,\"era\":1,\"dayOfYear\":249,\"year\":1979,\"chronology\":{\"zone\":{\"fixed\":true,\"id\":\"UTC\"}},\"centuryOfEra\":19,\"yearOfEra\":1979,\"yearOfCentury\":79,\"weekyear\":1979,\"monthOfYear\":9,\"weekOfWeekyear\":36,\"fields\":[{\"rangeDurationField\":null,\"lenient\":false,\"leapDurationField\":{\"unitMillis\":86400000,\"precise\":true,\"name\":\"days\",\"type\":{\"name\":\"days\"},\"supported\":true},\"minimumValue\":-292275054,\"maximumValue\":292278993,\"durationField\":{\"unitMillis\":31556952000,\"precise\":false,\"name\":\"years\",\"type\":{\"name\":\"years\"},\"supported\":true},\"name\":\"year\",\"type\":{\"durationType\":{\"name\":\"years\"},\"rangeDurationType\":null,\"name\":\"year\"},\"supported\":true},{\"rangeDurationField\":{\"unitMillis\":31556952000,\"precise\":false,\"name\":\"years\",\"type\":{\"name\":\"years\"},\"supported\":true},\"lenient\":false,\"leapDurationField\":{\"unitMillis\":86400000,\"precise\":true,\"name\":\"days\",\"type\":{\"name\":\"days\"},\"supported\":true},\"minimumValue\":1,\"maximumValue\":12,\"durationField\":{\"unitMillis\":2629746000,\"precise\":false,\"name\":\"months\",\"type\":{\"name\":\"months\"},\"supported\":true},\"name\":\"monthOfYear\",\"type\":{\"durationType\":{\"name\":\"months\"},\"rangeDurationType\":{\"name\":\"years\"},\"name\":\"monthOfYear\"},\"supported\":true},{\"rangeDurationField\":{\"unitMillis\":2629746000,\"precise\":false,\"name\":\"months\",\"type\":{\"name\":\"months\"},\"supported\":true},\"minimumValue\":1,\"maximumValue\":31,\"lenient\":false,\"unitMillis\":86400000,\"durationField\":{\"unitMillis\":86400000,\"precise\":true,\"name\":\"days\",\"type\":{\"name\":\"days\"},\"supported\":true},\"name\":\"dayOfMonth\",\"type\":{\"durationType\":{\"name\":\"days\"},\"rangeDurationType\":{\"name\":\"months\"},\"name\":\"dayOfMonth\"},\"supported\":true,\"leapDurationField\":null}],\"values\":[1979,9,6],\"fieldTypes\":[{\"durationType\":{\"name\":\"years\"},\"rangeDurationType\":null,\"name\":\"year\"},{\"durationType\":{\"name\":\"months\"},\"rangeDurationType\":{\"name\":\"years\"},\"name\":\"monthOfYear\"},{\"durationType\":{\"name\":\"days\"},\"rangeDurationType\":{\"name\":\"months\"},\"name\":\"dayOfMonth\"}]},\"geboorteDatumOpgemaakt\":\"06-09-1979\",\"overlijdensdatum\":{\"dayOfMonth\":12,\"dayOfWeek\":1,\"era\":1,\"dayOfYear\":132,\"year\":2014,\"chronology\":{\"zone\":{\"fixed\":true,\"id\":\"UTC\"}},\"centuryOfEra\":20,\"yearOfEra\":2014,\"yearOfCentury\":14,\"weekyear\":2014,\"monthOfYear\":5,\"weekOfWeekyear\":20,\"fields\":[{\"rangeDurationField\":null,\"lenient\":false,\"leapDurationField\":{\"unitMillis\":86400000,\"precise\":true,\"name\":\"days\",\"type\":{\"name\":\"days\"},\"supported\":true},\"minimumValue\":-292275054,\"maximumValue\":292278993,\"durationField\":{\"unitMillis\":31556952000,\"precise\":false,\"name\":\"years\",\"type\":{\"name\":\"years\"},\"supported\":true},\"name\":\"year\",\"type\":{\"durationType\":{\"name\":\"years\"},\"rangeDurationType\":null,\"name\":\"year\"},\"supported\":true},{\"rangeDurationField\":{\"unitMillis\":31556952000,\"precise\":false,\"name\":\"years\",\"type\":{\"name\":\"years\"},\"supported\":true},\"lenient\":false,\"leapDurationField\":{\"unitMillis\":86400000,\"precise\":true,\"name\":\"days\",\"type\":{\"name\":\"days\"},\"supported\":true},\"minimumValue\":1,\"maximumValue\":12,\"durationField\":{\"unitMillis\":2629746000,\"precise\":false,\"name\":\"months\",\"type\":{\"name\":\"months\"},\"supported\":true},\"name\":\"monthOfYear\",\"type\":{\"durationType\":{\"name\":\"months\"},\"rangeDurationType\":{\"name\":\"years\"},\"name\":\"monthOfYear\"},\"supported\":true},{\"rangeDurationField\":{\"unitMillis\":2629746000,\"precise\":false,\"name\":\"months\",\"type\":{\"name\":\"months\"},\"supported\":true},\"minimumValue\":1,\"maximumValue\":31,\"lenient\":false,\"unitMillis\":86400000,\"durationField\":{\"unitMillis\":86400000,\"precise\":true,\"name\":\"days\",\"type\":{\"name\":\"days\"},\"supported\":true},\"name\":\"dayOfMonth\",\"type\":{\"durationType\":{\"name\":\"days\"},\"rangeDurationType\":{\"name\":\"months\"},\"name\":\"dayOfMonth\"},\"supported\":true,\"leapDurationField\":null}],\"values\":[2014,5,12],\"fieldTypes\":[{\"durationType\":{\"name\":\"years\"},\"rangeDurationType\":null,\"name\":\"year\"},{\"durationType\":{\"name\":\"months\"},\"rangeDurationType\":{\"name\":\"years\"},\"name\":\"monthOfYear\"},{\"durationType\":{\"name\":\"days\"},\"rangeDurationType\":{\"name\":\"months\"},\"name\":\"dayOfMonth\"}]},\"geslacht\":Vrouw,\"burgerlijkeStaat\":Gehuwd,\"onderlingeRelaties\":null,\"bedrijven\":null}";
            Gson gson = new Gson();
            GebruikerController.jsonRelatie = gson.fromJson(json, JsonRelatie.class);
        }
        return GebruikerController.jsonRelatie;
    }

    @GET
    @Path("/lijstRelaties")
    @Produces(MediaType.TEXT_PLAIN)
    public String lijstRelaties(@QueryParam("weglaten") String weglaten) {
        return null;
    }

    @POST
    @Path("/opslaan")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response opslaan(JsonRelatie jsonRelatie) {
        GebruikerController.jsonRelatie = jsonRelatie;
        return null;
    }

    @GET
    @Path("/verwijderen")
    @Produces(MediaType.TEXT_PLAIN)
    public Response verwijderen(@QueryParam("id") Long id) {
        GebruikerController.id = id;

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
