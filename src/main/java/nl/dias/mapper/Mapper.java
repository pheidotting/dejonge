package nl.dias.mapper;


import com.google.common.base.Predicate;
import nl.dias.domein.*;
import nl.lakedigital.djfc.commons.json.*;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.util.List;

import static com.google.common.collect.Iterables.filter;
import static com.google.common.collect.Iterables.getOnlyElement;

@Component
public class Mapper {
    private static final Logger LOGGER = LoggerFactory.getLogger(Mapper.class);

    @Inject
    private List<AbstractMapper> mappers;

    public <T> T map(final Object objectIn, final Class<T> clazz) {
        return map(objectIn, clazz, null);
    }

    public <T> T map(final Object objectIn, final Class<T> clazz, Object parent) {
        Object objectUit = null;

        LOGGER.debug("Mappen van {}", ReflectionToStringBuilder.toString(objectIn, ToStringStyle.SHORT_PREFIX_STYLE));

        AbstractMapper mapper = getOnlyElement(filter(mappers, new Predicate<AbstractMapper>() {
            @Override
            public boolean apply(AbstractMapper abstractMapper) {
                return abstractMapper.isVoorMij(objectIn);
            }
        }));

        objectUit = mapper.map(objectIn);

        LOGGER.debug("mappen van {} naar {}", objectIn.getClass(), objectUit.getClass());

        if (objectIn instanceof ObjectMetOpmerkingen) {
            for (Opmerking opmerking : ((ObjectMetOpmerkingen) objectIn).getOpmerkingen()) {
                LOGGER.debug("Mappen van {}", ReflectionToStringBuilder.toString(opmerking, ToStringStyle.SHORT_PREFIX_STYLE));
                ((ObjectMetJsonOpmerkingen) objectUit).getOpmerkingen().add(map(opmerking, JsonOpmerking.class));
            }
        }

        if (objectIn instanceof ObjectMetBijlages) {
            for (Bijlage bijlage : ((ObjectMetBijlages) objectIn).getBijlages()) {
                LOGGER.debug("Mappen van {}", ReflectionToStringBuilder.toString(bijlage, ToStringStyle.SHORT_PREFIX_STYLE));
                ((ObjectMetJsonBijlages) objectUit).getBijlages().add(map(bijlage, JsonBijlage.class));
            }
        }

        if (objectIn instanceof ObjectMetAdressen) {

            LOGGER.debug("mappen van {} naar {}", objectIn.getClass(), objectUit.getClass());

            for (Adres adres : ((ObjectMetAdressen) objectIn).getAdressen()) {
                LOGGER.debug("Mappen van {}", ReflectionToStringBuilder.toString(adres, ToStringStyle.SHORT_PREFIX_STYLE));
                ((ObjectMetJsonAdressen) objectUit).getAdressen().add(map(adres, JsonAdres.class));
            }
        }

        if (objectIn instanceof ObjectMetJsonAdressen) {

            LOGGER.debug("mappen van {} naar {}", objectIn.getClass(), objectUit.getClass());

            for (JsonAdres adres : ((ObjectMetJsonAdressen) objectIn).getAdressen()) {
                LOGGER.debug("Mappen van {}", ReflectionToStringBuilder.toString(adres, ToStringStyle.SHORT_PREFIX_STYLE));
                ((ObjectMetAdressen) objectUit).getAdressen().add(map(adres, Adres.class));
            }
        }

        if (objectIn instanceof ObjectMetTelefoonnummers) {

            LOGGER.debug("mappen van {} naar {}", objectIn.getClass(), objectUit.getClass());

            for (Telefoonnummer telefoonnummer : ((ObjectMetTelefoonnummers) objectIn).getTelefoonnummers()) {
                LOGGER.debug("Mappen van {}", ReflectionToStringBuilder.toString(telefoonnummer, ToStringStyle.SHORT_PREFIX_STYLE));
                ((ObjectMetJsonTelefoonnummers) objectUit).getTelefoonnummers().add(map(telefoonnummer, JsonTelefoonnummer.class));
            }
        }


        if (objectIn instanceof ObjectMetJsonTelefoonnummers) {

            LOGGER.debug("mappen van {} naar {}", objectIn.getClass(), objectUit.getClass());

            for (JsonTelefoonnummer telefoonnummer : ((ObjectMetJsonTelefoonnummers) objectIn).getTelefoonnummers()) {
                LOGGER.debug("Mappen van {}", ReflectionToStringBuilder.toString(telefoonnummer, ToStringStyle.SHORT_PREFIX_STYLE));
                ((ObjectMetTelefoonnummers) objectUit).getTelefoonnummers().add(map(telefoonnummer, Telefoonnummer.class));
            }
        }

        return clazz.cast(objectUit);
    }
}
