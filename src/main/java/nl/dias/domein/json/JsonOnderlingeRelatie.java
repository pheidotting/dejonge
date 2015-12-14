package nl.dias.domein.json;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class JsonOnderlingeRelatie {
    private Long idRelatieMet;
    private String relatieMet;
    private String soortRelatie;

    public Long getIdRelatieMet() {
        return idRelatieMet;
    }

    public void setIdRelatieMet(Long idRelatieMet) {
        this.idRelatieMet = idRelatieMet;
    }

    public String getRelatieMet() {
        return relatieMet;
    }

    public void setRelatieMet(String relatieMet) {
        this.relatieMet = relatieMet;
    }

    public String getSoortRelatie() {
        return soortRelatie;
    }

    public void setSoortRelatie(String soortRelatie) {
        this.soortRelatie = soortRelatie;
    }
}
