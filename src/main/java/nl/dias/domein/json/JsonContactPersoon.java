package nl.dias.domein.json;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.util.ArrayList;
import java.util.List;

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class JsonContactPersoon {

    private Long id;
    private String wachtwoord;
    private String voornaam;
    private String tussenvoegsel;
    private String achternaam;
    private String emailadres;
    private Long bedrijf;
    private String functie;
    private List<JsonTelefoonnummer> telefoonnummers;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getWachtwoord() {
        return wachtwoord;
    }

    public void setWachtwoord(String wachtwoord) {
        this.wachtwoord = wachtwoord;
    }

    public String getVoornaam() {
        return voornaam;
    }

    public void setVoornaam(String voornaam) {
        this.voornaam = voornaam;
    }

    public String getTussenvoegsel() {
        return tussenvoegsel;
    }

    public void setTussenvoegsel(String tussenvoegsel) {
        this.tussenvoegsel = tussenvoegsel;
    }

    public String getAchternaam() {
        return achternaam;
    }

    public void setAchternaam(String achternaam) {
        this.achternaam = achternaam;
    }

    public String getEmailadres() {
        return emailadres;
    }

    public void setEmailadres(String emailadres) {
        this.emailadres = emailadres;
    }

    public Long getBedrijf() {
        return bedrijf;
    }

    public void setBedrijf(Long bedrijf) {
        this.bedrijf = bedrijf;
    }

    public String getFunctie() {
        return functie;
    }

    public void setFunctie(String functie) {
        this.functie = functie;
    }

    public List<JsonTelefoonnummer> getTelefoonnummers() {
        if (telefoonnummers == null) {
            telefoonnummers = new ArrayList<>();
        }
        return telefoonnummers;
    }

    public void setTelefoonnummers(List<JsonTelefoonnummer> telefoonnummers) {
        this.telefoonnummers = telefoonnummers;
    }
}
