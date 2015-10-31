package nl.dias.web.medewerker;

import nl.dias.domein.Opmerking;
import nl.dias.domein.json.JsonFoutmelding;
import nl.dias.domein.json.JsonOpmerking;
import nl.dias.service.OpmerkingService;
import nl.dias.web.mapper.OpmerkingMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.inject.Inject;
import javax.ws.rs.core.Response;
import java.util.List;

@RequestMapping("/opmerking")
@Controller
public class OpmerkingController {
    private final static Logger LOGGER = LoggerFactory.getLogger(OpmerkingController.class);

    @Inject
    private OpmerkingService opmerkingService;
    @Inject
    private OpmerkingMapper opmerkingMapper;

    @RequestMapping(method = RequestMethod.GET, value = "/lijst")
    @ResponseBody
    public List<JsonOpmerking> lijstOpmerkingen(Long relatie) {
        return opmerkingMapper.mapAllNaarJson(opmerkingService.alleOpmerkingenVoorRelatie(relatie));
    }

    @RequestMapping(method = RequestMethod.POST, value = "/opslaan")
    @ResponseBody
    public Response opslaan(@RequestBody JsonOpmerking jsonOpmerking) {
        Opmerking opmerking = null;
        try {
            opmerking = opmerkingMapper.mapVanJson(jsonOpmerking);
        } catch (IllegalArgumentException e) {
            LOGGER.debug("Fout opgetreden bij opslaan Opmerking", e);
            return Response.status(500).entity(new JsonFoutmelding(e.getMessage())).build();
        }
        if (opmerking != null) {
            opmerkingService.opslaan(opmerking);
        }
        return Response.status(200).entity(opmerking.getId()).build();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/nieuw")
    @ResponseBody
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
