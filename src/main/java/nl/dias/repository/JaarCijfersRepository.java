package nl.dias.repository;

import nl.dias.domein.Bedrijf;
import nl.dias.domein.JaarCijfers;
import nl.lakedigital.hulpmiddelen.repository.AbstractRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class JaarCijfersRepository extends AbstractRepository<JaarCijfers> {
    private static final Logger LOGGER = LoggerFactory.getLogger(JaarCijfersRepository.class);

    public JaarCijfersRepository() {
        super(JaarCijfers.class);
        zetPersistenceContext("dias");
    }

    @Override
    public void setPersistenceContext(String persistenceContext) {
        zetPersistenceContext(persistenceContext);
    }

    public List<JaarCijfers> allesBijBedrijf(Bedrijf bedrijf) {
        LOGGER.debug("allesBijBedrijf met id {}", bedrijf.getId());

        TypedQuery<JaarCijfers> query = getEm().createNamedQuery("JaarCijfers.allesJaarCijfersBijBedrijf", JaarCijfers.class);
        query.setParameter("bedrijf", bedrijf);

        return query.getResultList();
    }
}
