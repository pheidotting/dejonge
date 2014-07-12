package nl.dias.dias_web.medewerker;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import nl.dias.domein.json.JsonFoutmelding;
import nl.dias.domein.json.JsonSchade;

@Path("/schade")
public class SchadeController {

    @Path("/opslaan")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response opslaan(JsonSchade jsonSchade) {
        System.out.println(jsonSchade);

        return Response.status(202).entity(new JsonFoutmelding()).build();
    }
}
