package nl.dias.domein.polis;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

@Entity
@Table(name = "POLIS")
@DiscriminatorValue(value = "A")
public class AutoVerzekering extends Polis {
	private static final long serialVersionUID = 4412157021471248581L;

	@Column(length = 10, name = "SOORTAUTOVERZEKERING")
	@Enumerated(EnumType.STRING)
	private SoortAutoVerzekering soortAutoVerzekering;

	@Column(length = 10, name = "KENTEKEN")
	private String kenteken;

	@Override
	public SoortVerzekering getSoortVerzekering() {
		return SoortVerzekering.PARTICULIER;
	}

	public SoortAutoVerzekering getSoortAutoVerzekering() {
		return soortAutoVerzekering;
	}

	public void setSoortAutoVerzekering(SoortAutoVerzekering soortAutoVerzekering) {
		this.soortAutoVerzekering = soortAutoVerzekering;
	}

	public String getKenteken() {
		return kenteken;
	}

	public void setKenteken(String kenteken) {
		this.kenteken = kenteken;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((kenteken == null) ? 0 : kenteken.hashCode());
		result = prime * result + ((soortAutoVerzekering == null) ? 0 : soortAutoVerzekering.hashCode());
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
		AutoVerzekering other = (AutoVerzekering) obj;
		if (kenteken == null) {
			if (other.kenteken != null) {
				return false;
			}
		} else if (!kenteken.equals(other.kenteken)) {
			return false;
		}
		if (soortAutoVerzekering != other.soortAutoVerzekering) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("AutoVerzekering [soortAutoVerzekering=");
		builder.append(soortAutoVerzekering);
		builder.append(", kenteken=");
		builder.append(kenteken);
		builder.append(", getId()=");
		builder.append(getId());
		builder.append(", getPolisNummer()=");
		builder.append(getPolisNummer());
		builder.append(", getIngangsDatum()=");
		builder.append(getIngangsDatum());
		builder.append(", getPremie()=");
		builder.append(getPremie());
		builder.append(", getRelatie()=");
		builder.append(getRelatie());
		builder.append(", getBijlages()=");
		builder.append(getBijlages().size());
		builder.append(", getOpmerkingen()=");
		builder.append(getOpmerkingen().size());
		builder.append(", getMaatschappij()=");
		builder.append(getMaatschappij());
		builder.append("]");
		return builder.toString();
	}

}
