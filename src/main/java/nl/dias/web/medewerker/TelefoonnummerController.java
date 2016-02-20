package nl.dias.web.medewerker;

import nl.dias.service.TelefoonnummerService;
import nl.dias.web.SoortEntiteit;
import nl.dias.web.mapper.TelefoonnummerMapper;
import nl.lakedigital.djfc.commons.json.JsonTelefoonnummer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.inject.Inject;
import javax.ws.rs.QueryParam;
import java.util.List;

@RequestMapping("/telefoonnummer")
@Controller
public class TelefoonnummerController {
    private static final Logger LOGGER = LoggerFactory.getLogger(TelefoonnummerController.class);

    @Inject
    private TelefoonnummerService telefoonnummerService;
    @Inject
    private TelefoonnummerMapper telefoonnummerMapper;

    @RequestMapping(method = RequestMethod.GET, value = "/alles")
    @ResponseBody
    public List<JsonTelefoonnummer> alles(@QueryParam("soortentiteit") String soortentiteit, @QueryParam("parentid") Long parentid) {
        LOGGER.debug("alles soortEntiteit {} parentId {}", soortentiteit, parentid);

        return telefoonnummerMapper.mapAllNaarJson(telefoonnummerService.alles(SoortEntiteit.valueOf(soortentiteit), parentid));
    }
}
