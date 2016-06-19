//package nl.dias.web.medewerker;
//
//import nl.dias.repository.KantoorRepository;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.ResponseBody;
//
//import javax.inject.Inject;
//
//@RequestMapping("/selenium")
//@Controller
//public class SeleniumController {
//
//    @Inject
//    private KantoorRepository kantoorService;
//
//    private static String wachtwoord;
//
//    @RequestMapping(method = RequestMethod.GET, value = "/leegAlles")
//    @ResponseBody
//    public String leegAlles() {
//        if (!isSeleniumOmgeving()) {
//            return "false";
//        }
//
//        kantoorService.wisAlles();
//
//        return "true";
//    }
//
//    @RequestMapping(method = RequestMethod.GET, value = "/wachtwoord")
//    @ResponseBody
//    public String wachtwoord() {
//        if (!isSeleniumOmgeving()) {
//            return "false";
//        }
//
//        return SeleniumController.wachtwoord;
//    }
//
//    private boolean isSeleniumOmgeving() {
//        return "fat".equalsIgnoreCase(System.getProperty("omgeving"));
//    }
//
//    public static void setWachtwoord(String wachtwoord) {
//        SeleniumController.wachtwoord = wachtwoord;
//    }
//
//    public void setKantoorService(KantoorRepository kantoorService) {
//        this.kantoorService = kantoorService;
//    }
//}
