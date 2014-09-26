package nl.dias.repository;

import java.util.List;

import javax.inject.Named;
import javax.persistence.TypedQuery;

import nl.dias.domein.Bijlage;
import nl.dias.domein.Hypotheek;
import nl.dias.domein.SoortHypotheek;
import nl.lakedigital.hulpmiddelen.repository.AbstractRepository;

import org.springframework.transaction.annotation.Transactional;

@Named
public class HypotheekRepository extends AbstractRepository<Hypotheek> {
    public HypotheekRepository() {
        super(Hypotheek.class);
        zetPersistenceContext("dias");
    }

    @Override
    public void setPersistenceContext(String persistenceContext) {
        zetPersistenceContext(persistenceContext);
    }

    public List<SoortHypotheek> alleSoortenHypotheekInGebruik() {
        TypedQuery<SoortHypotheek> query = getEm().createNamedQuery("SoortHypotheek.allesInGebruik", SoortHypotheek.class);
        return query.getResultList();
    }

    public SoortHypotheek leesSoortHypotheek(Long id) {
        return getEm().find(SoortHypotheek.class, id);
    }

    @Transactional
    public void opslaanBijlage(Bijlage bijlage) {
        getEm().getTransaction().begin();
        getEm().persist(bijlage);
        getEm().getTransaction().commit();
    }

}
