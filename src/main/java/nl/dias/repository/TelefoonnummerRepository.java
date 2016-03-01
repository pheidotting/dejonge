package nl.dias.repository;

import nl.dias.domein.Telefoonnummer;
import nl.dias.web.SoortEntiteit;
import nl.lakedigital.hulpmiddelen.repository.AbstractRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class TelefoonnummerRepository extends AbstractRepository<Telefoonnummer> {
    public TelefoonnummerRepository() {
        super(Telefoonnummer.class);
        zetPersistenceContext("dias");
    }

    @Override
    public void setPersistenceContext(String persistenceContext) {
        zetPersistenceContext(persistenceContext);
    }

    public List<Telefoonnummer> alles(SoortEntiteit soortEntiteit, Long entiteitId) {
        TypedQuery<Telefoonnummer> query = getEm().createNamedQuery("Telefoonnummer.zoekTelefoonnummersBijEntiteit", Telefoonnummer.class);
        query.setParameter("soortEntiteit", soortEntiteit);
        query.setParameter("entiteitId", entiteitId);

        return query.getResultList();
    }
}
