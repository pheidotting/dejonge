package nl.dias.web.medewerker;

import nl.lakedigital.djfc.client.oga.RekeningClient;
import nl.lakedigital.djfc.commons.json.JsonRekeningNummer;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RequestMapping("/rekeningnummer")
@Controller
public class RekeningNummerController extends AbstractController {
    private RekeningClient rekeningClient = new RekeningClient(8081);

    @RequestMapping(method = RequestMethod.POST, value = "/opslaan")
    @ResponseBody
    public void opslaan(@RequestBody List<JsonRekeningNummer> jsonEntiteiten, HttpServletRequest httpServletRequest) {
        rekeningClient.opslaan(jsonEntiteiten, getIngelogdeGebruiker(httpServletRequest), getTrackAndTraceId(httpServletRequest));
    }

    @RequestMapping(method = RequestMethod.GET, value = "/alles/{soortentiteit}/{parentid}")
    @ResponseBody
    public List<JsonRekeningNummer> alles(@PathVariable("soortentiteit") String soortentiteit, @PathVariable("parentid") Long parentid) {
        List<JsonRekeningNummer> jsonEntiteiten = rekeningClient.lijst(soortentiteit, parentid);

        return jsonEntiteiten;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/verwijderen/{soortentiteit}/{parentid}")
    @ResponseBody
    public void verwijderen(@PathVariable("soortentiteit") String soortentiteit, @PathVariable("parentid") Long parentid, HttpServletRequest httpServletRequest) {
        rekeningClient.verwijder(soortentiteit, parentid, getIngelogdeGebruiker(httpServletRequest), getTrackAndTraceId(httpServletRequest));
    }

    @RequestMapping(method = RequestMethod.GET, value = "/zoeken/{zoekTerm}")
    @ResponseBody
    public List<JsonRekeningNummer> zoeken(@PathVariable("zoekTerm") String zoekTerm) {
        List<JsonRekeningNummer> result = rekeningClient.zoeken(zoekTerm);

        return result;
    }
}