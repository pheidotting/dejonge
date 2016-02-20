package nl.dias.service;

import nl.dias.domein.*;
import nl.dias.domein.polis.Polis;
import nl.dias.repository.OpmerkingRepository;
import nl.dias.repository.PolisRepository;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

@Service
public class OpmerkingService {
    @Inject
    private OpmerkingRepository opmerkingRepository;
    @Inject
    private AuthorisatieService authorisatieService;
    @Inject
    private SchadeService schadeService;
    @Inject
    private HypotheekService hypotheekService;
    @Inject
    private GebruikerService gebruikerService;
    @Inject
    private PolisRepository polisRepository;
    @Inject
    private BedrijfService bedrijfService;
    @Inject
    private AangifteService aangifteService;

    public List<Opmerking> alleOpmerkingenBijPolis(Long polis) {
        return opmerkingRepository.alleOpmerkingenBijPolis(polis);
    }

    public List<Opmerking> alleOpmerkingenBijSchade(Long schade) {
        return opmerkingRepository.alleOpmerkingenBijSchade(schade);
    }

    public List<Opmerking> alleOpmerkingenBijRelatie(Long relatie) {
        return opmerkingRepository.alleOpmerkingenBijRelatie(relatie);
    }

    public List<Opmerking> alleOpmerkingenBijBedrijf(Long bedrijf) {
        return opmerkingRepository.alleOpmerkingenBijBedrijf(bedrijf);
    }

    public List<Opmerking> alleOpmerkingenBijJaarCijfers(Long jaarCijfers) {
        return opmerkingRepository.alleOpmerkingenBijJaarCijfers(jaarCijfers);
    }

    public void verwijder(Long id) {
        Opmerking opmerking = opmerkingRepository.lees(id);

        opmerkingRepository.verwijder(opmerking);
    }

    public void opslaan(Opmerking opmerking) {
        opmerkingRepository.opslaan(opmerking);

        if (opmerking.getRelatie() != null) {
            Relatie relatie = (Relatie) gebruikerService.lees(opmerking.getRelatie().getId());
            relatie.getOpmerkingen().add(opmerking);

            gebruikerService.opslaan(relatie);
        }

        if (opmerking.getSchade() != null) {
            Schade schade = schadeService.lees(opmerking.getSchade());
            //            schade.getOpmerkingen().add(opmerking);

            schadeService.opslaan(schade);
        }

        if (opmerking.getHypotheek() != null) {
            Hypotheek hypotheek = hypotheekService.leesHypotheek(opmerking.getHypotheek().getId());
            hypotheek.getOpmerkingen().add(opmerking);

            hypotheekService.opslaan(hypotheek);
        }

        if (opmerking.getPolis() != null) {
            Polis polis = polisRepository.lees(opmerking.getPolis());

            polisRepository.opslaan(polis);
        }

        if (opmerking.getBedrijf() != null) {
            Bedrijf bedrijf = bedrijfService.lees(opmerking.getBedrijf());
            //            bedrijf.getOpmerkingen().add(opmerking);

            bedrijfService.opslaan(bedrijf);
        }

        if (opmerking.getAangifte() != null) {
            Aangifte aangifte = aangifteService.lees(opmerking.getAangifte().getId());
            aangifte.getOpmerkingen().add(opmerking);

            aangifteService.opslaan(aangifte);
        }
    }
}
