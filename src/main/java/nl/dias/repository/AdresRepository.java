package nl.dias.repository;

import nl.dias.domein.Adres;
import nl.dias.web.SoortEntiteit;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.resource.transaction.spi.TransactionStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class AdresRepository {
    private final static Logger LOGGER = LoggerFactory.getLogger(AdresRepository.class);

    @Autowired
    private SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    protected Session getSession() {
        return sessionFactory.getCurrentSession();
    }

    protected Session getEm() {
        return sessionFactory.getCurrentSession();
    }

    protected Transaction getTransaction() {
        Transaction transaction = getSession().getTransaction();
        if (transaction.getStatus() != TransactionStatus.ACTIVE) {
            transaction.begin();
        }

        return transaction;
    }

    public List<Adres> alles(SoortEntiteit soortEntiteit, Long entiteitId) {
        getTransaction();

        Query query = getEm().getNamedQuery("Adres.zoekAdressgenBijEntiteit");
        query.setParameter("soortEntiteit", soortEntiteit);
        query.setParameter("entiteitId", entiteitId);

        List<Adres> adressen = query.list();

        getTransaction().commit();

        return adressen;
    }

    public Adres lees(Long id) {
        getTransaction();

        Adres adres = getEm().get(Adres.class, id);

        getTransaction().commit();

        return adres;
    }

    public void opslaan(Adres adres) {
        getTransaction();

        LOGGER.info("Opslaan {}", ReflectionToStringBuilder.toString(adres, ToStringStyle.SHORT_PREFIX_STYLE));
        if (adres.getId() == null) {
            getSession().save(adres);
        } else {
            getSession().merge(adres);
        }

        getTransaction().commit();
    }

    public void verwijder(Adres adres) {
        getTransaction();

        getEm().delete(adres);

        getTransaction().commit();
    }
}
