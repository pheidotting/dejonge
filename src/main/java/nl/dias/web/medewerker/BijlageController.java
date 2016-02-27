package nl.dias.web.medewerker;

import nl.dias.domein.Bijlage;
import nl.dias.service.*;
import nl.dias.utils.Utils;
import nl.dias.web.SoortEntiteit;
import nl.dias.web.mapper.BijlageMapper;
import nl.lakedigital.djfc.commons.json.JsonBijlage;
import nl.lakedigital.djfc.commons.json.WijzigenOmschrijvingBijlage;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.FormParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

@RequestMapping("/bijlage")
@Controller
public class BijlageController {
    private static final Logger LOGGER = LoggerFactory.getLogger(BijlageController.class);

    @Inject
    private PolisService polisService;
    @Inject
    private BijlageService bijlageService;
    @Inject
    private SchadeService schadeService;
    @Inject
    private GebruikerService gebruikerService;
    @Inject
    private BijlageMapper bijlageMapper;
    @Inject
    private HypotheekService hypotheekService;
    @Inject
    private AangifteService aangifteService;
    @Inject
    private BedrijfService bedrijfService;
    @Inject
    private JaarCijfersService jaarCijfersService;
    @Inject
    private RisicoAnalyseService risicoAnalyseService;
    @Autowired
    private HttpServletRequest httpServletRequest;
    @Autowired
    private HttpServletResponse httpServletResponse;

    @RequestMapping(method = RequestMethod.POST, value = "/wijzigOmschrijvingBijlage")
    @ResponseBody
    public void wijzigOmschrijvingBijlage(@RequestBody WijzigenOmschrijvingBijlage wijzigenOmschrijvingBijlage) {
        LOGGER.info("WijzigenOmschrijvingBijlage {}", ReflectionToStringBuilder.toString(wijzigenOmschrijvingBijlage, ToStringStyle.SHORT_PREFIX_STYLE));

        bijlageService.wijzigOmschrijvingBijlage(wijzigenOmschrijvingBijlage.getBijlageId(), wijzigenOmschrijvingBijlage.getNieuweOmschrijving());
    }

    @RequestMapping(method = RequestMethod.GET, value = "/verwijder")
    @ResponseBody
    public Response verwijder(@QueryParam("id") String id) {
        bijlageService.verwijderBijlage(Long.valueOf(id));
        return Response.ok().build();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/lijstBijlages")
    @ResponseBody
    public List<JsonBijlage> lijstBijlages(@QueryParam("soortentiteit") String soortentiteit, @QueryParam("parentid") Long parentid) {
        SoortEntiteit soortEntiteit = SoortEntiteit.valueOf(soortentiteit);

        return bijlageMapper.mapAllNaarJson(bijlageService.alleBijlagesBijEntiteit(soortEntiteit, parentid));
    }

    @RequestMapping(method = RequestMethod.GET, value = "/download")
    @ResponseBody
    @Produces("application/pdf")
    public ResponseEntity<byte[]> getFile(@QueryParam("id") String id) throws IOException {
        LOGGER.info("Ophalen bijlage met id " + id);

        Bijlage bijlage = polisService.leesBijlage(Long.parseLong(id));

        File file = new File(Utils.getUploadPad() + "/" + bijlage.getS3Identificatie());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType("application/pdf"));
        headers.add("content-disposition", "inline;filename=" + bijlage.getBestandsNaam());
        headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
        ResponseEntity<byte[]> response = new ResponseEntity<byte[]>(Files.readAllBytes(Paths.get(file.getAbsolutePath())), headers, HttpStatus.OK);
        return response;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/uploadBijlage")
    @ResponseBody
    public JsonBijlage uploadBijlage(@RequestParam("bijlageFile") MultipartFile fileDetail, @FormParam("id") String id, @FormParam("soortEntiteit") String soortEntiteit) {
        LOGGER.info("uploaden bestand voor {} met id {}", soortEntiteit, id);

        LOGGER.debug("{}", ReflectionToStringBuilder.toString(fileDetail));

        Bijlage bijlage = uploaden(fileDetail, soortEntiteit, Long.valueOf(id));

        LOGGER.debug("Geupload {}", ReflectionToStringBuilder.toString(bijlage, ToStringStyle.SHORT_PREFIX_STYLE));

        JsonBijlage jsonBijlage = bijlageMapper.mapNaarJson(bijlage);

        LOGGER.debug("Naar de front-end {}", ReflectionToStringBuilder.toString(jsonBijlage, ToStringStyle.SHORT_PREFIX_STYLE));

        return jsonBijlage;
    }

    private Bijlage uploaden(MultipartFile fileDetail, String soortEntiteit, Long entiteitId) {

        Bijlage bijlage = null;

        if (fileDetail != null && fileDetail.getName() != null) {

            LOGGER.debug("Naar BijlageService");
            bijlage = bijlageService.uploaden(fileDetail);
            bijlage.setSoortBijlage(SoortEntiteit.valueOf(soortEntiteit.toUpperCase()));
            bijlage.setEntiteitId(entiteitId);
            LOGGER.debug(ReflectionToStringBuilder.toString(bijlage, ToStringStyle.SHORT_PREFIX_STYLE));
            bijlageService.opslaan(bijlage);
        }
        return bijlage;
    }
}
