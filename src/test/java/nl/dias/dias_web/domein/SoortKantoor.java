package nl.dias.dias_web.domein;

public enum SoortKantoor {
	HYP("Hypotheek"), VZ("Verzekering");

	private String omschrijving;

	private SoortKantoor(String omschrijving) {
		this.omschrijving = omschrijving;
	}

	public String getOmschrijving() {
		return omschrijving;
	}

	public void setOmschrijving(String omschrijving) {
		this.omschrijving = omschrijving;
	}
}
