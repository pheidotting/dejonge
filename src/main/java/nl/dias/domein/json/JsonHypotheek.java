package nl.dias.domein.json;

import java.util.List;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

public class JsonHypotheek {
    private Long id;
    private Long relatie;
    private String hypotheekVorm;
    private String omschrijving;
    private String hypotheekBedrag;
    private String rente;
    private String marktWaarde;
    private String onderpand;
    private String koopsom;
    private String vrijeVerkoopWaarde;
    private String taxatieDatum;
    private String wozWaarde;
    private String waardeVoorVerbouwing;
    private String waardeNaVerbouwing;
    private String ingangsDatum;
    private String eindDatum;
    private Long duur;
    private String ingangsDatumRenteVastePeriode;
    private String eindDatumRenteVastePeriode;
    private Long duurRenteVastePeriode;
    private List<String> errors;
    private List<JsonOpmerking> opmerkingen;
    private List<JsonBijlage> bijlages;
    // moet vanuit de schermkant
    private String idDiv;
    private String idDivLink;
    private String className;
    private String titel;
    private List<String> soortenHypotheek;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getRelatie() {
        return relatie;
    }

    public void setRelatie(Long relatie) {
        this.relatie = relatie;
    }

    public String getHypotheekVorm() {
        return hypotheekVorm;
    }

    public void setHypotheekVorm(String hypotheekVorm) {
        this.hypotheekVorm = hypotheekVorm;
    }

    public String getOmschrijving() {
        return omschrijving;
    }

    public void setOmschrijving(String omschrijving) {
        this.omschrijving = omschrijving;
    }

    public String getHypotheekBedrag() {
        return hypotheekBedrag;
    }

    public void setHypotheekBedrag(String hypotheekBedrag) {
        this.hypotheekBedrag = hypotheekBedrag;
    }

    public String getRente() {
        return rente;
    }

    public void setRente(String rente) {
        this.rente = rente;
    }

    public String getMarktWaarde() {
        return marktWaarde;
    }

    public void setMarktWaarde(String marktWaarde) {
        this.marktWaarde = marktWaarde;
    }

    public String getOnderpand() {
        return onderpand;
    }

    public void setOnderpand(String onderpand) {
        this.onderpand = onderpand;
    }

    public String getKoopsom() {
        return koopsom;
    }

    public void setKoopsom(String koopsom) {
        this.koopsom = koopsom;
    }

    public String getVrijeVerkoopWaarde() {
        return vrijeVerkoopWaarde;
    }

    public void setVrijeVerkoopWaarde(String vrijeVerkoopWaarde) {
        this.vrijeVerkoopWaarde = vrijeVerkoopWaarde;
    }

    public String getTaxatieDatum() {
        return taxatieDatum;
    }

    public void setTaxatieDatum(String taxatieDatum) {
        this.taxatieDatum = taxatieDatum;
    }

    public String getWozWaarde() {
        return wozWaarde;
    }

    public void setWozWaarde(String wozWaarde) {
        this.wozWaarde = wozWaarde;
    }

    public String getWaardeVoorVerbouwing() {
        return waardeVoorVerbouwing;
    }

    public void setWaardeVoorVerbouwing(String waardeVoorVerbouwing) {
        this.waardeVoorVerbouwing = waardeVoorVerbouwing;
    }

    public String getWaardeNaVerbouwing() {
        return waardeNaVerbouwing;
    }

    public void setWaardeNaVerbouwing(String waardeNaVerbouwing) {
        this.waardeNaVerbouwing = waardeNaVerbouwing;
    }

    public String getIngangsDatum() {
        return ingangsDatum;
    }

    public void setIngangsDatum(String ingangsDatum) {
        this.ingangsDatum = ingangsDatum;
    }

    public String getEindDatum() {
        return eindDatum;
    }

    public void setEindDatum(String eindDatum) {
        this.eindDatum = eindDatum;
    }

    public Long getDuur() {
        return duur;
    }

    public void setDuur(Long duur) {
        this.duur = duur;
    }

    public String getIngangsDatumRenteVastePeriode() {
        return ingangsDatumRenteVastePeriode;
    }

    public void setIngangsDatumRenteVastePeriode(String ingangsDatumRenteVastePeriode) {
        this.ingangsDatumRenteVastePeriode = ingangsDatumRenteVastePeriode;
    }

    public String getEindDatumRenteVastePeriode() {
        return eindDatumRenteVastePeriode;
    }

    public void setEindDatumRenteVastePeriode(String eindDatumRenteVastePeriode) {
        this.eindDatumRenteVastePeriode = eindDatumRenteVastePeriode;
    }

    public Long getDuurRenteVastePeriode() {
        return duurRenteVastePeriode;
    }

    public void setDuurRenteVastePeriode(Long duurRenteVastePeriode) {
        this.duurRenteVastePeriode = duurRenteVastePeriode;
    }

    public List<String> getErrors() {
        return errors;
    }

    public void setErrors(List<String> errors) {
        this.errors = errors;
    }

    public List<JsonOpmerking> getOpmerkingen() {
        return opmerkingen;
    }

    public void setOpmerkingen(List<JsonOpmerking> opmerkingen) {
        this.opmerkingen = opmerkingen;
    }

    public List<JsonBijlage> getBijlages() {
        return bijlages;
    }

    public void setBijlages(List<JsonBijlage> bijlages) {
        this.bijlages = bijlages;
    }

    public String getIdDiv() {
        return idDiv;
    }

    public void setIdDiv(String idDiv) {
        this.idDiv = idDiv;
    }

    public String getIdDivLink() {
        return idDivLink;
    }

    public void setIdDivLink(String idDivLink) {
        this.idDivLink = idDivLink;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getTitel() {
        return titel;
    }

    public void setTitel(String titel) {
        this.titel = titel;
    }

    public List<String> getSoortenHypotheek() {
        return soortenHypotheek;
    }

    public void setSoortenHypotheek(List<String> soortenHypotheek) {
        this.soortenHypotheek = soortenHypotheek;
    }

    /**
     * @see java.lang.Object#equals(Object)
     */
    @Override
    public boolean equals(Object object) {
        if (!(object instanceof JsonHypotheek)) {
            return false;
        }
        JsonHypotheek rhs = (JsonHypotheek) object;
        return new EqualsBuilder().append(this.taxatieDatum, rhs.taxatieDatum).append(this.eindDatum, rhs.eindDatum).append(this.koopsom, rhs.koopsom).append(this.relatie, rhs.relatie)
                .append(this.hypotheekBedrag, rhs.hypotheekBedrag).append(this.rente, rhs.rente).append(this.hypotheekVorm, rhs.hypotheekVorm).append(this.duur, rhs.duur)
                .append(this.waardeVoorVerbouwing, rhs.waardeVoorVerbouwing).append(this.id, rhs.id).append(this.onderpand, rhs.onderpand).append(this.errors, rhs.errors)
                .append(this.duurRenteVastePeriode, rhs.duurRenteVastePeriode).append(this.vrijeVerkoopWaarde, rhs.vrijeVerkoopWaarde).append(this.ingangsDatum, rhs.ingangsDatum)
                .append(this.ingangsDatumRenteVastePeriode, rhs.ingangsDatumRenteVastePeriode).append(this.waardeNaVerbouwing, rhs.waardeNaVerbouwing).append(this.marktWaarde, rhs.marktWaarde)
                .append(this.wozWaarde, rhs.wozWaarde).append(this.omschrijving, rhs.omschrijving).append(this.eindDatumRenteVastePeriode, rhs.eindDatumRenteVastePeriode).isEquals();
    }

    /**
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(this.taxatieDatum).append(this.eindDatum).append(this.koopsom).append(this.relatie).append(this.hypotheekBedrag).append(this.rente)
                .append(this.hypotheekVorm).append(this.duur).append(this.waardeVoorVerbouwing).append(this.id).append(this.onderpand).append(this.errors).append(this.duurRenteVastePeriode)
                .append(this.vrijeVerkoopWaarde).append(this.ingangsDatum).append(this.ingangsDatumRenteVastePeriode).append(this.waardeNaVerbouwing).append(this.marktWaarde).append(this.wozWaarde)
                .append(this.omschrijving).append(this.eindDatumRenteVastePeriode).toHashCode();
    }

    /**
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return new ToStringBuilder(this).append("taxatieDatum", this.taxatieDatum).append("eindDatum", this.eindDatum).append("koopsom", this.koopsom).append("relatie", this.relatie)
                .append("hypotheekBedrag", this.hypotheekBedrag).append("rente", this.rente).append("hypotheekVorm", this.hypotheekVorm).append("duur", this.duur)
                .append("waardeVoorVerbouwing", this.waardeVoorVerbouwing).append("id", this.id).append("onderpand", this.onderpand).append("errors", this.errors)
                .append("duurRenteVastePeriode", this.duurRenteVastePeriode).append("vrijeVerkoopWaarde", this.vrijeVerkoopWaarde).append("ingangsDatum", this.ingangsDatum)
                .append("ingangsDatumRenteVastePeriode", this.ingangsDatumRenteVastePeriode).append("waardeNaVerbouwing", this.waardeNaVerbouwing).append("marktWaarde", this.marktWaarde)
                .append("wozWaarde", this.wozWaarde).append("omschrijving", this.omschrijving).append("eindDatumRenteVastePeriode", this.eindDatumRenteVastePeriode).toString();
    }

}
