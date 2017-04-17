package nl.dias.service;

import nl.dias.domein.Relatie;
import nl.lakedigital.djfc.client.identificatie.IdentificatieClient;
import nl.lakedigital.djfc.client.oga.AdresClient;
import nl.lakedigital.djfc.client.polisadministratie.PolisClient;
import nl.lakedigital.djfc.commons.json.Identificatie;
import nl.lakedigital.djfc.commons.json.JsonAdres;
import nl.lakedigital.djfc.commons.json.JsonPolis;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

@Service
public class RelatieService {
    @Inject
    private IdentificatieClient identificatieClient;
    @Inject
    private GebruikerService gebruikerService;
    @Inject
    private PolisClient polisClient;
    @Inject
    private AdresClient adresClient;

    public Relatie zoekRelatie(String identificatieCode) {
        Identificatie identificatie = identificatieClient.zoekIdentificatieCode(identificatieCode);

        Long relatieId = null;

        switch (identificatie.getSoortEntiteit()) {
            case "RELATIE":
                relatieId = identificatie.getEntiteitId();
                break;
            case "POLIS":
                relatieId = pakRelatieBijPolis(identificatie.getEntiteitId());
                break;
            case "ADRES":
                relatieId = pakRelatieBijAdres(identificatie.getEntiteitId());
                break;
        }

        return (Relatie) gebruikerService.lees(relatieId);
    }

    private Long pakRelatieBijPolis(Long polisId) {
        JsonPolis polis = polisClient.lees(String.valueOf(polisId));

        return polis.getEntiteitId();
    }

    private Long pakRelatieBijAdres(Long adresId) {
        JsonAdres adres = adresClient.lees(adresId);

        return adres.getEntiteitId();
    }
}
