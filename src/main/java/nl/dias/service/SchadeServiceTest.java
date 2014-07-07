package nl.dias.service;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import nl.dias.domein.SoortSchade;
import nl.dias.repository.SchadeRepository;

import org.easymock.EasyMock;
import org.easymock.EasyMockSupport;
import org.junit.Before;
import org.junit.Test;

public class SchadeServiceTest extends EasyMockSupport {
    private SchadeService service;
    private SchadeRepository schadeRepository;

    @Before
    public void setUp() throws Exception {
        service = new SchadeService();

        schadeRepository = createMock(SchadeRepository.class);
        service.setSchadeRepository(schadeRepository);
    }

    @Test
    public void testSoortenSchade() {
        SoortSchade soortSchade = createMock(SoortSchade.class);
        List<SoortSchade> lijst = new ArrayList<SoortSchade>();
        lijst.add(soortSchade);

        EasyMock.expect(schadeRepository.soortenSchade()).andReturn(lijst);

        replayAll();

        assertEquals(lijst, service.soortenSchade());

        verifyAll();
    }

    @Test
    public void testSoortenSchadeString() {
        SoortSchade soortSchade = createMock(SoortSchade.class);
        List<SoortSchade> lijst = new ArrayList<SoortSchade>();
        lijst.add(soortSchade);

        EasyMock.expect(schadeRepository.soortenSchade("omschr")).andReturn(lijst);

        replayAll();

        assertEquals(lijst, service.soortenSchade("omschr"));

        verifyAll();
    }

}
