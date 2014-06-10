package nl.dias.web.mapper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.inject.Named;

import nl.dias.domein.Bedrag;
import nl.dias.domein.Bijlage;
import nl.dias.domein.json.JsonBijlage;
import nl.dias.domein.json.JsonPolis;
import nl.dias.domein.polis.Polis;
import nl.dias.domein.polis.PolisComperator;
import nl.lakedigital.archief.domain.ArchiefBestand;
import nl.lakedigital.archief.service.ArchiefService;

import org.joda.time.LocalDate;

import com.sun.jersey.api.core.InjectParam;

@Named
public class PolisMapper implements Mapper<Polis, JsonPolis> {
    @InjectParam
    private OpmerkingMapper opmerkingMapper;
    @InjectParam
    private ArchiefService archiefService;

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
        jsonPolis.setIngangsDatum(polis.getIngangsDatum().toString("yyyy-MM-dd"));
        jsonPolis.setPremie(zetBedragOm(polis.getPremie()));
        jsonPolis.setWijzigingsDatum(polis.getWijzigingsDatum().toString("yyyy-MM-dd"));
        jsonPolis.setProlongatieDatum(polis.getProlongatieDatum().toString("yyyy-MM-dd"));
        if (polis.getBetaalfrequentie() != null) {
            jsonPolis.setBetaalfrequentie(polis.getBetaalfrequentie().getOmschrijving());
        }
        for (Bijlage bijlage : polis.getBijlages()) {
            ArchiefBestand archiefBestand = archiefService.ophalen(bijlage.getS3Identificatie(), true);

            JsonBijlage jsonBijlage = new JsonBijlage();
            jsonBijlage.setId(bijlage.getId().toString());
            jsonBijlage.setSoortBijlage(bijlage.getSoortBijlage().getOmschrijving());
            jsonBijlage.setBestandsNaam(archiefBestand.getBestandsnaam());

            jsonPolis.getBijlages().add(jsonBijlage);
        }
        jsonPolis.setOpmerkingen(opmerkingMapper.mapAllNaarJson(polis.getOpmerkingen()));
        jsonPolis.setMaatschappij(polis.getMaatschappij().getNaam());
        jsonPolis.setSoort(polis.getClass().getSimpleName());
        if (polis.getBedrijf() != null) {
            jsonPolis.setBedrijf(polis.getBedrijf().getNaam());
        }

        if (polis.getIngangsDatum().isAfter(new LocalDate())) {
            jsonPolis.setActief(false);
        } else {
            jsonPolis.setActief(true);
        }

        return jsonPolis;
    }

    private String zetBedragOm(Bedrag bedrag) {
        String waarde = null;
        String[] x = bedrag.getBedrag().toString().split("\\.");
        if (x[1].length() == 1) {
            waarde = bedrag.getBedrag().toString() + "0 euro";
        } else {
            waarde = bedrag.getBedrag().toString() + " euro";
        }
        return waarde;
    }

    @Override
    public List<JsonPolis> mapAllNaarJson(Set<Polis> polissen) {
        List<JsonPolis> ret = new ArrayList<>();

        List<Polis> polissenSortable = new ArrayList<Polis>();
        for (Polis polis : polissen) {
            polissenSortable.add(polis);
        }
        Collections.sort(polissenSortable, new PolisComperator());

        for (Polis polis : polissen) {
            ret.add(mapNaarJson(polis));
        }
        return ret;
    }
}
