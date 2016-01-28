package nl.dias.domein;

import nl.lakedigital.hulpmiddelen.domein.PersistenceObject;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "TELEFOONNUMMER")
@NamedQueries({@NamedQuery(name = "Telefoonnummer.verwijderTelefoonnummersBijRelatie", query = "delete from Telefoonnummer a where a.relatie = :relatie")})
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
    @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER, optional = false, targetEntity = Relatie.class)
    @JoinColumn(name = "RELATIE")
    private Relatie relatie;
    @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER, optional = false, targetEntity = Bedrijf.class)
    @JoinColumn(name = "BEDRIJF")
    private Bedrijf bedrijf;
    @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER, optional = false, targetEntity = ContactPersoon.class)
    @JoinColumn(name = "CONTACTPERSOON")
    private ContactPersoon contactPersoon;

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

    public Relatie getRelatie() {
        return relatie;
    }

    public void setRelatie(Relatie relatie) {
        this.relatie = relatie;
    }

    public String getOmschrijving() {
        return omschrijving;
    }

    public void setOmschrijving(String omschrijving) {
        this.omschrijving = omschrijving;
    }

    public ContactPersoon getContactPersoon() {
        return contactPersoon;
    }

    public void setContactPersoon(ContactPersoon contactPersoon) {
        this.contactPersoon = contactPersoon;
    }

    public Bedrijf getBedrijf() {
        return bedrijf;
    }

    public void setBedrijf(Bedrijf bedrijf) {
        this.bedrijf = bedrijf;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(id).append(relatie).append(soort).append(telefoonnummer).append(omschrijving).toHashCode();
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

        return new EqualsBuilder().append(id, other.id).append(relatie, other.relatie).append(soort, other.soort).append(telefoonnummer, other.telefoonnummer).append(omschrijving, other.omschrijving).isEquals();
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