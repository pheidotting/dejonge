package nl.dias.web.medewerker;

import com.google.common.collect.Lists;
import nl.dias.domein.Adres;
import nl.dias.service.AdresService;
import nl.dias.web.SoortEntiteit;
import nl.dias.web.mapper.AdresMapper;
import nl.lakedigital.djfc.commons.json.JsonAdres;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.ws.rs.QueryParam;
import java.util.List;
import java.util.Set;

@RequestMapping("/adres")
@Controller
public class AdresController {
    private static final Logger LOGGER = LoggerFactory.getLogger(AdresController.class);

    @Inject
    private AdresService adresService;
    @Inject
    private AdresMapper adresMapper;

    @RequestMapping(method = RequestMethod.GET, value = "/alles")
    @ResponseBody
    public List<JsonAdres> alles(@QueryParam("soortentiteit") String soortentiteit, @QueryParam("parentid") Long parentid) {
        LOGGER.debug("alles soortEntiteit {} parentId {}", soortentiteit, parentid);

        return adresMapper.mapAllNaarJson(adresService.alles(SoortEntiteit.valueOf(soortentiteit), parentid));
    }

    @RequestMapping(method = RequestMethod.POST, value = "/opslaan")
    @ResponseBody
    public void opslaan(@RequestBody List<JsonAdres> jsonAdressen) {
        if (jsonAdressen != null && jsonAdressen.size() > 0) {
            JsonAdres eersteAdres = jsonAdressen.get(0);

            Set<Adres> adressen = adresMapper.mapAllVanJson(jsonAdressen);
            adresService.opslaan(Lists.newArrayList(adressen), SoortEntiteit.valueOf(eersteAdres.getSoortEntiteit()), eersteAdres.getEntiteitId());
        }
    }

    @RequestMapping(method = RequestMethod.POST, value = "/verwijderen/{soortentiteit}/{parentid}")
    @ResponseBody
    public void verwijderen(@PathVariable("soortentiteit") String soortentiteit, @PathVariable("parentid") Long parentid) {
        LOGGER.debug("Verwijderen adressen bij {} en {}", soortentiteit, parentid);

        adresService.verwijderen(SoortEntiteit.valueOf(soortentiteit), parentid);
    }
}
