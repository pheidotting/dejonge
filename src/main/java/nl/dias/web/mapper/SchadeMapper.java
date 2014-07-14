package nl.dias.web.mapper;

import javax.inject.Named;

import nl.dias.domein.Bedrag;
import nl.dias.domein.Schade;
import nl.dias.domein.json.JsonSchade;

import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.joda.time.format.DateTimeFormat;

import com.sun.jersey.api.core.InjectParam;

@Named
public class SchadeMapper extends Mapper<Schade, JsonSchade> {
    @InjectParam
    private OpmerkingMapper opmerkingMapper;
    @InjectParam
    private BijlageMapper bijlageMapper;

    @Override
    public Schade mapVanJson(JsonSchade json) {
        String pattern = "dd-MM-yyyy HH:mm";

        LocalDateTime datumTijdMelding = LocalDateTime.parse(json.getDatumTijdMelding(), DateTimeFormat.forPattern(pattern));
        LocalDateTime datumTijdSchade = LocalDateTime.parse(json.getDatumTijdSchade(), DateTimeFormat.forPattern(pattern));

        Schade schade = new Schade();

        schade.setDatumAfgehandeld(new LocalDate(json.getDatumAfgehandeld()));
        schade.setDatumTijdMelding(new LocalDateTime(datumTijdMelding));
        schade.setDatumTijdSchade(new LocalDateTime(datumTijdSchade));
        schade.setEigenRisico(new Bedrag(json.getEigenRisico()));
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
        jsonSchade.setDatumAfgehandeld(schade.getDatumAfgehandeld().toString("dd-MM-yyyy"));
        jsonSchade.setDatumTijdMelding(schade.getDatumTijdMelding().toString("dd-MM-yyyy hh:mm"));
        jsonSchade.setDatumTijdSchade(schade.getDatumTijdSchade().toString("dd-MM-yyyy hh:mm"));
        jsonSchade.setEigenRisico(schade.getEigenRisico().getBedrag().toString());
        jsonSchade.setId(schade.getId());
        jsonSchade.setLocatie(schade.getLocatie());
        jsonSchade.setOmschrijving(schade.getOmschrijving());
        jsonSchade.setOpmerkingen(opmerkingMapper.mapAllNaarJson(schade.getOpmerkingen()));
        jsonSchade.setSchadeNummerMaatschappij(schade.getSchadeNummerMaatschappij());
        jsonSchade.setSchadeNummerTussenPersoon(schade.getSchadeNummerTussenPersoon());
        if (schade.getSoortSchade() != null) {
            jsonSchade.setSoortSchade(schade.getSoortSchade().getOmschrijving());
        } else {
            jsonSchade.setSoortSchade(schade.getSoortSchadeOngedefinieerd());
        }
        jsonSchade.setStatusSchade(schade.getStatusSchade().getStatus());

        return jsonSchade;
    }

    public void setOpmerkingMapper(OpmerkingMapper opmerkingMapper) {
        this.opmerkingMapper = opmerkingMapper;
    }

    public void setBijlageMapper(BijlageMapper bijlageMapper) {
        this.bijlageMapper = bijlageMapper;
    }
}
