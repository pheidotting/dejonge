package nl.dias.repository;

import java.util.List;

import javax.inject.Named;
import javax.persistence.TypedQuery;

import nl.dias.domein.Aangifte;
import nl.dias.domein.Bijlage;
import nl.dias.domein.Relatie;
import nl.lakedigital.hulpmiddelen.repository.AbstractRepository;

import org.springframework.transaction.annotation.Transactional;

@Named
public class AangifteRepository extends AbstractRepository<Aangifte> {
    public AangifteRepository() {
        super(Aangifte.class);
        zetPersistenceContext("dias");
    }

    @Override
    public void setPersistenceContext(String persistenceContext) {
        zetPersistenceContext(persistenceContext);
    }

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
