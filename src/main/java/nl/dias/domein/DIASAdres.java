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
@Table(name = "DIASADRES")
@NamedQueries({ @NamedQuery(name = "DIASAdres.zoekOpRegistratieNummer", query = "select a from DIASAdres a where a.registratieNummer = :registratieNummer") })
public class DIASAdres implements PersistenceObject, Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "1_RELATIE_REGISTRATIENUMMER")
    private String registratieNummer;
    @Column(name = "2_HUISNUMMER")
    private String huisNummer;
    @Column(name = "4_PLAATS")
    private String plaats;
    @Column(name = "5_POSTCODE")
    private String postcode;
    @Column(name = "6_STRAAT")
    private String straat;
    @Column(name = "7_TOEVOEGING")
    private String toevoeging;

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

    public String getHuisNummer() {
        return huisNummer;
    }

    public void setHuisNummer(String huisNummer) {
        this.huisNummer = huisNummer;
    }

    public String getPlaats() {
        return plaats;
    }

    public void setPlaats(String plaats) {
        this.plaats = plaats;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public String getStraat() {
        return straat;
    }

    public void setStraat(String straat) {
        this.straat = straat;
    }

    public String getToevoeging() {
        return toevoeging;
    }

    public void setToevoeging(String toevoeging) {
        this.toevoeging = toevoeging;
    }
}
