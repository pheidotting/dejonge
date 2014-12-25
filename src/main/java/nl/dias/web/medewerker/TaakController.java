package nl.dias.web.medewerker;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import nl.dias.service.AuthorisatieService;
import nl.lakedigital.as.taakbeheer.client.TaakClient;
import nl.lakedigital.as.taakbeheer.domein.json.JsonTaak;
import nl.lakedigital.as.taakbeheer.domein.json.JsonTaakAfhandelen;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sun.jersey.api.core.InjectParam;

@Path("/taak")
public class TaakController {
    private final static Logger LOGGER = LoggerFactory.getLogger(TaakController.class);

    @Context
    private HttpServletRequest httpServletRequest;

    @InjectParam
    private TaakClient taakClient;

    @InjectParam
    private AuthorisatieService authorisatieService;

    @GET
    @Path("/lees")
    @Produces(MediaType.APPLICATION_JSON)
    public JsonTaak lees(@QueryParam("id") Long id) {
        return taakClient.lees(id);
    }

    @GET
    @Path("/lijst")
    @Produces(MediaType.APPLICATION_JSON)
    public List<JsonTaak> alleTaken() {
        LOGGER.debug("Ophalen alle taken van TaakClient");

        return taakClient.alleTaken();
    }

    @GET
    @Path("/vrijgeven")
    @Produces(MediaType.APPLICATION_JSON)
    public Response vrijgeven(@QueryParam("id") Long id) {
        LOGGER.debug("Vrijgeven taak met id {} via TaakCLient", id);

        return taakClient.vrijgeven(id);
    }

    @GET
    @Path("/oppakken")
    @Produces(MediaType.APPLICATION_JSON)
    public Response oppakken(@QueryParam("id") Long id) {
        String sessie = null;
        if (httpServletRequest.getSession().getAttribute("sessie") != null && !"".equals(httpServletRequest.getSession().getAttribute("sessie"))) {
            sessie = httpServletRequest.getSession().getAttribute("sessie").toString();
        }

        Long ingelogdeGebruiker = authorisatieService.getIngelogdeGebruiker(httpServletRequest, sessie, httpServletRequest.getRemoteAddr()).getId();
        LOGGER.debug("Oppakken taak met id {} via TaakCLient, de ingelogde Gebruiker is {}", id, ingelogdeGebruiker);

        return taakClient.oppakken(id, ingelogdeGebruiker);
    }

    @POST
    @Path("/afhandelen")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public void afhandelen(JsonTaakAfhandelen taak) {
        taakClient.afhandelenTaak(taak);
    }
}
