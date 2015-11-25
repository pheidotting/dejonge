package nl.dias.repository;

import nl.dias.domein.Bedrijf;
import nl.dias.domein.RisicoAnalyse;
import nl.lakedigital.hulpmiddelen.repository.AbstractRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;

@Repository
public class RisicoAnalyseRepository extends AbstractRepository<RisicoAnalyse> {
    private final static Logger LOGGER = LoggerFactory.getLogger(RisicoAnalyseRepository.class);

    public RisicoAnalyseRepository() {
        super(RisicoAnalyse.class);
        zetPersistenceContext("dias");
    }

    @Override
    public void setPersistenceContext(String persistenceContext) {
        zetPersistenceContext(persistenceContext);
    }

    public RisicoAnalyse leesBijBedrijf(Bedrijf bedrijf) {
        LOGGER.debug("leesBijBedrijf met id {}", bedrijf.getId());

        TypedQuery<RisicoAnalyse> query = getEm().createNamedQuery("RisicoAnalyse.alleRisicoAnalysesBijBedrijf", RisicoAnalyse.class);
        query.setParameter("bedrijf", bedrijf);

        return query.getSingleResult();
    }
}
