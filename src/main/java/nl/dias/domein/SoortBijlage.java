package nl.dias.domein;

public enum SoortBijlage {
    POLIS("Polis");
    private String omschrijving;

    private SoortBijlage(String omschrijving) {
        this.omschrijving = omschrijving;
    }

    public String getOmschrijving() {
        return omschrijving;
    }
}
