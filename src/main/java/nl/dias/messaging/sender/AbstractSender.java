package nl.dias.messaging.sender;

import nl.dias.inloggen.SessieHolder;
import nl.lakedigital.as.messaging.AbstractMessage;
import org.slf4j.Logger;
import org.springframework.jms.core.JmsTemplate;

import javax.jms.TextMessage;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.StringWriter;

public abstract class AbstractSender<M extends AbstractMessage, T extends Object> {
    protected static Logger LOGGER_;
    protected JmsTemplate jmsTemplate;
    protected Class<M> clazz;

    public AbstractSender() {
        this.jmsTemplate = null;
    }

    public AbstractSender(final JmsTemplate jmsTemplate, Class<M> clazz) {
        this.jmsTemplate = jmsTemplate;
        this.clazz = clazz;
    }

    public abstract M maakMessage(T t);

    public void send(T t) {
        M m = maakMessage(t);

        send(m);
    }

    public void send(final AbstractMessage abstractMessage) {
        jmsTemplate.send(session -> {
            try {
                abstractMessage.setTrackAndTraceId(SessieHolder.get().getTrackAndTraceId());
                abstractMessage.setIngelogdeGebruiker(SessieHolder.get().getIngelogdeGebruiker());

                JAXBContext jaxbContext = JAXBContext.newInstance(clazz);
                Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

                jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

                StringWriter sw = new StringWriter();

                jaxbMarshaller.marshal(abstractMessage, sw);

                TextMessage message = session.createTextMessage(sw.toString());

                LOGGER_.debug("Verzenden message {}", message.getText());
                return message;
            } catch (JAXBException e) {
                LOGGER_.error("{}", e);
            }
            return null;
        });
    }
}
