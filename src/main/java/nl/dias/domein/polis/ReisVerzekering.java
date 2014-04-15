package nl.dias.domein.polis;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "POLIS")
@DiscriminatorValue(value = "R")
public class ReisVerzekering extends Polis {
	private static final long serialVersionUID = 5435705210054090729L;

	@Override
	public SoortVerzekering getSoortVerzekering() {
		return SoortVerzekering.PARTICULIER;
	}

}
