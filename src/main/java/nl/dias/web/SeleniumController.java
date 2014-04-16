package nl.dias.web;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import nl.dias.service.KantoorService;

import org.springframework.transaction.annotation.Transactional;

import com.sun.jersey.api.core.InjectParam;

@Path("/selenium")
public class SeleniumController {

    @InjectParam
    private KantoorService kantoorService;

    private static String wachtwoord;

    @GET
    @Path("/leegAlles")
    @Produces(MediaType.TEXT_PLAIN)
    @Transactional
    public String leegAlles() {
        if (!isSeleniumOmgeving()) {
            return "false";
        }

        kantoorService.wisAlles();

        return "true";
    }

    @GET
    @Path("/wachtwoord")
    @Produces(MediaType.TEXT_PLAIN)
    public String wachtwoord() {
        if (!isSeleniumOmgeving()) {
            return "false";
        }

        return SeleniumController.wachtwoord;
    }

    private boolean isSeleniumOmgeving() {
        return "fat".equalsIgnoreCase(System.getProperty("omgeving"));
    }

    public static void setWachtwoord(String wachtwoord) {
        SeleniumController.wachtwoord = wachtwoord;
    }

    public void setKantoorService(KantoorService kantoorService) {
        this.kantoorService = kantoorService;
    }
}
