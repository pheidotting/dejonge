package nl.dias.domein;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import nl.dias.domein.polis.AansprakelijkheidVerzekering;
import nl.dias.domein.polis.AnnuleringsVerzekering;
import nl.dias.domein.polis.AutoVerzekering;
import nl.dias.domein.polis.BromSnorfietsVerzekering;
import nl.dias.domein.polis.CamperVerzekering;
import nl.dias.domein.polis.FietsVerzekering;
import nl.dias.domein.polis.InboedelVerzekering;
import nl.dias.domein.polis.LevensVerzekering;
import nl.dias.domein.polis.MobieleApparatuurVerzekering;
import nl.dias.domein.polis.MotorVerzekering;
import nl.dias.domein.polis.PleziervaartuigVerzekering;
import nl.dias.domein.polis.Polis;
import nl.dias.domein.polis.RechtsbijstandVerzekering;
import nl.dias.domein.polis.RecreatieVerzekering;
import nl.dias.domein.polis.ReisVerzekering;
import nl.dias.domein.polis.SoortVerzekering;
import nl.dias.domein.polis.WoonhuisVerzekering;
import nl.dias.domein.polis.ZorgVerzekering;
import nl.lakedigital.hulpmiddelen.domein.PersistenceObject;

import org.codehaus.jackson.annotate.JsonProperty;
import org.hibernate.envers.Audited;
import org.joda.time.LocalDate;

@Entity
@Table(name = "GEBRUIKER")
@DiscriminatorValue(value = "R")
@AttributeOverrides({ @AttributeOverride(name = "identificatie", column = @Column(name = "EMAILADRES")) })
@NamedQueries({ @NamedQuery(name = "Relatie.zoekAllesVoorKantoor", query = "select r from Relatie r where r.kantoor = :kantoor"),
        @NamedQuery(name = "Relatie.zoekOpEmail", query = "select r from Relatie r where r.identificatie = :emailadres") })
@Audited
public class Relatie extends Gebruiker implements Serializable, PersistenceObject, Cloneable {
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
        this.geboorteDatum = geboorteDatum.toDate();
    }

    public LocalDate getOverlijdensdatum() {
        return new LocalDate(overlijdensdatum);
    }

    public void setOverlijdensdatum(LocalDate overlijdensdatum) {
        this.overlijdensdatum = overlijdensdatum.toDate();
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

    public Relatie clone() throws CloneNotSupportedException {
        return clone(true);
    }

    public Relatie clone(boolean onderlingeRelatie) throws CloneNotSupportedException {
        Relatie relatie = new Relatie();

        relatie.setId(this.getId());
        relatie.setAchternaam(this.getAchternaam());
        relatie.setTussenvoegsel(this.getTussenvoegsel());
        relatie.setVoornaam(this.getVoornaam());
        relatie.setBsn(this.getBsn());
        relatie.setAdresOpgemaakt(this.getAdresOpgemaakt());
        relatie.setIdentificatie(this.getIdentificatie());
        relatie.setSalt(null);
        relatie.setBurgerlijkeStaat(this.getBurgerlijkeStaat());
        relatie.setGeboorteDatum(this.getGeboorteDatum());
        relatie.setGeslacht(this.getGeslacht());
        relatie.setOverlijdensdatum(this.getOverlijdensdatum());
        relatie.setBedrijven(this.getBedrijven());
        for (Bedrijf bedrijf : relatie.getBedrijven()) {
            bedrijf.setRelatie(null);
        }

        for (Polis p : this.getPolissen()) {
            Polis po = null;
            if (p instanceof AansprakelijkheidVerzekering) {
                po = new AansprakelijkheidVerzekering();
            }
            if (p instanceof AnnuleringsVerzekering) {
                po = new AnnuleringsVerzekering();
            }
            if (p instanceof AutoVerzekering) {
                po = new AutoVerzekering();
                ((AutoVerzekering) po).setKenteken(((AutoVerzekering) p).getKenteken());
                ((AutoVerzekering) po).setSoortAutoVerzekering(((AutoVerzekering) p).getSoortAutoVerzekering());
            }
            if (p instanceof BromSnorfietsVerzekering) {
                po = new BromSnorfietsVerzekering();
            }
            if (p instanceof CamperVerzekering) {
                po = new CamperVerzekering();
            }
            if (p instanceof FietsVerzekering) {
                po = new FietsVerzekering();
            }
            if (p instanceof InboedelVerzekering) {
                po = new InboedelVerzekering();
            }
            if (p instanceof LevensVerzekering) {
                po = new LevensVerzekering();
            }
            if (p instanceof MobieleApparatuurVerzekering) {
                po = new MobieleApparatuurVerzekering();
            }
            if (p instanceof MotorVerzekering) {
                po = new MotorVerzekering();
            }
            if (p instanceof PleziervaartuigVerzekering) {
                po = new PleziervaartuigVerzekering();
            }
            if (p instanceof RechtsbijstandVerzekering) {
                po = new RechtsbijstandVerzekering();
            }
            if (p instanceof RecreatieVerzekering) {
                po = new RecreatieVerzekering();
            }
            if (p instanceof ReisVerzekering) {
                po = new ReisVerzekering();
            }
            if (p instanceof WoonhuisVerzekering) {
                po = new WoonhuisVerzekering();
            }
            if (p instanceof ZorgVerzekering) {
                po = new ZorgVerzekering();
            }

            po.setId(p.getId());
            po.setIngangsDatumString(p.getIngangsDatumString());
            po.setMaatschappij(p.getMaatschappij());

            for (Opmerking o : p.getOpmerkingen()) {
                Opmerking nieuw = new Opmerking();
                nieuw.setId(o.getId());
                nieuw.setOpmerking(o.getOpmerking());
            }

            for (Bijlage b : p.getBijlages()) {
                Bijlage bi = new Bijlage();
                bi.setId(b.getId());
                bi.setBestandsNaam(b.getBestandsNaam());

                po.getBijlages().add(bi);
            }

            relatie.getPolissen().add(po);
        }

        for (RekeningNummer rek : this.getRekeningnummers()) {
            RekeningNummer rekeningNummer = new RekeningNummer();
            rekeningNummer.setId(rek.getId());
            rekeningNummer.setBic(rek.getBic());
            rekeningNummer.setRekeningnummer(rek.getRekeningnummer());

            relatie.getRekeningnummers().add(rekeningNummer);
        }

        relatie.setAdres(this.getAdres());

        for (Telefoonnummer tel : this.getTelefoonnummers()) {
            Telefoonnummer telefoonnummer = new Telefoonnummer();
            telefoonnummer.setId(tel.getId());
            telefoonnummer.setSoort(tel.getSoort());
            telefoonnummer.setTelefoonnummer(tel.getTelefoonnummer());

            relatie.getTelefoonnummers().add(telefoonnummer);
        }

        for (OnderlingeRelatie o : this.getOnderlingeRelaties()) {
            OnderlingeRelatie or = new OnderlingeRelatie();
            or.setId(o.getId());
            or.setOnderlingeRelatieSoort(o.getOnderlingeRelatieSoort());
            if (onderlingeRelatie) {
                or.setRelatieMet(o.getRelatieMet().clone(false), false);
            }

            relatie.getOnderlingeRelaties().add(or);
        }

        return relatie;
    }

    public void verwerkWijzigingen(Relatie relatie) {
        this.setAchternaam(relatie.getAchternaam());
        if (adres != null) {
            this.setAdres(adres);
        }
        this.getAdres().setHuisnummer(relatie.getAdres().getHuisnummer());
        this.getAdres().setPlaats(relatie.getAdres().getPlaats());
        this.getAdres().setPostcode(relatie.getAdres().getPostcode());
        this.getAdres().setStraat(relatie.getAdres().getStraat());
        this.getAdres().setToevoeging(relatie.getAdres().getToevoeging());
        this.setBsn(relatie.getBsn());
        this.setIdentificatie(relatie.getIdentificatie());
        this.setRekeningnummers(new HashSet<RekeningNummer>());
        this.setBurgerlijkeStaat(relatie.getBurgerlijkeStaat());
        this.setGeslacht(relatie.getGeslacht());
        this.setGeboorteDatum(relatie.getGeboorteDatum());
        this.setOverlijdensdatum(relatie.getOverlijdensdatum());
        for (RekeningNummer r : relatie.getRekeningnummers()) {
            this.getRekeningnummers().add(r);
        }
        this.setTelefoonnummers(new HashSet<Telefoonnummer>());
        for (Telefoonnummer t : relatie.getTelefoonnummers()) {
            this.getTelefoonnummers().add(t);
        }
        this.setTussenvoegsel(relatie.getTussenvoegsel());
        this.setVoornaam(relatie.getVoornaam());
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
