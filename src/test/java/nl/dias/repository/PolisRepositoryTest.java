package nl.dias.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import javax.persistence.NoResultException;

import nl.dias.domein.Bedrag;
import nl.dias.domein.Bedrijf;
import nl.dias.domein.Kantoor;
import nl.dias.domein.Relatie;
import nl.dias.domein.VerzekeringsMaatschappij;
import nl.dias.domein.polis.AutoVerzekering;
import nl.dias.domein.polis.CamperVerzekering;
import nl.dias.domein.polis.FietsVerzekering;
import nl.dias.domein.polis.OngevallenVerzekering;
import nl.dias.domein.polis.ReisVerzekering;
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
        autoVerzekering.setPremie(new Bedrag(123.00));
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

    @Test
    public void zoekOpPolisNummer() {
        Kantoor kantoor1 = new Kantoor();
        Kantoor kantoor2 = new Kantoor();

        opslaan(kantoor1);
        opslaan(kantoor2);

        Relatie relatie1 = new Relatie();
        Relatie relatie2 = new Relatie();

        relatie1.setKantoor(kantoor1);
        relatie2.setKantoor(kantoor2);

        opslaan(relatie1);
        opslaan(relatie2);

        kantoor1.getRelaties().add(relatie1);
        kantoor1.getRelaties().add(relatie2);

        update(kantoor1);
        update(kantoor2);

        VerzekeringsMaatschappij maatschappij = new VerzekeringsMaatschappij();
        maatschappij.setNaam("Naam");
        opslaan(maatschappij);

        AutoVerzekering verzekering1 = new AutoVerzekering();
        CamperVerzekering verzekering2 = new CamperVerzekering();
        FietsVerzekering verzekering3 = new FietsVerzekering();

        verzekering1.setPolisNummer("polisNummer1");
        verzekering1.setRelatie(relatie1);
        verzekering1.setMaatschappij(maatschappij);
        verzekering2.setPolisNummer("polisNummer2");
        verzekering2.setRelatie(relatie1);
        verzekering2.setMaatschappij(maatschappij);
        verzekering3.setPolisNummer("polisNummer3");
        verzekering3.setRelatie(relatie2);
        verzekering3.setMaatschappij(maatschappij);

        opslaan(verzekering1);
        opslaan(verzekering2);
        opslaan(verzekering3);

        relatie1.getPolissen().add(verzekering1);
        relatie1.getPolissen().add(verzekering2);
        relatie2.getPolissen().add(verzekering3);

        update(relatie1);
        update(relatie2);

        assertEquals(verzekering1, PolisRepository.zoekOpPolisNummer("polisNummer1", kantoor1));
        assertEquals(verzekering2, PolisRepository.zoekOpPolisNummer("polisNummer2", kantoor1));
        assertEquals(verzekering3, PolisRepository.zoekOpPolisNummer("polisNummer3", kantoor2));

        try {
            assertEquals(verzekering3, PolisRepository.zoekOpPolisNummer("polisNummer3", kantoor1));
            fail("exception verwacht");
        } catch (NoResultException e) {
            // verwacht
        }

    }

    private void opslaan(Object object) {
        PolisRepository.getEm().getTransaction().begin();
        PolisRepository.getEm().persist(object);
        PolisRepository.getEm().getTransaction().commit();
    }

    private void update(Object object) {
        PolisRepository.getEm().getTransaction().begin();
        PolisRepository.getEm().merge(object);
        PolisRepository.getEm().getTransaction().commit();
    }
}
