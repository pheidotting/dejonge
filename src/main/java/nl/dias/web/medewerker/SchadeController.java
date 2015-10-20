package nl.dias.web.medewerker;

import com.sun.jersey.api.core.InjectParam;
import nl.dias.domein.Relatie;
import nl.dias.domein.Schade;
import nl.dias.domein.json.JsonFoutmelding;
import nl.dias.domein.json.JsonSchade;
import nl.dias.service.GebruikerService;
import nl.dias.service.SchadeService;
import nl.dias.web.mapper.SchadeMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

<<<<<<< HEAD
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sun.jersey.api.core.InjectParam;
=======
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
>>>>>>> 561c015bc16347b4be76e8f0

@Path("/schade")
public class SchadeController {
    private final static Logger LOGGER = LoggerFactory.getLogger(SchadeController.class);

    @InjectParam
    private SchadeService schadeService;
    @InjectParam
    private SchadeMapper schadeMapper;
    @InjectParam
    private GebruikerService gebruikerService;

    @Path("/opslaan")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response opslaan(JsonSchade jsonSchade) {
<<<<<<< HEAD
        LOGGER.debug("{}",jsonSchade);
=======
        LOGGER.debug("{}", jsonSchade);
>>>>>>> 561c015bc16347b4be76e8f0

        Schade schade = schadeMapper.mapVanJson(jsonSchade);
        schadeService.opslaan(schade, jsonSchade.getSoortSchade(), jsonSchade.getPolis(), jsonSchade.getStatusSchade());

        return Response.status(202).entity(new JsonFoutmelding()).build();
    }

    @GET
    @Path("/lijst")
    @Produces(MediaType.APPLICATION_JSON)
    public List<JsonSchade> lijst(@QueryParam("relatieId") String relatieId) {
        Relatie relatie = (Relatie) gebruikerService.lees(Long.valueOf(relatieId));

        Set<Schade> schades = new HashSet<>();
        for (Schade schade : schadeService.alleSchadesBijRelatie(relatie)) {
            schades.add(schade);
        }

        return schadeMapper.mapAllNaarJson(schades);
    }

    @GET
    @Path("/lees")
    @Produces(MediaType.APPLICATION_JSON)
    public JsonSchade lees(@QueryParam("id") String id) {
        return schadeMapper.mapNaarJson(schadeService.lees(Long.valueOf(id)));
    }

    @GET
    @Path("/verwijder")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response verwijder(@QueryParam("id") Long id) {
        LOGGER.debug("verwijderen Schade met id " + id);
        try {
            schadeService.verwijder(id);
        } catch (IllegalArgumentException e) {
            LOGGER.error("Fout bij verwijderen Schade", e);
            return Response.status(500).entity(new JsonFoutmelding(e.getMessage())).build();
        }
        return Response.status(202).entity(new JsonFoutmelding()).build();
    }

}
