package nl.dias.web.medewerker;

import nl.dias.domein.Opmerking;
import nl.dias.service.OpmerkingService;
import nl.dias.web.SoortEntiteit;
import nl.dias.web.mapper.OpmerkingMapper;
import nl.lakedigital.djfc.commons.json.JsonOpmerking;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.ws.rs.QueryParam;
import java.util.ArrayList;
import java.util.List;

@RequestMapping("/opmerking")
@Controller
public class OpmerkingController {
    private static final Logger LOGGER = LoggerFactory.getLogger(OpmerkingController.class);

    @Inject
    private OpmerkingService opmerkingService;
    @Inject
    private OpmerkingMapper opmerkingMapper;

    @RequestMapping(method = RequestMethod.GET, value = "/lijstOpmerkingen")
    @ResponseBody
    public List<JsonOpmerking> lijstOpmerkingen(@QueryParam("soortentiteit") String soortentiteit, @QueryParam("parentid") Long parentid) {
        SoortEntiteit soortEntiteit = SoortEntiteit.valueOf(soortentiteit);

        switch (soortEntiteit) {
            case BEDRIJF:
                return opmerkingMapper.mapAllNaarJson(opmerkingService.alleOpmerkingenBijBedrijf(parentid));
            case JAARCIJFERS:
                return opmerkingMapper.mapAllNaarJson(opmerkingService.alleOpmerkingenBijJaarCijfers(parentid));
            case POLIS:
                return opmerkingMapper.mapAllNaarJson(opmerkingService.alleOpmerkingenBijPolis(parentid));
            case RELATIE:
                return opmerkingMapper.mapAllNaarJson(opmerkingService.alleOpmerkingenBijRelatie(parentid));
            case SCHADE:
                return opmerkingMapper.mapAllNaarJson(opmerkingService.alleOpmerkingenBijSchade(parentid));
            default:
                return new ArrayList<>();
        }
    }

    @RequestMapping(method = RequestMethod.GET, value = "/lijst")
    @ResponseBody
    public List<JsonOpmerking> lijstOpmerkingen(Long relatie) {
        return opmerkingMapper.mapAllNaarJson(opmerkingService.alleOpmerkingenBijRelatie(relatie));
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
