package nl.dias.messaging.sender;

import com.google.gson.Gson;
import nl.dias.inloggen.SessieHolder;
import nl.lakedigital.as.messaging.AbstractMessage;
import org.slf4j.Logger;
import org.springframework.jms.core.JmsTemplate;

import javax.jms.TextMessage;

public abstract class AbstractSender<M extends AbstractMessage, T extends Object> {
    protected static Logger LOGGER_;
    private final Gson gson = new Gson();
    protected JmsTemplate jmsTemplate;

    public AbstractSender() {
        this.jmsTemplate = null;
    }

    public AbstractSender(final JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }

    public abstract M maakMessage(T t);

    public void send(T t) {
        M m = maakMessage(t);

        send(m);
    }

    ;

    public void send(final AbstractMessage abstractMessage) {
        jmsTemplate.send(session -> {
            abstractMessage.setTrackAndTraceId(SessieHolder.get().getTrackAndTraceId());
            abstractMessage.setIngelogdeGebruiker(SessieHolder.get().getIngelogdeGebruiker());

            TextMessage message = session.createTextMessage(gson.toJson(abstractMessage));

            LOGGER_.debug("Verzenden message {}", message.getText());
            return message;
        });
    }
}
