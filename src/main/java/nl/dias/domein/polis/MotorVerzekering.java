package nl.dias.domein.polis;

import org.springframework.stereotype.Component;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

@Component
@Entity
@Table(name = "POLIS")
@DiscriminatorValue(value = "M")
public class MotorVerzekering extends Polis {
    private static final long serialVersionUID = -7971692907347145987L;

    @Override
    public SoortVerzekering getSoortVerzekering() {
        return SoortVerzekering.PARTICULIER;
    }

    @Override
    public String getSchermNaam() {
        String pakket = this.getClass().getPackage().toString().replace("package ", "") + ".";
        return this.getClass().getCanonicalName().replace("Verzekering", "").replace(pakket, "");
    }
}
