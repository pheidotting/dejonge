package nl.dias.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import nl.dias.domein.Bedrijf;
import nl.dias.domein.Beheerder;
import nl.dias.domein.Gebruiker;
import nl.dias.domein.Medewerker;
import nl.dias.domein.Relatie;
import nl.dias.domein.json.IngelogdeGebruiker;
import nl.dias.domein.json.Inloggen;
import nl.dias.domein.json.JsonBedrijf;
import nl.dias.domein.json.JsonFoutmelding;
import nl.dias.domein.json.JsonLijstRelaties;
import nl.dias.domein.json.JsonRelatie;
import nl.dias.repository.KantoorRepository;
import nl.dias.service.BedrijfService;
import nl.dias.service.GebruikerService;
import nl.dias.web.mapper.BedrijfMapper;
import nl.dias.web.mapper.RelatieMapper;
import nl.lakedigital.loginsystem.exception.NietGevondenException;
import nl.lakedigital.loginsystem.exception.OnjuistWachtwoordException;

import org.apache.log4j.Logger;

import com.sun.jersey.api.core.InjectParam;

@Path("/gebruiker")
public class GebruikerController {
    private final static Logger LOGGER = Logger.getLogger(GebruikerController.class);

    @Context
    private HttpServletRequest httpServletRequest;
    @Context
    private HttpServletResponse httpServletResponse;

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

    @POST
    @Path("/inloggen")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response inloggen(Inloggen inloggen) {
        try {
            LOGGER.debug("Inloggen");
            authorisatieService.inloggen(inloggen.getIdentificatie(), inloggen.getWachtwoord(), inloggen.isOnthouden(), httpServletRequest, httpServletResponse);
        } catch (OnjuistWachtwoordException | NietGevondenException e) {
            LOGGER.debug("Onjuist wachtwoord of Gebruiker niet gevonden", e);
            return Response.status(401).entity(new JsonFoutmelding(e.getMessage())).build();
        }

        return Response.status(200).entity(new JsonFoutmelding()).build();
    }

    @GET
    @Path("/uitloggen")
    @Produces(MediaType.APPLICATION_JSON)
    public Response uitloggen() {
        authorisatieService.uitloggen(httpServletRequest);
        return Response.status(200).entity(new JsonFoutmelding()).build();
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

    @GET
    @Path("/ingelogdeGebruiker")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getIngelogdeGebruiker() {
        LOGGER.debug("Ophalen ingelogde gebruiker");

        Gebruiker gebruiker = authorisatieService.getIngelogdeGebruiker(httpServletRequest, httpServletRequest.getSession().getAttribute("sessie").toString(), httpServletRequest.getRemoteAddr());

        IngelogdeGebruiker ingelogdeGebruiker = new IngelogdeGebruiker();
        if (gebruiker != null) {
            ingelogdeGebruiker.setGebruikersnaam(gebruiker.getNaam());
            if (gebruiker instanceof Beheerder) {
            } else if (gebruiker instanceof Medewerker) {
                ingelogdeGebruiker.setKantoor(((Medewerker) gebruiker).getKantoor().getNaam());
            } else if (gebruiker instanceof Relatie) {
                ingelogdeGebruiker.setKantoor(((Relatie) gebruiker).getKantoor().getNaam());
            }

            return Response.status(200).entity(ingelogdeGebruiker).build();
        }

        return Response.status(401).entity(null).build();
    }

    @POST
    @Path("/opslaan")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response opslaan(JsonRelatie jsonRelatie) {
        LOGGER.debug("Opslaan " + jsonRelatie);

        try {
            Relatie relatie = relatieMapper.mapVanJson(jsonRelatie);
            relatie.setKantoor(kantoorRepository.getIngelogdKantoor());

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
    @Consumes(MediaType.APPLICATION_JSON)
    public Response verwijderen(Long id) {
        LOGGER.debug("Verwijderen Relatie met id " + id);

        gebruikerService.verwijder(id);

        return Response.status(200).build();
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

    @GET
    @Path("/isIngelogd")
    @Produces(MediaType.APPLICATION_JSON)
    public boolean isIngelogd() {
        LOGGER.debug("is gebruiker ingelogd");

        Gebruiker gebruiker = authorisatieService.getIngelogdeGebruiker(httpServletRequest, httpServletRequest.getSession().getAttribute("sessie").toString(), httpServletRequest.getRemoteAddr());

        return gebruiker != null;
    }

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
}
