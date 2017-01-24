package nl.dias.web.medewerker;

import nl.dias.domein.features.MyFeatures;
import nl.lakedigital.djfc.client.oga.AdresClient;
import nl.lakedigital.djfc.commons.json.JsonAdres;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RequestMapping("/adres")
@Controller
public class AdresController extends AbstractController {
    private final static Logger LOGGER = LoggerFactory.getLogger(AdresController.class);

    @Inject
    private AdresClient adresClient;

    @RequestMapping(method = RequestMethod.POST, value = "/opslaan")
    @ResponseBody
    public void opslaan(@RequestBody List<JsonAdres> jsonEntiteiten, HttpServletRequest httpServletRequest) {
        adresClient.opslaan(jsonEntiteiten, getIngelogdeGebruiker(httpServletRequest), getTrackAndTraceId(httpServletRequest));
    }

    @RequestMapping(method = RequestMethod.GET, value = "/alles/{soortentiteit}/{parentid}")
    @ResponseBody
    public List<JsonAdres> alles(@PathVariable("soortentiteit") String soortentiteit, @PathVariable("parentid") Long parentid) {
        List<JsonAdres> jsonEntiteiten = adresClient.lijst(soortentiteit, parentid);

        return jsonEntiteiten;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/verwijderen/{soortentiteit}/{parentid}")
    @ResponseBody
    public void verwijderen(@PathVariable("soortentiteit") String soortentiteit, @PathVariable("parentid") Long parentid, HttpServletRequest httpServletRequest) {
        adresClient.verwijder(soortentiteit, parentid, getIngelogdeGebruiker(httpServletRequest), getTrackAndTraceId(httpServletRequest));
    }

    @RequestMapping(method = RequestMethod.GET, value = "/zoeken/{zoekTerm}")
    @ResponseBody
    public List<JsonAdres> zoeken(@PathVariable("zoekTerm") String zoekTerm) {
        List<JsonAdres> result = adresClient.zoeken(zoekTerm);

        return result;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/ophalenAdresOpPostcode/{postcode}/{huisnummer}")
    @ResponseBody
    public JsonAdres ophalenAdresOpPostcode(@PathVariable("postcode") String postcode, @PathVariable("huisnummer") String huisnummer) {
        return adresClient.ophalenAdresOpPostcode(postcode, huisnummer, MyFeatures.ADRES_NIET_VIA_API.isActive());
    }

    @RequestMapping(method = RequestMethod.GET, value = "/alleAdressenBijLijstMetEntiteiten")
    @ResponseBody
    public List<JsonAdres> alleAdressenBijLijstMetEntiteiten(@RequestParam("soortEntiteit") String soortEntiteit, @RequestParam("lijst") List<Long> ids) {
        return adresClient.alleAdressenBijLijstMetEntiteiten(ids, "RELATIE");
    }
}
