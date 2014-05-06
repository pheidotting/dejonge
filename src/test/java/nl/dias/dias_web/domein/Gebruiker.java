package nl.dias.dias_web.domein;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Transient;

public abstract class Gebruiker extends Onderwerp {
	private String voornaam;
	private String tussenvoegsel;
	private String achternaam;
	private Set<Sessie> sessies;

	@Transient
	private String wachtwoordString;

	public String getVoornaam() {
		return voornaam;
	}

	public void setVoornaam(String voornaam) {
		this.voornaam = voornaam;
	}

	public String getTussenvoegsel() {
		return tussenvoegsel;
	}

	public void setTussenvoegsel(String tussenvoegsel) {
		this.tussenvoegsel = tussenvoegsel;
	}

	public String getAchternaam() {
		return achternaam;
	}

	public void setAchternaam(String achternaam) {
		this.achternaam = achternaam;
	}

	public Set<Sessie> getSessies() {
		if (sessies == null) {
			sessies = new HashSet<>();
		}
		return sessies;
	}

	public void setSessies(Set<Sessie> sessies) {
		this.sessies = sessies;
	}

	public String getWachtwoordString() {
		return wachtwoordString;
	}

	public void setWachtwoordString(String wachtwoordString) {
		this.wachtwoordString = wachtwoordString;
	}

}
