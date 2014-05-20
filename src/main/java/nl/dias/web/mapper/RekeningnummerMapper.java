package nl.dias.web.mapper;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.inject.Named;

import nl.dias.domein.RekeningNummer;
import nl.dias.domein.json.JsonRekeningNummer;

@Named
public class RekeningnummerMapper implements Mapper<RekeningNummer, JsonRekeningNummer> {
    @Override
    public RekeningNummer mapVanJson(JsonRekeningNummer jsonRekeningNummer) {
        RekeningNummer rekeningNummer = new RekeningNummer();

        rekeningNummer.setId(jsonRekeningNummer.getId());
        rekeningNummer.setBic(jsonRekeningNummer.getBic());
        rekeningNummer.setRekeningnummer(jsonRekeningNummer.getRekeningnummer());

        return rekeningNummer;
    }

    @Override
    public Set<RekeningNummer> mapAllVanJson(List<JsonRekeningNummer> jsonRekeningNummers) {
        Set<RekeningNummer> ret = new HashSet<>();
        for (JsonRekeningNummer json : jsonRekeningNummers) {
            ret.add(mapVanJson(json));
        }
        return ret;
    }

    @Override
    public JsonRekeningNummer mapNaarJson(RekeningNummer rekeningNummer) {
        JsonRekeningNummer jsonRekeningNummer = new JsonRekeningNummer();

        jsonRekeningNummer.setBic(rekeningNummer.getBic());
        jsonRekeningNummer.setId(rekeningNummer.getId());
        jsonRekeningNummer.setRekeningnummer(rekeningNummer.getRekeningnummer());

        return jsonRekeningNummer;
    }

    @Override
    public List<JsonRekeningNummer> mapAllNaarJson(Set<RekeningNummer> rekeningNummers) {
        List<JsonRekeningNummer> ret = new ArrayList<>();
        for (RekeningNummer rekeningNummer : rekeningNummers) {
            ret.add(mapNaarJson(rekeningNummer));
        }
        return ret;
    }
}
