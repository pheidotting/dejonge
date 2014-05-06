package nl.dias.dias_web.domein.polis;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import nl.dias.dias_web.domein.Bedrag;
import nl.dias.dias_web.domein.Bedrijf;
import nl.dias.dias_web.domein.Bijlage;
import nl.dias.dias_web.domein.Opmerking;
import nl.dias.dias_web.domein.Relatie;
import nl.dias.dias_web.domein.VerzekeringsMaatschappij;

import org.joda.time.LocalDate;

public abstract class Polis {
	private Long id;

	private String polisNummer;

	private Date ingangsDatum;

	private Bedrag premie;

	private Relatie relatie;

	private Bedrijf bedrijf;

	private Set<Bijlage> bijlages;

	private Set<Opmerking> opmerkingen;

	private VerzekeringsMaatschappij maatschappij;

	private String ingangsDatumString;

	public abstract SoortVerzekering getSoortVerzekering();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPolisNummer() {
		return polisNummer;
	}

	public void setPolisNummer(String polisNummer) {
		this.polisNummer = polisNummer;
	}

	public LocalDate getIngangsDatum() {
		return new LocalDate(ingangsDatum);
	}

	public void setIngangsDatum(LocalDate ingangsDatum) {
		this.ingangsDatum = ingangsDatum.toDate();
		setIngangsDatumString(getIngangsDatumString());
	}

	public String getIngangsDatumString() {
		return getIngangsDatum().toString("dd-MM-yyyy");
	}

	public void setIngangsDatumString(String ingangsDatum) {
		this.ingangsDatumString = ingangsDatum;
	}

	public Bedrag getPremie() {
		return premie;
	}

	public void setPremie(Bedrag premie) {
		this.premie = premie;
	}

	public Relatie getRelatie() {
		return relatie;
	}

	public void setRelatie(Relatie relatie) {
		this.relatie = relatie;
	}

	public Set<Bijlage> getBijlages() {
		if (bijlages == null) {
			bijlages = new HashSet<>();
		}
		return bijlages;
	}

	public void setBijlages(Set<Bijlage> bijlages) {
		this.bijlages = bijlages;
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

	public VerzekeringsMaatschappij getMaatschappij() {
		return maatschappij;
	}

	public void setMaatschappij(VerzekeringsMaatschappij maatschappij) {
		this.maatschappij = maatschappij;
	}

	public void setIngangsDatum(Date ingangsDatum) {
		this.ingangsDatum = ingangsDatum;
	}

	public Bedrijf getBedrijf() {
		return bedrijf;
	}

	public void setBedrijf(Bedrijf bedrijf) {
		this.bedrijf = bedrijf;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((bijlages == null) ? 0 : bijlages.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((ingangsDatum == null) ? 0 : ingangsDatum.hashCode());
		result = prime * result + ((ingangsDatumString == null) ? 0 : ingangsDatumString.hashCode());
		result = prime * result + ((maatschappij == null) ? 0 : maatschappij.hashCode());
		result = prime * result + ((opmerkingen == null) ? 0 : opmerkingen.hashCode());
		result = prime * result + ((polisNummer == null) ? 0 : polisNummer.hashCode());
		result = prime * result + ((premie == null) ? 0 : premie.hashCode());
		result = prime * result + ((relatie == null) ? 0 : relatie.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		Polis other = (Polis) obj;
		if (bijlages == null) {
			if (other.bijlages != null) {
				return false;
			}
		} else if (!bijlages.equals(other.bijlages)) {
			return false;
		}
		if (id == null) {
			if (other.id != null) {
				return false;
			}
		} else if (!id.equals(other.id)) {
			return false;
		}
		if (ingangsDatum == null) {
			if (other.ingangsDatum != null) {
				return false;
			}
		} else if (!ingangsDatum.equals(other.ingangsDatum)) {
			return false;
		}
		if (ingangsDatumString == null) {
			if (other.ingangsDatumString != null) {
				return false;
			}
		} else if (!ingangsDatumString.equals(other.ingangsDatumString)) {
			return false;
		}
		if (maatschappij == null) {
			if (other.maatschappij != null) {
				return false;
			}
		} else if (!maatschappij.equals(other.maatschappij)) {
			return false;
		}
		if (opmerkingen == null) {
			if (other.opmerkingen != null) {
				return false;
			}
		} else if (!opmerkingen.equals(other.opmerkingen)) {
			return false;
		}
		if (polisNummer == null) {
			if (other.polisNummer != null) {
				return false;
			}
		} else if (!polisNummer.equals(other.polisNummer)) {
			return false;
		}
		if (premie == null) {
			if (other.premie != null) {
				return false;
			}
		} else if (!premie.equals(other.premie)) {
			return false;
		}
		if (relatie == null) {
			if (other.relatie != null) {
				return false;
			}
		} else if (!relatie.equals(other.relatie)) {
			return false;
		}
		return true;
	}
}
