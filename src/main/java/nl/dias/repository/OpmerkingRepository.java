package nl.dias.repository;

import javax.inject.Named;

import nl.dias.domein.Opmerking;
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
}