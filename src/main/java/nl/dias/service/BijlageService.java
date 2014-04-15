package nl.dias.service;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Named;
import javax.persistence.TypedQuery;

import nl.dias.domein.Bijlage;
import nl.dias.domein.polis.Polis;
import nl.lakedigital.hulpmiddelen.repository.AbstractRepository;

@Named
public class BijlageService extends AbstractRepository<Bijlage> {
    public BijlageService() {
        super(Bijlage.class);
        zetPersistenceContext("dias");
    }

    @Override
    public void setPersistenceContext(String persistenceContext) {
        zetPersistenceContext(persistenceContext);
    }

    public List<Bijlage> zoekBijlagenBijPolis(Polis polis) {
        List<Bijlage> ret = null;

        getTx().begin();
        TypedQuery<Bijlage> query = getEm().createNamedQuery("Bijlage.zoekBijlagenBijPolis", Bijlage.class);
        query.setParameter("polis", polis);

        ret = query.getResultList();
        getTx().commit();

        if (ret == null) {
            ret = new ArrayList<>();
        }
        return ret;
    }
}
