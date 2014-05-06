package nl.dias.dias_web.domein;

import java.io.Serializable;

public class OnderlingeRelatieJson implements Serializable {
	private Long id;
	private Long idNaar;
	private String soort;
	private String metWie;

	public OnderlingeRelatieJson(OnderlingeRelatie or) {
		setId(or.getId());
		setIdNaar(or.getRelatieMet().getId());
		setSoort(or.getOnderlingeRelatieSoort().getOmschrijving());
		if (or.getRelatieMet().getTussenvoegsel() == null) {
			setMetWie(or.getRelatieMet().getVoornaam() + " " + or.getRelatieMet().getAchternaam());
		} else {
			setMetWie(or.getRelatieMet().getVoornaam() + " " + or.getRelatieMet().getTussenvoegsel() + " " + or.getRelatieMet().getAchternaam());
		}
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getIdNaar() {
		return idNaar;
	}

	public void setIdNaar(Long idNaar) {
		this.idNaar = idNaar;
	}

	public String getSoort() {
		return soort;
	}

	public void setSoort(String soort) {
		this.soort = soort;
	}

	public String getMetWie() {
		return metWie;
	}

	public void setMetWie(String metWie) {
		this.metWie = metWie;
	}
}
