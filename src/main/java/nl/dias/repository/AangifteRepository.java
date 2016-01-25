package nl.dias.repository;

import nl.dias.domein.Aangifte;
import nl.dias.domein.Bijlage;
import nl.dias.domein.Relatie;
import nl.lakedigital.hulpmiddelen.repository.AbstractRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class AangifteRepository extends AbstractRepository<Aangifte> {
    public AangifteRepository() {
        super(Aangifte.class);
        zetPersistenceContext("dias");
    }

    @Override
    public void setPersistenceContext(String persistenceContext) {
        zetPersistenceContext(persistenceContext);
    }

    //    @PersistenceContext
    //    private EntityManager entityManager;
    //
    //    @PostConstruct
    //    public void init() {
    //        entityManager= entityManager.getEntityManagerFactory().createEntityManager();
    //    }
    //
    //
    //    public EntityManager getEm() {
    //        return entityManager;
    //    }
    //
    //    @Transactional
    //    public void verwijder(Aangifte o) {
    //        entityManager.getTransaction().begin();
    //        entityManager.remove(o);
    //        entityManager.getTransaction().commit();
    //    }
    //
    //    public List<Aangifte> alles() {
    //        Query query = entityManager.createQuery("select e from Aangifte e");
    //        @SuppressWarnings("unchecked")
    //        List<Aangifte> ret = query.getResultList();
    //
    //        return ret;
    //    }
    //
    //    public void opslaan(Aangifte o) {
    //
    //        entityManager.getTransaction().begin();
    //        if (((PersistenceObject) o).getId() == null) {
    //            entityManager.persist(o);
    //        } else {
    //            entityManager.merge(o);
    //        }
    //        entityManager.getTransaction().commit();
    //    }
    //
    //    @Transactional
    //    public Aangifte lees(Long id) {
    //        return entityManager.find(Aangifte.class, id);
    //    }
    
    public List<Aangifte> getOpenAngiftes(Relatie relatie) {
        TypedQuery<Aangifte> query = getEm().createNamedQuery("Aangifte.openAangiftesBijRelatie", Aangifte.class);
        query.setParameter("relatie", relatie);

        return query.getResultList();
    }

    public List<Aangifte> getGeslotenAngiftes(Relatie relatie) {
        TypedQuery<Aangifte> query = getEm().createNamedQuery("Aangifte.geslotenAangiftesBijRelatie", Aangifte.class);
        query.setParameter("relatie", relatie);

        return query.getResultList();
    }

    public List<Aangifte> getAlleAngiftes(Relatie relatie) {
        TypedQuery<Aangifte> query = getEm().createNamedQuery("Aangifte.alleAangiftesBijRelatie", Aangifte.class);
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
