package nl.dias.domein;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import org.apache.commons.lang3.builder.EqualsBuilder;

@Embeddable
public class Bedrag {

	@Column(name = "BEDRAG")
	private Double bedrag;

	protected Bedrag() {
	}

	public Bedrag(String bedrag) {
		this(Long.parseLong(bedrag));
	}

	public Bedrag(Long bedrag) {
		this(bedrag.doubleValue());
	}

	public Bedrag(Double bedrag) {
		this.bedrag = bedrag;
	}

	public Double getBedrag() {
		return bedrag;
	}

	public void setBedrag(Double bedrag) {
		this.bedrag = bedrag;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((bedrag == null) ? 0 : bedrag.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		Bedrag other = (Bedrag) obj;
		return new EqualsBuilder().append(bedrag, other.bedrag).isEquals();
	}
}
