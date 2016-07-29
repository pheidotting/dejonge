package nl.dias.service;

import com.google.common.base.Function;
import com.google.common.base.Predicate;
import nl.dias.domein.*;
import nl.dias.domein.polis.Polis;
import nl.dias.domein.predicates.SessieOpCookiePredicate;
import nl.dias.mapper.Mapper;
import nl.dias.messaging.sender.AanmakenTaakSender;
import nl.dias.messaging.sender.AdresAangevuldSender;
import nl.dias.messaging.sender.BsnAangevuldSender;
import nl.dias.messaging.sender.EmailAdresAangevuldSender;
import nl.dias.repository.*;
import nl.dias.web.SoortEntiteit;
import nl.lakedigital.as.messaging.AanmakenTaak;
import nl.lakedigital.as.messaging.AanmakenTaak.SoortTaak;
import nl.lakedigital.as.messaging.BsnAangevuld;
import nl.lakedigital.as.messaging.EmailadresAangevuld;
import nl.lakedigital.djfc.client.oga.AdresClient;
import nl.lakedigital.djfc.client.oga.TelefoonnummerClient;
import nl.lakedigital.djfc.commons.json.AbstracteJsonEntiteitMetSoortEnId;
import nl.lakedigital.djfc.commons.json.JsonContactPersoon;
import nl.lakedigital.djfc.commons.json.JsonTelefoonnummer;
import nl.lakedigital.loginsystem.exception.NietGevondenException;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
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
import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Lists.transform;
import static org.apache.commons.lang3.StringUtils.isBlank;

@Service
public class GebruikerService {

    private static final Logger LOGGER = LoggerFactory.getLogger(GebruikerService.class);

    @Inject
    private GebruikerRepository gebruikerRepository;
    @Inject
    private PolisRepository polisRepository;
    @Inject
    private KantoorRepository kantoorRepository;
    @Inject
    private HypotheekRepository hypotheekRepository;
    @Inject
    private AanmakenTaakSender aanmakenTaakSender;
    @Inject
    private AdresAangevuldSender adresAangevuldSender;
    @Inject
    private EmailAdresAangevuldSender emailAdresAangevuldSender;
    @Inject
    private BsnAangevuldSender bsnAangevuldSender;
    @Inject
    private Mapper mapper;
    @Inject
    private TelefoonnummerService telefoonnummerService;
    @Inject
    private AdresRepository adresRepository;

    @Inject
    private AdresClient adresClient;
    @Inject
    private TelefoonnummerClient telefoonnummerClient;

    public List<ContactPersoon> alleContactPersonen(Long bedrijfsId) {
        return gebruikerRepository.alleContactPersonen(bedrijfsId);
    }

    public void opslaanAdresBijRelatie(Adres adres, Long relatieId) {
        adres.setEntiteitId(relatieId);
        adres.setSoortEntiteit(SoortEntiteit.RELATIE);

        adresRepository.opslaan(adres);
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
        LOGGER.debug("Opslaan {}", gebruiker);

        gebruikerRepository.opslaan(gebruiker);

        // Als Gebruiker een Relatie is en BSN leeg is, moet er een taak worden aangemaakt
        if (gebruiker instanceof Relatie) {
            verstuurEvents((Relatie) gebruiker);
        }
    }

    public void opslaan(final List<JsonContactPersoon> jsonContactPersonen, Long bedrijfId) {
        LOGGER.debug("Opslaan ContactPersonen, aantal {}, bedrijfId {}", jsonContactPersonen.size(), bedrijfId);

        LOGGER.debug("Ophalen bestaande ContactPersonen bij bedrijf");
        List<Long> bestaandeContactPersonen = newArrayList(transform(alleContactPersonen(bedrijfId), new Function<ContactPersoon, Long>() {
            @Override
            public Long apply(ContactPersoon contactPersoon) {
                return contactPersoon.getId();
            }
        }));
        LOGGER.debug("Ids bestaande ContactPersonen {}", bestaandeContactPersonen);

        final List<Long> lijstIdsBewaren = newArrayList(filter(transform(jsonContactPersonen, new Function<JsonContactPersoon, Long>() {
            @Override
            public Long apply(JsonContactPersoon contactPersoon) {
                return contactPersoon.getId();
            }
        }), new Predicate<Long>() {
            @Override
            public boolean apply(Long id) {
                return id != null;
            }
        }));
        LOGGER.debug("Ids ContactPersonen uit front-end {}", lijstIdsBewaren);

        //Verwijderen wat niet (meer) voorkomt
        Iterable<Long> teVerwijderen = filter(bestaandeContactPersonen, new Predicate<Long>() {
            @Override
            public boolean apply(Long contactPersoon) {
                return !lijstIdsBewaren.contains(contactPersoon);
            }
        });

        LOGGER.debug("Ids die dus weg kunnen {}", teVerwijderen);
        for (Long id : teVerwijderen) {
            gebruikerRepository.verwijder(lees(id));
        }

        LOGGER.debug("Opslaan ContactPersonen");
        for (JsonContactPersoon jsonContactPersoon : jsonContactPersonen) {
            jsonContactPersoon.setBedrijf(bedrijfId);
            LOGGER.debug("opslaan {}", ReflectionToStringBuilder.toString(jsonContactPersoon, ToStringStyle.SHORT_PREFIX_STYLE));
            ContactPersoon contactPersoon = mapper.map(jsonContactPersoon, ContactPersoon.class);

            LOGGER.debug("opslaan {}", ReflectionToStringBuilder.toString(contactPersoon, ToStringStyle.SHORT_PREFIX_STYLE));

            gebruikerRepository.opslaan(contactPersoon);

            List<Telefoonnummer> telefoonnummers = new ArrayList<>();
            for (JsonTelefoonnummer jsonTelefoonnummer : jsonContactPersoon.getTelefoonnummers()) {
                telefoonnummers.add(mapper.map(jsonTelefoonnummer, Telefoonnummer.class));
            }
            telefoonnummerService.opslaan(telefoonnummers, contactPersoon.getId(), SoortEntiteit.CONTACTPERSOON);


        }
    }

    public void verwijderOudSpul(Relatie relatie) {
        LOGGER.debug("Adressen wissen bij relatie");
        gebruikerRepository.verwijderAdressenBijRelatie(relatie);
    }

    private void verstuurEvents(Relatie relatie) {
        //        verstuurBsnEvent(relatie);
        //        verstuurAdresEvent(relatie);
        //        verstuurEmailEvent(relatie);
    }

    private void verstuurBsnEvent(Relatie relatie) {
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
    }

    private void verstuurAdresEvent(Relatie relatie) {
        //        if (relatie.getAdres() == null || !relatie.getAdres().isCompleet()) {
        //            LOGGER.info("Adres is leeg of niet volledig, Taak aanmaken");
        //
        //            AanmakenTaak taak = new AanmakenTaak();
        //            taak.setDatumTijdCreatie(LocalDateTime.now());
        //            taak.setRelatie(relatie.getId());
        //            taak.setSoort(SoortTaak.AANVULLEN_ADRES);
        //
        //            aanmakenTaakSender.send(taak);
        //        } else if (relatie.getAdres() != null && relatie.getAdres().isCompleet()) {
        //            LOGGER.info("Adres gevuld, bericht versturen");
        //
        //            AdresAangevuld adresAangevuld = new AdresAangevuld();
        //            adresAangevuld.setRelatie(relatie.getId());
        //
        //            adresAangevuldSender.send(adresAangevuld);
        //        }
    }

    private void verstuurEmailEvent(Relatie relatie) {
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

    public void verwijder(Long id) {
        LOGGER.info("Verwijderen gebruiker met id " + id);

        // Eerst ophalen
        Gebruiker gebruiker = gebruikerRepository.lees(id);

        LOGGER.debug("Opgehaalde gebruiker : ");
        LOGGER.debug(ReflectionToStringBuilder.toString(gebruiker));

        if (gebruiker instanceof Relatie) {
            Relatie relatie = (Relatie) gebruiker;
            //            for (Polis polis : relatie.getPolissen()) {
            //                LOGGER.debug("Verwijder Polis :");
            //                LOGGER.debug(ReflectionToStringBuilder.toString(polis));
            //                polisRepository.verwijder(polis);
            //                relatie.getPolissen().remove(polis);
            //            }
            for (Hypotheek hypotheek : relatie.getHypotheken()) {
                LOGGER.debug("Verwijder Hypotheek :");
                LOGGER.debug(ReflectionToStringBuilder.toString(hypotheek));
                hypotheekRepository.verwijder(hypotheek);
                relatie.getHypotheken().remove(hypotheek);
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

    public Gebruiker zoekOpIdentificatie(String identificatie) throws NietGevondenException {
        return gebruikerRepository.zoekOpIdentificatie(identificatie);
    }

    public List<Gebruiker> zoekOpNaam(String naam) {
        return gebruikerRepository.zoekOpNaam(naam);
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
        relaties.addAll(destilleerRelatie(adresClient.zoeken(zoekTerm)));
        LOGGER.debug("Zoeken op telefoonnummer");
        String zoekTermNumeriek = zoekTerm.replace(" ", "").replace("-", "");
        try {
            Long.valueOf(zoekTermNumeriek);
        } catch (NumberFormatException nfe) {
            zoekTermNumeriek = null;
            LOGGER.trace("", nfe);
        }
        if (zoekTermNumeriek != null) {
            relaties.addAll(destilleerRelatie(telefoonnummerClient.zoeken(zoekTerm)));
        }
        LOGGER.debug("Zoeken op bedrijfnsaam");
        for (Gebruiker g : gebruikerRepository.zoekRelatiesOpBedrijfsnaam(zoekTerm)) {
            relaties.add((Relatie) g);
        }

        LOGGER.debug("Gevonden " + relaties.size() + " Relaties");
        Polis polis = null;
        try {
            polis = polisRepository.zoekOpPolisNummer(zoekTerm, null);
        } catch (NoResultException e) {
            LOGGER.trace("Niks gevonden ", e);
        }
        if (polis != null) {
            relaties.add((Relatie) lees(polis.getRelatie()));
        }
        LOGGER.debug("Gevonden " + relaties.size() + " Relaties");

        List<Relatie> ret = new ArrayList<>();
        for (Relatie r : relaties) {
            if (r != null) {
                //                LOGGER.debug("{}", ReflectionToStringBuilder.toString(r, ToStringStyle.SHORT_PREFIX_STYLE));
                ret.add(r);
            }
        }

        return ret;
    }

    private List<Relatie> destilleerRelatie(List<? extends AbstracteJsonEntiteitMetSoortEnId> entiteitenMetSoortEnId) {
        List<Relatie> relaties = newArrayList(transform(newArrayList(filter(entiteitenMetSoortEnId, new Predicate<AbstracteJsonEntiteitMetSoortEnId>() {
            @Override
            public boolean apply(AbstracteJsonEntiteitMetSoortEnId adres) {
                return adres.getSoortEntiteit().equals(nl.lakedigital.djfc.domain.SoortEntiteit.RELATIE.name());
            }
        })), new Function<AbstracteJsonEntiteitMetSoortEnId, Relatie>() {
            @Override
            public Relatie apply(AbstracteJsonEntiteitMetSoortEnId adres) {
                return (Relatie) gebruikerRepository.lees(adres.getEntiteitId());
            }
        }));

        return relaties;
    }
}
