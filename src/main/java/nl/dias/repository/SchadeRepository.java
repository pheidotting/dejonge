package nl.dias.repository;

import java.util.List;

import javax.inject.Named;
import javax.persistence.TypedQuery;

import nl.dias.domein.Bijlage;
import nl.dias.domein.Relatie;
import nl.dias.domein.Schade;
import nl.dias.domein.SoortSchade;
import nl.dias.domein.StatusSchade;
import nl.lakedigital.hulpmiddelen.repository.AbstractRepository;

import org.springframework.transaction.annotation.Transactional;

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

    @Transactional
    public void opslaanBijlage(Bijlage bijlage) {
        getEm().getTransaction().begin();
        getEm().persist(bijlage);
        getEm().getTransaction().commit();
    }
}
