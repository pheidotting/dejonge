package nl.dias.web.medewerker;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import nl.dias.domein.Bijlage;
import nl.dias.domein.Schade;
import nl.dias.domein.polis.Polis;
import nl.dias.service.BijlageService;
import nl.dias.service.PolisService;
import nl.dias.service.SchadeService;
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
    @Path("/upload")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response uploadFile(@FormDataParam("file") InputStream uploadedInputStream, @FormDataParam("file") FormDataContentDisposition fileDetail, @FormDataParam("polisNummer") String polisNummer) {

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
        if (fileDetail != null) {
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
