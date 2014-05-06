package nl.dias.dias_web.domein;

import java.util.HashSet;
import java.util.Set;

import nl.dias.dias_web.domein.polis.Polis;

public class Bedrijf {
	private Long id;

	private Relatie relatie;

	private Set<Polis> polissen;

	private String naam;

	private String kvk;

	private Adres adres;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Relatie getRelatie() {
		return relatie;
	}

	public void setRelatie(Relatie relatie) {
		this.relatie = relatie;
	}

	public String getNaam() {
		return naam;
	}

	public void setNaam(String naam) {
		this.naam = naam;
	}

	public Set<Polis> getPolissen() {
		if (polissen == null) {
			polissen = new HashSet<>();
		}
		return polissen;
	}

	public void setPolissen(Set<Polis> polissen) {
		this.polissen = polissen;
	}

	public String getKvk() {
		return kvk;
	}

	public void setKvk(String kvk) {
		this.kvk = kvk;
	}

	public Adres getAdres() {
		return adres;
	}

	public void setAdres(Adres adres) {
		this.adres = adres;
	}

}
