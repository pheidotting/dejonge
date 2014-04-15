package nl.dias.domein.polis;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

@Entity
@Table(name = "POLIS")
@DiscriminatorValue(value = "M")
public class MotorVerzekering extends Polis {
	private static final long serialVersionUID = -7971692907347145987L;

	@Column(name = "OLDTIMER")
	private boolean oldtimer;

	@Column(length = 10, name = "KENTEKEN")
	private String kenteken;

	@Override
	public SoortVerzekering getSoortVerzekering() {
		return SoortVerzekering.PARTICULIER;
	}

	public boolean isOldtimer() {
		return oldtimer;
	}

	public void setOldtimer(boolean oldtimer) {
		this.oldtimer = oldtimer;
	}

	public String getKenteken() {
		return kenteken;
	}

	public void setKenteken(String kenteken) {
		this.kenteken = kenteken;
	}

	@Override
	public int hashCode() {
		HashCodeBuilder builder = new HashCodeBuilder();
		builder.append(kenteken);
		builder.append(oldtimer);
		return builder.toHashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (!super.equals(obj)) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		MotorVerzekering other = (MotorVerzekering) obj;
		return new EqualsBuilder().append(kenteken, other.kenteken).append(oldtimer, other.oldtimer).isEquals();
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("MotorVerzekering [oldtimer=");
		builder.append(oldtimer);
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
