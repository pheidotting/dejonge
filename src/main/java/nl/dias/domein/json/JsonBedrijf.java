package nl.dias.domein.json;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

public class JsonBedrijf {
    private String id;
    private List<JsonPolis> polissen;
    private String naam;
    private String kvk;
    private String straat;
    private String huisnummer;
    private String toevoeging;
    private String postcode;
    private String plaats;
    private String relatie;
    private String idDiv;
    private String idDivLink;
    private List<String> errors;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<JsonPolis> getPolissen() {
        if (polissen == null) {
            polissen = new ArrayList<JsonPolis>();
        }
        return polissen;
    }

    public void setPolissen(List<JsonPolis> polissen) {
        this.polissen = polissen;
    }

    public String getNaam() {
        return naam;
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }

    public String getKvk() {
        return kvk;
    }

    public void setKvk(String kvk) {
        this.kvk = kvk;
    }

    public String getStraat() {
        return straat;
    }

    public void setStraat(String straat) {
        this.straat = straat;
    }

    public String getHuisnummer() {
        return huisnummer;
    }

    public void setHuisnummer(String huisnummer) {
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

    public String getRelatie() {
        return relatie;
    }

    public void setRelatie(String relatie) {
        this.relatie = relatie;
    }

    public String getIdDiv() {
        return idDiv;
    }

    public void setIdDiv(String idDiv) {
        this.idDiv = idDiv;
    }

    public String getIdDivLink() {
        return idDivLink;
    }

    public void setIdDivLink(String idDivLink) {
        this.idDivLink = idDivLink;
    }

    public List<String> getErrors() {
        return errors;
    }

    public void setErrors(List<String> errors) {
        this.errors = errors;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("\nJsonBedrijf [id=");
        builder.append(id);
        builder.append(", polissen=");
        builder.append(getPolissen());
        builder.append(", naam=");
        builder.append(naam);
        builder.append(", kvk=");
        builder.append(kvk);
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
        builder.append(", relatie=");
        builder.append(relatie);
        builder.append("]");
        return builder.toString();
    }

    /**
     * @see java.lang.Object#equals(Object)
     */
    @Override
    public boolean equals(Object object) {
        if (!(object instanceof JsonBedrijf)) {
            return false;
        }
        JsonBedrijf rhs = (JsonBedrijf) object;
        return new EqualsBuilder().append(this.id, rhs.id).append(this.plaats, rhs.plaats).append(this.relatie, rhs.relatie).append(this.postcode, rhs.postcode).append(this.kvk, rhs.kvk)
                .append(this.naam, rhs.naam).append(this.toevoeging, rhs.toevoeging).append(this.huisnummer, rhs.huisnummer).append(this.straat, rhs.straat)
                .append(this.getPolissen(), rhs.getPolissen()).isEquals();
    }

    /**
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(this.id).append(this.plaats).append(this.relatie).append(this.postcode).append(this.kvk).append(this.naam).append(this.toevoeging).append(this.huisnummer)
                .append(this.straat).append(this.getPolissen()).toHashCode();
    }

}
