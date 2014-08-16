package nl.dias.service;

import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;
import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import nl.dias.domein.Bedrijf;
import nl.dias.domein.Relatie;
import nl.dias.repository.BedrijfRepository;

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
        expectLastCall();

        replayAll();

        bedrijfService.opslaan(bedrijf);
    }

    @Test
    public void testLees() {
        Bedrijf bedrijf = new Bedrijf();

        expect(bedrijfRepository.lees(1L)).andReturn(bedrijf);

        replayAll();

        assertEquals(bedrijf, bedrijfService.lees(1L));
    }

    @Test
    public void testAlleBedrijvenBijRelatie() {
        List<Bedrijf> bedrijven = new ArrayList<>();
        Relatie relatie = new Relatie();

        expect(bedrijfRepository.alleBedrijvenBijRelatie(relatie)).andReturn(bedrijven);

        replayAll();

        assertEquals(bedrijven, bedrijfService.alleBedrijvenBijRelatie(relatie));
    }

    @Test
    public void testVerwijder() {
        Long id = 69L;
        Bedrijf bedrijf = new Bedrijf();

        expect(bedrijfRepository.lees(id)).andReturn(bedrijf);
        bedrijfRepository.verwijder(bedrijf);
        expectLastCall();

        replayAll();

        bedrijfService.verwijder(id);
    }
}