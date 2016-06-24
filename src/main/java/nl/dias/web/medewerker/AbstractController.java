package nl.dias.web.medewerker;

import nl.dias.inloggen.SessieHolder;
import nl.dias.service.AuthorisatieService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

public abstract class AbstractController {
    private final static Logger LOGGER = LoggerFactory.getLogger(AbstractController.class);

    @Inject
    protected AuthorisatieService authorisatieService;

    protected void zetSessieWaarden(HttpServletRequest httpServletRequest) {
        SessieHolder.get().setIngelogdeGebruiker(getIngelogdeGebruiker(httpServletRequest));
        SessieHolder.get().setTrackAndTraceId(getTrackAndTraceId(httpServletRequest));
    }

    protected Long getIngelogdeGebruiker(HttpServletRequest httpServletRequest) {
        String sessie = null;
        if (httpServletRequest.getSession().getAttribute("sessie") != null && !"".equals(httpServletRequest.getSession().getAttribute("sessie"))) {
            sessie = httpServletRequest.getSession().getAttribute("sessie").toString();
        }

        Long ingelogdeGebruiker = authorisatieService.getIngelogdeGebruiker(httpServletRequest, sessie, httpServletRequest.getRemoteAddr()).getId();

        LOGGER.debug("DJFC Ingelogde Gebruiker opgehaald : {}", ingelogdeGebruiker);

        return ingelogdeGebruiker;

    }

    protected String getTrackAndTraceId(HttpServletRequest httpServletRequest) {
        String tati = httpServletRequest.getHeader("trackAndTraceId");
        LOGGER.debug("DJFC Track And Trace Id : {}", tati);

        return tati;
    }
}
