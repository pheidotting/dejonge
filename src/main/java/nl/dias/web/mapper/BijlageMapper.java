package nl.dias.web.mapper;

import javax.inject.Named;

import nl.dias.domein.Bijlage;
import nl.dias.domein.SoortBijlage;
import nl.dias.domein.json.JsonBijlage;
import nl.lakedigital.archief.domain.ArchiefBestand;
import nl.lakedigital.archief.service.ArchiefService;

import com.sun.jersey.api.core.InjectParam;

@Named
public class BijlageMapper extends Mapper<Bijlage, JsonBijlage> {
    @InjectParam
    private ArchiefService archiefService;

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

        return json;
    }

    public void setArchiefService(ArchiefService archiefService) {
        this.archiefService = archiefService;
    }

}
