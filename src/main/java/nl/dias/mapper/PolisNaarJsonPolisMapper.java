package nl.dias.mapper;

import nl.dias.domein.json.JsonPolis;
import nl.dias.domein.polis.Polis;

public class PolisNaarJsonPolisMapper extends AbstractMapper<Polis, JsonPolis> {
    @Override
    public JsonPolis map(Polis polis, Object parent) {
        JsonPolis jsonPolis = new JsonPolis();

        jsonPolis.setId(polis.getId());
        // polissen die al in het systeem staan hoeven net per se een status te
        // hebben
        if (polis.getStatus() != null) {
            jsonPolis.setStatus(polis.getStatus().getOmschrijving());
        }
        jsonPolis.setPolisNummer(polis.getPolisNummer());
        jsonPolis.setKenmerk(polis.getKenmerk());
        if (polis.getPremie() != null) {
            jsonPolis.setPremie(zetBedragOm(polis.getPremie()));
        }
        if (polis.getIngangsDatum() != null) {
            jsonPolis.setIngangsDatum(polis.getIngangsDatum().toString("yyyy-MM-dd"));
        }
        if (polis.getEindDatum() != null) {
            jsonPolis.setEindDatum(polis.getEindDatum().toString("yyyy-MM-dd"));
        }
        if (polis.getWijzigingsDatum() != null) {
            jsonPolis.setWijzigingsDatum(polis.getWijzigingsDatum().toString("yyyy-MM-dd"));
        }
        if (polis.getProlongatieDatum() != null) {
            jsonPolis.setProlongatieDatum(polis.getProlongatieDatum().toString("yyyy-MM-dd"));
        }
        if (polis.getBetaalfrequentie() != null) {
            jsonPolis.setBetaalfrequentie(polis.getBetaalfrequentie().getOmschrijving());
        }
        jsonPolis.setDekking(polis.getDekking());
        jsonPolis.setVerzekerdeZaak(polis.getVerzekerdeZaak());
        if (polis.getMaatschappij() != null) {
            jsonPolis.setMaatschappij(polis.getMaatschappij().toString());
        }
        jsonPolis.setSoort(polis.getClass().getSimpleName().replace("Verzekering", ""));
        if (polis.getBedrijf() != null) {
            jsonPolis.setBedrijf(polis.getBedrijf().getNaam());
            jsonPolis.setBedrijfsId(polis.getBedrijf().getId());
        }
        //        jsonPolis.setSchades(schadeMapper.mapAllNaarJson(polis.getSchades()));
        if (polis.getRelatie() != null) {
            jsonPolis.setRelatie(polis.getRelatie().getId().toString());
        }
        jsonPolis.setOmschrijvingVerzekering(polis.getOmschrijvingVerzekering());

        return jsonPolis;
    }

    @Override
    boolean isVoorMij(Object object) {
        return object instanceof Polis;
    }
}