package nl.dias.dias_web.selenium.tests;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import nl.dias.dias_web.GebruikerController;
import nl.dias.dias_web.hulp.Hulp;
import nl.dias.dias_web.selenium.AbstractSeleniumTest;
import nl.dias.domein.json.JsonRekeningNummer;
import nl.dias.domein.json.JsonRelatie;
import nl.dias.domein.json.JsonTelefoonnummer;
import nl.dias.web.pagina.BeherenRelatie;

import org.openqa.selenium.support.PageFactory;

public class BeherenRelatieTest extends AbstractSeleniumTest {

    @Override
    public void voerTestUit() {
        GebruikerController.jsonRelatie = new JsonRelatie();

        Hulp.naarAdres(driver, "http://localhost:9999/dias-web/index.html#beherenRelatie/3");

        BeherenRelatie pagina = PageFactory.initElements(driver, BeherenRelatie.class);

        JsonRelatie jsonRelatie = maakJsonRelatie();
        System.out.println(jsonRelatie);
        pagina.vulVeldenEnDrukOpOpslaan(jsonRelatie.getVoornaam(), jsonRelatie.getAchternaam(), jsonRelatie.getTussenvoegsel(), jsonRelatie.getStraat(), null, jsonRelatie.getToevoeging(),
                jsonRelatie.getPostcode(), jsonRelatie.getPlaats(), jsonRelatie.getBsn(), jsonRelatie.getIdentificatie(), jsonRelatie.getGeboorteDatum(), jsonRelatie.getOverlijdensdatum());

        assertEquals(jsonRelatie, GebruikerController.jsonRelatie);

    }

    private JsonRelatie maakJsonRelatie() {
        JsonRelatie jsonRelatie = new JsonRelatie();

        jsonRelatie.setGeslacht("Man");
        jsonRelatie.setBurgerlijkeStaat("Ongehuwd");

        jsonRelatie.setStraat("Herderstraat");
        // jsonRelatie.setHuisnummer(65L);
        jsonRelatie.setBedrijven(new ArrayList<Long>());
        jsonRelatie.setOnderlingeRelaties(new ArrayList<Long>());
        jsonRelatie.setRekeningnummers(new ArrayList<JsonRekeningNummer>());
        jsonRelatie.setTelefoonnummers(new ArrayList<JsonTelefoonnummer>());

        return jsonRelatie;
    }
}
