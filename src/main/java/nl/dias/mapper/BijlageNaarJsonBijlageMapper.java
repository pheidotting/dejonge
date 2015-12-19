package nl.dias.mapper;

import nl.dias.domein.Bijlage;
import nl.dias.domein.json.JsonBijlage;
import org.springframework.stereotype.Component;

@Component
public class BijlageNaarJsonBijlageMapper extends AbstractMapper<Bijlage, JsonBijlage> {
    @Override
    public JsonBijlage map(Bijlage bijlage, Object parent) {
        JsonBijlage json = new JsonBijlage();
        json.setId(bijlage.getId() == null ? null : bijlage.getId().toString());
        json.setSoortBijlage(bijlage.getSoortBijlage().getOmschrijving());
        if (bijlage.getOmschrijving() != null) {
            json.setOmschrijvingOfBestandsNaam(bijlage.getOmschrijving());
        } else {
            json.setOmschrijvingOfBestandsNaam(bijlage.getBestandsNaam());
        }
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
            case BEDRIJF:
                parentId = bijlage.getBedrijf().getId().toString();
                break;
            case JAARCIJFERS:
                parentId = bijlage.getJaarCijfers().getId().toString();
                break;
        }
        json.setParentId(parentId);

        return json;
    }

    @Override
    boolean isVoorMij(Object object) {
        return object instanceof Bijlage;
    }
}
