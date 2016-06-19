package nl.dias.web.mapper;

import nl.dias.domein.Medewerker;
import nl.dias.domein.Opmerking;
import nl.dias.service.GebruikerService;
import nl.dias.service.OpmerkingService;
import nl.dias.web.SoortEntiteit;
import nl.lakedigital.djfc.commons.json.JsonOpmerking;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.inject.Inject;

@Component
public class OpmerkingMapper extends Mapper<Opmerking, JsonOpmerking> {
    private static final Logger LOGGER = LoggerFactory.getLogger(OpmerkingMapper.class);

    @Inject
    private GebruikerService gebruikerService;
    @Inject
    private OpmerkingService opmerkingService;

    @Override
    public Opmerking mapVanJson(JsonOpmerking jsonOpmerking) {
        Opmerking opmerking = new Opmerking();

        if (jsonOpmerking.getId() != null) {
            opmerking = opmerkingService.lees(jsonOpmerking.getId());
        }

        opmerking.setId(jsonOpmerking.getId());
        opmerking.setOpmerking(jsonOpmerking.getOpmerking());
        opmerking.setEntiteitId(jsonOpmerking.getEntiteitId());
        if (jsonOpmerking.getSoortEntiteit() != null) {
            opmerking.setSoortEntiteit(SoortEntiteit.valueOf(jsonOpmerking.getSoortEntiteit()));
        }
        opmerking.setMedewerker((Medewerker) gebruikerService.lees(Long.valueOf(jsonOpmerking.getMedewerkerId())));

        LOGGER.debug("gemapte opmerking {}", opmerking);

        return opmerking;
    }

    @Override
    public JsonOpmerking mapNaarJson(Opmerking opmerking) {
        JsonOpmerking jsonOpmerking = new JsonOpmerking();

        jsonOpmerking.setId(opmerking.getId());
        jsonOpmerking.setOpmerking(opmerking.getOpmerking());
        jsonOpmerking.setTijd(opmerking.getTijd().toString("dd-MM-yyyy HH:mm"));
        jsonOpmerking.setMedewerker(opmerking.getMedewerker().getNaam());
        jsonOpmerking.setMedewerkerId(opmerking.getMedewerker().getId());
        jsonOpmerking.setEntiteitId(opmerking.getEntiteitId());

        return jsonOpmerking;
    }

}
