package nl.dias.repository;

import nl.dias.domein.Bijlage;
import nl.dias.web.SoortEntiteit;
import nl.lakedigital.hulpmiddelen.repository.AbstractRepository;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class BijlageRepository extends AbstractRepository<Bijlage> {
    private static final Logger LOGGER = LoggerFactory.getLogger(BijlageRepository.class);

    public BijlageRepository() {
        super(Bijlage.class);
        zetPersistenceContext("dias");
    }

    @Override
    public void setPersistenceContext(String persistenceContext) {
        zetPersistenceContext(persistenceContext);
    }

    public List<Bijlage> alleBijlagesBijEntiteit(SoortEntiteit soortEntiteit, Long entiteitId) {
        TypedQuery<Bijlage> query = getEm().createNamedQuery("Bijlage.zoekBijlagesBijEntiteit", Bijlage.class);
        query.setParameter("soortBijlage", soortEntiteit);
        query.setParameter("entiteitId", entiteitId);

        return query.getResultList();
    }

    public Bijlage leesBijlage(Long id) {
        return getEm().find(Bijlage.class, id);
    }

    @Override
    public void opslaan(Bijlage bijlage) {
        LOGGER.debug("Opslaan Bijlage : {}", ReflectionToStringBuilder.toString(bijlage, ToStringStyle.SHORT_PREFIX_STYLE));
        if (!getTx().isActive()) {
            LOGGER.debug("Geen actieve transactie, dus start nieuwe");
            getTx().begin();
            LOGGER.debug("Transactie gestart");
        } else {
            LOGGER.debug("Transactie open, prima zo.");
        }
        if (bijlage.getId() == null) {
            LOGGER.debug("Nieuwe Bijlage, dus persist doen");
            getEm().persist(bijlage);
            LOGGER.debug("Klaar met persist");
        } else {
            LOGGER.debug("Bijlage is al bekend, merge!");
            getEm().merge(bijlage);
            LOGGER.debug("Merge klaar");
        }
        LOGGER.debug("Transactie committen");
        getTx().commit();
        LOGGER.debug("Commit klaar");
    }

}