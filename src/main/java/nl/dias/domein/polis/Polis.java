package nl.dias.domein.polis;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.AttributeOverride;
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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import nl.dias.domein.Bedrag;
import nl.dias.domein.Bedrijf;
import nl.dias.domein.Bijlage;
import nl.dias.domein.Opmerking;
import nl.dias.domein.Relatie;
import nl.dias.domein.Schade;
import nl.dias.domein.VerzekeringsMaatschappij;
import nl.lakedigital.hulpmiddelen.domein.PersistenceObject;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.hibernate.envers.Audited;
import org.joda.time.LocalDate;

@Entity
@Table(name = "POLIS")
@DiscriminatorColumn(name = "SOORT", length = 2)
@Audited
@NamedQueries({ @NamedQuery(name = "Polis.allesBijMaatschappij", query = "select p from Polis p where p.maatschappij = :maatschappij"),
        @NamedQuery(name = "Polis.zoekOpPolisNummer", query = "select p from Polis p where p.polisNummer = :polisNummer and p.relatie.kantoor = :kantoor") })
public abstract class Polis implements PersistenceObject, Serializable {
    private static final long serialVersionUID = 1011438129295546984L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "POLISNUMMER", length = 25)
    private String polisNummer;

    @Column(name = "INGANGSDATUM")
    @Temporal(TemporalType.DATE)
    private Date ingangsDatum;

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
    @ManyToOne(cascade = { CascadeType.REFRESH, CascadeType.DETACH, CascadeType.MERGE }, fetch = FetchType.EAGER, optional = true, targetEntity = Relatie.class)
    private Relatie relatie;

    @JoinColumn(name = "BEDRIJF")
    @ManyToOne(cascade = { CascadeType.REFRESH, CascadeType.DETACH, CascadeType.MERGE }, fetch = FetchType.EAGER, optional = true, targetEntity = Bedrijf.class)
    private Bedrijf bedrijf;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "polis", orphanRemoval = true, targetEntity = Bijlage.class)
    private Set<Bijlage> bijlages;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "polis", orphanRemoval = true, targetEntity = Opmerking.class)
    private Set<Opmerking> opmerkingen;

    @ManyToOne(cascade = { CascadeType.REFRESH, CascadeType.DETACH, CascadeType.MERGE }, fetch = FetchType.EAGER, optional = false, targetEntity = VerzekeringsMaatschappij.class)
    @JoinColumn(name = "MAATSCHAPPIJ")
    private VerzekeringsMaatschappij maatschappij;

    @OneToMany(mappedBy = "polis")
    private Set<Schade> schades;

    @Transient
    private String ingangsDatumString;

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

    public void setPolisNummer(String polisNummer) {
        this.polisNummer = polisNummer;
    }

    public LocalDate getIngangsDatum() {
        return new LocalDate(ingangsDatum);
    }

    public void setIngangsDatum(LocalDate ingangsDatum) {
        this.ingangsDatum = ingangsDatum.toDateMidnight().toDate();
    }

    public String getIngangsDatumString() {
        return new LocalDate(ingangsDatum).toString("dd-MM-yyyy");
    }

    public void setIngangsDatumString(String ingangsDatum) {
        this.ingangsDatumString = ingangsDatum;
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

    public VerzekeringsMaatschappij getMaatschappij() {
        return maatschappij;
    }

    public void setMaatschappij(VerzekeringsMaatschappij maatschappij) {
        this.maatschappij = maatschappij;
    }

    public LocalDate getWijzigingsDatum() {
        return new LocalDate(wijzigingsDatum);
    }

    public void setWijzigingsDatum(LocalDate wijzigingsDatum) {
        this.wijzigingsDatum = wijzigingsDatum.toDateMidnight().toDate();
    }

    public LocalDate getProlongatieDatum() {
        return new LocalDate(prolongatieDatum);
    }

    public void setProlongatieDatum(LocalDate prolongatieDatum) {
        this.prolongatieDatum = prolongatieDatum.toDateMidnight().toDate();
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

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(bijlages).append(id).append(ingangsDatum).append(ingangsDatumString).append(maatschappij).append(opmerkingen).append(polisNummer).append(premie)
                .append(relatie).toHashCode();
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
        Polis other = (Polis) obj;

        return new EqualsBuilder().append(bijlages, other.bijlages).append(id, other.id).append(ingangsDatum, other.ingangsDatum).append(ingangsDatumString, other.ingangsDatumString)
                .append(maatschappij, other.maatschappij).append(opmerkingen, other.opmerkingen).append(polisNummer, other.polisNummer).append(premie, other.premie).append(relatie, other.relatie)
                .isEquals();
    }
}
