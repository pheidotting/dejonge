package nl.dias.service;

import java.util.List;

import javax.inject.Named;

import nl.dias.domein.Bedrijf;
import nl.dias.domein.Relatie;
import nl.dias.repository.BedrijfRepository;

import com.sun.jersey.api.core.InjectParam;

@Named
public class BedrijfService {
    @InjectParam
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

    public void setBedrijfRepository(BedrijfRepository bedrijfRepository) {
        this.bedrijfRepository = bedrijfRepository;
    }
}
