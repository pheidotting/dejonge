package nl.dias.domein;

import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
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

import nl.lakedigital.hulpmiddelen.domein.PersistenceObject;

@Entity
@Table(name = "TAAK")
@DiscriminatorColumn(name = "SOORT", length = 1)
public abstract class Taak implements PersistenceObject {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;
    @Column(name = "DATUMTIJDCREATIE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date datumTijdCreatie;
    @Column(name = "DATUMTIJDOPPAKKEN")
    @Temporal(TemporalType.TIMESTAMP)
    private Date datumTijdOppakken;
    @Column(name = "DATUMTIJDAFRONDEN")
    @Temporal(TemporalType.TIMESTAMP)
    private Date datumTijdAfgerond;
    @Column(name = "EINDDATUM")
    @Temporal(TemporalType.DATE)
    private Date eindDatum;
    @JoinColumn(name = "AANGEMAAKTDOOR")
    @ManyToOne(cascade = { CascadeType.REFRESH, CascadeType.DETACH, CascadeType.MERGE }, fetch = FetchType.EAGER, optional = true, targetEntity = Medewerker.class)
    private Medewerker aangemaaktDoor;
    @JoinColumn(name = "OPGEPAKTDOOR")
    @ManyToOne(cascade = { CascadeType.REFRESH, CascadeType.DETACH, CascadeType.MERGE }, fetch = FetchType.EAGER, optional = true, targetEntity = Medewerker.class)
    private Medewerker opgepaktDoor;
    @JoinColumn(name = "GERELATEERDAAN")
    @ManyToOne(cascade = { CascadeType.REFRESH, CascadeType.DETACH, CascadeType.MERGE }, fetch = FetchType.EAGER, optional = true, targetEntity = Relatie.class)
    private Relatie gerelateerdAan;
    @Column(name = "OMSCHRIJVING", length = 2500)
    private String omschrijving;
    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS", length = 1)
    private TaakStatus status;
    @JoinColumn(name = "GERELATEERDETAAK")
    @ManyToOne(cascade = { CascadeType.REFRESH, CascadeType.DETACH, CascadeType.MERGE }, fetch = FetchType.EAGER, optional = true, targetEntity = Taak.class)
    private Taak gerelateerdeTaak;
    @OneToMany(targetEntity = TaakRelaties.class, mappedBy = "taak", fetch = FetchType.EAGER)
    private Set<TaakRelaties> taakRelaties;

    public Taak() {
        status = TaakStatus.O;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public Date getDatumTijdCreatie() {
        return datumTijdCreatie;
    }

    public void setDatumTijdCreatie(Date datumTijdCreatie) {
        this.datumTijdCreatie = datumTijdCreatie;
    }

    public Date getDatumTijdOppakken() {
        return datumTijdOppakken;
    }

    public void setDatumTijdOppakken(Date datumTijdOppakken) {
        this.datumTijdOppakken = datumTijdOppakken;
    }

    public Date getDatumTijdAfgerond() {
        return datumTijdAfgerond;
    }

    public void setDatumTijdAfgerond(Date datumTijdAfgerond) {
        this.datumTijdAfgerond = datumTijdAfgerond;
    }

    public Date getEindDatum() {
        return eindDatum;
    }

    public void setEindDatum(Date eindDatum) {
        this.eindDatum = eindDatum;
    }

    public Medewerker getAangemaaktDoor() {
        return aangemaaktDoor;
    }

    public void setAangemaaktDoor(Medewerker aangemaaktDoor) {
        this.aangemaaktDoor = aangemaaktDoor;
    }

    public Medewerker getOpgepaktDoor() {
        return opgepaktDoor;
    }

    public void setOpgepaktDoor(Medewerker opgepaktDoor) {
        this.opgepaktDoor = opgepaktDoor;
    }

    public Relatie getGerelateerdAan() {
        return gerelateerdAan;
    }

    public void setGerelateerdAan(Relatie gerelateerdAan) {
        this.gerelateerdAan = gerelateerdAan;
    }

    public String getOmschrijving() {
        return omschrijving;
    }

    public void setOmschrijving(String omschrijving) {
        this.omschrijving = omschrijving;
    }

    public TaakStatus getStatus() {
        return status;
    }

    public void setStatus(TaakStatus status) {
        this.status = status;
    }

    public Taak getGerelateerdeTaak() {
        return gerelateerdeTaak;
    }

    public void setGerelateerdeTaak(Taak gerelateerdeTaak) {
        this.gerelateerdeTaak = gerelateerdeTaak;
    }

}
