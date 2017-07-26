package nl.dias.web.filter;

import nl.dias.repository.GebruikerRepository;
import nl.dias.service.AuthorisatieService;
import nl.dias.service.GebruikerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.ext.Provider;
import java.io.IOException;


@Provider
public class AuthorisatieFilterIT implements Filter {
    private static final Logger LOGGER = LoggerFactory.getLogger(AuthorisatieFilterIT.class);

    private GebruikerService gebruikerService = null;
    private GebruikerRepository gebruikerRepository = null;
    private AuthorisatieService authorisatieService = null;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        LOGGER.debug("In AuthorisatieFilter");
        HttpServletRequest req = (HttpServletRequest) request;

        init();

        //            Gebruiker gebruikerUitDatabase=gebruikerRepository.lees(3L);
        //
        //        Sessie sessie = new Sessie();
        //        sessie.setBrowser(req.getHeader("user-agent"));
        //        sessie.setIpadres(req.getRemoteAddr());
        //        sessie.setDatumLaatstGebruikt(new Date());
        //        sessie.setGebruiker(gebruikerUitDatabase);
        //        sessie.setSessie(UUID.randomUUID().toString());
        //
        //        gebruikerService.opslaan(sessie);
        //
        //        gebruikerUitDatabase.getSessies().add(sessie);
        //        gebruikerUitDatabase.setAchternaam("tt");
        //
        //        gebruikerService.opslaan(gebruikerUitDatabase);
        //
        //        LOGGER.debug("sessie id " + sessie.getSessie() + " in de request plaatsen");
        //        LOGGER.debug(ReflectionToStringBuilder.toString(sessie));

        req.getSession().setAttribute("id", 3L);

        opruimen();

        chain.doFilter(request, response);
    }

    private void init() {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
        gebruikerRepository = (GebruikerRepository) applicationContext.getBean("gebruikerRepository");
        gebruikerService = (GebruikerService) applicationContext.getBean("gebruikerService");
        authorisatieService = (AuthorisatieService) applicationContext.getBean("authorisatieService");
    }

    private void opruimen() {
        gebruikerService = null;
        gebruikerRepository = null;
        authorisatieService = null;
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
