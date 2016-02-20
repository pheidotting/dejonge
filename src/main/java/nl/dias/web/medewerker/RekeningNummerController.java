package nl.dias.web.medewerker;

import nl.dias.service.RekeningNummerService;
import nl.dias.web.SoortEntiteit;
import nl.dias.web.mapper.RekeningnummerMapper;
import nl.lakedigital.djfc.commons.json.JsonRekeningNummer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.inject.Inject;
import javax.ws.rs.QueryParam;
import java.util.List;

@RequestMapping("/rekeningnummer")
@Controller
public class RekeningNummerController {
    private static final Logger LOGGER = LoggerFactory.getLogger(RekeningNummerController.class);

    @Inject
    private RekeningNummerService rekeningNummerService;
    @Inject
    private RekeningnummerMapper rekeningnummerMapper;

    @RequestMapping(method = RequestMethod.GET, value = "/alles")
    @ResponseBody
    public List<JsonRekeningNummer> alles(@QueryParam("soortentiteit") String soortentiteit, @QueryParam("parentid") Long parentid) {
        LOGGER.debug("alles soortEntiteit {} parentId {}", soortentiteit, parentid);

        return rekeningnummerMapper.mapAllNaarJson(rekeningNummerService.alles(SoortEntiteit.valueOf(soortentiteit), parentid));
    }
}
