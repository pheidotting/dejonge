package nl.dias.repository;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.TypedQuery;

import nl.dias.domein.Bijlage;
import nl.dias.domein.Relatie;
import nl.lakedigital.hulpmiddelen.repository.AbstractRepository;

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

        return bijlages;
    }
}