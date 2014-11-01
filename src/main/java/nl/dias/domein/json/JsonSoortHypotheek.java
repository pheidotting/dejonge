package nl.dias.domein.json;

public class JsonSoortHypotheek implements Comparable<JsonSoortHypotheek> {
    private Long id;
    private String omschrijving;
    private boolean ingebruik;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOmschrijving() {
        return omschrijving;
    }

    public void setOmschrijving(String omschrijving) {
        this.omschrijving = omschrijving;
    }

    public boolean isIngebruik() {
        return ingebruik;
    }

    public void setIngebruik(boolean ingebruik) {
        this.ingebruik = ingebruik;
    }

    @Override
    public int compareTo(JsonSoortHypotheek o) {
        return omschrijving.compareTo(o.getOmschrijving());
    }
}
