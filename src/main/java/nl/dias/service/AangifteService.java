package nl.dias.service;

import com.sun.jersey.api.core.InjectParam;
import nl.dias.domein.*;
import nl.dias.repository.AangifteRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.joda.time.LocalDate;

import javax.inject.Named;
import java.util.List;

@Named
public class AangifteService {
    private final static Logger LOGGER = LoggerFactory.getLogger(AangifteService.class);

    @InjectParam
    private AangifteRepository aangifteRepository;
    @InjectParam
    private GebruikerService gebruikerService;

    public List<Aangifte> getAfgeslotenAangiftes(Relatie relatie) {
        return aangifteRepository.getGeslotenAngiftes(relatie);
    }

    public void opslaan(Aangifte aangifte) {
        aangifte.setRelatie((Relatie) gebruikerService.lees(aangifte.getRelatie().getId()));

        aangifteRepository.opslaan(aangifte);
    }

    public Aangifte lees(Long id) {
        return aangifteRepository.lees(id);
    }

    public void slaAangifteOp(Aangifte aangifte, Bijlage bijlage, String omschrijving) {
        LOGGER.debug("Opslaan Bijlage bij Aangifte, aangifteId " + aangifte.getId());

        bijlage.setAangifte(aangifte);
        bijlage.setSoortBijlage(SoortBijlage.IBAANGIFTE);
        bijlage.setOmschrijving(omschrijving);

        LOGGER.debug("Bijlage naar repository " + bijlage);

        aangifteRepository.opslaanBijlage(bijlage);
    }

    public List<Aangifte> getOpenstaandeAangiftes(Relatie relatie) {
        List<Aangifte> aangiftes = aangifteRepository.getOpenAngiftes(relatie);

        Aangifte aangifteHuidigJaarMinusEen = new Aangifte();
        aangifteHuidigJaarMinusEen.setJaar(LocalDate.now().minusYears(1).getYear());
        aangifteHuidigJaarMinusEen.setRelatie(relatie);

        if (!aangiftes.contains(aangifteHuidigJaarMinusEen) && !isAangifteAanwezigVoorHuidigJaarMinEen(relatie)) {
            aangiftes.add(aangifteHuidigJaarMinusEen);
        }
        return aangiftes;
    }

    protected boolean isAangifteAanwezigVoorHuidigJaarMinEen(Relatie relatie) {
        LOGGER.debug("Eerst de bestaande aangiftes ophalen");

        List<Aangifte> aangiftes = aangifteRepository.getAlleAngiftes(relatie);
        LOGGER.debug("Opgehaald " + aangiftes.size() + " aangiftes");

        for (Aangifte aangifte : aangiftes) {
            LOGGER.debug("{}",aangifte.getJaar());

            if (aangifte.getJaar() == LocalDate.now().minusYears(1).getYear()) {
                return true;
            }
        }

        return false;
    }

    public void afronden(Long id, LocalDate datumAfronden, Gebruiker medewerker) {
        Aangifte aangifte = aangifteRepository.lees(id);

        aangifte.setDatumAfgerond(datumAfronden);
        aangifte.setAfgerondDoor((Medewerker) medewerker);

        aangifteRepository.opslaan(aangifte);
    }

    public void setAangifteRepository(AangifteRepository aangifteRepository) {
        this.aangifteRepository = aangifteRepository;
    }
}
