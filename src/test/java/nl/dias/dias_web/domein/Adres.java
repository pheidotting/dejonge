package nl.dias.dias_web.domein;

import java.io.Serializable;

public class Adres implements Serializable {

	private String straat;
	private Long huisnummer;
	private String toevoeging;
	private String postcode;
	private String plaats;

	public String getStraat() {
		return straat;
	}

	public void setStraat(String straat) {
		this.straat = straat;
	}

	public Long getHuisnummer() {
		return huisnummer;
	}

	public void setHuisnummer(Long huisnummer) {
		this.huisnummer = huisnummer;
	}

	public String getToevoeging() {
		return toevoeging;
	}

	public void setToevoeging(String toevoeging) {
		this.toevoeging = toevoeging;
	}

	public String getPostcode() {
		return postcode;
	}

	public void setPostcode(String postcode) {
		if (postcode != null) {
			this.postcode = postcode.trim().replace(" ", "");
		}
	}

	public String getPlaats() {
		return plaats;
	}

	public void setPlaats(String plaats) {
		this.plaats = plaats;
	}
}
