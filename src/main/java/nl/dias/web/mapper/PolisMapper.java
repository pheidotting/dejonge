package nl.dias.web.mapper;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.inject.Named;

import nl.dias.domein.Bijlage;
import nl.dias.domein.json.JsonBijlage;
import nl.dias.domein.json.JsonPolis;
import nl.dias.domein.polis.Polis;

import com.sun.jersey.api.core.InjectParam;

@Named
public class PolisMapper implements Mapper<Polis, JsonPolis> {
    @InjectParam
    private OpmerkingMapper opmerkingMapper;

    @Override
    public Polis mapVanJson(JsonPolis jsonPolis) {

        return null;
    }

    @Override
    public Set<Polis> mapAllVanJson(List<JsonPolis> jsonPolissen) {
        Set<Polis> ret = new HashSet<>();
        for (JsonPolis jsonPolis : jsonPolissen) {
            ret.add(mapVanJson(jsonPolis));
        }
        return ret;
    }

    @Override
    public JsonPolis mapNaarJson(Polis polis) {
        JsonPolis jsonPolis = new JsonPolis();

        jsonPolis.setId(polis.getId());
        jsonPolis.setPolisNummer(polis.getPolisNummer());
        // jsonPolis.setIngangsDatum(polis.getIngangsDatum());
        jsonPolis.setPremie(polis.getPremie().toString());
        // jsonPolis.setWijzigingsDatum(polis.getWijzigingsDatum());
        // jsonPolis.setProlongatieDatum(polis.getProlongatieDatum());
        if (polis.getBetaalfrequentie() != null) {
            jsonPolis.setBetaalfrequentie(polis.getBetaalfrequentie().getOmschrijving());
        }
        for (Bijlage bijlage : polis.getBijlages()) {
            JsonBijlage jsonBijlage = new JsonBijlage();
            jsonBijlage.setBestandsNaam(bijlage.getBestandsNaam());
            jsonBijlage.setId(bijlage.getId().toString());
            jsonPolis.getBijlages().add(jsonBijlage);
        }
        jsonPolis.setOpmerkingen(opmerkingMapper.mapAllNaarJson(polis.getOpmerkingen()));
        jsonPolis.setMaatschappij(polis.getMaatschappij().getNaam());
        jsonPolis.setSoort(polis.getClass().getSimpleName());

        return jsonPolis;
    }

    @Override
    public List<JsonPolis> mapAllNaarJson(Set<Polis> polissen) {
        List<JsonPolis> ret = new ArrayList<>();
        for (Polis polis : polissen) {
            ret.add(mapNaarJson(polis));
        }
        return ret;
    }
}
