package nl.dias.service;

import nl.dias.domein.Opmerking;
import nl.dias.repository.OpmerkingRepository;
import nl.dias.web.SoortEntiteit;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

@Service
public class OpmerkingService {
    @Inject
    private OpmerkingRepository opmerkingRepository;

    public List<Opmerking> alleOpmerkingenBijEntiteit(SoortEntiteit soortEntiteit, Long entiteitId) {
        return opmerkingRepository.alleOpmerkingenBijEntiteit(soortEntiteit, entiteitId);
    }

    public void verwijder(Long id) {
        Opmerking opmerking = opmerkingRepository.lees(id);

        opmerkingRepository.verwijder(opmerking);
    }

    public void opslaan(Opmerking opmerking) {
        opmerkingRepository.opslaan(opmerking);
    }

    public Opmerking lees(Long id) {
        return opmerkingRepository.lees(id);
    }
}
