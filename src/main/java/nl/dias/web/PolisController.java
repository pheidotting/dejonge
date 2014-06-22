package nl.dias.web;

import java.io.InputStream;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import nl.dias.domein.json.JsonFoutmelding;
import nl.dias.domein.json.OpslaanPolis;
import nl.dias.domein.polis.Polis;
import nl.dias.service.BijlageService;
import nl.dias.service.GebruikerService;
import nl.dias.service.PolisService;

import org.apache.log4j.Logger;

import com.sun.jersey.api.core.InjectParam;
import com.sun.jersey.core.header.FormDataContentDisposition;
import com.sun.jersey.multipart.FormDataParam;

@Path("/polis")
public class PolisController {
    private final static Logger LOGGER = Logger.getLogger(PolisController.class);

    @InjectParam
    private PolisService polisService;
    @InjectParam
    private GebruikerService gebruikerService;
    @InjectParam
    private BijlageService bijlageService;

    @POST
    @Path("/opslaan")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response opslaan(OpslaanPolis opslaanPolis) {
        try {
            polisService.opslaan(opslaanPolis);
        } catch (IllegalArgumentException e) {
            LOGGER.debug(e.getMessage());
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
        polisService.slaBijlageOp(polis.getId(), bijlageService.uploaden(uploadedInputStream, fileDetail));

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
            LOGGER.error(e.getMessage());
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
