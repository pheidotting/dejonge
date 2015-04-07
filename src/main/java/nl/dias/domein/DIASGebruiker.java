package nl.dias.domein;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import nl.lakedigital.hulpmiddelen.domein.PersistenceObject;

@Entity
@Table(name = "DIASGEBRUIKER")
public class DIASGebruiker implements PersistenceObject, Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "1_NAAM")
    private String achternaam;
    @Column(name = "2_RELATIE_REGISTRATIENUMMER")
    private String nummer;
    @Column(name = "5_VOLLEDIGE_NAAM")
    private String volledigeNaam;
    @Column(name = "6_BURGER_SERVICE_NUMMER")
    private String bsn;
    @Column(name = "7_BURGERLIJKE_STAAT")
    private String burgerlijkeStaat;
    @Column(name = "8_DATUM_OVERLIJDEN")
    private String datumOverlijden;
    @Column(name = "9_GEBOORTEDATUM")
    private String geboorteDatum;
    @Column(name = "10_GEBOORTELAND")
    private String geboorteLand;
    @Column(name = "11_GEBOORTEPLAATS")
    private String geboortePlaats;
    @Column(name = "12_GESLACHT")
    private String geslacht;
    @Column(name = "14_LEGITIMATIE")
    private String legitimatie;
    @Column(name = "15_LEGITIMATIEDATUM")
    private String legitimatieDatum;
    @Column(name = "16_LEGITIMATIENUMMER")
    private String legitimatieNummer;
    @Column(name = "17_LEGITIMATIEPLAATS")
    private String legitimatiePlaats;
    @Column(name = "18_NATIONALITEIT")
    private String nationaliteit;
    @Column(name = "19_ROEPNAAM")
    private String roepnaam;
    @Column(name = "20_ROKER")
    private String roker;
    @Column(name = "21_TITEL")
    private String titel;
    @Column(name = "22_TITULATUUR")
    private String titulatuur;
    @Column(name = "23_AANSCHRIJFTITEL")
    private String aanschrijftitel;
    @Column(name = "24_VOORLETTERS")
    private String voorletters;
    @Column(name = "25_VOORNAMEN")
    private String voornamen;
    @Column(name = "26_VOORVOEGSELS")
    private String voorvoegsels;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public String getAchternaam() {
        return achternaam;
    }

    public void setAchternaam(String achternaam) {
        this.achternaam = achternaam;
    }

    public String getNummer() {
        return nummer;
    }

    public void setNummer(String nummer) {
        this.nummer = nummer;
    }

    public String getVolledigeNaam() {
        return volledigeNaam;
    }

    public void setVolledigeNaam(String volledigeNaam) {
        this.volledigeNaam = volledigeNaam;
    }

    public String getBsn() {
        return bsn;
    }

    public void setBsn(String bsn) {
        this.bsn = bsn;
    }

    public String getBurgerlijkeStaat() {
        return burgerlijkeStaat;
    }

    public void setBurgerlijkeStaat(String burgerlijkeStaat) {
        this.burgerlijkeStaat = burgerlijkeStaat;
    }

    public String getDatumOverlijden() {
        return datumOverlijden;
    }

    public void setDatumOverlijden(String datumOverlijden) {
        this.datumOverlijden = datumOverlijden;
    }

    public String getGeboorteDatum() {
        return geboorteDatum;
    }

    public void setGeboorteDatum(String geboorteDatum) {
        this.geboorteDatum = geboorteDatum;
    }

    public String getGeboorteLand() {
        return geboorteLand;
    }

    public void setGeboorteLand(String geboorteLand) {
        this.geboorteLand = geboorteLand;
    }

    public String getGeboortePlaats() {
        return geboortePlaats;
    }

    public void setGeboortePlaats(String geboortePlaats) {
        this.geboortePlaats = geboortePlaats;
    }

    public String getGeslacht() {
        return geslacht;
    }

    public void setGeslacht(String geslacht) {
        this.geslacht = geslacht;
    }

    public String getLegitimatie() {
        return legitimatie;
    }

    public void setLegitimatie(String legitimatie) {
        this.legitimatie = legitimatie;
    }

    public String getLegitimatieDatum() {
        return legitimatieDatum;
    }

    public void setLegitimatieDatum(String legitimatieDatum) {
        this.legitimatieDatum = legitimatieDatum;
    }

    public String getLegitimatieNummer() {
        return legitimatieNummer;
    }

    public void setLegitimatieNummer(String legitimatieNummer) {
        this.legitimatieNummer = legitimatieNummer;
    }

    public String getLegitimatiePlaats() {
        return legitimatiePlaats;
    }

    public void setLegitimatiePlaats(String legitimatiePlaats) {
        this.legitimatiePlaats = legitimatiePlaats;
    }

    public String getNationaliteit() {
        return nationaliteit;
    }

    public void setNationaliteit(String nationaliteit) {
        this.nationaliteit = nationaliteit;
    }

    public String getRoepnaam() {
        return roepnaam;
    }

    public void setRoepnaam(String roepnaam) {
        this.roepnaam = roepnaam;
    }

    public String getRoker() {
        return roker;
    }

    public void setRoker(String roker) {
        this.roker = roker;
    }

    public String getTitel() {
        return titel;
    }

    public void setTitel(String titel) {
        this.titel = titel;
    }

    public String getTitulatuur() {
        return titulatuur;
    }

    public void setTitulatuur(String titulatuur) {
        this.titulatuur = titulatuur;
    }

    public String getAanschrijftitel() {
        return aanschrijftitel;
    }

    public void setAanschrijftitel(String aanschrijftitel) {
        this.aanschrijftitel = aanschrijftitel;
    }

    public String getVoorletters() {
        return voorletters;
    }

    public void setVoorletters(String voorletters) {
        this.voorletters = voorletters;
    }

    public String getVoornamen() {
        return voornamen;
    }

    public void setVoornamen(String voornamen) {
        this.voornamen = voornamen;
    }

    public String getVoorvoegsels() {
        return voorvoegsels;
    }

    public void setVoorvoegsels(String voorvoegsels) {
        this.voorvoegsels = voorvoegsels;
    }

}
