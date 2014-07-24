package nl.dias.web.pagina;

import nl.dias.dias_web.hulp.Hulp;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class InlogScherm extends IndexPagina {
    @FindBy(id = "identificatie")
    private WebElement identificatie;
    @FindBy(id = "wachtwoord")
    private WebElement wachtwoord;
    @FindBy(id = "inlogButton")
    private WebElement inlogButton;

    public void inloggen(String identificatie, String wachtwoord) {
        Hulp.vulVeld(this.identificatie, identificatie);
        Hulp.vulVeld(this.wachtwoord, wachtwoord);
        Hulp.klikEnWacht(inlogButton);
    }

}
