package nl.dias.domein;

import nl.dias.domein.json.predicates.AdresPredicate;
import nl.lakedigital.hulpmiddelen.domein.PersistenceObject;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.joda.time.LocalDate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.*;

import static com.google.common.collect.Iterables.filter;
import static com.google.common.collect.Iterables.getFirst;

@Entity
@Table(name = "KANTOOR")
public class Kantoor implements Serializable, PersistenceObject {
    private static final long serialVersionUID = 3842257675777516787L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "NAAM")
    private String naam;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, targetEntity = Adres.class, mappedBy = "kantoor")
    private Set<Adres> adressen;

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

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "kantoor", targetEntity = Relatie.class)
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

    public Set<Adres> getAdressen() {
        if (adressen == null) {
            adressen = new HashSet<>();
        }
        return adressen;
    }

    public void setAdressen(Set<Adres> adressen) {
        this.adressen = adressen;
    }

    public Adres getPostAdres() {
        return getFirst(filter(adressen, new AdresPredicate(Adres.SoortAdres.POSTADRES)), null);
    }

    public Adres getFactuurAdres() {
        return getFirst(filter(adressen, new AdresPredicate(Adres.SoortAdres.FACTUURADRES)), null);
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
        builder.append(adressen);
        builder.append(btwNummer);
        builder.append(datumOprichting);
        builder.append(emailadres);
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
        return new EqualsBuilder().append(adressen, other.adressen).append(btwNummer, other.btwNummer).append(datumOprichting, other.datumOprichting).append(emailadres, other.emailadres).append(id, other.id).append(kvk, other.kvk).append(naam, other.naam).append(rechtsvorm, other.rechtsvorm).append(opmerkingen, other.opmerkingen).append(soortKantoor, other.soortKantoor).append(medewerkers, other.medewerkers).append(rekeningnummers, other.rekeningnummers).append(relaties, other.relaties).isEquals();
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Kantoor [id=");
        builder.append(id);
        builder.append(", naam=");
        builder.append(naam);
        builder.append(", adressen=");
        builder.append(adressen);
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
