package nl.dias.domein.json;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

public class Inloggen {
    private String identificatie;
    private String wachtwoord;
    private boolean onthouden;

    public String getIdentificatie() {
        return identificatie;
    }

    public void setIdentificatie(String identificatie) {
        this.identificatie = identificatie;
    }

    public String getWachtwoord() {
        return wachtwoord;
    }

    public void setWachtwoord(String wachtwoord) {
        this.wachtwoord = wachtwoord;
    }

    public boolean isOnthouden() {
        return onthouden;
    }

    public void setOnthouden(boolean onthouden) {
        this.onthouden = onthouden;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Inloggen)) {
            return false;
        }
        Inloggen rhs = (Inloggen) object;
        return new EqualsBuilder().append(this.wachtwoord, rhs.wachtwoord).append(this.identificatie, rhs.identificatie).append(this.onthouden, rhs.onthouden).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(this.wachtwoord).append(this.identificatie).append(this.onthouden).toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("wachtwoord", this.wachtwoord).append("identificatie", this.identificatie).append("onthouden", this.onthouden).toString();
    }
}
