package nl.dias.web.medewerker;

import com.google.common.collect.Lists;
import nl.dias.domein.polis.Polis;
import nl.dias.domein.polis.SoortVerzekering;
import nl.dias.mapper.Mapper;
import nl.dias.service.BedrijfService;
import nl.dias.service.BijlageService;
import nl.dias.service.GebruikerService;
import nl.dias.service.PolisService;
import nl.dias.web.mapper.PolisMapper;
import nl.lakedigital.djfc.commons.json.JsonFoutmelding;
import nl.lakedigital.djfc.commons.json.JsonPolis;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
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
import java.util.List;

@RequestMapping("/polis")
@Controller
public class PolisController {
    private static final Logger LOGGER = LoggerFactory.getLogger(PolisController.class);

    @Inject
    private PolisService polisService;
    @Inject
    private GebruikerService gebruikerService;
    @Inject
    private BijlageService bijlageService;
    @Inject
    private PolisMapper polisMapper;
    @Inject
    private List<Polis> polissen;
    @Inject
    private BedrijfService bedrijfService;
    @Inject
    private Mapper mapper;

    @RequestMapping(method = RequestMethod.GET, value = "/alleParticulierePolisSoorten")
    @ResponseBody
    public List<String> alleParticulierePolisSoorten() {
        return polisService.allePolisSoorten(SoortVerzekering.PARTICULIER);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/alleZakelijkePolisSoorten")
    @ResponseBody
    public List<String> alleZakelijkePolisSoorten() {
        return polisService.allePolisSoorten(SoortVerzekering.ZAKELIJK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/lees")
    @ResponseBody
    public JsonPolis lees(@QueryParam("id") String id) {
        LOGGER.debug("ophalen Polis met id " + id);
        if (id != null && !"".equals(id) && !"0".equals(id)) {
            LOGGER.debug("ophalen Polis");
            return polisMapper.mapNaarJson(polisService.lees(Long.valueOf(id)));
        } else {
            LOGGER.debug("Nieuwe Polis tonen");
            return new JsonPolis();
        }
    }

    @RequestMapping(method = RequestMethod.GET, value = "/beeindigen")
    @ResponseBody
    public void beeindigen(@QueryParam("id") Long id) {
        LOGGER.debug("beeindigen Polis met id " + id);
        polisService.beeindigen(id);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/lijst")
    @ResponseBody
    public List<JsonPolis> lijst(@QueryParam("relatieId") String relatieId) {
        LOGGER.debug("Ophalen alle polissen voor Relatie " + relatieId);
        //        Relatie relatie = (Relatie) gebruikerService.lees(Long.valueOf(relatieId));
        Long relatie = Long.valueOf(relatieId);

        List<JsonPolis> result = Lists.newArrayList();
        for (Polis polis : polisService.allePolissenBijRelatie(relatie)) {
            result.add(polisMapper.mapNaarJson(polis));
        }

        return result;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/lijstBijBedrijf")
    @ResponseBody
    public List<JsonPolis> lijstBijBedrijf(@QueryParam("bedrijfId") Long bedrijfId) {
        LOGGER.debug("Ophalen alle polissen voor Bedrijf " + bedrijfId);
        //        Bedrijf bedrijf = bedrijfService.lees(bedrijfId);

        //        Set<Polis> polissen = new HashSet<>();
        //        for (Polis polis : polisService.allePolissenBijBedrijf(bedrijfId)) {
        //            polissen.add(polis);
        //        }

        return polisMapper.mapAllNaarJson(polisService.allePolissenBijBedrijf(bedrijfId));
    }

    @RequestMapping(method = RequestMethod.POST, value = "/opslaan")
    @ResponseBody
    public Response opslaan(@RequestBody JsonPolis jsonPolis) {
        LOGGER.debug("Opslaan " + ReflectionToStringBuilder.toString(jsonPolis));

        Polis polis = polisMapper.mapVanJson(jsonPolis);
        try {
            polisService.opslaan(polis);
        } catch (IllegalArgumentException e) {
            LOGGER.debug("Fout opgetreden bij opslaan Polis", e);
            return Response.status(500).entity(new JsonFoutmelding(e.getMessage())).build();
        }
        return Response.status(200).entity(new JsonFoutmelding(polis.getId().toString())).build();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/verwijder")
    @ResponseBody
    public Response verwijder(@QueryParam("id") Long id) {
        LOGGER.debug("verwijderen Polis met id " + id);
        try {
            polisService.verwijder(id);
        } catch (IllegalArgumentException e) {
            LOGGER.error("Fout bij verwijderen Polis", e);
            return Response.status(500).entity(new JsonFoutmelding(e.getMessage())).build();
        }
        return Response.status(202).entity(new JsonFoutmelding()).build();
    }

    public void setPolisService(PolisService polisService) {
        this.polisService = polisService;
    }

    public void setGebruikerService(GebruikerService gebruikerService) {
        this.gebruikerService = gebruikerService;
    }
}
