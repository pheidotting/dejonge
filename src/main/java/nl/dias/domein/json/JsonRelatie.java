package nl.dias.domein.json;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.joda.time.LocalDate;

public class JsonRelatie implements Serializable {
    private static final long serialVersionUID = 1178673982786375486L;

    private Long id;
    private String identificatie;
    private String voornaam;
    private String tussenvoegsel;
    private String achternaam;
    private String straat;
    private Long huisnummer;
    private String toevoeging;
    private String postcode;
    private String plaats;
    private String adresOpgemaakt;
    private List<JsonTelefoonnummer> telefoonnummers;
    private String bsn;
    private List<JsonRekeningNummer> rekeningnummers;
    private Long kantoor;
    private List<JsonOpmerking> opmerkingen;
    private LocalDate geboorteDatum;
    private String geboorteDatumOpgemaakt;
    private LocalDate overlijdensdatum;
    private String overlijdensdatumOpgemaakt;
    private String geslacht;
    private String burgerlijkeStaat;
    private List<Long> onderlingeRelaties;
    private List<Long> bedrijven;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIdentificatie() {
        return identificatie;
    }

    public void setIdentificatie(String identificatie) {
        this.identificatie = identificatie;
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

    public String getStraat() {
        return straat;
    }

    public void setStraat(String straat) {
        this.straat = straat;
    }

    public Long getHuisnummer() {
        return huisnummer;
    }

    public void setHuisnummer(Long huisnummer) {
        this.huisnummer = huisnummer;
    }

    public String getToevoeging() {
        return toevoeging;
    }

    public void setToevoeging(String toevoeging) {
        this.toevoeging = toevoeging;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public String getPlaats() {
        return plaats;
    }

    public void setPlaats(String plaats) {
        this.plaats = plaats;
    }

    public String getAdresOpgemaakt() {
        return adresOpgemaakt;
    }

    public void setAdresOpgemaakt(String adresOpgemaakt) {
        this.adresOpgemaakt = adresOpgemaakt;
    }

    public List<JsonTelefoonnummer> getTelefoonnummers() {
        return telefoonnummers;
    }

    public void setTelefoonnummers(List<JsonTelefoonnummer> telefoonnummers) {
        this.telefoonnummers = telefoonnummers;
    }

    public String getBsn() {
        return bsn;
    }

    public void setBsn(String bsn) {
        this.bsn = bsn;
    }

    public List<JsonRekeningNummer> getRekeningnummers() {
        return rekeningnummers;
    }

    public void setRekeningnummers(List<JsonRekeningNummer> rekeningnummers) {
        this.rekeningnummers = rekeningnummers;
    }

    public Long getKantoor() {
        return kantoor;
    }

    public void setKantoor(Long kantoor) {
        this.kantoor = kantoor;
    }

    public List<JsonOpmerking> getOpmerkingen() {
        return opmerkingen;
    }

    public void setOpmerkingen(List<JsonOpmerking> opmerkingen) {
        this.opmerkingen = opmerkingen;
    }

    public LocalDate getGeboorteDatum() {
        return geboorteDatum;
    }

    public void setGeboorteDatum(LocalDate geboorteDatum) {
        this.geboorteDatum = geboorteDatum;
    }

    public String getGeboorteDatumOpgemaakt() {
        return geboorteDatumOpgemaakt;
    }

    public void setGeboorteDatumOpgemaakt(String geboorteDatumOpgemaakt) {
        this.geboorteDatumOpgemaakt = geboorteDatumOpgemaakt;
    }

    public LocalDate getOverlijdensdatum() {
        return overlijdensdatum;
    }

    public void setOverlijdensdatum(LocalDate overlijdensdatum) {
        this.overlijdensdatum = overlijdensdatum;
    }

    public String getOverlijdensdatumOpgemaakt() {
        return overlijdensdatumOpgemaakt;
    }

    public void setOverlijdensdatumOpgemaakt(String overlijdensdatumOpgemaakt) {
        this.overlijdensdatumOpgemaakt = overlijdensdatumOpgemaakt;
    }

    public String getGeslacht() {
        return geslacht;
    }

    public void setGeslacht(String geslacht) {
        this.geslacht = geslacht;
    }

    public String getBurgerlijkeStaat() {
        return burgerlijkeStaat;
    }

    public void setBurgerlijkeStaat(String burgerlijkeStaat) {
        this.burgerlijkeStaat = burgerlijkeStaat;
    }

    public List<Long> getOnderlingeRelaties() {
        return onderlingeRelaties;
    }

    public void setOnderlingeRelaties(List<Long> onderlingeRelaties) {
        this.onderlingeRelaties = onderlingeRelaties;
    }

    public List<Long> getBedrijven() {
        return bedrijven;
    }

    public void setBedrijven(List<Long> bedrijven) {
        this.bedrijven = bedrijven;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(achternaam).append(bedrijven).append(bsn).append(burgerlijkeStaat).append(geboorteDatum).append(geslacht).append(huisnummer).append(id)
                .append(identificatie).append(kantoor).append(onderlingeRelaties).append(opmerkingen).append(overlijdensdatum).append(plaats).append(postcode).append(rekeningnummers).append(straat)
                .append(telefoonnummers).append(toevoeging).append(tussenvoegsel).append(voornaam).toHashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        JsonRelatie other = (JsonRelatie) obj;

        return new EqualsBuilder().append(achternaam, other.achternaam).append(bedrijven, other.bedrijven).append(bsn, other.bsn).append(burgerlijkeStaat, other.burgerlijkeStaat)
                .append(geboorteDatum, other.geboorteDatum).append(geslacht, other.geslacht).append(huisnummer, other.huisnummer).append(id, other.id).append(identificatie, other.identificatie)
                .append(kantoor, other.kantoor).append(onderlingeRelaties, other.onderlingeRelaties).append(opmerkingen, other.opmerkingen).append(overlijdensdatum, other.overlijdensdatum)
                .append(plaats, other.plaats).append(postcode, other.postcode).append(rekeningnummers, other.rekeningnummers).append(straat, other.straat)
                .append(telefoonnummers, other.telefoonnummers).append(toevoeging, other.toevoeging).append(tussenvoegsel, other.tussenvoegsel).append(voornaam, other.voornaam).isEquals();
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Relatie [id=");
        builder.append(id);
        builder.append(", identificatie=");
        builder.append(identificatie);
        builder.append(", voornaam=");
        builder.append(voornaam);
        builder.append(", tussenvoegsel=");
        builder.append(tussenvoegsel);
        builder.append(", achternaam=");
        builder.append(achternaam);
        builder.append(", straat=");
        builder.append(straat);
        builder.append(", huisnummer=");
        builder.append(huisnummer);
        builder.append(", toevoeging=");
        builder.append(toevoeging);
        builder.append(", postcode=");
        builder.append(postcode);
        builder.append(", plaats=");
        builder.append(plaats);
        builder.append(", telefoonnummers=");
        builder.append(telefoonnummers);
        builder.append(", bsn=");
        builder.append(bsn);
        builder.append(", rekeningnummers=");
        builder.append(rekeningnummers);
        builder.append(", kantoor=");
        builder.append(kantoor);
        builder.append(", opmerkingen=");
        builder.append(opmerkingen);
        builder.append(", geboorteDatum=");
        builder.append(geboorteDatum);
        builder.append(", overlijdensdatum=");
        builder.append(overlijdensdatum);
        builder.append(", geslacht=");
        builder.append(geslacht);
        builder.append(", burgerlijkeStaat=");
        builder.append(burgerlijkeStaat);
        builder.append(", onderlingeRelaties=");
        builder.append(onderlingeRelaties);
        builder.append(", bedrijven=");
        builder.append(bedrijven);
        builder.append("]");
        return builder.toString();
    }

}
