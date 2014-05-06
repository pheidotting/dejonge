package nl.dias.dias_web.domein;

import java.util.Date;

import nl.dias.dias_web.domein.polis.Polis;

import org.joda.time.LocalDateTime;

public class Opmerking {
	private Long id;

	private Date tijd;

	private Relatie relatie;

	private Kantoor kantoor;

	private Polis polis;

	private String opmerking;

	public Opmerking() {
		tijd = new Date();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LocalDateTime getTijd() {
		return new LocalDateTime(tijd);
	}

	public void setTijd(LocalDateTime tijd) {
		this.tijd = tijd.toDate();
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

	public String getOpmerking() {
		return opmerking;
	}

	public void setOpmerking(String opmerking) {
		this.opmerking = opmerking;
	}

	public Polis getPolis() {
		return polis;
	}

	public void setPolis(Polis polis) {
		this.polis = polis;
	}
}
