package nl.dias.dias_web.medewerker;

import nl.dias.domein.json.*;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/gebruiker")
public class GebruikerControllerTest {
    public static Long id;
    public static JsonRelatie jsonRelatie;
    public static JsonLijstRelaties jsonLijstRelaties;

    @GET
    @Path("/lees")
    @Produces("application/json")
    public JsonRelatie lees(@QueryParam("id") Long id) {
        if (id == 0) {
            return new JsonRelatie();
        }
        if (GebruikerControllerTest.jsonRelatie == null) {
            JsonRelatie jsonRelatie = new JsonRelatie();
            jsonRelatie.setAchternaam("Heidotting");
            jsonRelatie.setAdresOpgemaakt("Eemslandweg 41");
            jsonRelatie.setBsn("103127586");
            jsonRelatie.setBurgerlijkeStaat("Ongehuwd");
            jsonRelatie.setGeboorteDatumOpgemaakt("06-09-1979");
            jsonRelatie.setGeslacht("Man");
            //            jsonRelatie.setHuisnummer("41");
            jsonRelatie.setId(3L);
            jsonRelatie.setIdentificatie("patrick@heidotting.nl");
            //            jsonRelatie.setPlaats("Zwartemeer");
            //            jsonRelatie.setPostcode("7894AB");
            //            jsonRelatie.setStraat("Eemslandweg");
            jsonRelatie.setVoornaam("Patrick");

            jsonRelatie.setZakelijkeKlant(true);

            JsonRekeningNummer jsonRekeningNummer = new JsonRekeningNummer();
            jsonRekeningNummer.setBic("bic");
            jsonRekeningNummer.setRekeningnummer("rekeningnummer");
            jsonRelatie.getRekeningnummers().add(jsonRekeningNummer);

            JsonRekeningNummer jsonRekeningNummer2 = new JsonRekeningNummer();
            jsonRekeningNummer2.setBic("bic2");
            jsonRekeningNummer2.setRekeningnummer("rekeningnummer2");
            jsonRelatie.getRekeningnummers().add(jsonRekeningNummer2);

            JsonTelefoonnummer jsonTelefoonnummer1 = new JsonTelefoonnummer();
            jsonTelefoonnummer1.setSoort("Werk");
            jsonTelefoonnummer1.setTelefoonnummer("telefoonnummer1");
            jsonRelatie.getTelefoonnummers().add(jsonTelefoonnummer1);

            JsonTelefoonnummer jsonTelefoonnummer2 = new JsonTelefoonnummer();
            jsonTelefoonnummer2.setSoort("Vast");
            jsonTelefoonnummer2.setTelefoonnummer("telefoonnummer2");
            jsonRelatie.getTelefoonnummers().add(jsonTelefoonnummer2);

            // JsonPolis jsonPolis1 = new JsonPolis();
            // jsonPolis1.setId(1L);
            // jsonPolis1.setBetaalfrequentie("Maandelijks");
            // jsonPolis1.setPolisNummer("12345");
            // jsonPolis1.setIngangsDatum(new
            // LocalDate().toString("yyyy-MM-dd"));
            // jsonPolis1.setMaatschappij("Fa. List & Bedrog");
            // jsonPolis1.setPremie("100 euro");
            // jsonPolis1.setProlongatieDatum(new
            // LocalDate().toString("yyyy-MM-dd"));
            // jsonPolis1.setSoort("Autoverzekering");
            // jsonPolis1.setWijzigingsDatum(new
            // LocalDate().toString("yyyy-MM-dd"));
            //
            // jsonRelatie.getPolissen().add(jsonPolis1);
            //
            // JsonPolis jsonPolis2 = new JsonPolis();
            // jsonPolis2.setId(2L);
            // jsonPolis2.setBetaalfrequentie("Maandelijks");
            // jsonPolis2.setPolisNummer("12345");
            // jsonPolis2.setIngangsDatum(new
            // LocalDate().toString("yyyy-MM-dd"));
            // jsonPolis2.setMaatschappij("Fa. List & Bedrog");
            // jsonPolis2.setPremie("100 euro");
            // jsonPolis2.setProlongatieDatum(new
            // LocalDate().toString("yyyy-MM-dd"));
            // jsonPolis2.setSoort("Woonhuisverzekering");
            // jsonPolis2.setWijzigingsDatum(new
            // LocalDate().toString("yyyy-MM-dd"));
            //
            // JsonBijlage jsonBijlage = new JsonBijlage();
            // jsonBijlage.setId("1");
            // jsonBijlage.setBestandsNaam("Polis-5408096516-2.pdf");
            // jsonBijlage.setSoortBijlage("Polis");
            // jsonPolis2.getBijlages().add(jsonBijlage);
            //
            // JsonBijlage jsonBijlage1 = new JsonBijlage();
            // jsonBijlage1.setId("2");
            // jsonBijlage1.setBestandsNaam("Polis-5408096516-2.pdf");
            // jsonBijlage1.setSoortBijlage("Polis");
            // jsonPolis2.getBijlages().add(jsonBijlage1);
            //
            // jsonRelatie.getPolissen().add(jsonPolis2);
            //
            // JsonBedrijf jsonBedrijf1 = new JsonBedrijf();
            // jsonBedrijf1.setId("2");
            // jsonBedrijf1.setHuisnummer("666");
            // jsonBedrijf1.setKvk("kvknummer");
            // jsonBedrijf1.setNaam("Fa. List & Bedrog");
            // jsonBedrijf1.setPlaats("Verwegistan");
            // jsonBedrijf1.setPostcode("1234AA");
            // jsonBedrijf1.setStraat("StraatBedrijf");
            //
            // JsonBedrijf jsonBedrijf2 = new JsonBedrijf();
            // jsonBedrijf2.setId("1");
            // jsonBedrijf2.setHuisnummer("33");
            // jsonBedrijf2.setKvk("kvknummer");
            // jsonBedrijf2.setNaam("NaamBedrijf 1");
            // jsonBedrijf2.setPlaats("PlaatsBedrijf");
            // jsonBedrijf2.setPostcode("1234AA");
            // jsonBedrijf2.setStraat("StraatBedrijf");
            // jsonBedrijf2.setToevoeging("A");
            //
            // jsonRelatie.getBedrijven().add(jsonBedrijf1);
            // jsonRelatie.getBedrijven().add(jsonBedrijf2);
            //
            // jsonRelatie.getLijstBijlages().add(jsonBijlage);
            // jsonRelatie.getLijstBijlages().add(jsonBijlage1);
            //
            // JsonSchade jsonSchade = new JsonSchade();
            // jsonSchade.setDatumAfgehandeld("01-07-2014");
            // jsonSchade.setDatumTijdMelding("30-06-2014 09:12");
            // jsonSchade.setDatumTijdSchade("29-06-2014 10:23");
            // jsonSchade.setEigenRisico("100 euro");
            // jsonSchade.setLocatie("Ergens tussen de weg en de straat");
            // jsonSchade.setOmschrijving("Tja, toen was het ineens boem!");
            // jsonSchade.setPolis(1L);
            // jsonSchade.setSchadeNummerMaatschappij("schadeNummerMaatschappij");
            // jsonSchade.setSchadeNummerTussenPersoon("schadeNummerTussenPersoon");
            // jsonSchade.setSoortSchade("Diefstal");
            // jsonSchade.setStatusSchade("statusSchade");
            //
            // JsonBijlage jsonBijlage2 = new JsonBijlage();
            // jsonBijlage2.setBestandsNaam("schadeformulier.pdf");
            // jsonBijlage2.setSoortBijlage("Schade");
            // jsonBijlage2.setId("3");
            //
            // JsonBijlage jsonBijlage3 = new JsonBijlage();
            // jsonBijlage3.setBestandsNaam("politiedossier.pdf");
            // jsonBijlage3.setSoortBijlage("Schade");
            // jsonBijlage3.setId("4");
            //
            // jsonSchade.getBijlages().add(jsonBijlage2);
            // jsonSchade.getBijlages().add(jsonBijlage3);
            //
            // jsonRelatie.getLijstBijlages().add(jsonBijlage2);
            // jsonRelatie.getLijstBijlages().add(jsonBijlage3);
            //
            // JsonOpmerking jsonOpmerking1 = new JsonOpmerking();
            // jsonOpmerking1.setId(1L);
            // jsonOpmerking1.setOpmerking("Dit is een opmerking");
            // jsonOpmerking1.setTijd("01-02-2014 09:55");
            // jsonOpmerking1.setMedewerker("Patrick Heidotting");
            // jsonSchade.getOpmerkingen().add(jsonOpmerking1);
            //
            // JsonOpmerking jsonOpmerking2 = new JsonOpmerking();
            // jsonOpmerking2.setId(1L);
            // jsonOpmerking2
            // .setOpmerking("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Etiam nec purus sollicitudin, volutpat velit ac, ultricies ipsum. Proin sem lacus, interdum vel tellus a, hendrerit ultricies orci. Nulla facilisi. Phasellus lobortis, lectus non luctus consectetur, tortor eros pulvinar mauris, in hendrerit augue enim vitae lectus. Vestibulum elementum malesuada pretium. Cras ac tempus lorem. Quisque ultrices nunc id posuere pretium. Sed varius consequat nunc. Praesent odio lacus, pretium euismod mi interdum, pellentesque fringilla risus. Quisque a leo dolor. Nulla adipiscing tempus eros, ac sodales lorem congue vel. Aenean scelerisque quam diam, vitae molestie eros porta nec. Vivamus pretium sed augue nec.");
            // jsonOpmerking2.setTijd("02-03-2014 13:45");
            // jsonOpmerking2.setMedewerker("Gerben Zwiers");
            // jsonSchade.getOpmerkingen().add(jsonOpmerking2);
            //
            // jsonRelatie.getSchades().add(jsonSchade);

            GebruikerControllerTest.jsonRelatie = jsonRelatie;
        }
        return GebruikerControllerTest.jsonRelatie;
    }

    @GET
    @Path("/lijstRelaties")
    @Produces(MediaType.APPLICATION_JSON)
    public JsonLijstRelaties lijstRelaties(@QueryParam("weglaten") String weglaten) {
        if (GebruikerControllerTest.jsonLijstRelaties == null) {
            JsonRelatie jsonRelatie = new JsonRelatie();
            jsonRelatie.setId(1L);
            jsonRelatie.setVoornaam("voornaam1");
            jsonRelatie.setTussenvoegsel("tussenvoegsel1");
            jsonRelatie.setAchternaam("achternaam1");
            jsonRelatie.setGeboorteDatum("01-01-2001");
            jsonRelatie.setAdresOpgemaakt("adresOpgemaakt1");
            GebruikerControllerTest.jsonLijstRelaties = new JsonLijstRelaties();
            GebruikerControllerTest.jsonLijstRelaties.getJsonRelaties().add(jsonRelatie);
        }
        System.out.println(GebruikerControllerTest.jsonLijstRelaties);
        return GebruikerControllerTest.jsonLijstRelaties;
    }

    @POST
    @Path("/opslaan")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response opslaan(JsonRelatie jsonRelatie) {
        GebruikerControllerTest.jsonRelatie = jsonRelatie;

        // return Response.status(500).entity(new
        // JsonFoutmelding("jadajada")).build();
        return Response.status(202).entity(new JsonFoutmelding()).build();
    }

    @POST
    @Path("/opslaanBedrijf")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response opslaanBedrijf(JsonBedrijf jsonBedrijf) {
        System.out.println(jsonBedrijf);
        // return Response.status(500).entity(new
        // JsonFoutmelding("jadajada")).build();
        return Response.status(202).entity(new JsonFoutmelding()).build();
    }

    @GET
    @Path("/verwijderen")
    @Produces(MediaType.TEXT_PLAIN)
    public Response verwijderen(@QueryParam("id") Long id) {
        GebruikerControllerTest.id = id;

        return null;
    }

    @GET
    @Path("/toevoegenRelatieRelatie")
    @Produces(MediaType.TEXT_PLAIN)
    public String toevoegenRelatieRelatie(@QueryParam("idAanToevoegen") String sidAanToevoegen, @QueryParam("idToeTeVoegen") String sidToeTeVoegen, @QueryParam("soortRelatie") String soortRelatie) {
        return null;
    }

    //
    // @GET
    // @Path("/uitloggen")
    // @Produces(MediaType.TEXT_PLAIN)
    // public String loguit(@Context HttpServletRequest request) {
    // return null;
    // }

}
