package nl.dias.web.pagina;

import java.util.List;

import nl.dias.dias_web.hulp.Hulp;
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
    @FindBy(id = "toevoegenNieuweRelatie")
    private WebElement toevoegenNieuweRelatie;

    public boolean checkVelden(JsonLijstRelaties jsonLijstRelaties) {
        boolean ok = true;

        Hulp.wachtFf();
        if (voornaam.size() != jsonLijstRelaties.getJsonRelaties().size()) {
            ok = false;
        }

        int index = 0;
        for (JsonRelatie jsonRelatie : jsonLijstRelaties.getJsonRelaties()) {
            Hulp.wachtFf();
            if (!jsonRelatie.getVoornaam().equals(voornaam.get(index).getText())) {
                ok = false;
            }
            Hulp.wachtFf();
            if (!jsonRelatie.getTussenvoegsel().equals(tussenvoegsel.get(index).getText())) {
                ok = false;
            }
            Hulp.wachtFf();
            if (!jsonRelatie.getAchternaam().equals(achternaam.get(index).getText())) {
                ok = false;
            }
            Hulp.wachtFf();
            if (!jsonRelatie.getGeboorteDatum().equals(geboortedatum.get(index).getText())) {
                ok = false;
            }
            Hulp.wachtFf();
            if (!jsonRelatie.getAdresOpgemaakt().equals(adres.get(index).getText())) {
                ok = false;
            }
            Hulp.wachtFf();
            index++;
        }

        return ok;
    }

    public void toevoegenNieuweRelatie() {
        Hulp.klikEnWacht(toevoegenNieuweRelatie);
    }
}
