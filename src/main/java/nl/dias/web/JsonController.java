package nl.dias.web;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import nl.dias.domein.VerzekeringsMaatschappij;
import nl.dias.service.VerzekeringsMaatschappijService;

import org.apache.log4j.Logger;

import com.sun.jersey.api.core.InjectParam;

@Path("/overig")
public class JsonController {
    private Logger logger = Logger.getLogger(this.getClass());

    @InjectParam
    private VerzekeringsMaatschappijService maatschappijService;

    @GET
    @Path("/lijstVerzekeringsMaatschappijen")
    @Produces(MediaType.TEXT_PLAIN)
    public List<VerzekeringsMaatschappij> lijstVerzekeringsMaatschappijen() {

        logger.debug("ophalen lijst met VerzekeringsMaatschappijen");

        List<VerzekeringsMaatschappij> lijst = maatschappijService.alles();
        // List<String> ret = new ArrayList<>();
        // ret.add("Kies een maatschappij...");

        // for (VerzekeringsMaatschappij vm : lijst) {
        // ret.add(vm.getNaam());
        // }

        return lijst;
    }

    @GET
    @Path("/extraInfo")
    @Produces(MediaType.TEXT_PLAIN)
    public String extraInfo() {

        String omgeving = System.getProperty("omgeving");

        logger.debug("omgeving " + omgeving);
        String ret = null;

        if (omgeving.equals("PRD")) {
            ret = "DIAS";
        } else {
            ret = "DIAS " + omgeving;
        }

        return ret;
    }

    public void setMaatschappijService(VerzekeringsMaatschappijService maatschappijService) {
        this.maatschappijService = maatschappijService;
    }
}
