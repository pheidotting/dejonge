package nl.dias.domein.json;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.io.Serializable;

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public final class WijzigenOmschrijvingBijlage implements Serializable {
    private Long bijlageId;
    private String nieuweOmschrijving;

    public Long getBijlageId() {
        return bijlageId;
    }

    public void setBijlageId(Long bijlageId) {
        this.bijlageId = bijlageId;
    }

    public String getNieuweOmschrijving() {
        return nieuweOmschrijving;
    }

    public void setNieuweOmschrijving(String nieuweOmschrijving) {
        this.nieuweOmschrijving = nieuweOmschrijving;
    }
}
