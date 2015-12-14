package nl.dias.repository;

import nl.dias.domein.Bijlage;
import nl.dias.domein.Hypotheek;
import nl.dias.domein.Relatie;
import nl.dias.domein.SoortHypotheek;
import nl.lakedigital.hulpmiddelen.repository.AbstractRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class HypotheekRepository extends AbstractRepository<Hypotheek> {
    private static final Logger LOGGER = LoggerFactory.getLogger(HypotheekRepository.class);

    public HypotheekRepository() {
        super(Hypotheek.class);
        zetPersistenceContext("dias");
    }

    @Override
    public void setPersistenceContext(String persistenceContext) {
        zetPersistenceContext(persistenceContext);
    }

    public List<SoortHypotheek> alleSoortenHypotheekInGebruik() {
        LOGGER.debug("Ophalen alleSoortenHypotheekInGebruik");
        TypedQuery<SoortHypotheek> query = getEm().createNamedQuery("SoortHypotheek.allesInGebruik", SoortHypotheek.class);
        return query.getResultList();
    }

    public SoortHypotheek leesSoortHypotheek(Long id) {
        return getEm().find(SoortHypotheek.class, id);
    }

    public List<Hypotheek> allesVanRelatie(Relatie relatie) {
        TypedQuery<Hypotheek> query = getEm().createNamedQuery("Hypotheek.allesVanRelatie", Hypotheek.class);
        query.setParameter("relatie", relatie);

        return query.getResultList();
    }

    public List<Hypotheek> allesVanRelatieInEenPakket(Relatie relatie) {
        TypedQuery<Hypotheek> query = getEm().createNamedQuery("Hypotheek.allesVanRelatieInEenPakket", Hypotheek.class);
        query.setParameter("relatie", relatie);

        return query.getResultList();
    }

    public List<Hypotheek> allesVanRelatieInclDePakketten(Relatie relatie) {
        TypedQuery<Hypotheek> query = getEm().createNamedQuery("Hypotheek.allesVanRelatieInclDePakketten", Hypotheek.class);
        query.setParameter("relatie", relatie);

        return query.getResultList();
    }

    @Transactional
    public void opslaanBijlage(Bijlage bijlage) {
        getEm().getTransaction().begin();
        getEm().persist(bijlage);
        getEm().getTransaction().commit();
    }

}
