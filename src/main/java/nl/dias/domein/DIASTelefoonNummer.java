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
@Table(name = "DIASTELEFOON")
@NamedQueries({ @NamedQuery(name = "DIASTelefoonNummer.zoekOpRegistratieNummer", query = "select t from DIASTelefoonNummer t where t.registratieNummer = :registratieNummer") })
public class DIASTelefoonNummer implements PersistenceObject, Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "1_RELATIE_REGISTRATIENUMMER")
    private String registratieNummer;
    @Column(name = "2_OMSCHRIJVING")
    private String omschrijving;
    @Column(name = "3_TELEFOONNUMMER")
    private String telefoonummer;
    @Column(name = "4_TYPE_TELEFOON")
    private String type;

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

    public String getOmschrijving() {
        return omschrijving;
    }

    public void setOmschrijving(String omschrijving) {
        this.omschrijving = omschrijving;
    }

    public String getTelefoonummer() {
        return telefoonummer;
    }

    public void setTelefoonummer(String telefoonummer) {
        this.telefoonummer = telefoonummer;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
