package nl.dias.service;

import nl.dias.repository.BijlageRepository;
import nl.lakedigital.archief.service.ArchiefService;

import org.easymock.EasyMockSupport;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class BijlageServiceTest extends EasyMockSupport {
    private BijlageService service;
    private ArchiefService archiefService;
    private BijlageRepository repository;

    @Before
    public void setUp() throws Exception {
        service = new BijlageService();

        archiefService = createMock(ArchiefService.class);
        service.setArchiefService(archiefService);

        repository = createMock(BijlageRepository.class);
        service.setBijlageRepository(repository);
    }

    @After
    public void tearDown() throws Exception {
        // verifyAll();
    }

    @Test
    public void testAlleBijlagesBijRelatie() {
    }

    @Test
    public void testUploaden() {
    }

    @Test
    public void testWriteToFile() {
    }

}