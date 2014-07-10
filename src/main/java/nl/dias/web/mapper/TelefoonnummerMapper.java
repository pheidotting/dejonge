package nl.dias.web.mapper;

import javax.inject.Named;

import nl.dias.domein.Telefoonnummer;
import nl.dias.domein.TelefoonnummerSoort;
import nl.dias.domein.json.JsonTelefoonnummer;

@Named
public class TelefoonnummerMapper extends Mapper<Telefoonnummer, JsonTelefoonnummer> {
    @Override
    public Telefoonnummer mapVanJson(JsonTelefoonnummer jsonTelefoonnummer) {
        Telefoonnummer telefoonnummer = new Telefoonnummer();

        telefoonnummer.setId(jsonTelefoonnummer.getId());
        telefoonnummer.setTelefoonnummer(jsonTelefoonnummer.getTelefoonnummer());
        telefoonnummer.setSoort(TelefoonnummerSoort.valueOf(jsonTelefoonnummer.getSoort().toUpperCase()));

        return telefoonnummer;
    }

    @Override
    public JsonTelefoonnummer mapNaarJson(Telefoonnummer telefoonnummer) {
        JsonTelefoonnummer jsonTelefoonnummer = new JsonTelefoonnummer();

        jsonTelefoonnummer.setId(telefoonnummer.getId());
        jsonTelefoonnummer.setTelefoonnummer(telefoonnummer.getTelefoonnummer());
        jsonTelefoonnummer.setSoort(telefoonnummer.getSoort().toString());

        return jsonTelefoonnummer;
    }
}
