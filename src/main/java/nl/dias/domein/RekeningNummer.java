package nl.dias.domein;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import nl.lakedigital.hulpmiddelen.domein.PersistenceObject;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

@Entity
@Table(name = "REKENINGNUMMER")
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

    @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER, optional = true, targetEntity = Relatie.class)
    @JoinColumn(name = "RELATIE")
    private Relatie relatie;

    @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER, optional = true, targetEntity = Kantoor.class)
    @JoinColumn(name = "KANTOOR")
    private Kantoor kantoor;

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

    public Relatie getRelatie() {
        return relatie;
    }

    public void setRelatie(Relatie relatie) {
        this.relatie = relatie;
    }

    public Kantoor getKantoor() {
        return kantoor;
    }

    public void setKantoor(Kantoor kantoor) {
        this.kantoor = kantoor;
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

        return new EqualsBuilder().append(bic, other.bic).append(id, other.id).append(kantoor, other.kantoor).append(rekeningnummer, other.rekeningnummer).append(relatie, other.relatie).isEquals();
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
