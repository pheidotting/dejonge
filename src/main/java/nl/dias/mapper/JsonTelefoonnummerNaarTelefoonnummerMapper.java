package nl.dias.mapper;

import com.google.common.base.Predicate;
import nl.dias.domein.*;
import nl.dias.domein.json.JsonTelefoonnummer;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Set;

import static com.google.common.collect.Iterables.filter;
import static com.google.common.collect.Iterables.getFirst;

@Component
public class JsonTelefoonnummerNaarTelefoonnummerMapper extends AbstractMapper<JsonTelefoonnummer, Telefoonnummer> {
    private final static Logger LOGGER = LoggerFactory.getLogger(JsonTelefoonnummerNaarTelefoonnummerMapper.class);

    @Override
    public Telefoonnummer map(final JsonTelefoonnummer object, Object parent) {
        if (object == null) {
            return null;
        }
        LOGGER.debug("Mappen : {}", ReflectionToStringBuilder.toString(object, ToStringStyle.SHORT_PREFIX_STYLE));

        Telefoonnummer telefoonnummer = null;

        if (parent != null) {
            Set<Telefoonnummer> telefoonnummers = null;
            if (parent instanceof Relatie) {
                telefoonnummers = ((Relatie) parent).getTelefoonnummers();
            } else if (parent instanceof ContactPersoon) {
                telefoonnummers = ((ContactPersoon) parent).getTelefoonnummers();
            } else if (parent instanceof Bedrijf) {
                telefoonnummers = ((Bedrijf) parent).getTelefoonnummers();
            }
            if (telefoonnummers != null && !telefoonnummers.isEmpty()) {
                telefoonnummer = getFirst(filter(telefoonnummers, new Predicate<Telefoonnummer>() {
                    @Override
                    public boolean apply(Telefoonnummer telefoonnummer) {
                        return telefoonnummer.getId() == object.getId();
                    }
                }), new Telefoonnummer());
            } else {
                telefoonnummer = new Telefoonnummer();
            }
        } else {
            telefoonnummer = new Telefoonnummer();
        }
        telefoonnummer.setId(object.getId());
        telefoonnummer.setOmschrijving(object.getOmschrijving());
        telefoonnummer.setSoort(TelefoonnummerSoort.valueOf(object.getSoort()));
        telefoonnummer.setTelefoonnummer(object.getTelefoonnummer());

        if (parent instanceof Relatie) {
            telefoonnummer.setRelatie((Relatie) parent);
        } else if (parent instanceof ContactPersoon) {
            telefoonnummer.setContactPersoon((ContactPersoon) parent);
        } else if (parent instanceof Bedrijf) {
            telefoonnummer.setBedrijf((Bedrijf) parent);
        }

        return telefoonnummer;
    }

    @Override
    boolean isVoorMij(Object object) {
        return object instanceof JsonTelefoonnummer;
    }
}
