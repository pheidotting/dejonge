package nl.dias.domein;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import nl.dias.domein.polis.Polis;
import nl.lakedigital.hulpmiddelen.domein.PersistenceObject;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

@Entity
@Table(name = "BIJLAGE")
@NamedQueries({ @NamedQuery(name = "Bijlage.zoekBijlagenBijPolis", query = "select b from Bijlage b where b.polis = :polis"),
        @NamedQuery(name = "Bijlage.allesVanRelatieSchade", query = "select b from Bijlage b where b.schade.polis.relatie = :relatie"),
        @NamedQuery(name = "Bijlage.allesVanRelatiePolis", query = "select b from Bijlage b where b.polis.relatie = :relatie"),
        @NamedQuery(name = "Bijlage.allesVanRelatieHypotheek", query = "select b from Bijlage b where b.hypotheek.relatie = :relatie"),
        @NamedQuery(name = "Bijlage.allesVanRelatieAangifte", query = "select b from Bijlage b where b.aangifte.relatie = :relatie") })
public class Bijlage implements PersistenceObject, Serializable {
    private static final long serialVersionUID = 5743959281799187372L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "POLIS", nullable = true)
    private Polis polis;

    @ManyToOne
    @JoinColumn(name = "HYPOTHEEK", nullable = true)
    private Hypotheek hypotheek;

    @ManyToOne
    @JoinColumn(name = "SCHADE", nullable = true)
    private Schade schade;

    @ManyToOne
    @JoinColumn(name = "AANGIFTE", nullable = true)
    private Aangifte aangifte;

    @Enumerated(EnumType.STRING)
    @Column(length = 50, name = "SOORTBIJLAGE")
    private SoortBijlage soortBijlage;

    @Column(name = "S3")
    private String s3Identificatie;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public Polis getPolis() {
        return polis;
    }

    public void setPolis(Polis polis) {
        this.polis = polis;
    }

    public Hypotheek getHypotheek() {
        return hypotheek;
    }

    public void setHypotheek(Hypotheek hypotheek) {
        this.hypotheek = hypotheek;
    }

    public Schade getSchade() {
        return schade;
    }

    public void setSchade(Schade schade) {
        this.schade = schade;
    }

    public Aangifte getAangifte() {
        return aangifte;
    }

    public void setAangifte(Aangifte aangifte) {
        this.aangifte = aangifte;
    }

    public SoortBijlage getSoortBijlage() {
        return soortBijlage;
    }

    public void setSoortBijlage(SoortBijlage soortBijlage) {
        this.soortBijlage = soortBijlage;
    }

    public String getS3Identificatie() {
        return s3Identificatie;
    }

    public void setS3Identificatie(String s3Identificatie) {
        this.s3Identificatie = s3Identificatie;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Bijlage [id=");
        builder.append(id);
        builder.append(", soortBijlage=");
        builder.append(soortBijlage);
        builder.append(", s3Identificatie=");
        builder.append(s3Identificatie);
        builder.append("]");
        return builder.toString();
    }

    /**
     * @see java.lang.Object#equals(Object)
     */
    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Bijlage)) {
            return false;
        }
        Bijlage rhs = (Bijlage) object;

        return new EqualsBuilder().append(this.id, rhs.id).append(this.soortBijlage, rhs.soortBijlage).append(this.s3Identificatie, rhs.s3Identificatie).isEquals();
    }

    /**
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(this.id).append(this.soortBijlage).append(this.s3Identificatie).toHashCode();
    }
}
