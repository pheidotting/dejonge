package nl.dias.web.mapper;

import com.google.common.collect.Lists;
import nl.dias.domein.Bedrag;
import nl.dias.domein.Relatie;
import nl.dias.domein.StatusPolis;
import nl.dias.domein.json.JsonPolis;
import nl.dias.domein.polis.Betaalfrequentie;
import nl.dias.domein.polis.Polis;
import nl.dias.domein.polis.PolisComperator;
import nl.dias.domein.predicates.StatusPolisBijStatusPredicate;
import nl.dias.service.GebruikerService;
import nl.dias.service.PolisService;
import nl.dias.service.VerzekeringsMaatschappijService;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import static com.google.common.collect.Iterables.filter;
import static com.google.common.collect.Iterables.getFirst;

@Component
public class PolisMapper extends Mapper<Polis, JsonPolis> {
    private final static Logger LOGGER = LoggerFactory.getLogger(PolisMapper.class);

    @Inject
    private OpmerkingMapper opmerkingMapper;
    @Inject
    private BijlageMapper bijlageMapper;
    @Inject
    private SchadeMapper schadeMapper;
    @Inject
    private PolisService polisService;
    @Inject
    private VerzekeringsMaatschappijService verzekeringsMaatschappijService;
    @Inject
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

        Polis polis = null;
        if (jsonPolis.getId() == null || jsonPolis.getId() == 0L) {
            polis = polisService.definieerPolisSoort(jsonPolis.getSoort());

            polis.setId(jsonPolis.getId());
        } else {
            polis = polisService.lees(jsonPolis.getId());
        }

        if (jsonPolis.getStatus() != null) {
            polis.setStatus(getFirst(filter(Lists.newArrayList(StatusPolis.values()), new StatusPolisBijStatusPredicate(jsonPolis.getStatus())), StatusPolis.ACT));
        }

        polis.setPolisNummer(jsonPolis.getPolisNummer());
        polis.setKenmerk(jsonPolis.getKenmerk());
        polis.setIngangsDatum(ingangsDatum);
        if (jsonPolis.getPremie() != null) {
            polis.setPremie(new Bedrag(jsonPolis.getPremie().replace(",", ".")));
        }
        polis.setWijzigingsDatum(wijzigingsDatum);
        polis.setProlongatieDatum(prolongatieDatum);
        polis.setDekking(jsonPolis.getDekking());
        if (StringUtils.isNotEmpty(jsonPolis.getBetaalfrequentie())) {
            polis.setBetaalfrequentie(Betaalfrequentie.valueOf(jsonPolis.getBetaalfrequentie().toUpperCase().substring(0, 1)));
        }

        polis.setMaatschappij(Long.valueOf(jsonPolis.getMaatschappij()));
//        if (jsonPolis.getMaatschappij() != null && !"Kies een maatschappij...".equals(jsonPolis.getMaatschappij())) {
//            polis.setMaatschappij(verzekeringsMaatschappijService.zoekOpNaam(jsonPolis.getMaatschappij()));
//        }
        if (StringUtils.isNotEmpty(jsonPolis.getRelatie())) {
            polis.setRelatie((Relatie) gebruikerService.lees(Long.valueOf(jsonPolis.getRelatie())));
        }
        polis.setOmschrijvingVerzekering(jsonPolis.getOmschrijvingVerzekering());

        if (polis.getId() != null && polis.getId() != 0) {
            Polis p = polisService.lees(polis.getId());

            polis.setSchades(p.getSchades());
            polis.setOpmerkingen(p.getOpmerkingen());
        }

        //        polis.setBijlages(bijlageMapper.mapAllVanJson(jsonPolis.getBijlages()));
        //        for (Bijlage bijlage : polis.getBijlages()) {
        //            bijlage.setPolis(polis);
        //        }

        LOGGER.debug(ReflectionToStringBuilder.toString(polis));
        return polis;
    }

    @Override
    public JsonPolis mapNaarJson(Polis polis) {
        LOGGER.debug("Mappen Polis " + polis);

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
        LOGGER.debug("{}", polis.getBijlages());
        jsonPolis.setBijlages(bijlageMapper.mapAllNaarJson(polis.getBijlages()));

        jsonPolis.setOpmerkingen(opmerkingMapper.mapAllNaarJson(polis.getOpmerkingen()));
        if (polis.getMaatschappij() != null) {
            jsonPolis.setMaatschappij(polis.getMaatschappij().toString());
        }
        jsonPolis.setSoort(polis.getClass().getSimpleName().replace("Verzekering", ""));
        if (polis.getBedrijf() != null) {
            jsonPolis.setBedrijf(polis.getBedrijf().getNaam());
        }
        jsonPolis.setSchades(schadeMapper.mapAllNaarJson(polis.getSchades()));
        if (polis.getRelatie() != null) {
            jsonPolis.setRelatie(polis.getRelatie().getId().toString());
        }
        jsonPolis.setOmschrijvingVerzekering(polis.getOmschrijvingVerzekering());

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
