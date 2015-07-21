package nl.dias.domein.json;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

public class JsonBijlage implements Comparable<JsonBijlage> {
    private String id;
    private String bestandsNaam;
    private String soortBijlage;
    private String url;
    private String parentId;
    private String tonen;
    private String omschrijving;
    private String datumUpload;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBestandsNaam() {
        return bestandsNaam;
    }

    public void setBestandsNaam(String bestandsNaam) {
        this.bestandsNaam = bestandsNaam;
    }

    public String getSoortBijlage() {
        return soortBijlage;
    }

    public void setSoortBijlage(String soortBijlage) {
        this.soortBijlage = soortBijlage;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getTonen() {
        return tonen;
    }

    public void setTonen(String tonen) {
        this.tonen = tonen;
    }

    public String getOmschrijving() {
        return omschrijving;
    }

    public void setOmschrijving(String omschrijving) {
        this.omschrijving = omschrijving;
    }

    public String getDatumUpload() {
        return datumUpload;
    }

    public void setDatumUpload(String datumUpload) {
        this.datumUpload = datumUpload;
    }

    /**
     * @see java.lang.Object#equals(Object)
     */
    @Override
    public boolean equals(Object object) {
        if (!(object instanceof JsonBijlage)) {
            return false;
        }
        JsonBijlage rhs = (JsonBijlage) object;
        return new EqualsBuilder().append(this.bestandsNaam, rhs.bestandsNaam).append(this.id, rhs.id).append(this.soortBijlage, rhs.soortBijlage).append(this.url, rhs.url)
                .append(this.parentId, rhs.parentId).isEquals();
    }

    /**
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(this.bestandsNaam).append(this.id).append(this.soortBijlage).append(this.url).append(this.parentId).toHashCode();
    }

    /**
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return new ToStringBuilder(this).append("soortBijlage", this.soortBijlage).append("url", this.url).append("bestandsNaam", this.bestandsNaam).append("id", this.id)
                .append("parentId", this.parentId).toString();
    }

    @Override
    public int compareTo(JsonBijlage o) {
        if (this.soortBijlage == o.soortBijlage) {
            return this.bestandsNaam.compareTo(o.bestandsNaam);
        } else {
            return 0;
        }
    }
}
