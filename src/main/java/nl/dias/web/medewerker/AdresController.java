package nl.dias.web.medewerker;

import nl.dias.service.AdresService;
import nl.dias.web.SoortEntiteit;
import nl.dias.web.mapper.AdresMapper;
import nl.lakedigital.djfc.commons.json.JsonAdres;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.inject.Inject;
import javax.ws.rs.QueryParam;
import java.util.List;

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
}
