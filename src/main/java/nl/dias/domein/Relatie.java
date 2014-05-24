package nl.dias.domein;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import nl.dias.domein.polis.Polis;
import nl.dias.domein.polis.SoortVerzekering;
import nl.lakedigital.hulpmiddelen.domein.PersistenceObject;

import org.codehaus.jackson.annotate.JsonProperty;
import org.hibernate.annotations.Type;
import org.hibernate.envers.Audited;
import org.joda.time.LocalDate;

@Entity
@Table(name = "GEBRUIKER")
@DiscriminatorValue(value = "R")
@AttributeOverrides({ @AttributeOverride(name = "identificatie", column = @Column(name = "EMAILADRES")) })
@NamedQueries({ @NamedQuery(name = "Relatie.zoekAllesVoorKantoor", query = "select r from Relatie r where r.kantoor = :kantoor"),
        @NamedQuery(name = "Relatie.zoekOpEmail", query = "select r from Relatie r where r.identificatie = :emailadres") })
@Audited
public class Relatie extends Gebruiker implements Serializable, PersistenceObject {
    private static final long serialVersionUID = -1920949633670770763L;

    private Adres adres;
    @Transient
    private String adresOpgemaakt;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, targetEntity = Telefoonnummer.class, mappedBy = "relatie")
    private Set<Telefoonnummer> telefoonnummers;

    @Column(name = "BSN")
    private String bsn;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, targetEntity = RekeningNummer.class, mappedBy = "relatie")
    private Set<RekeningNummer> rekeningnummers;

    @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER, optional = false, targetEntity = Kantoor.class)
    @JoinColumn(name = "KANTOOR")
    private Kantoor kantoor;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, targetEntity = Opmerking.class, mappedBy = "relatie")
    private Set<Opmerking> opmerkingen;

    @Column(name = "GEBOORTEDATUM")
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDate")
    private LocalDate geboorteDatum;

    @Column(name = "OVERLIJDENSDATUM")
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDate")
    private LocalDate overlijdensdatum;

    @Column(name = "GESLACHT", length = 1)
    @Enumerated(EnumType.STRING)
    private Geslacht geslacht;

    @Column(name = "BURGERLIJKESTAAT", length = 1)
    @Enumerated(EnumType.STRING)
    private BurgerlijkeStaat burgerlijkeStaat;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, targetEntity = OnderlingeRelatie.class, mappedBy = "relatie")
    private Set<OnderlingeRelatie> onderlingeRelaties;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, targetEntity = Polis.class, mappedBy = "relatie")
    private Set<Polis> polissen;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, targetEntity = Bedrijf.class, mappedBy = "relatie")
    private Set<Bedrijf> bedrijven;

    @JsonProperty(value = "zakelijkeKlant")
    public boolean isZakelijk() {
        return (bedrijven != null && bedrijven.size() > 0);
    }

    public Adres getAdres() {
        if (adres == null) {
            adres = new Adres();
        }
        return adres;
    }

    public void setAdres(Adres adres) {
        this.adres = adres;
    }

    public String getAdresOpgemaakt() {
        this.adresOpgemaakt = getAdres().getStraat() + " " + getAdres().getHuisnummer() + " " + getAdres().getToevoeging() + ", " + getAdres().getPlaats();
        return this.adresOpgemaakt;
    }

    public void setAdresOpgemaakt(String adresOpgemaakt) {
        this.adresOpgemaakt = adresOpgemaakt;
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
        return new LocalDate(geboorteDatum);
    }

    public void setGeboorteDatum(LocalDate geboorteDatum) {
        this.geboorteDatum = geboorteDatum;
    }

    public LocalDate getOverlijdensdatum() {
        return new LocalDate(overlijdensdatum);
    }

    public void setOverlijdensdatum(LocalDate overlijdensdatum) {
        this.overlijdensdatum = overlijdensdatum;
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
        List<Polis> lijst = new ArrayList<>();
        for (Polis polis : getPolissen()) {
            if (polis.getSoortVerzekering().equals(SoortVerzekering.ZAKELIJK)) {
                lijst.add(polis);
            }
        }

        return lijst;
    }

    public List<Polis> getParticulierePolissen() {
        List<Polis> lijst = new ArrayList<>();
        for (Polis polis : getPolissen()) {
            if (polis.getSoortVerzekering().equals(SoortVerzekering.PARTICULIER)) {
                lijst.add(polis);
            }
        }

        return lijst;
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

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Relatie [adres=");
        builder.append(adres);
        builder.append(", telefoonnummers=");
        builder.append(telefoonnummers);
        builder.append(", bsn=");
        builder.append(bsn);
        builder.append(", rekeningnummers=");
        builder.append(rekeningnummers);
        builder.append(", adresOpgemaakt=");
        builder.append(adresOpgemaakt);
        builder.append(", polissen=");
        builder.append(getPolissen().size());
        builder.append(", toString()=");
        builder.append(super.toString());
        builder.append("]");
        return builder.toString();
    }
}
