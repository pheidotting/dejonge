package nl.dias.repository;

import java.util.List;

import javax.persistence.TypedQuery;

import nl.dias.domein.DIASAdres;
import nl.lakedigital.hulpmiddelen.repository.AbstractRepository;

import org.springframework.stereotype.Repository;

@Repository
public class DIASAdresRepository extends AbstractRepository<DIASAdres> {
    public DIASAdresRepository() {
        super(DIASAdres.class);
        zetPersistenceContext("dias");
    }

    @Override
    public void setPersistenceContext(String persistenceContext) {
        zetPersistenceContext(persistenceContext);
    }

    public List<DIASAdres> zoekOpRegistratieNummer(String registratieNummer) {
        TypedQuery<DIASAdres> query = getEm().createNamedQuery("DIASAdres.zoekOpRegistratieNummer", DIASAdres.class);
        query.setParameter("registratieNummer", registratieNummer);

        return query.getResultList();
    }
}
