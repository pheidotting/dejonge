package nl.dias.dias_web.domein;

import nl.dias.dias_web.domein.polis.Polis;

public class Bijlage {
	private Long id;

	private Polis polis;

	private String bestandsNaam;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Polis getPolis() {
		return polis;
	}

	public void setPolis(Polis polis) {
		this.polis = polis;
	}

	public String getBestandsNaam() {
		return bestandsNaam;
	}

	public void setBestandsNaam(String bestandsNaam) {
		this.bestandsNaam = bestandsNaam;
	}
}
