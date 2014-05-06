package nl.dias.dias_web.domein;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.joda.time.LocalDate;

public class Kantoor {
	private Long id;

	private String naam;

	private Adres adres;

	private Adres factuurAdres;

	private Long kvk;

	private String btwNummer;

	private Date datumOprichting;

	private Rechtsvorm rechtsvorm;

	private SoortKantoor soortKantoor;

	private String emailadres;

	private List<Medewerker> medewerkers;

	private Set<Relatie> relaties;

	private Set<Opmerking> opmerkingen;

	private Set<RekeningNummer> rekeningnummers;

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

	public Adres getAdres() {
		if (adres == null) {
			adres = new Adres();
		}
		return adres;
	}

	public void setAdres(Adres adres) {
		this.adres = adres;
	}

	public Adres getFactuurAdres() {
		if (factuurAdres == null) {
			factuurAdres = new Adres();
		}
		return factuurAdres;
	}

	public void setFactuurAdres(Adres factuurAdres) {
		this.factuurAdres = factuurAdres;
	}

	public List<Medewerker> getMedewerkers() {
		if (medewerkers == null) {
			medewerkers = new ArrayList<>();
		}
		return medewerkers;
	}

	public void setMedewerkers(List<Medewerker> medewerkers) {
		this.medewerkers = medewerkers;
	}

	public Set<Relatie> getRelaties() {
		if (relaties == null) {
			relaties = new HashSet<>();
		}
		return relaties;
	}

	public void setRelaties(Set<Relatie> relaties) {
		this.relaties = relaties;
	}

	public Set<Opmerking> getOpmerkingen() {
		if (opmerkingen == null) {
			opmerkingen = new HashSet<>();
		}
		return opmerkingen;
	}

	public void setOpmerkingen(Set<Opmerking> opmerkingen) {
		this.opmerkingen = opmerkingen;
	}

	public Long getKvk() {
		return kvk;
	}

	public void setKvk(Long kvk) {
		this.kvk = kvk;
	}

	public String getBtwNummer() {
		return btwNummer;
	}

	public void setBtwNummer(String btwNummer) {
		this.btwNummer = btwNummer;
	}

	public LocalDate getDatumOprichting() {
		return new LocalDate(datumOprichting);
	}

	public void setDatumOprichting(LocalDate datumOprichting) {
		this.datumOprichting = datumOprichting.toDate();
	}

	public Rechtsvorm getRechtsvorm() {
		return rechtsvorm;
	}

	public void setRechtsvorm(Rechtsvorm rechtsvorm) {
		this.rechtsvorm = rechtsvorm;
	}

	public SoortKantoor getSoortKantoor() {
		return soortKantoor;
	}

	public void setSoortKantoor(SoortKantoor soortKantoor) {
		this.soortKantoor = soortKantoor;
	}

	public String getEmailadres() {
		return emailadres;
	}

	public void setEmailadres(String emailadres) {
		this.emailadres = emailadres;
	}

	public Set<RekeningNummer> getRekeningnummers() {
		if (rekeningnummers == null) {
			rekeningnummers = new HashSet<>();
		}
		return rekeningnummers;
	}

	public void setRekeningnummers(Set<RekeningNummer> rekeningnummers) {
		this.rekeningnummers = rekeningnummers;
	}

}
