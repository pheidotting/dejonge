package nl.dias.repository;

import nl.dias.domein.Bijlage;
import nl.dias.domein.Relatie;
import nl.lakedigital.hulpmiddelen.repository.AbstractRepository;

import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;

public class BijlageRepository extends AbstractRepository<Bijlage> {
    public BijlageRepository() {
        super(Bijlage.class);
        zetPersistenceContext("dias");
    }

    @Override
    public void setPersistenceContext(String persistenceContext) {
        zetPersistenceContext(persistenceContext);
    }

    public List<Bijlage> alleBijlagesBijRelatie(Relatie relatie) {
        List<Bijlage> bijlages = new ArrayList<>();

        TypedQuery<Bijlage> query = getEm().createNamedQuery("Bijlage.allesVanRelatiePolis", Bijlage.class);
        query.setParameter("relatie", relatie);
        bijlages.addAll(query.getResultList());

        query = getEm().createNamedQuery("Bijlage.allesVanRelatieSchade", Bijlage.class);
        query.setParameter("relatie", relatie);
        bijlages.addAll(query.getResultList());

        query = getEm().createNamedQuery("Bijlage.allesVanRelatieHypotheek", Bijlage.class);
        query.setParameter("relatie", relatie);
        bijlages.addAll(query.getResultList());

        query = getEm().createNamedQuery("Bijlage.allesVanRelatieAangifte", Bijlage.class);
        query.setParameter("relatie", relatie);
        bijlages.addAll(query.getResultList());

        return bijlages;
    }

    public Bijlage leesBijlage(Long id) {
        return getEm().find(Bijlage.class, id);
    }
}