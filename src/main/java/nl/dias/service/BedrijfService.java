package nl.dias.service;

import javax.inject.Named;

import nl.dias.domein.Bedrijf;
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
}
