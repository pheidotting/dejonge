package nl.dias.web.authorisatie;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import nl.dias.domein.Beheerder;
import nl.dias.domein.Gebruiker;
import nl.dias.domein.Medewerker;
import nl.dias.domein.Relatie;
import nl.dias.domein.json.IngelogdeGebruiker;
import nl.dias.domein.json.Inloggen;
import nl.dias.domein.json.JsonFoutmelding;
import nl.dias.service.AuthorisatieService;
import nl.lakedigital.loginsystem.exception.NietGevondenException;
import nl.lakedigital.loginsystem.exception.OnjuistWachtwoordException;

import org.apache.log4j.Logger;

import com.sun.jersey.api.core.InjectParam;

@Path("/authorisatie")
public class AuthorisatieController {
    private final static Logger LOGGER = Logger.getLogger(AuthorisatieController.class);
    @Context
    private HttpServletRequest httpServletRequest;
    @Context
    private HttpServletResponse httpServletResponse;
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

        return authorisatieService.getIngelogdeGebruiker(httpServletRequest, sessie, httpServletRequest.getRemoteAddr());

    }
}
