package nl.dias.messaging.sender;

import javax.inject.Named;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;

import nl.lakedigital.as.messaging.EmailadresAangevuld;

import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

import com.google.gson.Gson;

@Named
public class EmailAdresAangevuldSender {
    private final Gson gson = new Gson();
    private final JmsTemplate jmsTemplate;

    public EmailAdresAangevuldSender(final JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }

    public void send(final EmailadresAangevuld emailadresAangevuld) {
        jmsTemplate.send(new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {
                TextMessage message = session.createTextMessage(gson.toJson(emailadresAangevuld));

                return message;
            }
        });
    }
}