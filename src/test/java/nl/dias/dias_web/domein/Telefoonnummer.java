package nl.dias.dias_web.domein;

public class Telefoonnummer {

	private Long id;

	private String telefoonnummer;

	private TelefoonnummerSoort soort;

	private Relatie relatie;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTelefoonnummer() {
		return telefoonnummer;
	}

	public void setTelefoonnummer(String telefoonnummer) {
		this.telefoonnummer = telefoonnummer;
	}

	public TelefoonnummerSoort getSoort() {
		return soort;
	}

	public void setSoort(TelefoonnummerSoort soort) {
		this.soort = soort;
	}

	public Relatie getRelatie() {
		return relatie;
	}

	public void setRelatie(Relatie relatie) {
		this.relatie = relatie;
	}
}