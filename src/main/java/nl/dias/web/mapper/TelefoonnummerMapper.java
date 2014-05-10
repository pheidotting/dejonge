package nl.dias.web.mapper;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.inject.Named;

import nl.dias.domein.Telefoonnummer;
import nl.dias.domein.TelefoonnummerSoort;
import nl.dias.domein.json.JsonTelefoonnummer;

@Named
public class TelefoonnummerMapper {
    public Telefoonnummer mapVanJson(JsonTelefoonnummer jsonTelefoonnummer) {
        Telefoonnummer telefoonnummer = new Telefoonnummer();

        telefoonnummer.setId(jsonTelefoonnummer.getId());
        telefoonnummer.setTelefoonnummer(jsonTelefoonnummer.getTelefoonnummer());
        telefoonnummer.setSoort(TelefoonnummerSoort.valueOf(jsonTelefoonnummer.getSoort()));

        return telefoonnummer;
    }

    public Set<Telefoonnummer> mapAllVanJson(List<JsonTelefoonnummer> jsons) {
        Set<Telefoonnummer> ret = new HashSet<>();
        for (JsonTelefoonnummer json : jsons) {
            ret.add(mapVanJson(json));
        }

        return ret;
    }

    public JsonTelefoonnummer mapNaarJson(Telefoonnummer telefoonnummer) {
        JsonTelefoonnummer jsonTelefoonnummer = new JsonTelefoonnummer();

        jsonTelefoonnummer.setId(telefoonnummer.getId());
        jsonTelefoonnummer.setTelefoonnummer(telefoonnummer.getTelefoonnummer());
        jsonTelefoonnummer.setSoort(telefoonnummer.getSoort().toString());

        return jsonTelefoonnummer;
    }

    public List<JsonTelefoonnummer> mapAllNaarJson(Set<Telefoonnummer> nummers) {
        List<JsonTelefoonnummer> ret = new ArrayList<>();
        for (Telefoonnummer nummer : nummers) {
            ret.add(mapNaarJson(nummer));
        }

        return ret;
    }
}
