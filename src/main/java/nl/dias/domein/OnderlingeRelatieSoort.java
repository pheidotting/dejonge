package nl.dias.domein;

import java.util.HashMap;
import java.util.Map;

public enum OnderlingeRelatieSoort {
	O("Ouder"), K("Kind"), W("Werknemer"), WG("Werkgever");

	private String omschrijving;

	private OnderlingeRelatieSoort(String omschrijving) {
		this.omschrijving = omschrijving;
	}

	public String getOmschrijving() {
		return omschrijving;
	}

	public void setOmschrijving(String omschrijving) {
		this.omschrijving = omschrijving;
	}

	public static OnderlingeRelatieSoort getTegenGesteld(OnderlingeRelatieSoort soort) {
		Map<OnderlingeRelatieSoort, OnderlingeRelatieSoort> soorten = new HashMap<OnderlingeRelatieSoort, OnderlingeRelatieSoort>();
		soorten.put(O, K);
		soorten.put(K, O);
		soorten.put(W, WG);
		soorten.put(WG, W);

		return soorten.get(soort);
	}
}