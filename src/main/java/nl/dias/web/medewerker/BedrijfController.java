package nl.dias.web.medewerker;

import com.sun.jersey.api.core.InjectParam;
import nl.dias.domein.Bedrijf;
import nl.dias.domein.Relatie;
import nl.dias.domein.json.JsonBedrijf;
import nl.dias.domein.json.JsonFoutmelding;
import nl.dias.service.BedrijfService;
import nl.dias.service.GebruikerService;
import nl.dias.web.mapper.BedrijfMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

<<<<<<< HEAD
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sun.jersey.api.core.InjectParam;
=======
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
>>>>>>> 561c015bc16347b4be76e8f0

@Path("/bedrijf")
public class BedrijfController {
    private final static Logger LOGGER = LoggerFactory.getLogger(BedrijfController.class);

    @InjectParam
    private BedrijfService bedrijfService;
    @InjectParam
    private GebruikerService gebruikerService;
    @InjectParam
    private BedrijfMapper bedrijfMapper;

    @GET
    @Path("/lees")
    @Produces(MediaType.APPLICATION_JSON)
    public JsonBedrijf lees(@QueryParam("id") String id) {
        Bedrijf bedrijf = null;
        if (id == null || "0".equals(id)) {
            bedrijf = new Bedrijf();
        } else {
            bedrijf = bedrijfService.lees(Long.valueOf(id));
        }
        return bedrijfMapper.mapNaarJson(bedrijf);
    }

    @GET
    @Path("/lijst")
    @Produces(MediaType.APPLICATION_JSON)
    public List<JsonBedrijf> lijst(@QueryParam("relatieId") String relatieId) {
        Relatie relatie = (Relatie) gebruikerService.lees(Long.valueOf(relatieId));

        Set<Bedrijf> bedrijven = new HashSet<>();
        for (Bedrijf bedrijf : bedrijfService.alleBedrijvenBijRelatie(relatie)) {
            bedrijven.add(bedrijf);
        }

        return bedrijfMapper.mapAllNaarJson(bedrijven);
    }

    @GET
    @Path("/verwijder")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response verwijder(@QueryParam("id") Long id) {
        LOGGER.debug("verwijderen Polis met id " + id);
        try {
            bedrijfService.verwijder(id);
        } catch (IllegalArgumentException e) {
            LOGGER.error("Fout bij verwijderen Polis", e);
            return Response.status(500).entity(new JsonFoutmelding(e.getMessage())).build();
        }
        return Response.status(202).entity(new JsonFoutmelding()).build();
    }

    public void setBedrijfService(BedrijfService bedrijfService) {
        this.bedrijfService = bedrijfService;
    }

    public void setGebruikerService(GebruikerService gebruikerService) {
        this.gebruikerService = gebruikerService;
    }

    public void setBedrijfMapper(BedrijfMapper bedrijfMapper) {
        this.bedrijfMapper = bedrijfMapper;
    }
}
