package nl.dias.domein;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import nl.lakedigital.hulpmiddelen.domein.PersistenceObject;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.hibernate.envers.Audited;

@Entity
@Table(name = "SESSIE")
@Audited
public class Sessie implements PersistenceObject, Serializable {
    private static final long serialVersionUID = -6578849306393389265L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;
    @Column(name = "SESSIE")
    private String sessie;
    @Column(name = "IPADRES")
    private String ipadres;
    @Column(name = "COOKIECODE")
    private String cookieCode;
    @Column(name = "BROWSER")
    private String browser;
    @Column(name = "DATUMGECREERD")
    @Temporal(TemporalType.TIMESTAMP)
    private Date datumGecreerd;
    @Column(name = "DATUMLAATSTGEBRUIKT")
    @Temporal(TemporalType.TIMESTAMP)
    private Date datumLaatstGebruikt;
    @JoinColumn(name = "GEBRUIKER")
    @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER, optional = true, targetEntity = Gebruiker.class)
    private Gebruiker gebruiker;

    public Sessie() {
        datumGecreerd = new Date();
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public String getSessie() {
        return sessie;
    }

    public void setSessie(String sessie) {
        this.sessie = sessie;
    }

    public String getIpadres() {
        return ipadres;
    }

    public void setIpadres(String ipadres) {
        this.ipadres = ipadres;
    }

    public String getCookieCode() {
        return cookieCode;
    }

    public void setCookieCode(String cookieCode) {
        this.cookieCode = cookieCode;
    }

    public String getBrowser() {
        return browser;
    }

    public void setBrowser(String browser) {
        this.browser = browser;
    }

    public Date getDatumGecreerd() {
        return datumGecreerd;
    }

    public void setDatumGecreerd(Date datumGecreerd) {
        this.datumGecreerd = datumGecreerd;
    }

    public Date getDatumLaatstGebruikt() {
        return datumLaatstGebruikt;
    }

    public void setDatumLaatstGebruikt(Date datumLaatstGebruikt) {
        this.datumLaatstGebruikt = datumLaatstGebruikt;
    }

    public Gebruiker getGebruiker() {
        return gebruiker;
    }

    public void setGebruiker(Gebruiker gebruiker) {
        this.gebruiker = gebruiker;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Sessie)) {
            return false;
        }
        Sessie rhs = (Sessie) object;
        return new EqualsBuilder().append(this.id, rhs.id).append(this.ipadres, rhs.ipadres).append(this.browser, rhs.browser).append(this.cookieCode, rhs.cookieCode).append(this.sessie, rhs.sessie)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(this.id).append(this.ipadres).append(this.browser).append(this.cookieCode).append(this.sessie).toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("id", this.id).append("ipadres", this.ipadres).append("datumLaatstGebruikt", this.datumLaatstGebruikt).append("datumGecreerd", this.datumGecreerd)
                .append("browser", this.browser).append("cookieCode", this.cookieCode).append("sessie", this.sessie).toString();
    }
}
