package nl.dias.web.filter;

import nl.dias.domein.Gebruiker;
import nl.dias.domein.Sessie;
import nl.dias.repository.GebruikerRepository;
import nl.lakedigital.loginsystem.exception.NietGevondenException;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.inject.Inject;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

@Component
public class HeaderFilter extends OncePerRequestFilter {
    private static final Logger LOGGER = LoggerFactory.getLogger(HeaderFilter.class);

    @Inject
    private GebruikerRepository gebruikerRepository = new GebruikerRepository();

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        LOGGER.debug("In HeaderFilter");
        HttpServletRequest req = (HttpServletRequest) httpServletRequest;

        String sessieHeader = req.getHeader("sessieCode");
        Gebruiker gebruiker = null;
        if (sessieHeader != null) {
            LOGGER.debug("sessieHeader : {}", sessieHeader);
            try {
                LOGGER.debug("Gebruiker opzoeken");
                gebruiker = gebruikerRepository.zoekOpSessieEnIpadres(sessieHeader, "0:0:0:0:0:0:0:1");

                if(gebruiker==null) {
                    LOGGER.debug("Geen Gebruiker gevonden");
                }else{
                    LOGGER.debug("Gebruiker met id {} gevonden", gebruiker.getId());

                    Sessie sessie=null;

                    LOGGER.debug("Sessies");
                    for(Sessie sessie1 : gebruiker.getSessies()){
                        LOGGER.debug(ReflectionToStringBuilder.toString(sessie1));
                        if(sessie1.getSessie().equals(sessieHeader)){
                            sessie = sessie1;
                        }
                    }
                    LOGGER.debug("/ Sessies");

//                    Sessie sessie = getFirst(filter(gebruiker.getSessies(), new SessieOpSessiecodePredicate(sessieHeader)), null);

if(sessie!=null){
    LOGGER.debug(ReflectionToStringBuilder.toString(sessie));
                    LOGGER.debug("Sessie weer opslaan met bijgewerkte datum");
                    req.getSession().setAttribute("sessie", sessie.getSessie());
                    sessie.setDatumLaatstGebruikt(new Date());
                    gebruikerRepository.opslaan(sessie);

                    LOGGER.debug("Gebruiker opgehaald : {}", gebruiker != null ? gebruiker.getId() : "0");
                }else{
    LOGGER.debug("Geen sessie gevonden");
                }
                }
            } catch (NietGevondenException nge) {
                LOGGER.trace("Niet gevonden blijkbaar ", nge);
            }
        }
        LOGGER.debug("Verder met het filter");
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }
}
