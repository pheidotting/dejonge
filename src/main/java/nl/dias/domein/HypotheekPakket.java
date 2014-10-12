package nl.dias.domein;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import nl.lakedigital.hulpmiddelen.domein.PersistenceObject;

@Entity
@Table(name = "HYPOTHEEKPAKKET")
@NamedQueries({ @NamedQuery(name = "HypotheekPakket.allesVanRelatie", query = "select h from HypotheekPakket h where h.relatie = :relatie") })
public class HypotheekPakket implements PersistenceObject, Serializable {
    private static final long serialVersionUID = -2386437329178396939L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    protected Long id;

    @JoinColumn(name = "RELATIE")
    @ManyToOne(cascade = { CascadeType.REFRESH, CascadeType.DETACH, CascadeType.MERGE }, fetch = FetchType.EAGER, optional = true, targetEntity = Relatie.class)
    protected Relatie relatie;

    @OneToMany(targetEntity = Hypotheek.class, mappedBy = "hypotheekPakket")
    private Set<Hypotheek> hypotheken;

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

    public Set<Hypotheek> getHypotheken() {
        if (hypotheken == null) {
            hypotheken = new HashSet<Hypotheek>();
        }
        return hypotheken;
    }

    public void setHypotheken(Set<Hypotheek> hypotheken) {
        this.hypotheken = hypotheken;
    }

}