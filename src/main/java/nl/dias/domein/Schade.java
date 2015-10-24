package nl.dias.domein;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.AttributeOverride;
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

import nl.dias.domein.polis.Polis;
import nl.lakedigital.hulpmiddelen.domein.PersistenceObject;

import org.apache.commons.lang.builder.CompareToBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;

@Entity
@Table(name = "SCHADE")
@NamedQueries({ @NamedQuery(name = "Schade.zoekOpschadeNummerMaatschappij", query = "select s from Schade s where s.schadeNummerMaatschappij = :schadeNummerMaatschappij"),
        @NamedQuery(name = "Schade.allesVanRelatie", query = "select s from Schade s where s.polis.relatie = :relatie") })
public class Schade implements Comparable, PersistenceObject, Serializable {
    private static final long serialVersionUID = -8340805705038811388L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @JoinColumn(name = "POLIS", nullable = true)
    @ManyToOne(cascade = { CascadeType.REFRESH, CascadeType.DETACH, CascadeType.MERGE, CascadeType.REMOVE }, fetch = FetchType.EAGER, optional = true, targetEntity = Polis.class)
    private Polis polis;

    @Column(length = 25, name = "SCHADENUMMERMAATSCHAPPIJ", nullable = false)
    private String schadeNummerMaatschappij;

    @Column(length = 25, name = "SCHADENUMMERTUSSENPERSOON")
    private String schadeNummerTussenPersoon;

    @JoinColumn(name = "SOORT", nullable = true)
    @ManyToOne
    private SoortSchade soortSchade;

    @Column(name = "SOORTSCHADEONGEDEFINIEERD", length = 100)
    private String soortSchadeOngedefinieerd;

    @Column(length = 50, name = "LOCATIE")
    private String locatie;

    @JoinColumn(name = "STATUS", nullable = false)
    @ManyToOne
    private StatusSchade statusSchade;

    @Column(name = "DATUMTIJD", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date datumTijdSchade;

    @Column(name = "DATUMTIJDMELDING", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date datumTijdMelding;

    @Column(name = "DATUMAFGEHANDELD")
    @Temporal(TemporalType.DATE)
    private Date datumAfgehandeld;

    @AttributeOverride(name = "bedrag", column = @Column(name = "EIGENRISICO"))
    private Bedrag eigenRisico;

    @Column(length = 1000, name = "OMSCHRIJVING")
    private String omschrijving;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "schade", orphanRemoval = true, targetEntity = Opmerking.class)
    private Set<Opmerking> opmerkingen;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "schade", orphanRemoval = true, targetEntity = Bijlage.class)
    private Set<Bijlage> bijlages;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public Polis getPolis() {
        return polis;
    }

    public void setPolis(Polis polis) {
        this.polis = polis;
    }

    public String getSchadeNummerMaatschappij() {
        return schadeNummerMaatschappij;
    }

    public void setSchadeNummerMaatschappij(String schadeNummerMaatschappij) {
        this.schadeNummerMaatschappij = schadeNummerMaatschappij;
    }

    public String getSchadeNummerTussenPersoon() {
        return schadeNummerTussenPersoon;
    }

    public void setSchadeNummerTussenPersoon(String schadeNummerTussenPersoon) {
        this.schadeNummerTussenPersoon = schadeNummerTussenPersoon;
    }

    public SoortSchade getSoortSchade() {
        return soortSchade;
    }

    public void setSoortSchade(SoortSchade soortSchade) {
        this.soortSchade = soortSchade;
    }

    public String getSoortSchadeOngedefinieerd() {
        return soortSchadeOngedefinieerd;
    }

    public void setSoortSchadeOngedefinieerd(String soortSchadeOngedefinieerd) {
        this.soortSchadeOngedefinieerd = soortSchadeOngedefinieerd;
    }

    public String getLocatie() {
        return locatie;
    }

    public void setLocatie(String locatie) {
        this.locatie = locatie;
    }

    public StatusSchade getStatusSchade() {
        return statusSchade;
    }

    public void setStatusSchade(StatusSchade statusSchade) {
        this.statusSchade = statusSchade;
    }

    public LocalDateTime getDatumTijdSchade() {
        return new LocalDateTime(datumTijdSchade);
    }

    public void setDatumTijdSchade(LocalDateTime datumTijdSchade) {
        this.datumTijdSchade = datumTijdSchade.toDate();
    }

    public LocalDateTime getDatumTijdMelding() {
        return new LocalDateTime(datumTijdMelding);
    }

    public void setDatumTijdMelding(LocalDateTime datumTijdMelding) {
        this.datumTijdMelding = datumTijdMelding.toDate();
    }

    public LocalDate getDatumAfgehandeld() {
        return new LocalDate(datumAfgehandeld);
    }

    public void setDatumAfgehandeld(LocalDate datumAfgehandeld) {
        this.datumAfgehandeld = datumAfgehandeld.toDate();
    }

    public Bedrag getEigenRisico() {
        return eigenRisico;
    }

    public void setEigenRisico(Bedrag eigenRisico) {
        this.eigenRisico = eigenRisico;
    }

    public String getOmschrijving() {
        return omschrijving;
    }

    public void setOmschrijving(String omschrijving) {
        this.omschrijving = omschrijving;
    }

    public Set<Opmerking> getOpmerkingen() {
        if (opmerkingen == null) {
            opmerkingen = new HashSet<Opmerking>();
        }
        return opmerkingen;
    }

    public void setOpmerkingen(Set<Opmerking> opmerkingen) {
        this.opmerkingen = opmerkingen;
    }

    public Set<Bijlage> getBijlages() {
        if (bijlages == null) {
            bijlages = new HashSet<Bijlage>();
        }
        return bijlages;
    }

    public void setBijlages(Set<Bijlage> bijlages) {
        this.bijlages = bijlages;
    }

    /**
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return new ToStringBuilder(this).append("soortSchade", this.soortSchade).append("datumTijdMelding", this.datumTijdMelding).append("locatie", this.locatie)
                .append("schadeNummerMaatschappij", this.schadeNummerMaatschappij).append("datumTijdSchade", this.datumTijdSchade).append("statusSchade", this.statusSchade).append("id", this.id)
                .append("soortSchadeOngedefinieerd", this.soortSchadeOngedefinieerd).append("opmerkingen", this.opmerkingen).append("schadeNummerTussenPersoon", this.schadeNummerTussenPersoon)
                .append("eigenRisico", this.eigenRisico).append("omschrijving", this.omschrijving).append("datumAfgehandeld", this.datumAfgehandeld).toString();
    }

    /**
     * @see java.lang.Comparable#compareTo(Object)
     */
    @Override
    public int compareTo(Object object) {
        Schade myClass = (Schade) object;
        return new CompareToBuilder().append(this.datumTijdMelding, myClass.datumTijdMelding).append(this.locatie, myClass.locatie)
                .append(this.schadeNummerMaatschappij, myClass.schadeNummerMaatschappij).append(this.datumTijdSchade, myClass.datumTijdSchade).append(this.statusSchade, myClass.statusSchade)
                .append(this.id, myClass.id).append(this.soortSchadeOngedefinieerd, myClass.soortSchadeOngedefinieerd)
                .append(this.schadeNummerTussenPersoon, myClass.schadeNummerTussenPersoon).append(this.eigenRisico, myClass.eigenRisico).append(this.omschrijving, myClass.omschrijving)
                .append(this.datumAfgehandeld, myClass.datumAfgehandeld).toComparison();
    }

    /**
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        return new HashCodeBuilder(761314323, 831310645).appendSuper(super.hashCode()).append(this.datumTijdMelding).append(this.locatie)
                .append(this.schadeNummerMaatschappij).append(this.datumTijdSchade).append(this.statusSchade).append(this.id).append(this.soortSchadeOngedefinieerd)
                .append(this.schadeNummerTussenPersoon).append(this.eigenRisico).append(this.omschrijving).append(this.datumAfgehandeld).toHashCode();
    }

    /**
     * @see java.lang.Object#equals(Object)
     */
    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Schade)) {
            return false;
        }
        Schade rhs = (Schade) object;
        return new EqualsBuilder().appendSuper(super.equals(object)).append(this.soortSchade, rhs.soortSchade).append(this.datumTijdMelding, rhs.datumTijdMelding).append(this.locatie, rhs.locatie)
                .append(this.polis, rhs.polis).append(this.schadeNummerMaatschappij, rhs.schadeNummerMaatschappij).append(this.datumTijdSchade, rhs.datumTijdSchade)
                .append(this.statusSchade, rhs.statusSchade).append(this.id, rhs.id).append(this.soortSchadeOngedefinieerd, rhs.soortSchadeOngedefinieerd).append(this.opmerkingen, rhs.opmerkingen)
                .append(this.bijlages, rhs.bijlages).append(this.schadeNummerTussenPersoon, rhs.schadeNummerTussenPersoon).append(this.eigenRisico, rhs.eigenRisico)
                .append(this.omschrijving, rhs.omschrijving).append(this.datumAfgehandeld, rhs.datumAfgehandeld).isEquals();
    }
}
