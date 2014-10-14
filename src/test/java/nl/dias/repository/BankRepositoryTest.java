package nl.dias.repository;

import static org.junit.Assert.assertEquals;
import nl.dias.domein.Bank;

import org.junit.Before;
import org.junit.Test;

public class BankRepositoryTest {
    private BankRepository repository;

    @Before
    public void setUp() throws Exception {
        repository = new BankRepository();
        repository.setPersistenceContext("unittest");
    }

    @Test
    public void test() {
        Bank bank = new Bank();
        bank.setNaam("Bank naam");

        repository.opslaan(bank);

        assertEquals(1, repository.alles().size());
    }

}
