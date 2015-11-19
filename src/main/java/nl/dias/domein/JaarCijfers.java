package nl.dias.domein;

import com.google.common.collect.Sets;
import nl.lakedigital.hulpmiddelen.domein.PersistenceObject;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
@Table(name = "JAARCIJFERS")
@NamedQueries({@NamedQuery(name = "JaarCijfers.allesJaarCijfersBijBedrijf", query = "select jc from JaarCijfers jc where jc.bedrijf = :bedrijf")})
public class JaarCijfers implements Serializable, PersistenceObject, ObjectMetOpmerkingen, ObjectMetBijlages {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "JAAR")
    private Long jaar;

    @JoinColumn(name = "BEDRIJF")
    @ManyToOne(cascade = {CascadeType.REFRESH, CascadeType.DETACH, CascadeType.MERGE}, fetch = FetchType.EAGER, optional = true, targetEntity = Bedrijf.class)
    private Bedrijf bedrijf;

    @OneToMany(cascade = {CascadeType.ALL}, fetch = FetchType.EAGER, mappedBy = "jaarCijfers", orphanRemoval = true, targetEntity = Bijlage.class)
    private Set<Bijlage> bijlages;

    @OneToMany(cascade = {CascadeType.REFRESH, CascadeType.DETACH, CascadeType.MERGE}, fetch = FetchType.EAGER, mappedBy = "jaarCijfers", orphanRemoval = true, targetEntity = Opmerking.class)
    private Set<Opmerking> opmerkingen;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public Long getJaar() {
        return jaar;
    }

    public void setJaar(Long jaar) {
        this.jaar = jaar;
    }

    public Bedrijf getBedrijf() {
        return bedrijf;
    }

    public void setBedrijf(Bedrijf bedrijf) {
        this.bedrijf = bedrijf;
    }

    public Set<Bijlage> getBijlages() {
        if (bijlages == null) {
            bijlages = Sets.newHashSet();
        }
        return bijlages;
    }

    public void setBijlages(Set<Bijlage> bijlages) {
        this.bijlages = bijlages;
    }

    public Set<Opmerking> getOpmerkingen() {
        if (opmerkingen == null) {
            opmerkingen = Sets.newHashSet();
        }
        return opmerkingen;
    }

    public void setOpmerkingen(Set<Opmerking> opmerkingen) {
        this.opmerkingen = opmerkingen;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (!(o instanceof JaarCijfers)) {
            return false;
        }

        JaarCijfers that = (JaarCijfers) o;

        return new EqualsBuilder().append(getId(), that.getId()).append(getJaar(), that.getJaar()).append(getBedrijf(), that.getBedrijf()).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(getId()).append(getJaar()).append(getBedrijf()).toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("id", id).append("jaar", jaar).append("bedrijf", bedrijf).toString();
    }
}
