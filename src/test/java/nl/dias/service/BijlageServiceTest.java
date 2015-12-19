package nl.dias.service;

import nl.dias.domein.Bijlage;
import nl.dias.domein.Relatie;
import nl.dias.repository.BijlageRepository;
import org.easymock.*;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

import static org.easymock.EasyMock.*;
import static org.junit.Assert.assertEquals;

@RunWith(EasyMockRunner.class)
public class BijlageServiceTest extends EasyMockSupport {
    @TestSubject
    private BijlageService service=new BijlageService();
    @Mock
    private BijlageRepository repository;

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
        Bijlage bijlage = new Bijlage();
        expect(repository.lees(3L)).andReturn(bijlage);

        repository.verwijder(bijlage);
        expectLastCall();

        replayAll();

        service.verwijderBijlage(3L);
    }

    @Test
    public void testWijzigOmschrijvingBijlage() {
        String nieuweOmschrijving = "nieuweOmschrijving";
        Long id = 46L;

        Bijlage bijlage = new Bijlage();
        bijlage.setOmschrijving("omschrijving");

        expect(repository.leesBijlage(id)).andReturn(bijlage);

        Capture<Bijlage> bijlageCapture = newCapture();
        repository.opslaan(capture(bijlageCapture));
        expectLastCall();

        replayAll();

        service.wijzigOmschrijvingBijlage(id, nieuweOmschrijving);

        assertEquals(nieuweOmschrijving, bijlageCapture.getValue().getOmschrijving());
    }
}
