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
    public JsonBijlage mapNaarJson(Bijlage object) {
        ArchiefBestand archiefBestand = archiefService.ophalen(object.getS3Identificatie(), true);

        JsonBijlage json = new JsonBijlage();
        json.setId(object.getId().toString());
        json.setSoortBijlage(object.getSoortBijlage().getOmschrijving());
        if (archiefBestand != null) {
            json.setBestandsNaam(archiefBestand.getBestandsnaam());
        }

        return json;
    }

    public void setArchiefService(ArchiefService archiefService) {
        this.archiefService = archiefService;
    }

}
