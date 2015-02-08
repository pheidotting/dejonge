package nl.dias.domein;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import nl.lakedigital.hulpmiddelen.domein.PersistenceObject;

import org.joda.time.LocalDate;

@Entity
@Table(name = "AANGIFTE")
@NamedQueries({ @NamedQuery(name = "Aangifte.openAangiftesBijRelatie", query = "select a from Aangifte a where a.datumAfgerond is null and a.relatie = :relatie"),
        @NamedQuery(name = "Aangifte.geslotenAangiftesBijRelatie", query = "select a from Aangifte a where a.datumAfgerond is not null and a.relatie = :relatie"),
        @NamedQuery(name = "Aangifte.alleAangiftesBijRelatie", query = "select a from Aangifte a where a.relatie = :relatie") })
public class Aangifte implements PersistenceObject, Serializable {
    private static final long serialVersionUID = 5438273206870999491L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(length = 4, name = "JAAR")
    private int jaar;

    @Column(name = "DATUMAFGEROND")
    @Temporal(TemporalType.DATE)
    private Date datumAfgerond;

    @Column(name = "UITSTELTOT")
    @Temporal(TemporalType.DATE)
    private Date uitstelTot;

    @JoinColumn(name = "AFGERONDDOOR")
    @ManyToOne(cascade = { CascadeType.REFRESH, CascadeType.DETACH, CascadeType.MERGE }, fetch = FetchType.EAGER, optional = true, targetEntity = Medewerker.class)
    private Medewerker afgerondDoor;

    @JoinColumn(name = "RELATIE")
    @ManyToOne(cascade = { CascadeType.REFRESH, CascadeType.DETACH, CascadeType.MERGE }, fetch = FetchType.EAGER, optional = true, targetEntity = Relatie.class)
    private Relatie relatie;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "aangifte", targetEntity = Bijlage.class)
    private Set<Bijlage> bijlages;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public int getJaar() {
        return jaar;
    }

    public void setJaar(int jaar) {
        this.jaar = jaar;
    }

    public LocalDate getDatumAfgerond() {
        return new LocalDate(datumAfgerond);
    }

    public void setDatumAfgerond(LocalDate datumAfgerond) {
        if (datumAfgerond == null) {
            this.datumAfgerond = null;
        } else {
            this.datumAfgerond = datumAfgerond.toDate();
        }
    }

    public LocalDate getUitstelTot() {
        return new LocalDate(uitstelTot);
    }

    public void setUitstelTot(LocalDate uitstelTot) {
        this.uitstelTot = uitstelTot.toDate();
    }

    public Relatie getRelatie() {
        return relatie;
    }

    public void setRelatie(Relatie relatie) {
        this.relatie = relatie;
    }

    public Medewerker getAfgerondDoor() {
        return afgerondDoor;
    }

    public void setAfgerondDoor(Medewerker afgerondDoor) {
        this.afgerondDoor = afgerondDoor;
    }

    public Set<Bijlage> getBijlages() {
        return bijlages;
    }

    public void setBijlages(Set<Bijlage> bijlages) {
        this.bijlages = bijlages;
    }

    public void afhandelen(Medewerker medewerker) {
        setAfgerondDoor(medewerker);
        setDatumAfgerond(LocalDate.now());
    }

    public void reset() {
        setAfgerondDoor(null);
        setDatumAfgerond(null);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + jaar;
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
        Aangifte other = (Aangifte) obj;
        if (jaar != other.jaar) {
            return false;
        }
        return true;
    }
}
