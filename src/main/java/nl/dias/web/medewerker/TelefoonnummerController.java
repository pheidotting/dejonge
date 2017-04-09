package nl.dias.web.medewerker;

import nl.lakedigital.djfc.client.identificatie.IdentificatieClient;
import nl.lakedigital.djfc.client.oga.TelefoonnummerClient;
import nl.lakedigital.djfc.commons.json.JsonTelefoonnummer;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@RequestMapping("/telefoonnummer")
@Controller
public class TelefoonnummerController extends AbstractController {
    @Inject
    private TelefoonnummerClient telefoonnummerClient;
    @Inject
    private IdentificatieClient identificatieClient;

    @RequestMapping(method = RequestMethod.POST, value = "/opslaan", produces = MediaType.APPLICATION_JSON)
    @ResponseBody
    public void opslaan(@RequestBody List<JsonTelefoonnummer> jsonEntiteiten, HttpServletRequest httpServletRequest) {
        List<JsonTelefoonnummer> lijst = jsonEntiteiten.stream().map(new Function<JsonTelefoonnummer, JsonTelefoonnummer>() {
            @Override
            public JsonTelefoonnummer apply(JsonTelefoonnummer adres) {
                Long entiteitId = identificatieClient.zoekIdentificatieCode(adres.getParentIdentificatie()).getEntiteitId();

                adres.setEntiteitId(entiteitId);

                return adres;
            }
        }).collect(Collectors.toList());
        telefoonnummerClient.opslaan(lijst, getIngelogdeGebruiker(httpServletRequest), getTrackAndTraceId(httpServletRequest));
    }

    @RequestMapping(method = RequestMethod.GET, value = "/alles/{soortentiteit}/{parentid}", produces = MediaType.APPLICATION_JSON)
    @ResponseBody
    public List<JsonTelefoonnummer> alles(@PathVariable("soortentiteit") String soortentiteit, @PathVariable("parentid") Long parentid) {
        List<JsonTelefoonnummer> jsonEntiteiten = telefoonnummerClient.lijst(soortentiteit, parentid);

        return jsonEntiteiten;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/verwijderen/{soortentiteit}/{parentid}", produces = MediaType.APPLICATION_JSON)
    @ResponseBody
    public void verwijderen(@PathVariable("soortentiteit") String soortentiteit, @PathVariable("parentid") Long parentid, HttpServletRequest httpServletRequest) {
        telefoonnummerClient.verwijder(soortentiteit, parentid, getIngelogdeGebruiker(httpServletRequest), getTrackAndTraceId(httpServletRequest));
    }

    @RequestMapping(method = RequestMethod.GET, value = "/zoeken/{zoekTerm}", produces = MediaType.APPLICATION_JSON)
    @ResponseBody
    public List<JsonTelefoonnummer> zoeken(@PathVariable("zoekTerm") String zoekTerm) {
        List<JsonTelefoonnummer> result = telefoonnummerClient.zoeken(zoekTerm);

        return result;
    }
}
