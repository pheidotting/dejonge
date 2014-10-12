package nl.dias.repository;

import static org.junit.Assert.assertEquals;

import java.util.Set;

import nl.dias.domein.Bank;
import nl.dias.domein.Bedrag;
import nl.dias.domein.Gebruiker;
import nl.dias.domein.Hypotheek;
import nl.dias.domein.HypotheekPakket;
import nl.dias.domein.Relatie;
import nl.dias.domein.SoortHypotheek;

import org.joda.time.LocalDate;
import org.junit.Before;
import org.junit.Test;

public class HypotheekRepositoryTest {
    private HypotheekRepository hypotheekRepository;

    @Before
    public void setUp() throws Exception {
        hypotheekRepository = new HypotheekRepository();
        hypotheekRepository.setPersistenceContext("unittest");
    }

    @Test
    public void test() {
        Relatie relatie = new Relatie();
        Relatie relatie2 = new Relatie();
        SoortHypotheek soortHypotheek = new SoortHypotheek();
        soortHypotheek.setOmschrijving("jadajada");

        Bank bank = new Bank();
        bank.setNaam("naamBank");

        hypotheekRepository.getEm().getTransaction().begin();
        hypotheekRepository.getEm().persist(soortHypotheek);
        hypotheekRepository.getEm().persist(bank);
        hypotheekRepository.getEm().persist(relatie);
        hypotheekRepository.getEm().persist(relatie2);
        hypotheekRepository.getEm().getTransaction().commit();

        Hypotheek hypotheek1 = maakHypotheek(soortHypotheek, bank, relatie, "leningNummer1");
        Hypotheek hypotheek2 = maakHypotheek(soortHypotheek, bank, relatie, "leningNummer2");
        Hypotheek hypotheek3 = maakHypotheek(soortHypotheek, bank, relatie, "leningNummer3");
        Hypotheek hypotheek4 = maakHypotheek(soortHypotheek, bank, relatie2, "leningNummer4");

        relatie.getHypotheken().add(hypotheek1);
        relatie.getHypotheken().add(hypotheek2);
        relatie.getHypotheken().add(hypotheek3);
        relatie2.getHypotheken().add(hypotheek4);

        hypotheekRepository.opslaan(hypotheek1);
        hypotheekRepository.opslaan(hypotheek2);
        hypotheekRepository.opslaan(hypotheek4);

        HypotheekPakket pakket = new HypotheekPakket();
        pakket.setRelatie(relatie);
        relatie.getHypotheekPakketten().add(pakket);
        pakket.getHypotheken().add(hypotheek2);
        pakket.getHypotheken().add(hypotheek3);
        hypotheekRepository.getEm().getTransaction().begin();
        hypotheekRepository.getEm().persist(pakket);
        hypotheekRepository.getEm().getTransaction().commit();

        hypotheekRepository.opslaan(hypotheek3);

        hypotheek2.setHypotheekPakket(pakket);
        pakket.getHypotheken().add(hypotheek2);
        hypotheek3.setHypotheekPakket(pakket);
        pakket.getHypotheken().add(hypotheek3);

        hypotheekRepository.getEm().getTransaction().begin();
        hypotheekRepository.getEm().merge(hypotheek1);
        hypotheekRepository.getEm().merge(hypotheek2);
        hypotheekRepository.getEm().merge(hypotheek3);
        hypotheekRepository.getEm().merge(pakket);
        hypotheekRepository.getEm().merge(relatie);
        hypotheekRepository.getEm().getTransaction().commit();

        assertEquals(4, hypotheekRepository.alles().size());
        assertEquals(1, hypotheekRepository.allesVanRelatie(relatie).size());
        assertEquals(1, hypotheekRepository.allesVanRelatie(relatie2).size());

        Relatie r = (Relatie) hypotheekRepository.getEm().find(Gebruiker.class, relatie.getId());
        assertEquals(1, r.getHypotheken().size());
        Set<HypotheekPakket> hps = r.getHypotheekPakketten();
        assertEquals(1, hps.size());
        HypotheekPakket hp = hps.iterator().next();
        assertEquals(2, hp.getHypotheken().size());
    }

    private Hypotheek maakHypotheek(SoortHypotheek soortHypotheek, Bank bank, Relatie relatie, String leningNummer) {
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
        hypotheek.setRente(234);
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
