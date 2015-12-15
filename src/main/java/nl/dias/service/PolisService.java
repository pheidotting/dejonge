package nl.dias.service;

import com.google.common.collect.Lists;
import nl.dias.domein.*;
import nl.dias.domein.json.JsonPolis;
import nl.dias.domein.polis.Betaalfrequentie;
import nl.dias.domein.polis.Polis;
import nl.dias.domein.polis.SoortVerzekering;
import nl.dias.domein.predicates.PolisOpSchermNaamPredicate;
import nl.dias.domein.predicates.PolissenOpSoortPredicate;
import nl.dias.domein.transformers.PolisToSchermNaamTransformer;
import nl.dias.repository.KantoorRepository;
import nl.dias.repository.PolisRepository;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.joda.time.LocalDate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import javax.persistence.NoResultException;
import java.util.List;

import static com.google.common.collect.Iterables.*;

@Service
public class PolisService {
    private static final Logger LOGGER = LoggerFactory.getLogger(PolisService.class);

    @Inject
    private PolisRepository polisRepository;
    @Inject
    private GebruikerService gebruikerService;
    @Inject
    private KantoorRepository kantoorRepository;
    @Inject
    private BedrijfService bedrijfService;
    @Inject
    private VerzekeringsMaatschappijService verzekeringsMaatschappijService;
    @Inject
    private List<Polis> polissen;

    public List<String> allePolisSoorten(final SoortVerzekering soortVerzekering) {
        Iterable<Polis> poli = filter(polissen, new PolissenOpSoortPredicate(soortVerzekering));

        Iterable<String> polisString = transform(poli, new PolisToSchermNaamTransformer());

        return Lists.newArrayList(polisString);
    }

    public List<Polis> allePolissenVanRelatieEnZijnBedrijf(Relatie relatie) {
        return polisRepository.allePolissenVanRelatieEnZijnBedrijf(relatie);
    }

    public void beeindigen(Long id) {
        Polis polis = polisRepository.lees(id);

        polis.setEindDatum(new LocalDate());
        polisRepository.opslaan(polis);
    }

    public void opslaan(Polis polis) {
        LOGGER.debug(ReflectionToStringBuilder.toString(polis));

        polisRepository.opslaan(polis);

        LOGGER.debug("{}", lees(polis.getId()));
    }

    public Polis lees(Long id) {
        return polisRepository.lees(id);
    }

    public Polis zoekOpPolisNummer(String polisNummer) {
        try {
            return polisRepository.zoekOpPolisNummer(polisNummer, kantoorRepository.lees(1L));
        } catch (NoResultException e) {
            LOGGER.debug("Niks gevonden ", e);
            return null;
        }
    }

    public Bijlage opslaanBijlage(String polisId, Bijlage bijlage) {
        LOGGER.info("Opslaan bijlage met id {}, bij Polis met id {}", bijlage.getId(), polisId);

        Polis polis = polisRepository.lees(Long.valueOf(polisId));

        polis.getBijlages().add(bijlage);

        LOGGER.debug(ReflectionToStringBuilder.toString(bijlage));
        LOGGER.debug(ReflectionToStringBuilder.toString(polis));

        polisRepository.opslaan(polis);

        bijlage.setSoortBijlage(SoortBijlage.POLIS);
        bijlage.setPolis(polis);

        return bijlage;
    }

    public void slaBijlageOp(Long polisId, Bijlage bijlage, String omschrijving) {
        LOGGER.debug("Opslaan Bijlage bij Polis, polisId " + polisId);

        bijlage.setPolis(polisRepository.lees(polisId));
        bijlage.setSoortBijlage(SoortBijlage.POLIS);
        bijlage.setOmschrijving(omschrijving);

        LOGGER.debug("Bijlage naar repository " + bijlage);

        polisRepository.opslaanBijlage(bijlage);
    }

    public Bijlage leesBijlage(Long id) {
        return polisRepository.leesBijlage(id);
    }

    public Bijlage leesBijlage(String s3) {
        return polisRepository.leesBijlage(s3);
    }

    public void verwijder(Long id) throws IllegalArgumentException {
        LOGGER.debug("Ophalen Polis");
        Polis polis = polisRepository.lees(id);

        if (polis == null) {
            throw new IllegalArgumentException("Geen Polis gevonden met id " + id);
        }
        LOGGER.debug("Polis gevonden : " + polis);

        LOGGER.debug("Ophalen Relatie");
        Relatie relatie = (Relatie) gebruikerService.lees(polis.getRelatie().getId());

        LOGGER.debug("Verwijderen Polis bij Relatie");
        relatie.getPolissen().remove(polis);
        LOGGER.debug("Kijken of de Polis nog bij een bedrijf zit");

        gebruikerService.opslaan(relatie);

        polisRepository.verwijder(polis);
    }

    public List<Polis> allePolissenBijRelatie(Relatie relatie) {
        return polisRepository.allePolissenBijRelatie(relatie);
    }

    public List<Polis> allePolissenBijBedrijf(Bedrijf bedrijf) {
        return polisRepository.allePolissenBijBedrijf(bedrijf);
    }

    public void opslaan(JsonPolis jsonPolis) {
        LOGGER.info(ReflectionToStringBuilder.toString(jsonPolis, ToStringStyle.SHORT_PREFIX_STYLE));

        VerzekeringsMaatschappij maatschappij = verzekeringsMaatschappijService.zoekOpNaam(jsonPolis.getMaatschappij());
        LOGGER.debug("maatschappij gevonden : " + maatschappij);

        Relatie relatie = (Relatie) gebruikerService.lees(Long.valueOf(jsonPolis.getRelatie()));
        LOGGER.debug("bij relatie : " + relatie);

        String messages = null;

        if (maatschappij == null) {
            messages = "Kies een verzekeringsmaatschappij";
        } else {
            Polis polis = null;

            if (jsonPolis.getId() != null) {
                LOGGER.debug("Polis opzoeken in database, id = " + jsonPolis.getId());
                polis = polisRepository.lees(jsonPolis.getId());

                LOGGER.debug("{}", polis);
            }

            if (polis == null) {
                // Eerst kijken of het polisnummer al voorkomt
                if (zoekOpPolisNummer(jsonPolis.getPolisNummer()) != null) {
                    throw new IllegalArgumentException("Het betreffende polisnummer komt al voor.");
                }

                LOGGER.debug("Polis = null, daarom opmaken uit Request");
                polis = definieerPolisSoort(jsonPolis.getSoort());
            }

            LOGGER.debug("polis aanvullen met ingevoerde gegevens");
            LOGGER.debug("zet polisnummer " + jsonPolis.getPolisNummer());
            polis.setPolisNummer(jsonPolis.getPolisNummer());
            try {
                polis.setIngangsDatum(stringNaarLocalDate(jsonPolis.getIngangsDatum()));
            } catch (IllegalArgumentException e1) {
                LOGGER.debug("Fout bij parsen datum", e1);
                messages = messages + "Ingangsdatum : " + e1.getMessage() + "<br />";
            }
            try {
                polis.setProlongatieDatum(stringNaarLocalDate(jsonPolis.getProlongatieDatum()));
            } catch (IllegalArgumentException e1) {
                LOGGER.debug("Fout bij parsen datum", e1);
                messages = messages + "Prolongatiedatum : " + e1.getMessage() + "<br />";
            }
            try {
                polis.setWijzigingsDatum(stringNaarLocalDate(jsonPolis.getWijzigingsDatum()));
            } catch (IllegalArgumentException e1) {
                LOGGER.debug("Fout bij parsen datum", e1);
                messages = messages + "Wijzigingsdatum : " + e1.getMessage() + "<br />";
            }
            polis.setBetaalfrequentie(Betaalfrequentie.valueOf(jsonPolis.getBetaalfrequentie().toUpperCase().substring(0, 1)));

            LOGGER.debug("Maatschappij gezet");

            relatie.getPolissen().add(polis);
            polis.setRelatie(relatie);

            if (jsonPolis.getBedrijf() != null) {
                Long bedrijfId = Long.valueOf(jsonPolis.getBedrijf());
                if (bedrijfId != 0) {
                    Bedrijf bedrijf = bedrijfService.lees(Long.valueOf(bedrijfId));
                    polis.setBedrijf(bedrijf);
                    bedrijf.getPolissen().add(polis);
                }
            }

            try {
                LOGGER.debug("zet premiebedrag " + jsonPolis.getPremie());
                polis.setPremie(new Bedrag(jsonPolis.getPremie()));
            } catch (NumberFormatException e) {
                LOGGER.debug(e.getMessage());
            }

            LOGGER.debug("Opslaan polis : " + polis);
            polisRepository.opslaan(polis);

            relatie.getPolissen().add(polis);
            gebruikerService.opslaan(relatie);
        }

        if (messages == null) {
            messages = "ok";
        } else {
            throw new IllegalArgumentException(messages);
        }
    }

    private LocalDate stringNaarLocalDate(String datum) throws IllegalArgumentException {
        String[] d = datum.split("-");

        LocalDate ld = null;
        try {
            ld = new LocalDate(Integer.parseInt(d[2]), Integer.parseInt(d[1]), Integer.parseInt(d[0]));
        } catch (ArrayIndexOutOfBoundsException e) {
            LOGGER.debug("Ongeledige datum meegegeven", e);
            throw new IllegalArgumentException("Datum bevat een ongeldige waarde.");
        }

        return ld;
    }

    public Polis definieerPolisSoort(String soort) {
        Polis p = getOnlyElement(filter(polissen, new PolisOpSchermNaamPredicate(soort)));
        return p.nieuweInstantie();
    }

}
