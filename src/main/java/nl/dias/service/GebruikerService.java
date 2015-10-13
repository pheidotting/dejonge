package nl.dias.service;

import com.sun.jersey.api.core.InjectParam;
import nl.dias.domein.*;
import nl.dias.domein.polis.Polis;
import nl.dias.messaging.sender.AanmakenTaakSender;
import nl.dias.messaging.sender.AdresAangevuldSender;
import nl.dias.messaging.sender.BsnAangevuldSender;
import nl.dias.messaging.sender.EmailAdresAangevuldSender;
import nl.dias.repository.*;
import nl.lakedigital.as.messaging.AanmakenTaak;
import nl.lakedigital.as.messaging.AanmakenTaak.SoortTaak;
import nl.lakedigital.as.messaging.AdresAangevuld;
import nl.lakedigital.as.messaging.BsnAangevuld;
import nl.lakedigital.as.messaging.EmailadresAangevuld;
import nl.lakedigital.loginsystem.exception.NietGevondenException;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.joda.time.format.DateTimeFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.inject.Named;
import javax.persistence.NoResultException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.apache.commons.lang3.StringUtils.isBlank;

@Named
public class GebruikerService {

    private final static Logger LOGGER = LoggerFactory.getLogger(GebruikerService.class);

    @InjectParam
    private GebruikerRepository gebruikerRepository;
    @InjectParam
    private DIASGebruikerRepository diasGebruikerRepository;
    @InjectParam
    private DIASAdresRepository diasAdresRepository;
    @InjectParam
    private DIASBedrijfRepository diasBedrijfRepository;
    @InjectParam
    private DIASTelefoonNummerRepository diasTelefoonNummerRepository;
    @InjectParam
    private PolisRepository polisRepository;
    @InjectParam
    private KantoorRepository kantoorRepository;
    @InjectParam
    private HypotheekRepository hypotheekRepository;
    @InjectParam
    private BedrijfRepository bedrijfService;

    private AanmakenTaakSender aanmakenTaakSender;
    private AdresAangevuldSender adresAangevuldSender;
    private EmailAdresAangevuldSender emailAdresAangevuldSender;
    private BsnAangevuldSender bsnAangevuldSender;

    public void opslaanBijlage(String relatieId, Bijlage bijlage){
        LOGGER.info("Opslaan bijlage met id {}, bij Relatie met id {}", bijlage.getId(),relatieId);

        Relatie relatie=(Relatie)gebruikerRepository.lees(Long.valueOf(relatieId));

        relatie.getBijlages().add(bijlage);
        bijlage.setRelatie(relatie);
        bijlage.setSoortBijlage(SoortBijlage.RELATIE);

        LOGGER.debug(ReflectionToStringBuilder.toString(bijlage));

        gebruikerRepository.opslaan(relatie);
    }

    @Deprecated
    public void converteren() {
        String patternDatum = "dd-MM-yyyy";

        Kantoor kantoor = kantoorRepository.lees(1L);

        for (DIASGebruiker diasGebruiker : diasGebruikerRepository.alles()) {
            Relatie relatie = new Relatie();
            relatie.setAchternaam(diasGebruiker.getAchternaam());
            if (!"".equals(diasGebruiker.getBsn().trim())) {
                relatie.setBsn(diasGebruiker.getBsn().replace(".", ""));
            }
            if ("gehuwd".equalsIgnoreCase(diasGebruiker.getBurgerlijkeStaat())) {
                relatie.setBurgerlijkeStaat(BurgerlijkeStaat.G);
            } else {
                relatie.setBurgerlijkeStaat(BurgerlijkeStaat.O);
            }
            if (diasGebruiker.getGeboorteDatum() != null && !diasGebruiker.getGeboorteDatum().equals("")) {
                relatie.setGeboorteDatum(LocalDate.parse(diasGebruiker.getGeboorteDatum(), DateTimeFormat.forPattern(patternDatum)));
            }
            if (!"".equals(diasGebruiker.getGeslacht())) {
                relatie.setGeslacht(Geslacht.valueOf(diasGebruiker.getGeslacht().substring(0, 1).toUpperCase()));
            }
            if (diasGebruiker.getDatumOverlijden() != null && !"".equals(diasGebruiker.getDatumOverlijden())) {
                relatie.setOverlijdensdatum(LocalDate.parse(diasGebruiker.getDatumOverlijden(), DateTimeFormat.forPattern(patternDatum)));
            }
            relatie.setRoepnaam(diasGebruiker.getRoepnaam());
            relatie.setTussenvoegsel(diasGebruiker.getVoorvoegsels());
            if (!"".equals(diasGebruiker.getVoornamen())) {
                relatie.setVoornaam(diasGebruiker.getVoornamen());
            } else {
                relatie.setVoornaam(diasGebruiker.getVoorletters());
            }
            relatie.setKantoor(kantoor);

            List<DIASAdres> adressen = diasAdresRepository.zoekOpRegistratieNummer(diasGebruiker.getNummer());
            if (adressen != null && !adressen.isEmpty()) {
                DIASAdres diasAdres = adressen.get(0);

                relatie.getAdres().setHuisnummer(Long.valueOf(diasAdres.getHuisNummer()));
                relatie.getAdres().setPlaats(diasAdres.getPlaats());
                relatie.getAdres().setPostcode(diasAdres.getPostcode());
                relatie.getAdres().setStraat(diasAdres.getPostcode());
                relatie.getAdres().setToevoeging(diasAdres.getToevoeging());
            }

            List<DIASTelefoonNummer> diasTelefoonNummers = diasTelefoonNummerRepository.zoekOpRegistratieNummer(diasGebruiker.getNummer());
            if (diasTelefoonNummers != null) {
                for (DIASTelefoonNummer diasTelefoonNummer : diasTelefoonNummers) {
                    String telNr = diasTelefoonNummer.getTelefoonummer().replace("(", "").replace(")", "");
                    if (!"".equals(telNr.trim()) && telNr.length() <= 10) {
                        Telefoonnummer telefoonnummer = new Telefoonnummer();

                        telefoonnummer.setRelatie(relatie);
                        telefoonnummer.setTelefoonnummer(diasTelefoonNummer.getTelefoonummer().replace("(", "").replace(")", ""));
                        if ("priv".equalsIgnoreCase(diasTelefoonNummer.getType())) {
                            telefoonnummer.setSoort(TelefoonnummerSoort.VAST);
                        }
                        if ("mobiel".equalsIgnoreCase(diasTelefoonNummer.getType())) {
                            telefoonnummer.setSoort(TelefoonnummerSoort.MOBIEL);
                        } else if ("zakelijk".equalsIgnoreCase(diasTelefoonNummer.getType())) {
                            telefoonnummer.setSoort(TelefoonnummerSoort.WERK);
                        } else {
                            telefoonnummer.setSoort(TelefoonnummerSoort.VAST);
                        }

                        relatie.getTelefoonnummers().add(telefoonnummer);
                    }
                }
            }

            List<DIASBedrijf> diasBedrijven = diasBedrijfRepository.zoekOpRegistratieNummer(diasGebruiker.getNummer());
            if (diasBedrijven != null) {
                for (DIASBedrijf diasBedrijf : diasBedrijven) {
                    Bedrijf bedrijf = new Bedrijf();
                    bedrijf.setRelatie(relatie);
                    bedrijf.setKvk(diasBedrijf.getKvk());
                    bedrijf.setNaam(diasBedrijf.getNaam());

                    relatie.getBedrijven().add(bedrijf);
                }
            }

            Relatie aanwezigeRelatie = null;
            try {
                aanwezigeRelatie = gebruikerRepository.zoekOpBsn(relatie.getBsn());
            } catch (Exception e) {
            }

            if (relatie.getBsn() != null && aanwezigeRelatie != null) {
                relatie.setBsn(null);
            }

            boolean opgeslagen = false;
            try {
                opslaan(relatie);
                opgeslagen = true;
            } catch (Exception e) {
            }

            if (opgeslagen) {
                diasGebruikerRepository.verwijder(diasGebruiker);
            }
        }
    }

    public Gebruiker lees(Long id) {
        return gebruikerRepository.lees(id);
    }

    public Relatie leesRelatie(Long id) {
        return (Relatie) this.lees(id);
    }

    public List<Relatie> alleRelaties(Kantoor kantoor) {
        return gebruikerRepository.alleRelaties(kantoor);
    }

    public void opslaan(Gebruiker gebruiker) {
        LOGGER.debug("GO " + gebruiker);
        Gebruiker gebruikerAanwezig = null;
        LOGGER.info("gebruiker " + gebruiker.getIdentificatie() + " opzoeken");
        if (gebruiker.getIdentificatie() != null && !"".equals(gebruiker.getIdentificatie())) {
            try {
                gebruikerAanwezig = gebruikerRepository.zoek(gebruiker.getIdentificatie());
            } catch (NietGevondenException e) {
                // niets aan de hand;
                LOGGER.info("gebruiker " + gebruiker.getIdentificatie() + " niet gevonden");
            }
        }

        if (gebruikerAanwezig != null && !gebruikerAanwezig.getId().equals(gebruiker.getId())) {
            LOGGER.debug("Gebruiker komt al voor");
            LOGGER.debug("Gevonden user id : '" + gebruikerAanwezig.getId() + "', op te slaan id : '" + gebruiker.getId() + "'");
            gebruikerAanwezig = null;
            // throw new
            // IllegalArgumentException("E-mailadres komt al voor bij een andere gebruiker");
        }

        if (gebruikerAanwezig != null && gebruikerAanwezig instanceof Relatie) {
            LOGGER.debug("Adressen wissen bij relatie");
            //            ((Relatie)gebruikerAanwezig).setAdressen(new ArrayList<Adres>());
            //            gebruikerRepository.opslaan(gebruikerAanwezig);
            gebruikerRepository.verwijderAdressenBijRelatie((Relatie) gebruiker);
        }

        // BSN mag ook niet al voorkomen, daarom deze ook eerst opzoeken
        if (gebruiker instanceof Relatie) {
            if (!"".equals(((Relatie) gebruiker).getBsn())) {
                try {
                    gebruikerAanwezig = gebruikerRepository.zoekOpBsn(((Relatie) gebruiker).getBsn());
                } catch (NoResultException e) {
                    // niets aan de hand;
                    LOGGER.info("gebruiker met bsn " + ((Relatie) gebruiker).getBsn() + " niet gevonden");
                }

                if (gebruikerAanwezig != null && gebruikerAanwezig.getId() != gebruiker.getId()) {
                    gebruikerAanwezig = null;
                    // throw new
                    // IllegalArgumentException("Burgerservicenummer komt al voor bij een andere gebruiker");
                }
            }
        }
        // Even checken of over de connectie met de Relatie is ingevuld
        if (gebruiker instanceof Relatie) {
            for (Telefoonnummer telefoonnummer : ((Relatie) gebruiker).getTelefoonnummers()) {
                telefoonnummer.setRelatie((Relatie) gebruiker);
            }
            for (RekeningNummer rekeningNummer : ((Relatie) gebruiker).getRekeningnummers()) {
                rekeningNummer.setRelatie((Relatie) gebruiker);
            }
        }

        gebruikerRepository.opslaan(gebruiker);

        // Als Gebruiker een Relatie is en BSN leeg is, moet er een taak worden
        // aangemaakt
        if (gebruiker instanceof Relatie) {
            ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
            if (aanmakenTaakSender == null) {
                aanmakenTaakSender = (AanmakenTaakSender) context.getBean("aanmakenTaakSender");
            }
            if (adresAangevuldSender == null) {
                adresAangevuldSender = (AdresAangevuldSender) context.getBean("adresAangevuldSender");
            }
            if (emailAdresAangevuldSender == null) {
                emailAdresAangevuldSender = (EmailAdresAangevuldSender) context.getBean("emailAdresAangevuldSender");
            }
            if (bsnAangevuldSender == null) {
                bsnAangevuldSender = (BsnAangevuldSender) context.getBean("bsnAangevuldSender");
            }

            Relatie relatie = (Relatie) gebruiker;
            if (isBlank(relatie.getBsn())) {
                LOGGER.info("BSN is leeg, Taak aanmaken");

                AanmakenTaak taak = new AanmakenTaak();
                taak.setDatumTijdCreatie(LocalDateTime.now());
                taak.setRelatie(relatie.getId());
                taak.setSoort(SoortTaak.AANVULLEN_BSN);

                aanmakenTaakSender.send(taak);
            } else {
                LOGGER.info("BSN gevuld, bericht versturen");

                BsnAangevuld bsnAangevuld = new BsnAangevuld();
                bsnAangevuld.setRelatie(relatie.getId());

                bsnAangevuldSender.send(bsnAangevuld);
            }

            if (relatie.getAdres() == null || !relatie.getAdres().isCompleet()) {
                LOGGER.info("Adres is leeg of niet volledig, Taak aanmaken");

                AanmakenTaak taak = new AanmakenTaak();
                taak.setDatumTijdCreatie(LocalDateTime.now());
                taak.setRelatie(relatie.getId());
                taak.setSoort(SoortTaak.AANVULLEN_ADRES);

                aanmakenTaakSender.send(taak);
            } else if (relatie.getAdres() != null && relatie.getAdres().isCompleet()) {
                LOGGER.info("Adres gevuld, bericht versturen");

                AdresAangevuld adresAangevuld = new AdresAangevuld();
                adresAangevuld.setRelatie(relatie.getId());

                adresAangevuldSender.send(adresAangevuld);
            }

            if (isBlank(relatie.getIdentificatie())) {
                LOGGER.info("E-mailadres is leeg, Taak aanmaken");

                AanmakenTaak taak = new AanmakenTaak();
                taak.setDatumTijdCreatie(LocalDateTime.now());
                taak.setRelatie(relatie.getId());
                taak.setSoort(SoortTaak.AANVULLEN_EMAIL);

                aanmakenTaakSender.send(taak);
            } else {
                LOGGER.info("E-mailadres gevuld, bericht versturen");

                EmailadresAangevuld emailadresAangevuld = new EmailadresAangevuld();
                emailadresAangevuld.setRelatie(relatie.getId());

                emailAdresAangevuldSender.send(emailadresAangevuld);
            }
        }
    }

    public void verwijder(Long id) {
        LOGGER.info("Verwijderen gebruiker met id " + id);

        // Eerst ophalen
        Gebruiker gebruiker = gebruikerRepository.lees(id);

        LOGGER.debug("Opgehaalde gebruiker : ");
        LOGGER.debug(ReflectionToStringBuilder.toString(gebruiker));

        if (gebruiker instanceof Relatie) {
            Relatie relatie = (Relatie) gebruiker;
            for (Polis polis : relatie.getPolissen()) {
                LOGGER.debug("Verwijder Polis :");
                LOGGER.debug(ReflectionToStringBuilder.toString(polis));
                polisRepository.verwijder(polis);
                relatie.getPolissen().remove(polis);
            }
            for (Hypotheek hypotheek : relatie.getHypotheken()) {
                LOGGER.debug("Verwijder Hypotheek :");
                LOGGER.debug(ReflectionToStringBuilder.toString(hypotheek));
                hypotheekRepository.verwijder(hypotheek);
                relatie.getHypotheken().remove(hypotheek);
            }
            for (Bedrijf bedrijf : relatie.getBedrijven()) {
                LOGGER.debug("Verwijder Bedrijf :");
                LOGGER.debug(ReflectionToStringBuilder.toString(bedrijf));
                bedrijfService.verwijder(bedrijf);
                relatie.getBedrijven().remove(bedrijf);
            }
        }
        // en dan verwijderen
        gebruikerRepository.verwijder(gebruiker);
    }

    public Gebruiker zoekOpSessieEnIpAdres(String sessie, String ipadres) throws NietGevondenException {
        return gebruikerRepository.zoekOpSessieEnIpadres(sessie, ipadres);
    }

    public Sessie zoekSessieOp(String sessieId, String ipadres, Set<Sessie> sessies) {
        for (Sessie sessie : sessies) {
            if (sessie.getSessie().equals(sessieId) && sessie.getIpadres().equals(ipadres)) {
                return sessie;
            }
        }

        return null;
    }

    public Sessie zoekSessieOp(String cookieCode, Set<Sessie> sessies) {
        for (Sessie sessie : sessies) {
            if (sessie.getCookieCode() != null && sessie.getCookieCode().equals(cookieCode)) {
                return sessie;
            }
        }

        return null;
    }

    public Gebruiker zoekOpCookieCode(String cookieCode) {
        try {
            return gebruikerRepository.zoekOpCookieCode(cookieCode);
        } catch (NietGevondenException e) {
            LOGGER.info("Niets gevonden", e);
            return null;
        }
    }

    public Gebruiker zoek(String emailadres) throws NietGevondenException {
        return gebruikerRepository.zoek(emailadres);
    }

    public void refresh(Sessie sessie) {
        gebruikerRepository.refresh(sessie);
    }

    public void opslaan(Sessie sessie) {
        gebruikerRepository.opslaan(sessie);
    }

    public void verwijder(Sessie sessie) {
        gebruikerRepository.verwijder(sessie);
    }

    public void verwijderVerlopenSessies(Gebruiker gebr) {
        Gebruiker gebruiker = gebruikerRepository.lees(gebr.getId());

        List<Sessie> teVerwijderenSessies = new ArrayList<>();

        for (Sessie sessie : gebruiker.getSessies()) {
            if (sessie.getDatumLaatstGebruikt().isBefore(LocalDate.now().minusDays(3))) {
                teVerwijderenSessies.add(sessie);
            }
        }

        if (!teVerwijderenSessies.isEmpty()) {
            for (Sessie sessie : teVerwijderenSessies) {
                gebruiker.getSessies().remove(sessie);
            }

            gebruikerRepository.opslaan(gebruiker);
        }
    }

    public List<Relatie> zoekOpNaamAdresOfPolisNummer(String zoekTerm) {
        LOGGER.debug("zoekOpNaamAdresOfPolisNummer met zoekTerm " + zoekTerm);
        Set<Relatie> relaties = new HashSet<Relatie>();
        for (Gebruiker g : gebruikerRepository.zoekOpNaam(zoekTerm)) {
            if (g instanceof Relatie) {
                relaties.add((Relatie) g);
            }
        }
        LOGGER.debug("Gevonden " + relaties.size() + " Relaties");
        for (Gebruiker g : gebruikerRepository.zoekOpAdres(zoekTerm)) {
            relaties.add((Relatie) g);
        }
        LOGGER.debug("Zoeken op telefoonnummer");
        String zoekTermNumeriek = zoekTerm.replace(" ", "").replace("-", "");
        try {
            Long.valueOf(zoekTermNumeriek);
        } catch (NumberFormatException nfe) {
            zoekTermNumeriek = null;
            LOGGER.trace("",nfe);
        }
        if (zoekTermNumeriek != null) {
            for (Gebruiker g : gebruikerRepository.zoekRelatiesOpTelefoonnummer(zoekTermNumeriek)) {
                relaties.add((Relatie) g);
            }
        }
        LOGGER.debug("Zoeken op bedrijfnsaam");
        for (Gebruiker g : gebruikerRepository.zoekRelatiesOpBedrijfsnaam(zoekTerm)) {
            relaties.add((Relatie) g);
        }

        LOGGER.debug("Gevonden " + relaties.size() + " Relaties");
        Polis polis = null;
        try {
            polis = polisRepository.zoekOpPolisNummer(zoekTerm, kantoorRepository.lees(1L));
        } catch (NoResultException e) {
            LOGGER.debug("Niks gevonden ", e);
        }
        if (polis != null) {
            relaties.add(polis.getRelatie());
        }
        LOGGER.debug("Gevonden " + relaties.size() + " Relaties");

        List<Relatie> ret = new ArrayList<>();
        for (Relatie r : relaties) {
            ret.add(r);
        }

        return ret;
    }

    public void setGebruikerRepository(GebruikerRepository gebruikerRepository) {
        this.gebruikerRepository = gebruikerRepository;
    }

    public void setAanmakenTaakSender(AanmakenTaakSender aanmakenTaakSender) {
        this.aanmakenTaakSender = aanmakenTaakSender;
    }

    public void setAdresAangevuldSender(AdresAangevuldSender adresAangevuldSender) {
        this.adresAangevuldSender = adresAangevuldSender;
    }

    public void setEmailAdresAangevuldSender(EmailAdresAangevuldSender emailAdresAangevuldSender) {
        this.emailAdresAangevuldSender = emailAdresAangevuldSender;
    }

    public void setBsnAangevuldSender(BsnAangevuldSender bsnAangevuldSender) {
        this.bsnAangevuldSender = bsnAangevuldSender;
    }

    public void setPolisRepository(PolisRepository polisRepository) {
        this.polisRepository = polisRepository;
    }

    public void setKantoorRepository(KantoorRepository kantoorRepository) {
        this.kantoorRepository = kantoorRepository;
    }

}
