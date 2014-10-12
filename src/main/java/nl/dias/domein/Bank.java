package nl.dias.domein;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import nl.lakedigital.hulpmiddelen.domein.PersistenceObject;

@Entity
@Table(name = "BANK")
public class Bank implements PersistenceObject, Serializable {
    private static final long serialVersionUID = -325575434345411135L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;
    @Column(length = 50, name = "NAAM")
    private String naam;
    @Column(name = "TONEN")
    private boolean tonen;

    public Bank() {
        tonen = true;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public String getNaam() {
        return naam;
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }

    public boolean isTonen() {
        return tonen;
    }

    public void setTonen(boolean tonen) {
        this.tonen = tonen;
    }
}
