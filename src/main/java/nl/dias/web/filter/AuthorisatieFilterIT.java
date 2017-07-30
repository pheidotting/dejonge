package nl.dias.web.filter;

import nl.dias.repository.GebruikerRepository;
import nl.dias.service.AuthorisatieService;
import nl.dias.service.GebruikerService;
import nl.lakedigital.djfc.reflection.ReflectionToStringBuilder;
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

        LOGGER.debug(ReflectionToStringBuilder.toString(req));

        init();

        LOGGER.debug("Request set Attribute");

        req.getSession().setAttribute("id", 3L);

        LOGGER.debug("Done request set Attribute");

        opruimen();

        LOGGER.debug("Opgeruimd, verder de filterchain in");

        chain.doFilter(request, response);
    }

    private void init() {
        LOGGER.debug("1");
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:applicationContext-local-it.xml");
        LOGGER.debug("2");
        gebruikerRepository = (GebruikerRepository) applicationContext.getBean("gebruikerRepository");
        LOGGER.debug("3");
        gebruikerService = (GebruikerService) applicationContext.getBean("gebruikerService");
        LOGGER.debug("4");
        authorisatieService = (AuthorisatieService) applicationContext.getBean("authorisatieService");
        LOGGER.debug("5");
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
