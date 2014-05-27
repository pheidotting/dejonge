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

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((jsonRelaties == null) ? 0 : jsonRelaties.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        JsonLijstRelaties other = (JsonLijstRelaties) obj;
        if (jsonRelaties == null) {
            if (other.jsonRelaties != null) {
                return false;
            }
        } else if (!jsonRelaties.equals(other.jsonRelaties)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("JsonLijstRelaties [jsonRelaties=");
        builder.append(jsonRelaties);
        builder.append("]");
        return builder.toString();
    }
}
