package nl.dias.service;

import nl.dias.domein.*;
import nl.dias.domein.polis.Polis;
import nl.dias.domein.predicates.SessieOpCookiePredicate;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import javax.persistence.NoResultException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.google.common.collect.Iterables.filter;
import static com.google.common.collect.Iterables.getFirst;
import static org.apache.commons.lang3.StringUtils.isBlank;

@Service
public class GebruikerService {

    private final static Logger LOGGER = LoggerFactory.getLogger(GebruikerService.class);

    @Inject
    private GebruikerRepository gebruikerRepository;
    @Inject
    private PolisRepository polisRepository;
    @Inject
    private KantoorRepository kantoorRepository;
    @Inject
    private HypotheekRepository hypotheekRepository;
    @Inject
    private BedrijfRepository bedrijfService;
    @Inject
    private AanmakenTaakSender aanmakenTaakSender;
    @Inject
    private AdresAangevuldSender adresAangevuldSender;
    @Inject
    private EmailAdresAangevuldSender emailAdresAangevuldSender;
    @Inject
    private BsnAangevuldSender bsnAangevuldSender;

    public void opslaanAdresBijRelatie(Adres adres, Long relatieId) {
        Relatie relatie = (Relatie) gebruikerRepository.lees(relatieId);

        adres.setRelatie(relatie);
        relatie.getAdressen().add(adres);
        gebruikerRepository.opslaan(relatie);
    }

    public void koppelenOnderlingeRelatie(Long relatieId, Long relatieMetId, String soortRelatie) {
        LOGGER.info("koppelenOnderlingeRelatie({}, {}, {})", relatieId, relatieMetId, soortRelatie);

        Relatie relatie = (Relatie) gebruikerRepository.lees(relatieId);
        Relatie relatieMet = (Relatie) gebruikerRepository.lees(relatieMetId);

        OnderlingeRelatieSoort onderlingeRelatieSoort = OnderlingeRelatieSoort.valueOf(soortRelatie);
        OnderlingeRelatieSoort onderlingeRelatieSoortTegengesteld = OnderlingeRelatieSoort.getTegenGesteld(onderlingeRelatieSoort);

        OnderlingeRelatie onderlingeRelatie = new OnderlingeRelatie(relatie, relatieMet, false, onderlingeRelatieSoort);
        OnderlingeRelatie onderlingeRelatieTegengesteld = new OnderlingeRelatie(relatieMet, relatie, false, onderlingeRelatieSoortTegengesteld);

        relatie.getOnderlingeRelaties().add(onderlingeRelatie);
        relatieMet.getOnderlingeRelaties().add(onderlingeRelatieTegengesteld);

        gebruikerRepository.opslaan(relatie);
        gebruikerRepository.opslaan(relatieMet);

    }

    public void opslaanBijlage(String relatieId, Bijlage bijlage) {
        LOGGER.info("Opslaan bijlage met id {}, bij Relatie met id {}", bijlage.getId(), relatieId);

        Relatie relatie = (Relatie) gebruikerRepository.lees(Long.valueOf(relatieId));

        relatie.getBijlages().add(bijlage);
        bijlage.setRelatie(relatie);
        bijlage.setSoortBijlage(SoortBijlage.RELATIE);

        gebruikerRepository.opslaan(relatie);

        LOGGER.debug(ReflectionToStringBuilder.toString(bijlage));
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
        LOGGER.debug("Opslaan {}", ReflectionToStringBuilder.toString(gebruiker));
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

            if (gebruikerAanwezig != null) {
                ((Relatie) gebruiker).setBijlages(((Relatie) gebruikerAanwezig).getBijlages());
            }
        }

        gebruikerRepository.opslaan(gebruiker);

        // Als Gebruiker een Relatie is en BSN leeg is, moet er een taak worden
        // aangemaakt
        if (gebruiker instanceof Relatie) {

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
        return getFirst(filter(sessies, new SessieOpCookiePredicate(cookieCode)), null);
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
            LOGGER.trace("", nfe);
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
            LOGGER.trace("Niks gevonden ", e);
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
