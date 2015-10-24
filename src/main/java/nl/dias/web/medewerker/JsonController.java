package nl.dias.web.medewerker;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.core.InjectParam;
import com.sun.jersey.api.json.JSONConfiguration;
import nl.dias.domein.StatusSchade;
import nl.dias.domein.VerzekeringsMaatschappij;
import nl.dias.domein.json.JsonSoortSchade;
import nl.dias.service.SchadeService;
import nl.dias.service.VerzekeringsMaatschappijService;
import nl.dias.web.mapper.SoortSchadeMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.List;

@Path("/overig")
public class JsonController {
    private static final Logger LOGGER = LoggerFactory.getLogger(JsonController.class);

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

        LOGGER.debug("{}",ret);

        return ret;
    }

    @GET
    @Path("/extraInfo")
    @Produces(MediaType.TEXT_PLAIN)
    public String extraInfo() {

        String omgeving = System.getProperty("omgeving");

        LOGGER.debug("omgeving " + omgeving);

        return omgeving;
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

    @GET
    @Path("/ophalenAdresOpPostcode")
    @Produces(MediaType.APPLICATION_JSON)
    public String ophalenAdresOpPostcode(@QueryParam("postcode") String postcode, @QueryParam("huisnummer") String huisnummer) {
        String adres = "http://api.postcodeapi.nu/" + postcode + "/" + huisnummer;

        ClientConfig clientConfig = new DefaultClientConfig();
        clientConfig.getFeatures().put(JSONConfiguration.FEATURE_POJO_MAPPING, Boolean.TRUE);
        Client client = Client.create(clientConfig);
        WebResource webResource = client.resource(adres);
        ClientResponse response = webResource.header("Api-Key", "0eaff635fe5d9be439582d7501027f34d5a3ca9d").accept("application/x-www-form-urlencoded; charset=UTF-8").get(ClientResponse.class);
//        if (response.getStatus() != 200) {
//            throw new RuntimeException("Failed : HTTP error code : " + response.getStatus());
//        }

        return response.getEntity(String.class);
    }

    public void setMaatschappijService(VerzekeringsMaatschappijService maatschappijService) {
        this.maatschappijService = maatschappijService;
    }
}
