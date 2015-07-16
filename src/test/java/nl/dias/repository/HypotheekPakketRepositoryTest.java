package nl.dias.repository;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.util.Set;

import nl.dias.domein.Bedrag;
import nl.dias.domein.Gebruiker;
import nl.dias.domein.Hypotheek;
import nl.dias.domein.HypotheekPakket;
import nl.dias.domein.Relatie;
import nl.dias.domein.SoortHypotheek;

import org.joda.time.LocalDate;
import org.junit.Before;
import org.junit.Test;

public class HypotheekPakketRepositoryTest {
    private HypotheekPakketRepository repository;

    @Before
    public void setUp() throws Exception {
        repository = new HypotheekPakketRepository();
        repository.setPersistenceContext("unittest");
    }

    @Test
    public void test() {
        Relatie relatie = new Relatie();
        SoortHypotheek soortHypotheek = new SoortHypotheek();
        soortHypotheek.setOmschrijving("jadajada");

        repository.getEm().getTransaction().begin();
        repository.getEm().persist(soortHypotheek);
        repository.getEm().persist(relatie);
        repository.getEm().getTransaction().commit();

        Hypotheek hypotheek1 = maakHypotheek(soortHypotheek, "bank", relatie, "leningNummer1");
        Hypotheek hypotheek2 = maakHypotheek(soortHypotheek, "bank", relatie, "leningNummer2");
        Hypotheek hypotheek3 = maakHypotheek(soortHypotheek, "bank", relatie, "leningNummer3");

        relatie.getHypotheken().add(hypotheek1);
        relatie.getHypotheken().add(hypotheek2);
        relatie.getHypotheken().add(hypotheek3);

        repository.getEm().getTransaction().begin();
        repository.getEm().persist(hypotheek1);
        repository.getEm().persist(hypotheek2);
        repository.getEm().getTransaction().commit();

        HypotheekPakket pakket = new HypotheekPakket();
        pakket.setRelatie(relatie);
        relatie.getHypotheekPakketten().add(pakket);
        pakket.getHypotheken().add(hypotheek2);
        pakket.getHypotheken().add(hypotheek3);
        repository.getEm().getTransaction().begin();
        repository.getEm().persist(pakket);
        repository.getEm().getTransaction().commit();

        repository.getEm().getTransaction().begin();
        repository.getEm().persist(hypotheek3);
        repository.getEm().getTransaction().commit();

        hypotheek2.setHypotheekPakket(pakket);
        pakket.getHypotheken().add(hypotheek2);
        hypotheek3.setHypotheekPakket(pakket);
        pakket.getHypotheken().add(hypotheek3);

        repository.getEm().getTransaction().begin();
        repository.getEm().merge(hypotheek1);
        repository.getEm().merge(hypotheek2);
        repository.getEm().merge(hypotheek3);
        repository.getEm().merge(pakket);
        repository.getEm().merge(relatie);
        repository.getEm().getTransaction().commit();

        assertEquals(1, repository.alles().size());
        assertEquals(1, repository.allesVanRelatie(relatie).size());

        Relatie r = (Relatie) repository.getEm().find(Gebruiker.class, relatie.getId());
        assertEquals(1, r.getHypotheken().size());
        Set<HypotheekPakket> hps = r.getHypotheekPakketten();
        assertEquals(1, hps.size());
        HypotheekPakket hp = hps.iterator().next();
        assertEquals(2, hp.getHypotheken().size());
    }

    private Hypotheek maakHypotheek(SoortHypotheek soortHypotheek, String bank, Relatie relatie, String leningNummer) {
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
        hypotheek.setOnderpand("onderpand");
        hypotheek.setLeningNummer(leningNummer);
        hypotheek.setRente(new BigDecimal("234"));
        hypotheek.setTaxatieDatum(new LocalDate());
        hypotheek.setVrijeVerkoopWaarde(new Bedrag("567890.12"));
        hypotheek.setWaardeNaVerbouwing(new Bedrag("678901.12"));
        hypotheek.setWaardeVoorVerbouwing(new Bedrag("789012.34"));
        hypotheek.setWozWaarde(new Bedrag("890123.45"));
        hypotheek.setBank(bank);
        hypotheek.setRelatie(relatie);

        return hypotheek;
    }
}
