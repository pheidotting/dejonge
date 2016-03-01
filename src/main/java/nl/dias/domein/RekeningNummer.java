package nl.dias.domein;

import nl.dias.web.SoortEntiteit;
import nl.lakedigital.hulpmiddelen.domein.PersistenceObject;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "REKENINGNUMMER")
@NamedQueries({//
        @NamedQuery(name = "RekeningNummer.zoekRekeningNummersBijEntiteit", query = "select r from RekeningNummer r where r.soortEntiteit = :soortEntiteit and r.entiteitId = :entiteitId"),//
})
public class RekeningNummer implements Serializable, PersistenceObject {
    private static final long serialVersionUID = 6164849876034232194L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;
    @Column(name = "BIC")
    private String bic;
    @Column(name = "REKENINGNUMMER")
    private String rekeningnummer;
    @Enumerated(EnumType.STRING)
    @Column(length = 50, name = "SOORTENTITEIT")
    private SoortEntiteit soortEntiteit;
    @Column(name = "ENTITEITID")
    private Long entiteitId;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public String getBic() {
        return bic;
    }

    public void setBic(String bic) {
        this.bic = bic;
    }

    public String getRekeningnummer() {
        return rekeningnummer;
    }

    public void setRekeningnummer(String rekeningnummer) {
        this.rekeningnummer = rekeningnummer;
    }

    public SoortEntiteit getSoortEntiteit() {
        return soortEntiteit;
    }

    public void setSoortEntiteit(SoortEntiteit soortEntiteit) {
        this.soortEntiteit = soortEntiteit;
    }

    public Long getEntiteitId() {
        return entiteitId;
    }

    public void setEntiteitId(Long entiteitId) {
        this.entiteitId = entiteitId;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(bic).append(id).append(rekeningnummer).toHashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        RekeningNummer other = (RekeningNummer) obj;

        return new EqualsBuilder().append(bic, other.bic).append(id, other.id).append(rekeningnummer, other.rekeningnummer).isEquals();
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("RekeningNummer [id=").append(id);
        builder.append(", bic=").append(bic);
        builder.append(", rekeningnummer=").append(rekeningnummer);
        builder.append("]");
        return builder.toString();
    }
}
