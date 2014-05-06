package nl.dias.dias_web.domein.polis;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

import nl.dias.dias_web.domein.polis.Polis;
import nl.dias.dias_web.domein.polis.SoortVerzekering;

@Entity
@Table(name = "POLIS")
@DiscriminatorValue(value = "BS")
public class BromSnorfietsVerzekering extends Polis {
	@Override
	public SoortVerzekering getSoortVerzekering() {
		return SoortVerzekering.PARTICULIER;
	}

}
