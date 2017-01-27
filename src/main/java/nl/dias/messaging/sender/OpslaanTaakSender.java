package nl.dias.messaging.sender;

import nl.lakedigital.as.messaging.TaakOpslaan;
import nl.lakedigital.as.messaging.domain.Taak;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

@Component
public class OpslaanTaakSender extends AbstractSender<TaakOpslaan, Taak> {
    private static final Logger LOGGER = LoggerFactory.getLogger(OpslaanTaakSender.class);

    public OpslaanTaakSender() {
        this.jmsTemplate = null;
        this.LOGGER_ = LOGGER;
    }

    public OpslaanTaakSender(final JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
        this.LOGGER_ = LOGGER;
    }

    @Override
    public TaakOpslaan maakMessage(Taak taak) {
        TaakOpslaan taakOpslaan = new TaakOpslaan();

        taakOpslaan.setTaak(taak);

        return taakOpslaan;
    }
}
