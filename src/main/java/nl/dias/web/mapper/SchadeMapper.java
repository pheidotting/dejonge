package nl.dias.web.mapper;

import nl.dias.domein.Bedrag;
import nl.dias.domein.Schade;
import nl.dias.domein.json.JsonOpmerking;
import nl.dias.domein.json.JsonSchade;
import nl.dias.service.SchadeService;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.joda.time.format.DateTimeFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.util.Collections;
import java.util.List;

@Component
public class SchadeMapper extends Mapper<Schade, JsonSchade> {
    private final static Logger LOGGER = LoggerFactory.getLogger(SchadeMapper.class);

    @Inject
    private OpmerkingMapper opmerkingMapper;
    @Inject
    private BijlageMapper bijlageMapper;
    @Inject
    private SchadeService schadeService;

    @Override
    public Schade mapVanJson(JsonSchade json) {
        String patternDatumTijd = "dd-MM-yyyy HH:mm";
        String patternDatum = "dd-MM-yyyy";

        LocalDate datumAfgehandeld = null;
        if (json.getDatumAfgehandeld() != null) {
            datumAfgehandeld = LocalDate.parse(json.getDatumAfgehandeld(), DateTimeFormat.forPattern(patternDatum));
        }

        Schade schade = null;
        if (json.getId() == null) {
            schade = new Schade();
        } else {
            schade = schadeService.lees(json.getId());
        }
        schade.setId(json.getId());

        if (json.getDatumTijdMelding() != null) {
            LocalDateTime datumTijdMelding = LocalDateTime.parse(json.getDatumTijdMelding(), DateTimeFormat.forPattern(patternDatumTijd));
            schade.setDatumTijdMelding(datumTijdMelding);
        }
        if (json.getDatumTijdSchade() != null) {
            LocalDateTime datumTijdSchade = LocalDateTime.parse(json.getDatumTijdSchade(), DateTimeFormat.forPattern(patternDatumTijd));
            schade.setDatumTijdSchade(datumTijdSchade);
        }

        if (datumAfgehandeld != null) {
            schade.setDatumAfgehandeld(datumAfgehandeld);
        }
        if (json.getEigenRisico() != null) {
            schade.setEigenRisico(new Bedrag(json.getEigenRisico()));
        }
        schade.setLocatie(json.getLocatie());
        schade.setOmschrijving(json.getOmschrijving());
        schade.setSchadeNummerMaatschappij(json.getSchadeNummerMaatschappij());
        schade.setSchadeNummerTussenPersoon(json.getSchadeNummerTussenPersoon());

        return schade;
    }

    @Override
    public JsonSchade mapNaarJson(Schade schade) {
        JsonSchade jsonSchade = new JsonSchade();

        jsonSchade.setBijlages(bijlageMapper.mapAllNaarJson(schade.getBijlages()));
        if (schade.getDatumAfgehandeld() != null) {
            jsonSchade.setDatumAfgehandeld(schade.getDatumAfgehandeld().toString("dd-MM-yyyy"));
        }
        jsonSchade.setDatumTijdMelding(schade.getDatumTijdMelding().toString("dd-MM-yyyy HH:mm"));
        jsonSchade.setDatumTijdSchade(schade.getDatumTijdSchade().toString("dd-MM-yyyy HH:mm"));
        if (schade.getEigenRisico() != null) {
            jsonSchade.setEigenRisico(schade.getEigenRisico().getBedrag().toString());
        }
        jsonSchade.setId(schade.getId());
        jsonSchade.setLocatie(schade.getLocatie());
        jsonSchade.setOmschrijving(schade.getOmschrijving());
        //        if (schade.getPolis() != null && schade.getPolis().getRelatie() != null) {
        //            jsonSchade.setRelatie(schade.getPolis().getRelatie().getId().toString());
        //        }
        if (schade.getPolis() != null && schade.getPolis().getBedrijf() != null) {
            jsonSchade.setBedrijf(schade.getPolis().getBedrijf().getId());
        }

        List<JsonOpmerking> opmerkingen = opmerkingMapper.mapAllNaarJson(schade.getOpmerkingen());
        Collections.sort(opmerkingen);

        jsonSchade.setOpmerkingen(opmerkingen);
        jsonSchade.setSchadeNummerMaatschappij(schade.getSchadeNummerMaatschappij());
        jsonSchade.setSchadeNummerTussenPersoon(schade.getSchadeNummerTussenPersoon());
        if (schade.getSoortSchade() != null) {
            jsonSchade.setSoortSchade(schade.getSoortSchade().getOmschrijving());
        } else {
            jsonSchade.setSoortSchade(schade.getSoortSchadeOngedefinieerd());
        }
        if (schade.getStatusSchade() != null) {
            jsonSchade.setStatusSchade(schade.getStatusSchade().getStatus());
        }
        if (schade.getPolis() != null && schade.getPolis().getId() != null) {
            jsonSchade.setPolis(schade.getPolis().getId().toString());
        }

        LOGGER.debug("{}", jsonSchade);

        return jsonSchade;
    }

    public void setOpmerkingMapper(OpmerkingMapper opmerkingMapper) {
        this.opmerkingMapper = opmerkingMapper;
    }

    public void setBijlageMapper(BijlageMapper bijlageMapper) {
        this.bijlageMapper = bijlageMapper;
    }
}
