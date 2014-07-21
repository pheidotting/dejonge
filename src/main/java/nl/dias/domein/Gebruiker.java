package nl.dias.domein;

import java.security.Principal;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import nl.lakedigital.domein.Onderwerp;
import nl.lakedigital.hulpmiddelen.domein.PersistenceObject;

import org.apache.commons.lang.builder.ToStringBuilder;

@Entity
@Table(name = "GEBRUIKER")
@DiscriminatorColumn(name = "SOORT", length = 1)
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@AttributeOverrides({ @AttributeOverride(name = "identificatie", column = @Column(name = "EMAILADRES")) })
@NamedQueries({ @NamedQuery(name = "Gebruiker.zoekOpEmail", query = "select g from Gebruiker g where g.identificatie = :emailadres"),
        @NamedQuery(name = "Gebruiker.zoekOpSessieEnIpAdres", query = "select distinct g from Gebruiker g join g.sessies as s where s.sessie = :sessie and s.ipadres = :ipadres"),
        @NamedQuery(name = "Gebruiker.zoekOpCookieCode", query = "select distinct g from Gebruiker g join g.sessies as s where s.cookieCode = :cookieCode"), })
public abstract class Gebruiker extends Onderwerp implements PersistenceObject, Principal {
    private static final long serialVersionUID = -643848502264838675L;

    @Column(name = "VOORNAAM")
    private String voornaam;
    @Column(name = "TUSSENVOEGSEL")
    private String tussenvoegsel;
    @Column(name = "ACHTERNAAM")
    private String achternaam;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "gebruiker", orphanRemoval = true, targetEntity = Sessie.class)
    private Set<Sessie> sessies;

    @Transient
    private String wachtwoordString;

    public String getVoornaam() {
        return voornaam;
    }

    public void setVoornaam(String voornaam) {
        this.voornaam = voornaam;
    }

    public String getTussenvoegsel() {
        return tussenvoegsel;
    }

    public void setTussenvoegsel(String tussenvoegsel) {
        this.tussenvoegsel = tussenvoegsel;
    }

    public String getAchternaam() {
        return achternaam;
    }

    public void setAchternaam(String achternaam) {
        this.achternaam = achternaam;
    }

    public Set<Sessie> getSessies() {
        if (sessies == null) {
            sessies = new HashSet<>();
        }
        return sessies;
    }

    public void setSessies(Set<Sessie> sessies) {
        this.sessies = sessies;
    }

    public String getWachtwoordString() {
        return wachtwoordString;
    }

    public void setWachtwoordString(String wachtwoordString) {
        this.wachtwoordString = wachtwoordString;
    }

    /**
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return new ToStringBuilder(this).append("wachtwoordString", this.wachtwoordString).append("achternaam", this.achternaam).append("tussenvoegsel", this.tussenvoegsel)
                .append("voornaam", this.voornaam).append("sessies", this.sessies).toString();
    }

    public String getNaam() {
        StringBuilder sb = new StringBuilder();
        sb.append(getVoornaam());
        if (getTussenvoegsel() != null) {
            sb.append(" ").append(getTussenvoegsel());
        }
        sb.append(" ").append(getAchternaam());
        return sb.toString();
    }
}
