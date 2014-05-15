package nl.dias.dias_web.selenium.tests;

import static org.junit.Assert.assertTrue;
import nl.dias.dias_web.GebruikerController;
import nl.dias.dias_web.hulp.Hulp;
import nl.dias.dias_web.selenium.AbstractSeleniumTest;
import nl.dias.domein.json.JsonLijstRelaties;
import nl.dias.domein.json.JsonRelatie;
import nl.dias.web.pagina.LijstRelaties;

import org.joda.time.LocalDate;
import org.openqa.selenium.support.PageFactory;

public class LijstRelatiesTest extends AbstractSeleniumTest {

    @Override
    public void voerTestUit() {
        GebruikerController.jsonLijstRelaties = new JsonLijstRelaties();
        JsonRelatie jsonRelatie = new JsonRelatie();
        jsonRelatie.setVoornaam("voornaam1");
        jsonRelatie.setTussenvoegsel("tussenvoegsel1");
        jsonRelatie.setAchternaam("achternaam1");
        jsonRelatie.setGeboorteDatum(new LocalDate(2001, 1, 1));
        jsonRelatie.setAdresOpgemaakt("adresOpgemaakt1");
        GebruikerController.jsonLijstRelaties.getJsonRelaties().add(jsonRelatie);
        JsonRelatie jsonRelatie1 = new JsonRelatie();
        jsonRelatie1.setVoornaam("voornaam2");
        jsonRelatie1.setTussenvoegsel("tussenvoegsel2");
        jsonRelatie1.setAchternaam("achternaam2");
        jsonRelatie1.setGeboorteDatum(new LocalDate(2002, 2, 2));
        jsonRelatie1.setAdresOpgemaakt("adresOpgemaakt2");
        GebruikerController.jsonLijstRelaties.getJsonRelaties().add(jsonRelatie1);
        System.out.println(GebruikerController.jsonLijstRelaties);

        Hulp.naarAdres(driver, "http://localhost:9999/dias-web/index.html#lijstRelaties");

        LijstRelaties pagina = PageFactory.initElements(driver, LijstRelaties.class);

        assertTrue(pagina.checkVelden(GebruikerController.jsonLijstRelaties));
    }

}
