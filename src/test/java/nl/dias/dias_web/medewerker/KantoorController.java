package nl.dias.dias_web.medewerker;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import com.google.gson.Gson;

@Path("/kantoor")
public class KantoorController {// implements KantoorControllerInterface {
    private final Gson gson = new Gson();

    @GET
    @Path("/aanmelden")
    @Produces(MediaType.TEXT_PLAIN)
    public String aanmelden(@QueryParam("identificatie") String identificatie, @QueryParam("waar") String waar, @Context HttpServletRequest request) {
        return "A";
    }

    public String leesKantoor(@Context HttpServletRequest request) {
        return null;
    }

    @GET
    @Path("/lees")
    @Produces(MediaType.TEXT_PLAIN)
    public String leesKantoor() {
        return gson.toJson("B");
    }

    @GET
    @Path("/opslaan")
    @Produces(MediaType.TEXT_PLAIN)
    public String opslaan(@QueryParam("kantoor") String strKantoor, @Context HttpServletRequest request) {
        return "C";
    }

    @GET
    @Path("/verwijder")
    @Produces(MediaType.TEXT_PLAIN)
    public String verwijder(@QueryParam("id") String id, @Context HttpServletRequest request) {
        return "D";
    }
}
