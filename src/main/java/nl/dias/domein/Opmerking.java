package nl.dias.domein;

import nl.dias.web.SoortEntiteit;
import nl.lakedigital.hulpmiddelen.domein.PersistenceObject;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.joda.time.LocalDateTime;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "OPMERKING")
@NamedQueries({//
        @NamedQuery(name = "Opmerking.zoekOpmerkingenBijEntiteit", query = "select o from Opmerking o where o.soortEntiteit = :soortEntiteit and o.entiteitId = :entiteitId")//
})
public class Opmerking implements PersistenceObject, Serializable {
    private static final long serialVersionUID = -2928569293026238403L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "TIJD")
    @Temporal(TemporalType.TIMESTAMP)
    private Date tijd;

    @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "MEDEWERKER")
    private Medewerker medewerker;

    @Enumerated(EnumType.STRING)
    @Column(length = 50, name = "SOORTENTITEIT")
    private SoortEntiteit soortEntiteit;

    @Column(name = "ENTITEITID")
    private Long entiteitId;

    @Column(columnDefinition = "varchar(2500)", name = "OPMERKING")
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

    public Medewerker getMedewerker() {
        return medewerker;
    }

    public void setMedewerker(Medewerker medewerker) {
        this.medewerker = medewerker;
    }

    public Long getEntiteitId() {
        return entiteitId;
    }

    public void setEntiteitId(Long entiteitId) {
        this.entiteitId = entiteitId;
    }

    public SoortEntiteit getSoortEntiteit() {
        return soortEntiteit;
    }

    public void setSoortEntiteit(SoortEntiteit soortEntiteit) {
        this.soortEntiteit = soortEntiteit;
    }

    public String getOpmerking() {
        return opmerking;
    }

    public void setOpmerking(String opmerking) {
        this.opmerking = opmerking;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("id", id).append("tijd", tijd).append("opmerking", opmerking).toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (!(o instanceof Opmerking)) {
            return false;
        }

        Opmerking opmerking1 = (Opmerking) o;

        return new EqualsBuilder().append(getId(), opmerking1.getId()).append(getTijd(), opmerking1.getTijd()).append(getOpmerking(), opmerking1.getOpmerking()).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(getId()).append(getTijd()).append(getOpmerking()).toHashCode();
    }
}
