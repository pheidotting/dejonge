package nl.dias.it;

import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class AbstractITest {
    private final static Logger LOGGER = LoggerFactory.getLogger(AbstractITest.class);

    protected final String GEBRUIKER_OPSLAAN = "http://localhost:7075/dejonge/rest/medewerker/gebruiker/opslaan";
    protected final String RELATIE_LEZEN = "http://localhost:7075/dejonge/rest/medewerker/relatie/lees";

    protected String doePost(Object entiteit, String url, String trackAndTraceId) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("trackAndTraceId", trackAndTraceId);

        HttpEntity<String> entity = new HttpEntity<>(new Gson().toJson(entiteit), headers);

        RestTemplate restTemplate = new RestTemplate();

        LOGGER.debug("Aanroepen {}", url);
        System.out.println("Aanroepen " + url);

        ResponseEntity<String> response = restTemplate.postForEntity(url, entity, String.class);

        return response.getBody();
    }

    protected String doeGet(String url) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        RestTemplate restTemplate = new RestTemplate();

        LOGGER.debug("Aanroepen {}", url);
        System.out.println("Aanroepen " + url);

        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);

        return response.getBody();
    }

    protected Stub stubIdentificatieZoekOpSoortEnId(String response) {
        return new Stub("/rest/identificatie/zoeken/RELATIE/([0-9]*)", response, false);
    }

    protected Stub stubIdentificatieZoekenOpCode(String id, String response) {
        return new Stub("/rest/identificatie/zoekenOpCode/" + id, response, true);
    }

    protected Stub stubZoekAlleAdressen(String response) {
        return new Stub("/rest/adres/alles%2FRELATIE%2F([0-9]*)", response, false);
    }

    protected Stub stubZoekAlleBijlages(String response) {
        return new Stub("/rest/bijlage/alles/RELATIE/([0-9]*)", response, false);
    }

    protected Stub stubGroepenBijlage(String response) {
        return new Stub("/rest/bijlage/alleGroepen/RELATIE/([0-9]*)", response, false);
    }

    protected Stub stubRekeningnummer(String response) {
        return new Stub("/rest/rekeningnummer/alles%2FRELATIE%2F([0-9]*)", response, false);
    }

    protected Stub stubTelefoonnummer(String response) {
        return new Stub("/rest/telefoonnummer/alles%2FRELATIE%2F([0-9]*)", response, false);
    }

    protected Stub stubOpmerking(String response) {
        return new Stub("/rest/opmerking/alles%2FRELATIE%2F([0-9]*)", response, false);
    }

    protected Stub stubPolis(String response) {
        return new Stub("/rest/polis/lijst/([0-9]*)", response, false);
    }
}
