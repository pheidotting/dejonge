package nl.dias.repository;

import static org.junit.Assert.*;
import static org.easymock.EasyMock.*;

import com.google.common.collect.Lists;
import nl.dias.domein.Bedrijf;
import nl.dias.domein.JaarCijfers;
import org.easymock.EasyMockRunner;
import org.easymock.EasyMockSupport;
import org.easymock.*;
import org.junit.*;
import org.junit.runner.RunWith;

public class JaarCijfersRepositoryTest  {
    private JaarCijfersRepository jaarCijfersRepository;

    @Before
    public void setUp() throws Exception {
        jaarCijfersRepository = new JaarCijfersRepository();
        jaarCijfersRepository.setPersistenceContext("unittest");
    }

    @Test
    public void opslaan(){
        JaarCijfers jaarCijfers=new JaarCijfers();
        jaarCijfers.setJaar(2014L);

        jaarCijfersRepository.opslaan(jaarCijfers);

        assertEquals(1,jaarCijfersRepository.alles().size());
    }

    @Test
    public void testAllesBijBedrijf() throws Exception {
        Bedrijf bedrijf=new Bedrijf();

        jaarCijfersRepository.getEm().getTransaction().begin();
        jaarCijfersRepository.getEm().persist(bedrijf);
        jaarCijfersRepository.getEm().getTransaction().commit();

        JaarCijfers jaarCijfers=new JaarCijfers();
        jaarCijfers.setBedrijf(bedrijf);

        jaarCijfersRepository.opslaan(jaarCijfers);

        assertEquals(Lists.newArrayList(jaarCijfers),jaarCijfersRepository.allesBijBedrijf(bedrijf));
    }
}