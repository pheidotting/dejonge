package nl.dias.domein;

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
@NamedQueries({@NamedQuery(name = "Opmerking.allesVoorRelatie", query = "select o from Opmerking o where o.relatie = :relatie"),//
        @NamedQuery(name = "Opmerking.allesVoorPolis", query = "select o from Opmerking o where o.polis = :polis"),//
        @NamedQuery(name = "Opmerking.allesVoorSchade", query = "select o from Opmerking o where o.schade = :schade"),//
        @NamedQuery(name = "Opmerking.allesVoorRelatie", query = "select o from Opmerking o where o.relatie = :relatie"),//
        @NamedQuery(name = "Opmerking.allesVoorBedrijf", query = "select o from Opmerking o where o.bedrijf = :bedrijf"),//
        @NamedQuery(name = "Opmerking.allesVoorJaarCijfers", query = "select o from Opmerking o where o.jaarCijfers = :jaarCijfers")//
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

    @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER, optional = true)
    @JoinColumn(name = "RELATIE")
    private Relatie relatie;

    @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "MEDEWERKER")
    private Medewerker medewerker;

    @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER, optional = true)
    @JoinColumn(name = "KANTOOR")
    private Kantoor kantoor;

    @Column(name = "POLIS")
    private Long polis;

    @Column(name = "SCHADE")
    private Long schade;

    @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER, optional = true)
    @JoinColumn(name = "HYPOTHEEK")
    private Hypotheek hypotheek;

    //    @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER, optional = true)
    @Column(name = "BEDRIJF")
    private Long bedrijf;

    @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER, optional = true)
    @JoinColumn(name = "AANGIFTE")
    private Aangifte aangifte;

    @Column(name = "JAARCIJFERS", nullable = true)
    //    @ManyToOne(cascade = {CascadeType.REFRESH, CascadeType.DETACH, CascadeType.MERGE}, fetch = FetchType.EAGER, optional = true, targetEntity = JaarCijfers.class)
    private Long jaarCijfers;

    @JoinColumn(name = "RISICOANALYSE", nullable = true)
    @ManyToOne(cascade = {CascadeType.REFRESH, CascadeType.DETACH, CascadeType.MERGE}, fetch = FetchType.EAGER, optional = true, targetEntity = RisicoAnalyse.class)
    private RisicoAnalyse risicoAnalyse;

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

    public Relatie getRelatie() {
        return relatie;
    }

    public void setRelatie(Relatie relatie) {
        this.relatie = relatie;
    }

    public Medewerker getMedewerker() {
        return medewerker;
    }

    public void setMedewerker(Medewerker medewerker) {
        this.medewerker = medewerker;
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

    public Long getPolis() {
        return polis;
    }

    public void setPolis(Long polis) {
        this.polis = polis;
    }

    public Long getSchade() {
        return schade;
    }

    public void setSchade(Long schade) {
        this.schade = schade;
    }

    public Hypotheek getHypotheek() {
        return hypotheek;
    }

    public void setHypotheek(Hypotheek hypotheek) {
        this.hypotheek = hypotheek;
    }

    public Long getBedrijf() {
        return bedrijf;
    }

    public void setBedrijf(Long bedrijf) {
        this.bedrijf = bedrijf;
    }

    public Aangifte getAangifte() {
        return aangifte;
    }

    public void setAangifte(Aangifte aangifte) {
        this.aangifte = aangifte;
    }

    public Long getJaarCijfers() {
        return jaarCijfers;
    }

    public void setJaarCijfers(Long jaarCijfers) {
        this.jaarCijfers = jaarCijfers;
    }

    public RisicoAnalyse getRisicoAnalyse() {
        return risicoAnalyse;
    }

    public void setRisicoAnalyse(RisicoAnalyse risicoAnalyse) {
        this.risicoAnalyse = risicoAnalyse;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("id", id).append("tijd", tijd).append("relatie", relatie == null ? null : relatie.getId()).append("medewerker", medewerker == null ? null : medewerker.getId()).append("kantoor", kantoor == null ? null : kantoor.getId()).append("polis", polis == null ? null : polis).append("hypotheek", hypotheek == null ? null : hypotheek.getId()).append("opmerking", opmerking).toString();
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

        return new EqualsBuilder().append(getId(), opmerking1.getId()).append(getTijd(), opmerking1.getTijd()).append(getRelatie(), opmerking1.getRelatie()).append(getMedewerker(), opmerking1.getMedewerker()).append(getKantoor(), opmerking1.getKantoor()).append(getPolis(), opmerking1.getPolis()).append(getSchade(), opmerking1.getSchade()).append(getHypotheek(), opmerking1.getHypotheek()).append(getOpmerking(), opmerking1.getOpmerking()).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(getId()).append(getTijd()).append(getRelatie()).append(getMedewerker()).append(getKantoor()).append(getPolis()).append(getSchade()).append(getHypotheek()).append(getOpmerking()).toHashCode();
    }
}
