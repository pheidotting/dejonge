package nl.dias.web.pagina;

import nl.dias.dias_web.hulp.Hulp;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public abstract class IndexPagina {
    @FindBy(id = "alertSucces")
    private WebElement alertSucces;
    @FindBy(id = "alertDanger")
    private WebElement alertDanger;
    @FindBy(id = "uitloggen")
    private WebElement uitloggen;

    public String leesFoutmelding() {
        return alertDanger.getText();
    }

    public String leesmelding() {
        return alertSucces.getText();
    }

    public void uitloggen() {
        Hulp.klikEnWacht(uitloggen);
    }
}
