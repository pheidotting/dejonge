package nl.dias.web.medewerker;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import nl.dias.domein.json.JsonFoutmelding;
import nl.dias.domein.json.JsonOpmerking;
import nl.dias.service.OpmerkingService;
import nl.dias.web.mapper.OpmerkingMapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sun.jersey.api.core.InjectParam;

@Path("/opmerking")
public class OpmerkingController {
    private final static Logger LOGGER = LoggerFactory.getLogger(OpmerkingController.class);

    @InjectParam
    private OpmerkingService opmerkingService;
    @InjectParam
    private OpmerkingMapper opmerkingMapper;

    @GET
    @Path("/lijst")
    @Produces(MediaType.APPLICATION_JSON)
    public List<JsonOpmerking> lijstOpmerkingen(Long relatie) {
        return opmerkingMapper.mapAllNaarJson(opmerkingService.alleOpmerkingenVoorRelatie(relatie));
    }

    @POST
    @Path("/opslaan")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response opslaan(JsonOpmerking jsonOpmerking) {
        try {
            opmerkingService.opslaan(opmerkingMapper.mapVanJson(jsonOpmerking));
        } catch (IllegalArgumentException e) {
            LOGGER.debug("Fout opgetreden bij opslaan Opmerking", e);
            return Response.status(500).entity(new JsonFoutmelding(e.getMessage())).build();
        }
        return Response.status(200).entity(new JsonFoutmelding()).build();
    }

    @GET
    @Path("/nieuw")
    @Produces(MediaType.APPLICATION_JSON)
    public JsonOpmerking nieuw() {
        return new JsonOpmerking();
    }

    public void setOpmerkingService(OpmerkingService opmerkingService) {
        this.opmerkingService = opmerkingService;
    }

    public void setOpmerkingMapper(OpmerkingMapper opmerkingMapper) {
        this.opmerkingMapper = opmerkingMapper;
    }
}
