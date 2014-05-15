package nl.dias.web.pagina;

import nl.dias.dias_web.hulp.Hulp;

import org.joda.time.LocalDate;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class BeherenRelatie {
    @FindBy(id = "voornaam")
    private WebElement voornaam;
    @FindBy(id = "achternaam")
    private WebElement achternaam;
    @FindBy(id = "tussenvoegsel")
    private WebElement tussenvoegsel;
    @FindBy(id = "straat")
    private WebElement straat;
    @FindBy(id = "huisnummer")
    private WebElement huisnummer;
    @FindBy(id = "toevoeging")
    private WebElement toevoeging;
    @FindBy(id = "postcode")
    private WebElement postcode;
    @FindBy(id = "plaats")
    private WebElement plaats;
    @FindBy(id = "bsn")
    private WebElement bsn;
    @FindBy(id = "emailadres")
    private WebElement emailadres;
    @FindBy(id = "geboorteDatum")
    private WebElement geboorteDatum;
    @FindBy(id = "overlijdensdatum")
    private WebElement overlijdensdatum;
    @FindBy(id = "geslacht")
    private WebElement geslacht;
    @FindBy(id = "burgerlijkeStaat")
    private WebElement burgerlijkeStaat;

    @FindBy(id = "opslaanrelatie")
    private WebElement opslaan;

    public void vulVeldenEnDrukOpOpslaan(String voornaam, String achternaam, String tussenvoegsel, String straat, String huisnummer, String toevoeging, String postcode, String plaats, String bsn,
            String emailadres, LocalDate geboorteDatum, LocalDate overlijdensdatum, String geslacht, String burgerlijkeStaat) {
        vulVelden(voornaam, achternaam, tussenvoegsel, straat, huisnummer, toevoeging, postcode, plaats, bsn, emailadres, geboorteDatum, overlijdensdatum, geslacht, burgerlijkeStaat);
        drukOpOpslaan();
    }

    public void vulVelden(String voornaam, String achternaam, String tussenvoegsel, String straat, String huisnummer, String toevoeging, String postcode, String plaats, String bsn, String emailadres,
            LocalDate geboorteDatum, LocalDate overlijdensdatum, String geslacht, String burgerlijkeStaat) {
        Hulp.vulVeld(this.voornaam, voornaam);
        Hulp.vulVeld(this.achternaam, achternaam);
        Hulp.vulVeld(this.tussenvoegsel, tussenvoegsel);
        Hulp.vulVeld(this.straat, straat);
        Hulp.vulVeld(this.huisnummer, huisnummer);
        Hulp.vulVeld(this.toevoeging, toevoeging);
        Hulp.vulVeld(this.postcode, postcode);
        Hulp.vulVeld(this.plaats, plaats);
        Hulp.vulVeld(this.bsn, bsn);
        Hulp.vulVeld(this.emailadres, emailadres);
        if (geboorteDatum != null) {
            Hulp.vulVeld(this.geboorteDatum, geboorteDatum.toString("dd-MM-yyyy"));
        }
        if (overlijdensdatum != null) {
            Hulp.vulVeld(this.overlijdensdatum, overlijdensdatum.toString("dd-MM-yyyy"));
        }
        if (geslacht != null) {
            Hulp.selecteerUitSelectieBox(this.geslacht, geslacht);
        }
        if (burgerlijkeStaat != null) {
            Hulp.selecteerUitSelectieBox(this.burgerlijkeStaat, burgerlijkeStaat);
        }
    }

    public void drukOpOpslaan() {
        Hulp.klikEnWacht(opslaan);
    }

}
