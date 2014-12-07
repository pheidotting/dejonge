package nl.dias.web.medewerker;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import nl.dias.messaging.sender.AanmakenTaakSender;
import nl.lakedigital.as.messaging.AanmakenTaak;
import nl.lakedigital.as.messaging.AanmakenTaak.SoortTaak;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

@Path("/mq")
public class MQController {
    private static final Logger LOGGER = Logger.getLogger(MQController.class);

    @GET
    @Path("/mq")
    @Produces(MediaType.APPLICATION_JSON)
    public String mq() {
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        AanmakenTaakSender sender = (AanmakenTaakSender) context.getBean("aanmakenTaakSender");
        AanmakenTaak aanmakenTaak = new AanmakenTaak();
        aanmakenTaak.setSoort(SoortTaak.INVULLEN_BELASTINGPAPIEREN);

        sender.send(aanmakenTaak);

        return "ok";

    }
}
