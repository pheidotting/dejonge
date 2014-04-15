package nl.dias.domein.polis;

public enum SoortAutoVerzekering {
	Auto, Vrachtauto, Oldtimer;

	public static SoortAutoVerzekering zoek(String soort) {
		for (SoortAutoVerzekering sav : SoortAutoVerzekering.values()) {
			if (sav.toString().equals(soort)) {
				return sav;
			}
		}

		return null;
	}
}