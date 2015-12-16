package nl.dias.service;

import nl.dias.domein.json.JsonAdres;
import org.springframework.stereotype.Service;

@Service
public class PostcodeService {

    public JsonAdres extraHeerAdres(String apiAntwoord) {
        JsonAdres jsonAdres = new JsonAdres();

        int index = apiAntwoord.indexOf("city");
        if (index > -1) {
            String plaats = apiAntwoord.substring(index);
            index = plaats.indexOf("label");
            if (index > -1) {
                plaats = plaats.substring(index + 8);
                index = plaats.indexOf("\"");
                if (index > -1) {
                    plaats = plaats.substring(0, index);
                    jsonAdres.setPlaats(plaats);

                    String straat = apiAntwoord.substring(apiAntwoord.indexOf("street") + 9);
                    index = straat.indexOf("\"");
                    if (index > -1) {
                        straat = straat.substring(0, index);
                        jsonAdres.setStraat(straat);
                    }
                }
            }
        }

        return jsonAdres;
    }
}
