package nl.dias.mapper;

import nl.dias.domein.Opmerking;
import nl.lakedigital.djfc.commons.json.JsonOpmerking;
import org.springframework.stereotype.Component;

@Component
public class OpmerkingNaarJsonOpmerkingMapper extends  AbstractMapper<Opmerking,JsonOpmerking>{
    @Override
    public JsonOpmerking map(Opmerking opmerking, Object parent, Object bestaandOjbect) {
        JsonOpmerking jsonOpmerking = new JsonOpmerking();

        jsonOpmerking.setId(opmerking.getId());
        jsonOpmerking.setOpmerking(opmerking.getOpmerking());
        jsonOpmerking.setTijd(opmerking.getTijd().toString("dd-MM-yyyy HH:mm"));
        jsonOpmerking.setMedewerker(opmerking.getMedewerker().getNaam());
        jsonOpmerking.setMedewerkerId(opmerking.getMedewerker().getId());
        jsonOpmerking.setEntiteitId(opmerking.getEntiteitId());
        jsonOpmerking.setSoortEntiteit(opmerking.getSoortEntiteit().toString());

        return jsonOpmerking;
    }

    @Override
    boolean isVoorMij(Object object) {
        return object instanceof  Opmerking;
    }
}
