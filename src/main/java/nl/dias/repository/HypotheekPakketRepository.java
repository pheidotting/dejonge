package nl.dias.repository;

import java.util.List;

import javax.inject.Named;
import javax.persistence.TypedQuery;

import nl.dias.domein.HypotheekPakket;
import nl.dias.domein.Relatie;
import nl.lakedigital.hulpmiddelen.repository.AbstractRepository;

@Named
public class HypotheekPakketRepository extends AbstractRepository<HypotheekPakket> {

    public HypotheekPakketRepository() {
        super(HypotheekPakket.class);
        zetPersistenceContext("dias");
    }

    @Override
    public void setPersistenceContext(String persistenceContext) {
        zetPersistenceContext(persistenceContext);
    }

    public List<HypotheekPakket> allesVanRelatie(Relatie relatie) {
        TypedQuery<HypotheekPakket> query = getEm().createNamedQuery("HypotheekPakket.allesVanRelatie", HypotheekPakket.class);
        query.setParameter("relatie", relatie);

        return query.getResultList();
    }

}
