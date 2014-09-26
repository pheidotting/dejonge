package nl.dias.web.medewerker;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import nl.dias.domein.Hypotheek;
import nl.dias.domein.SoortHypotheek;
import nl.dias.domein.json.JsonFoutmelding;
import nl.dias.domein.json.JsonHypotheek;
import nl.dias.domein.json.JsonSoortHypotheek;
import nl.dias.service.HypotheekService;
import nl.dias.web.mapper.HypotheekMapper;
import nl.dias.web.mapper.SoortHypotheekMapper;

import org.apache.log4j.Logger;

import com.sun.jersey.api.core.InjectParam;

@Path("/hypotheek")
public class HypotheekController {
    private final static Logger LOGGER = Logger.getLogger(HypotheekController.class);

    @InjectParam
    private HypotheekService hypotheekService;
    @InjectParam
    private SoortHypotheekMapper soortHypotheekMapper;
    @InjectParam
    private HypotheekMapper hypotheekMapper;

    @GET
    @Path("/lees")
    @Produces(MediaType.APPLICATION_JSON)
    public JsonHypotheek lees(@QueryParam("id") String id) {
        JsonHypotheek jsonHypotheek = null;

        if (id == null || id.equals("") || id.equals("0")) {
            jsonHypotheek = new JsonHypotheek();
        } else {
            jsonHypotheek = hypotheekMapper.mapNaarJson(hypotheekService.lees(Long.valueOf(id)));
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

        return soortHypotheekMapper.mapAllNaarJson(soorten);
    }

    @GET
    @Path("/lijst")
    @Produces(MediaType.APPLICATION_JSON)
    public List<JsonHypotheek> alles() {
        Set<Hypotheek> hypotheken = new HashSet<>();

        for (Hypotheek soort : hypotheekService.alles()) {
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

        Hypotheek hypotheek = hypotheekMapper.mapVanJson(jsonHypotheek);

        hypotheekService.opslaan(hypotheek, jsonHypotheek.getHypotheekVorm(), jsonHypotheek.getRelatie());

        LOGGER.debug("Opgeslagen");
        return Response.status(200).entity(new JsonFoutmelding(hypotheek.getId().toString())).build();
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
}
