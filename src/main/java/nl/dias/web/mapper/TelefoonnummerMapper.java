package nl.dias.web.mapper;

import nl.dias.domein.Telefoonnummer;
import nl.dias.domein.TelefoonnummerSoort;
import nl.dias.web.SoortEntiteit;
import nl.lakedigital.djfc.commons.json.JsonTelefoonnummer;
import org.springframework.stereotype.Component;

@Component
public class TelefoonnummerMapper extends Mapper<Telefoonnummer, JsonTelefoonnummer> {
    @Override
    public Telefoonnummer mapVanJson(JsonTelefoonnummer jsonTelefoonnummer) {
        Telefoonnummer telefoonnummer = new Telefoonnummer();

        telefoonnummer.setId(jsonTelefoonnummer.getId());
        telefoonnummer.setTelefoonnummer(jsonTelefoonnummer.getTelefoonnummer().replace(" ", ""));
        telefoonnummer.setSoort(TelefoonnummerSoort.valueOf(jsonTelefoonnummer.getSoort().toUpperCase()));
        telefoonnummer.setOmschrijving(jsonTelefoonnummer.getOmschrijving());
        telefoonnummer.setEntiteitId(jsonTelefoonnummer.getEntiteitId());
        telefoonnummer.setSoortEntiteit(SoortEntiteit.valueOf(jsonTelefoonnummer.getSoortEntiteit()));

        return telefoonnummer;
    }

    @Override
    public JsonTelefoonnummer mapNaarJson(Telefoonnummer telefoonnummer) {
        JsonTelefoonnummer jsonTelefoonnummer = new JsonTelefoonnummer();

        jsonTelefoonnummer.setId(telefoonnummer.getId());
        jsonTelefoonnummer.setTelefoonnummer(telefoonnummer.getTelefoonnummer());
        jsonTelefoonnummer.setSoort(telefoonnummer.getSoort().toString());
        jsonTelefoonnummer.setOmschrijving(telefoonnummer.getOmschrijving());

        return jsonTelefoonnummer;
    }
}
