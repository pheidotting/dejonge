package nl.dias.web.pagina;

import java.util.List;

import nl.dias.domein.json.JsonLijstRelaties;
import nl.dias.domein.json.JsonRelatie;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class LijstRelaties {
    @FindBy(name = "voornaam")
    private List<WebElement> voornaam;
    @FindBy(name = "tussenvoegsel")
    private List<WebElement> tussenvoegsel;
    @FindBy(name = "achternaam")
    private List<WebElement> achternaam;
    @FindBy(name = "geboortedatum")
    private List<WebElement> geboortedatum;
    @FindBy(name = "adres")
    private List<WebElement> adres;

    public boolean checkVelden(JsonLijstRelaties jsonLijstRelaties) {
        boolean ok = true;

        if (voornaam.size() != jsonLijstRelaties.getJsonRelaties().size()) {
            ok = false;
        }

        int index = 0;
        for (JsonRelatie jsonRelatie : jsonLijstRelaties.getJsonRelaties()) {
            if (!jsonRelatie.getVoornaam().equals(voornaam.get(index).getText())) {
                ok = false;
            }
            if (!jsonRelatie.getTussenvoegsel().equals(tussenvoegsel.get(index).getText())) {
                ok = false;
            }
            if (!jsonRelatie.getAchternaam().equals(achternaam.get(index).getText())) {
                ok = false;
            }
            // if
            // (!jsonRelatie.getGeboorteDatum().toString("dd-MM-yyyy").equals(geboortedatum.get(index).getText()))
            // {
            // ok = false;
            // }
            if (!jsonRelatie.getAdresOpgemaakt().equals(adres.get(index).getText())) {
                ok = false;
            }
            index++;
        }

        return ok;
    }
}
