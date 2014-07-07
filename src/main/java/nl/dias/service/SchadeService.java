package nl.dias.service;

import java.util.List;

import javax.inject.Named;

import nl.dias.domein.SoortSchade;
import nl.dias.repository.SchadeRepository;

import com.sun.jersey.api.core.InjectParam;

@Named
public class SchadeService {
    @InjectParam
    private SchadeRepository schadeRepository;

    public List<SoortSchade> soortenSchade() {
        return schadeRepository.soortenSchade();
    }

    public List<SoortSchade> soortenSchade(String omschrijving) {
        return schadeRepository.soortenSchade(omschrijving);
    }

    public void setSchadeRepository(SchadeRepository schadeRepository) {
        this.schadeRepository = schadeRepository;
    }
}
