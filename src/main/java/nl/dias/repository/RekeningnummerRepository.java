package nl.dias.repository;

import nl.dias.domein.RekeningNummer;
import nl.dias.web.SoortEntiteit;
import nl.lakedigital.hulpmiddelen.repository.AbstractRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import java.util.ArrayList;
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

    public List<RekeningNummer> alles(SoortEntiteit soortEntiteit, Long parentId) {
        String where = null;
        switch (soortEntiteit) {
            case RELATIE:
                where = "relatie = ";
                break;
            default:
                return new ArrayList<>();
        }

        TypedQuery<RekeningNummer> query = getEm().createQuery("select t from RekeningNummer t where t." + where + parentId, RekeningNummer.class);
        return query.getResultList();
    }
}
