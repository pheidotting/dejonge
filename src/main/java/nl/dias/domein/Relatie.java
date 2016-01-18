package nl.dias.domein;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import nl.dias.domein.polis.Polis;
import nl.dias.domein.polis.SoortVerzekering;
import nl.dias.domein.predicates.PolissenOpSoortPredicate;
import nl.dias.domein.predicates.WoonAdresPredicate;
import nl.dias.domein.transformers.BedrijfToStringTransformer;
import nl.dias.domein.transformers.SessieToStringTransformer;
import nl.lakedigital.hulpmiddelen.domein.PersistenceObject;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.joda.time.LocalDate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.google.common.collect.Iterables.*;

@Entity
@Table(name = "GEBRUIKER")
@DiscriminatorValue(value = "R")
@AttributeOverrides({@AttributeOverride(name = "identificatie", column = @Column(name = "GEBRUIKERSNAAM"))})
@NamedQueries({@NamedQuery(name = "Relatie.zoekAllesVoorKantoor", query = "select r from Relatie r where r.kantoor = :kantoor"), @NamedQuery(name = "Relatie.zoekOpEmail", query = "select r from Relatie r where r.identificatie = :emailadres"), @NamedQuery(name = "Relatie.zoekOpBsn", query = "select r from Relatie r where r.bsn = :bsn"), @NamedQuery(name = "Relatie.zoekOpAdres", query = "select a.relatie from Adres a where a.straat like :adres or a.plaats like :adres"), @NamedQuery(name = "Relatie.zoekOpTelefoonnummer", query = "select r from Relatie r inner join r.telefoonnummers t where t.telefoonnummer = :telefoonnummer"), @NamedQuery(name = "Relatie.zoekOpBedrijfsnaam", query = "select r from Relatie r inner join r.bedrijven b where b.naam LIKE :bedrijfsnaam")})
public class Relatie extends Gebruiker implements Serializable, PersistenceObject, ObjectMetAdressen, ObjectMetBijlages, ObjectMetOpmerkingen, ObjectMetTelefoonnummers {
    private static final long serialVersionUID = -1920949633670770763L;

    @Column(name = "ROEPNAAM")
    private String roepnaam;

    @OneToMany(cascade = {CascadeType.MERGE, CascadeType.REFRESH, CascadeType.PERSIST, CascadeType.REMOVE}, fetch = FetchType.LAZY, targetEntity = Adres.class, mappedBy = "relatie")
    private Set<Adres> adressen;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, targetEntity = Telefoonnummer.class, mappedBy = "relatie")
    private Set<Telefoonnummer> telefoonnummers;

    @Column(name = "BSN")
    private String bsn;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, targetEntity = RekeningNummer.class, mappedBy = "relatie")
    private Set<RekeningNummer> rekeningnummers;

    @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY, optional = false, targetEntity = Kantoor.class)
    @JoinColumn(name = "KANTOOR")
    private Kantoor kantoor;

    @OneToMany(cascade = {CascadeType.REFRESH, CascadeType.DETACH, CascadeType.MERGE}, fetch = FetchType.LAZY, targetEntity = Opmerking.class, mappedBy = "relatie")
    private Set<Opmerking> opmerkingen;

    @Column(name = "GEBOORTEDATUM")
    @Temporal(TemporalType.DATE)
    private Date geboorteDatum;

    @Column(name = "OVERLIJDENSDATUM")
    @Temporal(TemporalType.DATE)
    private Date overlijdensdatum;

    @Column(name = "GESLACHT", length = 1)
    @Enumerated(EnumType.STRING)
    private Geslacht geslacht;

    @Column(name = "BURGERLIJKESTAAT", length = 1)
    @Enumerated(EnumType.STRING)
    private BurgerlijkeStaat burgerlijkeStaat;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, targetEntity = OnderlingeRelatie.class, mappedBy = "relatie")
    private Set<OnderlingeRelatie> onderlingeRelaties;

    //    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, targetEntity = Polis.class, mappedBy = "relatie")
    @Transient
    private Set<Polis> polissen;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, targetEntity = Bedrijf.class, mappedBy = "relatie")
    private Set<Bedrijf> bedrijven;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, targetEntity = Hypotheek.class, mappedBy = "relatie", orphanRemoval = true)
    private Set<Hypotheek> hypotheken;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, targetEntity = HypotheekPakket.class, mappedBy = "relatie", orphanRemoval = true)
    private Set<HypotheekPakket> hypotheekPakketten;

    @OneToMany(cascade = {CascadeType.REFRESH, CascadeType.MERGE}, fetch = FetchType.LAZY, targetEntity = Bijlage.class, mappedBy = "relatie", orphanRemoval = true)
    private Set<Bijlage> bijlages;

    public String getRoepnaam() {
        return roepnaam;
    }

    public void setRoepnaam(String roepnaam) {
        this.roepnaam = roepnaam;
    }

    public Set<Adres> getAdressen() {
        if (adressen == null) {
            adressen = new HashSet<>();
        }
        return adressen;
    }

    public void setAdressen(List<Adres> adressen) {
        this.adressen = Sets.newHashSet(adressen);
    }

    public void setAdressen(Set<Adres> adressen) {
        this.adressen = adressen;
    }

    public Adres getAdres() {
        return (Adres) getFirst(filter(getAdressen(), new WoonAdresPredicate()), null);
    }

    public String getAdresOpgemaakt() {
        Adres adres = (Adres) getFirst(getAdressen(), new WoonAdresPredicate());
        if (adres != null) {
            return adres.getStraat() + " " + adres.getHuisnummer() + " " + adres.getToevoeging() + ", " + adres.getPlaats();
        } else {
            return null;
        }
    }


    public Set<Telefoonnummer> getTelefoonnummers() {
        if (telefoonnummers == null) {
            telefoonnummers = new HashSet<>();
        }
        return telefoonnummers;
    }

    public void setTelefoonnummers(Set<Telefoonnummer> telefoonnummers) {
        this.telefoonnummers = telefoonnummers;
    }

    public String getBsn() {
        return bsn;
    }

    public void setBsn(String bsn) {
        this.bsn = bsn;
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

    public Set<Opmerking> getOpmerkingen() {
        if (opmerkingen == null) {
            opmerkingen = new HashSet<>();
        }
        return opmerkingen;
    }

    public void setOpmerkingen(Set<Opmerking> opmerkingen) {
        this.opmerkingen = opmerkingen;
    }

    public Kantoor getKantoor() {
        return kantoor;
    }

    public void setKantoor(Kantoor kantoor) {
        this.kantoor = kantoor;
    }

    public LocalDate getGeboorteDatum() {
        if (geboorteDatum == null) {
            return null;
        }
        return new LocalDate(geboorteDatum);
    }

    public void setGeboorteDatum(LocalDate geboorteDatum) {
        this.geboorteDatum = geboorteDatum.toDateMidnight().toDate();
    }

    public LocalDate getOverlijdensdatum() {
        if (overlijdensdatum == null) {
            return null;
        }
        return new LocalDate(overlijdensdatum);
    }

    public void setOverlijdensdatum(LocalDate overlijdensdatum) {
        this.overlijdensdatum = overlijdensdatum.toDateMidnight().toDate();
    }

    public Geslacht getGeslacht() {
        return geslacht;
    }

    public void setGeslacht(Geslacht geslacht) {
        this.geslacht = geslacht;
    }

    public BurgerlijkeStaat getBurgerlijkeStaat() {
        return burgerlijkeStaat;
    }

    public void setBurgerlijkeStaat(BurgerlijkeStaat burgerlijkeStaat) {
        this.burgerlijkeStaat = burgerlijkeStaat;
    }

    public Set<OnderlingeRelatie> getOnderlingeRelaties() {
        if (onderlingeRelaties == null) {
            onderlingeRelaties = new HashSet<OnderlingeRelatie>();
        }
        return onderlingeRelaties;
    }

    public void setOnderlingeRelaties(Set<OnderlingeRelatie> onderlingeRelaties) {
        this.onderlingeRelaties = onderlingeRelaties;
    }

    public List<Polis> getZakelijkePolissen() {
        return Lists.newArrayList(filter(getPolissen(), new PolissenOpSoortPredicate(SoortVerzekering.ZAKELIJK)));
    }

    public List<Polis> getParticulierePolissen() {
        return Lists.newArrayList(filter(getPolissen(), new PolissenOpSoortPredicate(SoortVerzekering.PARTICULIER)));
    }

    public Set<Polis> getPolissen() {
        if (polissen == null) {
            polissen = new HashSet<>();
        }
        return polissen;
    }

    public void setPolissen(Set<Polis> polissen) {
        this.polissen = polissen;
    }

    public Set<Bedrijf> getBedrijven() {
        if (bedrijven == null) {
            bedrijven = new HashSet<>();
        }
        return bedrijven;
    }

    public void setBedrijven(Set<Bedrijf> bedrijven) {
        this.bedrijven = bedrijven;
    }

    public Set<Hypotheek> getHypotheken() {
        if (hypotheken == null) {
            hypotheken = new HashSet<Hypotheek>();
        }
        return hypotheken;
    }

    public void setHypotheken(Set<Hypotheek> hypotheken) {
        this.hypotheken = hypotheken;
    }

    public Set<HypotheekPakket> getHypotheekPakketten() {
        if (hypotheekPakketten == null) {
            hypotheekPakketten = new HashSet<HypotheekPakket>();
        }
        return hypotheekPakketten;
    }

    public void setHypotheekPakketten(Set<HypotheekPakket> hypotheekPakketten) {
        this.hypotheekPakketten = hypotheekPakketten;
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

    /**
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(this.burgerlijkeStaat).append(this.getAdressen()).append(this.bsn).append(this.geboorteDatum).append(this.geslacht).append(this.overlijdensdatum).toHashCode();
    }

    /**
     * @see java.lang.Object#equals(Object)
     */
    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Relatie)) {
            return false;
        }
        Relatie rhs = (Relatie) object;
        return new EqualsBuilder().appendSuper(super.equals(object)).append(this.burgerlijkeStaat, rhs.burgerlijkeStaat).append(this.getAdressen(), rhs.getAdressen()).append(this.bsn, rhs.bsn).append(this.geboorteDatum, rhs.geboorteDatum).append(this.geslacht, rhs.geslacht).append(this.overlijdensdatum, rhs.overlijdensdatum).isEquals();
    }

    /**
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        Long kantoorId = null;
        if (kantoor != null) {
            kantoorId = kantoor.getId();
        }

        return new ToStringBuilder(this).append("\ngeslacht", this.geslacht).append("kantoor", kantoorId).append("burgerlijkeStaat", this.burgerlijkeStaat).append("adressen", this.getAdressen()).append("telefoonnummers", this.telefoonnummers).append("identificatie", this.getIdentificatie()).append("zakelijkePolissen", this.getZakelijkePolissen()).append("rekeningnummers", this.rekeningnummers).append("bedrijven", transform(getBedrijven(), new BedrijfToStringTransformer())).append("voornaam", this.getVoornaam()).append("id", this.getId()).append("overlijdensdatum", this.overlijdensdatum).append("sessies", transform(this.getSessies(), new SessieToStringTransformer())).append("opmerkingen", this.opmerkingen).append("geboorteDatum", this.geboorteDatum).append("bsn", this.bsn).append("particulierePolissen", this.getParticulierePolissen()).append("onderlingeRelaties", this.onderlingeRelaties).append("wachtwoordString", this.getWachtwoordString()).append("polissen", this.polissen).append("tussenvoegsel", this.getTussenvoegsel()).append("achternaam", this.getAchternaam()).toString();
    }

    @Override
    public String getName() {
        return this.getIdentificatie();
    }

}
