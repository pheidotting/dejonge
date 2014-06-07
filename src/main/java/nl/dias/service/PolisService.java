package nl.dias.service;

import java.util.List;

import javax.inject.Named;

import nl.dias.domein.Bijlage;
import nl.dias.domein.Relatie;
import nl.dias.domein.SoortBijlage;
import nl.dias.domein.polis.Polis;
import nl.dias.repository.PolisRepository;

import com.sun.jersey.api.core.InjectParam;

@Named
public class PolisService {
    @InjectParam
    private PolisRepository polisRepository;

    public List<Polis> allePolissenVanRelatieEnZijnBedrijf(Relatie relatie) {
        return polisRepository.allePolissenVanRelatieEnZijnBedrijf(relatie);
    }

    public void opslaan(Polis polis) {
        polisRepository.opslaan(polis);
    }

    public Polis zoekOpPolisNummer(String PolisNummer) {
        return polisRepository.zoekOpPolisNummer(PolisNummer);
    }

    public void slaBijlageOp(String bestandsNaam, Long polisId, SoortBijlage soortBijlage) {
        Bijlage bijlage = new Bijlage();
        bijlage.setBestandsNaam(bestandsNaam);
        bijlage.setPolis(polisRepository.lees(polisId));
        bijlage.setSoortBijlage(soortBijlage);

        polisRepository.opslaanBijlage(bijlage);
    }

    public Bijlage leesBijlage(Long id) {
        return polisRepository.leesBijlage(id);
    }
}
