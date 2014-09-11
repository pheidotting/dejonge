package nl.dias.service;

import static org.easymock.EasyMock.expectLastCall;
import nl.dias.domein.Opmerking;
import nl.dias.repository.OpmerkingRepository;

import org.easymock.EasyMockSupport;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class OpmerkingServiceTest extends EasyMockSupport {
    private OpmerkingService service;
    private OpmerkingRepository repository;

    @Before
    public void setUp() throws Exception {
        repository = createMock(OpmerkingRepository.class);

        service = new OpmerkingService();
        service.setOpmerkingRepository(repository);
    }

    @After
    public void tearDown() throws Exception {
        verifyAll();
    }

    @Test
    public void test() {
        Opmerking opmerking = createMock(Opmerking.class);

        repository.opslaan(opmerking);
        expectLastCall();

        replayAll();

        service.opslaan(opmerking);
    }

}
