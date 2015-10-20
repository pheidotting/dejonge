package nl.dias.web.medewerker;

import com.sun.jersey.api.core.InjectParam;
import com.sun.jersey.core.header.FormDataContentDisposition;
import com.sun.jersey.multipart.FormDataParam;
import nl.dias.domein.Aangifte;
import nl.dias.domein.Bijlage;
import nl.dias.domein.Relatie;
import nl.dias.domein.Schade;
import nl.dias.domein.json.JsonBijlage;
import nl.dias.domein.polis.Polis;
import nl.dias.service.*;
import nl.dias.utils.Utils;
import nl.dias.web.mapper.BijlageMapper;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Path("/bijlage")
public class BijlageController {
    private final static Logger LOGGER = LoggerFactory.getLogger(BijlageController.class);

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
    @InjectParam
    private HypotheekService hypotheekService;
    @InjectParam
    private AangifteService aangifteService;

    @GET
    @Path("/verwijder")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response verwijder(@QueryParam("id") String id) {
        bijlageService.verwijderBijlage(Long.valueOf(id));
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
    public Response getFile(@QueryParam("id") String id) throws IOException {
        LOGGER.debug("Ophalen bijlage met id " + id);

        Bijlage bijlage = polisService.leesBijlage(Long.parseLong(id));

        File file = new File(Utils.getUploadPad() + "/" + bijlage.getS3Identificatie());

        Date fileDate = new Date(file.lastModified());
        return Response.ok(new FileInputStream(file)).lastModified(fileDate).build();
    }

    @POST
    @Path("/uploadBijlageBijRelatie")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public String uploadBijlageBijRelatie(@FormDataParam("bijlageFile") InputStream uploadedInputStream, @FormDataParam("bijlageFile") FormDataContentDisposition fileDetail, @FormDataParam("relatieId") String relatieId) {
        LOGGER.debug("uploaden bestand 1");
        String bijlageId = uploaden(uploadedInputStream, fileDetail, null, null, null, null, null, relatieId);

        //        return Response.status(200).entity(bijlageId).build();
        return bijlageId;
    }


    @POST
    @Path("/uploadBijlage")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public JsonBijlage uploadBijlage(@FormDataParam("bijlageFile") InputStream uploadedInputStream, @FormDataParam("bijlageFile") FormDataContentDisposition fileDetail, @FormDataParam("id") String id, @FormDataParam("soortEntiteit") String soortEntiteit) {
        LOGGER.debug("uploaden bestand voor {} met id {}", soortEntiteit, id);

        return bijlageMapper.mapNaarJson(uploaden(uploadedInputStream, fileDetail, soortEntiteit, id));
    }

    @POST
    @Path("/uploadHypotheek1File")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public String uploadHypotheek1File(@FormDataParam("uploadHypotheek1File") InputStream uploadedInputStream, @FormDataParam("uploadHypotheek1File") FormDataContentDisposition fileDetail, @FormDataParam("id") String id, @FormDataParam("uploadHypotheek1Omschrijving") String omschrijving) {
        LOGGER.debug("uploaden bestand 1");
        String bijlageId = uploaden(uploadedInputStream, fileDetail, null, null, id, null, omschrijving, null);

        //        return Response.status(200).entity(bijlageId).build();
        return bijlageId;
    }

    @POST
    @Path("/uploadHypotheek2File")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response uploadHypotheek2File(@FormDataParam("uploadHypotheek2File") InputStream uploadedInputStream, @FormDataParam("uploadHypotheek2File") FormDataContentDisposition fileDetail, @FormDataParam("id") String id, @FormDataParam("uploadHypotheek2Omschrijving") String omschrijving) {
        LOGGER.debug("uploaden bestand 2");
        String bijlageId = uploaden(uploadedInputStream, fileDetail, null, null, id, null, omschrijving, null);

        return Response.status(200).entity(bijlageId).build();
    }

    @POST
    @Path("/uploadHypotheek3File")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response uploadHypotheek3File(@FormDataParam("uploadHypotheek3File") InputStream uploadedInputStream, @FormDataParam("uploadHypotheek3File") FormDataContentDisposition fileDetail, @FormDataParam("id") String id, @FormDataParam("uploadHypotheek3Omschrijving") String omschrijving) {
        LOGGER.debug("uploaden bestand 3");
        String bijlageId = uploaden(uploadedInputStream, fileDetail, null, null, id, null, omschrijving, null);

        return Response.status(200).entity(bijlageId).build();
    }

    @POST
    @Path("/uploadHypotheek4File")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response uploadHypotheek4File(@FormDataParam("uploadHypotheek4File") InputStream uploadedInputStream, @FormDataParam("uploadHypotheek4File") FormDataContentDisposition fileDetail, @FormDataParam("id") String id, @FormDataParam("uploadHypotheek4Omschrijving") String omschrijving) {
        LOGGER.debug("uploaden bestand 4");
        String bijlageId = uploaden(uploadedInputStream, fileDetail, null, null, id, null, omschrijving, null);

        return Response.status(200).entity(bijlageId).build();
    }

    @POST
    @Path("/uploadHypotheek5File")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response uploadHypotheek5File(@FormDataParam("uploadHypotheek5File") InputStream uploadedInputStream, @FormDataParam("uploadHypotheek5File") FormDataContentDisposition fileDetail, @FormDataParam("id") String id, @FormDataParam("uploadHypotheek5Omschrijving") String omschrijving) {
        LOGGER.debug("uploaden bestand 5");
        String bijlageId = uploaden(uploadedInputStream, fileDetail, null, null, id, null, omschrijving, null);

        return Response.status(200).entity(bijlageId).build();
    }

    @POST
    @Path("/uploadHypotheek6File")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response uploadHypotheek6File(@FormDataParam("uploadHypotheek6File") InputStream uploadedInputStream, @FormDataParam("uploadHypotheek6File") FormDataContentDisposition fileDetail, @FormDataParam("id") String id, @FormDataParam("uploadHypotheek6Omschrijving") String omschrijving) {
        LOGGER.debug("uploaden bestand 6");
        String bijlageId = uploaden(uploadedInputStream, fileDetail, null, null, id, null, omschrijving, null);

        return Response.status(200).entity(bijlageId).build();
    }

    @POST
    @Path("/uploadHypotheek7File")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response uploadHypotheek7File(@FormDataParam("uploadHypotheek7File") InputStream uploadedInputStream, @FormDataParam("uploadHypotheek7File") FormDataContentDisposition fileDetail, @FormDataParam("id") String id, @FormDataParam("uploadHypotheek7Omschrijving") String omschrijving) {
        LOGGER.debug("uploaden bestand 7");
        String bijlageId = uploaden(uploadedInputStream, fileDetail, null, null, id, null, omschrijving, null);

        return Response.status(200).entity(bijlageId).build();
    }

    @POST
    @Path("/uploadHypotheek8File")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response uploadHypotheek8File(@FormDataParam("uploadHypotheek8File") InputStream uploadedInputStream, @FormDataParam("uploadHypotheek8File") FormDataContentDisposition fileDetail, @FormDataParam("id") String id, @FormDataParam("uploadHypotheek8Omschrijving") String omschrijving) {
        LOGGER.debug("uploaden bestand 8");
        String bijlageId = uploaden(uploadedInputStream, fileDetail, null, null, id, null, omschrijving, null);

        return Response.status(200).entity(bijlageId).build();
    }

    @POST
    @Path("/uploadHypotheek9File")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response uploadHypotheek9File(@FormDataParam("uploadHypotheek9File") InputStream uploadedInputStream, @FormDataParam("uploadHypotheek9File") FormDataContentDisposition fileDetail, @FormDataParam("id") String id, @FormDataParam("uploadHypotheek9Omschrijving") String omschrijving) {
        LOGGER.debug("uploaden bestand 9");
        String bijlageId = uploaden(uploadedInputStream, fileDetail, null, null, id, null, omschrijving, null);

        return Response.status(200).entity(bijlageId).build();
    }

    @POST
    @Path("/uploadHypotheek10File")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response uploadHypotheek10File(@FormDataParam("uploadHypotheek10File") InputStream uploadedInputStream, @FormDataParam("uploadHypotheek10File") FormDataContentDisposition fileDetail, @FormDataParam("id") String id, @FormDataParam("uploadHypotheek10Omschrijving") String omschrijving) {
        LOGGER.debug("uploaden bestand 10");
        String bijlageId = uploaden(uploadedInputStream, fileDetail, null, null, id, null, omschrijving, null);

        return Response.status(200).entity(bijlageId).build();
    }

    @POST
    @Path("/uploadPolis1File")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response uploadPolis1File(@FormDataParam("uploadPolis1File") InputStream uploadedInputStream, @FormDataParam("uploadPolis1File") FormDataContentDisposition fileDetail, @FormDataParam("polisNummer") String polisNummer, @FormDataParam("uploadPolis1Omschrijving") String omschrijving) {
        LOGGER.debug("uploaden bestand 1");
        String bijlageId = uploaden(uploadedInputStream, fileDetail, polisNummer, null, null, null, omschrijving, null);

        return Response.status(200).entity(bijlageId).build();
    }

    @POST
    @Path("/uploadPolis2File")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response uploadPolis2File(@FormDataParam("uploadPolis2File") InputStream uploadedInputStream, @FormDataParam("uploadPolis2File") FormDataContentDisposition fileDetail, @FormDataParam("polisNummer") String polisNummer, @FormDataParam("uploadPolis2Omschrijving") String omschrijving) {
        LOGGER.debug("uploaden bestand 1");
        String bijlageId = uploaden(uploadedInputStream, fileDetail, polisNummer, null, null, null, omschrijving, null);

        return Response.status(200).entity(bijlageId).build();
    }

    @POST
    @Path("/uploadPolis3File")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response uploadPolis3File(@FormDataParam("uploadPolis3File") InputStream uploadedInputStream, @FormDataParam("uploadPolis3File") FormDataContentDisposition fileDetail, @FormDataParam("polisNummer") String polisNummer, @FormDataParam("uploadPolis3Omschrijving") String omschrijving) {
        LOGGER.debug("uploaden bestand 1");
        String bijlageId = uploaden(uploadedInputStream, fileDetail, polisNummer, null, null, null, omschrijving, null);

        return Response.status(200).entity(bijlageId).build();
    }

    @POST
    @Path("/uploadPolis4File")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response uploadPolis4File(@FormDataParam("uploadPolis4File") InputStream uploadedInputStream, @FormDataParam("uploadPolis4File") FormDataContentDisposition fileDetail, @FormDataParam("polisNummer") String polisNummer, @FormDataParam("uploadPolis4Omschrijving") String omschrijving) {
        LOGGER.debug("uploaden bestand 1");
        String bijlageId = uploaden(uploadedInputStream, fileDetail, polisNummer, null, null, null, omschrijving, null);

        return Response.status(200).entity(bijlageId).build();
    }

    @POST
    @Path("/uploadPolis5File")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response uploadPolis5File(@FormDataParam("uploadPolis5File") InputStream uploadedInputStream, @FormDataParam("uploadPolis5File") FormDataContentDisposition fileDetail, @FormDataParam("polisNummer") String polisNummer, @FormDataParam("uploadPolis5Omschrijving") String omschrijving) {
        LOGGER.debug("uploaden bestand 1");
        String bijlageId = uploaden(uploadedInputStream, fileDetail, polisNummer, null, null, null, omschrijving, null);

        return Response.status(200).entity(bijlageId).build();
    }

    @POST
    @Path("/uploadPolis6File")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response uploadPolis6File(@FormDataParam("uploadPolis6File") InputStream uploadedInputStream, @FormDataParam("uploadPolis6File") FormDataContentDisposition fileDetail, @FormDataParam("polisNummer") String polisNummer, @FormDataParam("uploadPolis6Omschrijving") String omschrijving) {
        LOGGER.debug("uploaden bestand 1");
        String bijlageId = uploaden(uploadedInputStream, fileDetail, polisNummer, null, null, null, omschrijving, null);

        return Response.status(200).entity(bijlageId).build();
    }

    @POST
    @Path("/uploadPolis7File")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response uploadPolis7File(@FormDataParam("uploadPolis7File") InputStream uploadedInputStream, @FormDataParam("uploadPolis7File") FormDataContentDisposition fileDetail, @FormDataParam("polisNummer") String polisNummer, @FormDataParam("uploadPolis7Omschrijving") String omschrijving) {
        LOGGER.debug("uploaden bestand 1");
        String bijlageId = uploaden(uploadedInputStream, fileDetail, polisNummer, null, null, null, omschrijving, null);

        return Response.status(200).entity(bijlageId).build();
    }

    @POST
    @Path("/uploadPolis8File")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response uploadPolis8File(@FormDataParam("uploadPolis8File") InputStream uploadedInputStream, @FormDataParam("uploadPolis8File") FormDataContentDisposition fileDetail, @FormDataParam("polisNummer") String polisNummer, @FormDataParam("uploadPolis8Omschrijving") String omschrijving) {
        LOGGER.debug("uploaden bestand 1");
        String bijlageId = uploaden(uploadedInputStream, fileDetail, polisNummer, null, null, null, omschrijving, null);

        return Response.status(200).entity(bijlageId).build();
    }

    @POST
    @Path("/uploadPolis9File")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response uploadPolis9File(@FormDataParam("uploadPolis9File") InputStream uploadedInputStream, @FormDataParam("uploadPolis9File") FormDataContentDisposition fileDetail, @FormDataParam("polisNummer") String polisNummer, @FormDataParam("uploadPolis9Omschrijving") String omschrijving) {
        LOGGER.debug("uploaden bestand 1");
        String bijlageId = uploaden(uploadedInputStream, fileDetail, polisNummer, null, null, null, omschrijving, null);

        return Response.status(200).entity(bijlageId).build();
    }

    @POST
    @Path("/uploadPolis10File")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response uploadPolis10File(@FormDataParam("uploadPolis10File") InputStream uploadedInputStream, @FormDataParam("uploadPolis10File") FormDataContentDisposition fileDetail, @FormDataParam("polisNummer") String polisNummer, @FormDataParam("uploadPolis10Omschrijving") String omschrijving) {
        LOGGER.debug("uploaden bestand 1");
        String bijlageId = uploaden(uploadedInputStream, fileDetail, polisNummer, null, null, null, omschrijving, null);

        return Response.status(200).entity(bijlageId).build();
    }

    @POST
    @Path("/uploadSchade1File")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response uploadSchade1File(@FormDataParam("uploadSchade1File") InputStream uploadedInputStream, @FormDataParam("uploadSchade1File") FormDataContentDisposition fileDetail, @FormDataParam("schadeNummerMaatschappij") String schadeNummerMaatschappij, @FormDataParam("uploadSchade1Omschrijving") String omschrijving) {

        String bijlageId = uploaden(uploadedInputStream, fileDetail, null, schadeNummerMaatschappij, null, null, omschrijving, null);

        return Response.status(200).entity(bijlageId).build();
    }

    @POST
    @Path("/uploadSchade2File")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response uploadSchade2File(@FormDataParam("uploadSchade2File") InputStream uploadedInputStream, @FormDataParam("uploadSchade2File") FormDataContentDisposition fileDetail, @FormDataParam("schadeNummerMaatschappij") String schadeNummerMaatschappij, @FormDataParam("uploadSchade2Omschrijving") String omschrijving) {

        String bijlageId = uploaden(uploadedInputStream, fileDetail, null, schadeNummerMaatschappij, null, null, omschrijving, null);

        return Response.status(200).entity(bijlageId).build();
    }

    @POST
    @Path("/uploadSchade3File")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response uploadSchade3File(@FormDataParam("uploadSchade3File") InputStream uploadedInputStream, @FormDataParam("uploadSchade3File") FormDataContentDisposition fileDetail, @FormDataParam("schadeNummerMaatschappij") String schadeNummerMaatschappij, @FormDataParam("uploadSchade3Omschrijving") String omschrijving) {

        String bijlageId = uploaden(uploadedInputStream, fileDetail, null, schadeNummerMaatschappij, null, null, omschrijving, null);

        return Response.status(200).entity(bijlageId).build();
    }

    @POST
    @Path("/uploadSchade4File")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response uploadSchade4File(@FormDataParam("uploadSchade4File") InputStream uploadedInputStream, @FormDataParam("uploadSchade4File") FormDataContentDisposition fileDetail, @FormDataParam("schadeNummerMaatschappij") String schadeNummerMaatschappij, @FormDataParam("uploadSchade4Omschrijving") String omschrijving) {

        String bijlageId = uploaden(uploadedInputStream, fileDetail, null, schadeNummerMaatschappij, null, null, omschrijving, null);

        return Response.status(200).entity(bijlageId).build();
    }

    @POST
    @Path("/uploadSchade5File")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response uploadSchade5File(@FormDataParam("uploadSchade5File") InputStream uploadedInputStream, @FormDataParam("uploadSchade5File") FormDataContentDisposition fileDetail, @FormDataParam("schadeNummerMaatschappij") String schadeNummerMaatschappij, @FormDataParam("uploadSchade5Omschrijving") String omschrijving) {

        String bijlageId = uploaden(uploadedInputStream, fileDetail, null, schadeNummerMaatschappij, null, null, omschrijving, null);

        return Response.status(200).entity(bijlageId).build();
    }

    @POST
    @Path("/uploadSchade6File")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response uploadSchade6File(@FormDataParam("uploadSchade6File") InputStream uploadedInputStream, @FormDataParam("uploadSchade6File") FormDataContentDisposition fileDetail, @FormDataParam("schadeNummerMaatschappij") String schadeNummerMaatschappij, @FormDataParam("uploadSchade6Omschrijving") String omschrijving) {

        String bijlageId = uploaden(uploadedInputStream, fileDetail, null, schadeNummerMaatschappij, null, null, omschrijving, null);

        return Response.status(200).entity(bijlageId).build();
    }

    @POST
    @Path("/uploadSchade7File")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response uploadSchade7File(@FormDataParam("uploadSchade7File") InputStream uploadedInputStream, @FormDataParam("uploadSchade7File") FormDataContentDisposition fileDetail, @FormDataParam("schadeNummerMaatschappij") String schadeNummerMaatschappij, @FormDataParam("uploadSchade7Omschrijving") String omschrijving) {

        String bijlageId = uploaden(uploadedInputStream, fileDetail, null, schadeNummerMaatschappij, null, null, omschrijving, null);

        return Response.status(200).entity(bijlageId).build();
    }

    @POST
    @Path("/uploadSchade8File")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response uploadSchade8File(@FormDataParam("uploadSchade8File") InputStream uploadedInputStream, @FormDataParam("uploadSchade8File") FormDataContentDisposition fileDetail, @FormDataParam("schadeNummerMaatschappij") String schadeNummerMaatschappij, @FormDataParam("uploadSchade8Omschrijving") String omschrijving) {

        String bijlageId = uploaden(uploadedInputStream, fileDetail, null, schadeNummerMaatschappij, null, null, omschrijving, null);

        return Response.status(200).entity(bijlageId).build();
    }

    @POST
    @Path("/uploadSchade9File")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response uploadSchade9File(@FormDataParam("uploadSchade9File") InputStream uploadedInputStream, @FormDataParam("uploadSchade9File") FormDataContentDisposition fileDetail, @FormDataParam("schadeNummerMaatschappij") String schadeNummerMaatschappij, @FormDataParam("uploadSchade9Omschrijving") String omschrijving) {

        String bijlageId = uploaden(uploadedInputStream, fileDetail, null, schadeNummerMaatschappij, null, null, omschrijving, null);

        return Response.status(200).entity(bijlageId).build();
    }

    @POST
    @Path("/uploadSchade10File")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response uploadSchade10File(@FormDataParam("uploadSchade10File") InputStream uploadedInputStream, @FormDataParam("uploadSchade10File") FormDataContentDisposition fileDetail, @FormDataParam("schadeNummerMaatschappij") String schadeNummerMaatschappij, @FormDataParam("uploadSchade10Omschrijving") String omschrijving) {

        String bijlageId = uploaden(uploadedInputStream, fileDetail, null, schadeNummerMaatschappij, null, null, omschrijving, null);

        return Response.status(200).entity(bijlageId).build();
    }

    @POST
    @Path("/uploadAangifte1File")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response uploadAangifte1File(@FormDataParam("uploadAangifte1File") InputStream uploadedInputStream, @FormDataParam("uploadAangifte1File") FormDataContentDisposition fileDetail, @FormDataParam("aangifteId") String aangifteId, @FormDataParam("uploadHypotheek1Omschrijving") String omschrijving) {

        String bijlageId = uploaden(uploadedInputStream, fileDetail, null, null, null, aangifteId, omschrijving, null);

        return Response.status(200).entity(bijlageId).build();
    }

    private String uploaden(InputStream uploadedInputStream, FormDataContentDisposition fileDetail, String polisNummer, String schadeNummerMaatschappij, String hypotheekId, String aangifteId, String omschrijving, String relatieId) {
        Bijlage bijlage =null;

        LOGGER.debug("opslaan bijlage bij polis {}, schadeNummerMaatschappij {} of bij hypotheekId {} of bij aangifteId {} of bij relatieId {}" ,polisNummer,schadeNummerMaatschappij,hypotheekId,aangifteId,relatieId);
        if (fileDetail != null && fileDetail.getFileName() != null && uploadedInputStream != null) {

            bijlage  = bijlageService.uploaden(uploadedInputStream,fileDetail);

            if (StringUtils.isNotBlank(polisNummer)) {
                Polis polis = polisService.zoekOpPolisNummer(polisNummer);

                LOGGER.debug("eigen database bijwerken");
                polisService.slaBijlageOp(polis.getId(), bijlage, omschrijving);
            } else if (schadeNummerMaatschappij != null) {
                Schade schade = schadeService.zoekOpSchadeNummerMaatschappij(schadeNummerMaatschappij);

                LOGGER.debug("Opslaan schade en uploaden bijlage " + fileDetail.getFileName());

                schadeService.slaBijlageOp(schade.getId(), bijlage, omschrijving);
            } else if (aangifteId != null) {
                Aangifte aangifte = aangifteService.lees(Long.valueOf(aangifteId));

                LOGGER.debug("Opslaan bijlage bij aangifte");

                aangifteService.slaAangifteOp(aangifte, bijlage, omschrijving);
            } else if (hypotheekId != null) {
                LOGGER.debug("Opslaan hypotheek en uploaden bijlage " + fileDetail.getFileName());

                hypotheekService.slaBijlageOp(Long.valueOf(hypotheekId), bijlage, omschrijving);
            }else if(relatieId!=null){
                LOGGER.debug("Opslaan bijlage bij Relatie id {}", relatieId);

                gebruikerService.opslaanBijlage(relatieId, bijlage);
            } else {
                bijlageService.opslaan(bijlage);

                return bijlage.getId().toString();
            }
        }
        String result = null;
        if(bijlage!=null&&bijlage.getId()!=null){
            result = bijlage.getId().toString();
        }
        return result;
    }

    private Bijlage uploaden(InputStream uploadedInputStream, FormDataContentDisposition fileDetail, String soortEntiteit, String id) {

        Bijlage bijlage = null;

        if (fileDetail != null && fileDetail.getFileName() != null && uploadedInputStream != null) {

            bijlage = bijlageService.uploaden(uploadedInputStream, fileDetail);
            LOGGER.debug(ReflectionToStringBuilder.toString(bijlage));

            switch (soortEntiteit) {
                case "Relatie":
                    LOGGER.debug("Opslaan bijlage bij Relatie id {}", id);

                    gebruikerService.opslaanBijlage(id, bijlage);

                    break;
                case "Polis":

                    polisService.opslaanBijlage(id, bijlage);

                    break;
                case "Schade":

                    schadeService.opslaanBijlage(id, bijlage);

                    break;
                case "Aangifte":

                    aangifteService.opslaanBijlage(id, bijlage);

                    break;
                case "Hypotheek":

                    hypotheekService.opslaanBijlage(id, bijlage);

                    break;
                default:
                    bijlageService.opslaan(bijlage);
            }
        }
        return bijlage;
    }
}
