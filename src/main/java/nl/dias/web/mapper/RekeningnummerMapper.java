package nl.dias.web.mapper;

import javax.inject.Named;

import nl.dias.domein.RekeningNummer;
import nl.dias.domein.json.JsonRekeningNummer;

@Named
public class RekeningnummerMapper extends Mapper<RekeningNummer, JsonRekeningNummer> {
    @Override
    public RekeningNummer mapVanJson(JsonRekeningNummer jsonRekeningNummer) {
        RekeningNummer rekeningNummer = new RekeningNummer();

        rekeningNummer.setId(jsonRekeningNummer.getId());
        rekeningNummer.setBic(jsonRekeningNummer.getBic());
        rekeningNummer.setRekeningnummer(jsonRekeningNummer.getRekeningnummer());

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
