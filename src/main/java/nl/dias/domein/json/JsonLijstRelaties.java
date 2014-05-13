package nl.dias.domein.json;

import java.util.ArrayList;
import java.util.List;

public class JsonLijstRelaties {
    private List<JsonRelatie> jsonRelaties;

    public List<JsonRelatie> getJsonRelaties() {
        if (jsonRelaties == null) {
            jsonRelaties = new ArrayList<>();
        }
        return jsonRelaties;
    }

    public void setJsonRelaties(List<JsonRelatie> jsonRelaties) {
        this.jsonRelaties = jsonRelaties;
    }
}
