package nl.dias.web;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import nl.dias.domein.Bedrag;
import nl.dias.domein.Relatie;
import nl.dias.domein.VerzekeringsMaatschappij;
import nl.dias.domein.polis.AansprakelijkheidVerzekering;
import nl.dias.domein.polis.AnnuleringsVerzekering;
import nl.dias.domein.polis.AutoVerzekering;
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
import nl.dias.domein.polis.SoortAutoVerzekering;
import nl.dias.domein.polis.WoonhuisVerzekering;
import nl.dias.domein.polis.ZorgVerzekering;
import nl.dias.service.GebruikerService;
import nl.dias.service.PolisService;
import nl.dias.service.VerzekeringsMaatschappijService;

import org.apache.log4j.Logger;
import org.joda.time.LocalDate;

import com.google.gson.Gson;
import com.sun.jersey.api.core.InjectParam;

@Path("/polis")
public class PolisController {// extends AbstractController {
    private Logger logger = Logger.getLogger(this.getClass());

    @InjectParam
    private PolisService polisService;
    @InjectParam
    private GebruikerService gebruikerService;
    @InjectParam
    private VerzekeringsMaatschappijService verzekeringsMaatschappijService;

    private Gson gson = new Gson();

    @GET
    @Path("/opslaan")
    @Produces(MediaType.TEXT_PLAIN)
    public String opslaan(@QueryParam("maatschappij") String strMaatschappij, @QueryParam("soort") String soort, @QueryParam("polisNummer") String polisNummer,
            @QueryParam("soortAutoVerzekering") String soortAutoVerzekering, @QueryParam("kenteken") String kenteken, @QueryParam("oldtimer") String oldtimer,
            @QueryParam("ingangsDatumString") String ingangsDatumString, @QueryParam("premie") String premie, @QueryParam("relatie") String relatieString) {

        VerzekeringsMaatschappij maatschappij = verzekeringsMaatschappijService.zoekOpNaam(strMaatschappij);

        Relatie relatie = (Relatie) gebruikerService.lees(Long.parseLong(relatieString));

        String messages = null;

        if (maatschappij == null) {
            messages = "Kies een verzekeringsmaatschappij";
        } else {
            Polis polis = null;

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
                ((AutoVerzekering) polis).setKenteken(kenteken);
                ((AutoVerzekering) polis).setSoortAutoVerzekering(SoortAutoVerzekering.zoek(soortAutoVerzekering));
            }
            if ("Woonhuis".equals(soort)) {
                polis = new WoonhuisVerzekering();
            }
            if ("Leven".equals(soort)) {
                polis = new LevensVerzekering();
            }
            if ("Motor".equals(soort)) {
                polis = new MotorVerzekering();
                ((MotorVerzekering) polis).setOldtimer(oldtimer.equals("true"));
                ((MotorVerzekering) polis).setKenteken(kenteken);
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
                polis.setPolisNummer(polisNummer);
                polis.setIngangsDatum(stringNaarLocalDate(ingangsDatumString));
                polis.setMaatschappij(maatschappij);

                relatie.getPolissen().add(polis);
                polis.setRelatie(relatie);
                try {
                    polis.setPremie(new Bedrag(premie));
                } catch (NumberFormatException e) {
                    logger.debug(e.getMessage());
                }
            }

            if (polis != null) {
                polisService.opslaan(polis);
            }
        }

        if (messages == null) {
            messages = "ok";
        }
        return gson.toJson(messages);
    }

    /**
     * Levert alle Polissen, incl. die van de bedrijven van de Relatie
     * 
     * @param relatie
     * @return
     */
    @GET
    @Path("/allePolissen")
    @Produces(MediaType.TEXT_PLAIN)
    public String allePolissen(@QueryParam("relatie") String strRelatie) {
        Relatie relatie = (Relatie) gebruikerService.lees(Long.parseLong(strRelatie));

        List<Polis> lijst = polisService.allePolissenVanRelatieEnZijnBedrijf(relatie);

        for (Polis polis : lijst) {
            polis.setBedrijf(null);
            polis.setRelatie(null);
        }
        return gson.toJson(lijst);
    }

    private LocalDate stringNaarLocalDate(String datum) {
        String[] d = datum.split("-");

        LocalDate ld = new LocalDate(Integer.parseInt(d[2]), Integer.parseInt(d[1]), Integer.parseInt(d[0]));

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
