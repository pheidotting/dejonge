package nl.dias.domein;

public enum SoortBijlage {
    POLIS("Polis"), SCHADE("Schade"), HYPOTHEEK("Hypotheek"), IBAANGIFTE("IB-Aangifte"), RELATIE("Relatie"), BEDRIJF("Bedrijf"), JAARCIJFERS("JaarCijfers"), RISICOANALYSE("RisicoAnalyse");

    private String omschrijving;

    private SoortBijlage(String omschrijving) {
        this.omschrijving = omschrijving;
    }

    public String getOmschrijving() {
        return omschrijving;
    }
}
