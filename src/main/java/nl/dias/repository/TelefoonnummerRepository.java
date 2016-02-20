package nl.dias.repository;

import nl.dias.domein.Telefoonnummer;
import nl.dias.web.SoortEntiteit;
import nl.lakedigital.hulpmiddelen.repository.AbstractRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import java.util.ArrayList;
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

    public List<Telefoonnummer> alles(SoortEntiteit soortEntiteit, Long parentId) {
        String where = null;
        switch (soortEntiteit) {
            case BEDRIJF:
                where = "bedrijf = ";
                break;
            case RELATIE:
                where = "relatie = ";
                break;
            case CONTACTPERSOON:
                where = "contactPersoon = ";
                break;
            default:
                return new ArrayList<>();
        }

        TypedQuery<Telefoonnummer> query = getEm().createQuery("select t from Telefoonnummer t where t." + where + parentId, Telefoonnummer.class);
        return query.getResultList();
    }
}
