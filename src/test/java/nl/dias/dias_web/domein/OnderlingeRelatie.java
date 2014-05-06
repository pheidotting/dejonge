package nl.dias.dias_web.domein;

public class OnderlingeRelatie {

	private Long id;

	private Relatie relatie;

	private Relatie relatieMet;

	private OnderlingeRelatieSoort onderlingeRelatieSoort;

	public OnderlingeRelatie() {
	}

	public OnderlingeRelatie(Relatie relatie, Relatie relatieMet, OnderlingeRelatieSoort soort) {
		this.relatie = relatie;
		this.onderlingeRelatieSoort = soort;
		setRelatieMet(relatieMet, true);
	}

	public OnderlingeRelatie(Relatie relatie, Relatie relatieMet, boolean terug, OnderlingeRelatieSoort soort) {
		this.relatie = relatie;
		this.onderlingeRelatieSoort = soort;
		setRelatieMet(relatieMet, terug);
	}

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

	public Relatie getRelatieMet() {
		return relatieMet;
	}

	public void setRelatieMet(Relatie relatieMet, boolean terug) {
		this.relatieMet = relatieMet;
		if (terug) {
			this.relatieMet.getOnderlingeRelaties().add(new OnderlingeRelatie(this.relatieMet, relatie, false, OnderlingeRelatieSoort.getTegenGesteld(onderlingeRelatieSoort)));
		}
	}

	public OnderlingeRelatieSoort getOnderlingeRelatieSoort() {
		return onderlingeRelatieSoort;
	}

	public void setOnderlingeRelatieSoort(OnderlingeRelatieSoort onderlingeRelatieSoort) {
		this.onderlingeRelatieSoort = onderlingeRelatieSoort;
	}
}
