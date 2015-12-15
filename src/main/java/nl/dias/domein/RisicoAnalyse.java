package nl.dias.domein;

import nl.lakedigital.hulpmiddelen.domein.PersistenceObject;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "RISICOANALYSE")
@NamedQueries({@NamedQuery(name = "RisicoAnalyse.alleRisicoAnalysesBijBedrijf", query = "select ra from RisicoAnalyse ra where ra.bedrijf = :bedrijf")})
public class RisicoAnalyse implements Serializable, PersistenceObject, ObjectMetOpmerkingen, ObjectMetBijlages {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @JoinColumn(name = "BEDRIJF")
    @OneToOne(cascade = {CascadeType.REFRESH, CascadeType.DETACH, CascadeType.MERGE}, fetch = FetchType.EAGER, optional = true, targetEntity = Bedrijf.class)
    private Bedrijf bedrijf;

    @OneToMany(cascade = {CascadeType.ALL}, fetch = FetchType.EAGER, mappedBy = "risicoAnalyse", orphanRemoval = true, targetEntity = Bijlage.class)
    private Set<Bijlage> bijlages;

    @OneToMany(cascade = {CascadeType.REFRESH, CascadeType.DETACH, CascadeType.MERGE}, fetch = FetchType.EAGER, mappedBy = "risicoAnalyse", orphanRemoval = true, targetEntity = Opmerking.class)
    private Set<Opmerking> opmerkingen;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public Bedrijf getBedrijf() {
        return bedrijf;
    }

    public void setBedrijf(Bedrijf bedrijf) {
        this.bedrijf = bedrijf;
    }

    @Override
    public Set<Bijlage> getBijlages() {
        if (bijlages == null) {
            bijlages = new HashSet<>();
        }
        return bijlages;
    }

    @Override
    public void setBijlages(Set<Bijlage> bijlages) {
        this.bijlages = bijlages;
    }

    @Override
    public Set<Opmerking> getOpmerkingen() {
        return opmerkingen;
    }

    @Override
    public void setOpmerkingen(Set<Opmerking> opmerkingen) {
        this.opmerkingen = opmerkingen;
    }
}
