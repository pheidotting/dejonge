package nl.dias.web.medewerker;

import nl.dias.domein.Bijlage;
import nl.dias.mapper.BijlageNaarJsonBijlageMapper;
import nl.dias.service.BijlageService;
import nl.dias.web.SoortEntiteit;
import nl.lakedigital.djfc.client.oga.BijlageClient;
import nl.lakedigital.djfc.client.oga.GroepBijlagesClient;
import nl.lakedigital.djfc.commons.json.JsonBijlage;
import nl.lakedigital.djfc.commons.json.JsonGroepBijlages;
import nl.lakedigital.djfc.commons.json.WijzigenOmschrijvingBijlage;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.FormParam;
import javax.ws.rs.Produces;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

@RequestMapping("/bijlage")
@Controller
public class BijlageController extends AbstractController {
    @Inject
    private BijlageClient bijlageClient;
    @Inject
    private GroepBijlagesClient groepBijlagesClient;
    private final static Logger LOGGER = LoggerFactory.getLogger(BijlageController.class);
    @Inject
    private BijlageService bijlageService;
    @Inject
    private BijlageNaarJsonBijlageMapper bijlageNaarJsonBijlageMapper;

    @RequestMapping(method = RequestMethod.POST, value = "/wijzigOmschrijvingBijlage")
    @ResponseBody
    public void wijzigOmschrijvingBijlage(@RequestBody WijzigenOmschrijvingBijlage wijzigenOmschrijvingBijlage, HttpServletRequest httpServletRequest) {
        bijlageClient.wijzigOmschrijvingBijlage(wijzigenOmschrijvingBijlage, getIngelogdeGebruiker(httpServletRequest), getTrackAndTraceId(httpServletRequest));
    }

    @RequestMapping(method = RequestMethod.POST, value = "/opslaan")
    @ResponseBody
    public void opslaan(@RequestBody List<JsonBijlage> jsonEntiteiten, HttpServletRequest httpServletRequest) {
        bijlageClient.opslaan(jsonEntiteiten, getIngelogdeGebruiker(httpServletRequest), getTrackAndTraceId(httpServletRequest));
    }

    @RequestMapping(method = RequestMethod.POST, value = "/opslaanBijlage")
    @ResponseBody
    public void opslaanBijlage(JsonBijlage jsonBijlage, HttpServletRequest httpServletRequest) {
        bijlageClient.opslaan(jsonBijlage, getIngelogdeGebruiker(httpServletRequest), getTrackAndTraceId(httpServletRequest));
    }

    @RequestMapping(method = RequestMethod.GET, value = "/genereerBestandsnaam")
    @ResponseBody
    public String genereerBestandsnaam() {
        return bijlageClient.genereerBestandsnaam();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/getUploadPad")
    @ResponseBody
    public String getUploadPad() {
        return bijlageClient.getUploadPad();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/alles/{soortentiteit}/{parentid}")
    @ResponseBody
    public List<JsonBijlage> alles(@PathVariable("soortentiteit") String soortentiteit, @PathVariable("parentid") Long parentid) {
        List<JsonBijlage> jsonEntiteiten = bijlageClient.lijst(soortentiteit, parentid);

        return jsonEntiteiten;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/verwijderen/{soortentiteit}/{parentid}")
    @ResponseBody
    public void verwijderen(@PathVariable("soortentiteit") String soortentiteit, @PathVariable("parentid") Long parentid, HttpServletRequest httpServletRequest) {
        bijlageClient.verwijder(soortentiteit, parentid, getIngelogdeGebruiker(httpServletRequest), "tantid");
    }

    @RequestMapping(method = RequestMethod.GET, value = "/zoeken/{zoekTerm}")
    @ResponseBody
    public List<JsonBijlage> zoeken(@PathVariable("zoekTerm") String zoekTerm) {
        List<JsonBijlage> result = bijlageClient.zoeken(zoekTerm);

        return result;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/download")
    @ResponseBody
    @Produces("application/pdf")
    public ResponseEntity<byte[]> getFile(@RequestParam("id") Long id) throws IOException {

        JsonBijlage bijlage = bijlageClient.lees(id);

        File file = new File(bijlageClient.getUploadPad() + "/" + bijlage.getS3Identificatie());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType("application/pdf"));
        headers.add("content-disposition", "inline;filename=" + bijlage.getBestandsNaam());
        headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
        ResponseEntity<byte[]> response = new ResponseEntity<byte[]>(Files.readAllBytes(Paths.get(file.getAbsolutePath())), headers, HttpStatus.OK);
        return response;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/uploadBijlage")
    @ResponseBody
    public JsonBijlage uploadBijlage(@RequestParam("bijlageFile") MultipartFile fileDetail, @FormParam("id") String id, @FormParam("soortEntiteit") String soortEntiteit, HttpServletRequest httpServletRequest) {
        LOGGER.info("uploaden bestand voor {} met id {}", soortEntiteit, id);

        LOGGER.debug("{}", ReflectionToStringBuilder.toString(fileDetail));

        JsonBijlage bijlage = uploaden(fileDetail, soortEntiteit, Long.valueOf(id), httpServletRequest);

        LOGGER.debug("Geupload {}", ReflectionToStringBuilder.toString(bijlage, ToStringStyle.SHORT_PREFIX_STYLE));

        //        JsonBijlage jsonBijlage = bijlageMapper.mapNaarJson(bijlage);
        //
        LOGGER.debug("Naar de front-end {}", ReflectionToStringBuilder.toString(bijlage, ToStringStyle.SHORT_PREFIX_STYLE));

        return bijlage;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/alleGroepen/{soortentiteit}/{parentid}")
    @ResponseBody
    public List<JsonGroepBijlages> alleGroepen(@PathVariable("soortentiteit") String soortentiteit, @PathVariable("parentid") Long parentid) {
        LOGGER.debug("alleGroepen voor soortentiteit {} en {}", soortentiteit, parentid);

        List<JsonGroepBijlages> result = groepBijlagesClient.lijst(soortentiteit, parentid);
        LOGGER.debug(ReflectionToStringBuilder.toString(result, ToStringStyle.SHORT_PREFIX_STYLE));
        LOGGER.debug("{}", result);

        for (Object jsonGroepBijlages : result) {
            LOGGER.debug(ReflectionToStringBuilder.toString(jsonGroepBijlages, ToStringStyle.SHORT_PREFIX_STYLE));
        }

        return result;
    }

    private JsonBijlage uploaden(MultipartFile fileDetail, String soortEntiteit, Long entiteitId, HttpServletRequest httpServletRequest) {

        Bijlage bijlage = null;
        JsonBijlage jsonBijlage = null;

        if (fileDetail != null && fileDetail.getName() != null) {

            LOGGER.debug("Naar BijlageService");
            bijlage = bijlageService.uploaden(fileDetail, getUploadPad());
            bijlage.setSoortBijlage(SoortEntiteit.valueOf(soortEntiteit.toUpperCase()));
            bijlage.setEntiteitId(entiteitId);
            LOGGER.debug(ReflectionToStringBuilder.toString(bijlage, ToStringStyle.SHORT_PREFIX_STYLE));
            jsonBijlage = bijlageNaarJsonBijlageMapper.map(bijlage, null, null);
            String id = bijlageClient.opslaan(jsonBijlage, getIngelogdeGebruiker(httpServletRequest), getTrackAndTraceId(httpServletRequest));
            jsonBijlage.setId(Long.valueOf(id));
        }
        return jsonBijlage;
    }
}
