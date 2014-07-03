package nl.dias.web.filter;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.ext.Provider;

import nl.dias.domein.Gebruiker;
import nl.dias.domein.Sessie;
import nl.dias.repository.GebruikerRepository;
import nl.dias.service.GebruikerService;
import nl.dias.web.AuthorisatieService;
import nl.lakedigital.loginsystem.exception.NietGevondenException;

import org.apache.log4j.Logger;

@Provider
public class AuthorisatieFilter implements Filter {
    private static final Logger LOGGER = Logger.getLogger(AuthorisatieFilter.class);

    private GebruikerService gebruikerService = null;
    private GebruikerRepository gebruikerRepository = null;
    private AuthorisatieService authorisatieService = null;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        init();

        LOGGER.debug("In AuthorisatieFilter");
        HttpServletRequest req = (HttpServletRequest) request;

        if (getFullURL(req).contains("/rest/gebruiker/inloggen") || getFullURL(req).endsWith("/rest/gebruiker/uitloggen")) {
            LOGGER.debug("Gebruiker wil blijkbaar inloggen, dit hoeft uiteraard niet gefilterd..");
            chain.doFilter(request, response);
        } else {

            Gebruiker gebruiker = null;
            Sessie sessie = null;
            Cookie cookie = null;

            LOGGER.debug("koekjes opzoeken");
            init();
            List<Cookie> cookies = authorisatieService.getCookies(req);
            for (Cookie koekje : cookies) {
                LOGGER.debug(koekje.getValue());
                if (gebruiker == null) {
                    try {
                        init();
                        gebruiker = gebruikerRepository.zoekOpCookieCode(koekje.getValue());
                    } catch (NietGevondenException e) {
                        LOGGER.debug("niks gevonden in de database op basis van cookie code", e);
                    }
                    cookie = koekje;
                }
            }
            LOGGER.debug("klaar met de koekjes, gevonden : " + gebruiker);
            if (cookie != null) {
                LOGGER.debug("via cookie met code " + cookie.getValue());
            }

            if (gebruiker == null) {
                final String sessieId = (String) req.getSession().getAttribute("sessie");
                final String ipAdres = req.getRemoteAddr();

                if (sessieId != null && sessieId.length() > 0) {
                    LOGGER.debug("Sessie met id " + sessieId + " gevonden in het request");
                    try {
                        init();
                        gebruiker = gebruikerRepository.zoekOpSessieEnIpadres(sessieId, ipAdres);
                        LOGGER.debug("Gebruiker met id " + gebruiker.getId() + " opgehaald.");
                    } catch (NietGevondenException e) {
                        LOGGER.debug("Niet gevonden", e);
                    }
                } else {
                    LOGGER.debug("Geen sessieId gevonden in het request");
                }

                if (gebruiker != null) {
                    LOGGER.debug("Sessie ophalen van de ingelogde gebruiker");
                    init();
                    sessie = gebruikerService.zoekSessieOp(sessieId, ipAdres, gebruiker.getSessies());
                    sessie.setDatumLaatstGebruikt(new Date());
                    LOGGER.debug("Sessie weer opslaan met bijgewerkte datum");
                    gebruikerRepository.opslaan(gebruiker);

                    LOGGER.debug("Verder filteren");

                    opruimen();

                    chain.doFilter(request, response);
                } else {
                    opruimen();

                    LOGGER.debug("Stuur een UNAUTHORIZED");
                    ((HttpServletResponse) response).sendError(HttpServletResponse.SC_UNAUTHORIZED);
                }
            } else {
                init();
                Sessie sessie2 = gebruikerService.zoekSessieOp(cookie.getValue(), gebruiker.getSessies());
                req.getSession().setAttribute("sessie", sessie2.getSessie());

                opruimen();
                chain.doFilter(request, response);
            }
        }
    }

    private void init() {
        if (gebruikerService == null) {
            gebruikerService = new GebruikerService();
        }
        if (gebruikerRepository == null) {
            gebruikerRepository = new GebruikerRepository();
        }
        if (authorisatieService == null) {
            authorisatieService = new AuthorisatieService();
        }
    }

    private void opruimen() {
        gebruikerService = null;
        gebruikerRepository = null;
        authorisatieService = null;
    }

    private String getFullURL(HttpServletRequest request) {
        StringBuffer requestURL = request.getRequestURL();
        String queryString = request.getQueryString();

        if (queryString == null) {
            return requestURL.toString();
        } else {
            return requestURL.append('?').append(queryString).toString();
        }
    }

    @Override
    public void destroy() {
        LOGGER.debug("destroy filter");
    }

    @Override
    public void init(FilterConfig arg0) throws ServletException {
        LOGGER.debug("init filter");
    }

    public void setGebruikerService(GebruikerService gebruikerService) {
        this.gebruikerService = gebruikerService;
    }

    public void setGebruikerRepository(GebruikerRepository gebruikerRepository) {
        this.gebruikerRepository = gebruikerRepository;
    }

    public void setAuthorisatieService(AuthorisatieService authorisatieService) {
        this.authorisatieService = authorisatieService;
    }
}
