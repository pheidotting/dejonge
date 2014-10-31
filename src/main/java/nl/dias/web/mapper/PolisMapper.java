package nl.dias.web.mapper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import javax.inject.Named;

import nl.dias.domein.Bedrag;
import nl.dias.domein.Relatie;
import nl.dias.domein.json.JsonPolis;
import nl.dias.domein.polis.Betaalfrequentie;
import nl.dias.domein.polis.Polis;
import nl.dias.domein.polis.PolisComperator;
import nl.dias.service.GebruikerService;
import nl.dias.service.PolisService;
import nl.dias.service.VerzekeringsMaatschappijService;

import org.apache.log4j.Logger;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;

import com.sun.jersey.api.core.InjectParam;

@Named
public class PolisMapper extends Mapper<Polis, JsonPolis> {
    private final static Logger LOGGER = Logger.getLogger(PolisService.class);

    @InjectParam
    private OpmerkingMapper opmerkingMapper;
    @InjectParam
    private BijlageMapper bijlageMapper;
    @InjectParam
    private SchadeMapper schadeMapper;
    @InjectParam
    private PolisService polisService;
    @InjectParam
    private VerzekeringsMaatschappijService verzekeringsMaatschappijService;
    @InjectParam
    private GebruikerService gebruikerService;

    @Override
    public Polis mapVanJson(JsonPolis jsonPolis) {
        String patternDatum = "dd-MM-yyyy";

        LocalDate ingangsDatum = null;
        if (jsonPolis.getIngangsDatum() != null && !"".equals(jsonPolis.getIngangsDatum())) {
            ingangsDatum = LocalDate.parse(jsonPolis.getIngangsDatum(), DateTimeFormat.forPattern(patternDatum));
        }
        LocalDate wijzigingsDatum = null;
        if (jsonPolis.getWijzigingsDatum() != null && !"".equals(jsonPolis.getWijzigingsDatum())) {
            wijzigingsDatum = LocalDate.parse(jsonPolis.getWijzigingsDatum(), DateTimeFormat.forPattern(patternDatum));
        }
        LocalDate prolongatieDatum = null;
        if (jsonPolis.getProlongatieDatum() != null && !"".equals(jsonPolis.getProlongatieDatum())) {
            prolongatieDatum = LocalDate.parse(jsonPolis.getProlongatieDatum(), DateTimeFormat.forPattern(patternDatum));
        }

        Polis polis = polisService.definieerPolisSoort(jsonPolis.getSoort());

        polis.setId(jsonPolis.getId());
        polis.setPolisNummer(jsonPolis.getPolisNummer());
        polis.setIngangsDatum(ingangsDatum);
        if (jsonPolis.getPremie() != null) {
            polis.setPremie(new Bedrag(jsonPolis.getPremie().replace(",", ".")));
        }
        polis.setWijzigingsDatum(wijzigingsDatum);
        polis.setProlongatieDatum(prolongatieDatum);
        polis.setBetaalfrequentie(Betaalfrequentie.valueOf(jsonPolis.getBetaalfrequentie().toUpperCase().substring(0, 1)));

        polis.setMaatschappij(verzekeringsMaatschappijService.zoekOpNaam(jsonPolis.getMaatschappij()));
        polis.setRelatie((Relatie) gebruikerService.lees(Long.valueOf(jsonPolis.getRelatie())));

        if (polis.getId() != null && polis.getId() != 0) {
            Polis p = polisService.lees(polis.getId());

            polis.setSchades(p.getSchades());
            polis.setOpmerkingen(p.getOpmerkingen());
        }

        return polis;
    }

    @Override
    public JsonPolis mapNaarJson(Polis polis) {
        LOGGER.debug("Mappen Polis " + polis);

        JsonPolis jsonPolis = new JsonPolis();

        jsonPolis.setId(polis.getId());
        jsonPolis.setPolisNummer(polis.getPolisNummer());
        if (polis.getPremie() != null) {
            jsonPolis.setPremie(zetBedragOm(polis.getPremie()));
        }
        if (polis.getIngangsDatum() != null && !"".equals(polis.getIngangsDatum())) {
            jsonPolis.setIngangsDatum(polis.getIngangsDatum().toString("yyyy-MM-dd"));
        }
        if (polis.getEindDatum() != null && !"".equals(polis.getEindDatum())) {
            jsonPolis.setEindDatum(polis.getEindDatum().toString("yyyy-MM-dd"));
        }
        if (polis.getWijzigingsDatum() != null && !"".equals(polis.getWijzigingsDatum())) {
            jsonPolis.setWijzigingsDatum(polis.getWijzigingsDatum().toString("yyyy-MM-dd"));
        }
        if (polis.getProlongatieDatum() != null && !"".equals(polis.getProlongatieDatum())) {
            jsonPolis.setProlongatieDatum(polis.getProlongatieDatum().toString("yyyy-MM-dd"));
        }
        if (polis.getBetaalfrequentie() != null) {
            jsonPolis.setBetaalfrequentie(polis.getBetaalfrequentie().getOmschrijving());
        }
        LOGGER.debug(polis.getBijlages());
        jsonPolis.setBijlages(bijlageMapper.mapAllNaarJson(polis.getBijlages()));
        jsonPolis.setOpmerkingen(opmerkingMapper.mapAllNaarJson(polis.getOpmerkingen()));
        jsonPolis.setMaatschappij(polis.getMaatschappij().getNaam());
        jsonPolis.setSoort(polis.getClass().getSimpleName().replace("Verzekering", ""));
        if (polis.getBedrijf() != null) {
            jsonPolis.setBedrijf(polis.getBedrijf().getNaam());
        }
        jsonPolis.setSchades(schadeMapper.mapAllNaarJson(polis.getSchades()));

        return jsonPolis;
    }

    public static String zetBedragOm(Bedrag bedrag) {
        String waarde = null;
        String[] x = bedrag.getBedrag().toString().split("\\.");
        if (x[1].length() == 1) {
            waarde = bedrag.getBedrag().toString() + "0";
        } else {
            waarde = bedrag.getBedrag().toString() + "";
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

    public void setOpmerkingMapper(OpmerkingMapper opmerkingMapper) {
        this.opmerkingMapper = opmerkingMapper;
    }

    public void setBijlageMapper(BijlageMapper bijlageMapper) {
        this.bijlageMapper = bijlageMapper;
    }

    public void setSchadeMapper(SchadeMapper schadeMapper) {
        this.schadeMapper = schadeMapper;
    }

    public void setPolisService(PolisService polisService) {
        this.polisService = polisService;
    }

    public void setVerzekeringsMaatschappijService(VerzekeringsMaatschappijService verzekeringsMaatschappijService) {
        this.verzekeringsMaatschappijService = verzekeringsMaatschappijService;
    }

    public void setGebruikerService(GebruikerService gebruikerService) {
        this.gebruikerService = gebruikerService;
    }
}
