package nl.dias.domein.polis;

import nl.dias.domein.*;
import nl.lakedigital.hulpmiddelen.domein.PersistenceObject;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.joda.time.LocalDate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "POLIS")
@DiscriminatorColumn(name = "SOORT", length = 2)
@NamedQueries({@NamedQuery(name = "Polis.allesBijMaatschappij", query = "select p from Polis p where p.maatschappij = :maatschappij"), @NamedQuery(name = "Polis.zoekOpPolisNummer", query = "select p from Polis p where p.polisNummer = :polisNummer and p.relatie.kantoor = :kantoor"), @NamedQuery(name = "Polis.allesVanRelatie", query = "select p from Polis p where p.relatie = :relatie")})
public abstract class Polis implements PersistenceObject, Serializable, Cloneable {
    private static final long serialVersionUID = 1011438129295546984L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "STATUS", length = 3)
    @Enumerated(EnumType.STRING)
    private StatusPolis status;

    @Column(name = "POLISNUMMER", length = 25)
    private String polisNummer;

    @Column(name = "KENMERK", length = 100)
    private String kenmerk;

    @Column(name = "INGANGSDATUM")
    @Temporal(TemporalType.DATE)
    private Date ingangsDatum;

    @Column(name = "EINDDATUM")
    @Temporal(TemporalType.DATE)
    private Date eindDatum;

    @AttributeOverride(name = "bedrag", column = @Column(name = "PREMIE"))
    private Bedrag premie;

    @Temporal(TemporalType.DATE)
    @Column(name = "WIJZIGINGSDATUM")
    private Date wijzigingsDatum;

    @Temporal(TemporalType.DATE)
    @Column(name = "PROLONGATIEDATUM")
    private Date prolongatieDatum;

    @Enumerated(EnumType.STRING)
    @Column(length = 1, name = "BETAALFREQUENTIE")
    private Betaalfrequentie betaalfrequentie;

    @JoinColumn(name = "RELATIE")
    @ManyToOne(cascade = {CascadeType.REFRESH, CascadeType.DETACH, CascadeType.MERGE}, fetch = FetchType.EAGER, optional = true, targetEntity = Relatie.class)
    private Relatie relatie;

    @JoinColumn(name = "BEDRIJF")
    @ManyToOne(cascade = {CascadeType.REFRESH, CascadeType.DETACH, CascadeType.MERGE}, fetch = FetchType.EAGER, optional = true, targetEntity = Bedrijf.class)
    private Bedrijf bedrijf;

    @OneToMany(cascade = {CascadeType.ALL}, fetch = FetchType.EAGER, mappedBy = "polis", orphanRemoval = true, targetEntity = Bijlage.class)
    private Set<Bijlage> bijlages;

    @OneToMany(cascade = {CascadeType.REFRESH, CascadeType.DETACH, CascadeType.MERGE}, fetch = FetchType.EAGER, mappedBy = "polis", orphanRemoval = true, targetEntity = Opmerking.class)
    private Set<Opmerking> opmerkingen;

//    @ManyToOne(cascade = {CascadeType.REFRESH}, fetch = FetchType.EAGER, optional = false, targetEntity = VerzekeringsMaatschappij.class)
//    @JoinColumn(name = "MAATSCHAPPIJ")
    @Column(name = "MAATSCHAPPIJ")
    private Long maatschappij;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "polis", orphanRemoval = true, targetEntity = Schade.class)
    private Set<Schade> schades;

    @Column(name = "OMSCHRIJVING", columnDefinition = "varchar(2500)")
    private String omschrijvingVerzekering;

    public abstract SoortVerzekering getSoortVerzekering();

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public String getPolisNummer() {
        return polisNummer;
    }

    public String getKenmerk() {
        return kenmerk;
    }

    public void setKenmerk(String kenmerk) {
        this.kenmerk = kenmerk;
    }

    public StatusPolis getStatus() {
        return status;
    }

    public void setStatus(StatusPolis status) {
        this.status = status;
    }

    public void setPolisNummer(String polisNummer) {
        this.polisNummer = polisNummer;
    }

    public LocalDate getIngangsDatum() {
        if (ingangsDatum == null) {
            return null;
        }
        return new LocalDate(ingangsDatum);
    }

    public void setIngangsDatum(LocalDate ingangsDatum) {
        if (ingangsDatum == null) {
            this.ingangsDatum = null;
        } else {
            this.ingangsDatum = ingangsDatum.toDateMidnight().toDate();
        }
    }

    public LocalDate getEindDatum() {
        if (eindDatum == null) {
            return null;
        }
        return new LocalDate(eindDatum);
    }

    public void setEindDatum(LocalDate eindDatum) {
        if (eindDatum == null) {
            eindDatum = null;
        }
        if (eindDatum != null) {
            this.eindDatum = eindDatum.toDate();
        }
    }

    public Bedrag getPremie() {
        return premie;
    }

    public void setPremie(Bedrag premie) {
        this.premie = premie;
    }

    public Relatie getRelatie() {
        return relatie;
    }

    public void setRelatie(Relatie relatie) {
        this.relatie = relatie;
    }

    public Set<Bijlage> getBijlages() {
        if (bijlages == null) {
            bijlages = new HashSet<>();
        }
        return bijlages;
    }

    public void setBijlages(Set<Bijlage> bijlages) {
        this.bijlages = bijlages;
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

    public Long getMaatschappij() {
        return maatschappij;
    }

    public void setMaatschappij(Long maatschappij) {
        this.maatschappij = maatschappij;
    }

    public LocalDate getWijzigingsDatum() {
        if (wijzigingsDatum == null) {
            return null;
        }
        return new LocalDate(wijzigingsDatum);
    }

    public void setWijzigingsDatum(LocalDate wijzigingsDatum) {
        if (wijzigingsDatum == null) {
            this.wijzigingsDatum = null;
        } else {
            this.wijzigingsDatum = wijzigingsDatum.toDateMidnight().toDate();
        }
    }

    public LocalDate getProlongatieDatum() {
        if (prolongatieDatum == null) {
            return null;
        }
        return new LocalDate(prolongatieDatum);
    }

    public void setProlongatieDatum(LocalDate prolongatieDatum) {
        if (prolongatieDatum == null) {
            this.prolongatieDatum = null;
        } else {
            this.prolongatieDatum = prolongatieDatum.toDateMidnight().toDate();
        }
    }

    public Betaalfrequentie getBetaalfrequentie() {
        return betaalfrequentie;
    }

    public void setBetaalfrequentie(Betaalfrequentie betaalfrequentie) {
        this.betaalfrequentie = betaalfrequentie;
    }

    public Bedrijf getBedrijf() {
        return bedrijf;
    }

    public void setBedrijf(Bedrijf bedrijf) {
        this.bedrijf = bedrijf;
    }

    public Set<Schade> getSchades() {
        if (schades == null) {
            schades = new HashSet<Schade>();
        }
        return schades;
    }

    public void setSchades(Set<Schade> schades) {
        this.schades = schades;
    }

    public String getOmschrijvingVerzekering() {
        return omschrijvingVerzekering;
    }

    public void setOmschrijvingVerzekering(String omschrijvingVerzekering) {
        this.omschrijvingVerzekering = omschrijvingVerzekering;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (!(o instanceof Polis)) {
            return false;
        }

        Polis polis = (Polis) o;

        return new EqualsBuilder().append(getId(), polis.getId()).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(getId()).toHashCode();
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Polis{");
        sb.append("id=").append(id).append("\n");
        if (status != null) {
            sb.append(", status=").append(status.getOmschrijving()).append("\n");
        }
        sb.append(", polisNummer='").append(polisNummer).append('\'').append("\n");
        sb.append(", kenmerk='").append(kenmerk).append('\'').append("\n");
        sb.append(", ingangsDatum=").append(getIngangsDatum()).append("\n");
        sb.append(", eindDatum=").append(getEindDatum()).append("\n");
        sb.append(", premie=").append(premie).append("\n");
        sb.append(", wijzigingsDatum=").append(getWijzigingsDatum()).append("\n");
        sb.append(", prolongatieDatum=").append(getProlongatieDatum()).append("\n");
        sb.append(", betaalfrequentie=").append(betaalfrequentie).append("\n");
        //        if (relatie != null) {
        //            sb.append(", relatie=").append(relatie.getIdentificatie()).append("\n");
        //        }
        if (bedrijf != null) {
            sb.append(", bedrijf=").append(bedrijf.getNaam()).append("\n");
        }
        //        sb.append(", bijlages=").append(bijlages).append("\n");
        sb.append(", opmerkingen=").append(opmerkingen).append("\n");
        if (maatschappij != null) {
            sb.append(", maatschappij=").append(maatschappij).append("\n");
        }
        sb.append(", schades=").append(schades).append("\n");
        sb.append(", omschrijvingVerzekering='").append(omschrijvingVerzekering).append('\'').append("\n");
        sb.append('}');
        return sb.toString();
    }
}
