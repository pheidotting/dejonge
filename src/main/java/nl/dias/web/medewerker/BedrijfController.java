package nl.dias.web.medewerker;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import nl.dias.domein.Bedrijf;
import nl.dias.domein.Relatie;
import nl.dias.domein.json.JsonBedrijf;
import nl.dias.service.BedrijfService;
import nl.dias.service.GebruikerService;
import nl.dias.web.mapper.BedrijfMapper;

import com.sun.jersey.api.core.InjectParam;

@Path("/bedrijf")
public class BedrijfController {
    @InjectParam
    private BedrijfService bedrijfService;
    @InjectParam
    private GebruikerService gebruikerService;
    @InjectParam
    private BedrijfMapper bedrijfMapper;

    @GET
    @Path("/lijst")
    @Produces(MediaType.APPLICATION_JSON)
    public List<JsonBedrijf> lijst(@QueryParam("relatieId") String relatieId) {
        Relatie relatie = (Relatie) gebruikerService.lees(Long.valueOf(relatieId));

        Set<Bedrijf> bedrijven = new HashSet<>();
        Bedrijf dummy = new Bedrijf();
        dummy.setNaam("Kies (evt.) een Bedrijf uit de lijst");
        bedrijven.add(dummy);
        for (Bedrijf bedrijf : bedrijfService.alleBedrijvenBijRelatie(relatie)) {
            bedrijven.add(bedrijf);
        }

        return bedrijfMapper.mapAllNaarJson(bedrijven);
    }
}
