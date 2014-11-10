package nl.dias.service;

import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;
import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import nl.dias.domein.Bijlage;
import nl.dias.domein.Relatie;
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
        verifyAll();
    }

    @Test
    public void testAlleBijlagesBijRelatie() {
        Relatie relatie = createMock(Relatie.class);

        List<Bijlage> bijlages = new ArrayList<Bijlage>();

        expect(repository.alleBijlagesBijRelatie(relatie)).andReturn(bijlages);

        replayAll();

        assertEquals(bijlages, service.alleBijlagesBijRelatie(relatie));
    }

    @Test
    public void testVerwijderBijlage() {
        Bijlage bijlage = createMock(Bijlage.class);
        expect(repository.lees(3L)).andReturn(bijlage);

        archiefService.setBucketName("dias");
        expectLastCall();

        expect(bijlage.getS3Identificatie()).andReturn("1234");
        archiefService.verwijderen("1234");
        expectLastCall();

        repository.verwijder(bijlage);
        expectLastCall();

        replayAll();

        service.verwijderBijlage(3L);
    }
}
