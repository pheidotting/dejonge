package nl.dias.web.medewerker;

import com.sun.jersey.api.core.InjectParam;
import com.sun.jersey.core.header.FormDataContentDisposition;
import com.sun.jersey.multipart.FormDataParam;
import nl.dias.domein.Relatie;
import nl.dias.domein.json.JsonFoutmelding;
import nl.dias.domein.json.JsonPolis;
import nl.dias.domein.polis.Polis;
import nl.dias.service.BijlageService;
import nl.dias.service.GebruikerService;
import nl.dias.service.PolisService;
import nl.dias.web.mapper.PolisMapper;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.InputStream;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Path("/polis")
public class PolisController {
    private final static Logger LOGGER = LoggerFactory.getLogger(PolisController.class);

    @InjectParam
    private PolisService polisService;
    @InjectParam
    private GebruikerService gebruikerService;
    @InjectParam
    private BijlageService bijlageService;
    @InjectParam
    private PolisMapper polisMapper;

    @GET
    @Path("/lees")
    @Produces(MediaType.APPLICATION_JSON)
    public JsonPolis lees(@QueryParam("id") String id) {
        LOGGER.debug("ophalen Polis met id " + id);
        if (id != null && !"".equals(id) && !"0".equals(id)) {
            LOGGER.debug("ophalen Polis");
            return polisMapper.mapNaarJson(polisService.lees(Long.valueOf(id)));
        } else {
            LOGGER.debug("Nieuwe Polis tonen");
            return new JsonPolis();
        }
    }

    @GET
    @Path("/beeindigen")
    @Produces(MediaType.APPLICATION_JSON)
    public void beeindigen(@QueryParam("id") Long id) {
        LOGGER.debug("beeindigen Polis met id " + id);
        polisService.beeindigen(id);
    }

    @GET
    @Path("/lijst")
    @Produces(MediaType.APPLICATION_JSON)
    public List<JsonPolis> lijst(@QueryParam("relatieId") String relatieId) {
        LOGGER.debug("Ophalen alle polissen voor Relatie " + relatieId);
        Relatie relatie = (Relatie) gebruikerService.lees(Long.valueOf(relatieId));

        Set<Polis> polissen = new HashSet<>();
        for (Polis polis : polisService.allePolissenBijRelatie(relatie)) {
            polissen.add(polis);
        }

        return polisMapper.mapAllNaarJson(polissen);
    }

    @POST
    @Path("/opslaan")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response opslaan(JsonPolis jsonPolis) {
        LOGGER.debug("Opslaan " + ReflectionToStringBuilder.toString(jsonPolis));
        try {
            polisService.opslaan(polisMapper.mapVanJson(jsonPolis));
        } catch (IllegalArgumentException e) {
            LOGGER.debug("Fout opgetreden bij opslaan Polis", e);
            return Response.status(500).entity(new JsonFoutmelding(e.getMessage())).build();
        }
        return Response.status(200).entity(new JsonFoutmelding()).build();
    }

    @POST
    @Path("/upload")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response uploadFile(@FormDataParam("file") InputStream uploadedInputStream, @FormDataParam("file") FormDataContentDisposition fileDetail, @FormDataParam("polisNummer") String polisNummer) {

        LOGGER.debug("opslaan bijlage bij polis " + polisNummer + ", bestandsnaam " + fileDetail.getFileName());
        Polis polis = polisService.zoekOpPolisNummer(polisNummer);

        LOGGER.debug("eigen database bijwerken");
//        polisService.slaBijlageOp(polis.getId(), bijlageService.uploaden(uploadedInputStream, fileDetail), omsch);

        return Response.status(200).entity("").build();

    }

    @GET
    @Path("/verwijder")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response verwijder(@QueryParam("id") Long id) {
        LOGGER.debug("verwijderen Polis met id " + id);
        try {
            polisService.verwijder(id);
        } catch (IllegalArgumentException e) {
            LOGGER.error("Fout bij verwijderen Polis", e);
            return Response.status(500).entity(new JsonFoutmelding(e.getMessage())).build();
        }
        return Response.status(202).entity(new JsonFoutmelding()).build();
    }

    public void setPolisService(PolisService polisService) {
        this.polisService = polisService;
    }

    public void setGebruikerService(GebruikerService gebruikerService) {
        this.gebruikerService = gebruikerService;
    }
}
