package nl.dias.it.webtesten;

import nl.dias.domein.json.Inloggen;
import nl.dias.domein.json.JsonBijlage;
import nl.dias.domein.json.JsonPolis;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;

import static org.junit.Assert.assertEquals;

/**
 * Created by patrickheidotting on 13-10-15.
 */
@Ignore
public class PolisTest extends AbstractITest {
    private final static Logger LOGGER = LoggerFactory.getLogger(PolisTest.class);

    private String sessieCode = "894cdec4-6731-4dee-b408-2e7e5ff26e46";

    @Test
    public void moi() {
        // ff kijken of ik eerst moet inlogge

        try {
            LOGGER.debug(uitvoerenGet("http://localhost:8080/dejonge/rest/medewerker/polis/lees?id=5", sessieCode));
        } catch (RuntimeException rte) {
            //Ik moet dus eerst inloggen
            Inloggen inloggen = new Inloggen("djfc.gerben", "gerben");

            aanroepenUrlPost("http://localhost:8080/dejonge/rest/authorisatie/authorisatie/inloggen", inloggen, sessieCode);

        }
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
        }

        JsonPolis jsonPolis = gson.fromJson(uitvoerenGet("http://localhost:8080/dejonge/rest/medewerker/polis/lees?id=5", sessieCode), JsonPolis.class);

        String kenmerk = LocalDateTime.now().toString("dd-MM-yyyy HH:mm");
        jsonPolis.setKenmerk(kenmerk);
        jsonPolis.setMaatschappij("Achmea");
        LocalDate ingangsDatum = null;
        if (jsonPolis.getIngangsDatum() != null) {
            ingangsDatum = new LocalDate(jsonPolis.getIngangsDatum());
        } else {
            ingangsDatum = LocalDate.now();
        }
        ingangsDatum = ingangsDatum.plusDays(1);
        jsonPolis.setIngangsDatum(ingangsDatum.toString("dd-MM-yyyy"));

        aanroepenUrlPost("http://localhost:8080/dejonge/rest/medewerker/polis/opslaan", jsonPolis, sessieCode);

        JsonPolis jsonPolisOpgeslagen = gson.fromJson(uitvoerenGet("http://localhost:8080/dejonge/rest/medewerker/polis/lees?id=5", sessieCode), JsonPolis.class);
        assertEquals(kenmerk, jsonPolisOpgeslagen.getKenmerk());
        assertEquals(ingangsDatum.toString("yyyy-MM-dd"), jsonPolisOpgeslagen.getIngangsDatum());

        JsonBijlage jsonBijlage = new JsonBijlage();
        jsonBijlage.setBestandsNaam(UUID.randomUUID().toString());
        jsonBijlage.setSoortBijlage("Polis");
        jsonPolis.getBijlages().add(jsonBijlage);
        kenmerk = LocalDateTime.now().toString("dd-MM-yyyy HH:mm");
        jsonPolis.setKenmerk(kenmerk);

        aanroepenUrlPost("http://localhost:8080/dejonge/rest/medewerker/polis/opslaan", jsonPolis, sessieCode);

        jsonPolisOpgeslagen = gson.fromJson(uitvoerenGet("http://localhost:8080/dejonge/rest/medewerker/polis/lees?id=5", sessieCode), JsonPolis.class);
        assertEquals(kenmerk, jsonPolisOpgeslagen.getKenmerk());
        assertEquals(ingangsDatum.toString("yyyy-MM-dd"), jsonPolisOpgeslagen.getIngangsDatum());
    }
}
