package nl.dias.web.medewerker;

import nl.dias.service.AuthorisatieService;
import nl.lakedigital.as.taakbeheer.client.TaakClient;
import nl.lakedigital.as.taakbeheer.domein.json.JsonTaak;
import nl.lakedigital.as.taakbeheer.domein.json.JsonTaakAfhandelen;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import java.util.List;

@RequestMapping("/taak")
@Controller
public class TaakController {
    private static final Logger LOGGER = LoggerFactory.getLogger(TaakController.class);

    @Autowired
    private HttpServletRequest httpServletRequest;

    @Inject
    private TaakClient taakClient;
    @Inject
    private AuthorisatieService authorisatieService;

    @RequestMapping(method = RequestMethod.GET, value = "/alleOpenTakenVoorRelatie")
    @ResponseBody
    public List<JsonTaak> alleOpenTakenBijRelatie(@QueryParam("relatieId") Long relatieId) {
        return taakClient.alleOpenTakenBijRelatie(relatieId);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/aantalOpenTaken")
    @ResponseBody
    public Long aantalOpenTaken() {
        return taakClient.aantalOpenTaken();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/lees")
    @ResponseBody
    public JsonTaak lees(@QueryParam("id") Long id) {
        return taakClient.lees(id);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/lijst")
    @ResponseBody
    public List<JsonTaak> alleTaken() {
        LOGGER.debug("Ophalen alle taken van TaakClient");

        return taakClient.alleTaken();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/vrijgeven")
    @ResponseBody
    public Response vrijgeven(@QueryParam("id") Long id) {
        LOGGER.debug("Vrijgeven taak met id {} via TaakCLient", id);

        return taakClient.vrijgeven(id);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/oppakken")
    @ResponseBody
    public Response oppakken(@QueryParam("id") Long id) {
        String sessie = null;
        if (httpServletRequest.getSession().getAttribute("sessie") != null && !"".equals(httpServletRequest.getSession().getAttribute("sessie"))) {
            sessie = httpServletRequest.getSession().getAttribute("sessie").toString();
        }

        Long ingelogdeGebruiker = authorisatieService.getIngelogdeGebruiker(httpServletRequest, sessie, httpServletRequest.getRemoteAddr()).getId();
        LOGGER.debug("Oppakken taak met id {} via TaakCLient, de ingelogde Gebruiker is {}", id, ingelogdeGebruiker);

        return taakClient.oppakken(id, ingelogdeGebruiker);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/afhandelen")
    @ResponseBody
    public void afhandelen(@RequestBody JsonTaakAfhandelen taak) {
        taakClient.afhandelenTaak(taak);
    }
}
