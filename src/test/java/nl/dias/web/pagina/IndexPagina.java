package nl.dias.web.pagina;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public abstract class IndexPagina {
    @FindBy(id = "alertSucces")
    private WebElement alertSucces;
    @FindBy(id = "alertDanger")
    private WebElement alertDanger;

    public String leesFoutmelding() {
        return alertDanger.getText();
    }

    public String leesmelding() {
        return alertSucces.getText();
    }

}
