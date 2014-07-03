package nl.dias.service;

import java.util.List;
import java.util.Set;

import javax.inject.Named;

import nl.dias.domein.Gebruiker;
import nl.dias.domein.Kantoor;
import nl.dias.domein.RekeningNummer;
import nl.dias.domein.Relatie;
import nl.dias.domein.Sessie;
import nl.dias.domein.Telefoonnummer;
import nl.dias.repository.GebruikerRepository;
import nl.lakedigital.loginsystem.exception.NietGevondenException;

import org.apache.log4j.Logger;

import com.sun.jersey.api.core.InjectParam;

@Named
public class GebruikerService {
    private final static Logger LOGGER = Logger.getLogger(GebruikerService.class);

    @InjectParam
    private GebruikerRepository gebruikerRepository;

    public Gebruiker lees(Long id) {
        return gebruikerRepository.lees(id);
    }

    public List<Relatie> alleRelaties(Kantoor kantoor) {
        return gebruikerRepository.alleRelaties(kantoor);
    }

    public void opslaan(Gebruiker gebruiker) {
        // Even checken of over de connectie met de Relatie is ingevuld
        if (gebruiker instanceof Relatie) {
            for (Telefoonnummer telefoonnummer : ((Relatie) gebruiker).getTelefoonnummers()) {
                telefoonnummer.setRelatie((Relatie) gebruiker);
            }
            for (RekeningNummer rekeningNummer : ((Relatie) gebruiker).getRekeningnummers()) {
                rekeningNummer.setRelatie((Relatie) gebruiker);
            }
        }
        gebruikerRepository.opslaan(gebruiker);
    }

    public void verwijder(Long id) {
        // Eerst ophalen
        Gebruiker gebruiker = gebruikerRepository.lees(id);
        // en dan verwijderen
        gebruikerRepository.verwijder(gebruiker);
    }

    public Gebruiker zoekOpSessieEnIpAdres(String sessie, String ipadres) throws NietGevondenException {
        return gebruikerRepository.zoekOpSessieEnIpadres(sessie, ipadres);
    }

    public Sessie zoekSessieOp(String sessieId, String ipadres, Set<Sessie> sessies) {
        for (Sessie sessie : sessies) {
            if (sessie.getSessie().equals(sessieId) && sessie.getIpadres().equals(ipadres)) {
                return sessie;
            }
        }

        return null;
    }

    public Sessie zoekSessieOp(String cookieCode, Set<Sessie> sessies) {
        for (Sessie sessie : sessies) {
            if (sessie.getCookieCode() != null && sessie.getCookieCode().equals(cookieCode)) {
                return sessie;
            }
        }

        return null;
    }

    public Gebruiker zoekOpCookieCode(String cookieCode) {
        try {
            return gebruikerRepository.zoekOpCookieCode(cookieCode);
        } catch (NietGevondenException e) {
            LOGGER.debug("Niets gevonden", e);
            return null;
        }
    }

    public Gebruiker zoek(String emailadres) throws NietGevondenException {
        return gebruikerRepository.zoek(emailadres);
    }

    public void refresh(Sessie sessie) {
        gebruikerRepository.refresh(sessie);
    }

    public void opslaan(Sessie sessie) {
        gebruikerRepository.opslaan(sessie);
    }

    public void verwijder(Sessie sessie) {
        gebruikerRepository.verwijder(sessie);
    }

    public void setGebruikerRepository(GebruikerRepository gebruikerRepository) {
        this.gebruikerRepository = gebruikerRepository;
    }
}
