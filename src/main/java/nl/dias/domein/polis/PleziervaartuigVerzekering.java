package nl.dias.domein.polis;

import org.springframework.stereotype.Component;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

@Component
@Entity
@Table(name = "POLIS")
@DiscriminatorValue(value = "PV")
public class PleziervaartuigVerzekering extends Polis {
    @Override
    public SoortVerzekering getSoortVerzekering() {
        return SoortVerzekering.PARTICULIER;
    }

    @Override
    public String getSchermNaam() {
        String pakket = this.getClass().getPackage().toString().replace("package ", "") + ".";
        return this.getClass().getCanonicalName().replace("Verzekering", "").replace(pakket, "");
    }

    @Override
    public PleziervaartuigVerzekering nieuweInstantie() {
        return new PleziervaartuigVerzekering();
    }
}
