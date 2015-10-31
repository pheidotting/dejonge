package nl.dias.web.mapper;

import nl.dias.domein.Bijlage;
import nl.dias.domein.SoortBijlage;
import nl.dias.domein.json.JsonBijlage;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class BijlageMapper extends Mapper<Bijlage, JsonBijlage> {
    private final static Logger LOGGER = LoggerFactory.getLogger(BijlageMapper.class);

    @Override
    public Bijlage mapVanJson(JsonBijlage json) {
        LOGGER.debug("{}", json);

        Bijlage bijlage = new Bijlage();
        if (json.getId() != null) {
            bijlage.setId(Long.valueOf(json.getId()));
        }
        bijlage.setOmschrijving(json.getOmschrijvingOfBestandsNaam());
        bijlage.setSoortBijlage(SoortBijlage.valueOf(json.getSoortBijlage().toUpperCase()));

        LOGGER.debug("{}", bijlage);
        return bijlage;
    }

    @Override
    public JsonBijlage mapNaarJson(Bijlage bijlage) {

        JsonBijlage json = new JsonBijlage();
        json.setId(bijlage.getId() == null ? null : bijlage.getId().toString());
        json.setSoortBijlage(bijlage.getSoortBijlage().getOmschrijving());
        json.setOmschrijvingOfBestandsNaam(bijlage.getOmschrijving());
        json.setDatumUpload(bijlage.getUploadMoment().toString("dd-MM-yyyy HH:mm"));
        json.setBestandsNaam(bijlage.getBestandsNaam());

        String parentId = null;
        switch (bijlage.getSoortBijlage()) {
            case POLIS:
                parentId = bijlage.getPolis().getPolisNummer();
                break;
            case SCHADE:
                parentId = bijlage.getSchade().getSchadeNummerMaatschappij();
                break;
            case HYPOTHEEK:
                parentId = bijlage.getHypotheek().getId().toString();
                break;
            case IBAANGIFTE:
                parentId = Integer.toString(bijlage.getAangifte().getJaar());
                break;
        }
        json.setParentId(parentId);

        LOGGER.debug("In  : " + ReflectionToStringBuilder.toString(bijlage));
        LOGGER.debug("Uit : " + ReflectionToStringBuilder.toString(json));

        return json;
    }
}
