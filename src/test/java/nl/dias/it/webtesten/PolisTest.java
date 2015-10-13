package nl.dias.it.webtesten;

import nl.dias.domein.json.Inloggen;
import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by patrickheidotting on 13-10-15.
 */
@Ignore
public class PolisTest extends AbstractITest {
    private final static Logger LOGGER = LoggerFactory.getLogger(PolisTest.class);

    @Test
    public void moi() {
        // ff kijken of ik eerst moet inlogge

        try {
            LOGGER.debug(uitvoerenGet("http://localhost:8080/dejonge/rest/medewerker/polis/lijst/24"));
        } catch (RuntimeException rte) {
            //Ik moet dus eerst inloggen
            Inloggen inloggen = new Inloggen("djfc.gerben", "gerben");

            aanroepenUrlPost("http://localhost:8080/dejonge/rest/authorisatie/authorisatie/inloggen", inloggen);

        }
        LOGGER.debug(uitvoerenGet("http://localhost:8080/dejonge/rest/medewerker/polis/lijst/24"));
    }
}
