package nl.dias.web.medewerker;

import nl.dias.domein.Bijlage;
import nl.dias.domein.Relatie;
import nl.dias.domein.json.JsonBijlage;
import nl.dias.service.*;
import nl.dias.utils.Utils;
import nl.dias.web.mapper.BijlageMapper;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
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
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RequestMapping("/bijlage")
@Controller
public class BijlageController {
    private final static Logger LOGGER = LoggerFactory.getLogger(BijlageController.class);

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

    @RequestMapping(method = RequestMethod.GET, value = "/verwijder")
    @ResponseBody
    public Response verwijder(@QueryParam("id") String id) {
        bijlageService.verwijderBijlage(Long.valueOf(id));
        return Response.ok().build();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/lijst")
    @ResponseBody
    public List<JsonBijlage> lijst(@QueryParam("relatieId") String relatieId) {
        Relatie relatie = (Relatie) gebruikerService.lees(Long.valueOf(relatieId));

        Set<Bijlage> bijlages = new HashSet<>();
        for (Bijlage bijlage : bijlageService.alleBijlagesBijRelatie(relatie)) {
            bijlages.add(bijlage);
        }

        return bijlageMapper.mapAllNaarJson(bijlages);
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
    public JsonBijlage uploadBijlage(@FormParam("bijlageFile") InputStream uploadedInputStream, @RequestParam("bijlageFile") MultipartFile fileDetail, @FormParam("id") String id, @FormParam("soortEntiteit") String soortEntiteit) {
        LOGGER.info("uploaden bestand voor {} met id {}", soortEntiteit, id);

        LOGGER.debug("{}", ReflectionToStringBuilder.toString(uploadedInputStream));
        LOGGER.debug("{}", ReflectionToStringBuilder.toString(fileDetail));

        Bijlage bijlage = uploaden(uploadedInputStream, fileDetail, soortEntiteit, id);

        LOGGER.debug("Geupload {}", ReflectionToStringBuilder.toString(bijlage, ToStringStyle.SHORT_PREFIX_STYLE));

        JsonBijlage jsonBijlage = bijlageMapper.mapNaarJson(bijlage);

        LOGGER.debug("Naar de front-end {}", ReflectionToStringBuilder.toString(jsonBijlage, ToStringStyle.SHORT_PREFIX_STYLE));

        return jsonBijlage;
    }

    private Bijlage uploaden(InputStream uploadedInputStream, MultipartFile fileDetail, String soortEntiteit, String id) {

        Bijlage bijlage = null;

        if (fileDetail != null && fileDetail.getName() != null && uploadedInputStream != null) {

            LOGGER.debug("Naar BijlageService");
            bijlage = bijlageService.uploaden(uploadedInputStream, fileDetail);
            LOGGER.debug(ReflectionToStringBuilder.toString(bijlage));

            if ("Relatie".equalsIgnoreCase(soortEntiteit)) {
                LOGGER.debug("Opslaan bijlage bij Relatie id {}", id);

                gebruikerService.opslaanBijlage(id, bijlage);
            } else if ("Polis".equalsIgnoreCase(soortEntiteit)) {
                LOGGER.debug("Entiteit is een Polis, dus opslaan bij Polis.");

                bijlage = polisService.opslaanBijlage(id, bijlage);

                LOGGER.debug("Bijlage opgelsagen {}", ReflectionToStringBuilder.toString(bijlage, ToStringStyle.SHORT_PREFIX_STYLE));
                //                    bijlage.setSoortBijlage(SoortBijlage.POLIS);
            } else if ("Schade".equalsIgnoreCase(soortEntiteit)) {
                schadeService.opslaanBijlage(id, bijlage);
            } else if ("Aangifte".equalsIgnoreCase(soortEntiteit)) {
                aangifteService.opslaanBijlage(id, bijlage);
            } else if ("Hypotheek".equalsIgnoreCase(soortEntiteit)) {
                hypotheekService.opslaanBijlage(id, bijlage);
            } else if ("Bedrijf".equalsIgnoreCase(soortEntiteit)) {
                bedrijfService.opslaanBijlage(id, bijlage);
            } else if ("JaarCijfers".equalsIgnoreCase(soortEntiteit)) {
                jaarCijfersService.opslaanBijlage(id, bijlage);
            } else if ("RisicoAnalyse".equalsIgnoreCase(soortEntiteit)) {
                    risicoAnalyseService.opslaanBijlage(id, bijlage);
            }
            LOGGER.debug("Nogmaals opslaan Bijlage");
            LOGGER.debug(ReflectionToStringBuilder.toString(bijlage, ToStringStyle.SHORT_PREFIX_STYLE));
            bijlageService.opslaan(bijlage);
        }
        return bijlage;
    }
}
