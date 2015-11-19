package nl.dias.service;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;
import static org.easymock.EasyMock.*;

import com.google.common.collect.Lists;
import nl.dias.domein.Bedrijf;
import nl.dias.domein.JaarCijfers;
import nl.dias.repository.JaarCijfersRepository;
import org.easymock.EasyMockRunner;
import org.easymock.EasyMockSupport;
import org.easymock.*;
import org.joda.time.LocalDate;
import org.junit.*;
import org.junit.runner.RunWith;

import java.util.List;

@RunWith(EasyMockRunner.class)
public class JaarCijfersServiceTest extends EasyMockSupport {
    @TestSubject
    private JaarCijfersService jaarCijfersService = new JaarCijfersService();

    @Mock
    private JaarCijfersRepository jaarCijfersRepository;
    @Mock
    private BedrijfService bedrijfService;

@Test
    public void testAllesMetHuidigJaar(){
        Long bedrijsId = 55L;
        Bedrijf bedrijf                =new Bedrijf();

        expect(bedrijfService.lees(bedrijsId)).andReturn(bedrijf);

        JaarCijfers jaarCijfersVorigJaar=new JaarCijfers();
        jaarCijfersVorigJaar.setJaar(Long.valueOf(LocalDate.now().minusYears(1).getYear()));
        JaarCijfers jaarCijfersHuidigJaar = new JaarCijfers();
        jaarCijfersHuidigJaar.setJaar(Long.valueOf(LocalDate.now().getYear()));

        expect(jaarCijfersRepository.allesBijBedrijf(bedrijf)).andReturn(Lists.newArrayList(jaarCijfersVorigJaar,jaarCijfersHuidigJaar));

        replayAll();

        assertEquals(Lists.newArrayList(jaarCijfersVorigJaar, jaarCijfersHuidigJaar), jaarCijfersService.alles(bedrijsId));

        verifyAll();
    }@Test
    public void testAllesZonderHuidigJaar(){
        Long bedrijsId = 55L;
        Bedrijf bedrijf                =new Bedrijf();

        expect(bedrijfService.lees(bedrijsId)).andReturn(bedrijf);

        JaarCijfers jaarCijfersVorigJaar=new JaarCijfers();
        jaarCijfersVorigJaar.setJaar(Long.valueOf(LocalDate.now().minusYears(1).getYear()));
        JaarCijfers jaarCijfersHuidigJaar = new JaarCijfers();
        jaarCijfersHuidigJaar.setJaar(Long.valueOf(LocalDate.now().getYear()));

        expect(jaarCijfersRepository.allesBijBedrijf(bedrijf)).andReturn(Lists.newArrayList(jaarCijfersVorigJaar));

        replayAll();

        assertEquals(Lists.newArrayList(jaarCijfersVorigJaar, jaarCijfersHuidigJaar), jaarCijfersService.alles(bedrijsId));

        verifyAll();
    }
    }