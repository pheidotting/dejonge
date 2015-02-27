package nl.dias.web.medewerker;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import nl.dias.domein.Aangifte;
import nl.dias.domein.Relatie;
import nl.dias.domein.json.JsonAangifte;
import nl.dias.service.AangifteService;
import nl.dias.service.GebruikerService;
import nl.dias.web.mapper.AangifteMapper;

import org.apache.log4j.Logger;
import org.joda.time.LocalDate;

import com.sun.jersey.api.core.InjectParam;

@Path("/aangifte")
public class AangifteController {
    private final static Logger LOGGER = Logger.getLogger(AangifteController.class);

    @InjectParam
    private AangifteService aangifteService;
    @InjectParam
    private GebruikerService gebruikerService;
    @InjectParam
    private AangifteMapper aangifteMapper;

    @GET
    @Path("/openAangiftes")
    @Produces(MediaType.APPLICATION_JSON)
    public List<JsonAangifte> openAangiftes(@QueryParam("relatie") Long relatie) {
        return aangifteMapper.mapAllNaarJson(aangifteService.getOpenstaandeAangiftes((Relatie) gebruikerService.lees(relatie)));
    }

    @POST
    @Path("/afronden")
    public Response afronden(@QueryParam("datum") String datum, @QueryParam("id") Long id) {
        aangifteService.afronden(id, new LocalDate(datum));
        return Response.ok().build();
    }

    @POST
    @Path("/opslaan")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response opslaan(JsonAangifte jsonAangifte) {
        Aangifte aangifte = aangifteMapper.mapVanJson(jsonAangifte);
        aangifteService.opslaan(aangifte);

        return Response.ok(aangifte.getId()).build();
    }

    @GET
    @Path("/geslotenAangiftes")
    @Produces(MediaType.APPLICATION_JSON)
    public List<JsonAangifte> geslotenAangiftes(@QueryParam("relatie") Long relatie) {
        return aangifteMapper.mapAllNaarJson(aangifteService.getAfgeslotenAangiftes((Relatie) gebruikerService.lees(relatie)));
    }

}
