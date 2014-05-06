package nl.dias.domein.polis;

import java.io.Serializable;
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
import javax.persistence.Transient;

import nl.dias.domein.Bedrag;
import nl.dias.domein.Bedrijf;
import nl.dias.domein.Bijlage;
import nl.dias.domein.Opmerking;
import nl.dias.domein.Relatie;
import nl.dias.domein.VerzekeringsMaatschappij;
import nl.lakedigital.hulpmiddelen.domein.PersistenceObject;

import org.hibernate.annotations.Type;
import org.hibernate.envers.Audited;
import org.joda.time.LocalDate;

@Entity
@Table(name = "POLIS")
@DiscriminatorColumn(name = "SOORT", length = 2)
@Audited
@NamedQueries({ @NamedQuery(name = "Polis.allesBijMaatschappij", query = "select p from Polis p where p.maatschappij = :maatschappij") })
public abstract class Polis implements PersistenceObject, Serializable {
    private static final long serialVersionUID = 1011438129295546984L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "POLISNUMMER", length = 25)
    private String polisNummer;

    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDate")
    @Column(name = "INGANGSDATUM")
    private LocalDate ingangsDatum;

    @AttributeOverride(name = "bedrag", column = @Column(name = "PREMIE"))
    private Bedrag premie;

    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDate")
    @Column(name = "WIJZIGINGSDATUM")
    private LocalDate wijzigingsDatum;
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDate")
    @Column(name = "PROLONGATIEDATUM")
    private LocalDate prolongatieDatum;

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

    @Transient
    private String ingangsDatumString;

    public abstract SoortVerzekering getSoortVerzekering();

    public Long getId() {
        return id;
    }

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
        return ingangsDatum;
    }

    public void setIngangsDatum(LocalDate ingangsDatum) {
        this.ingangsDatum = ingangsDatum;
    }

    public String getIngangsDatumString() {
        return ingangsDatum.toString("dd-MM-yyyy");
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
        return wijzigingsDatum;
    }

    public void setWijzigingsDatum(LocalDate wijzigingsDatum) {
        this.wijzigingsDatum = wijzigingsDatum;
    }

    public LocalDate getProlongatieDatum() {
        return prolongatieDatum;
    }

    public void setProlongatieDatum(LocalDate prolongatieDatum) {
        this.prolongatieDatum = prolongatieDatum;
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

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((bijlages == null) ? 0 : bijlages.hashCode());
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((ingangsDatum == null) ? 0 : ingangsDatum.hashCode());
        result = prime * result + ((ingangsDatumString == null) ? 0 : ingangsDatumString.hashCode());
        result = prime * result + ((maatschappij == null) ? 0 : maatschappij.hashCode());
        result = prime * result + ((opmerkingen == null) ? 0 : opmerkingen.hashCode());
        result = prime * result + ((polisNummer == null) ? 0 : polisNummer.hashCode());
        result = prime * result + ((premie == null) ? 0 : premie.hashCode());
        result = prime * result + ((relatie == null) ? 0 : relatie.hashCode());
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
        Polis other = (Polis) obj;
        if (bijlages == null) {
            if (other.bijlages != null) {
                return false;
            }
        } else if (!bijlages.equals(other.bijlages)) {
            return false;
        }
        if (id == null) {
            if (other.id != null) {
                return false;
            }
        } else if (!id.equals(other.id)) {
            return false;
        }
        if (ingangsDatum == null) {
            if (other.ingangsDatum != null) {
                return false;
            }
        } else if (!ingangsDatum.equals(other.ingangsDatum)) {
            return false;
        }
        if (ingangsDatumString == null) {
            if (other.ingangsDatumString != null) {
                return false;
            }
        } else if (!ingangsDatumString.equals(other.ingangsDatumString)) {
            return false;
        }
        if (maatschappij == null) {
            if (other.maatschappij != null) {
                return false;
            }
        } else if (!maatschappij.equals(other.maatschappij)) {
            return false;
        }
        if (opmerkingen == null) {
            if (other.opmerkingen != null) {
                return false;
            }
        } else if (!opmerkingen.equals(other.opmerkingen)) {
            return false;
        }
        if (polisNummer == null) {
            if (other.polisNummer != null) {
                return false;
            }
        } else if (!polisNummer.equals(other.polisNummer)) {
            return false;
        }
        if (premie == null) {
            if (other.premie != null) {
                return false;
            }
        } else if (!premie.equals(other.premie)) {
            return false;
        }
        if (relatie == null) {
            if (other.relatie != null) {
                return false;
            }
        } else if (!relatie.equals(other.relatie)) {
            return false;
        }
        return true;
    }
}
