package nl.dias.web.mapper;

import nl.dias.domein.RekeningNummer;
import nl.dias.web.SoortEntiteit;
import nl.lakedigital.djfc.commons.json.JsonRekeningNummer;
import org.springframework.stereotype.Component;

@Component
public class RekeningnummerMapper extends Mapper<RekeningNummer, JsonRekeningNummer> {
    @Override
    public RekeningNummer mapVanJson(JsonRekeningNummer jsonRekeningNummer) {
        RekeningNummer rekeningNummer = new RekeningNummer();

        rekeningNummer.setId(jsonRekeningNummer.getId());
        rekeningNummer.setBic(jsonRekeningNummer.getBic());
        rekeningNummer.setRekeningnummer(jsonRekeningNummer.getRekeningnummer());
        rekeningNummer.setEntiteitId(jsonRekeningNummer.getEntiteitId());
        rekeningNummer.setSoortEntiteit(SoortEntiteit.valueOf(jsonRekeningNummer.getSoortEntiteit()));

        return rekeningNummer;
    }

    @Override
    public JsonRekeningNummer mapNaarJson(RekeningNummer rekeningNummer) {
        JsonRekeningNummer jsonRekeningNummer = new JsonRekeningNummer();

        jsonRekeningNummer.setBic(rekeningNummer.getBic());
        jsonRekeningNummer.setId(rekeningNummer.getId());
        jsonRekeningNummer.setRekeningnummer(rekeningNummer.getRekeningnummer());

        return jsonRekeningNummer;
    }
}
