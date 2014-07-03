package nl.dias.repository;

import java.util.List;

import javax.inject.Named;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import nl.dias.domein.Gebruiker;
import nl.dias.domein.Kantoor;
import nl.dias.domein.Relatie;
import nl.dias.domein.Sessie;
import nl.lakedigital.hulpmiddelen.repository.AbstractRepository;
import nl.lakedigital.loginsystem.exception.NietGevondenException;

import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Transactional;

@Named
public class GebruikerRepository extends AbstractRepository<Gebruiker> {
    private final Logger logger = Logger.getLogger(this.getClass());

    public GebruikerRepository() {
        super(Gebruiker.class);
        zetPersistenceContext("dias");
    }

    @Override
    public void setPersistenceContext(String persistenceContext) {
        zetPersistenceContext(persistenceContext);
    }

    @Transactional
    public List<Relatie> alleRelaties() {
        TypedQuery<Relatie> query = getEm().createQuery("select e from Relatie e", Relatie.class);
        List<Relatie> ret = query.getResultList();

        return ret;
    }

    @Transactional
    public List<Relatie> alleRelaties(Kantoor kantoor) {
        TypedQuery<Relatie> query = getEm().createNamedQuery("Relatie.zoekAllesVoorKantoor", Relatie.class);
        query.setParameter("kantoor", kantoor);

        return query.getResultList();
    }

    @Transactional
    public Gebruiker zoek(String emailadres) throws NietGevondenException {
        Gebruiker gebruiker = null;

        TypedQuery<Gebruiker> query = getEm().createNamedQuery("Gebruiker.zoekOpEmail", Gebruiker.class);
        query.setParameter("emailadres", emailadres);
        try {
            gebruiker = query.getSingleResult();
        } catch (NoResultException e) {
            logger.info("Niets gevonden", e);
            throw new NietGevondenException(emailadres);
        }

        return gebruiker;
    }

    public Gebruiker zoekOpSessieEnIpadres(String sessie, String ipadres) throws NietGevondenException {
        logger.debug("zoekOpSessieEnIpadres(" + sessie + " , " + ipadres + ")");

        Gebruiker gebruiker = null;

        TypedQuery<Gebruiker> query = getEm().createNamedQuery("Gebruiker.zoekOpSessieEnIpAdres", Gebruiker.class);
        query.setParameter("sessie", sessie);
        query.setParameter("ipadres", ipadres);

        List<Gebruiker> lijst = query.getResultList();

        if (lijst != null && lijst.size() > 0) {
            logger.debug("Aantal gevonden : " + lijst.size());
            gebruiker = lijst.get(0);
        } else if (lijst != null) {
            logger.debug("Lege lijst");
            throw new NietGevondenException(sessie);
        } else {
            logger.debug("null lijst");
            throw new NietGevondenException(sessie);
        }

        return gebruiker;
    }

    public void refresh(Sessie sessie) {
        getEm().refresh(sessie);
    }

    public void opslaan(Sessie sessie) {
        if (sessie.getId() == null) {
            getEm().persist(sessie);
        } else {
            getEm().merge(sessie);
        }
    }

    public void verwijder(Sessie sessie) {
        getEm().remove(sessie);
    }

    public Gebruiker zoekOpCookieCode(String cookieCode) throws NietGevondenException {
        Gebruiker gebruiker = null;

        TypedQuery<Gebruiker> query = getEm().createNamedQuery("Gebruiker.zoekOpCookieCode", Gebruiker.class);
        query.setParameter("cookieCode", cookieCode);
        try {
            gebruiker = query.getSingleResult();
        } catch (NoResultException e) {
            logger.info("Niets gevonden", e);
            throw new NietGevondenException(cookieCode);
        }

        return gebruiker;
    }
}
