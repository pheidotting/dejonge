package nl.dias.messaging.sender;

import com.google.gson.Gson;
import nl.lakedigital.as.messaging.AanmakenTaak;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;

@Component
public class AanmakenTaakSender {
    private final Gson gson = new Gson();
    private final JmsTemplate jmsTemplate;

    public AanmakenTaakSender() {
        this.jmsTemplate = null;
    }

    public AanmakenTaakSender(final JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }

    public void send(final AanmakenTaak aanmakenTaak) {
        jmsTemplate.send(new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {
                TextMessage message = session.createTextMessage(gson.toJson(aanmakenTaak));

                return message;
            }
        });
    }
}
