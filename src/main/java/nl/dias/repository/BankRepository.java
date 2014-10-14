package nl.dias.repository;

import javax.inject.Named;

import nl.dias.domein.Bank;
import nl.lakedigital.hulpmiddelen.repository.AbstractRepository;

@Named
public class BankRepository extends AbstractRepository<Bank> {
    public BankRepository() {
        super(Bank.class);
        zetPersistenceContext("dias");
    }

    @Override
    public void setPersistenceContext(String persistenceContext) {
        zetPersistenceContext(persistenceContext);
    }

}
