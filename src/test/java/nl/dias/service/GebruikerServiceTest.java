package nl.dias.service;

import com.google.common.collect.Lists;
import nl.dias.domein.*;
import nl.dias.domein.polis.AutoVerzekering;
import nl.dias.domein.polis.Polis;
import nl.dias.messaging.sender.AanmakenTaakSender;
import nl.dias.messaging.sender.AdresAangevuldSender;
import nl.dias.messaging.sender.BsnAangevuldSender;
import nl.dias.messaging.sender.EmailAdresAangevuldSender;
import nl.dias.repository.GebruikerRepository;
import nl.dias.repository.KantoorRepository;
import nl.dias.repository.PolisRepository;
import nl.lakedigital.as.messaging.AanmakenTaak;
import nl.lakedigital.as.messaging.AanmakenTaak.SoortTaak;
import nl.lakedigital.as.messaging.AdresAangevuld;
import nl.lakedigital.as.messaging.BsnAangevuld;
import nl.lakedigital.as.messaging.EmailadresAangevuld;
import nl.lakedigital.loginsystem.exception.NietGevondenException;
import org.easymock.EasyMockSupport;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.easymock.EasyMock.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

@Ignore
public class GebruikerServiceTest extends EasyMockSupport {
    private GebruikerRepository repository;
    private GebruikerService service;
    private AanmakenTaakSender aanmakenTaakSender;
    private AdresAangevuldSender adresAangevuldSender;
    private EmailAdresAangevuldSender emailAdresAangevuldSender;
    private BsnAangevuldSender bsnAangevuldSender;
    private PolisRepository polisRepository;
    private KantoorRepository kantoorRepository;

    @Before
    public void setUp() throws Exception {
        service = new GebruikerService();

        repository = createMock(GebruikerRepository.class);
        service.setGebruikerRepository(repository);

        aanmakenTaakSender = createMock(AanmakenTaakSender.class);
        service.setAanmakenTaakSender(aanmakenTaakSender);

        adresAangevuldSender = createMock(AdresAangevuldSender.class);
        service.setAdresAangevuldSender(adresAangevuldSender);

        emailAdresAangevuldSender = createMock(EmailAdresAangevuldSender.class);
        service.setEmailAdresAangevuldSender(emailAdresAangevuldSender);

        bsnAangevuldSender = createMock(BsnAangevuldSender.class);
        service.setBsnAangevuldSender(bsnAangevuldSender);

        polisRepository = createMock(PolisRepository.class);
        service.setPolisRepository(polisRepository);

        kantoorRepository = createMock(KantoorRepository.class);
        service.setKantoorRepository(kantoorRepository);
    }

    @After
    public void teardown() {
        verifyAll();
    }

    @Test
    public void testLees() {
        Gebruiker gebruiker = new Relatie();

        expect(repository.lees(1L)).andReturn(gebruiker);

        replayAll();

        assertEquals(gebruiker, service.lees(1L));
    }

    @Test
    public void testAlleRelaties() {
        Kantoor kantoor = new Kantoor();
        Relatie relatie = new Relatie();
        List<Relatie> relaties = new ArrayList<>();
        relaties.add(relatie);

        expect(repository.alleRelaties(kantoor)).andReturn(relaties);

        replayAll();

        assertEquals(relaties, service.alleRelaties(kantoor));
    }

    @Test
    public void testOpslaan() throws UnsupportedEncodingException, NoSuchAlgorithmException, NietGevondenException {
        Relatie relatie = new Relatie();
        relatie.setBsn("1234");
        relatie.setAdressen(Lists.newArrayList(maakAdres()));
        relatie.setIdentificatie("id");

        expect(repository.zoek(relatie.getIdentificatie())).andReturn(relatie);
        expect(repository.zoekOpBsn("1234")).andReturn(null);

        BsnAangevuld bsnAangevuld = new BsnAangevuld();
        bsnAangevuldSender.send(bsnAangevuld);
        expectLastCall();

        AdresAangevuld adresAangevuld = new AdresAangevuld();
        adresAangevuldSender.send(adresAangevuld);
        expectLastCall();

        EmailadresAangevuld emailadresAangevuld = new EmailadresAangevuld();
        emailAdresAangevuldSender.send(emailadresAangevuld);
        expectLastCall();

        repository.opslaan(relatie);
        expectLastCall();

        replayAll();

        service.opslaan(relatie);
    }

    @Test
    public void testOpslaanMetRekeningNummer() throws UnsupportedEncodingException, NoSuchAlgorithmException, NietGevondenException {
        Relatie relatie = new Relatie();
        relatie.getRekeningnummers().add(new RekeningNummer());
        relatie.setBsn("1234");
        relatie.setAdressen(Lists.newArrayList(maakAdres()));
        relatie.setIdentificatie("id");

        expect(repository.zoek(relatie.getIdentificatie())).andReturn(relatie);
        expect(repository.zoekOpBsn("1234")).andReturn(null);

        repository.opslaan(relatie);
        expectLastCall();

        BsnAangevuld bsnAangevuld = new BsnAangevuld();
        bsnAangevuld.setRelatie(relatie.getId());
        bsnAangevuldSender.send(bsnAangevuld);
        expectLastCall();

        EmailadresAangevuld emailadresAangevuld = new EmailadresAangevuld();
        emailadresAangevuld.setRelatie(relatie.getId());
        emailAdresAangevuldSender.send(emailadresAangevuld);
        expectLastCall();

        AdresAangevuld adresAangevuld = new AdresAangevuld();
        adresAangevuld.setRelatie(relatie.getId());
        adresAangevuldSender.send(adresAangevuld);
        expectLastCall();

        replayAll();

        service.opslaan(relatie);
    }

    @Test
    public void testOpslaanMetTelefoonNummer() throws UnsupportedEncodingException, NoSuchAlgorithmException, NietGevondenException {
        Relatie relatie = new Relatie();
        relatie.getTelefoonnummers().add(new Telefoonnummer());
        relatie.setBsn("1234");
        relatie.setAdressen(Lists.newArrayList(maakAdres()));
        relatie.setIdentificatie("id");

        expect(repository.zoek(relatie.getIdentificatie())).andReturn(relatie);
        expect(repository.zoekOpBsn("1234")).andReturn(null);

        BsnAangevuld bsnAangevuld = new BsnAangevuld();
        bsnAangevuldSender.send(bsnAangevuld);
        expectLastCall();

        AdresAangevuld adresAangevuld = new AdresAangevuld();
        adresAangevuldSender.send(adresAangevuld);
        expectLastCall();

        EmailadresAangevuld emailadresAangevuld = new EmailadresAangevuld();
        emailAdresAangevuldSender.send(emailadresAangevuld);
        expectLastCall();

        repository.opslaan(relatie);
        expectLastCall();

        replayAll();

        service.opslaan(relatie);
    }

    @Test
    public void testOpslaanZonderBsn() throws UnsupportedEncodingException, NoSuchAlgorithmException, NietGevondenException {
        Relatie relatie = new Relatie();
        relatie.setId(2L);
        relatie.setAdressen(Lists.newArrayList(maakAdres()));
        relatie.setIdentificatie("id");

        expect(repository.zoek(relatie.getIdentificatie())).andReturn(relatie);
        expect(repository.zoekOpBsn(null)).andReturn(null);

        repository.opslaan(relatie);
        expectLastCall();

        AanmakenTaak taak = new AanmakenTaak();
        taak.setDatumTijdCreatie(new LocalDateTime());
        taak.setRelatie(2L);
        taak.setSoort(SoortTaak.AANVULLEN_BSN);
        aanmakenTaakSender.send(taak);
        expectLastCall();

        AdresAangevuld adresAangevuld = new AdresAangevuld();
        adresAangevuld.setRelatie(relatie.getId());
        adresAangevuldSender.send(adresAangevuld);
        expectLastCall();

        EmailadresAangevuld emailadresAangevuld = new EmailadresAangevuld();
        emailadresAangevuld.setRelatie(relatie.getId());
        emailAdresAangevuldSender.send(emailadresAangevuld);
        expectLastCall();

        replayAll();

        service.opslaan(relatie);

        verifyAll();
    }

    @Test
    public void testOpslaanZonderEmail() throws UnsupportedEncodingException, NoSuchAlgorithmException, NietGevondenException {
        Relatie relatie = new Relatie();
        relatie.setId(2L);
        relatie.setAdressen(Lists.newArrayList(maakAdres()));
        relatie.setBsn("id");

        // expect(repository.zoek(relatie.getIdentificatie())).andReturn(relatie);
        expect(repository.zoekOpBsn("id")).andReturn(null);

        repository.opslaan(relatie);
        expectLastCall();

        AanmakenTaak taak = new AanmakenTaak();
        taak.setDatumTijdCreatie(new LocalDateTime());
        taak.setRelatie(2L);
        taak.setSoort(SoortTaak.AANVULLEN_EMAIL);
        aanmakenTaakSender.send(taak);
        expectLastCall();

        BsnAangevuld bsnAangevuld = new BsnAangevuld();
        bsnAangevuld.setRelatie(relatie.getId());
        bsnAangevuldSender.send(bsnAangevuld);
        expectLastCall();

        AdresAangevuld adresAangevuld = new AdresAangevuld();
        adresAangevuld.setRelatie(relatie.getId());
        adresAangevuldSender.send(adresAangevuld);
        expectLastCall();

        replayAll();

        service.opslaan(relatie);

        verifyAll();
    }

    @Test
    public void testOpslaanZonderAdres() throws UnsupportedEncodingException, NoSuchAlgorithmException, NietGevondenException {
        Relatie relatie = new Relatie();
        relatie.setId(2L);
        relatie.setBsn("bsn");
        relatie.setIdentificatie("id");

        expect(repository.zoek(relatie.getIdentificatie())).andReturn(relatie);
        expect(repository.zoekOpBsn("bsn")).andReturn(null);

        repository.opslaan(relatie);
        expectLastCall();

        AanmakenTaak taak = new AanmakenTaak();
        taak.setDatumTijdCreatie(new LocalDateTime());
        taak.setRelatie(2L);
        taak.setSoort(SoortTaak.AANVULLEN_ADRES);
        aanmakenTaakSender.send(isA(AanmakenTaak.class));
        expectLastCall();

        BsnAangevuld bsnAangevuld = new BsnAangevuld();
        bsnAangevuld.setRelatie(relatie.getId());
        bsnAangevuldSender.send(bsnAangevuld);
        expectLastCall();

        EmailadresAangevuld emailadresAangevuld = new EmailadresAangevuld();
        emailadresAangevuld.setRelatie(relatie.getId());
        emailAdresAangevuldSender.send(emailadresAangevuld);
        expectLastCall();

        replayAll();

        service.opslaan(relatie);

        verifyAll();
    }

    @Test
    public void testOpslaanMetAdresMaarIncompleet() throws UnsupportedEncodingException, NoSuchAlgorithmException, NietGevondenException {
        Relatie relatie = new Relatie();
        relatie.setId(2L);
        relatie.setBsn("bsn");
        relatie.setAdressen(Lists.newArrayList(maakAdres()));
        relatie.getAdres().setStraat(null);
        relatie.setIdentificatie("id");

        expect(repository.zoek(relatie.getIdentificatie())).andReturn(relatie);
        expect(repository.zoekOpBsn("bsn")).andReturn(null);

        repository.opslaan(relatie);
        expectLastCall();

        AanmakenTaak taak = new AanmakenTaak();
        taak.setDatumTijdCreatie(new LocalDateTime());
        taak.setRelatie(2L);
        taak.setSoort(SoortTaak.AANVULLEN_ADRES);
        aanmakenTaakSender.send(taak);
        expectLastCall();

        BsnAangevuld bsnAangevuld = new BsnAangevuld();
        bsnAangevuld.setRelatie(relatie.getId());
        bsnAangevuldSender.send(bsnAangevuld);
        expectLastCall();

        EmailadresAangevuld emailadresAangevuld = new EmailadresAangevuld();
        emailadresAangevuld.setRelatie(relatie.getId());
        emailAdresAangevuldSender.send(emailadresAangevuld);
        expectLastCall();

        replayAll();

        service.opslaan(relatie);

        verifyAll();
    }

    @Test
    public void testOpslaanMedewerker() throws UnsupportedEncodingException, NoSuchAlgorithmException, NietGevondenException {
        Medewerker medewerker = new Medewerker();
        medewerker.setIdentificatie("identificatie");

        expect(repository.zoek("identificatie")).andReturn(medewerker);

        repository.opslaan(medewerker);
        expectLastCall();

        replayAll();

        service.opslaan(medewerker);
    }

    @Test
    public void testVerwijder() {
        Relatie relatie = new Relatie();

        expect(repository.lees(1L)).andReturn(relatie);
        repository.verwijder(relatie);
        expectLastCall();

        replayAll();

        service.verwijder(1L);
    }

    @Test
    public void testZoek() throws NietGevondenException {
        Medewerker medewerker = new Medewerker();

        expect(repository.zoek("e")).andReturn(medewerker);

        replayAll();

        assertEquals(medewerker, service.zoek("e"));
    }

    @Test
    public void testZoekOpSessieEnIpAdres() throws NietGevondenException {
        String sessie = "sessie";
        String ipadres = "ipadres";

        Medewerker medewerker = new Medewerker();

        expect(repository.zoekOpSessieEnIpadres(sessie, ipadres)).andReturn(medewerker);

        replayAll();

        assertEquals(medewerker, service.zoekOpSessieEnIpAdres(sessie, ipadres));
    }

    @Test
    public void testZoekSessieOpOpSessieEnIpadres() {
        Sessie sessie1 = new Sessie();
        Sessie sessie2 = new Sessie();
        Sessie sessie3 = new Sessie();

        sessie1.setSessie("sessie1");
        sessie1.setIpadres("ipadres1");
        sessie2.setSessie("sessie2");
        sessie2.setIpadres("ipadres2");
        sessie3.setSessie("sessie3");
        sessie3.setIpadres("ipadres3");

        Relatie relatie = new Relatie();
        relatie.getSessies().add(sessie1);
        relatie.getSessies().add(sessie2);
        relatie.getSessies().add(sessie3);

        replayAll();

        assertEquals(sessie1, service.zoekSessieOp("sessie1", "ipadres1", relatie.getSessies()));
        assertEquals(sessie2, service.zoekSessieOp("sessie2", "ipadres2", relatie.getSessies()));
        assertEquals(sessie3, service.zoekSessieOp("sessie3", "ipadres3", relatie.getSessies()));
        assertNull(service.zoekSessieOp("sessie", "ipadres1", relatie.getSessies()));
        assertNull(service.zoekSessieOp("sessie1", "ipadres", relatie.getSessies()));
    }

    @Test
    public void testZoekSessieOpCookie() {
        Sessie sessie1 = new Sessie();
        Sessie sessie2 = new Sessie();
        Sessie sessie3 = new Sessie();

        sessie1.setCookieCode("cookieCode1");
        sessie2.setCookieCode("cookieCode2");
        sessie3.setCookieCode("cookieCode3");

        Relatie relatie = new Relatie();
        relatie.getSessies().add(sessie1);
        relatie.getSessies().add(sessie2);
        relatie.getSessies().add(sessie3);

        replayAll();

        assertEquals(sessie1, service.zoekSessieOp("cookieCode1", relatie.getSessies()));
        assertEquals(sessie2, service.zoekSessieOp("cookieCode2", relatie.getSessies()));
        assertEquals(sessie3, service.zoekSessieOp("cookieCode3", relatie.getSessies()));
        assertNull(service.zoekSessieOp("cookieCode", relatie.getSessies()));
    }

    @Test
    public void testZoekOpCookieCode() throws NietGevondenException {
        Medewerker medewerker = new Medewerker();
        String cookieCode = "cookieCode";

        expect(repository.zoekOpCookieCode(cookieCode)).andReturn(medewerker);

        replayAll();

        assertEquals(medewerker, service.zoekOpCookieCode(cookieCode));
    }

    @Test
    public void testZoekOpCookieCodeNietGevondenException() throws NietGevondenException {
        String cookieCode = "cookieCode";

        expect(repository.zoekOpCookieCode(cookieCode)).andThrow(new NietGevondenException("wie"));

        replayAll();

        service.zoekOpCookieCode(cookieCode);
    }

    @Test
    public void refresh() {
        Sessie sessie = new Sessie();

        repository.refresh(sessie);
        expectLastCall();

        replayAll();

        service.refresh(sessie);
    }

    @Test
    public void opslaan() {
        Sessie sessie = new Sessie();

        repository.opslaan(sessie);
        expectLastCall();

        replayAll();

        service.opslaan(sessie);
    }

    @Test
    public void verwijder() {
        Sessie sessie = new Sessie();

        repository.verwijder(sessie);
        expectLastCall();

        replayAll();

        service.verwijder(sessie);
    }

    @Test
    public void verwijderVerlopenSessies() {
        Long id = 46L;
        Medewerker medewerker = createMock(Medewerker.class);

        expect(medewerker.getId()).andReturn(id);

        expect(repository.lees(id)).andReturn(medewerker);

        Sessie sessie1 = createMock(Sessie.class);
        Sessie sessie2 = createMock(Sessie.class);
        Sessie sessie3 = createMock(Sessie.class);

        Set<Sessie> sessies = new HashSet<>();
        sessies.add(sessie1);
        sessies.add(sessie2);
        sessies.add(sessie3);

        expect(sessie1.getDatumLaatstGebruikt()).andReturn(new LocalDate().minusDays(2));
        expect(sessie2.getDatumLaatstGebruikt()).andReturn(new LocalDate().minusDays(3));
        expect(sessie3.getDatumLaatstGebruikt()).andReturn(new LocalDate().minusDays(4));

        expect(medewerker.getSessies()).andReturn(sessies).times(2);

        repository.opslaan(medewerker);
        expectLastCall();

        replayAll();

        service.verwijderVerlopenSessies(medewerker);
    }

    @Test
    public void testZoekOpNaamAdresOfPolisNummer() throws UnsupportedEncodingException, NoSuchAlgorithmException {
        String zoekterm = "a";
        Kantoor kantoor = new Kantoor();

        List<Gebruiker> relatiesZoekOpNaam = new ArrayList<Gebruiker>();
        Relatie relatieZoekOpNaam = new Relatie();
        relatieZoekOpNaam.setId(2L);
        relatieZoekOpNaam.setAchternaam("relatieZoekOpNaam");
        relatieZoekOpNaam.setIdentificatie("relatieZoekOpNaamId");
        relatiesZoekOpNaam.add(relatieZoekOpNaam);

        List<Relatie> relatiesZoekOpAdres = new ArrayList<Relatie>();
        Relatie relatieZoekOpAdres = new Relatie();
        relatieZoekOpAdres.setId(2L);
        relatieZoekOpAdres.setAchternaam("relatieZoekOpAdres");
        relatieZoekOpAdres.setIdentificatie("relatieZoekOpAdresId");
        relatiesZoekOpAdres.add(relatieZoekOpAdres);

        List<Relatie> relatiesZoekOpBedrijfsnaam = new ArrayList<>();
        Relatie relatieZoekOpBedrijfsnaam = new Relatie();
        Bedrijf bedrijf = new Bedrijf();
        bedrijf.setNaam("naamBedrijf");
        relatiesZoekOpBedrijfsnaam.add(relatieZoekOpBedrijfsnaam);

        Polis polis = new AutoVerzekering();
        Relatie relatiePolis = new Relatie();
        polis.setRelatie(relatiePolis);

        expect(repository.zoekOpNaam(zoekterm)).andReturn(relatiesZoekOpNaam);
        expect(repository.zoekOpAdres(zoekterm)).andReturn(relatiesZoekOpAdres);
        expect(kantoorRepository.lees(1L)).andReturn(kantoor);
        expect(polisRepository.zoekOpPolisNummer(zoekterm, kantoor)).andReturn(polis);
        expect(repository.zoekRelatiesOpBedrijfsnaam("a")).andReturn(relatiesZoekOpBedrijfsnaam);

        replayAll();

        List<Relatie> relatiesVerwacht = new ArrayList<>();
        relatiesVerwacht.add(relatieZoekOpNaam);
        relatiesVerwacht.add(relatieZoekOpAdres);
        relatiesVerwacht.add(relatiePolis);

        assertEquals(relatiesVerwacht.size(), service.zoekOpNaamAdresOfPolisNummer(zoekterm).size());
    }


    @Test
    public void testZoekOpNaamAdresOfPolisNummerMetTelefoonnummer() throws UnsupportedEncodingException, NoSuchAlgorithmException {
        String zoekterm = "06-12 345 678";
        Kantoor kantoor = new Kantoor();

        Relatie relatieTelefoonnummer = new Relatie();
        Telefoonnummer telefoonnummer = new Telefoonnummer();
        telefoonnummer.setTelefoonnummer("031");
        relatieTelefoonnummer.getTelefoonnummers().add(telefoonnummer);
        telefoonnummer.setRelatie(relatieTelefoonnummer);
        List<Relatie> relatiesTelefoonnummer = new ArrayList<>();
        relatiesTelefoonnummer.add(relatieTelefoonnummer);

        expect(repository.zoekOpNaam(zoekterm)).andReturn(new ArrayList<Gebruiker>());
        expect(repository.zoekOpAdres(zoekterm)).andReturn(new ArrayList<Relatie>());
        expect(kantoorRepository.lees(1L)).andReturn(kantoor);
        expect(polisRepository.zoekOpPolisNummer(zoekterm, kantoor)).andReturn(null);
        expect(repository.zoekRelatiesOpTelefoonnummer("0612345678")).andReturn(relatiesTelefoonnummer);
        expect(repository.zoekRelatiesOpBedrijfsnaam(zoekterm)).andReturn(new ArrayList<Relatie>());

        replayAll();

        List<Relatie> relatiesVerwacht = new ArrayList<>();
        relatiesVerwacht.add(relatieTelefoonnummer);

        assertEquals(relatiesVerwacht.size(), service.zoekOpNaamAdresOfPolisNummer(zoekterm).size());
    }

    @Test
    public void testZoekOpNaamAdresOfPolisNummerZonderPolis() throws UnsupportedEncodingException, NoSuchAlgorithmException {
        String zoekterm = "a";
        Kantoor kantoor = new Kantoor();

        List<Gebruiker> relatiesZoekOpNaam = new ArrayList<Gebruiker>();
        Relatie relatieZoekOpNaam = new Relatie();
        relatieZoekOpNaam.setAchternaam("relatieZoekOpNaam");
        relatieZoekOpNaam.setId(2L);
        relatieZoekOpNaam.setIdentificatie("relatieZoekOpNaamId");
        relatiesZoekOpNaam.add(relatieZoekOpNaam);

        List<Relatie> relatiesZoekOpAdres = new ArrayList<Relatie>();
        Relatie relatieZoekOpAdres = new Relatie();
        relatieZoekOpAdres.setAchternaam("relatieZoekOpAdres");
        relatieZoekOpAdres.setId(23L);
        relatieZoekOpAdres.setIdentificatie("relatieZoekOpAdresId");
        relatiesZoekOpAdres.add(relatieZoekOpAdres);

        List<Relatie> relatiesZoekOpBedrijfsnaam = new ArrayList<>();
        Relatie relatieZoekOpBedrijfsnaam = new Relatie();
        Bedrijf bedrijf = new Bedrijf();
        bedrijf.setNaam("naamBedrijf");
        relatiesZoekOpBedrijfsnaam.add(relatieZoekOpBedrijfsnaam);

        expect(repository.zoekOpNaam(zoekterm)).andReturn(relatiesZoekOpNaam);
        expect(repository.zoekOpAdres(zoekterm)).andReturn(relatiesZoekOpAdres);
        expect(kantoorRepository.lees(1L)).andReturn(kantoor);
        expect(polisRepository.zoekOpPolisNummer(zoekterm, kantoor)).andReturn(null);
        expect(repository.zoekRelatiesOpBedrijfsnaam("a")).andReturn(relatiesZoekOpBedrijfsnaam);

        replayAll();

        List<Relatie> relatiesVerwacht = new ArrayList<>();
        relatiesVerwacht.add(relatieZoekOpNaam);
        relatiesVerwacht.add(relatieZoekOpAdres);
        relatiesVerwacht.add(relatieZoekOpBedrijfsnaam);

        assertEquals(relatiesVerwacht.size(), service.zoekOpNaamAdresOfPolisNummer(zoekterm).size());
    }

    private Adres maakAdres() {
        Adres adres = new Adres();
        adres.setHuisnummer(41L);
        adres.setPlaats("Zwartemeer");
        adres.setPostcode("7894AB");
        adres.setStraat("Eemslandweg");

        return adres;
    }
}
