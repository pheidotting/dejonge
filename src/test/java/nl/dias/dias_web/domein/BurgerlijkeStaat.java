package nl.dias.dias_web.domein;

public enum BurgerlijkeStaat {
	O("Ongehuwd"), G("Gehuwd");

	private String omschrijving;

	private BurgerlijkeStaat(String omschrijving) {
		this.omschrijving = omschrijving;
	}

	public String getOmschrijving() {
		return omschrijving;
	}

	public void setOmschrijving(String omschrijving) {
		this.omschrijving = omschrijving;
	}

}
