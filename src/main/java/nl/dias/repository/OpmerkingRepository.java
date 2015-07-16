package nl.dias.repository;

import java.util.List;

import javax.inject.Named;
import javax.persistence.TypedQuery;

import nl.dias.domein.Opmerking;
import nl.dias.domein.Relatie;
import nl.lakedigital.hulpmiddelen.repository.AbstractRepository;

@Named
public class OpmerkingRepository extends AbstractRepository<Opmerking> {
    public OpmerkingRepository() {
        super(Opmerking.class);
        zetPersistenceContext("dias");
    }

    @Override
    public void setPersistenceContext(String persistenceContext) {
        zetPersistenceContext(persistenceContext);
    }

    public List<Opmerking> alleOpmerkingenVoorRelatie(Relatie relatie) {
        TypedQuery<Opmerking> query = getEm().createNamedQuery("Opmerking.allesVoorRelatie", Opmerking.class);
        query.setParameter("relatie", relatie);

        return query.getResultList();
    }
}