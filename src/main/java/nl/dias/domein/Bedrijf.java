package nl.dias.domein;

import com.google.common.collect.Sets;
import nl.dias.domein.polis.Polis;
import nl.lakedigital.hulpmiddelen.domein.PersistenceObject;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "BEDRIJF")
@NamedQueries({@NamedQuery(name = "Bedrijf.allesBijRelatie", query = "select b from Bedrijf b where b.relatie = :relatie"), @NamedQuery(name = "Bedrijf.zoekOpNaam", query = "select b from Bedrijf b where b.naam like :zoekTerm")})
public class Bedrijf implements Serializable, PersistenceObject {
    private static final long serialVersionUID = 4611123664803995245L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @JoinColumn(name = "RELATIE")
    @ManyToOne(cascade = {CascadeType.REFRESH, CascadeType.DETACH, CascadeType.MERGE}, fetch = FetchType.EAGER, optional = true, targetEntity = Relatie.class)
    private Relatie relatie;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, targetEntity = Polis.class, mappedBy = "bedrijf")
    private Set<Polis> polissen;

    @Column(name = "NAAM")
    private String naam;

    @Column(name = "KVK", length = 8)
    private String kvk;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, targetEntity = Adres.class, mappedBy = "bedrijf")
    private Set<Adres> adressen;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "bedrijf", targetEntity = Opmerking.class)
    private Set<Opmerking> opmerkingen;

    @OneToMany(cascade = {CascadeType.ALL}, fetch = FetchType.EAGER, mappedBy = "bedrijf", orphanRemoval = true, targetEntity = Bijlage.class)
    private Set<Bijlage> bijlages;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public Relatie getRelatie() {
        return relatie;
    }

    public void setRelatie(Relatie relatie) {
        this.relatie = relatie;
    }

    public String getNaam() {
        return naam;
    }

    public void setNaam(String naam) {
        this.naam = naam;
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

    public String getKvk() {
        return kvk;
    }

    public void setKvk(String kvk) {
        this.kvk = kvk;
    }

    public Set<Adres> getAdressen() {
        if (adressen == null) {
            adressen = new HashSet<>();
        }
        return adressen;
    }

    public void setAdressen(Set<Adres> adressen) {
        this.adressen = adressen;
    }

    public Set<Opmerking> getOpmerkingen() {
        if (opmerkingen == null) {
            opmerkingen = new HashSet<>();
        }
        return opmerkingen;
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

    public void setOpmerkingen(Set<Opmerking> opmerkingen) {
        this.opmerkingen = opmerkingen;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (!(o instanceof Bedrijf)) {
            return false;
        }

        Bedrijf bedrijf = (Bedrijf) o;

        return new EqualsBuilder().append(getId(), bedrijf.getId()).append(getRelatie(), bedrijf.getRelatie()).append(getNaam(), bedrijf.getNaam()).append(getKvk(), bedrijf.getKvk()).append(getAdressen(), bedrijf.getAdressen()).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(getId()).append(getRelatie()).append(getNaam()).append(getKvk()).append(getAdressen()).toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("id", id).append("relatie", relatie).append("polissen", polissen).append("naam", naam).append("kvk", kvk).append("adressen", adressen).toString();
    }
}
