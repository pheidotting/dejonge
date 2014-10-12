package nl.dias.web.mapper;

import javax.inject.Named;

import nl.dias.domein.Bedrag;
import nl.dias.domein.Hypotheek;
import nl.dias.domein.json.JsonHypotheek;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;

import com.sun.jersey.api.core.InjectParam;

@Named
public class HypotheekMapper extends Mapper<Hypotheek, JsonHypotheek> {
    private final static String DATUM_FORMAAT = "dd-MM-yyyy";

    @InjectParam
    private OpmerkingMapper opmerkingMapper;
    @InjectParam
    private BijlageMapper bijlageMapper;

    @Override
    public Hypotheek mapVanJson(JsonHypotheek jsonHypotheek) {
        return mapVanJson(jsonHypotheek, new Hypotheek());
    }

    public Hypotheek mapVanJson(JsonHypotheek jsonHypotheek, Hypotheek hypotheek) {
        String patternDatum = "dd-MM-yyyy";

        LocalDate eindDatum = null;
        if (jsonHypotheek.getEindDatum() != null && !"".equals(jsonHypotheek.getEindDatum())) {
            eindDatum = LocalDate.parse(jsonHypotheek.getEindDatum(), DateTimeFormat.forPattern(patternDatum));
        }
        LocalDate eindDatumRenteVastePeriode = null;
        if (jsonHypotheek.getEindDatumRenteVastePeriode() != null && !"".equals(jsonHypotheek.getEindDatumRenteVastePeriode())) {
            eindDatumRenteVastePeriode = LocalDate.parse(jsonHypotheek.getEindDatumRenteVastePeriode(), DateTimeFormat.forPattern(patternDatum));
        }
        LocalDate ingangsDatum = null;
        if (jsonHypotheek.getIngangsDatum() != null && !"".equals(jsonHypotheek.getIngangsDatum())) {
            ingangsDatum = LocalDate.parse(jsonHypotheek.getIngangsDatum(), DateTimeFormat.forPattern(patternDatum));
        }
        LocalDate ingangsDatumRenteVastePeriode = null;
        if (jsonHypotheek.getIngangsDatumRenteVastePeriode() != null && !"".equals(jsonHypotheek.getIngangsDatumRenteVastePeriode())) {
            ingangsDatumRenteVastePeriode = LocalDate.parse(jsonHypotheek.getIngangsDatumRenteVastePeriode(), DateTimeFormat.forPattern(patternDatum));
        }
        LocalDate taxatieDatum = null;
        if (jsonHypotheek.getTaxatieDatum() != null && !"".equals(jsonHypotheek.getTaxatieDatum())) {
            taxatieDatum = LocalDate.parse(jsonHypotheek.getTaxatieDatum(), DateTimeFormat.forPattern(patternDatum));
        }

        // Hypotheek hypotheek = new Hypotheek();

        hypotheek.setId(jsonHypotheek.getId());
        hypotheek.setDuur(jsonHypotheek.getDuur());
        hypotheek.setDuurRenteVastePeriode(jsonHypotheek.getDuurRenteVastePeriode());
        hypotheek.setEindDatum(eindDatum);
        hypotheek.setEindDatumRenteVastePeriode(eindDatumRenteVastePeriode);
        if (StringUtils.isNotBlank(jsonHypotheek.getHypotheekBedrag())) {
            hypotheek.setHypotheekBedrag(new Bedrag(jsonHypotheek.getHypotheekBedrag()));
        }
        hypotheek.setIngangsDatum(ingangsDatum);
        hypotheek.setIngangsDatumRenteVastePeriode(ingangsDatumRenteVastePeriode);
        if (StringUtils.isNotBlank(jsonHypotheek.getKoopsom())) {
            hypotheek.setKoopsom(new Bedrag(jsonHypotheek.getKoopsom()));
        }
        if (StringUtils.isNotBlank(jsonHypotheek.getMarktWaarde())) {
            hypotheek.setMarktWaarde(new Bedrag(jsonHypotheek.getMarktWaarde()));
        }
        hypotheek.setOmschrijving(jsonHypotheek.getOmschrijving());
        if (StringUtils.isNotBlank(jsonHypotheek.getOnderpand())) {
            hypotheek.setOnderpand(jsonHypotheek.getOnderpand());
        }
        hypotheek.setRente(Integer.valueOf(jsonHypotheek.getRente()));
        hypotheek.setTaxatieDatum(taxatieDatum);
        if (StringUtils.isNotBlank(jsonHypotheek.getVrijeVerkoopWaarde())) {
            hypotheek.setVrijeVerkoopWaarde(new Bedrag(jsonHypotheek.getVrijeVerkoopWaarde()));
        }
        if (StringUtils.isNotBlank(jsonHypotheek.getWaardeNaVerbouwing())) {
            hypotheek.setWaardeNaVerbouwing(new Bedrag(jsonHypotheek.getWaardeNaVerbouwing()));
        }
        if (StringUtils.isNotBlank(jsonHypotheek.getWaardeVoorVerbouwing())) {
            hypotheek.setWaardeVoorVerbouwing(new Bedrag(jsonHypotheek.getWaardeVoorVerbouwing()));
        }
        if (StringUtils.isNotBlank(jsonHypotheek.getWozWaarde())) {
            hypotheek.setWozWaarde(new Bedrag(jsonHypotheek.getWozWaarde()));
        }

        return hypotheek;
    }

    @Override
    public JsonHypotheek mapNaarJson(Hypotheek hypotheek) {
        JsonHypotheek jsonHypotheek = new JsonHypotheek();

        jsonHypotheek.setId(hypotheek.getId());
        jsonHypotheek.setDuur(hypotheek.getDuur());
        jsonHypotheek.setDuurRenteVastePeriode(hypotheek.getDuurRenteVastePeriode());
        jsonHypotheek.setEindDatum(hypotheek.getEindDatum().toString(DATUM_FORMAAT));
        jsonHypotheek.setEindDatumRenteVastePeriode(hypotheek.getEindDatumRenteVastePeriode().toString(DATUM_FORMAAT));
        jsonHypotheek.setHypotheekBedrag(hypotheek.getHypotheekBedrag().getBedrag().toString());
        jsonHypotheek.setHypotheekVorm(hypotheek.getHypotheekVorm().getId().toString());
        jsonHypotheek.setIngangsDatum(hypotheek.getIngangsDatum().toString(DATUM_FORMAAT));
        jsonHypotheek.setIngangsDatumRenteVastePeriode(hypotheek.getIngangsDatumRenteVastePeriode().toString(DATUM_FORMAAT));
        if (hypotheek.getKoopsom() != null) {
            jsonHypotheek.setKoopsom(hypotheek.getKoopsom().getBedrag().toString());
        }
        if (hypotheek.getMarktWaarde() != null) {
            jsonHypotheek.setMarktWaarde(hypotheek.getMarktWaarde().getBedrag().toString());
        }
        jsonHypotheek.setOmschrijving(hypotheek.getOmschrijving());
        if (hypotheek.getOnderpand() != null) {
            jsonHypotheek.setOnderpand(hypotheek.getOnderpand());
        }
        jsonHypotheek.setRelatie(hypotheek.getRelatie().getId());
        jsonHypotheek.setRente(hypotheek.getRente().toString());
        jsonHypotheek.setTaxatieDatum(hypotheek.getTaxatieDatum().toString(DATUM_FORMAAT));
        if (hypotheek.getVrijeVerkoopWaarde() != null) {
            jsonHypotheek.setVrijeVerkoopWaarde(hypotheek.getVrijeVerkoopWaarde().getBedrag().toString());
        }
        if (hypotheek.getWaardeNaVerbouwing() != null) {
            jsonHypotheek.setWaardeNaVerbouwing(hypotheek.getWaardeNaVerbouwing().getBedrag().toString());
        }
        if (hypotheek.getWaardeVoorVerbouwing() != null) {
            jsonHypotheek.setWaardeVoorVerbouwing(hypotheek.getWaardeVoorVerbouwing().getBedrag().toString());
        }
        if (hypotheek.getWozWaarde() != null) {
            jsonHypotheek.setWozWaarde(hypotheek.getWozWaarde().getBedrag().toString());
        }

        jsonHypotheek.setOpmerkingen(opmerkingMapper.mapAllNaarJson(hypotheek.getOpmerkingen()));
        jsonHypotheek.setBijlages(bijlageMapper.mapAllNaarJson(hypotheek.getBijlages()));
        jsonHypotheek.setLeningNummer(hypotheek.getLeningNummer());
        if (hypotheek.getBank() != null) {
            jsonHypotheek.setBank(hypotheek.getBank().getNaam());
        }

        return jsonHypotheek;
    }

    // private JsonGekoppeldeHypotheek bepaalTitel(Hypotheek hypotheek) {
    // JsonGekoppeldeHypotheek gekoppeldeHypotheek = new
    // JsonGekoppeldeHypotheek();
    //
    // gekoppeldeHypotheek.setBank(hypotheek.getBank().getNaam());
    // gekoppeldeHypotheek.setHypotheekBedrag(hypotheek.getHypotheekBedrag().getBedrag().toString());
    // gekoppeldeHypotheek.setHypotheekVorm(hypotheek.getHypotheekVorm().getOmschrijving());
    // gekoppeldeHypotheek.setLeningNummer(hypotheek.getLeningNummer());
    // gekoppeldeHypotheek.setRente(hypotheek.getRente().toString());
    //
    // return gekoppeldeHypotheek;
    // }
    //
    // private List<JsonHypotheek> getGekoppeldAan(Hypotheek hypotheek) {
    // List<JsonHypotheek> gekoppeldAan = new ArrayList<>();
    //
    // gekoppeldAan.add(mapNaarJson(hypotheek));
    // for (Hypotheek h : hypotheek.getGekoppeldAan()) {
    // gekoppeldAan.addAll(getGekoppeldAan(h));
    // }
    //
    // return gekoppeldAan;
    // }

    public void setOpmerkingMapper(OpmerkingMapper opmerkingMapper) {
        this.opmerkingMapper = opmerkingMapper;
    }

    public void setBijlageMapper(BijlageMapper bijlageMapper) {
        this.bijlageMapper = bijlageMapper;
    }
}
