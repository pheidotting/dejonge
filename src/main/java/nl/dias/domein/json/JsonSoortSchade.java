package nl.dias.domein.json;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class JsonSoortSchade {
    private final String value;

    public JsonSoortSchade(String tekst) {
        this.value = tekst;
    }

    public String getValue() {
        return value;
    }
}
