package nl.dias.domein.json;

import java.util.List;

public class JsonBedrijf {
    private Long id;
    private List<JsonPolis> polissen;
    private String naam;
    private String kvk;
    private String straat;
    private Long huisnummer;
    private String toevoeging;
    private String postcode;
    private String plaats;
    private Long relatie;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<JsonPolis> getPolissen() {
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

    public Long getRelatie() {
        return relatie;
    }

    public void setRelatie(Long relatie) {
        this.relatie = relatie;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("JsonBedrijf [id=");
        builder.append(id);
        builder.append(", polissen=");
        builder.append(polissen);
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

}
