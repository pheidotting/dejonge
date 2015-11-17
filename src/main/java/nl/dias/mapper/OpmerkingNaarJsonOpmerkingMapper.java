package nl.dias.mapper;

import nl.dias.domein.Opmerking;
import nl.dias.domein.json.JsonOpmerking;
import org.springframework.stereotype.Component;

@Component
public class OpmerkingNaarJsonOpmerkingMapper extends  AbstractMapper<Opmerking,JsonOpmerking>{
    @Override
    public JsonOpmerking map(Opmerking opmerking) {
        JsonOpmerking jsonOpmerking = new JsonOpmerking();

        jsonOpmerking.setId(opmerking.getId());
        jsonOpmerking.setOpmerking(opmerking.getOpmerking());
        jsonOpmerking.setTijd(opmerking.getTijd().toString("dd-MM-yyyy HH:mm"));
        jsonOpmerking.setMedewerker(opmerking.getMedewerker().getNaam());
        jsonOpmerking.setMedewerkerId(opmerking.getMedewerker().getId().toString());
        if (opmerking.getSchade() != null) {
            jsonOpmerking.setSchade(opmerking.getSchade().getId().toString());
        }
        if (opmerking.getPolis() != null) {
            jsonOpmerking.setPolis(opmerking.getPolis().getId().toString());
        }
        if (opmerking.getHypotheek() != null) {
            jsonOpmerking.setHypotheek(opmerking.getHypotheek().getId().toString());
        }
        if (opmerking.getBedrijf() != null) {
            jsonOpmerking.setBedrijf(opmerking.getBedrijf().getId().toString());
        }
        if (jsonOpmerking.getAangifte() != null) {
            jsonOpmerking.setAangifte(opmerking.getAangifte().getId().toString());
        }

        return jsonOpmerking;
    }

    @Override
    boolean isVoorMij(Object object) {
        return object instanceof  Opmerking;
    }
}
