package nl.dias.dias_web.domein.polis;

public class MotorVerzekering extends Polis {

	private boolean oldtimer;

	private String kenteken;

	@Override
	public SoortVerzekering getSoortVerzekering() {
		return SoortVerzekering.PARTICULIER;
	}

	public boolean isOldtimer() {
		return oldtimer;
	}

	public void setOldtimer(boolean oldtimer) {
		this.oldtimer = oldtimer;
	}

	public String getKenteken() {
		return kenteken;
	}

	public void setKenteken(String kenteken) {
		this.kenteken = kenteken;
	}
}
