package nl.dias.web.mapper;

import com.sun.jersey.api.core.InjectParam;
import nl.dias.domein.Bedrag;
import nl.dias.domein.Schade;
import nl.dias.domein.json.JsonOpmerking;
import nl.dias.domein.json.JsonSchade;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.joda.time.format.DateTimeFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Named;
import java.util.Collections;
import java.util.List;

@Named
public class SchadeMapper extends Mapper<Schade, JsonSchade> {
    private final static Logger LOGGER = LoggerFactory.getLogger(SchadeMapper.class);

    @InjectParam
    private OpmerkingMapper opmerkingMapper;
    @InjectParam
    private BijlageMapper bijlageMapper;

    @Override
    public Schade mapVanJson(JsonSchade json) {
        String patternDatumTijd = "dd-MM-yyyy HH:mm";
        String patternDatum = "dd-MM-yyyy";

        LocalDateTime datumTijdMelding = LocalDateTime.parse(json.getDatumTijdMelding(), DateTimeFormat.forPattern(patternDatumTijd));
        LocalDateTime datumTijdSchade = LocalDateTime.parse(json.getDatumTijdSchade(), DateTimeFormat.forPattern(patternDatumTijd));
        LocalDate datumAfgehandeld = null;
        if (json.getDatumAfgehandeld() != null) {
            datumAfgehandeld = LocalDate.parse(json.getDatumAfgehandeld(), DateTimeFormat.forPattern(patternDatum));
        }
        Schade schade = new Schade();

        schade.setId(json.getId());
        if (datumAfgehandeld != null) {
            schade.setDatumAfgehandeld(datumAfgehandeld);
        }
        schade.setDatumTijdMelding(datumTijdMelding);
        schade.setDatumTijdSchade(datumTijdSchade);
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
        jsonSchade.setStatusSchade(schade.getStatusSchade().getStatus());
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
