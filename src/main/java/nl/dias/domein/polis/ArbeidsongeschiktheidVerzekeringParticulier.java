package nl.dias.domein.polis;

import nl.dias.domein.polis.zakelijk.BedrijfsSchadeVerzekering;
import org.springframework.stereotype.Component;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

@Component
@Entity
@Table(name = "POLIS")
@DiscriminatorValue(value = "AG")
public class ArbeidsongeschiktheidVerzekeringParticulier extends Polis {
    @Override
    public SoortVerzekering getSoortVerzekering() {
        return SoortVerzekering.PARTICULIER;
    }

    @Override
    public String getSchermNaam() {
        return "Arbeidsongeschiktheid";
    }

    @Override
    public Polis nieuweInstantie() {
        return new BedrijfsSchadeVerzekering();
    }
}
