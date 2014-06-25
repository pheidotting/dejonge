package nl.dias.service;

import java.util.Collections;
import java.util.List;

import javax.inject.Named;

import nl.dias.domein.VerzekeringsMaatschappij;
import nl.dias.repository.VerzekeringsMaatschappijRepository;

import com.sun.jersey.api.core.InjectParam;

@Named
public class VerzekeringsMaatschappijService {
    @InjectParam
    private VerzekeringsMaatschappijRepository verzekeringsMaatschappijRepository;

    public VerzekeringsMaatschappij zoekOpNaam(String naam) {
        return verzekeringsMaatschappijRepository.zoekOpNaam(naam);
    }

    public List<VerzekeringsMaatschappij> alles() {
        List<VerzekeringsMaatschappij> lijst = verzekeringsMaatschappijRepository.alles();
        Collections.sort(lijst);

        return lijst;
    }

    public void setVerzekeringsMaatschappijRepository(VerzekeringsMaatschappijRepository verzekeringsMaatschappijRepository) {
        this.verzekeringsMaatschappijRepository = verzekeringsMaatschappijRepository;
    }
}
