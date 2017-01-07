package nl.dias.service;

import nl.dias.domein.*;
import nl.lakedigital.loginsystem.exception.NietGevondenException;
import nl.lakedigital.loginsystem.exception.OnjuistWachtwoordException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class AuthorisatieService {
    private static final Logger LOGGER = LoggerFactory.getLogger(AuthorisatieService.class);

    public final static String COOKIE_DOMEIN_CODE = "lakedigitaladministratie";

    @Inject
    private GebruikerService gebruikerService;

    public void inloggen(String identificatie, String wachtwoord, boolean onthouden, HttpServletRequest request, HttpServletResponse response) throws OnjuistWachtwoordException, NietGevondenException {
        LOGGER.debug("Inloggen met " + identificatie + " en onthouden " + onthouden);

        Gebruiker gebruikerUitDatabase = gebruikerService.zoekOpIdentificatie(identificatie);
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

        // Eigenlijk is dit alleen om Sonar tevreden te houden
        if (inloggendeGebruiker == null) {
            throw new IllegalArgumentException();
        }

        try {
            inloggendeGebruiker.setIdentificatie(identificatie);
            inloggendeGebruiker.setHashWachtwoord(wachtwoord);
        } catch (UnsupportedEncodingException | NoSuchAlgorithmException e) {
            LOGGER.error("Fout opgetreden", e);
        }

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
        sessie.setSessie(UUID.randomUUID().toString());

        gebruikerService.opslaan(sessie);

        gebruikerUitDatabase.getSessies().add(sessie);

        gebruikerService.opslaan(gebruikerUitDatabase);

        LOGGER.debug("sessie id " + sessie.getSessie() + " in de request plaatsen");
        request.getSession().setAttribute("sessie", sessie.getSessie());

        if (onthouden) {
            LOGGER.debug("onthouden is true, dus cookie maken en opslaan");
            String cookieCode = UUID.randomUUID().toString();
            Cookie cookie = new Cookie(COOKIE_DOMEIN_CODE, cookieCode);
            cookie.setMaxAge(60 * 60);
            LOGGER.debug("cookie op de response zetten, code : " + cookieCode);
            response.addCookie(cookie);

            getCookies(request);

            // en ff naar de database
            LOGGER.debug("opslaan sessie");
            sessie.setCookieCode(cookieCode);
            gebruikerService.opslaan(sessie);
            gebruikerService.opslaan(gebruikerUitDatabase);
        }
    }

    public Gebruiker getIngelogdeGebruiker(HttpServletRequest request, String sessieId, String ipadres) {

        Gebruiker gebruiker = null;
        try {
            gebruiker = gebruikerService.zoekOpSessieEnIpAdres(sessieId, ipadres);

            if (gebruiker != null) {
                LOGGER.debug("Opzoeken Sessie met id " + sessieId + " en ipadres " + ipadres);
                Sessie sessie = gebruikerService.zoekSessieOp(sessieId, ipadres, gebruiker.getSessies());
                if (sessie != null) {
                    sessie.setDatumLaatstGebruikt(new Date());

                    gebruikerService.opslaan(gebruiker);

                    gebruikerService.verwijderVerlopenSessies(gebruiker);
                }
            }
        } catch (NietGevondenException e) {
            LOGGER.trace("Geen ingelogde gebruiker gevonden", e);
        }

        return gebruiker;
    }

    public void uitloggen(HttpServletRequest request) {
        String sessieId = (String) request.getSession().getAttribute("sessie");
        String ipadres = request.getRemoteAddr();

        Gebruiker gebruiker = getIngelogdeGebruiker(request, sessieId, ipadres);

        if (gebruiker == null) {
            LOGGER.debug("Er is helemaal niemand ingelogd");
        } else {
            Sessie sessie = gebruikerService.zoekSessieOp(sessieId, ipadres, gebruiker.getSessies());
            //            gebruikerService.verwijder(sessie);
            gebruiker.getSessies().remove(sessie);
            gebruikerService.opslaan(gebruiker);

            // Cookies opruimen
            for (Cookie cookie : getCookies(request)) {
                if (gebruikerService.zoekOpCookieCode(cookie.getValue()) != null) {
                    cookie.setMaxAge(0);
                }
            }
        }
    }

    public List<Cookie> getCookies(HttpServletRequest request) {
        List<Cookie> cookies = new ArrayList<Cookie>();

        if (request != null && request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                LOGGER.debug(cookie.getDomain());
                LOGGER.debug(cookie.getName());
                LOGGER.debug(cookie.getValue());
                if (cookie.getName().equals(COOKIE_DOMEIN_CODE)) {
                    cookies.add(cookie);
                }
            }
        }

        return cookies;
    }

}
