package nl.dias.web.medewerker;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import nl.dias.domein.StatusSchade;
import nl.dias.domein.VerzekeringsMaatschappij;
import nl.dias.domein.json.JsonSoortSchade;
import nl.dias.service.SchadeService;
import nl.dias.service.VerzekeringsMaatschappijService;
import nl.dias.web.mapper.SoortSchadeMapper;

import org.apache.log4j.Logger;

import com.sun.jersey.api.core.InjectParam;

@Path("/overig")
public class JsonController {
    private static final Logger LOGGER = Logger.getLogger(JsonController.class);

    @InjectParam
    private VerzekeringsMaatschappijService maatschappijService;
    @InjectParam
    private SchadeService schadeService;
    @InjectParam
    private SoortSchadeMapper soortSchadeMapper;

    @GET
    @Path("/lijstVerzekeringsMaatschappijen")
    @Produces(MediaType.APPLICATION_JSON)
    public List<String> lijstVerzekeringsMaatschappijen() {

        LOGGER.debug("ophalen lijst met VerzekeringsMaatschappijen");

        List<VerzekeringsMaatschappij> lijst = maatschappijService.alles();

        LOGGER.debug("Gevonden, " + lijst.size() + " VerzekeringsMaatschappijen");

        List<String> ret = new ArrayList<>();
        ret.add("Kies een maatschappij...");

        for (VerzekeringsMaatschappij vm : lijst) {
            ret.add(vm.getNaam());
        }

        LOGGER.debug(ret);

        return ret;
    }

    @GET
    @Path("/extraInfo")
    @Produces(MediaType.TEXT_PLAIN)
    public String extraInfo() {

        String omgeving = System.getProperty("omgeving");

        LOGGER.debug("omgeving " + omgeving);
        String ret = null;

        if ("PRD".equals(omgeving)) {
            ret = "DIAS";
        } else {
            ret = "DIAS " + omgeving;
        }

        return ret;
    }

    @GET
    @Path("/soortenSchade")
    @Produces(MediaType.APPLICATION_JSON)
    public List<JsonSoortSchade> soortenSchade(@QueryParam("query") String query) {
        return soortSchadeMapper.mapAllNaarJson(schadeService.soortenSchade(query));
    }

    @GET
    @Path("/lijstStatusSchade")
    @Produces(MediaType.APPLICATION_JSON)
    public List<String> lijstStatusSchade() {
        List<StatusSchade> lijst = schadeService.getStatussen();

        List<String> ret = new ArrayList<String>();

        for (StatusSchade statusSchade : lijst) {
            ret.add(statusSchade.getStatus());
        }

        return ret;
    }

    public void setMaatschappijService(VerzekeringsMaatschappijService maatschappijService) {
        this.maatschappijService = maatschappijService;
    }
}
