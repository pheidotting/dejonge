package nl.dias.mapper;

import nl.dias.domein.Bijlage;
import nl.lakedigital.djfc.commons.json.JsonBijlage;
import org.springframework.stereotype.Component;

@Component
public class BijlageNaarJsonBijlageMapper extends AbstractMapper<Bijlage, JsonBijlage> {
    @Override
    public JsonBijlage map(Bijlage bijlage, Object parent, Object bestaandObject) {
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
                parentId = bijlage.getPolis().toString();
                break;
            case SCHADE:
                parentId = bijlage.getSchade().toString();
                break;
            case HYPOTHEEK:
                parentId = bijlage.getHypotheek().getId().toString();
                break;
            case IBAANGIFTE:
                parentId = Integer.toString(bijlage.getAangifte().getJaar());
                break;
            case BEDRIJF:
                parentId = bijlage.getBedrijf().toString();
                break;
            case JAARCIJFERS:
                parentId = bijlage.getJaarCijfers().toString();
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
