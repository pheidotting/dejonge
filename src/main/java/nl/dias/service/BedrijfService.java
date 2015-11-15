package nl.dias.service;

import nl.dias.domein.Bedrijf;
import nl.dias.domein.Relatie;
import nl.dias.repository.BedrijfRepository;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

@Service
public class BedrijfService {
    @Inject
    private BedrijfRepository bedrijfRepository;

    public void opslaan(Bedrijf bedrijf) {
        bedrijfRepository.opslaan(bedrijf);
    }

    public Bedrijf lees(Long id) {
        return bedrijfRepository.lees(id);
    }

    public void verwijder(Long id) {
        bedrijfRepository.verwijder(lees(id));
    }

    public List<Bedrijf> alleBedrijvenBijRelatie(Relatie relatie) {
        return bedrijfRepository.alleBedrijvenBijRelatie(relatie);
    }

    public List<Bedrijf> alles(){
        return bedrijfRepository.alles();
    }

    public void setBedrijfRepository(BedrijfRepository bedrijfRepository) {
        this.bedrijfRepository = bedrijfRepository;
    }
}
