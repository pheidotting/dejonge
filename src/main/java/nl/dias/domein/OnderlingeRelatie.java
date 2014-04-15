package nl.dias.domein;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import nl.lakedigital.hulpmiddelen.domein.PersistenceObject;

import org.hibernate.envers.Audited;

@Entity
@Table(name = "ONDERLINGERELATIES")
@Audited
public class OnderlingeRelatie implements PersistenceObject, Serializable {
    private static final long serialVersionUID = -8731485363183283190L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER, optional = false, targetEntity = Relatie.class)
    @JoinColumn(name = "RELATIE")
    private Relatie relatie;

    @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER, optional = false, targetEntity = Relatie.class)
    @JoinColumn(name = "RELATIE_MET")
    private Relatie relatieMet;

    @Column(name = "RELATIESOORT", length = 3)
    @Enumerated(EnumType.STRING)
    private OnderlingeRelatieSoort onderlingeRelatieSoort;

    public OnderlingeRelatie() {
    }

    public OnderlingeRelatie(Relatie relatie, Relatie relatieMet, OnderlingeRelatieSoort soort) {
        this.relatie = relatie;
        this.onderlingeRelatieSoort = soort;
        setRelatieMet(relatieMet, true);
    }

    public OnderlingeRelatie(Relatie relatie, Relatie relatieMet, boolean terug, OnderlingeRelatieSoort soort) {
        this.relatie = relatie;
        this.onderlingeRelatieSoort = soort;
        setRelatieMet(relatieMet, terug);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Relatie getRelatie() {
        return relatie;
    }

    public void setRelatie(Relatie relatie) {
        this.relatie = relatie;
    }

    public Relatie getRelatieMet() {
        return relatieMet;
    }

    public void setRelatieMet(Relatie relatieMet, boolean terug) {
        this.relatieMet = relatieMet;
        if (terug) {
            this.relatieMet.getOnderlingeRelaties().add(new OnderlingeRelatie(this.relatieMet, relatie, false, OnderlingeRelatieSoort.getTegenGesteld(onderlingeRelatieSoort)));
        }
    }

    public OnderlingeRelatieSoort getOnderlingeRelatieSoort() {
        return onderlingeRelatieSoort;
    }

    public void setOnderlingeRelatieSoort(OnderlingeRelatieSoort onderlingeRelatieSoort) {
        this.onderlingeRelatieSoort = onderlingeRelatieSoort;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("OnderlingeRelatie [id=");
        builder.append(id);
        builder.append(", relatie=");
        if (relatie != null) {
            builder.append(relatie.getId());
        } else {
            builder.append("null");
        }
        builder.append(", relatieMet=");
        if (relatieMet != null) {
            builder.append(relatieMet.getId());
        } else {
            builder.append("null");
        }
        builder.append(", onderlingeRelatieSoort=");
        builder.append(onderlingeRelatieSoort);
        builder.append("]");
        return builder.toString();
    }
}
