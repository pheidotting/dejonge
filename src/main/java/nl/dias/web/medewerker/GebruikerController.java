package nl.dias.web.medewerker;

import com.sun.jersey.api.core.InjectParam;
import nl.dias.domein.Bedrijf;
import nl.dias.domein.Gebruiker;
import nl.dias.domein.Medewerker;
import nl.dias.domein.Relatie;
import nl.dias.domein.json.JsonBedrijf;
import nl.dias.domein.json.JsonFoutmelding;
import nl.dias.domein.json.JsonLijstRelaties;
import nl.dias.domein.json.JsonRelatie;
import nl.dias.repository.KantoorRepository;
import nl.dias.service.AuthorisatieService;
import nl.dias.service.BedrijfService;
import nl.dias.service.GebruikerService;
import nl.dias.web.mapper.BedrijfMapper;
import nl.dias.web.mapper.RelatieMapper;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/gebruiker")
public class GebruikerController {
    private final static Logger LOGGER = Logger.getLogger(GebruikerController.class);

    @InjectParam
    private GebruikerService gebruikerService;
    @InjectParam
    private KantoorRepository kantoorRepository;
    @InjectParam
    private BedrijfService bedrijfService;
    @InjectParam
    private RelatieMapper relatieMapper;
    @InjectParam
    private BedrijfMapper bedrijfMapper;
    @InjectParam
    private AuthorisatieService authorisatieService;
    @Context
    private HttpServletRequest httpServletRequest;

    @GET
    @Path("/converteren")
    @Produces(MediaType.APPLICATION_JSON)
    public String converteren() {
        gebruikerService.converteren();
        return "ok";
    }

    @GET
    @Path("/lees")
    @Produces(MediaType.APPLICATION_JSON)
    public JsonRelatie lees(@QueryParam("id") String id) {
        LOGGER.debug("Ophalen Relatie met id : " + id);

        JsonRelatie jsonRelatie = null;
        if (id != null && !"0".equals(id.trim())) {
            Relatie relatie = (Relatie) gebruikerService.lees(Long.parseLong(id));

            LOGGER.debug("Opgehaald : " + relatie);

            jsonRelatie = relatieMapper.mapNaarJson(relatie);
        } else {
            jsonRelatie = new JsonRelatie();
        }

        LOGGER.debug("Naar de front-end : " + jsonRelatie);

        return jsonRelatie;
    }

    @GET
    @Path("/lijstRelaties")
    @Produces(MediaType.APPLICATION_JSON)
    public JsonLijstRelaties lijstRelaties(@QueryParam("weglaten") String weglaten) {
        LOGGER.debug("Ophalen lijst met alle Relaties");

        Long idWeglaten = null;
        if (weglaten != null) {
            LOGGER.debug("id " + weglaten + " moet worden weggelaten");
            idWeglaten = Long.parseLong(weglaten);
        }

        JsonLijstRelaties lijst = new JsonLijstRelaties();

        for (Gebruiker r : gebruikerService.alleRelaties(kantoorRepository.getIngelogdKantoor())) {
            if (idWeglaten == null || !idWeglaten.equals(r.getId())) {
                lijst.getJsonRelaties().add(relatieMapper.mapNaarJson((Relatie) r));
            }
        }
        LOGGER.debug("Opgehaald, lijst met " + lijst.getJsonRelaties().size() + " relaties");

        return lijst;
    }

    @POST
    @Path("/opslaan")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response opslaan(JsonRelatie jsonRelatie) {
        LOGGER.info("Opslaan " + jsonRelatie);

        try {
            Relatie relatie = relatieMapper.mapVanJson(jsonRelatie);
            LOGGER.debug("Uit mapper " + relatie);
            String sessie = null;
            if (httpServletRequest.getSession().getAttribute("sessie") != null && !"".equals(httpServletRequest.getSession().getAttribute("sessie"))) {
                sessie = httpServletRequest.getSession().getAttribute("sessie").toString();
            }

            Medewerker medewerker = (Medewerker) authorisatieService.getIngelogdeGebruiker(httpServletRequest, sessie, httpServletRequest.getRemoteAddr());

            relatie.setKantoor(kantoorRepository.lees(medewerker.getKantoor().getId()));

            LOGGER.debug("Opslaan Relatie met id " + relatie.getId());

            gebruikerService.opslaan(relatie);

            LOGGER.debug("Relatie met id " + relatie.getId() + " opgeslagen");
            return Response.status(202).entity(new JsonFoutmelding()).build();
        } catch (Exception e) {
            LOGGER.error("Fout bij opslaan Relatie", e);
            return Response.status(500).entity(new JsonFoutmelding(e.getMessage())).build();
        }
    }

    @POST
    @Path("/opslaanBedrijf")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response opslaanBedrijf(JsonBedrijf jsonBedrijf) {
        Relatie relatie = (Relatie) gebruikerService.lees(Long.parseLong(jsonBedrijf.getRelatie()));

        try {
            Bedrijf bedrijf = bedrijfMapper.mapVanJson(jsonBedrijf);
            bedrijf.setRelatie(relatie);
            bedrijfService.opslaan(bedrijf);

            relatie.getBedrijven().add(bedrijf);

            gebruikerService.opslaan(relatie);

            return Response.status(200).entity(new JsonFoutmelding()).build();
        } catch (Exception e) {
            LOGGER.error("Fout bij opslaan Bedrijf", e);
            return Response.status(500).entity(new JsonFoutmelding(e.getMessage())).build();
        }
    }

    @GET
    @Path("/verwijderen")
    @Produces(MediaType.APPLICATION_JSON)
    public Response verwijderen(@QueryParam("id") Long id) {
        LOGGER.debug("Verwijderen Relatie met id " + id);

        gebruikerService.verwijder(id);

        return Response.status(200).build();
    }

    @GET
    @Path("/zoekOpNaamAdresOfPolisNummer")
    @Produces(MediaType.APPLICATION_JSON)
    public JsonLijstRelaties zoekOpNaamAdresOfPolisNummer(@QueryParam("zoekTerm") String zoekTerm) {
        LOGGER.info("zoekOpNaamAdresOfPolisNummer met zoekterm " + zoekTerm);

        JsonLijstRelaties lijst = new JsonLijstRelaties();

        if (zoekTerm == null || "".equals(zoekTerm)) {
            // for (Gebruiker r :
            // gebruikerService.alleRelaties(kantoorRepository.getIngelogdKantoor()))
            // {
            // lijst.getJsonRelaties().add(relatieMapper.mapNaarJson((Relatie)
            // r));
            // }
        } else {
            for (Gebruiker r : gebruikerService.zoekOpNaamAdresOfPolisNummer(zoekTerm)) {
                lijst.getJsonRelaties().add(relatieMapper.mapNaarJson((Relatie) r));
            }
        }
        return lijst;
    }

    // @GET
    // @Path("/toevoegenRelatieRelatie")
    // @Produces(MediaType.TEXT_PLAIN)
    // public String toevoegenRelatieRelatie(@QueryParam("idAanToevoegen")
    // String sidAanToevoegen, @QueryParam("idToeTeVoegen") String
    // sidToeTeVoegen, @QueryParam("soortRelatie") String soortRelatie) {
    // String melding = "";
    // Long idAanToevoegen = null;
    // Long idToeTeVoegen = null;
    // try {
    // idAanToevoegen = Long.parseLong(sidAanToevoegen);
    // idToeTeVoegen = Long.parseLong(sidToeTeVoegen);
    // } catch (NumberFormatException e) {
    // logger.info(e.getMessage());
    // melding = "Numeriek veld verwacht";
    // }
    //
    // if (soortRelatie == null || soortRelatie.equals("")) {
    // melding = "De soort Relatie moet worden ingevuld.";
    // }
    //
    // if (melding.equals("")) {
    // Relatie aanToevoegen = (Relatie) gebruikerService.lees(idAanToevoegen);
    // Relatie toeTeVoegen = (Relatie) gebruikerService.lees(idToeTeVoegen);
    //
    // OnderlingeRelatieSoort soort = null;
    // for (OnderlingeRelatieSoort s : OnderlingeRelatieSoort.values()) {
    // if (soortRelatie.equals(s.toString())) {
    // soort = s;
    // break;
    // }
    // }
    //
    // aanToevoegen.getOnderlingeRelaties().add(new
    // OnderlingeRelatie(aanToevoegen, toeTeVoegen, soort));
    //
    // gebruikerService.opslaan(aanToevoegen);
    // }
    //
    // Gson gson = new Gson();
    // String messages = null;
    // if (melding.equals("")) {
    // Relatie relatie = (Relatie) gebruikerService.lees(idAanToevoegen);
    // // try {
    // // messages = gson.toJson(new RelatieJson(relatie.clone()));
    // // } catch (CloneNotSupportedException e) {
    // // logger.error(e.getMessage());
    // // }
    // } else {
    // messages = gson.toJson(melding);
    // }
    //
    // return messages;
    // }

    public void setGebruikerService(GebruikerService gebruikerService) {
        this.gebruikerService = gebruikerService;
    }

    public void setRelatieMapper(RelatieMapper relatieMapper) {
        this.relatieMapper = relatieMapper;
    }

    public void setBedrijfService(BedrijfService bedrijfService) {
        this.bedrijfService = bedrijfService;
    }

    public void setBedrijfMapper(BedrijfMapper bedrijfMapper) {
        this.bedrijfMapper = bedrijfMapper;
    }

    public void setKantoorRepository(KantoorRepository kantoorRepository) {
        this.kantoorRepository = kantoorRepository;
    }

    public void setAuthorisatieService(AuthorisatieService authorisatieService) {
        this.authorisatieService = authorisatieService;
    }

    public void setHttpServletRequest(HttpServletRequest httpServletRequest) {
        this.httpServletRequest = httpServletRequest;
    }
}
