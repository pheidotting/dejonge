package nl.dias.web.filter;

import java.io.IOException;

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

import org.apache.log4j.Logger;

@Provider
public class AuthorisatieFilter implements Filter {
    private static final Logger LOGGER = Logger.getLogger(AuthorisatieFilter.class);

    private final GebruikerRepository gebruikerRepository = new GebruikerRepository();

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        LOGGER.debug("In AuthorisatieFilter");
        HttpServletRequest req = (HttpServletRequest) request;

        final String sessionId = null;// request.getHeaderValue("session-id");
        // LOGGER.debug("Sessie id uit request " + sessionId);
        //
        // LOGGER.debug("1");
        // LOGGER.debug(request.getHeaderValue("1"));
        // LOGGER.debug("a");
        // LOGGER.debug(request.getHeaderValue("a"));
        // LOGGER.debug("b");
        // LOGGER.debug(request.getHeaderValue(""));

        LOGGER.debug(req.getSession().getAttribute("a"));

        Gebruiker gebruiker = null;
        Sessie sessie = null;

        if (sessionId != null && sessionId.length() > 0) {
            // Load session object from repository
            // sessie = gebruikerRepository.lees(1L);

            // Load associated user from session
            // if (null != sessie) {
            gebruiker = gebruikerRepository.lees(1L);
            // }
        }

        LOGGER.debug("ophalen uit gebruikerRepository");
        Gebruiker gebruiker2 = gebruikerRepository.lees(1L);
        LOGGER.debug(gebruiker2);

        chain.doFilter(request, response);
        // Set security context
        // request.setSecurityContext(new MySecurityContext(sessie, gebruiker));
        // return response;
    }

    @Override
    public void destroy() {
        // TODO Auto-generated method stub

    }

    @Override
    public void init(FilterConfig arg0) throws ServletException {
        // TODO Auto-generated method stub

    }

}
