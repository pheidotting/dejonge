package nl.dias.web.medewerker;

import com.sun.jersey.api.core.InjectParam;
import nl.dias.domein.Hypotheek;
import nl.dias.domein.HypotheekPakket;
import nl.dias.domein.SoortHypotheek;
import nl.dias.domein.json.JsonFoutmelding;
import nl.dias.domein.json.JsonHypotheek;
import nl.dias.domein.json.JsonHypotheekPakket;
import nl.dias.domein.json.JsonSoortHypotheek;
import nl.dias.service.HypotheekService;
import nl.dias.web.mapper.HypotheekMapper;
import nl.dias.web.mapper.HypotheekPakketMapper;
import nl.dias.web.mapper.SoortHypotheekMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Path("/hypotheek")
public class HypotheekController {
    private final static Logger LOGGER = LoggerFactory.getLogger(HypotheekController.class);

    @InjectParam
    private HypotheekService hypotheekService;
    @InjectParam
    private SoortHypotheekMapper soortHypotheekMapper;
    @InjectParam
    private HypotheekMapper hypotheekMapper;
    @InjectParam
    private HypotheekPakketMapper hypotheekPakketMapper;

    @GET
    @Path("/lees")
    @Produces(MediaType.APPLICATION_JSON)
    public JsonHypotheek lees(@QueryParam("id") String id) {
        JsonHypotheek jsonHypotheek = null;

        if (id == null || id.equals("") || id.equals("0")) {
            jsonHypotheek = new JsonHypotheek();
        } else {
            jsonHypotheek = hypotheekMapper.mapNaarJson(hypotheekService.leesHypotheek(Long.valueOf(id)));
        }

        return jsonHypotheek;
    }

    @GET
    @Path("/alleSoortenHypotheek")
    @Produces(MediaType.APPLICATION_JSON)
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

    @GET
    @Path("/lijstHypotheekPakketten")
    @Produces(MediaType.APPLICATION_JSON)
    public List<JsonHypotheekPakket> alleHypotheekPakketten(@QueryParam("relatieId") Long relatieId) {
        Set<HypotheekPakket> hypotheekPakketten = new HashSet<>();

        for (HypotheekPakket hypotheekPakket : hypotheekService.allePakketenVanRelatie(relatieId)) {
            hypotheekPakketten.add(hypotheekPakket);
        }

        return hypotheekPakketMapper.mapAllNaarJson(hypotheekPakketten);
    }

    @GET
    @Path("/lijstHypotheken")
    @Produces(MediaType.APPLICATION_JSON)
    public List<JsonHypotheek> alleHypotheken(@QueryParam("relatieId") Long relatieId) {
        Set<Hypotheek> hypotheken = new HashSet<>();

        for (Hypotheek soort : hypotheekService.allesVanRelatie(relatieId)) {
            hypotheken.add(soort);
        }

        return hypotheekMapper.mapAllNaarJson(hypotheken);
    }

    @GET
    @Path("/lijstHypothekenInclDePakketten")
    @Produces(MediaType.APPLICATION_JSON)
    public List<JsonHypotheek> alleHypothekenInclDePakketten(@QueryParam("relatieId") Long relatieId) {
        hypotheekService.leesHypotheek(14L);

        Set<Hypotheek> hypotheken = new HashSet<>();

        for (Hypotheek soort : hypotheekService.allesVanRelatieInclDePakketten(relatieId)) {
            hypotheken.add(soort);
        }

        return hypotheekMapper.mapAllNaarJson(hypotheken);
    }

    @POST
    @Path("/opslaan")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response opslaan(JsonHypotheek jsonHypotheek) {
        LOGGER.debug("Opslaan Hypotheek " + jsonHypotheek);

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

    @GET
    @Path("/leesHypotheekVorm")
    @Produces(MediaType.APPLICATION_JSON)
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
