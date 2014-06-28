package nl.dias.web.filter;

import java.io.IOException;
import java.util.Date;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.ext.Provider;

import nl.dias.domein.Gebruiker;
import nl.dias.domein.Sessie;
import nl.dias.repository.GebruikerRepository;
import nl.dias.service.GebruikerService;
import nl.lakedigital.loginsystem.exception.NietGevondenException;

import org.apache.log4j.Logger;

@Provider
public class AuthorisatieFilter implements Filter {
    private static final Logger LOGGER = Logger.getLogger(AuthorisatieFilter.class);

    private final GebruikerService gebruikerService = new GebruikerService();
    private final GebruikerRepository gebruikerRepository = new GebruikerRepository();

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        LOGGER.debug("In AuthorisatieFilter");
        HttpServletRequest req = (HttpServletRequest) request;

        if (getFullURL(req).startsWith("http://localhost:8080/dejonge/rest/gebruiker/inloggen") || getFullURL(req).startsWith("http://localhost:8080/dejonge/rest/gebruiker/uitloggen")) {
            LOGGER.debug("Gebruiker wil blijkbaar inloggen, dit hoeft uiteraard niet gefilterd..");
            chain.doFilter(request, response);
        } else {
            // TODO bij geen sessieId krijg je hier een NullPointer
            final String sessieId = (String) req.getSession().getAttribute("sessie");
            final String ipAdres = req.getRemoteAddr();

            Gebruiker gebruiker = null;
            Sessie sessie = null;

            if (sessieId != null && sessieId.length() > 0) {
                LOGGER.debug("Sessie met id " + sessieId + " gevonden in het request");
                try {
                    gebruiker = gebruikerRepository.zoekOpSessieEnIpadres(sessieId, ipAdres);
                    LOGGER.debug("Gebruiker met id " + gebruiker.getId() + " opgehaald.");
                } catch (NietGevondenException e) {
                    LOGGER.debug(e.getMessage());
                }
            } else {
                LOGGER.debug("Geen sessieId gevonden in het request");
            }

            if (gebruiker != null) {
                LOGGER.debug("Sessie ophalen van de ingelogde gebruiker");
                sessie = gebruikerService.zoekSessieOp(sessieId, ipAdres, gebruiker.getSessies());
                sessie.setDatumLaatstGebruikt(new Date());
                LOGGER.debug("Sessie weer opslaan met bijgewerkte datum");
                gebruikerRepository.opslaan(gebruiker);

                LOGGER.debug("Verder filteren");
                chain.doFilter(request, response);
            }
        }
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

}
