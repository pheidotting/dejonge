package nl.dias.web;

import java.util.Date;
import java.util.Random;

import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import nl.dias.domein.Beheerder;
import nl.dias.domein.Gebruiker;
import nl.dias.domein.Medewerker;
import nl.dias.domein.Relatie;
import nl.dias.domein.Sessie;
import nl.dias.service.GebruikerService;
import nl.lakedigital.loginsystem.exception.NietGevondenException;
import nl.lakedigital.loginsystem.exception.OnjuistWachtwoordException;

import org.apache.log4j.Logger;

import com.sun.jersey.api.core.InjectParam;

@Named
public class AuthorisatieService {
    private final static Logger LOGGER = Logger.getLogger(AuthorisatieService.class);

    @InjectParam
    private GebruikerService gebruikerService;

    public void inloggen(String identificatie, String wachtwoord, boolean onthouden, HttpServletRequest request, HttpServletResponse response) throws OnjuistWachtwoordException, NietGevondenException {

        Gebruiker gebruikerUitDatabase = gebruikerService.zoek(identificatie);
        Gebruiker inloggendeGebruiker = null;
        if (gebruikerUitDatabase instanceof Medewerker) {
            LOGGER.debug("Gebruiker is een Medewerker");
            inloggendeGebruiker = new Medewerker();
        } else if (gebruikerUitDatabase instanceof Relatie) {
            LOGGER.debug("Gebruiker is een Relatie");
            inloggendeGebruiker = new Relatie();
        } else if (gebruikerUitDatabase instanceof Beheerder) {
            LOGGER.debug("Gebruiker is een Beheerder");
            inloggendeGebruiker = new Beheerder();
        }
        inloggendeGebruiker.setIdentificatie(identificatie);
        inloggendeGebruiker.setHashWachtwoord(wachtwoord);

        LOGGER.debug("Ingevoerd wachtwoord    " + inloggendeGebruiker.getWachtwoord());
        LOGGER.debug("Wachtwoord uit database " + gebruikerUitDatabase.getWachtwoord());

        if (!gebruikerUitDatabase.getWachtwoord().equals(inloggendeGebruiker.getWachtwoord())) {
            throw new OnjuistWachtwoordException();
        }

        // Gebruiker dus gevonden en wachtwoord dus juist..
        LOGGER.debug("Aanmaken nieuwe sessie");
        Sessie sessie = new Sessie();
        sessie.setBrowser(request.getHeader("user-agent"));
        sessie.setIpadres(request.getRemoteAddr());
        sessie.setDatumLaatstGebruikt(new Date());
        sessie.setGebruiker(gebruikerUitDatabase);

        gebruikerService.opslaan(sessie);

        gebruikerUitDatabase.getSessies().add(sessie);

        gebruikerService.opslaan(gebruikerUitDatabase);
        // TODO aanpassen met unieke code
        sessie.setSessie(genereerNieuweCode(25));
        gebruikerService.opslaan(sessie);
        gebruikerService.opslaan(gebruikerUitDatabase);

        LOGGER.debug("sessie id " + sessie.getSessie() + " in de request plaatsen");
        request.getSession().setAttribute("sessie", sessie.getSessie());
    }

    public void uitloggen(HttpServletRequest request) {
        String sessieId = (String) request.getSession().getAttribute("sessie");
        String ipadres = request.getRemoteAddr();

        Gebruiker gebruiker = null;
        try {
            gebruiker = gebruikerService.zoekOpSessieEnIpAdres(sessieId, ipadres);
            LOGGER.debug(gebruiker.getSessies());
        } catch (NietGevondenException e) {
            LOGGER.error("Geen ingelogde gebruiker gevonden");
        }

        gebruiker.getSessies().remove(gebruikerService.zoekSessieOp(sessieId, ipadres, gebruiker.getSessies()));
        gebruikerService.opslaan(gebruiker);
    }

    private String genereerNieuweCode(int max) {
        Random ran = new Random();
        char data = ' ';
        String dat = "";
        for (int i = 0; i <= max; i++) {
            data = (char) (ran.nextInt(25) + 97);
            dat = data + dat;
        }

        return dat;
    }

    public void setGebruikerService(GebruikerService gebruikerService) {
        this.gebruikerService = gebruikerService;
    }
}
