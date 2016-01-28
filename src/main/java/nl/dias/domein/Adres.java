package nl.dias.domein;

import org.apache.commons.lang3.builder.EqualsBuilder;

import javax.persistence.*;
import java.io.Serializable;

import static org.apache.commons.lang3.StringUtils.isNotBlank;

@Entity
@Table(name = "ADRES")
@NamedQueries({@NamedQuery(name = "Adres.verwijderAdressenBijRelatie", query = "delete from Adres a where a.relatie = :relatie")})
public class Adres implements Serializable {
    private static final long serialVersionUID = 2361944992062349932L;

    public enum SoortAdres {
        WOONADRES, POSTADRES, RISICOADRES, FACTUURADRES;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;
    @Column(name = "STRAAT")
    private String straat;
    @Column(name = "HUISNUMMER")
    private Long huisnummer;
    @Column(name = "TOEVOEGING")
    private String toevoeging;
    @Column(length = 6, name = "POSTCODE")
    private String postcode;
    @Column(name = "PLAATS")
    private String plaats;
    @Column(name = "SOORT")
    @Enumerated(EnumType.STRING)
    private SoortAdres soortAdres;
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER, optional = true, targetEntity = Relatie.class)
    @JoinColumn(name = "RELATIE")
    private Relatie relatie;
    @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER, optional = true, targetEntity = Bedrijf.class)
    @JoinColumn(name = "BEDRIJF")
    private Bedrijf bedrijf;
    @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER, optional = true, targetEntity = Kantoor.class)
    @JoinColumn(name = "KANTOOR")
    private Kantoor kantoor;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStraat() {
        return straat;
    }

    public void setStraat(String straat) {
        this.straat = straat;
    }

    public Long getHuisnummer() {
        return huisnummer;
    }

    public void setHuisnummer(Long huisnummer) {
        this.huisnummer = huisnummer;
    }

    public String getToevoeging() {
        return toevoeging;
    }

    public void setToevoeging(String toevoeging) {
        this.toevoeging = toevoeging;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        if (postcode != null) {
            this.postcode = postcode.trim().replace(" ", "");
        }
    }

    public String getPlaats() {
        return plaats;
    }

    public void setPlaats(String plaats) {
        this.plaats = plaats;
    }

    public SoortAdres getSoortAdres() {
        return soortAdres;
    }

    public void setSoortAdres(SoortAdres soortAdres) {
        this.soortAdres = soortAdres;
    }

    public Relatie getRelatie() {
        return relatie;
    }

    public void setRelatie(Relatie relatie) {
        this.relatie = relatie;
    }

    public Bedrijf getBedrijf() {
        return bedrijf;
    }

    public void setBedrijf(Bedrijf bedrijf) {
        this.bedrijf = bedrijf;
    }

    public Kantoor getKantoor() {
        return kantoor;
    }

    public void setKantoor(Kantoor kantoor) {
        this.kantoor = kantoor;
    }

    public boolean isCompleet() {
        return isNotBlank(straat) && huisnummer != null && isNotBlank(postcode) && isNotBlank(plaats);

    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Adres [straat=");
        builder.append(straat);
        builder.append(", huisnummer=");
        builder.append(huisnummer);
        builder.append(", toevoeging=");
        builder.append(toevoeging);
        builder.append(", postcode=");
        builder.append(postcode);
        builder.append(", plaats=");
        builder.append(plaats);
        builder.append("]");
        builder.append(", soortAdres=");
        builder.append(soortAdres);
        builder.append("]");
        return builder.toString();
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((huisnummer == null) ? 0 : huisnummer.hashCode());
        result = prime * result + ((plaats == null) ? 0 : plaats.hashCode());
        result = prime * result + ((postcode == null) ? 0 : postcode.hashCode());
        result = prime * result + ((straat == null) ? 0 : straat.hashCode());
        result = prime * result + ((toevoeging == null) ? 0 : toevoeging.hashCode());
        result = prime * result + ((soortAdres == null) ? 0 : soortAdres.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        Adres other = (Adres) obj;
        return new EqualsBuilder().append(huisnummer, other.huisnummer).append(plaats, other.plaats).append(postcode, other.postcode).append(straat, other.straat).append(toevoeging, other.toevoeging).append(soortAdres, other.soortAdres).isEquals();
    }
}
