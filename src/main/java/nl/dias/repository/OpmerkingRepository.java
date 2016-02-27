package nl.dias.repository;

import nl.dias.domein.Opmerking;
import nl.dias.domein.Relatie;
import nl.dias.web.SoortEntiteit;
import nl.lakedigital.hulpmiddelen.repository.AbstractRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class OpmerkingRepository extends AbstractRepository<Opmerking> {
    private static final Logger LOGGER = LoggerFactory.getLogger(OpmerkingRepository.class);

    public OpmerkingRepository() {
        super(Opmerking.class);
        zetPersistenceContext("dias");
    }

    public List<Opmerking> alleOpmerkingenBijEntiteit(SoortEntiteit soortEntiteit, Long entiteitId) {
        TypedQuery<Opmerking> query = getEm().createNamedQuery("Opmerking.zoekOpmerkingenBijEntiteit", Opmerking.class);
        query.setParameter("soortEntiteit", soortEntiteit);
        query.setParameter("entiteitId", entiteitId);

        return query.getResultList();
    }

    @Override
    public void setPersistenceContext(String persistenceContext) {
        zetPersistenceContext(persistenceContext);
    }

    public List<Opmerking> alleOpmerkingenVoorRelatie(Relatie relatie) {
        TypedQuery<Opmerking> query = getEm().createNamedQuery("Opmerking.allesVoorRelatie", Opmerking.class);
        query.setParameter("relatie", relatie);

        return query.getResultList();
    }

    @Override
    public void opslaan(Opmerking opmerking) {
        try {
            getTx().begin();
        } catch (java.lang.IllegalStateException ise) {
            LOGGER.debug("Fout opgetreden", ise);
        }
        if (opmerking.getId() == null) {
            getEm().persist(opmerking);
        } else {
            getEm().merge(opmerking);
        }
        getTx().commit();
    }

}