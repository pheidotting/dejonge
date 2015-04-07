package nl.dias.repository;

import java.util.List;

import javax.persistence.TypedQuery;

import nl.dias.domein.DIASBedrijf;
import nl.lakedigital.hulpmiddelen.repository.AbstractRepository;

import org.springframework.stereotype.Repository;

@Repository
public class DIASBedrijfRepository extends AbstractRepository<DIASBedrijf> {
    public DIASBedrijfRepository() {
        super(DIASBedrijf.class);
        zetPersistenceContext("dias");
    }

    @Override
    public void setPersistenceContext(String persistenceContext) {
        zetPersistenceContext(persistenceContext);
    }

    public List<DIASBedrijf> zoekOpRegistratieNummer(String registratieNummer) {
        TypedQuery<DIASBedrijf> query = getEm().createNamedQuery("DIASBedrijf.zoekOpRegistratieNummer", DIASBedrijf.class);
        query.setParameter("registratieNummer", registratieNummer);

        return query.getResultList();
    }

}
