package nl.dias.domein.polis;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "POLIS")
@DiscriminatorValue(value = "SV")
public class SviSchadeverzekerininzittende extends Polis{
    @Override
    public SoortVerzekering getSoortVerzekering() {
        return SoortVerzekering.PARTICULIER;
    }
}
