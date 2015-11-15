package nl.dias.web.medewerker;

import nl.dias.domein.Bedrijf;
import nl.dias.domein.Relatie;
import nl.dias.domein.json.JsonBedrijf;
import nl.dias.domein.json.JsonFoutmelding;
import nl.dias.mapper.Mapper;
import nl.dias.service.BedrijfService;
import nl.dias.service.GebruikerService;
import nl.dias.web.mapper.BedrijfMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.inject.Inject;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RequestMapping("/bedrijf")
@Controller
public class BedrijfController {
    private final static Logger LOGGER = LoggerFactory.getLogger(BedrijfController.class);

    @Inject
    private BedrijfService bedrijfService;
    @Inject
    private GebruikerService gebruikerService;
    @Inject
    private Mapper mapper;

    @RequestMapping(method = RequestMethod.GET, value = "/lees")
    @ResponseBody
    public JsonBedrijf lees(@QueryParam("id") String id) {
        Bedrijf bedrijf = null;
        if (id == null || "0".equals(id)) {
            bedrijf = new Bedrijf();
        } else {
            bedrijf = bedrijfService.lees(Long.valueOf(id));
        }
        return (JsonBedrijf) mapper.map(bedrijf);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/lijst")
    @ResponseBody
    public List<JsonBedrijf> lijst() {
        List<JsonBedrijf> bedrijven = new ArrayList<>();
        for (Bedrijf bedrijf : bedrijfService.alles()) {
            bedrijven.add((JsonBedrijf) mapper.map(bedrijf));
        }

        return bedrijven;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/verwijder")
    @ResponseBody
    public Response verwijder(@QueryParam("id") Long id) {
        LOGGER.debug("verwijderen Polis met id " + id);
        try {
            bedrijfService.verwijder(id);
        } catch (IllegalArgumentException e) {
            LOGGER.error("Fout bij verwijderen Polis", e);
            return Response.status(500).entity(new JsonFoutmelding(e.getMessage())).build();
        }
        return Response.status(202).entity(new JsonFoutmelding()).build();
    }

}
