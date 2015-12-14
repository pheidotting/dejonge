package nl.dias.repository;

import nl.dias.domein.Bedrijf;
import nl.dias.domein.Relatie;
import nl.lakedigital.hulpmiddelen.repository.AbstractRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class BedrijfRepository extends AbstractRepository<Bedrijf> {
    private final static Logger LOGGER = LoggerFactory.getLogger(BedrijfRepository.class);

    public BedrijfRepository() {
        super(Bedrijf.class);
        zetPersistenceContext("dias");
    }

    @Override
    public void setPersistenceContext(String persistenceContext) {
        zetPersistenceContext(persistenceContext);
    }

    public List<Bedrijf> alleBedrijvenBijRelatie(Relatie relatie) {
        TypedQuery<Bedrijf> query = getEm().createNamedQuery("Bedrijf.allesBijRelatie", Bedrijf.class);
        query.setParameter("relatie", relatie);

        return query.getResultList();
    }

    public List<Bedrijf> zoekOpNaam(String zoekTerm){
        TypedQuery<Bedrijf>query=getEm().createNamedQuery("Bedrijf.zoekOpNaam",Bedrijf.class);
        query.setParameter("zoekTerm","%"+zoekTerm+"%");

        return query.getResultList();
    }

}
