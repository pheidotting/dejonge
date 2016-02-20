package nl.dias.repository;

import nl.dias.domein.Adres;
import nl.dias.web.SoortEntiteit;
import nl.lakedigital.hulpmiddelen.repository.AbstractRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;

@Repository
public class AdresRepository extends AbstractRepository<Adres> {
    public AdresRepository() {
        super(Adres.class);
        zetPersistenceContext("dias");
    }

    @Override
    public void setPersistenceContext(String persistenceContext) {
        zetPersistenceContext(persistenceContext);
    }

    public List<Adres> alles(SoortEntiteit soortEntiteit, Long parentId) {
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

        TypedQuery<Adres> query = getEm().createQuery("select t from Adres t where t." + where + parentId, Adres.class);
        return query.getResultList();
    }
}
