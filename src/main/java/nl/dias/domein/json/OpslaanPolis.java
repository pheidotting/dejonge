package nl.dias.domein.json;

public class OpslaanPolis {
    private Long relatie;
    private Double premie;
    private Long maatschappij;
    private String soortVerzekering;
    private String polisNummer;
    private String ingangsDatumString;
    private String wijzigingsdatumString;
    private String prolongatiedatumString;
    private String betaalfrequentie;

    public Long getRelatie() {
        return relatie;
    }

    public void setRelatie(Long relatie) {
        this.relatie = relatie;
    }

    public Double getPremie() {
        return premie;
    }

    public void setPremie(Double premie) {
        this.premie = premie;
    }

    public Long getMaatschappij() {
        return maatschappij;
    }

    public void setMaatschappij(Long maatschappij) {
        this.maatschappij = maatschappij;
    }

    public String getSoortVerzekering() {
        return soortVerzekering;
    }

    public void setSoortVerzekering(String soortVerzekering) {
        this.soortVerzekering = soortVerzekering;
    }

    public String getPolisNummer() {
        return polisNummer;
    }

    public void setPolisNummer(String polisNummer) {
        this.polisNummer = polisNummer;
    }

    public String getIngangsDatumString() {
        return ingangsDatumString;
    }

    public void setIngangsDatumString(String ingangsDatumString) {
        this.ingangsDatumString = ingangsDatumString;
    }

    public String getWijzigingsdatumString() {
        return wijzigingsdatumString;
    }

    public void setWijzigingsdatumString(String wijzigingsdatumString) {
        this.wijzigingsdatumString = wijzigingsdatumString;
    }

    public String getProlongatiedatumString() {
        return prolongatiedatumString;
    }

    public void setProlongatiedatumString(String prolongatiedatumString) {
        this.prolongatiedatumString = prolongatiedatumString;
    }

    public String getBetaalfrequentie() {
        return betaalfrequentie;
    }

    public void setBetaalfrequentie(String betaalfrequentie) {
        this.betaalfrequentie = betaalfrequentie;
    }
}