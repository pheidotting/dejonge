package nl.dias.web.pagina;

import java.util.List;

import nl.dias.dias_web.hulp.Hulp;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class BeherenRelatie extends IndexPagina {
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

    @FindBy(id = "opslaanRelatie")
    private WebElement opslaanrelatie;
    @FindBy(id = "verwijderen")
    private WebElement verwijderen;

    // Rekeningnummer
    @FindBy(id = "voegRekeningToe")
    private WebElement voegRekeningToe;
    @FindBy(name = "rekeningid")
    private List<WebElement> rekeningid;
    @FindBy(name = "rekeningnummer")
    private List<WebElement> rekeningnummer;
    @FindBy(name = "bic")
    private List<WebElement> bic;
    @FindBy(name = "verwijderRekening")
    private List<WebElement> verwijderRekening;

    // Telefoonummer
    @FindBy(id = "voegTelefoonNummerToe")
    private WebElement voegTelefoonNummerToe;
    @FindBy(name = "telefoonid")
    private List<WebElement> telefoonid;
    @FindBy(name = "telnummer")
    private List<WebElement> telnummer;
    @FindBy(name = "soorttelnummer")
    private List<WebElement> soorttelnummer;
    @FindBy(name = "verwijderTelefoonNummer")
    private List<WebElement> verwijderTelefoonNummer;

    public void vulVeldenEnDrukOpOpslaan(String voornaam, String achternaam, String tussenvoegsel, String straat, String huisnummer, String toevoeging, String postcode, String plaats, String bsn,
            String emailadres, String geboorteDatum, String overlijdensdatum, String geslacht, String burgerlijkeStaat, List<BeherenRelatieRekeningnummer> rekeningnummers,
            List<BeherenRelatieTelefoonnummer> telefoonnummers) {
        vulVelden(voornaam, achternaam, tussenvoegsel, straat, huisnummer, toevoeging, postcode, plaats, bsn, emailadres, geboorteDatum, overlijdensdatum, geslacht, burgerlijkeStaat, rekeningnummers,
                telefoonnummers);
        drukOpOpslaan();
    }

    public void vulVelden(String voornaam, String achternaam, String tussenvoegsel, String straat, String huisnummer, String toevoeging, String postcode, String plaats, String bsn, String emailadres,
            String geboorteDatum, String overlijdensdatum, String geslacht, String burgerlijkeStaat, List<BeherenRelatieRekeningnummer> rekeningnummers,
            List<BeherenRelatieTelefoonnummer> telefoonnummers) {
        Hulp.wachtFf();
        Hulp.vulVeld(this.voornaam, voornaam);
        Hulp.vulVeld(this.achternaam, achternaam);
        // Hulp.vulVeld(this.tussenvoegsel, tussenvoegsel);
        // Hulp.vulVeld(this.straat, straat);
        Hulp.vulVeld(this.huisnummer, huisnummer);
        // Hulp.vulVeld(this.toevoeging, toevoeging);
        // Hulp.vulVeld(this.postcode, postcode);
        // Hulp.vulVeld(this.plaats, plaats);
        // Hulp.vulVeld(this.bsn, bsn);
        // Hulp.vulVeld(this.emailadres, emailadres);
        // Hulp.vulVeld(this.geboorteDatum, geboorteDatum);
        // Hulp.vulVeld(this.overlijdensdatum, overlijdensdatum);
        // if (geslacht != null) {
        // Hulp.selecteerUitSelectieBox(this.geslacht, geslacht);
        // }
        // if (burgerlijkeStaat != null) {
        // Hulp.selecteerUitSelectieBox(this.burgerlijkeStaat,
        // burgerlijkeStaat);
        // }
        // if (rekeningnummers != null) {
        // for (BeherenRelatieRekeningnummer rekeningnummer : rekeningnummers) {
        // Hulp.klikEnWacht(this.voegRekeningToe);
        // Hulp.vulVeld(this.rekeningnummer.get(this.rekeningnummer.size() - 1),
        // rekeningnummer.getRekeninnummer());
        // Hulp.vulVeld(this.bic.get(this.bic.size() - 1),
        // rekeningnummer.getBic());
        // }
        // }
        // if (telefoonnummers != null) {
        // for (BeherenRelatieTelefoonnummer telefoonnummer : telefoonnummers) {
        // Hulp.klikEnWacht(this.voegTelefoonNummerToe);
        // Hulp.vulVeld(this.telnummer.get(this.telnummer.size() - 1),
        // telefoonnummer.getTelefoonnummer());
        // Hulp.selecteerUitSelectieBox(this.soorttelnummer.get(this.soorttelnummer.size()
        // - 1), telefoonnummer.getSoortTelefoonnummer());
        // }
        // }
    }

    public void drukOpOpslaan() {
        Hulp.wachtFf();
        Hulp.klikEnWacht(opslaanrelatie);
        Hulp.wachtFf();
    }

    public void drukOpVerwijderen() {
        Hulp.klikEnWacht(verwijderen);
    }

    public void verwijderRekeningnummer(int index) {
        Hulp.wachtFf();
        Hulp.klikEnWacht(verwijderRekening.get(index - 1));
        Hulp.wachtFf();
    }

    public void verwijderTelefoonnummer(int index) {
        Hulp.wachtFf();
        Hulp.klikEnWacht(verwijderTelefoonNummer.get(index - 1));
        Hulp.wachtFf();
    }

}
