package nl.dias.dias_web.domein;


public class VerzekeringsMaatschappij {
	private Long id;

	private String naam;

	private boolean tonen;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNaam() {
		return naam;
	}

	public void setNaam(String naam) {
		this.naam = naam;
	}

	public boolean isTonen() {
		return tonen;
	}

	public void setTonen(boolean tonen) {
		this.tonen = tonen;
	}
}
