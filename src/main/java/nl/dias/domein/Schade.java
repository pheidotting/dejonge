package nl.dias.domein;

import java.util.Date;
import java.util.Set;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
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

import org.hibernate.envers.Audited;
import org.joda.time.LocalDate;

@Entity
@Table(name = "SCHADE")
@Audited
public class Schade implements PersistenceObject {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @JoinColumn(name = "POLIS")
    @ManyToOne(optional = false)
    private Polis polis;

    @Column(length = 25, name = "SCHADENUMMERMAATSCHAPPIJ", nullable = false)
    private String schadeNummerMaatschappij;

    @Column(length = 25, name = "SCHADENUMMERTUSSENPERSOON")
    private String schadeNummerTussenPersoon;

    @JoinColumn(name = "SOORT")
    @ManyToOne(optional = true)
    private SoortSchade soortSchade;

    @Column(name = "SOORTSCHADEONGEDEFINIEERD", length = 100)
    private String soortSchadeOngedefinieerd;

    @Column(length = 50, name = "LOCATIE")
    private String locatie;

    @JoinColumn(name = "STATUS")
    @ManyToOne(optional = false)
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

    @OneToMany(mappedBy = "schade")
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

    public LocalDate getDatumTijdSchade() {
        return new LocalDate(datumTijdSchade);
    }

    public void setDatumTijdSchade(LocalDate datumTijdSchade) {
        this.datumTijdSchade = datumTijdSchade.toDate();
    }

    public LocalDate getDatumTijdMelding() {
        return new LocalDate(datumTijdMelding);
    }

    public void setDatumTijdMelding(LocalDate datumTijdMelding) {
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
        return opmerkingen;
    }

    public void setOpmerkingen(Set<Opmerking> opmerkingen) {
        this.opmerkingen = opmerkingen;
    }

}
