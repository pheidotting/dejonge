package nl.dias.domein.json;

public class JsonBijlage {
    private String id;
    private String bestandsNaam;
    private String soortBijlage;
    private String url;

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
}
