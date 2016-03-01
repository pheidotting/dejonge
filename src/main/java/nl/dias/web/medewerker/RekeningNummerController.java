package nl.dias.web.medewerker;

import com.google.common.collect.Lists;
import nl.dias.domein.RekeningNummer;
import nl.dias.service.RekeningNummerService;
import nl.dias.web.SoortEntiteit;
import nl.dias.web.mapper.RekeningnummerMapper;
import nl.lakedigital.djfc.commons.json.JsonRekeningNummer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.ws.rs.QueryParam;
import java.util.List;
import java.util.Set;

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

    @RequestMapping(method = RequestMethod.POST, value = "/opslaan")
    @ResponseBody
    public void opslaan(@RequestBody List<JsonRekeningNummer> jsonRekeningNummers) {
        if (jsonRekeningNummers != null && jsonRekeningNummers.size() > 0) {
            JsonRekeningNummer eersteNummer = jsonRekeningNummers.get(0);

            Set<RekeningNummer> adressen = rekeningnummerMapper.mapAllVanJson(jsonRekeningNummers);
            rekeningNummerService.opslaan(Lists.newArrayList(adressen), SoortEntiteit.valueOf(eersteNummer.getSoortEntiteit()), eersteNummer.getEntiteitId());
        }
    }

    @RequestMapping(method = RequestMethod.POST, value = "/verwijderen/{soortentiteit}/{parentid}")
    @ResponseBody
    public void verwijderen(@PathVariable("soortentiteit") String soortentiteit, @PathVariable("parentid") Long parentid) {
        LOGGER.debug("Verwijderen rekeningnummers bij {} en {}", soortentiteit, parentid);

        rekeningNummerService.verwijderen(SoortEntiteit.valueOf(soortentiteit), parentid);
    }
}
