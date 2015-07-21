package nl.dias.web.mapper;

import com.sun.jersey.api.core.InjectParam;
import nl.dias.domein.Bijlage;
import nl.dias.domein.SoortBijlage;
import nl.dias.domein.json.JsonBijlage;
import nl.lakedigital.archief.domain.ArchiefBestand;
import nl.lakedigital.archief.service.ArchiefService;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.log4j.Logger;
import org.joda.time.LocalDateTime;

import javax.inject.Named;

@Named
public class BijlageMapper extends Mapper<Bijlage, JsonBijlage> {
    @InjectParam
    private ArchiefService archiefService;

    private final static Logger LOGGER = Logger.getLogger(BijlageMapper.class);

    @Override
    public Bijlage mapVanJson(JsonBijlage json) {
        Bijlage bijlage = new Bijlage();
        bijlage.setId(Long.valueOf(json.getId()));
        bijlage.setSoortBijlage(SoortBijlage.valueOf(json.getSoortBijlage().toUpperCase()));

        return bijlage;
    }

    @Override
    public JsonBijlage mapNaarJson(Bijlage bijlage) {
        ArchiefBestand archiefBestand = archiefService.ophalen(bijlage.getS3Identificatie(), true);

        JsonBijlage json = new JsonBijlage();
        json.setId(bijlage.getId().toString());
        json.setSoortBijlage(bijlage.getSoortBijlage().getOmschrijving());
        if (archiefBestand != null) {
            json.setBestandsNaam(archiefBestand.getBestandsnaam());
        }
        json.setOmschrijving(bijlage.getOmschrijving());
        if (archiefBestand != null && archiefBestand.getDatumOpgeslagen() != null) {
            json.setDatumUpload(new LocalDateTime(archiefBestand.getDatumOpgeslagen()).toString("dd-MM-yyyy HH:mm"));
        }

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

    public void setArchiefService(ArchiefService archiefService) {
        this.archiefService = archiefService;
    }

}
