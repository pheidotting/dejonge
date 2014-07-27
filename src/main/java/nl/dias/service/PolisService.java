package nl.dias.service;

import java.util.List;

import javax.inject.Named;
import javax.persistence.NoResultException;

import nl.dias.domein.Bedrag;
import nl.dias.domein.Bedrijf;
import nl.dias.domein.Bijlage;
import nl.dias.domein.Relatie;
import nl.dias.domein.SoortBijlage;
import nl.dias.domein.VerzekeringsMaatschappij;
import nl.dias.domein.json.JsonPolis;
import nl.dias.domein.polis.AansprakelijkheidVerzekering;
import nl.dias.domein.polis.AnnuleringsVerzekering;
import nl.dias.domein.polis.AutoVerzekering;
import nl.dias.domein.polis.Betaalfrequentie;
import nl.dias.domein.polis.BromSnorfietsVerzekering;
import nl.dias.domein.polis.CamperVerzekering;
import nl.dias.domein.polis.InboedelVerzekering;
import nl.dias.domein.polis.LevensVerzekering;
import nl.dias.domein.polis.MobieleApparatuurVerzekering;
import nl.dias.domein.polis.MotorVerzekering;
import nl.dias.domein.polis.OngevallenVerzekering;
import nl.dias.domein.polis.PleziervaartuigVerzekering;
import nl.dias.domein.polis.Polis;
import nl.dias.domein.polis.RechtsbijstandVerzekering;
import nl.dias.domein.polis.RecreatieVerzekering;
import nl.dias.domein.polis.ReisVerzekering;
import nl.dias.domein.polis.WoonhuisVerzekering;
import nl.dias.domein.polis.ZorgVerzekering;
import nl.dias.repository.KantoorRepository;
import nl.dias.repository.PolisRepository;
import nl.lakedigital.archief.service.ArchiefService;

import org.apache.log4j.Logger;
import org.joda.time.LocalDate;

import com.sun.jersey.api.core.InjectParam;

@Named
public class PolisService {
    private final static Logger LOGGER = Logger.getLogger(PolisService.class);

    @InjectParam
    private PolisRepository polisRepository;
    @InjectParam
    private ArchiefService archiefService;
    @InjectParam
    private GebruikerService gebruikerService;
    @InjectParam
    private KantoorRepository kantoorRepository;
    @InjectParam
    private BedrijfService bedrijfService;
    @InjectParam
    private VerzekeringsMaatschappijService verzekeringsMaatschappijService;

    public List<Polis> allePolissenVanRelatieEnZijnBedrijf(Relatie relatie) {
        return polisRepository.allePolissenVanRelatieEnZijnBedrijf(relatie);
    }

    public void opslaan(Polis polis) {
        polisRepository.opslaan(polis);
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

    public void slaBijlageOp(Long polisId, String s3Identificatie) {
        LOGGER.debug("Opslaan Bijlage bij Polis, polisId " + polisId + " s3Identificatie " + s3Identificatie);

        Bijlage bijlage = new Bijlage();
        bijlage.setPolis(polisRepository.lees(polisId));
        bijlage.setSoortBijlage(SoortBijlage.POLIS);
        bijlage.setS3Identificatie(s3Identificatie);

        LOGGER.debug("Bijlage naar repository " + bijlage);

        polisRepository.opslaanBijlage(bijlage);
    }

    public Bijlage leesBijlage(Long id) {
        return polisRepository.leesBijlage(id);
    }

    public void verwijder(Long id) throws IllegalArgumentException {
        archiefService.setBucketName("dias");

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

        for (Bijlage bijlage : polis.getBijlages()) {
            archiefService.verwijderen(bijlage.getS3Identificatie());
        }

        polisRepository.verwijder(polis);
    }

    public List<Polis> allePolissenBijRelatie(Relatie relatie) {
        return polisRepository.allePolissenBijRelatie(relatie);
    }

    public void opslaan(JsonPolis jsonPolis) {
        // Eerst kijken of het polisnummer al voorkomt
        if (zoekOpPolisNummer(jsonPolis.getPolisNummer()) != null) {
            throw new IllegalArgumentException("Het betreffende polisnummer komt al voor.");
        }

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

                LOGGER.debug(polis);
            }

            if (polis == null) {
                LOGGER.debug("Polis = null, daarom opmaken uit Request");
                polis = definieerPolisSoort(jsonPolis.getSoort());
            }

            if (polis == null) {
                messages = "Kies een soort verzekering";
            } else {
                LOGGER.debug("polis aanmaken");
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

                polis.setMaatschappij(maatschappij);

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
            }

            if (polis != null) {
                LOGGER.debug("Opslaan polis : " + polis);
                polisRepository.opslaan(polis);

                relatie.getPolissen().add(polis);
                gebruikerService.opslaan(relatie);
            } else {
                LOGGER.error("lege polis..");
            }
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

    private Polis definieerPolisSoort(String soort) {
        Polis polis = null;

        if ("Aansprakelijkheid".equals(soort)) {
            polis = new AansprakelijkheidVerzekering();
        }
        if ("Annulering".equals(soort) || "Doorlopende Annulering".equals(soort)) {
            polis = new AnnuleringsVerzekering();
        }
        if ("Auto".equals(soort)) {
            polis = new AutoVerzekering();
        }
        if ("Brom-/Snorfiets".equals(soort)) {
            polis = new BromSnorfietsVerzekering();
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
        if ("Pleziervaartuigen".equals(soort)) {
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

        return polis;
    }

    public void setPolisRepository(PolisRepository polisRepository) {
        this.polisRepository = polisRepository;
    }

    public void setArchiefService(ArchiefService archiefService) {
        this.archiefService = archiefService;
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
