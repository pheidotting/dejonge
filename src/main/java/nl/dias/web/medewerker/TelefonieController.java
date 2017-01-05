package nl.dias.web.medewerker;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.Produces;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

@RequestMapping("/telefonie")
@Controller
@Configuration
@PropertySources({@PropertySource("classpath:application.properties"), @PropertySource(value = "file:app.properties", ignoreResourceNotFound = true)})
public class TelefonieController extends AbstractController {
    private final static Logger LOGGER = LoggerFactory.getLogger(TelefonieController.class);

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertyConfigIn() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    @Value("${voicemailspad}")
    private String voicemailspad;
    @Value("${recordingspad}")
    private String recordingspad;

    @RequestMapping("/recordings")
    @ResponseBody
    public Map<String, List<String>> getRecordingsAndVoicemails(@RequestParam List<String> telefoonnummer) {
        Map<String, List<String>> ret = new HashMap<>();

        File f = new File(recordingspad);
        List<String> recordings = new ArrayList<>(Arrays.asList(f.list()));
        List<String> gefilterdOpTelefoonnummer = recordings.stream().filter(s -> telefoonnummer.contains(getTelefoonnummer(s))).collect(Collectors.toList());

        ret.put("recordings", gefilterdOpTelefoonnummer);

        return ret;
    }

    private String getTelefoonnummer(String bestandsnaam) {
        String[] parts = bestandsnaam.split("-");

        if (parts[0].equals("out")) {
            return parts[1];
        } else {
            return parts[2];
        }
    }

    @RequestMapping(method = RequestMethod.GET, value = "/download/{bestandsnaam}")
    @ResponseBody
    @Produces("application/wav")
    public ResponseEntity<byte[]> getFile(@PathVariable("bestandsnaam") String bestandsnaam) throws IOException {
        File file = new File(recordingspad + File.separator + bestandsnaam);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType("application/wav"));
        headers.add("content-disposition", "inline;filename=" + bestandsnaam);
        headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
        ResponseEntity<byte[]> response = new ResponseEntity<byte[]>(Files.readAllBytes(Paths.get(file.getAbsolutePath())), headers, HttpStatus.OK);
        return response;
    }

}
