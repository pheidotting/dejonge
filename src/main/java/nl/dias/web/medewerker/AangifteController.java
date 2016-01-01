package nl.dias.web.medewerker;

import nl.dias.domein.Aangifte;
import nl.dias.domein.Gebruiker;
import nl.dias.domein.Relatie;
import nl.dias.service.AangifteService;
import nl.dias.service.AuthorisatieService;
import nl.dias.service.GebruikerService;
import nl.dias.web.mapper.AangifteMapper;
import nl.lakedigital.djfc.commons.json.JsonAangifte;
import org.joda.time.LocalDate;
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

@RequestMapping("/aangifte")
@Controller
public class AangifteController {
    private static final Logger LOGGER = LoggerFactory.getLogger(AangifteController.class);

    @Inject
    private AangifteService aangifteService;
    @Inject
    private GebruikerService gebruikerService;
    @Inject
    private AangifteMapper aangifteMapper;
    @Autowired
    private HttpServletRequest httpServletRequest;
    @Inject
    private AuthorisatieService authorisatieService;

    @RequestMapping(method = RequestMethod.GET, value = "/openAangiftes")
    @ResponseBody
    public List<JsonAangifte> openAangiftes(@QueryParam("relatie") Long relatie) {
        return aangifteMapper.mapAllNaarJson(aangifteService.getOpenstaandeAangiftes((Relatie) gebruikerService.lees(relatie)));
    }

    @RequestMapping(method = RequestMethod.POST, value = "/afronden")
    @ResponseBody
    public Response afronden(@RequestBody Long id) {
        LOGGER.info("Afronden Aangifte met id " + id);
        try {
            aangifteService.afronden(id, LocalDate.now(), getGebruiker());
        } catch (Exception e) {
            LOGGER.trace("{}", e);
            return Response.status(500).build();
        }
        return Response.ok(id).build();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/opslaan")
    @ResponseBody
    public Response opslaan(@RequestBody JsonAangifte jsonAangifte) {
        Aangifte aangifte = aangifteMapper.mapVanJson(jsonAangifte);
        aangifteService.opslaan(aangifte);

        return Response.ok(aangifte.getId()).build();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/geslotenAangiftes")
    @ResponseBody
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
