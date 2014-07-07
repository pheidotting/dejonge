package nl.dias.dias_web.authorisatie;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import nl.dias.domein.json.IngelogdeGebruiker;
import nl.dias.domein.json.Inloggen;
import nl.dias.domein.json.JsonFoutmelding;

@Path("/authorisatie")
public class AuthorisatieController {

    @POST
    @Path("/inloggen")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response inloggen(Inloggen inloggen) {
        System.out.println(inloggen);
        return Response.status(200).entity(new JsonFoutmelding("D'r ging wat fout, maar ik weet niet wat.")).build();
    }

    @GET
    @Path("/ingelogdeGebruiker")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getIngelogdeGebruiker() {
        IngelogdeGebruiker ingelogdeGebruiker = new IngelogdeGebruiker();
        ingelogdeGebruiker.setGebruikersnaam("Patrick Heidotting");
        ingelogdeGebruiker.setKantoor("Fa. List & Bedrog");

        return Response.status(200).entity(ingelogdeGebruiker).build();
        // return Response.status(401).entity(null).build();
    }

    @GET
    @Path("/isIngelogd")
    @Produces(MediaType.APPLICATION_JSON)
    public boolean isIngelogd() {
        return true;
    }
}
