package nl.dias.repository;

import java.util.List;

import javax.inject.Named;
import javax.persistence.TypedQuery;

import nl.dias.domein.Schade;
import nl.dias.domein.SoortSchade;
import nl.lakedigital.hulpmiddelen.repository.AbstractRepository;

@Named
public class SchadeRepository extends AbstractRepository<Schade> {
    public SchadeRepository() {
        super(Schade.class);
        zetPersistenceContext("dias");
    }

    @Override
    public void setPersistenceContext(String persistenceContext) {
        zetPersistenceContext(persistenceContext);
    }

    public List<SoortSchade> soortenSchade() {
        TypedQuery<SoortSchade> query = getEm().createNamedQuery("SoortSchade.alles", SoortSchade.class);
        return query.getResultList();
    }

    public List<SoortSchade> soortenSchade(String omschrijving) {
        TypedQuery<SoortSchade> query = getEm().createNamedQuery("SoortSchade.zoekOpOmschrijving", SoortSchade.class);
        query.setParameter("omschrijving", "%" + omschrijving + "%");
        return query.getResultList();
    }
}
