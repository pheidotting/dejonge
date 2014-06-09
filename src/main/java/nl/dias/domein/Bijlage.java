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

import org.hibernate.envers.Audited;

@Entity
@Table(name = "BIJLAGE")
@Audited
@NamedQueries({ @NamedQuery(name = "Bijlage.zoekBijlagenBijPolis", query = "select b from Bijlage b where b.polis = :polis") })
public class Bijlage implements PersistenceObject, Serializable {
    private static final long serialVersionUID = 5743959281799187372L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "POLIS", nullable = true)
    private Polis polis;

    @Column(name = "BESTANDSNAAM")
    private String bestandsNaam;

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

    public String getBestandsNaam() {
        return bestandsNaam;
    }

    public void setBestandsNaam(String bestandsNaam) {
        this.bestandsNaam = bestandsNaam;
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
        builder.append(", bestandsNaam=");
        builder.append(bestandsNaam);
        builder.append(", soortBijlage=");
        builder.append(soortBijlage);
        builder.append(", s3Identificatie=");
        builder.append(s3Identificatie);
        builder.append("]");
        return builder.toString();
    }
}
