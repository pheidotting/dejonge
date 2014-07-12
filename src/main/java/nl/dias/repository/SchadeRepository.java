package nl.dias.repository;

import java.util.List;

import javax.inject.Named;
import javax.persistence.TypedQuery;

import nl.dias.domein.Schade;
import nl.dias.domein.SoortSchade;
import nl.dias.domein.StatusSchade;
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

    public StatusSchade getStatussen(String status) {
        TypedQuery<StatusSchade> query = getEm().createNamedQuery("StatusSchade.zoekOpSoort", StatusSchade.class);
        query.setParameter("status", status);

        return query.getSingleResult();
    }

    public List<StatusSchade> getStatussen() {
        return getEm().createQuery("select s from StatusSchade where s.ingebruik = '1'", StatusSchade.class).getResultList();
    }
}
