package nl.dias.repository;

import static org.junit.Assert.assertEquals;
import nl.dias.domein.Bedrijf;
import nl.dias.domein.Relatie;

import org.junit.Before;
import org.junit.Test;

public class BedrijfRepositoryTest {
    private BedrijfRepository bedrijfRepository;

    @Before
    public void setUp() throws Exception {
        bedrijfRepository = new BedrijfRepository();
        bedrijfRepository.setPersistenceContext("unittest");
    }

    @Test
    public void test() {
        Relatie relatie1 = new Relatie();
        Relatie relatie2 = new Relatie();

        bedrijfRepository.getEm().getTransaction().begin();
        bedrijfRepository.getEm().persist(relatie1);
        bedrijfRepository.getEm().persist(relatie2);
        bedrijfRepository.getEm().getTransaction().commit();

        Bedrijf bedrijf1 = new Bedrijf();
        bedrijf1.setRelatie(relatie1);
        Bedrijf bedrijf2 = new Bedrijf();
        bedrijf2.setRelatie(relatie2);
        Bedrijf bedrijf3 = new Bedrijf();
        bedrijf3.setRelatie(relatie2);

        bedrijfRepository.opslaan(bedrijf1);
        bedrijfRepository.opslaan(bedrijf2);
        bedrijfRepository.opslaan(bedrijf3);

        assertEquals(bedrijf1, bedrijfRepository.alleBedrijvenBijRelatie(relatie1).get(0));
        assertEquals(2, bedrijfRepository.alleBedrijvenBijRelatie(relatie2).size());
    }

}
