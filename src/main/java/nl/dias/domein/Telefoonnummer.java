package nl.dias.domein;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
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
import org.hibernate.envers.Audited;

@Entity
@Table(name = "TELEFOONNUMMER")
@Audited
public class Telefoonnummer implements Serializable, PersistenceObject {
    private static final long serialVersionUID = -8991287195295458633L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;
    @Column(length = 10, name = "TELEFOONNUMMER")
    private String telefoonnummer;
    @Column(name = "TELEFOONNUMMERSOORT")
    @Enumerated(EnumType.STRING)
    private TelefoonnummerSoort soort;
    @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER, optional = false, targetEntity = Relatie.class)
    @JoinColumn(name = "RELATIE")
    private Relatie relatie;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTelefoonnummer() {
        return telefoonnummer;
    }

    public void setTelefoonnummer(String telefoonnummer) {
        this.telefoonnummer = telefoonnummer;
    }

    public TelefoonnummerSoort getSoort() {
        return soort;
    }

    public void setSoort(TelefoonnummerSoort soort) {
        this.soort = soort;
    }

    public Relatie getRelatie() {
        return relatie;
    }

    public void setRelatie(Relatie relatie) {
        this.relatie = relatie;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(id).append(relatie).append(soort).append(telefoonnummer).toHashCode();
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
        Telefoonnummer other = (Telefoonnummer) obj;

        return new EqualsBuilder().append(id, other.id).append(relatie, other.relatie).append(soort, other.soort).append(telefoonnummer, other.telefoonnummer).isEquals();
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Telefoonnummer [id=");
        builder.append(id);
        builder.append(", telefoonnummer=");
        builder.append(telefoonnummer);
        builder.append(", soort=");
        builder.append(soort);
        builder.append("]");
        return builder.toString();
    }
}