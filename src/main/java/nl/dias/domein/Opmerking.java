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

import nl.dias.domein.polis.Polis;
import nl.lakedigital.hulpmiddelen.domein.PersistenceObject;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.hibernate.envers.Audited;
import org.joda.time.LocalDateTime;

@Entity
@Table(name = "OPMERKING")
@Audited
public class Opmerking implements PersistenceObject, Serializable {
    private static final long serialVersionUID = -2928569293026238403L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "TIJD")
    @Temporal(TemporalType.TIMESTAMP)
    private Date tijd;

    @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER, optional = true)
    @JoinColumn(name = "RELATIE")
    private Relatie relatie;

    @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER, optional = true)
    @JoinColumn(name = "KANTOOR")
    private Kantoor kantoor;

    @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER, optional = true)
    @JoinColumn(name = "POLIS")
    private Polis polis;

    @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER, optional = true)
    @JoinColumn(name = "SCHADE")
    private Polis schade;

    @Column(columnDefinition = "TEXT", name = "OPMERKING")
    private String opmerking;

    public Opmerking() {
        tijd = new Date();
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getTijd() {
        return new LocalDateTime(tijd);
    }

    public void setTijd(LocalDateTime tijd) {
        this.tijd = tijd.toDateTime().toDate();
    }

    public Relatie getRelatie() {
        return relatie;
    }

    public void setRelatie(Relatie relatie) {
        this.relatie = relatie;
    }

    public Kantoor getKantoor() {
        return kantoor;
    }

    public void setKantoor(Kantoor kantoor) {
        this.kantoor = kantoor;
    }

    public String getOpmerking() {
        return opmerking;
    }

    public void setOpmerking(String opmerking) {
        this.opmerking = opmerking;
    }

    public Polis getPolis() {
        return polis;
    }

    public void setPolis(Polis polis) {
        this.polis = polis;
    }

    public Polis getSchade() {
        return schade;
    }

    public void setSchade(Polis schade) {
        this.schade = schade;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("\nOpmerking [id=");
        builder.append(id);
        builder.append(", opmerking=");
        builder.append(opmerking);
        builder.append("]");
        return builder.toString();
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((opmerking == null) ? 0 : opmerking.hashCode());
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
        Opmerking other = (Opmerking) obj;
        return new EqualsBuilder().append(opmerking, other.opmerking).isEquals();
    }
}
