package nl.dias.messaging.sender;

import com.google.gson.Gson;
import nl.lakedigital.as.messaging.AdresAangevuld;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;

@Component
public class AdresAangevuldSender {
    private final Gson gson = new Gson();
    private final JmsTemplate jmsTemplate;

    public AdresAangevuldSender() {
        this.jmsTemplate = null;
    }

    public AdresAangevuldSender(final JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }

    public void send(final AdresAangevuld adresAangevuld) {
        jmsTemplate.send(new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {
                TextMessage message = session.createTextMessage(gson.toJson(adresAangevuld));

                return message;
            }
        });
    }
}
