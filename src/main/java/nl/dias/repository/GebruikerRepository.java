package nl.dias.repository;

import nl.dias.domein.*;
import nl.lakedigital.loginsystem.exception.NietGevondenException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.resource.transaction.spi.TransactionStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.NoResultException;
import java.util.ArrayList;
import java.util.List;


@Repository
public class GebruikerRepository {//extends AbstractRepository<Gebruiker> {
    private static final Logger LOGGER = LoggerFactory.getLogger(GebruikerRepository.class);
    private static final int MAX_RESULTS = 30;

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

    public List<ContactPersoon> alleContactPersonen(Long bedrijfsId) {
        getTransaction();

        Query query = getEm().getNamedQuery("ContactPersoon.alleContactPersonen");
        query.setParameter("bedrijf", bedrijfsId);

        List<ContactPersoon> result = query.list();

        getTransaction().commit();


        return result;
    }

    public List<Relatie> zoekRelatiesOpTelefoonnummer(String telefoonnummer) {
        getTransaction();

        Query query = getEm().getNamedQuery("Relatie.zoekOpTelefoonnummer");
        query.setParameter("telefoonnummer", telefoonnummer);

        List<Relatie> relaties = new ArrayList<Relatie>();

        for (Object relatie : query.list()) {
            relaties.add((Relatie) relatie);
        }
        getTransaction().commit();
        return relaties;
    }

    public List<Relatie> zoekRelatiesOpBedrijfsnaam(String bedrijfsnaam) {
        getTransaction();

        Query query = getEm().getNamedQuery("Relatie.zoekOpBedrijfsnaam");
        query.setParameter("bedrijfsnaam", "%" + bedrijfsnaam + "%");

        List<Relatie> relaties = new ArrayList<Relatie>();

        for (Object relatie : query.list()) {
            relaties.add((Relatie) relatie);
        }

        getTransaction().commit();

        return relaties;
    }

    @Transactional
    public List<Relatie> alleRelaties() {
        getTransaction();
        Query query = getEm().createQuery("select e from Relatie e");
        query.setMaxResults(MAX_RESULTS);
        List<Relatie> ret = query.list();

        getTransaction().commit();

        return ret;
    }

    @Transactional
    public List<Relatie> alleRelaties(Kantoor kantoor) {
        getTransaction();

        Query query = getEm().getNamedQuery("Relatie.zoekAllesVoorKantoor");
        query.setMaxResults(MAX_RESULTS);
        query.setParameter("kantoor", kantoor);

        List<Relatie> result = query.list();

        getTransaction().commit();

        return result;
    }

    @Transactional
    public Relatie zoekOpBsn(String bsn) {
        getTransaction();

        Query query = getEm().getNamedQuery("Relatie.zoekOpBsn");
        query.setParameter("bsn", bsn);

        Relatie relatie = (Relatie) query.list().get(0);

        getTransaction().commit();

        return relatie;
    }

    @Transactional
    public Gebruiker zoek(String emailadres) throws NietGevondenException {
        LOGGER.debug("Parameter : '{}'", emailadres);

        Gebruiker gebruiker = null;

        getTransaction();

        Query query = getEm().getNamedQuery("Gebruiker.zoekOpEmail");
        query.setParameter("emailadres", emailadres);
        //        query.setParameter("emailadres", "'"+emailadres+"'");
        try {
            gebruiker = (Relatie) query.list().get(0);
        } catch (NoResultException e) {
            LOGGER.info("Niets gevonden", e);
            throw new NietGevondenException(emailadres);
        }
        getTransaction().commit();

        return gebruiker;
    }

    @Transactional
    public Gebruiker zoekOpIdentificatie(String identificatie) throws NietGevondenException {
        LOGGER.debug("zoekOpIdentificatie Parameter : '{}'", identificatie);

        Gebruiker gebruiker = null;

        getTransaction();

        Query query = getEm().getNamedQuery("Gebruiker.zoekOpIdentificatie");
        query.setParameter("identificatie", identificatie);
        //        query.setParameter("emailadres", "'"+emailadres+"'");
        try {
            gebruiker = (Gebruiker) query.list().get(0);
        } catch (NoResultException e) {
            LOGGER.info("Niets gevonden", e);
            throw new NietGevondenException(identificatie);
        }

        getTransaction().commit();

        return gebruiker;
    }

    public List<Gebruiker> zoekOpNaam(String naam) {
        getTransaction();

        Query query = getEm().getNamedQuery("Gebruiker.zoekOpNaam");
        query.setMaxResults(MAX_RESULTS);
        query.setParameter("naam", "%" + naam + "%");

        List<Gebruiker> result = query.list();

        getTransaction().commit();

        return result;
    }

    //    public List<Relatie> zoekOpAdres(String adres) {
    //        getTransaction();
    //
    //        Query query = getEm().getNamedQuery("Adres.zoekAdres");
    //        query.setMaxResults(MAX_RESULTS);
    //        query.setParameter("adres", "%" + adres + "%");
    //
    //        Set<Relatie> result = new HashSet<>();
    //
    //        List<Adres> res = query.list();
    //
    //        for (Adres adr : filter(res, new Predicate<Adres>() {
    //            @Override
    //            public boolean apply(Adres adres) {
    //                return adres.getSoortEntiteit().equals(SoortEntiteit.RELATIE);
    //            }
    //        })) {
    //            result.add(getEm().get(Relatie.class, adr.getEntiteitId()));
    //        }
    //
    //        getTransaction().commit();
    //
    //        return Lists.newArrayList(result);
    //    }


    public Gebruiker zoekOpSessieEnIpadres(String sessie, String ipadres) throws NietGevondenException {
        LOGGER.debug("zoekOpSessieEnIpadres(" + sessie + " , " + ipadres + ")");


        Gebruiker gebruiker = null;

        getTransaction();

        Query query = getEm().getNamedQuery("Gebruiker.zoekOpSessieEnIpAdres");
        query.setParameter("sessie", sessie);
        query.setParameter("ipadres", ipadres);

        List<Gebruiker> lijst = query.list();

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

        getTransaction().commit();

        return gebruiker;
    }

    public void refresh(Sessie sessie) {
        getEm().refresh(sessie);
    }

    public void opslaan(Sessie sessie) {
        getTransaction();

        if (sessie.getId() == null) {
            getEm().persist(sessie);
        } else {
            getEm().merge(sessie);
        }

        getTransaction().commit();
    }

    public void verwijderAdressenBijRelatie(Relatie relatie) {
        getEm().getTransaction();
        getEm().getNamedQuery("Adres.verwijderAdressenBijRelatie").setParameter("relatie", relatie).executeUpdate();
        //        getEm().getTransaction().commit();
        //    }
        //    public void verwijderTelefoonnummersnBijRelatie(Relatie relatie) {
        //        getEm().getTransaction().begin();
        getEm().getNamedQuery("Telefoonnummer.verwijderTelefoonnummersBijRelatie").setParameter("relatie", relatie).executeUpdate();
        //        getEm().getTransaction().commit();
        //    }
        //    public void verwijderRekeningenBijRelatie(Relatie relatie) {
        //        getEm().getTransaction().begin();
        getEm().getNamedQuery("RekeningNummer.verwijderRekeningNummersBijRelatie").setParameter("relatie", relatie).executeUpdate();
        getEm().getTransaction().commit();
    }

    public void verwijder(Sessie sessie) {
        getTransaction();
        getEm().delete(sessie);
        getTransaction().commit();
    }

    public void verwijder(Gebruiker gebruiker) {
        getTransaction();
        getEm().delete(gebruiker);
        getTransaction().commit();
    }

    public Gebruiker zoekOpCookieCode(String cookieCode) throws NietGevondenException {
        Gebruiker gebruiker = null;
        getTransaction();

        Query query = getEm().getNamedQuery("Gebruiker.zoekOpCookieCode");
        query.setParameter("cookieCode", cookieCode);
        try {
            gebruiker = (Gebruiker) query.list().get(0);
        } catch (NoResultException e) {
            LOGGER.info("Niets gevonden", e);
            throw new NietGevondenException(cookieCode);
        }

        getTransaction().commit();

        return gebruiker;
    }

    public Gebruiker lees(Long id) {
        getTransaction();

        Gebruiker g = getEm().get(Gebruiker.class, id);

        getTransaction().commit();

        return g;
    }

    public void opslaan(Gebruiker gebruiker) {
        getTransaction();

        //        LOGGER.info("Opslaan {}", ReflectionToStringBuilder.toString(gebruiker, ToStringStyle.SHORT_PREFIX_STYLE));
        if (gebruiker.getId() == null) {
            getSession().save(gebruiker);
        } else {
            getSession().merge(gebruiker);
        }

        getTransaction().commit();
    }

    //    @Transactional
    //    public void verwijder(Gebruiker o) {
    //        getEm().remove(o);
    //    }
    //
    //    @Transactional
    //    public List<Gebruiker> alles() {
    //        Query query = getEm().createQuery("select e from Gebruiker e");
    //        @SuppressWarnings("unchecked")
    //        List<Gebruiker> ret = query.getResultList();
    //
    //        return ret;
    //    }
    //
    //    @Transactional
    //    public void opslaan(Gebruiker o) {
    //
    //        if (o.getId() == null) {
    //            getEm().persist(o);
    //        } else {
    //            getEm().merge(o);
    //        }
    //    }
    //
    //    @Transactional
    //    public Gebruiker lees(Long id) {
    //        System.out.println(getEm());
    //        return getEm().find(Gebruiker.class, id);
    //    }
}
