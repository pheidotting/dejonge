package nl.dias.web.medewerker;

import nl.dias.domein.Hypotheek;
import nl.dias.domein.HypotheekPakket;
import nl.dias.domein.SoortHypotheek;
import nl.dias.service.HypotheekService;
import nl.dias.web.mapper.HypotheekMapper;
import nl.dias.web.mapper.HypotheekPakketMapper;
import nl.dias.web.mapper.SoortHypotheekMapper;
import nl.lakedigital.djfc.commons.json.JsonFoutmelding;
import nl.lakedigital.djfc.commons.json.JsonHypotheek;
import nl.lakedigital.djfc.commons.json.JsonHypotheekPakket;
import nl.lakedigital.djfc.commons.json.JsonSoortHypotheek;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RequestMapping("/hypotheek")
@Controller
public class HypotheekController extends AbstractController {
    private static final Logger LOGGER = LoggerFactory.getLogger(HypotheekController.class);

    @Inject
    private HypotheekService hypotheekService;
    @Inject
    private SoortHypotheekMapper soortHypotheekMapper;
    @Inject
    private HypotheekMapper hypotheekMapper;
    @Inject
    private HypotheekPakketMapper hypotheekPakketMapper;

    @RequestMapping(method = RequestMethod.GET, value = "/lees")
    @ResponseBody
    public JsonHypotheek lees(@QueryParam("id") String id) {
        JsonHypotheek jsonHypotheek = null;

        if (id == null || id.equals("") || id.equals("0")) {
            jsonHypotheek = new JsonHypotheek();
        } else {
            jsonHypotheek = hypotheekMapper.mapNaarJson(hypotheekService.leesHypotheek(Long.valueOf(id)));
        }

        return jsonHypotheek;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/alleSoortenHypotheek")
    @ResponseBody
    public List<JsonSoortHypotheek> alleSoortenHypotheek() {
        Set<SoortHypotheek> soorten = new HashSet<>();

        for (SoortHypotheek soort : hypotheekService.alleSoortenHypotheekInGebruik()) {
            soorten.add(soort);
        }

        List<JsonSoortHypotheek> soortenList =

                soortHypotheekMapper.mapAllNaarJson(soorten);

        Collections.sort(soortenList);

        return soortenList;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/lijstHypotheekPakketten")
    @ResponseBody
    public List<JsonHypotheekPakket> alleHypotheekPakketten(@QueryParam("relatieId") Long relatieId) {
        Set<HypotheekPakket> hypotheekPakketten = new HashSet<>();

        for (HypotheekPakket hypotheekPakket : hypotheekService.allePakketenVanRelatie(relatieId)) {
            hypotheekPakketten.add(hypotheekPakket);
        }

        return hypotheekPakketMapper.mapAllNaarJson(hypotheekPakketten);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/lijstHypotheken")
    @ResponseBody
    public List<JsonHypotheek> alleHypotheken(@QueryParam("relatieId") Long relatieId) {
        Set<Hypotheek> hypotheken = new HashSet<>();

        for (Hypotheek soort : hypotheekService.allesVanRelatie(relatieId)) {
            hypotheken.add(soort);
        }

        return hypotheekMapper.mapAllNaarJson(hypotheken);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/lijstHypothekenInclDePakketten")
    @ResponseBody
    public List<JsonHypotheek> alleHypothekenInclDePakketten(@QueryParam("relatieId") Long relatieId) {
        hypotheekService.leesHypotheek(14L);

        Set<Hypotheek> hypotheken = new HashSet<>();

        for (Hypotheek soort : hypotheekService.allesVanRelatieInclDePakketten(relatieId)) {
            hypotheken.add(soort);
        }

        return hypotheekMapper.mapAllNaarJson(hypotheken);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/opslaan")
    @ResponseBody
    public Response opslaan(@RequestBody JsonHypotheek jsonHypotheek, HttpServletRequest httpServletRequest) {
        LOGGER.debug("Opslaan Hypotheek " + jsonHypotheek);

        zetSessieWaarden(httpServletRequest);

        // Hypotheek hypotheek = new Hypotheek();
        // if (jsonHypotheek.getId() != null && jsonHypotheek.getId() != 0) {
        // hypotheek = hypotheekService.leesHypotheek(jsonHypotheek.getId());
        // }
        //
        // hypotheek = hypotheekMapper.mapVanJson(jsonHypotheek, hypotheek);
        // LOGGER.info("Uit de mapper");
        // LOGGER.info(hypotheek);

        Hypotheek hypotheek = hypotheekService.opslaan(jsonHypotheek, jsonHypotheek.getHypotheekVorm(), jsonHypotheek.getRelatie(), jsonHypotheek.getGekoppeldeHypotheek());

        LOGGER.debug("Opgeslagen");

        return Response.status(200).entity(new JsonFoutmelding(hypotheek.getId().toString())).build();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/leesHypotheekVorm")
    @ResponseBody
    public String leesHypotheekVorm(@QueryParam("id") Long id) {
        SoortHypotheek soortHypotheek = hypotheekService.leesSoortHypotheek(id);

        return soortHypotheek.getOmschrijving();
    }

    public void setHypotheekService(HypotheekService hypotheekService) {
        this.hypotheekService = hypotheekService;
    }

    public void setSoortHypotheekMapper(SoortHypotheekMapper soortHypotheekMapper) {
        this.soortHypotheekMapper = soortHypotheekMapper;
    }

    public void setHypotheekMapper(HypotheekMapper hypotheekMapper) {
        this.hypotheekMapper = hypotheekMapper;
    }

    public void setHypotheekPakketMapper(HypotheekPakketMapper hypotheekPakketMapper) {
        this.hypotheekPakketMapper = hypotheekPakketMapper;
    }
}
