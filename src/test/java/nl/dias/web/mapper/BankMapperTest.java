package nl.dias.web.mapper;

import static org.junit.Assert.assertEquals;
import nl.dias.domein.Bank;
import nl.dias.domein.json.JsonBank;

import org.junit.Before;
import org.junit.Test;

public class BankMapperTest {
    private BankMapper mapper;
    private Bank bank;
    private JsonBank jsonBank;

    @Before
    public void setUp() throws Exception {
        mapper = new BankMapper();

        bank = new Bank();
        bank.setId(58L);
        bank.setNaam("Naam Bank");

        jsonBank = new JsonBank();
        jsonBank.setId(58L);
        jsonBank.setNaam("Naam Bank");
    }

    @Test
    public void test() {
        assertEquals(jsonBank, mapper.mapNaarJson(bank));
    }

}
