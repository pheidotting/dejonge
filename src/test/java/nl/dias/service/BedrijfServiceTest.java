package nl.dias.service;

import static org.junit.Assert.assertEquals;
import nl.dias.domein.Bedrijf;
import nl.dias.repository.BedrijfRepository;

import org.easymock.EasyMock;
import org.easymock.EasyMockSupport;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class BedrijfServiceTest extends EasyMockSupport {
    private BedrijfService bedrijfService;
    private BedrijfRepository bedrijfRepository;

    @Before
    public void setUp() throws Exception {
        bedrijfService = new BedrijfService();

        bedrijfRepository = createMock(BedrijfRepository.class);
        bedrijfService.setBedrijfRepository(bedrijfRepository);
    }

    @After
    public void afterTest() {
        verifyAll();
    }

    @Test
    public void testOpslaan() {
        Bedrijf bedrijf = new Bedrijf();

        bedrijfRepository.opslaan(bedrijf);
        EasyMock.expectLastCall();

        replayAll();

        bedrijfService.opslaan(bedrijf);
    }

    @Test
    public void testLees() {
        Bedrijf bedrijf = new Bedrijf();

        EasyMock.expect(bedrijfRepository.lees(1L)).andReturn(bedrijf);

        replayAll();

        assertEquals(bedrijf, bedrijfService.lees(1L));
    }
}
