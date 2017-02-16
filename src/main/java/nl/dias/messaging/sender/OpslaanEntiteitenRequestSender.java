package nl.dias.messaging.sender;

import nl.lakedigital.as.messaging.request.OpslaanEntiteitenRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

@Component
public class OpslaanEntiteitenRequestSender extends AbstractSender<OpslaanEntiteitenRequest, OpslaanEntiteitenRequest> {
    private static final Logger LOGGER = LoggerFactory.getLogger(PolisOpslaanRequestSender.class);

    public OpslaanEntiteitenRequestSender() {
        this.jmsTemplate = null;
        this.LOGGER_ = LOGGER;
    }

    public OpslaanEntiteitenRequestSender(final JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
        this.LOGGER_ = LOGGER;
        this.clazz = OpslaanEntiteitenRequest.class;
    }

    @Override
    public OpslaanEntiteitenRequest maakMessage(OpslaanEntiteitenRequest opslaanEntiteitenRequest) {
        return opslaanEntiteitenRequest;
    }
}
