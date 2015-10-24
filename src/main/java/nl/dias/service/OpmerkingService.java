package nl.dias.service;

import java.util.List;

import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;

import nl.dias.domein.*;
import nl.dias.domein.polis.Polis;
import nl.dias.repository.OpmerkingRepository;

import com.sun.jersey.api.core.InjectParam;
import nl.dias.repository.PolisRepository;

@Named
public class OpmerkingService {
    @InjectParam
    private OpmerkingRepository opmerkingRepository;
    @InjectParam
    private AuthorisatieService authorisatieService;
    @InjectParam
    private SchadeService schadeService;
    @InjectParam
    private HypotheekService hypotheekService;
    @InjectParam
    private GebruikerService gebruikerService;
    @InjectParam
    private PolisRepository polisRepository;
    @InjectParam
    private BedrijfService bedrijfService;

    public List<Opmerking> alleOpmerkingenVoorRelatie(Long relatieId) {
        Relatie relatie = (Relatie) gebruikerService.lees(relatieId);
        return opmerkingRepository.alleOpmerkingenVoorRelatie(relatie);
    }

    public void opslaan(Opmerking opmerking) {
        opmerkingRepository.opslaan(opmerking);

        if (opmerking.getRelatie() != null) {
            Relatie relatie = (Relatie) gebruikerService.lees(opmerking.getRelatie().getId());
            relatie.getOpmerkingen().add(opmerking);

            gebruikerService.opslaan(relatie);
        }

        if (opmerking.getSchade() != null) {
            Schade schade = schadeService.lees(opmerking.getSchade().getId());
            schade.getOpmerkingen().add(opmerking);

            schadeService.opslaan(schade);
        }

        if (opmerking.getHypotheek() != null) {
            Hypotheek hypotheek = hypotheekService.leesHypotheek(opmerking.getHypotheek().getId());
            hypotheek.getOpmerkingen().add(opmerking);

            hypotheekService.opslaan(hypotheek);
        }

        if (opmerking.getPolis() != null) {
            Polis polis = polisRepository.lees(opmerking.getPolis().getId());
            polis.getOpmerkingen().add(opmerking);

            polisRepository.opslaan(polis);
        }

        if (opmerking.getBedrijf() != null) {
            Bedrijf bedrijf = bedrijfService.lees(opmerking.getBedrijf().getId());
            bedrijf.getOpmerkingen().add(opmerking);

            bedrijfService.opslaan(bedrijf);
        }
    }

    public void setOpmerkingRepository(OpmerkingRepository opmerkingRepository) {
        this.opmerkingRepository = opmerkingRepository;
    }

    public void setAuthorisatieService(AuthorisatieService authorisatieService) {
        this.authorisatieService = authorisatieService;
    }

    public void setSchadeService(SchadeService schadeService) {
        this.schadeService = schadeService;
    }

    public void setHypotheekService(HypotheekService hypotheekService) {
        this.hypotheekService = hypotheekService;
    }


}
