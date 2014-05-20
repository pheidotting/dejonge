package nl.dias.domein.json;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class JsonPolis {
    private Long id;
    private String polisNummer;
    private Date ingangsDatum;
    private String premie;
    private Date wijzigingsDatum;
    private Date prolongatieDatum;
    private String betaalfrequentie;
    private List<JsonOpmerking> opmerkingen;
    private String maatschappij;
    private String soort;
    private List<JsonBijlage> bijlages;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPolisNummer() {
        return polisNummer;
    }

    public void setPolisNummer(String polisNummer) {
        this.polisNummer = polisNummer;
    }

    public Date getIngangsDatum() {
        return ingangsDatum;
    }

    public void setIngangsDatum(Date ingangsDatum) {
        this.ingangsDatum = ingangsDatum;
    }

    public String getPremie() {
        return premie;
    }

    public void setPremie(String premie) {
        this.premie = premie;
    }

    public Date getWijzigingsDatum() {
        return wijzigingsDatum;
    }

    public void setWijzigingsDatum(Date wijzigingsDatum) {
        this.wijzigingsDatum = wijzigingsDatum;
    }

    public Date getProlongatieDatum() {
        return prolongatieDatum;
    }

    public void setProlongatieDatum(Date prolongatieDatum) {
        this.prolongatieDatum = prolongatieDatum;
    }

    public String getBetaalfrequentie() {
        return betaalfrequentie;
    }

    public void setBetaalfrequentie(String betaalfrequentie) {
        this.betaalfrequentie = betaalfrequentie;
    }

    public List<JsonOpmerking> getOpmerkingen() {
        return opmerkingen;
    }

    public void setOpmerkingen(List<JsonOpmerking> opmerkingen) {
        this.opmerkingen = opmerkingen;
    }

    public String getMaatschappij() {
        return maatschappij;
    }

    public void setMaatschappij(String maatschappij) {
        this.maatschappij = maatschappij;
    }

    public String getSoort() {
        return soort;
    }

    public void setSoort(String soort) {
        this.soort = soort;
    }

    public List<JsonBijlage> getBijlages() {
        if (bijlages == null) {
            bijlages = new ArrayList<>();
        }
        return bijlages;
    }

    public void setBijlages(List<JsonBijlage> bijlages) {
        this.bijlages = bijlages;
    }
}
