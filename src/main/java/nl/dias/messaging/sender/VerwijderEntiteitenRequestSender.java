package nl.dias.messaging.sender;

import nl.dias.messaging.SoortEntiteitEnEntiteitId;
import nl.lakedigital.as.messaging.request.VerwijderEntiteitenRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

@Component
public class VerwijderEntiteitenRequestSender extends AbstractSender<VerwijderEntiteitenRequest, SoortEntiteitEnEntiteitId> {
    private static final Logger LOGGER = LoggerFactory.getLogger(VerwijderEntiteitenRequestSender.class);

    public VerwijderEntiteitenRequestSender() {
        this.jmsTemplate = null;
        this.LOGGER_ = LOGGER;
    }

    public VerwijderEntiteitenRequestSender(final JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
        this.LOGGER_ = LOGGER;
        this.clazz = VerwijderEntiteitenRequest.class;
    }

    @Override
    public VerwijderEntiteitenRequest maakMessage(SoortEntiteitEnEntiteitId soortEntiteitEnEntiteitId) {
        VerwijderEntiteitenRequest verwijderEntiteitenRequest = new VerwijderEntiteitenRequest();

        verwijderEntiteitenRequest.setSoortEntiteit(soortEntiteitEnEntiteitId.getSoortEntiteit());
        verwijderEntiteitenRequest.setEntiteitId(soortEntiteitEnEntiteitId.getEntiteitId());

        return verwijderEntiteitenRequest;
    }
}
