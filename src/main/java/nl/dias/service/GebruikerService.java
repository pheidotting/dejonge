package nl.dias.service;

import java.util.List;

import javax.inject.Named;

import nl.dias.domein.Gebruiker;
import nl.dias.domein.Kantoor;
import nl.dias.domein.Relatie;
import nl.dias.repository.GebruikerRepository;

import com.sun.jersey.api.core.InjectParam;

@Named
public class GebruikerService {
    @InjectParam
    private GebruikerRepository gebruikerRepository;

    public Gebruiker lees(Long id) {
        return gebruikerRepository.lees(id);
    }

    public List<Relatie> alleRelaties(Kantoor kantoor) {
        return gebruikerRepository.alleRelaties(kantoor);
    }

    public void opslaan(Gebruiker gebruiker) {
        gebruikerRepository.opslaan(gebruiker);
    }

    public void verwijder(Long id) {
        // Eerst ophalen
        Gebruiker gebruiker = gebruikerRepository.lees(id);
        // en dan verwijderen
        gebruikerRepository.verwijder(gebruiker);
    }

    public void setGebruikerRepository(GebruikerRepository gebruikerRepository) {
        this.gebruikerRepository = gebruikerRepository;
    }
}
