package nl.dias.repository;

import nl.dias.domein.RekeningNummer;
import nl.dias.web.SoortEntiteit;
import nl.lakedigital.hulpmiddelen.repository.AbstractRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class RekeningNummerRepository extends AbstractRepository<RekeningNummer> {

    public RekeningNummerRepository() {
        super(RekeningNummer.class);
        zetPersistenceContext("dias");
    }

    @Override
    public void setPersistenceContext(String persistenceContext) {
        zetPersistenceContext(persistenceContext);
    }

    public List<RekeningNummer> alles(SoortEntiteit soortEntiteit, Long entiteitId) {
        TypedQuery<RekeningNummer> query = getEm().createNamedQuery("RekeningNummer.zoekRekeningNummersBijEntiteit", RekeningNummer.class);
        query.setParameter("soortEntiteit", soortEntiteit);
        query.setParameter("entiteitId", entiteitId);

        return query.getResultList();
    }

}
