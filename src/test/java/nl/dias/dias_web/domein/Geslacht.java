package nl.dias.dias_web.domein;

public enum Geslacht {
	M("Man"), V("Vrouw");

	private String omschrijving;

	private Geslacht(String omschrijving) {
		this.omschrijving = omschrijving;
	}

	public String getOmschrijving() {
		return omschrijving;
	}

	public void setOmschrijving(String omschrijving) {
		this.omschrijving = omschrijving;
	}
}
