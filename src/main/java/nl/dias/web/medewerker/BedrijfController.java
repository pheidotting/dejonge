package nl.dias.web.medewerker;

import nl.dias.domein.Bedrijf;
import nl.dias.inloggen.SessieHolder;
import nl.dias.mapper.Mapper;
import nl.dias.service.BedrijfService;
import nl.dias.service.GebruikerService;
import nl.dias.web.mapper.BedrijfMapper;
import nl.lakedigital.djfc.commons.json.JsonBedrijf;
import nl.lakedigital.djfc.commons.json.JsonFoutmelding;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RequestMapping("/bedrijf")
@Controller
public class BedrijfController extends AbstractController {
    private static final Logger LOGGER = LoggerFactory.getLogger(BedrijfController.class);

    @Inject
    private BedrijfService bedrijfService;
    @Inject
    private GebruikerService gebruikerService;
    @Inject
    private Mapper mapper;
    @Inject
    private BedrijfMapper bedrijfMapper;

    @RequestMapping(method = RequestMethod.POST, value = "/opslaan", produces = MediaType.APPLICATION_JSON)
    @ResponseBody
    public Response opslaanBedrijf(@RequestBody JsonBedrijf jsonBedrijf, HttpServletRequest httpServletRequest) {
        LOGGER.debug("Opslaan {}", ReflectionToStringBuilder.toString(jsonBedrijf, ToStringStyle.SHORT_PREFIX_STYLE));

        zetSessieWaarden(httpServletRequest);

        try {
            Bedrijf bedrijf = mapper.map(jsonBedrijf, Bedrijf.class);
            bedrijfService.opslaan(bedrijf);

            return Response.status(200).entity(bedrijf.getId()).build();
        } catch (Exception e) {
            LOGGER.error("Fout bij opslaan Bedrijf", e);
            return Response.status(500).entity(new JsonFoutmelding(e.getMessage())).build();
        }
    }

    @RequestMapping(method = RequestMethod.GET, value = "/lees", produces = MediaType.APPLICATION_JSON)
    @ResponseBody
    public JsonBedrijf lees(@QueryParam("id") String id) {
        JsonBedrijf bedrijf;
        if (id == null || "0".equals(id)) {
            bedrijf = new JsonBedrijf();
        } else {
            bedrijf = mapper.map(bedrijfService.lees(Long.valueOf(id)), JsonBedrijf.class);
        }
        return bedrijf;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/lijst", produces = MediaType.APPLICATION_JSON)
    @ResponseBody
    public List<JsonBedrijf> lijst(@QueryParam("zoekTerm") String zoekTerm) {
        List<JsonBedrijf> bedrijven = new ArrayList<>();

        if (zoekTerm == null) {
            for (Bedrijf bedrijf : bedrijfService.alles()) {
                bedrijven.add(mapper.map(bedrijf, JsonBedrijf.class));
            }
        } else {
            for (Bedrijf bedrijf : bedrijfService.zoekOpNaam(zoekTerm)) {
                bedrijven.add(mapper.map(bedrijf, JsonBedrijf.class));
            }

        }
        return bedrijven;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/verwijder/{id}", produces = MediaType.APPLICATION_JSON)
    @ResponseBody
    public void verwijder(@PathVariable("id") Long id, HttpServletRequest httpServletRequest) {
        LOGGER.debug("verwijderen Bedrijf met id " + id);

        zetSessieWaarden(httpServletRequest);

        try {
            bedrijfService.verwijder(id);
        } catch (IllegalArgumentException e) {
            LOGGER.error("Fout bij verwijderen Polis", e);
        }
    }

    @RequestMapping(method = RequestMethod.GET, value = "/verwijderen", produces = MediaType.APPLICATION_JSON)
    @ResponseBody
    public void verwijder(HttpServletRequest httpServletRequest) {
        zetSessieWaarden(httpServletRequest);
        SessieHolder.get().setTrackAndTraceId(UUID.randomUUID().toString());

        try {
            for (int i = 29; i < 106; i++) {
                bedrijfService.verwijder(Long.valueOf(i));
            }
        } catch (IllegalArgumentException e) {
            LOGGER.error("Fout bij verwijderen Polis", e);
        }
    }

}
