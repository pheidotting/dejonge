package nl.dias.web.medewerker;

import nl.dias.domein.Bedrijf;
import nl.dias.domein.Relatie;
import nl.dias.domein.Schade;
import nl.dias.domein.json.JsonFoutmelding;
import nl.dias.domein.json.JsonSchade;
import nl.dias.service.BedrijfService;
import nl.dias.service.GebruikerService;
import nl.dias.service.SchadeService;
import nl.dias.web.mapper.SchadeMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.inject.Inject;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RequestMapping("/schade")
@Controller
public class SchadeController {
    private final static Logger LOGGER = LoggerFactory.getLogger(SchadeController.class);

    @Inject
    private SchadeService schadeService;
    @Inject
    private SchadeMapper schadeMapper;
    @Inject
    private GebruikerService gebruikerService;
    @Inject
    private BedrijfService bedrijfService;

    @RequestMapping(method = RequestMethod.POST, value = "/opslaan")
    @ResponseBody
    public Response opslaan(@RequestBody JsonSchade jsonSchade) {
        LOGGER.debug("{}", jsonSchade);

        Schade schade = schadeMapper.mapVanJson(jsonSchade);
        schadeService.opslaan(schade, jsonSchade.getSoortSchade(), jsonSchade.getPolis(), jsonSchade.getStatusSchade());

        return Response.status(202).entity(new JsonFoutmelding(schade.getId().toString())).build();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/lijst")
    @ResponseBody
    public List<JsonSchade> lijst(@QueryParam("relatieId") String relatieId) {
        Relatie relatie = (Relatie) gebruikerService.lees(Long.valueOf(relatieId));

        Set<Schade> schades = new HashSet<>();
        for (Schade schade : schadeService.alleSchadesBijRelatie(relatie)) {
            schades.add(schade);
        }

        return schadeMapper.mapAllNaarJson(schades);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/lijstBijBedrijf")
    @ResponseBody
    public List<JsonSchade> lijstBijBedrijf(@QueryParam("bedrijfId") Long bedrijfId) {
        LOGGER.debug("Opzoeken Schades bij Bedrijf met Id {}", bedrijfId);
        Bedrijf bedrijf = bedrijfService.lees(bedrijfId);

        Set<Schade> schades = new HashSet<>();
        for (Schade schade : schadeService.alleSchadesBijBedrijf(bedrijf)) {
            schades.add(schade);
        }

        return schadeMapper.mapAllNaarJson(schades);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/lees")
    @ResponseBody
    public JsonSchade lees(@QueryParam("id") String id) {
        return schadeMapper.mapNaarJson(schadeService.lees(Long.valueOf(id)));
    }

    @RequestMapping(method = RequestMethod.GET, value = "/verwijder")
    @ResponseBody
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
