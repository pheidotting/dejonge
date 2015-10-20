package nl.dias.web.medewerker;

import com.sun.jersey.api.core.InjectParam;
import nl.dias.domein.Aangifte;
import nl.dias.domein.Gebruiker;
import nl.dias.domein.Relatie;
import nl.dias.domein.json.JsonAangifte;
import nl.dias.service.AangifteService;
import nl.dias.service.AuthorisatieService;
import nl.dias.service.GebruikerService;
import nl.dias.web.mapper.AangifteMapper;
<<<<<<< HEAD

import org.joda.time.LocalDate;import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

=======
import org.joda.time.LocalDate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
>>>>>>> 561c015bc16347b4be76e8f0

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/aangifte")
public class AangifteController {
    private final static Logger LOGGER = LoggerFactory.getLogger(AangifteController.class);

    @InjectParam
    private AangifteService aangifteService;
    @InjectParam
    private GebruikerService gebruikerService;
    @InjectParam
    private AangifteMapper aangifteMapper;
    @Context
    private HttpServletRequest httpServletRequest;
    @InjectParam
    private AuthorisatieService authorisatieService;

    @GET
    @Path("/openAangiftes")
    @Produces(MediaType.APPLICATION_JSON)
    public List<JsonAangifte> openAangiftes(@QueryParam("relatie") Long relatie) {
        return aangifteMapper.mapAllNaarJson(aangifteService.getOpenstaandeAangiftes((Relatie) gebruikerService.lees(relatie)));
    }

    @POST
    @Path("/afronden")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response afronden(Long id) {
        LOGGER.info("Afronden Aangifte met id " + id);
        try {
            aangifteService.afronden(id, LocalDate.now(), getGebruiker());
        } catch (Exception e) {
            return Response.status(500).build();
        }
        return Response.ok(id).build();
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

    private Gebruiker getGebruiker() {
        String sessie = null;
        if (httpServletRequest.getSession().getAttribute("sessie") != null && !"".equals(httpServletRequest.getSession().getAttribute("sessie"))) {
            sessie = httpServletRequest.getSession().getAttribute("sessie").toString();
        }

        return authorisatieService.getIngelogdeGebruiker(httpServletRequest, sessie, httpServletRequest.getRemoteAddr());

    }
}
