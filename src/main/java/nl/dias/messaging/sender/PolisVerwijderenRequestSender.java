package nl.dias.messaging.sender;

import nl.lakedigital.as.messaging.request.PolisVerwijderenRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PolisVerwijderenRequestSender extends AbstractSender<PolisVerwijderenRequest, List<Long>> {
    private static final Logger LOGGER = LoggerFactory.getLogger(PolisVerwijderenRequestSender.class);

    public PolisVerwijderenRequestSender() {
        this.jmsTemplate = null;
        this.LOGGER_ = LOGGER;
    }

    public PolisVerwijderenRequestSender(final JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
        this.LOGGER_ = LOGGER;
        this.clazz = PolisVerwijderenRequest.class;
    }

    @Override
    public PolisVerwijderenRequest maakMessage(List<Long> ids) {
        PolisVerwijderenRequest polisVerwijderenRequest = new PolisVerwijderenRequest();
        polisVerwijderenRequest.setIds(ids);

        return polisVerwijderenRequest;
    }
}
