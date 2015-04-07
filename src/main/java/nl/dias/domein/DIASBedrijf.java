package nl.dias.domein;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import nl.lakedigital.hulpmiddelen.domein.PersistenceObject;

@Entity
@Table(name = "DIASBEDRIJF")
@NamedQueries({ @NamedQuery(name = "DIASBedrijf.zoekOpRegistratieNummer", query = "select b from DIASBedrijf b where b.registratieNummer = :registratieNummer") })
public class DIASBedrijf implements PersistenceObject, Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "1_RELATIE_REGISTRATIENUMMER")
    private String registratieNummer;
    @Column(name = "3_VOLLEDIGE_NAAM")
    private String naam;
    @Column(name = "4_KVK_NUMMER")
    private String kvk;
    @Column(name = "5_RECHTSVORM")
    private String rechtsvorm;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public String getRegistratieNummer() {
        return registratieNummer;
    }

    public void setRegistratieNummer(String registratieNummer) {
        this.registratieNummer = registratieNummer;
    }

    public String getNaam() {
        return naam;
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }

    public String getKvk() {
        return kvk;
    }

    public void setKvk(String kvk) {
        this.kvk = kvk;
    }

    public String getRechtsvorm() {
        return rechtsvorm;
    }

    public void setRechtsvorm(String rechtsvorm) {
        this.rechtsvorm = rechtsvorm;
    }
}
