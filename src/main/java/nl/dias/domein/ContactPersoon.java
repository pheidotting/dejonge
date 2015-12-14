package nl.dias.domein;

import com.google.common.collect.Sets;
import nl.lakedigital.hulpmiddelen.domein.PersistenceObject;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
@Table(name = "GEBRUIKER")
@DiscriminatorValue(value = "C")
@AttributeOverrides({@AttributeOverride(name = "identificatie", column = @Column(name = "GEBRUIKERSNAAM"))})
public class ContactPersoon extends Gebruiker implements Serializable, PersistenceObject, ObjectMetTelefoonnummers {
    private static final long serialVersionUID = -4313251874716582151L;

    @JoinColumn(name = "BEDRIJF")
    @ManyToOne(cascade = {CascadeType.REFRESH, CascadeType.DETACH, CascadeType.MERGE}, fetch = FetchType.EAGER, optional = true, targetEntity = Bedrijf.class)
    private Bedrijf bedrijf;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, targetEntity = Telefoonnummer.class, mappedBy = "contactPersoon")
    private Set<Telefoonnummer> telefoonnummers;

    @Column(name = "FUNCTIE")
    private String functie;

    public Bedrijf getBedrijf() {
        return bedrijf;
    }

    public void setBedrijf(Bedrijf bedrijf) {
        this.bedrijf = bedrijf;
    }

    public Set<Telefoonnummer> getTelefoonnummers() {
        if (telefoonnummers == null) {
            telefoonnummers = Sets.newHashSet();
        }
        return telefoonnummers;
    }

    public void setTelefoonnummers(Set<Telefoonnummer> telefoonnummers) {
        this.telefoonnummers = telefoonnummers;
    }

    public String getFunctie() {
        return functie;
    }

    public void setFunctie(String functie) {
        this.functie = functie;
    }

    @Override
    public String getName() {
        return this.getIdentificatie();
    }

}
