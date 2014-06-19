package nl.dias.web;

import java.io.InputStream;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import nl.dias.domein.Bedrag;
import nl.dias.domein.Bedrijf;
import nl.dias.domein.Relatie;
import nl.dias.domein.VerzekeringsMaatschappij;
import nl.dias.domein.json.JsonFoutmelding;
import nl.dias.domein.json.OpslaanPolis;
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
import nl.dias.service.BedrijfService;
import nl.dias.service.BijlageService;
import nl.dias.service.GebruikerService;
import nl.dias.service.PolisService;
import nl.dias.service.VerzekeringsMaatschappijService;
import nl.lakedigital.archief.service.ArchiefService;

import org.apache.log4j.Logger;
import org.joda.time.LocalDate;

import com.google.gson.Gson;
import com.sun.jersey.api.core.InjectParam;
import com.sun.jersey.core.header.FormDataContentDisposition;
import com.sun.jersey.multipart.FormDataParam;

@Path("/polis")
public class PolisController {// extends AbstractController {
    private final Logger logger = Logger.getLogger(this.getClass());

    @InjectParam
    private PolisService polisService;
    @InjectParam
    private GebruikerService gebruikerService;
    @InjectParam
    private VerzekeringsMaatschappijService verzekeringsMaatschappijService;
    @InjectParam
    private BedrijfService bedrijfService;
    @InjectParam
    private BijlageService bijlageService;
    @InjectParam
    private ArchiefService archiefService;

    private final Gson gson = new Gson();

    @POST
    @Path("/opslaan")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response opslaan(OpslaanPolis opslaanPolis) {
        VerzekeringsMaatschappij maatschappij = verzekeringsMaatschappijService.zoekOpNaam(opslaanPolis.getMaatschappij());
        logger.debug("maatschappij gevonden : " + maatschappij);

        Relatie relatie = (Relatie) gebruikerService.lees(opslaanPolis.getRelatie());
        logger.debug("bij relatie : " + relatie);

        String messages = null;

        if (maatschappij == null) {
            messages = "Kies een verzekeringsmaatschappij";
        } else {
            Polis polis = null;
            String soort = opslaanPolis.getSoortVerzekering();

            if ("Brom-/Snorfiets".equals(soort)) {
                polis = new BromSnorfietsVerzekering();
            }
            if ("Camper".equals(soort)) {
                polis = new CamperVerzekering();
            }
            if ("Doorlopende Annulering".equals(soort)) {
                polis = new AnnuleringsVerzekering();
            }
            if ("Leven".equals(soort)) {
                polis = new LevensVerzekering();
            }
            if ("Mobiele apparatuur".equals(soort)) {
                polis = new MobieleApparatuurVerzekering();
            }
            if ("Ongevallen".equals(soort)) {
                polis = new OngevallenVerzekering();
            }
            if ("Pleziervaartuigen".equals(soort)) {
                polis = new PleziervaartuigVerzekering();
            }
            if ("Recreatie".equals(soort)) {
                polis = new RecreatieVerzekering();
            }
            if ("Aansprakelijkheid".equals(soort)) {
                polis = new AansprakelijkheidVerzekering();
            }
            if ("Auto".equals(soort)) {
                polis = new AutoVerzekering();
                // ((AutoVerzekering) polis).setKenteken(kenteken);
                // ((AutoVerzekering)
                // polis).setSoortAutoVerzekering(SoortAutoVerzekering.zoek(soortAutoVerzekering));
            }
            if ("Woonhuis".equals(soort)) {
                polis = new WoonhuisVerzekering();
            }
            if ("Leven".equals(soort)) {
                polis = new LevensVerzekering();
            }
            if ("Motor".equals(soort)) {
                polis = new MotorVerzekering();
                // ((MotorVerzekering)
                // polis).setOldtimer(oldtimer.equals("true"));
                // ((MotorVerzekering) polis).setKenteken(kenteken);
            }
            if ("Inboedel".equals(soort)) {
                polis = new InboedelVerzekering();
            }
            if ("Rechtsbijstand".equals(soort)) {
                polis = new RechtsbijstandVerzekering();
            }
            if ("Reis".equals(soort)) {
                polis = new ReisVerzekering();
            }
            if ("Zorg".equals(soort)) {
                polis = new ZorgVerzekering();
            }

            if (polis == null) {
                messages = "Kies een soort verzekering";
            } else {
                logger.debug("polis aanmaken");
                polis.setPolisNummer(opslaanPolis.getPolisNummer());
                try {
                    polis.setIngangsDatum(stringNaarLocalDate(opslaanPolis.getIngangsDatumString()));
                } catch (IllegalArgumentException e1) {
                    messages = messages + "Ingangsdatum : " + e1.getMessage() + "<br />";
                }
                try {
                    polis.setProlongatieDatum(stringNaarLocalDate(opslaanPolis.getProlongatiedatumString()));
                } catch (IllegalArgumentException e1) {
                    messages = messages + "Prolongatiedatum : " + e1.getMessage() + "<br />";
                }
                try {
                    polis.setWijzigingsDatum(stringNaarLocalDate(opslaanPolis.getWijzigingsdatumString()));
                } catch (IllegalArgumentException e1) {
                    messages = messages + "Wijzigingsdatum : " + e1.getMessage() + "<br />";
                }
                polis.setBetaalfrequentie(Betaalfrequentie.valueOf(opslaanPolis.getBetaalfrequentie().toUpperCase().substring(0, 1)));

                polis.setMaatschappij(maatschappij);

                relatie.getPolissen().add(polis);
                polis.setRelatie(relatie);

                if (opslaanPolis.getBedrijf() != null) {
                    Long bedrijfId = Long.valueOf(opslaanPolis.getBedrijf());
                    if (bedrijfId != 0) {
                        Bedrijf bedrijf = bedrijfService.lees(Long.valueOf(bedrijfId));
                        polis.setBedrijf(bedrijf);
                        bedrijf.getPolissen().add(polis);
                    }
                }

                try {
                    logger.debug("zet premiebedrag " + opslaanPolis.getPremie());
                    polis.setPremie(new Bedrag(opslaanPolis.getPremie()));
                } catch (NumberFormatException e) {
                    logger.debug(e.getMessage());
                }
            }

            if (polis != null) {
                logger.debug("Opslaan polis : " + polis);
                polisService.opslaan(polis);

                relatie.getPolissen().add(polis);
                gebruikerService.opslaan(relatie);
            } else {
                logger.error("lege polis..");
            }
        }

        if (messages == null) {
            messages = "ok";
        } else {
            logger.error(messages);
            return Response.status(500).entity(new JsonFoutmelding(messages)).build();
        }
        return Response.status(200).entity(new JsonFoutmelding()).build();
    }

    @POST
    @Path("/upload")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response uploadFile(@FormDataParam("file") InputStream uploadedInputStream, @FormDataParam("file") FormDataContentDisposition fileDetail, @FormDataParam("polisNummer") String polisNummer) {

        logger.debug("opslaan bijlage bij polis " + polisNummer + ", bestandsnaam " + fileDetail.getFileName());
        Polis polis = polisService.zoekOpPolisNummer(polisNummer);

        logger.debug("eigen database bijwerken");
        polisService.slaBijlageOp(polis.getId(), bijlageService.uploaden(uploadedInputStream, fileDetail));

        return Response.status(200).entity("").build();

    }

    @GET
    @Path("/verwijder")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response verwijder(@QueryParam("id") Long id) {
        logger.debug("verwijderen Polis met id " + id);
        try {
            polisService.verwijder(id);
        } catch (IllegalArgumentException e) {
            logger.error(e.getMessage());
            return Response.status(500).entity(new JsonFoutmelding(e.getMessage())).build();
        }
        return Response.status(202).entity(new JsonFoutmelding()).build();
    }

    private LocalDate stringNaarLocalDate(String datum) throws IllegalArgumentException {
        String[] d = datum.split("-");

        LocalDate ld = null;
        try {
            ld = new LocalDate(Integer.parseInt(d[2]), Integer.parseInt(d[1]), Integer.parseInt(d[0]));
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new IllegalArgumentException("Datum bevat een ongeldige waarde.");
        }

        return ld;
    }

    public void setPolisService(PolisService polisService) {
        this.polisService = polisService;
    }

    public void setVerzekeringsMaatschappijService(VerzekeringsMaatschappijService verzekeringsMaatschappijService) {
        this.verzekeringsMaatschappijService = verzekeringsMaatschappijService;
    }

    public void setGebruikerService(GebruikerService gebruikerService) {
        this.gebruikerService = gebruikerService;
    }
}
