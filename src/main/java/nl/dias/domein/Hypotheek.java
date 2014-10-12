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

import nl.lakedigital.hulpmiddelen.domein.PersistenceObject;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.joda.time.LocalDate;

@Entity
@Table(name = "HYPOTHEEK")
@NamedQueries({ @NamedQuery(name = "Hypotheek.allesVanRelatie", query = "select h from Hypotheek h where h.relatie = :relatie and h.hypotheekPakket is null") })
public class Hypotheek implements PersistenceObject, Serializable {
    private static final long serialVersionUID = -8709743283669873667L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    protected Long id;

    @JoinColumn(name = "RELATIE")
    @ManyToOne(cascade = { CascadeType.REFRESH, CascadeType.DETACH, CascadeType.MERGE }, fetch = FetchType.EAGER, optional = true, targetEntity = Relatie.class)
    protected Relatie relatie;

    @JoinColumn(name = "SOORT", nullable = false)
    @ManyToOne
    private SoortHypotheek hypotheekVorm;
    @Column(name = "OMSCHRIJVING", length = 1000, nullable = true)
    private String omschrijving;
    @AttributeOverride(name = "bedrag", column = @Column(name = "HYPOTHEEKBEDRAG"))
    private Bedrag hypotheekBedrag;
    @Column(name = "RENTE")
    private Integer rente;
    @AttributeOverride(name = "bedrag", column = @Column(name = "MARKTWAARDE"))
    private Bedrag marktWaarde;
    @Column(name = "ONDERPAND")
    private String onderpand;
    @AttributeOverride(name = "bedrag", column = @Column(name = "KOOPSOM"))
    private Bedrag koopsom;
    @AttributeOverride(name = "bedrag", column = @Column(name = "VRIJEVERKOOPWAARDE"))
    private Bedrag vrijeVerkoopWaarde;
    @Temporal(TemporalType.DATE)
    @Column(name = "TAXATIEDATUM")
    private Date taxatieDatum;
    @AttributeOverride(name = "bedrag", column = @Column(name = "WOZWAARDE"))
    private Bedrag wozWaarde;
    @AttributeOverride(name = "bedrag", column = @Column(name = "WAARDEVOORVERBOUWING"))
    private Bedrag waardeVoorVerbouwing;
    @AttributeOverride(name = "bedrag", column = @Column(name = "WAARDENAVERBOUWING"))
    private Bedrag waardeNaVerbouwing;
    @Temporal(TemporalType.DATE)
    @Column(name = "INGANGSDATUM")
    private Date ingangsDatum;
    @Temporal(TemporalType.DATE)
    @Column(name = "EINDDATUM")
    private Date eindDatum;
    @Column(name = "DUUR")
    private Long duur;
    @Temporal(TemporalType.DATE)
    @Column(name = "INGANGSDATUMRENTEVASTEPERIODE")
    private Date ingangsDatumRenteVastePeriode;
    @Temporal(TemporalType.DATE)
    @Column(name = "EINDDATUMRENTEVASTEPERIODE")
    private Date eindDatumRenteVastePeriode;
    @Column(name = "DUURRENTEVASTEPERIODE")
    private Long duurRenteVastePeriode;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "hypotheek", targetEntity = Opmerking.class)
    private Set<Opmerking> opmerkingen;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "hypotheek", targetEntity = Bijlage.class)
    private Set<Bijlage> bijlages;
    @Column(name = "LENINGNUMMER", length = 50)
    private String leningNummer;
    @ManyToOne
    @JoinColumn(name = "BANK", nullable = true)
    private Bank bank;
    @ManyToOne
    @JoinColumn(name = "PAKKET")
    private HypotheekPakket hypotheekPakket;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public SoortHypotheek getHypotheekVorm() {
        return hypotheekVorm;
    }

    public void setHypotheekVorm(SoortHypotheek hypotheekVorm) {
        this.hypotheekVorm = hypotheekVorm;
    }

    public String getOmschrijving() {
        return omschrijving;
    }

    public void setOmschrijving(String omschrijving) {
        this.omschrijving = omschrijving;
    }

    public Bedrag getHypotheekBedrag() {
        return hypotheekBedrag;
    }

    public void setHypotheekBedrag(Bedrag hypotheekBedrag) {
        this.hypotheekBedrag = hypotheekBedrag;
    }

    public Integer getRente() {
        return rente;
    }

    public void setRente(Integer rente) {
        this.rente = rente;
    }

    public Bedrag getMarktWaarde() {
        return marktWaarde;
    }

    public void setMarktWaarde(Bedrag marktWaarde) {
        this.marktWaarde = marktWaarde;
    }

    public String getOnderpand() {
        return onderpand;
    }

    public void setOnderpand(String onderpand) {
        this.onderpand = onderpand;
    }

    public Bedrag getKoopsom() {
        return koopsom;
    }

    public void setKoopsom(Bedrag koopsom) {
        this.koopsom = koopsom;
    }

    public Bedrag getVrijeVerkoopWaarde() {
        return vrijeVerkoopWaarde;
    }

    public void setVrijeVerkoopWaarde(Bedrag vrijeVerkoopWaarde) {
        this.vrijeVerkoopWaarde = vrijeVerkoopWaarde;
    }

    public LocalDate getTaxatieDatum() {
        return new LocalDate(taxatieDatum);
    }

    public void setTaxatieDatum(LocalDate taxatieDatum) {
        if (taxatieDatum == null) {
            this.taxatieDatum = null;
        } else {
            this.taxatieDatum = taxatieDatum.toDate();
        }
    }

    public Bedrag getWozWaarde() {
        return wozWaarde;
    }

    public void setWozWaarde(Bedrag wozWaarde) {
        this.wozWaarde = wozWaarde;
    }

    public Bedrag getWaardeVoorVerbouwing() {
        return waardeVoorVerbouwing;
    }

    public void setWaardeVoorVerbouwing(Bedrag waardeVoorVerbouwing) {
        this.waardeVoorVerbouwing = waardeVoorVerbouwing;
    }

    public Bedrag getWaardeNaVerbouwing() {
        return waardeNaVerbouwing;
    }

    public void setWaardeNaVerbouwing(Bedrag waardeNaVerbouwing) {
        this.waardeNaVerbouwing = waardeNaVerbouwing;
    }

    public LocalDate getIngangsDatum() {
        return new LocalDate(ingangsDatum);
    }

    public void setIngangsDatum(LocalDate ingangsDatum) {
        if (ingangsDatum == null) {
            this.ingangsDatum = null;
        } else {
            this.ingangsDatum = ingangsDatum.toDate();
        }
    }

    public LocalDate getEindDatum() {
        return new LocalDate(eindDatum);
    }

    public void setEindDatum(LocalDate eindDatum) {
        if (eindDatum == null) {
            this.eindDatum = null;
        } else {
            this.eindDatum = eindDatum.toDate();
        }
    }

    public Long getDuur() {
        return duur;
    }

    public void setDuur(Long duur) {
        this.duur = duur;
    }

    public LocalDate getIngangsDatumRenteVastePeriode() {
        return new LocalDate(ingangsDatumRenteVastePeriode);
    }

    public void setIngangsDatumRenteVastePeriode(LocalDate ingangsDatumRenteVastePeriode) {
        if (ingangsDatumRenteVastePeriode == null) {
            this.ingangsDatumRenteVastePeriode = null;
        } else {
            this.ingangsDatumRenteVastePeriode = ingangsDatumRenteVastePeriode.toDate();
        }
    }

    public LocalDate getEindDatumRenteVastePeriode() {
        return new LocalDate(eindDatumRenteVastePeriode);
    }

    public void setEindDatumRenteVastePeriode(LocalDate eindDatumRenteVastePeriode) {
        if (eindDatumRenteVastePeriode == null) {
            this.eindDatumRenteVastePeriode = null;
        } else {
            this.eindDatumRenteVastePeriode = eindDatumRenteVastePeriode.toDate();
        }
    }

    public Long getDuurRenteVastePeriode() {
        return duurRenteVastePeriode;
    }

    public void setDuurRenteVastePeriode(Long duurRenteVastePeriode) {
        this.duurRenteVastePeriode = duurRenteVastePeriode;
    }

    public Relatie getRelatie() {
        return relatie;
    }

    public void setRelatie(Relatie relatie) {
        this.relatie = relatie;
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

    public Set<Bijlage> getBijlages() {
        if (bijlages == null) {
            bijlages = new HashSet<>();
        }
        return bijlages;
    }

    public void setBijlages(Set<Bijlage> bijlages) {
        this.bijlages = bijlages;
    }

    public String getLeningNummer() {
        return leningNummer;
    }

    public void setLeningNummer(String leningNummer) {
        this.leningNummer = leningNummer;
    }

    public Bank getBank() {
        return bank;
    }

    public void setBank(Bank bank) {
        this.bank = bank;
    }

    public HypotheekPakket getHypotheekPakket() {
        return hypotheekPakket;
    }

    public void setHypotheekPakket(HypotheekPakket hypotheekPakket) {
        this.hypotheekPakket = hypotheekPakket;
    }

    /**
     * @see java.lang.Object#equals(Object)
     */
    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Hypotheek)) {
            return false;
        }
        Hypotheek rhs = (Hypotheek) object;
        return new EqualsBuilder().append(this.taxatieDatum, rhs.taxatieDatum).append(this.eindDatum, rhs.eindDatum).append(this.koopsom, rhs.koopsom).append(this.relatie, rhs.relatie)
                .append(this.hypotheekBedrag, rhs.hypotheekBedrag).append(this.rente, rhs.rente).append(this.hypotheekVorm, rhs.hypotheekVorm).append(this.duur, rhs.duur)
                .append(this.waardeVoorVerbouwing, rhs.waardeVoorVerbouwing).append(this.id, rhs.id).append(this.onderpand, rhs.onderpand)
                .append(this.duurRenteVastePeriode, rhs.duurRenteVastePeriode).append(this.vrijeVerkoopWaarde, rhs.vrijeVerkoopWaarde).append(this.ingangsDatum, rhs.ingangsDatum)
                .append(this.ingangsDatumRenteVastePeriode, rhs.ingangsDatumRenteVastePeriode).append(this.waardeNaVerbouwing, rhs.waardeNaVerbouwing).append(this.marktWaarde, rhs.marktWaarde)
                .append(this.wozWaarde, rhs.wozWaarde).append(this.omschrijving, rhs.omschrijving).append(this.eindDatumRenteVastePeriode, rhs.eindDatumRenteVastePeriode).isEquals();
    }

    /**
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(this.taxatieDatum).append(this.eindDatum).append(this.koopsom).append(this.relatie).append(this.hypotheekBedrag).append(this.rente)
                .append(this.hypotheekVorm).append(this.duur).append(this.waardeVoorVerbouwing).append(this.id).append(this.onderpand).append(this.duurRenteVastePeriode)
                .append(this.vrijeVerkoopWaarde).append(this.ingangsDatum).append(this.ingangsDatumRenteVastePeriode).append(this.waardeNaVerbouwing).append(this.marktWaarde).append(this.wozWaarde)
                .append(this.omschrijving).append(this.eindDatumRenteVastePeriode).toHashCode();
    }

    /**
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return new ToStringBuilder(this).append("taxatieDatum", this.taxatieDatum + "\n").append("eindDatum", this.eindDatum + "\n").append("koopsom", this.koopsom + "\n")
                .append("hypotheekBedrag", this.hypotheekBedrag + "\n").append("rente", this.rente + "\n").append("hypotheekVorm", this.hypotheekVorm + "\n").append("duur", this.duur + "\n")
                .append("waardeVoorVerbouwing", this.waardeVoorVerbouwing + "\n").append("id", this.id + "\n").append("onderpand", this.onderpand + "\n")
                .append("duurRenteVastePeriode", this.duurRenteVastePeriode + "\n").append("vrijeVerkoopWaarde", this.vrijeVerkoopWaarde + "\n").append("ingangsDatum", this.ingangsDatum + "\n")
                .append("ingangsDatumRenteVastePeriode", this.ingangsDatumRenteVastePeriode + "\n").append("waardeNaVerbouwing", this.waardeNaVerbouwing + "\n")
                .append("marktWaarde", this.marktWaarde + "\n").append("wozWaarde", this.wozWaarde + "\n").append("omschrijving", this.omschrijving + "\n")
                .append("eindDatumRenteVastePeriode", this.eindDatumRenteVastePeriode + "\n").toString();
    }

}
