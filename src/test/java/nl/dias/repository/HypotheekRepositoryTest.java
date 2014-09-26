package nl.dias.repository;

import static org.junit.Assert.assertEquals;
import nl.dias.domein.Bedrag;
import nl.dias.domein.Hypotheek;
import nl.dias.domein.Relatie;
import nl.dias.domein.SoortHypotheek;

import org.joda.time.LocalDate;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class HypotheekRepositoryTest {
    private HypotheekRepository hypotheekRepository;

    @Before
    public void setUp() throws Exception {
        hypotheekRepository = new HypotheekRepository();
        hypotheekRepository.setPersistenceContext("unittest");
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void test() {
        Relatie relatie = new Relatie();
        SoortHypotheek soortHypotheek = new SoortHypotheek();
        soortHypotheek.setOmschrijving("jadajada");

        hypotheekRepository.getEm().getTransaction().begin();
        hypotheekRepository.getEm().persist(relatie);
        hypotheekRepository.getEm().persist(soortHypotheek);
        hypotheekRepository.getEm().getTransaction().commit();

        Hypotheek hypotheek = new Hypotheek();
        hypotheek.setDuur(10L);
        hypotheek.setDuurRenteVastePeriode(25L);
        hypotheek.setEindDatum(new LocalDate());
        hypotheek.setEindDatumRenteVastePeriode(new LocalDate());
        hypotheek.setHypotheekBedrag(new Bedrag("123456.78"));
        hypotheek.setHypotheekVorm(soortHypotheek);
        hypotheek.setIngangsDatum(new LocalDate());
        hypotheek.setIngangsDatumRenteVastePeriode(new LocalDate());
        hypotheek.setKoopsom(new Bedrag("234567.89"));
        hypotheek.setMarktWaarde(new Bedrag("345678.90"));
        hypotheek.setOmschrijving("abcdef");
        hypotheek.setOnderpand(new Bedrag("456789.01"));
        hypotheek.setRente(234);
        hypotheek.setTaxatieDatum(new LocalDate());
        hypotheek.setVrijeVerkoopWaarde(new Bedrag("567890.12"));
        hypotheek.setWaardeNaVerbouwing(new Bedrag("678901.12"));
        hypotheek.setWaardeVoorVerbouwing(new Bedrag("789012.34"));
        hypotheek.setWozWaarde(new Bedrag("890123.45"));
        hypotheek.setRelatie(relatie);

        relatie.getHypotheken().add(hypotheek);

        hypotheekRepository.opslaan(hypotheek);
        hypotheekRepository.getEm().getTransaction().begin();
        hypotheekRepository.getEm().merge(relatie);
        hypotheekRepository.getEm().getTransaction().commit();

        assertEquals(1, hypotheekRepository.alles().size());
    }

}
