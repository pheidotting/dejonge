package nl.dias.domein;

import nl.dias.web.SoortEntiteit;
import nl.lakedigital.hulpmiddelen.domein.PersistenceObject;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "TELEFOONNUMMER")
@NamedQueries({//
        @NamedQuery(name = "Telefoonnummer.zoekTelefoonnummersBijEntiteit", query = "select t from Telefoonnummer t where t.soortEntiteit = :soortEntiteit and t.entiteitId = :entiteitId"),//
})
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
    @Column(name = "OMSCHRIJVING", columnDefinition = "varchar(2500)")
    private String omschrijving;
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

    public String getOmschrijving() {
        return omschrijving;
    }

    public void setOmschrijving(String omschrijving) {
        this.omschrijving = omschrijving;
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
        return new HashCodeBuilder().append(id).append(soort).append(telefoonnummer).append(omschrijving).toHashCode();
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

        return new EqualsBuilder().append(id, other.id).append(soort, other.soort).append(telefoonnummer, other.telefoonnummer).append(omschrijving, other.omschrijving).isEquals();
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
        builder.append(", omschrijving=");
        builder.append(omschrijving);
        builder.append("]");
        return builder.toString();
    }
}