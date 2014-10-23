package nl.dias.domein;

public enum TaakStatus {
    O("Open"), I("In behandeling"), W("Wacht op klant"), A("Afgesloten"), H("Heropend");

    private String omschrijving;

    private TaakStatus(String omschrijving) {
        this.omschrijving = omschrijving;
    }

    public String getOmschrijving() {
        return omschrijving;
    }

}
