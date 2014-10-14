package nl.dias.service;

import static org.easymock.EasyMock.expect;
import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import nl.dias.domein.Bank;
import nl.dias.repository.BankRepository;

import org.easymock.EasyMockSupport;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class BankServiceTest extends EasyMockSupport {
    private BankService service;
    private BankRepository bankRepository;

    @Before
    public void setUp() throws Exception {
        service = new BankService();

        bankRepository = createMock(BankRepository.class);
        service.setBankRepository(bankRepository);
    }

    @After
    public void tearDown() {
        verifyAll();
    }

    @Test
    public void alles() {
        List<Bank> lijst = new ArrayList<Bank>();

        expect(bankRepository.alles()).andReturn(lijst);

        replayAll();

        assertEquals(lijst, service.alles());
    }

}
