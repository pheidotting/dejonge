//package nl.dias.web;
//
//import java.math.BigInteger;
//import java.security.SecureRandom;
//import java.util.ArrayList;
//import java.util.List;
//
//import javax.ws.rs.GET;
//import javax.ws.rs.Path;
//import javax.ws.rs.Produces;
//import javax.ws.rs.QueryParam;
//import javax.ws.rs.core.Context;
//import javax.ws.rs.core.Cookie;
//import javax.ws.rs.core.MediaType;
//
//import nl.dias.domein.Gebruiker;
//import nl.dias.domein.Medewerker;
//import nl.dias.domein.OnderlingeRelatie;
//import nl.dias.domein.OnderlingeRelatieSoort;
//import nl.dias.domein.Relatie;
//import nl.dias.domein.Sessie;
//import nl.dias.domein.json.RelatieJson;
//import nl.dias.exception.NietIngelogdException;
//import nl.dias.service.GebruikerService;
//import nl.lakedigital.loginsystem.exception.LeegVeldException;
//import nl.lakedigital.loginsystem.exception.NietGevondenException;
//import nl.lakedigital.loginsystem.exception.OnjuistWachtwoordException;
//
//import org.apache.log4j.Logger;
//
//import com.sun.jersey.api.core.InjectParam;
//
//@Path("/gebruiker")
//public class GebruikerController {// extends AbstractController {
//    private Logger logger = Logger.getLogger(this.getClass());
//
//    // private InlogUtil inlogUtil = new InlogUtil();
//    private String cookieCode;
//    private Cookie cookie;
//    @InjectParam
//    private GebruikerService gebruikerService;
//
//    @GET
//    @Path("/verwijder")
//    @Produces(MediaType.TEXT_PLAIN)
//    public String verwijder(@QueryParam("id") String id) {
//        logger.debug("Verwijder Medewerker met id " + id);
//
//        Medewerker medewerker = (Medewerker) gebruikerService.lees(Long.parseLong(id));
//
//        logger.debug("Medewerker gevonden");
//
//        gebruikerService.verwijder(medewerker);
//
//        return "";
//    }
//
//    @GET
//    @Path("/inloggen")
//    @Produces(MediaType.TEXT_PLAIN)
//    public String inloggen(@QueryParam("emailadres") String emailadres, @QueryParam("wachtwoord") String wachtwoord, @QueryParam("strOnthouden") String strOnthouden,
//            @Context HttpServletRequest request) {
//        Gson gson = new Gson();
//        String messages = null;
//
//        try {
//            logger.info("Proberen in te loggen met mail : " + emailadres);
//
//            boolean onthouden = false;
//            if (strOnthouden != null) {
//                onthouden = strOnthouden.equals("true");
//            }
//
//            logger.info("onthouden = " + onthouden);
//
//            if (emailadres == null || emailadres.equals("")) {
//                throw new LeegVeldException("E-mailadres");
//            }
//            if (wachtwoord == null || wachtwoord.equals("")) {
//                throw new LeegVeldException("wachtwoord");
//            }
//
//            @SuppressWarnings("serial")
//            Onderwerp onderwerp = new Onderwerp() {
//            };
//            onderwerp.setIdentificatie(emailadres);
//            onderwerp.setHashWachtwoord(wachtwoord);
//
//            Onderwerp gevonden;
//            try {
//                gevonden = gebruikerService.zoek(emailadres);
//            } catch (NietGevondenException e) {
//                gevonden = gebruikerService.zoek(emailadres);
//            }
//
//            if (gevonden != null) {
//                logger.info("gevonden id " + gevonden.getId());
//            } else {
//                logger.info(emailadres + ", werd niet gevonden");
//            }
//
//            HttpSession sessi = request.getSession();
//            String sess = sessi.getId();
//            String ipadres = request.getRemoteAddr();
//
//            inlogUtil.inloggen(onderwerp, ipadres, gevonden);
//
//            Sessie sessie = zoekSessie((Gebruiker) gevonden, sess, ipadres);
//
//            if (sessie == null) {
//                sessie = new Sessie();
//                sessie.setIpadres(request.getRemoteAddr());
//                sessie.setSessie(request.getSession().getId());
//            }
//            sessie.setBrowser(request.getHeader("user-agent"));
//
//            if (onthouden) {
//                sessie.setCookieCode(getCookieCode());
//                // response addCookie ( getCookie()
//            }
//
//            ((Gebruiker) gevonden).getSessies().add(sessie);
//            sessie.setGebruiker((Gebruiker) gevonden);
//
//            gebruikerService.opslaan((Gebruiker) gevonden);
//
//            messages = gson.toJson("ok");
//
//            // TODO mailtje de deur uit doen met het wachtwoord erin.
//
//        } catch (NietGevondenException | LeegVeldException | OnjuistWachtwoordException ex) {
//            messages = gson.toJson(ex.getMessage());
//        } catch (Exception ex) {
//            messages = gson.toJson(ex.getMessage());
//        } finally {
//            logger.info("Messages naar de front-end " + messages);
//        }
//
//        return messages;
//    }
//
//    @GET
//    @Path("/lees")
//    @Produces(MediaType.TEXT_PLAIN)
//    // @Produces("application/json")
//    public String lees(@QueryParam("id") String id) {
//        logger.debug("Ophalen Relatie met id : " + id);
//
//        Gson gson = new Gson();
//
//        Relatie relatie = (Relatie) gebruikerService.lees(Long.parseLong(id));
//        RelatieJson rj = null;
//        try {
//            rj = new RelatieJson(relatie.clone());
//        } catch (CloneNotSupportedException e) {
//            logger.error(e.getMessage());
//        }
//
//        return gson.toJson(rj);
//        // return rj;
//    }
//
//    @GET
//    @Path("/lijstRelaties")
//    @Produces(MediaType.TEXT_PLAIN)
//    public String lijstRelaties(@QueryParam("weglaten") String weglaten) {
//        logger.debug("Ophalen lijst met alle Relaties");
//
//        Long idWeglaten = null;
//        if (weglaten != null) {
//            logger.debug("id " + weglaten + " moet worden weggelaten");
//            idWeglaten = Long.parseLong(weglaten);
//        }
//
//        List<RelatieJson> lijst = new ArrayList<>();
//
//        for (Gebruiker r : gebruikerService.alleRelaties(getIngelogdKantoor())) {
//            if (idWeglaten == null || !idWeglaten.equals(r.getId())) {
//                try {
//                    lijst.add(new RelatieJson(((Relatie) r).clone()));
//                } catch (CloneNotSupportedException e) {
//                    logger.error(e.getMessage());
//                }
//            }
//        }
//        logger.debug("Opgehaald, lijst met " + lijst.size() + " relaties");
//
//        Gson gson = new Gson();
//
//        return gson.toJson(lijst);
//    }
//
//    @GET
//    @Path("/opslaan")
//    @Produces(MediaType.TEXT_PLAIN)
//    public String opslaan(@QueryParam("medewerker") String relatie) {
//        Gson gson = new Gson();
//
//        Relatie r = gson.fromJson(relatie, Relatie.class);
//
//        logger.debug("Opslaan Relatie met id " + r.getId());
//
//        gebruikerService.opslaan(r);
//
//        logger.debug("Relatie met id " + r.getId() + " opgeslagen");
//
//        return gson.toJson(new RelatieJson(r));
//    }
//
//    @GET
//    @Path("/verwijderen")
//    @Produces(MediaType.TEXT_PLAIN)
//    public String verwijderen(@QueryParam("medewerker") String relatie) {
//        logger.debug("Verwijderen Relatie met id " + relatie);
//
//        logger.debug("Eerst ophalen uit de database");
//        Relatie r = (Relatie) gebruikerService.lees(Long.parseLong(relatie));
//
//        gebruikerService.verwijder(r);
//
//        return "";
//    }
//
//    @GET
//    @Path("/toevoegenRelatieRelatie")
//    @Produces(MediaType.TEXT_PLAIN)
//    public String toevoegenRelatieRelatie(@QueryParam("idAanToevoegen") String sidAanToevoegen, @QueryParam("idToeTeVoegen") String sidToeTeVoegen, @QueryParam("soortRelatie") String soortRelatie) {
//        String melding = "";
//        Long idAanToevoegen = null;
//        Long idToeTeVoegen = null;
//        try {
//            idAanToevoegen = Long.parseLong(sidAanToevoegen);
//            idToeTeVoegen = Long.parseLong(sidToeTeVoegen);
//        } catch (NumberFormatException e) {
//            logger.info(e.getMessage());
//            melding = "Numeriek veld verwacht";
//        }
//
//        if (soortRelatie == null || soortRelatie.equals("")) {
//            melding = "De soort Relatie moet worden ingevuld.";
//        }
//
//        if (melding.equals("")) {
//            Relatie aanToevoegen = (Relatie) gebruikerService.lees(idAanToevoegen);
//            Relatie toeTeVoegen = (Relatie) gebruikerService.lees(idToeTeVoegen);
//
//            OnderlingeRelatieSoort soort = null;
//            for (OnderlingeRelatieSoort s : OnderlingeRelatieSoort.values()) {
//                if (soortRelatie.equals(s.toString())) {
//                    soort = s;
//                    break;
//                }
//            }
//
//            aanToevoegen.getOnderlingeRelaties().add(new OnderlingeRelatie(aanToevoegen, toeTeVoegen, soort));
//
//            gebruikerService.opslaan(aanToevoegen);
//        }
//
//        Gson gson = new Gson();
//        String messages = null;
//        if (melding.equals("")) {
//            Relatie relatie = (Relatie) gebruikerService.lees(idAanToevoegen);
//            try {
//                messages = gson.toJson(new RelatieJson(relatie.clone()));
//            } catch (CloneNotSupportedException e) {
//                logger.error(e.getMessage());
//            }
//        } else {
//            messages = gson.toJson(melding);
//        }
//
//        return messages;
//    }
//
//    @GET
//    @Path("/isIngelogd")
//    @Produces(MediaType.TEXT_PLAIN)
//    public String isIngelogd(@Context HttpServletRequest request) {
//        // logger.debug("is gebruiker ingelogd");
//
//        String messages = null;
//        try {
//            boolean ingelogd = false;
//            try {
//                checkIngelogd(request);
//                ingelogd = true;
//            } catch (NietIngelogdException e) {
//                logger.debug(e.getMessage());
//            }
//
//            Gson gson = new Gson();
//            messages = gson.toJson(ingelogd);
//        } catch (Exception ex) {
//            messages = "Error: " + ex.getMessage();
//        }
//
//        // logger.debug("naar front-end : " + messages);
//        return messages;
//    }
//
//    @GET
//    @Path("/uitloggen")
//    @Produces(MediaType.TEXT_PLAIN)
//    public String loguit(@Context HttpServletRequest request) {
//        logger.debug("uitloggen");
//
//        uitloggen(request);
//
//        return "";
//    }
//
//    protected Sessie zoekSessie(Gebruiker gebruiker, String sessie, String ipadres) {
//        Sessie gevondenSessie = null;
//
//        for (Sessie s : gebruiker.getSessies()) {
//            if (s.getSessie().equals(sessie) && s.getIpadres().equals(ipadres)) {
//                gevondenSessie = s;
//                break;
//            }
//        }
//
//        return gevondenSessie;
//    }
//
//    public void setInlogUtil(InlogUtil inlogUtil) {
//        this.inlogUtil = inlogUtil;
//    }
//
//    public String getCookieCode() {
//        if (cookieCode == null || cookieCode.equals("")) {
//            SecureRandom random = new SecureRandom();
//            cookieCode = new BigInteger(50, random).toString(64);
//        }
//        return cookieCode;
//    }
//
//    public void setCookieCode(String cookieCode) {
//        this.cookieCode = cookieCode;
//    }
//
//    public Cookie getCookie() {
//        if (cookie == null) {
//            cookie = new Cookie("inloggen", getCookieCode());
//            cookie.setDomain("dias");
//        }
//        return cookie;
//    }
//
//    public void setCookie(Cookie cookie) {
//        this.cookie = cookie;
//    }
// }
