package nl.dias.repository;

import javax.inject.Named;

import nl.dias.domein.Bedrijf;
import nl.lakedigital.hulpmiddelen.repository.AbstractRepository;

@Named
public class BedrijfRepository extends AbstractRepository<Bedrijf> {
    public BedrijfRepository() {
        super(Bedrijf.class);
        zetPersistenceContext("dias");
    }

    @Override
    public void setPersistenceContext(String persistenceContext) {
        zetPersistenceContext(persistenceContext);
    }

}
