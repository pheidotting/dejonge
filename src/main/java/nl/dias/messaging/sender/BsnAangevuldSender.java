package nl.dias.messaging.sender;

import com.google.gson.Gson;
import nl.lakedigital.as.messaging.BsnAangevuld;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;

@Component
public class BsnAangevuldSender {
    private final Gson gson = new Gson();
    private final JmsTemplate jmsTemplate;

    public BsnAangevuldSender() {
        this.jmsTemplate = null;
    }

    public BsnAangevuldSender(final JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }

    public void send(final BsnAangevuld bsnAangevuld) {
        jmsTemplate.send(new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {
                TextMessage message = session.createTextMessage(gson.toJson(bsnAangevuld));

                return message;
            }
        });
    }
}
