package nl.dias.web.medewerker;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
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

import nl.dias.domein.Bijlage;
import nl.dias.domein.Relatie;
import nl.dias.domein.Schade;
import nl.dias.domein.json.JsonBijlage;
import nl.dias.domein.polis.Polis;
import nl.dias.service.BijlageService;
import nl.dias.service.GebruikerService;
import nl.dias.service.PolisService;
import nl.dias.service.SchadeService;
import nl.dias.web.mapper.BijlageMapper;
import nl.lakedigital.archief.domain.ArchiefBestand;
import nl.lakedigital.archief.service.ArchiefService;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.sun.jersey.api.core.InjectParam;
import com.sun.jersey.core.header.FormDataContentDisposition;
import com.sun.jersey.multipart.FormDataParam;

@Path("/bijlage")
public class BijlageController {
    private final static Logger LOGGER = Logger.getLogger(BijlageController.class);

    @InjectParam
    private ArchiefService archiefService;
    @InjectParam
    private PolisService polisService;
    @InjectParam
    private BijlageService bijlageService;
    @InjectParam
    private SchadeService schadeService;
    @InjectParam
    private GebruikerService gebruikerService;
    @InjectParam
    private BijlageMapper bijlageMapper;

    @GET
    @Path("/verwijder")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response verwijder(@QueryParam("bijlageId") String bijlageId) {
        bijlageService.verwijderBijlage(Long.valueOf(bijlageId));
        return Response.ok().build();
    }

    @GET
    @Path("/lijst")
    @Produces(MediaType.APPLICATION_JSON)
    public List<JsonBijlage> lijst(@QueryParam("relatieId") String relatieId) {
        Relatie relatie = (Relatie) gebruikerService.lees(Long.valueOf(relatieId));

        Set<Bijlage> bijlages = new HashSet<>();
        for (Bijlage bijlage : bijlageService.alleBijlagesBijRelatie(relatie)) {
            bijlages.add(bijlage);
        }

        return bijlageMapper.mapAllNaarJson(bijlages);
    }

    @GET
    @Path("/download")
    @Produces("application/pdf")
    public Response getFile(@QueryParam("bijlageId") String bijlageId) throws IOException {
        archiefService.setBucketName("dias");
        LOGGER.debug("Ophalen bijlage met id " + bijlageId);

        Bijlage bijlage = polisService.leesBijlage(Long.parseLong(bijlageId));
        ArchiefBestand archiefBestand = archiefService.ophalen(bijlage.getS3Identificatie(), false);

        File tmpFile = File.createTempFile("dias", "download");

        try {
            bijlageService.writeToFile(new FileInputStream(archiefBestand.getBestand()), tmpFile.toString());
        } catch (FileNotFoundException e1) {
            LOGGER.error("Bestand niet gevonden", e1);
        }

        Date fileDate = new Date(archiefBestand.getBestand().lastModified());
        try {
            return Response.ok(new FileInputStream(archiefBestand.getBestand())).lastModified(fileDate).build();
        } catch (FileNotFoundException e) {
            LOGGER.error("Bestand niet gevonden", e);
            return Response.noContent().build();
        }
    }

    @POST
    @Path("/uploadPolis1File")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response uploadPolis1File(@FormDataParam("uploadPolis1File") InputStream uploadedInputStream, @FormDataParam("uploadPolis1File") FormDataContentDisposition fileDetail,
            @FormDataParam("polisNummer") String polisNummer) {
        LOGGER.debug("uploaden bestand 1");
        uploaden(uploadedInputStream, fileDetail, polisNummer, null);

        return Response.status(200).entity("").build();
    }

    @POST
    @Path("/uploadPolis2File")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response uploadPolis2File(@FormDataParam("uploadPolis2File") InputStream uploadedInputStream, @FormDataParam("uploadPolis2File") FormDataContentDisposition fileDetail,
            @FormDataParam("polisNummer") String polisNummer) {
        LOGGER.debug("uploaden bestand 1");
        uploaden(uploadedInputStream, fileDetail, polisNummer, null);

        return Response.status(200).entity("").build();
    }

    @POST
    @Path("/uploadPolis3File")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response uploadPolis3File(@FormDataParam("uploadPolis3File") InputStream uploadedInputStream, @FormDataParam("uploadPolis3File") FormDataContentDisposition fileDetail,
            @FormDataParam("polisNummer") String polisNummer) {
        LOGGER.debug("uploaden bestand 1");
        uploaden(uploadedInputStream, fileDetail, polisNummer, null);

        return Response.status(200).entity("").build();
    }

    @POST
    @Path("/uploadPolis4File")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response uploadPolis4File(@FormDataParam("uploadPolis4File") InputStream uploadedInputStream, @FormDataParam("uploadPolis4File") FormDataContentDisposition fileDetail,
            @FormDataParam("polisNummer") String polisNummer) {
        LOGGER.debug("uploaden bestand 1");
        uploaden(uploadedInputStream, fileDetail, polisNummer, null);

        return Response.status(200).entity("").build();
    }

    @POST
    @Path("/uploadPolis5File")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response uploadPolis5File(@FormDataParam("uploadPolis5File") InputStream uploadedInputStream, @FormDataParam("uploadPolis5File") FormDataContentDisposition fileDetail,
            @FormDataParam("polisNummer") String polisNummer) {
        LOGGER.debug("uploaden bestand 1");
        uploaden(uploadedInputStream, fileDetail, polisNummer, null);

        return Response.status(200).entity("").build();
    }

    @POST
    @Path("/uploadPolis6File")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response uploadPolis6File(@FormDataParam("uploadPolis6File") InputStream uploadedInputStream, @FormDataParam("uploadPolis6File") FormDataContentDisposition fileDetail,
            @FormDataParam("polisNummer") String polisNummer) {
        LOGGER.debug("uploaden bestand 1");
        uploaden(uploadedInputStream, fileDetail, polisNummer, null);

        return Response.status(200).entity("").build();
    }

    @POST
    @Path("/uploadPolis7File")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response uploadPolis7File(@FormDataParam("uploadPolis7File") InputStream uploadedInputStream, @FormDataParam("uploadPolis7File") FormDataContentDisposition fileDetail,
            @FormDataParam("polisNummer") String polisNummer) {
        LOGGER.debug("uploaden bestand 1");
        uploaden(uploadedInputStream, fileDetail, polisNummer, null);

        return Response.status(200).entity("").build();
    }

    @POST
    @Path("/uploadPolis8File")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response uploadPolis8File(@FormDataParam("uploadPolis8File") InputStream uploadedInputStream, @FormDataParam("uploadPolis8File") FormDataContentDisposition fileDetail,
            @FormDataParam("polisNummer") String polisNummer) {
        LOGGER.debug("uploaden bestand 1");
        uploaden(uploadedInputStream, fileDetail, polisNummer, null);

        return Response.status(200).entity("").build();
    }

    @POST
    @Path("/uploadPolis9File")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response uploadPolis9File(@FormDataParam("uploadPolis9File") InputStream uploadedInputStream, @FormDataParam("uploadPolis9File") FormDataContentDisposition fileDetail,
            @FormDataParam("polisNummer") String polisNummer) {
        LOGGER.debug("uploaden bestand 1");
        uploaden(uploadedInputStream, fileDetail, polisNummer, null);

        return Response.status(200).entity("").build();
    }

    @POST
    @Path("/uploadPolis10File")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response uploadPolis10File(@FormDataParam("uploadPolis10File") InputStream uploadedInputStream, @FormDataParam("uploadPolis10File") FormDataContentDisposition fileDetail,
            @FormDataParam("polisNummer") String polisNummer) {
        LOGGER.debug("uploaden bestand 1");
        uploaden(uploadedInputStream, fileDetail, polisNummer, null);

        return Response.status(200).entity("").build();
    }

    @POST
    @Path("/uploadSchade1File")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response uploadSchade1File(@FormDataParam("uploadSchade1File") InputStream uploadedInputStream, @FormDataParam("uploadSchade1File") FormDataContentDisposition fileDetail,
            @FormDataParam("schadeNummerMaatschappij") String schadeNummerMaatschappij) {

        uploaden(uploadedInputStream, fileDetail, null, schadeNummerMaatschappij);

        return Response.status(200).entity("").build();
    }

    @POST
    @Path("/uploadSchade2File")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response uploadSchade2File(@FormDataParam("uploadSchade2File") InputStream uploadedInputStream, @FormDataParam("uploadSchade2File") FormDataContentDisposition fileDetail,
            @FormDataParam("schadeNummerMaatschappij") String schadeNummerMaatschappij) {

        uploaden(uploadedInputStream, fileDetail, null, schadeNummerMaatschappij);

        return Response.status(200).entity("").build();
    }

    @POST
    @Path("/uploadSchade3File")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response uploadSchade3File(@FormDataParam("uploadSchade3File") InputStream uploadedInputStream, @FormDataParam("uploadSchade3File") FormDataContentDisposition fileDetail,
            @FormDataParam("schadeNummerMaatschappij") String schadeNummerMaatschappij) {

        uploaden(uploadedInputStream, fileDetail, null, schadeNummerMaatschappij);

        return Response.status(200).entity("").build();
    }

    @POST
    @Path("/uploadSchade4File")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response uploadSchade4File(@FormDataParam("uploadSchade4File") InputStream uploadedInputStream, @FormDataParam("uploadSchade4File") FormDataContentDisposition fileDetail,
            @FormDataParam("schadeNummerMaatschappij") String schadeNummerMaatschappij) {

        uploaden(uploadedInputStream, fileDetail, null, schadeNummerMaatschappij);

        return Response.status(200).entity("").build();
    }

    @POST
    @Path("/uploadSchade5File")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response uploadSchade5File(@FormDataParam("uploadSchade5File") InputStream uploadedInputStream, @FormDataParam("uploadSchade5File") FormDataContentDisposition fileDetail,
            @FormDataParam("schadeNummerMaatschappij") String schadeNummerMaatschappij) {

        uploaden(uploadedInputStream, fileDetail, null, schadeNummerMaatschappij);

        return Response.status(200).entity("").build();
    }

    @POST
    @Path("/uploadSchade6File")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response uploadSchade6File(@FormDataParam("uploadSchade6File") InputStream uploadedInputStream, @FormDataParam("uploadSchade6File") FormDataContentDisposition fileDetail,
            @FormDataParam("schadeNummerMaatschappij") String schadeNummerMaatschappij) {

        uploaden(uploadedInputStream, fileDetail, null, schadeNummerMaatschappij);

        return Response.status(200).entity("").build();
    }

    @POST
    @Path("/uploadSchade7File")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response uploadSchade7File(@FormDataParam("uploadSchade7File") InputStream uploadedInputStream, @FormDataParam("uploadSchade7File") FormDataContentDisposition fileDetail,
            @FormDataParam("schadeNummerMaatschappij") String schadeNummerMaatschappij) {

        uploaden(uploadedInputStream, fileDetail, null, schadeNummerMaatschappij);

        return Response.status(200).entity("").build();
    }

    @POST
    @Path("/uploadSchade8File")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response uploadSchade8File(@FormDataParam("uploadSchade8File") InputStream uploadedInputStream, @FormDataParam("uploadSchade8File") FormDataContentDisposition fileDetail,
            @FormDataParam("schadeNummerMaatschappij") String schadeNummerMaatschappij) {

        uploaden(uploadedInputStream, fileDetail, null, schadeNummerMaatschappij);

        return Response.status(200).entity("").build();
    }

    @POST
    @Path("/uploadSchade9File")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response uploadSchade9File(@FormDataParam("uploadSchade9File") InputStream uploadedInputStream, @FormDataParam("uploadSchade9File") FormDataContentDisposition fileDetail,
            @FormDataParam("schadeNummerMaatschappij") String schadeNummerMaatschappij) {

        uploaden(uploadedInputStream, fileDetail, null, schadeNummerMaatschappij);

        return Response.status(200).entity("").build();
    }

    @POST
    @Path("/uploadSchade10File")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response uploadSchade10File(@FormDataParam("uploadSchade10File") InputStream uploadedInputStream, @FormDataParam("uploadSchade10File") FormDataContentDisposition fileDetail,
            @FormDataParam("schadeNummerMaatschappij") String schadeNummerMaatschappij) {

        uploaden(uploadedInputStream, fileDetail, null, schadeNummerMaatschappij);

        return Response.status(200).entity("").build();
    }

    private void uploaden(InputStream uploadedInputStream, FormDataContentDisposition fileDetail, String polisNummer, String schadeNummerMaatschappij) {

        String bestandsnaam = null;
        if (fileDetail != null && fileDetail.getFileName() != null && uploadedInputStream != null) {
            bestandsnaam = fileDetail.getFileName();

            LOGGER.debug("opslaan bijlage bij polis " + polisNummer + ", of bij schadeNummerMaatschappij " + schadeNummerMaatschappij + " en bestandsnaam " + bestandsnaam);

            if (StringUtils.isNotBlank(polisNummer)) {
                Polis polis = polisService.zoekOpPolisNummer(polisNummer);

                LOGGER.debug("eigen database bijwerken");
                polisService.slaBijlageOp(polis.getId(), bijlageService.uploaden(uploadedInputStream, fileDetail));
            } else {
                Schade schade = schadeService.zoekOpSchadeNummerMaatschappij(schadeNummerMaatschappij);

                LOGGER.debug("Opslaan schade en uploaden bijlage " + fileDetail.getFileName());

                schadeService.slaBijlageOp(schade.getId(), bijlageService.uploaden(uploadedInputStream, fileDetail));
            }
        }
    }
}
