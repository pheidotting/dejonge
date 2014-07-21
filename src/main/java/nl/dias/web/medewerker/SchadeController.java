package nl.dias.web.medewerker;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import nl.dias.domein.Relatie;
import nl.dias.domein.Schade;
import nl.dias.domein.json.JsonFoutmelding;
import nl.dias.domein.json.JsonSchade;
import nl.dias.service.GebruikerService;
import nl.dias.service.SchadeService;
import nl.dias.web.mapper.SchadeMapper;

import org.apache.log4j.Logger;

import com.sun.jersey.api.core.InjectParam;

@Path("/schade")
public class SchadeController {
    private final static Logger LOGGER = Logger.getLogger(SchadeController.class);

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
        LOGGER.debug(jsonSchade);

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
}
