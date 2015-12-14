package nl.dias.mapper;

import nl.dias.domein.Bedrijf;
import nl.dias.domein.ContactPersoon;
import nl.dias.domein.json.JsonBedrijf;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.inject.Inject;

@Component
public class BedrijfNaarJsonBedrijfMapper extends AbstractMapper<Bedrijf, JsonBedrijf> {
    private final static Logger LOGGER = LoggerFactory.getLogger(BedrijfNaarJsonBedrijfMapper.class);

    @Inject
    private ContactPersoonNaarJsonContactPersoonMapper contactPersoonNaarJsonContactPersoonMapper;

    @Override
    public JsonBedrijf map(Bedrijf bedrijf, Object parent) {
        JsonBedrijf jsonBedrijf = new JsonBedrijf();

        LOGGER.debug("Map naar JSON {}", ReflectionToStringBuilder.toString(bedrijf, ToStringStyle.SHORT_PREFIX_STYLE));

        jsonBedrijf.setId(bedrijf.getId().toString());
        jsonBedrijf.setKvk(bedrijf.getKvk());
        jsonBedrijf.setNaam(bedrijf.getNaam());
        jsonBedrijf.setcAoVerplichtingen(bedrijf.getcAoVerplichtingen());
        jsonBedrijf.setEmail(bedrijf.getEmail());
        jsonBedrijf.setHoedanigheid(bedrijf.getHoedanigheid());
        jsonBedrijf.setRechtsvorm(bedrijf.getRechtsvorm());
        jsonBedrijf.setInternetadres(bedrijf.getInternetadres());

        for (ContactPersoon contactPersoon : bedrijf.getContactPersonen()) {
            jsonBedrijf.getContactpersonen().add(contactPersoonNaarJsonContactPersoonMapper.map(contactPersoon));
        }

        LOGGER.debug("Gemapt naar {}", ReflectionToStringBuilder.toString(jsonBedrijf, ToStringStyle.SHORT_PREFIX_STYLE));

        return jsonBedrijf;
    }

    @Override
    boolean isVoorMij(Object object) {
        return object instanceof Bedrijf;
    }
}
