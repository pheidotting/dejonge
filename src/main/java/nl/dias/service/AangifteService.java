package nl.dias.service;

import java.util.List;

import javax.inject.Named;

import nl.dias.domein.Aangifte;
import nl.dias.domein.Relatie;
import nl.dias.repository.AangifteRepository;

import org.apache.log4j.Logger;
import org.joda.time.LocalDate;

import com.sun.jersey.api.core.InjectParam;

@Named
public class AangifteService {
    private final static Logger LOGGER = Logger.getLogger(AangifteService.class);

    @InjectParam
    private AangifteRepository aangifteRepository;

    public List<Aangifte> getOpenstaandeAangiftes(Relatie relatie) {
        List<Aangifte> aangiftes = aangifteRepository.getOpenAngiftes(relatie);

        Aangifte aangifteHuidigJaarMinusEen = new Aangifte();
        aangifteHuidigJaarMinusEen.setJaar(LocalDate.now().minusYears(1).getYear());

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
            LOGGER.debug(aangifte.getJaar());

            if (aangifte.getJaar() == LocalDate.now().minusYears(1).getYear()) {
                return true;
            }
        }

        return false;
    }

    public void afronden(Long id, LocalDate datumAfronden) {
        Aangifte aangifte = aangifteRepository.lees(id);

        aangifte.setDatumAfgerond(datumAfronden);

        aangifteRepository.opslaan(aangifte);
    }

    public void setAangifteRepository(AangifteRepository aangifteRepository) {
        this.aangifteRepository = aangifteRepository;
    }
}
