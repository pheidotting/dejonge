//package nl.dias.web;
//
//import javax.ws.rs.core.Cookie;
//
//import nl.dias.domein.Gebruiker;
//import nl.dias.domein.Kantoor;
//import nl.dias.domein.Medewerker;
//import nl.dias.domein.Relatie;
//import nl.dias.domein.Sessie;
//import nl.dias.exception.NietIngelogdException;
//import nl.dias.service.GebruikerService;
//import nl.lakedigital.loginsystem.exception.NietGevondenException;
//
//import org.apache.log4j.Logger;
//
//import com.sun.jersey.api.core.InjectParam;
//
//public abstract class AbstractController {
//    private Logger logger = Logger.getLogger(this.getClass());
//
//    @InjectParam
//    private GebruikerService gebruikerService;
//
//    // protected Gebruiker ingelogdeGebruiker;
//    private Kantoor ingelogdKantoor;
//    private boolean tweedeKeer;
//
//    protected void checkIngelogd(HttpServletRequest request) throws NietIngelogdException {
//        try {
//            if (getIngelogdeGebruiker(request) == null) {
//                throw new NietIngelogdException();
//            }
//        } catch (NullPointerException e) {
//            logger.debug(e.getMessage());
//            throw new NietIngelogdException();
//        }
//    }
//
//    public Gebruiker getIngelogdeGebruiker(HttpServletRequest request) throws NietIngelogdException {
//        Gebruiker ingelogdeGebruiker = null;
//        // if(ingelogdeGebruiker == null){
//        try {
//            Cookie gevondenCookie = null;
//            for (Cookie c : request.getCookies()) {
//                if ("inloggen".equals(c.getName())) {
//                    gevondenCookie = c;
//                    break;
//                }
//            }
//
//            if (gevondenCookie != null) {
//                ingelogdeGebruiker = gebruikerService.zoekOpCookieCode(gevondenCookie.getValue());
//                ingelogdKantoor = ((Medewerker) ingelogdeGebruiker).getKantoor();
//            }
//
//            String sessie = request.getSession().getId();
//            String ipadres = request.getRemoteAddr();
//            String browser = request.getHeader("user-agent");
//
//            Sessie gevondenSessie = null;
//            if (ingelogdeGebruiker == null) {
//                try {
//                    ingelogdeGebruiker = gebruikerService.zoekOpSessieEnIpadres(sessie, ipadres);
//                    ingelogdKantoor = ((Medewerker) ingelogdeGebruiker).getKantoor();
//                } catch (Exception e) {
//                    logger.debug(e.getMessage());
//                    int teller = 0;
//                    while (ingelogdeGebruiker == null && teller < 5) {
//                        herLezen();
//                        teller++;
//                    }
//                }
//            }
//            gevondenSessie = zoekSessie(ingelogdeGebruiker, sessie, ipadres);
//
//            if (gevondenCookie != null) {
//                for (Sessie s : ingelogdeGebruiker.getSessies()) {
//                    if (s.getCookieCode() != null && s.getCookieCode().equals(gevondenCookie.getValue())) {
//                        gevondenSessie = s;
//                        break;
//                    }
//                }
//            }
//
//            herLezen();
//
//            if (gevondenSessie == null && ingelogdeGebruiker != null) {
//                gevondenSessie = new Sessie();
//                // ingelogdeGebruiker =
//                // gebruikerService.zoekOpSessieEnIpadres(sessie, ipadres);
//                logger.debug("ingelogdeGebruiker " + ingelogdeGebruiker.getId());
//                ingelogdeGebruiker = gebruikerService.lees(ingelogdeGebruiker.getId());
//                ingelogdeGebruiker.getSessies().add(gevondenSessie);
//
//                if (ingelogdeGebruiker instanceof Medewerker) {
//                    logger.debug("ingelogde gebruiker is een Medewerker");
//                    ingelogdKantoor = ((Medewerker) ingelogdeGebruiker).getKantoor();
//                } else if (ingelogdeGebruiker instanceof Relatie) {
//                    logger.debug("ingelogde gebruiker is een Relatie");
//                    ingelogdKantoor = ((Relatie) ingelogdeGebruiker).getKantoor();
//                }
//                logger.debug("kantoor gevonden " + ingelogdKantoor);
//
//                gevondenSessie.setGebruiker(ingelogdeGebruiker);
//                gevondenSessie.setSessie(sessie);
//                gevondenSessie.setIpadres(ipadres);
//                gevondenSessie.setBrowser(browser);
//
//                if (((Medewerker) ingelogdeGebruiker).getKantoor() == null) {
//                    ((Medewerker) ingelogdeGebruiker).setKantoor(ingelogdKantoor);
//                }
//                gebruikerService.opslaan(ingelogdeGebruiker);
//            }
//
//        } catch (NietGevondenException | NullPointerException e) {
//            if (!tweedeKeer) {
//                tweedeKeer = true;
//                return getIngelogdeGebruiker(request);
//            }
//            throw new NietIngelogdException();
//        }
//        if (ingelogdeGebruiker == null) {
//            throw new NietIngelogdException();
//        }
//        // }
//        return ingelogdeGebruiker;
//    }
//
//    private void herLezen() {
//        // if (ingelogdeGebruiker != null && ((Medewerker)
//        // ingelogdeGebruiker).getKantoor() == null) {
//        // ingelogdeGebruiker =
//        // gebruikerService.lees(ingelogdeGebruiker.getId());
//        // ingelogdKantoor = ((Medewerker) ingelogdeGebruiker).getKantoor();
//        // }
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
//    public void uitloggen(HttpServletRequest request) {
//        String sessie = request.getSession().getId();
//        String ipadres = request.getRemoteAddr();
//
//        logger.info("uitloggen");
//
//        Gebruiker gebruiker = null;
//        try {
//            gebruiker = getIngelogdeGebruiker(request);
//        } catch (NietIngelogdException e) {
//            logger.info("uitloggen, maar geen ingelogde gebruiker gevonden");
//        }
//
//        if (gebruiker != null) {
//            logger.info("ingelogde gebruiker: " + gebruiker.getIdentificatie());
//        }
//
//        logger.info("actieve sessie verwijderen");
//
//        int aantalSessies = 0;
//        if (gebruiker != null && gebruiker.getSessies() != null) {
//            aantalSessies = gebruiker.getSessies().size();
//
//            gebruiker.getSessies().remove(zoekSessie(gebruiker, sessie, ipadres));
//        }
//
//        if (gebruiker != null) {
//            if (gebruiker.getSessies().size() == aantalSessies) {
//                logger.error("aantal sessies bij " + gebruiker.getIdentificatie() + " is gelijk gebleven.");
//            }
//
//            gebruikerService.opslaan(gebruiker);
//
//            logger.info("vernieuwde gebruiker " + gebruiker.getIdentificatie() + " opgeslagen");
//
//        }
//        // ingelogdeGebruiker = null;
//    }
//
//    public GebruikerService getGebruikerService() {
//        return gebruikerService;
//    }
//
//    public void setGebruikerService(GebruikerService gebruikerService) {
//        this.gebruikerService = gebruikerService;
//    }
//
//    public Kantoor getIngelogdKantoor() {
//        return ingelogdKantoor;
//    }
//
//    public void setIngelogdKantoor(Kantoor ingelogdKantoor) {
//        this.ingelogdKantoor = ingelogdKantoor;
//    }
// }
