package nl.dias.dias_web.medewerker;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import nl.dias.domein.json.JsonBedrijf;

@Path("/bedrijf")
public class BedrijfController {
    @GET
    @Path("/lijst")
    @Produces(MediaType.APPLICATION_JSON)
    public List<JsonBedrijf> lijst() {
        List<JsonBedrijf> bedrijven = new ArrayList<>();

        JsonBedrijf jsonBedrijf1 = new JsonBedrijf();
        jsonBedrijf1.setId("2");
        jsonBedrijf1.setHuisnummer("666");
        jsonBedrijf1.setKvk("kvknummer");
        jsonBedrijf1.setNaam("Fa. List & Bedrog");
        jsonBedrijf1.setPlaats("Verwegistan");
        jsonBedrijf1.setPostcode("1234AA");
        jsonBedrijf1.setStraat("StraatBedrijf");

        JsonBedrijf jsonBedrijf2 = new JsonBedrijf();
        jsonBedrijf2.setId("1");
        jsonBedrijf2.setHuisnummer("33");
        jsonBedrijf2.setKvk("kvknummer");
        jsonBedrijf2.setNaam("NaamBedrijf 1");
        jsonBedrijf2.setPlaats("PlaatsBedrijf");
        jsonBedrijf2.setPostcode("1234AA");
        jsonBedrijf2.setStraat("StraatBedrijf");
        jsonBedrijf2.setToevoeging("A");

        JsonBedrijf dummy = new JsonBedrijf();
        dummy.setNaam("Kies (evt.) een Bedrijf uit de lijst");
        bedrijven.add(dummy);

        bedrijven.add(jsonBedrijf1);
        bedrijven.add(jsonBedrijf2);

        return bedrijven;
    }

}
