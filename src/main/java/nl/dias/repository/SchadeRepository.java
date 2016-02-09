package nl.dias.repository;

import nl.dias.domein.*;
import nl.lakedigital.hulpmiddelen.repository.AbstractRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.TypedQuery;
import java.util.List;

@Repository
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
        return getEm().createQuery("select s from StatusSchade s where s.ingebruik = '1'", StatusSchade.class).getResultList();
    }

    public Schade zoekOpSchadeNummerMaatschappij(String schadeNummerMaatschappij) {
        TypedQuery<Schade> query = getEm().createNamedQuery("Schade.zoekOpschadeNummerMaatschappij", Schade.class);
        query.setParameter("schadeNummerMaatschappij", schadeNummerMaatschappij);

        return query.getSingleResult();
    }

    public List<Schade> alleSchadesBijRelatie(Relatie relatie) {
        TypedQuery<Schade> query = getEm().createNamedQuery("Schade.allesVanRelatie", Schade.class);
        query.setParameter("relatie", relatie);

        return query.getResultList();
    }

    public List<Schade> allesBijPolis(Long polis) {
        TypedQuery<Schade> query = getEm().createNamedQuery("Schade.allesBijPolis", Schade.class);
        query.setParameter("polis", polis);

        return query.getResultList();
    }

    public List<Bijlage> zoekBijlagesBijSchade(Schade schade) {
        TypedQuery<Bijlage> query = getEm().createNamedQuery("Bijlage.zoekBijlagesBijSchade", Bijlage.class);
        query.setParameter("schade", schade);

        return query.getResultList();

    }

    @Transactional
    public void opslaanBijlage(Bijlage bijlage) {
        getEm().getTransaction().begin();
        getEm().persist(bijlage);
        getEm().getTransaction().commit();
    }
}
