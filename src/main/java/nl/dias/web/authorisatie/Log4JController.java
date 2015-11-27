package nl.dias.web.authorisatie;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.ws.rs.FormParam;

@RequestMapping("/log4j")
@Controller
public class Log4JController {
    private final static Logger LOGGER = LoggerFactory.getLogger(Log4JController.class);

    @RequestMapping(method = RequestMethod.POST, value = "/log4javascript")
    @ResponseBody
    public void log4javascript(@FormParam("logger") String logger, @FormParam("timestamp") String timestamp, @FormParam("level") String level, @FormParam("url") String url, @FormParam("message") String message, @FormParam("layout") String layout) {

        if ("debug".equalsIgnoreCase(level)) {
            LOGGER.debug("Message {}, URL {}", message, url);
        } else if ("info".equalsIgnoreCase(level)) {
            LOGGER.info("Message {}, URL {}", message, url);
        } else if ("warn".equalsIgnoreCase(level)) {
            LOGGER.warn("Message {}, URL {}", message, url);
        } else if ("error".equalsIgnoreCase(level)) {
            LOGGER.error("Message {}, URL {}", message, url);
        } else if ("fatal".equalsIgnoreCase(level)) {
            LOGGER.error("Message {}, URL {}", message, url);
        } else {
            LOGGER.trace("Message {}, URL {}", message, url);
        }
    }

}
