package nl.dias.web.medewerker;

import nl.dias.domein.Opmerking;
import nl.dias.domein.json.JsonOpmerking;
import nl.dias.service.OpmerkingService;
import nl.dias.web.mapper.OpmerkingMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.ws.rs.QueryParam;
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

    @RequestMapping(method = RequestMethod.GET, value = "/verwijder")
    @ResponseBody
    public void verwijder(@QueryParam("id") Long id) {
        LOGGER.debug("Verwijder opmerking met id {}", id);
        opmerkingService.verwijder(id);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/opslaan")
    @ResponseBody
    public Long opslaan(@RequestBody JsonOpmerking jsonOpmerking) {
        Opmerking opmerking = null;
        try {
            opmerking = opmerkingMapper.mapVanJson(jsonOpmerking);
        } catch (IllegalArgumentException e) {
            LOGGER.debug("Fout opgetreden bij opslaan Opmerking", e);
            throw new AlgemeneFoutException(e.getMessage());
        }
        if (opmerking != null) {
            opmerkingService.opslaan(opmerking);
        }
        return opmerking.getId();
    }

    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public class AlgemeneFoutException extends RuntimeException {
        public AlgemeneFoutException(String message) {
            super(message);
        }
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
