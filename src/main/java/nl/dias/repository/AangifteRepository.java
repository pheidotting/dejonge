package nl.dias.repository;

import java.util.List;

import javax.inject.Named;
import javax.persistence.TypedQuery;

import nl.dias.domein.Aangifte;
import nl.dias.domein.Relatie;
import nl.lakedigital.hulpmiddelen.repository.AbstractRepository;

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
}
