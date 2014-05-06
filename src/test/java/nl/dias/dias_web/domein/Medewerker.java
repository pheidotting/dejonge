package nl.dias.dias_web.domein;

public class Medewerker extends Gebruiker {
	private Kantoor kantoor;

	public Kantoor getKantoor() {
		return kantoor;
	}

	public void setKantoor(Kantoor kantoor) {
		this.kantoor = kantoor;
	}

}
