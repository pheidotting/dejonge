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

import org.hibernate.envers.Audited;

@Entity
@Table(name = "REKENINGNUMMER")
@Audited
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

    public Long getId() {
        return id;
    }

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
        final int prime = 31;
        int result = 1;
        result = prime * result + ((bic == null) ? 0 : bic.hashCode());
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((kantoor == null) ? 0 : kantoor.hashCode());
        result = prime * result + ((rekeningnummer == null) ? 0 : rekeningnummer.hashCode());
        result = prime * result + ((relatie == null) ? 0 : relatie.hashCode());
        return result;
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
        if (bic == null) {
            if (other.bic != null) {
                return false;
            }
        } else if (!bic.equals(other.bic)) {
            return false;
        }
        if (id == null) {
            if (other.id != null) {
                return false;
            }
        } else if (!id.equals(other.id)) {
            return false;
        }
        if (kantoor == null) {
            if (other.kantoor != null) {
                return false;
            }
        } else if (!kantoor.equals(other.kantoor)) {
            return false;
        }
        if (rekeningnummer == null) {
            if (other.rekeningnummer != null) {
                return false;
            }
        } else if (!rekeningnummer.equals(other.rekeningnummer)) {
            return false;
        }
        if (relatie == null) {
            if (other.relatie != null) {
                return false;
            }
        } else if (!relatie.equals(other.relatie)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("RekeningNummer [id=");
        builder.append(id);
        builder.append(", bic=");
        builder.append(bic);
        builder.append(", rekeningnummer=");
        builder.append(rekeningnummer);
        builder.append("]");
        return builder.toString();
    }
}
