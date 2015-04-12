package nl.dias.repository;

import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import nl.dias.domein.Gebruiker;
import nl.dias.domein.Kantoor;
import nl.dias.domein.Relatie;
import nl.dias.domein.Sessie;
import nl.lakedigital.hulpmiddelen.repository.AbstractRepository;
import nl.lakedigital.loginsystem.exception.NietGevondenException;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class GebruikerRepository extends AbstractRepository<Gebruiker> {
    private final static Logger LOGGER = Logger.getLogger(GebruikerRepository.class);
    private final static int MAX_RESULTS = 30;

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
        query.setMaxResults(MAX_RESULTS);
        List<Relatie> ret = query.getResultList();

        return ret;
    }

    @Transactional
    public List<Relatie> alleRelaties(Kantoor kantoor) {
        TypedQuery<Relatie> query = getEm().createNamedQuery("Relatie.zoekAllesVoorKantoor", Relatie.class);
        query.setMaxResults(MAX_RESULTS);
        query.setParameter("kantoor", kantoor);

        return query.getResultList();
    }

    @Transactional
    public Relatie zoekOpBsn(String bsn) {
        TypedQuery<Relatie> query = getEm().createNamedQuery("Relatie.zoekOpBsn", Relatie.class);
        query.setParameter("bsn", bsn);

        return query.getSingleResult();
    }

    @Transactional
    public Gebruiker zoek(String emailadres) throws NietGevondenException {
        Gebruiker gebruiker = null;

        TypedQuery<Gebruiker> query = getEm().createNamedQuery("Gebruiker.zoekOpEmail", Gebruiker.class);
        query.setMaxResults(MAX_RESULTS);
        query.setParameter("emailadres", emailadres);
        try {
            gebruiker = query.getSingleResult();
        } catch (NoResultException e) {
            LOGGER.info("Niets gevonden", e);
            throw new NietGevondenException(emailadres);
        }

        return gebruiker;
    }

    public List<Gebruiker> zoekOpNaam(String naam) {
        TypedQuery<Gebruiker> query = getEm().createNamedQuery("Gebruiker.zoekOpNaam", Gebruiker.class);
        query.setMaxResults(MAX_RESULTS);
        query.setParameter("naam", "%" + naam + "%");

        return query.getResultList();
    }

    public List<Relatie> zoekOpAdres(String adres) {
        TypedQuery<Relatie> query = getEm().createNamedQuery("Relatie.zoekOpAdres", Relatie.class);
        query.setMaxResults(MAX_RESULTS);
        query.setParameter("adres", "%" + adres + "%");

        return query.getResultList();
    }

    public Gebruiker zoekOpSessieEnIpadres(String sessie, String ipadres) throws NietGevondenException {
        LOGGER.debug("zoekOpSessieEnIpadres(" + sessie + " , " + ipadres + ")");

        Gebruiker gebruiker = null;

        TypedQuery<Gebruiker> query = getEm().createNamedQuery("Gebruiker.zoekOpSessieEnIpAdres", Gebruiker.class);
        query.setParameter("sessie", sessie);
        query.setParameter("ipadres", ipadres);

        List<Gebruiker> lijst = query.getResultList();

        if (lijst != null && !lijst.isEmpty()) {
            LOGGER.debug("Aantal gevonden : " + lijst.size());
            gebruiker = lijst.get(0);
        } else if (lijst != null) {
            LOGGER.debug("Lege lijst");
            throw new NietGevondenException(sessie);
        } else {
            LOGGER.debug("null lijst");
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
            LOGGER.info("Niets gevonden", e);
            throw new NietGevondenException(cookieCode);
        }

        return gebruiker;
    }
}
