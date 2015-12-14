package nl.dias.domein.json;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class JsonFoutmelding {
    private String foutmelding;

    public JsonFoutmelding(String foutmelding) {
        this.foutmelding = foutmelding;
    }

    public JsonFoutmelding() {
    }

    public String getFoutmelding() {
        return foutmelding;
    }

    public void setFoutmelding(String foutmelding) {
        this.foutmelding = foutmelding;
    }
}
