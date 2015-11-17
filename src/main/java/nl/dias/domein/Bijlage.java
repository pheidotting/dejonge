package nl.dias.domein;

import nl.dias.domein.polis.Polis;
import nl.lakedigital.hulpmiddelen.domein.PersistenceObject;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.joda.time.LocalDateTime;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "BIJLAGE")
@NamedQueries({@NamedQuery(name = "Bijlage.zoekBijlagenBijPolis", query = "select b from Bijlage b where b.polis = :polis"), @NamedQuery(name = "Bijlage.allesVanRelatieSchade", query = "select b from Bijlage b where b.schade.polis.relatie = :relatie"), @NamedQuery(name = "Bijlage.allesVanRelatiePolis", query = "select b from Bijlage b where b.polis.relatie = :relatie"), @NamedQuery(name = "Bijlage.allesVanRelatieHypotheek", query = "select b from Bijlage b where b.hypotheek.relatie = :relatie"), @NamedQuery(name = "Bijlage.allesVanRelatieAangifte", query = "select b from Bijlage b where b.aangifte.relatie = :relatie"), @NamedQuery(name = "Bijlage.zoekBijlagesBijPolis", query = "select b from Bijlage b where b.polis = :polis"), @NamedQuery(name = "Bijlage.zoekBijlagesBijSchade", query = "select b from Bijlage b where b.schade = :schade")})
public class Bijlage implements PersistenceObject, Serializable {
    private static final long serialVersionUID = 5743959281799187372L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "BESTANDSNAAM", length = 500)
    private String bestandsNaam;

    @Column(name = "UPLOADMOMENT")
    @Temporal(TemporalType.TIMESTAMP)
    private Date uploadMoment;

    @JoinColumn(name = "POLIS", nullable = true)
    @ManyToOne(cascade = {CascadeType.REFRESH, CascadeType.DETACH, CascadeType.MERGE}, fetch = FetchType.EAGER, optional = true, targetEntity = Polis.class)
    private Polis polis;

    @JoinColumn(name = "HYPOTHEEK", nullable = true)
    @ManyToOne(cascade = {CascadeType.REFRESH, CascadeType.DETACH, CascadeType.MERGE}, fetch = FetchType.EAGER, optional = true, targetEntity = Hypotheek.class)
    private Hypotheek hypotheek;

    @JoinColumn(name = "SCHADE", nullable = true)
    @ManyToOne(cascade = {CascadeType.REFRESH, CascadeType.DETACH, CascadeType.MERGE}, fetch = FetchType.EAGER, optional = true, targetEntity = Schade.class)
    private Schade schade;

    @JoinColumn(name = "AANGIFTE", nullable = true)
    @ManyToOne(cascade = {CascadeType.REFRESH, CascadeType.DETACH, CascadeType.MERGE}, fetch = FetchType.EAGER, optional = true, targetEntity = Aangifte.class)
    private Aangifte aangifte;

    @JoinColumn(name = "RELATIE", nullable = true)
    @ManyToOne(cascade = {CascadeType.REFRESH, CascadeType.DETACH, CascadeType.MERGE}, fetch = FetchType.EAGER, optional = true, targetEntity = Relatie.class)
    private Relatie relatie;

    @JoinColumn(name = "BEDRIJF", nullable = true)
    @ManyToOne(cascade = {CascadeType.REFRESH, CascadeType.DETACH, CascadeType.MERGE}, fetch = FetchType.EAGER, optional = true, targetEntity = Bedrijf.class)
    private Bedrijf bedrijf;

    @Enumerated(EnumType.STRING)
    @Column(length = 50, name = "SOORTBIJLAGE")
    private SoortBijlage soortBijlage;

    @Column(name = "S3")
    private String s3Identificatie;

    @Column(name = "OMSCHRIJVING")
    private String omschrijving;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public String getBestandsNaam() {
        return bestandsNaam;
    }

    public void setBestandsNaam(String bestandsNaam) {
        this.bestandsNaam = bestandsNaam;
    }

    public LocalDateTime getUploadMoment() {
        return new LocalDateTime(uploadMoment);
    }

    public void setUploadMoment(LocalDateTime uploadMoment) {
        this.uploadMoment = uploadMoment.toDate();
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

    public Relatie getRelatie() {
        return relatie;
    }

    public void setRelatie(Relatie relatie) {
        this.relatie = relatie;
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

    public String getOmschrijving() {
        return omschrijving;
    }

    public void setOmschrijving(String omschrijving) {
        this.omschrijving = omschrijving;
    }

    public Bedrijf getBedrijf() {
        return bedrijf;
    }

    public void setBedrijf(Bedrijf bedrijf) {
        this.bedrijf = bedrijf;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Bijlage{");
        sb.append("id=").append(id);
        if (polis != null) {
            sb.append(", polis=").append(polis.getId());
        }
        if (hypotheek != null) {
            sb.append(", hypotheek=").append(hypotheek.getId());
        }
        if (schade != null) {
            sb.append(", schade=").append(schade.getId());
        }
        if (aangifte != null) {
            sb.append(", aangifte=").append(aangifte.getId());
        }
        sb.append(", soortBijlage=").append(soortBijlage);
        sb.append(", s3Identificatie='").append(s3Identificatie).append('\'');
        sb.append(", omschrijving='").append(omschrijving).append('\'');
        sb.append('}');
        return sb.toString();
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
