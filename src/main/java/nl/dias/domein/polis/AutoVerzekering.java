package nl.dias.domein.polis;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "POLIS")
@DiscriminatorValue(value = "A")
public class AutoVerzekering extends Polis {

    private static final long serialVersionUID = 4412157021471248581L;

    @Override
    public SoortVerzekering getSoortVerzekering() {
        return SoortVerzekering.PARTICULIER;
    }

    // @Override
    // public String toString() {
    // StringBuilder builder = new StringBuilder();
    // builder.append("\nAutoVerzekering [getId()=");
    // builder.append(getId());
    // builder.append(", getPolisNummer()=");
    // builder.append(getPolisNummer());
    // builder.append(", getIngangsDatum()=");
    // builder.append(getIngangsDatum());
    // builder.append(", getPremie()=");
    // builder.append(getPremie());
    // builder.append(", getBijlages()=");
    // builder.append(getBijlages());
    // builder.append(", getOpmerkingen()=");
    // builder.append(getOpmerkingen());
    // builder.append(", getMaatschappij()=");
    // builder.append(getMaatschappij());
    // builder.append(", getWijzigingsDatum()=");
    // builder.append(getWijzigingsDatum());
    // builder.append(", getProlongatieDatum()=");
    // builder.append(getProlongatieDatum());
    // builder.append(", getBetaalfrequentie()=");
    // builder.append(getBetaalfrequentie());
    // builder.append(", getBedrijf()=");
    // builder.append(getBedrijf());
    // builder.append(", getSchades()=");
    // builder.append(getSchades());
    // builder.append(", hashCode()=");
    // builder.append(hashCode());
    // builder.append("]");
    // return builder.toString();
    // }

}
