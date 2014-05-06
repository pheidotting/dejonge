package nl.dias.repository;

import static org.junit.Assert.assertEquals;
import nl.dias.domein.Bedrag;
import nl.dias.domein.Bedrijf;
import nl.dias.domein.Relatie;
import nl.dias.domein.VerzekeringsMaatschappij;
import nl.dias.domein.polis.AutoVerzekering;
import nl.dias.domein.polis.OngevallenVerzekering;
import nl.dias.domein.polis.ReisVerzekering;
import nl.dias.domein.polis.SoortAutoVerzekering;
import nl.dias.domein.polis.WoonhuisVerzekering;

import org.joda.time.LocalDate;
import org.junit.Before;
import org.junit.Test;

public class PolisRepositoryTest {
    private PolisRepository PolisRepository;

    @Before
    public void setUp() throws Exception {
        PolisRepository = new PolisRepository();
        PolisRepository.zetPersistenceContext("unittest");
    }

    @Test
    public void test() {
        Relatie relatie = new Relatie();
        PolisRepository.getEm().getTransaction().begin();
        PolisRepository.getEm().persist(relatie);
        PolisRepository.getEm().getTransaction().commit();

        VerzekeringsMaatschappij maatschappij = new VerzekeringsMaatschappij();
        maatschappij.setNaam("Patrick's Verzekeringen");
        PolisRepository.getEm().getTransaction().begin();
        PolisRepository.getEm().persist(maatschappij);
        PolisRepository.getEm().getTransaction().commit();

        AutoVerzekering autoVerzekering = new AutoVerzekering();
        autoVerzekering.setMaatschappij(maatschappij);
        autoVerzekering.setRelatie(relatie);
        autoVerzekering.setIngangsDatum(new LocalDate());
        autoVerzekering.setKenteken("46-NLV-5");
        autoVerzekering.setPremie(new Bedrag(123.00));
        autoVerzekering.setSoortAutoVerzekering(SoortAutoVerzekering.AUTO);
        autoVerzekering.setPolisNummer("polisNummer");
        ReisVerzekering reisVerzekering = new ReisVerzekering();
        reisVerzekering.setMaatschappij(maatschappij);
        reisVerzekering.setRelatie(relatie);

        relatie.getPolissen().add(autoVerzekering);
        relatie.getPolissen().add(reisVerzekering);

        PolisRepository.opslaan(autoVerzekering);
        System.out.println(autoVerzekering.getId());
        PolisRepository.opslaan(reisVerzekering);
        System.out.println(reisVerzekering.getId());

        assertEquals(2, PolisRepository.alles().size());
        assertEquals(1, PolisRepository.zoekPolissenOpSoort(AutoVerzekering.class).size());
        assertEquals(1, PolisRepository.zoekPolissenOpSoort(ReisVerzekering.class).size());

        // TODO delete werkt nog niet
        relatie.getPolissen().remove(autoVerzekering);
        PolisRepository.verwijder(autoVerzekering);

        assertEquals(0, PolisRepository.zoekPolissenOpSoort(AutoVerzekering.class).size());
        assertEquals(1, PolisRepository.zoekPolissenOpSoort(ReisVerzekering.class).size());
    }

    @Test
    public void alleVerzekeringsMaatschappijen() {
        VerzekeringsMaatschappij maatschappij1 = new VerzekeringsMaatschappij();
        VerzekeringsMaatschappij maatschappij2 = new VerzekeringsMaatschappij();
        VerzekeringsMaatschappij maatschappij3 = new VerzekeringsMaatschappij();

        maatschappij1.setNaam("maatschappij1");
        maatschappij2.setNaam("maatschappij2");
        maatschappij3.setNaam("maatschappij3");

        PolisRepository.getEm().getTransaction().begin();
        PolisRepository.getEm().persist(maatschappij1);
        PolisRepository.getEm().persist(maatschappij2);
        PolisRepository.getEm().persist(maatschappij3);
        PolisRepository.getEm().getTransaction().commit();

        assertEquals(3, PolisRepository.alleVerzekeringsMaatschappijen().size());
    }

    @Test
    public void allePolissenBijMaatschappij() {
        VerzekeringsMaatschappij maatschappij1 = new VerzekeringsMaatschappij();
        VerzekeringsMaatschappij maatschappij2 = new VerzekeringsMaatschappij();

        maatschappij1.setNaam("maatschappij1");
        maatschappij2.setNaam("maatschappij2");

        PolisRepository.getEm().getTransaction().begin();
        PolisRepository.getEm().persist(maatschappij1);
        PolisRepository.getEm().persist(maatschappij2);
        PolisRepository.getEm().getTransaction().commit();
    }

    @Test
    public void allePolissenBijRelatieEnZijnBedrijven() {
        VerzekeringsMaatschappij maatschappij = new VerzekeringsMaatschappij();
        maatschappij.setNaam("Naam");
        opslaan(maatschappij);

        Relatie relatie = new Relatie();

        Bedrijf bedrijf1 = new Bedrijf();
        bedrijf1.setRelatie(relatie);
        Bedrijf bedrijf2 = new Bedrijf();
        bedrijf2.setRelatie(relatie);

        relatie.getBedrijven().add(bedrijf1);
        relatie.getBedrijven().add(bedrijf2);

        AutoVerzekering autoVerzekering = new AutoVerzekering();
        autoVerzekering.setRelatie(relatie);
        autoVerzekering.setMaatschappij(maatschappij);
        WoonhuisVerzekering woonhuisVerzekering = new WoonhuisVerzekering();
        woonhuisVerzekering.setBedrijf(bedrijf1);
        woonhuisVerzekering.setMaatschappij(maatschappij);
        OngevallenVerzekering ongevallenVerzekering = new OngevallenVerzekering();
        ongevallenVerzekering.setBedrijf(bedrijf2);
        ongevallenVerzekering.setMaatschappij(maatschappij);

        relatie.getPolissen().add(autoVerzekering);
        bedrijf1.getPolissen().add(woonhuisVerzekering);
        bedrijf2.getPolissen().add(ongevallenVerzekering);

        opslaan(relatie);

        assertEquals(3, PolisRepository.allePolissenVanRelatieEnZijnBedrijf(relatie).size());

    }

    private void opslaan(Object object) {
        PolisRepository.getEm().getTransaction().begin();
        PolisRepository.getEm().persist(object);
        PolisRepository.getEm().getTransaction().commit();
    }
}
