package nl.dias.dias_web.domein;


public class RekeningNummer {

	private Long id;

	private String bic;

	private String rekeningnummer;

	private Relatie relatie;

	private Kantoor kantoor;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getBic() {
		return bic;
	}

	public void setBic(String bic) {
		this.bic = bic;
	}

	public String getRekeningnummer() {
		return rekeningnummer;
	}

	public void setRekeningnummer(String rekeningnummer) {
		this.rekeningnummer = rekeningnummer;
	}

	public Relatie getRelatie() {
		return relatie;
	}

	public void setRelatie(Relatie relatie) {
		this.relatie = relatie;
	}

	public Kantoor getKantoor() {
		return kantoor;
	}

	public void setKantoor(Kantoor kantoor) {
		this.kantoor = kantoor;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("RekeningNummer [id=");
		builder.append(id);
		builder.append(", bic=");
		builder.append(bic);
		builder.append(", rekeningnummer=");
		builder.append(rekeningnummer);
		builder.append("]");
		return builder.toString();
	}
}
