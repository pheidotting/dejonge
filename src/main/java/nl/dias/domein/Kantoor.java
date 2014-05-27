package nl.dias.domein;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import nl.lakedigital.hulpmiddelen.domein.PersistenceObject;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.hibernate.envers.Audited;
import org.joda.time.LocalDate;

@Entity
@Table(name = "KANTOOR")
@Audited
public class Kantoor implements Serializable, PersistenceObject {
    private static final long serialVersionUID = 3842257675777516787L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "NAAM")
    private String naam;

    private Adres adres;

    @AttributeOverrides({ @AttributeOverride(name = "straat", column = @Column(name = "STRAAT_FACTUUR")), @AttributeOverride(name = "huisnummer", column = @Column(name = "HUISNUMMER_FACTUUR")),
            @AttributeOverride(name = "toevoeging", column = @Column(name = "TOEVOEGING_FACTUUR")), @AttributeOverride(name = "postcode", column = @Column(name = "POSTCODE_FACTUUR")),
            @AttributeOverride(name = "plaats", column = @Column(name = "PLAATS_FACTUUR")) })
    private Adres factuurAdres;

    @Column(name = "KVK", length = 8)
    private Long kvk;

    @Column(name = "BTW_NUMMER")
    private String btwNummer;

    @Column(name = "DATUMOPRICHTING")
    @Temporal(TemporalType.DATE)
    private Date datumOprichting;

    @Column(name = "RECHTSVORM", length = 4)
    @Enumerated(EnumType.STRING)
    private Rechtsvorm rechtsvorm;

    @Column(name = "SOORTKANTOOR", length = 3)
    @Enumerated(EnumType.STRING)
    private SoortKantoor soortKantoor;

    @Column(name = "EMAILADRES")
    private String emailadres;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "kantoor", targetEntity = Medewerker.class)
    private List<Medewerker> medewerkers;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "kantoor", targetEntity = Relatie.class)
    private Set<Relatie> relaties;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "kantoor", targetEntity = Opmerking.class)
    private Set<Opmerking> opmerkingen;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, targetEntity = RekeningNummer.class, mappedBy = "kantoor")
    private Set<RekeningNummer> rekeningnummers;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public String getNaam() {
        return naam;
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }

    public Adres getAdres() {
        if (adres == null) {
            adres = new Adres();
        }
        return adres;
    }

    public void setAdres(Adres adres) {
        this.adres = adres;
    }

    public Adres getFactuurAdres() {
        if (factuurAdres == null) {
            factuurAdres = new Adres();
        }
        return factuurAdres;
    }

    public void setFactuurAdres(Adres factuurAdres) {
        this.factuurAdres = factuurAdres;
    }

    public List<Medewerker> getMedewerkers() {
        if (medewerkers == null) {
            medewerkers = new ArrayList<>();
        }
        return medewerkers;
    }

    public void setMedewerkers(List<Medewerker> medewerkers) {
        this.medewerkers = medewerkers;
    }

    public Set<Relatie> getRelaties() {
        if (relaties == null) {
            relaties = new HashSet<>();
        }
        return relaties;
    }

    public void setRelaties(Set<Relatie> relaties) {
        this.relaties = relaties;
    }

    public Set<Opmerking> getOpmerkingen() {
        if (opmerkingen == null) {
            opmerkingen = new HashSet<>();
        }
        return opmerkingen;
    }

    public void setOpmerkingen(Set<Opmerking> opmerkingen) {
        this.opmerkingen = opmerkingen;
    }

    public Long getKvk() {
        return kvk;
    }

    public void setKvk(Long kvk) {
        this.kvk = kvk;
    }

    public String getBtwNummer() {
        return btwNummer;
    }

    public void setBtwNummer(String btwNummer) {
        this.btwNummer = btwNummer;
    }

    public LocalDate getDatumOprichting() {
        return new LocalDate(datumOprichting);
    }

    public void setDatumOprichting(LocalDate datumOprichting) {
        this.datumOprichting = datumOprichting.toDateMidnight().toDate();
    }

    public Rechtsvorm getRechtsvorm() {
        return rechtsvorm;
    }

    public void setRechtsvorm(Rechtsvorm rechtsvorm) {
        this.rechtsvorm = rechtsvorm;
    }

    public SoortKantoor getSoortKantoor() {
        return soortKantoor;
    }

    public void setSoortKantoor(SoortKantoor soortKantoor) {
        this.soortKantoor = soortKantoor;
    }

    public String getEmailadres() {
        return emailadres;
    }

    public void setEmailadres(String emailadres) {
        this.emailadres = emailadres;
    }

    public Set<RekeningNummer> getRekeningnummers() {
        if (rekeningnummers == null) {
            rekeningnummers = new HashSet<>();
        }
        return rekeningnummers;
    }

    public void setRekeningnummers(Set<RekeningNummer> rekeningnummers) {
        this.rekeningnummers = rekeningnummers;
    }

    @Override
    public int hashCode() {
        HashCodeBuilder builder = new HashCodeBuilder();
        builder.append(adres);
        builder.append(btwNummer);
        builder.append(datumOprichting);
        builder.append(emailadres);
        builder.append(factuurAdres);
        builder.append(id);
        builder.append(kvk);
        builder.append(medewerkers);
        builder.append(naam);
        builder.append(opmerkingen);
        builder.append(rechtsvorm);
        builder.append(rekeningnummers);
        builder.append(soortKantoor);

        return builder.toHashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        Kantoor other = (Kantoor) obj;
        return new EqualsBuilder().append(adres, other.adres).append(btwNummer, other.btwNummer).append(datumOprichting, other.datumOprichting).append(emailadres, other.emailadres)
                .append(factuurAdres, other.factuurAdres).append(id, other.id).append(kvk, other.kvk).append(naam, other.naam).append(rechtsvorm, other.rechtsvorm)
                .append(opmerkingen, other.opmerkingen).append(soortKantoor, other.soortKantoor).append(medewerkers, other.medewerkers).append(rekeningnummers, other.rekeningnummers)
                .append(relaties, other.relaties).isEquals();
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Kantoor [id=");
        builder.append(id);
        builder.append(", naam=");
        builder.append(naam);
        builder.append(", adres=");
        builder.append(adres);
        builder.append(", factuurAdres=");
        builder.append(factuurAdres);
        builder.append(", kvk=");
        builder.append(kvk);
        builder.append(", btwNummer=");
        builder.append(btwNummer);
        builder.append(", datumOprichting=");
        builder.append(datumOprichting);
        builder.append(", rechtsvorm=");
        builder.append(rechtsvorm);
        builder.append(", soortKantoor=");
        builder.append(soortKantoor);
        builder.append(", emailadres=");
        builder.append(emailadres);
        builder.append(", medewerkers=");
        builder.append(medewerkers);
        builder.append(", relaties=");
        builder.append(relaties);
        builder.append(", opmerkingen=");
        builder.append(opmerkingen);
        builder.append(", rekeningnummers=");
        builder.append(rekeningnummers);
        builder.append("]");
        return builder.toString();
    }

}
