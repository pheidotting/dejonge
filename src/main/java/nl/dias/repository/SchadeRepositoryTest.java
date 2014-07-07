package nl.dias.repository;

import static org.junit.Assert.assertEquals;
import nl.dias.domein.SoortSchade;

import org.junit.Before;
import org.junit.Test;

public class SchadeRepositoryTest {
    private SchadeRepository repository;

    @Before
    public void setUp() throws Exception {
        repository = new SchadeRepository();
        repository.setPersistenceContext("unittest");
    }

    @Test
    public void testSoortenSchade() {
        SoortSchade soortSchade1 = new SoortSchade();
        soortSchade1.setIngebruik(true);
        soortSchade1.setOmschrijving("bab");

        SoortSchade soortSchade2 = new SoortSchade();
        soortSchade2.setIngebruik(false);
        soortSchade2.setOmschrijving("ccc");

        SoortSchade soortSchade3 = new SoortSchade();
        soortSchade3.setIngebruik(true);
        soortSchade3.setOmschrijving("deb");

        repository.getTx().begin();
        repository.getEm().persist(soortSchade1);
        repository.getEm().persist(soortSchade2);
        repository.getEm().persist(soortSchade3);
        repository.getTx().commit();

        assertEquals(2, repository.soortenSchade().size());
        assertEquals(2, repository.soortenSchade("b").size());
        assertEquals(1, repository.soortenSchade("a").size());
        assertEquals(1, repository.soortenSchade("e").size());
        assertEquals(0, repository.soortenSchade("c").size());
    }
}
