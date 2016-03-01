package nl.dias.web.medewerker;

import com.google.common.collect.Lists;
import nl.dias.domein.Telefoonnummer;
import nl.dias.service.TelefoonnummerService;
import nl.dias.web.SoortEntiteit;
import nl.dias.web.mapper.TelefoonnummerMapper;
import nl.lakedigital.djfc.commons.json.JsonTelefoonnummer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.ws.rs.QueryParam;
import java.util.List;
import java.util.Set;

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

    @RequestMapping(method = RequestMethod.POST, value = "/opslaan")
    @ResponseBody
    public void opslaan(@RequestBody List<JsonTelefoonnummer> jsonTelefoonnummers) {
        if (jsonTelefoonnummers != null && jsonTelefoonnummers.size() > 0) {
            JsonTelefoonnummer eersteNummer = jsonTelefoonnummers.get(0);

            Set<Telefoonnummer> telefoonnummers = telefoonnummerMapper.mapAllVanJson(jsonTelefoonnummers);
            telefoonnummerService.opslaan(Lists.newArrayList(telefoonnummers), eersteNummer.getEntiteitId(), SoortEntiteit.valueOf(eersteNummer.getSoortEntiteit()));
        }
    }

    @RequestMapping(method = RequestMethod.POST, value = "/verwijderen/{soortentiteit}/{parentid}")
    @ResponseBody
    public void verwijderen(@PathVariable("soortentiteit") String soortentiteit, @PathVariable("parentid") Long parentid) {
        LOGGER.debug("Verwijderen telefoonnummers bij {} en {}", soortentiteit, parentid);

        telefoonnummerService.verwijderen(SoortEntiteit.valueOf(soortentiteit), parentid);
    }
}
