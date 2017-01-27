package nl.dias.web.medewerker;

import nl.dias.messaging.sender.OpslaanTaakSender;
import nl.lakedigital.as.messaging.domain.Taak;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RequestMapping("/taak")
@Controller
public class TaakController extends AbstractController {
    private static final Logger LOGGER = LoggerFactory.getLogger(TaakController.class);

    @Inject
    private OpslaanTaakSender opslaanAfgerondeTakenSender;

    @RequestMapping(method = RequestMethod.POST, value = "/opslaanAfgerondeTaken")
    @ResponseBody
    public void opslaanAfgerondeTaken(@RequestBody List<Taak> taaks, HttpServletRequest httpServletRequest) {
        zetSessieWaarden(httpServletRequest);

        for (Taak taak : taaks) {
            opslaanAfgerondeTakenSender.send(taak);
        }
    }
}
