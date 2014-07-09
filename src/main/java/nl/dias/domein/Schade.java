package nl.dias.domein;

import java.io.Serializable;
import java.util.Date;
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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import nl.dias.domein.polis.Polis;
import nl.lakedigital.hulpmiddelen.domein.PersistenceObject;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.hibernate.envers.Audited;

@Entity
@Table(name = "SCHADE")
@Audited
public class Schade implements PersistenceObject, Serializable {
    private static final long serialVersionUID = -8340805705038811388L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "POLIS", nullable = true)
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

    public Date getDatumTijdSchade() {
        return datumTijdSchade;
    }

    public void setDatumTijdSchade(Date datumTijdSchade) {
        this.datumTijdSchade = datumTijdSchade;
    }

    public Date getDatumTijdMelding() {
        return datumTijdMelding;
    }

    public void setDatumTijdMelding(Date datumTijdMelding) {
        this.datumTijdMelding = datumTijdMelding;
    }

    public Date getDatumAfgehandeld() {
        return datumAfgehandeld;
    }

    public void setDatumAfgehandeld(Date datumAfgehandeld) {
        this.datumAfgehandeld = datumAfgehandeld;
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
        return opmerkingen;
    }

    public void setOpmerkingen(Set<Opmerking> opmerkingen) {
        this.opmerkingen = opmerkingen;
    }

    /**
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return new ToStringBuilder(this).append("soortSchade", this.soortSchade).append("datumTijdMelding", this.datumTijdMelding).append("locatie", this.locatie).append("polis", this.polis.getId())
                .append("schadeNummerMaatschappij", this.schadeNummerMaatschappij).append("datumTijdSchade", this.datumTijdSchade).append("statusSchade", this.statusSchade).append("id", this.id)
                .append("soortSchadeOngedefinieerd", this.soortSchadeOngedefinieerd).append("opmerkingen", this.opmerkingen).append("schadeNummerTussenPersoon", this.schadeNummerTussenPersoon)
                .append("eigenRisico", this.eigenRisico).append("omschrijving", this.omschrijving).append("datumAfgehandeld", this.datumAfgehandeld).toString();
    }
}
