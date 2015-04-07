package nl.dias.repository;

import nl.dias.domein.DIASGebruiker;
import nl.lakedigital.hulpmiddelen.repository.AbstractRepository;

import org.springframework.stereotype.Repository;

@Repository
public class DIASGebruikerRepository extends AbstractRepository<DIASGebruiker> {
    public DIASGebruikerRepository() {
        super(DIASGebruiker.class);
        zetPersistenceContext("dias");
    }

    @Override
    public void setPersistenceContext(String persistenceContext) {
        zetPersistenceContext(persistenceContext);
    }

}
