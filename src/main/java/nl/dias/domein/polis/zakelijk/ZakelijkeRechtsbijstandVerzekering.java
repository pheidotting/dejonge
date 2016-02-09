package nl.dias.domein.polis.zakelijk;

import nl.dias.domein.polis.Polis;
import nl.dias.domein.polis.SoortVerzekering;
import org.springframework.stereotype.Component;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

@Component
@Entity
@Table(name = "POLIS")
@DiscriminatorValue(value = "RS")
public class ZakelijkeRechtsbijstandVerzekering extends Polis {
    @Override
    public SoortVerzekering getSoortVerzekering() {
        return SoortVerzekering.ZAKELIJK;
    }

    @Override
    public String getSchermNaam() {
        return "Rechtsbijstand";
    }

    @Override
    public Polis nieuweInstantie() {
        return new ZakelijkeRechtsbijstandVerzekering();
    }
}
