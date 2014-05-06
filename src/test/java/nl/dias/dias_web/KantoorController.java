package nl.dias.dias_web;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

import com.google.gson.Gson;

@Path("/kantoor")
public class KantoorController {// implements KantoorControllerInterface {
    private Gson gson = new Gson();

    // @GET
    // @Path("/aanmelden")
    // @Produces(MediaType.TEXT_PLAIN)
    // public String aanmelden(@QueryParam("identificatie") String
    // identificatie, @QueryParam("waar") String waar, @Context
    // HttpServletRequest request) {
    // // TODO Auto-generated method stub
    // return "A";
    // }
    //
    // public String leesKantoor(@Context HttpServletRequest request) {
    // return null;
    // }

    @GET
    @Path("/lees")
    // @Produces(MediaType.TEXT_PLAIN)
    public String leesKantoor() {
        System.out.println("SAFDsdfsdf");
        return gson.toJson("B");
    }

    // @GET
    // @Path("/opslaan")
    // @Produces(MediaType.TEXT_PLAIN)
    // public String opslaan(@QueryParam("kantoor") String strKantoor, @Context
    // HttpServletRequest request) {
    // // TODO Auto-generated method stub
    // return "C";
    // }
    //
    // @GET
    // @Path("/verwijder")
    // @Produces(MediaType.TEXT_PLAIN)
    // public String verwijder(@QueryParam("id") String id, @Context
    // HttpServletRequest request) {
    // // TODO Auto-generated method stub
    // return "D";
    // }
}
