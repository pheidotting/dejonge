package nl.dias.web.authorisatie;

import com.sun.jersey.api.core.InjectParam;
import nl.dias.domein.Beheerder;
import nl.dias.domein.Gebruiker;
import nl.dias.domein.Medewerker;
import nl.dias.domein.Relatie;
import nl.dias.domein.json.IngelogdeGebruiker;
import nl.dias.domein.json.Inloggen;
import nl.dias.domein.json.JsonFoutmelding;
import nl.dias.repository.GebruikerRepository;
import nl.dias.service.AuthorisatieService;
import nl.lakedigital.loginsystem.exception.NietGevondenException;
import nl.lakedigital.loginsystem.exception.OnjuistWachtwoordException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/authorisatie")
public class AuthorisatieController {
    private final static Logger LOGGER = LoggerFactory.getLogger(AuthorisatieController.class);
    @Context
    private HttpServletRequest httpServletRequest;
    @Context
    private HttpServletResponse httpServletResponse;
    @InjectParam
    private AuthorisatieService authorisatieService;
    @InjectParam
    private GebruikerRepository gebruikerRepository;

    @POST
    @Path("/inloggen")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response inloggen(Inloggen inloggen) {
        try {
            LOGGER.debug("Inloggen");
            authorisatieService.inloggen(inloggen.getIdentificatie().trim(), inloggen.getWachtwoord(), inloggen.isOnthouden(), httpServletRequest, httpServletResponse);
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
    @Path("/ingelogdeGebruiker")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getIngelogdeGebruiker() {
        LOGGER.debug("Ophalen ingelogde gebruiker");

        Gebruiker gebruiker = getGebruiker();

        IngelogdeGebruiker ingelogdeGebruiker = new IngelogdeGebruiker();
        if (gebruiker != null) {
            ingelogdeGebruiker.setGebruikersnaam(gebruiker.getNaam());
            if (gebruiker instanceof Beheerder) {
                // Nog te doen :)
            } else if (gebruiker instanceof Medewerker) {
                ingelogdeGebruiker.setKantoor(((Medewerker) gebruiker).getKantoor().getNaam());
            } else if (gebruiker instanceof Relatie) {
                ingelogdeGebruiker.setKantoor(((Relatie) gebruiker).getKantoor().getNaam());
            }

            return Response.status(200).entity(ingelogdeGebruiker).build();
        }

        return Response.status(401).entity(null).build();
    }

    @GET
    @Path("/isIngelogd")
    @Produces(MediaType.APPLICATION_JSON)
    public Response isIngelogd() {
        LOGGER.debug("is gebruiker ingelogd");

        Gebruiker gebruiker = getGebruiker();

        if (gebruiker == null) {
            return Response.status(401).entity(false).build();
        } else {
            return Response.status(200).entity(true).build();
        }
    }

    private Gebruiker getGebruiker() {
        String sessie = null;
        if (httpServletRequest.getSession().getAttribute("sessie") != null && !"".equals(httpServletRequest.getSession().getAttribute("sessie"))) {
            sessie = httpServletRequest.getSession().getAttribute("sessie").toString();
        }

        Gebruiker gebruiker = authorisatieService.getIngelogdeGebruiker(httpServletRequest, sessie, httpServletRequest.getRemoteAddr());

        if (gebruiker == null) {
            String sessieHeader = httpServletRequest.getHeader("sessieCode");

            try {
                gebruiker = gebruikerRepository.zoekOpSessieEnIpadres(sessieHeader, "0:0:0:0:0:0:0:1");
            } catch (NietGevondenException nge) {
                LOGGER.trace("Gebruiker dus niet gevonden", nge);
            }
        }

        return gebruiker;
    }
}
