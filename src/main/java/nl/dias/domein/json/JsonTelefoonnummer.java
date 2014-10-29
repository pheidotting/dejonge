package nl.dias.domein.json;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class JsonTelefoonnummer implements Serializable {
    private static final long serialVersionUID = 3624291960507458499L;

    private Long id;
    private String telefoonnummer;
    private String soort;
    private List<String> errors;

    public JsonTelefoonnummer() {
    }

    public JsonTelefoonnummer(Long id, String telefoonnummer, String soort) {
        super();
        this.id = id;
        this.telefoonnummer = telefoonnummer;
        this.soort = soort;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTelefoonnummer() {
        return telefoonnummer;
    }

    public void setTelefoonnummer(String telefoonnummer) {
        this.telefoonnummer = telefoonnummer;
    }

    public String getSoort() {
        return soort;
    }

    public void setSoort(String soort) {
        this.soort = soort;
    }

    public List<String> getErrors() {
        return errors;
    }

    public void setErrors(List<String> errors) {
        this.errors = errors;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(id).append(telefoonnummer).toHashCode();
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
        JsonTelefoonnummer other = (JsonTelefoonnummer) obj;

        return new EqualsBuilder().append(id, other.id).append(soort, other.soort).append(telefoonnummer, other.telefoonnummer).isEquals();
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Telefoonnummer [id=");
        builder.append(id);
        builder.append(", telefoonnummer=");
        builder.append(telefoonnummer);
        builder.append(", soort=");
        builder.append(soort);
        builder.append("]");
        return builder.toString();
    }
}