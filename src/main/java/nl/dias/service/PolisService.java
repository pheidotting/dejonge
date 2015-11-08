package nl.dias.service;

import nl.dias.domein.*;
import nl.dias.domein.json.JsonPolis;
import nl.dias.domein.polis.*;
import nl.dias.repository.KantoorRepository;
import nl.dias.repository.PolisRepository;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.joda.time.LocalDate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import javax.persistence.NoResultException;
import java.util.ArrayList;
import java.util.List;

@Service
public class PolisService {
    private final static Logger LOGGER = LoggerFactory.getLogger(PolisService.class);

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
        //        List<Bijlage> bijlages = new ArrayList<>();

        // ophalen al bestanden bijlages
        //        bijlages.addAll(polisRepository.zoekBijlagesBijPolis(polis));
        //        LOGGER.debug(bijlages);
        //        bijlages.addAll(werkBijlagesBij(polis));
        //        LOGGER.debug(bijlages);
        //
        //        polis.setBijlages(Sets.newHashSet(bijlages));
        //
        //        for (Bijlage bijlage : bijlages) {
        //            polisRepository.opslaanBijlage(bijlage);
        //        }
        //        polisRepository.verwijder(polisRepository.lees(polis.getId()));
        //        polis.setBijlages(null);
        //        polis.getBijlages();

        polisRepository.opslaan(polis);

        //        Relatie relatie = polis.getRelatie();
        //        relatie.getPolissen().add(polis);

        //        gebruikerService.opslaan(relatie);

        LOGGER.debug("{}", lees(polis.getId()));
    }

    private List<Bijlage> werkBijlagesBij(Polis polis) {
        List<Bijlage> result = new ArrayList<>();

        for (Bijlage bijlage : polis.getBijlages()) {
            Bijlage uitDb = polisRepository.leesBijlage(bijlage.getId());

            uitDb.setPolis(polis);
            uitDb.setSoortBijlage(SoortBijlage.POLIS);
            uitDb.setOmschrijving(bijlage.getOmschrijving());

            result.add(uitDb);
        }

        return result;
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

    public void opslaanBijlage(String polisId, Bijlage bijlage) {
        LOGGER.info("Opslaan bijlage met id {}, bij Polis met id {}", bijlage.getId(), polisId);

        Polis polis = polisRepository.lees(Long.valueOf(polisId));

        polis.getBijlages().add(bijlage);
        bijlage.setPolis(polis);
        bijlage.setSoortBijlage(SoortBijlage.POLIS);

        LOGGER.debug(ReflectionToStringBuilder.toString(bijlage));

        polisRepository.opslaan(polis);
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
        for (Bedrijf bedrijf : relatie.getBedrijven()) {
            LOGGER.debug("Bedrijf met id " + bedrijf.getId());
            bedrijf.getPolissen().remove(polis);
        }

        gebruikerService.opslaan(relatie);

        polisRepository.verwijder(polis);
    }

    public List<Polis> allePolissenBijRelatie(Relatie relatie) {
        return polisRepository.allePolissenBijRelatie(relatie);
    }

    public void opslaan(JsonPolis jsonPolis) {
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

//            LOGGER.debug("Maatschappij zetten" + maatschappij);
//            polis.setMaatschappij(maatschappij);
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
        Polis polis = null;

        if ("Aansprakelijkheid".equals(soort)) {
            polis = new AansprakelijkheidVerzekering();
        }
        if ("Annulering".equals(soort) || "Annulerings".equals(soort) || "Doorlopende Annulering".equals(soort)) {
            polis = new AnnuleringsVerzekering();
        }
        if ("Auto".equals(soort)) {
            polis = new AutoVerzekering();
        }
        if ("Brom-/Snorfiets".equals(soort)) {
            polis = new BromSnorfietsVerzekering();
        }
        if ("Caravan".equals(soort)) {
            polis = new CaravanVerzekering();
        }
        if ("Fiets".equals(soort)) {
            polis = new FietsVerzekering();
        }
        if ("Camper".equals(soort)) {
            polis = new CamperVerzekering();
        }
        if ("Inboedel".equals(soort)) {
            polis = new InboedelVerzekering();
        }
        if ("Leven".equals(soort)) {
            polis = new LevensVerzekering();
        }
        if ("Mobiele apparatuur".equals(soort)) {
            polis = new MobieleApparatuurVerzekering();
        }
        if ("Motor".equals(soort)) {
            polis = new MotorVerzekering();
        }
        if ("Ongevallen".equals(soort)) {
            polis = new OngevallenVerzekering();
        }
        if ("Pakket".equals(soort)) {
            polis = new Pakket();
        }
        if ("Pleziervaartuig".equals(soort)) {
            polis = new PleziervaartuigVerzekering();
        }
        if ("Rechtsbijstand".equals(soort)) {
            polis = new RechtsbijstandVerzekering();
        }
        if ("Reis".equals(soort)) {
            polis = new ReisVerzekering();
        }
        if ("Recreatie".equals(soort)) {
            polis = new RecreatieVerzekering();
        }
        if ("Woonhuis".equals(soort)) {
            polis = new WoonhuisVerzekering();
        }
        if ("Zorg".equals(soort)) {
            polis = new ZorgVerzekering();
        }
        if ("SVI".equals(soort)) {
            polis = new SviSchadeverzekerininzittende();
        }
        if (polis == null) {
            throw new IllegalArgumentException("Kies een soort verzekering");
        }

        return polis;
    }

    public void setPolisRepository(PolisRepository polisRepository) {
        this.polisRepository = polisRepository;
    }

    public void setGebruikerService(GebruikerService gebruikerService) {
        this.gebruikerService = gebruikerService;
    }

    public void setKantoorRepository(KantoorRepository kantoorRepository) {
        this.kantoorRepository = kantoorRepository;
    }

    public void setBedrijfService(BedrijfService bedrijfService) {
        this.bedrijfService = bedrijfService;
    }

    public void setVerzekeringsMaatschappijService(VerzekeringsMaatschappijService verzekeringsMaatschappijService) {
        this.verzekeringsMaatschappijService = verzekeringsMaatschappijService;
    }

}
