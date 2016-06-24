package nl.dias.mapper;

import nl.dias.domein.ContactPersoon;
import nl.dias.service.GebruikerService;
import nl.lakedigital.djfc.commons.json.JsonContactPersoon;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.inject.Inject;

@Component
public class JsonContactPersoonNaarContactPersoonMapper extends AbstractMapper<JsonContactPersoon, ContactPersoon> {
    private static final Logger LOGGER = LoggerFactory.getLogger(JsonContactPersoonNaarContactPersoonMapper.class);

    @Inject
    private GebruikerService gebruikerService;

    @Override
    public ContactPersoon map(JsonContactPersoon jsonContactPersoon, Object parent, Object bestaandOjbect) {
        if (jsonContactPersoon == null) {
            return null;
        }
        LOGGER.debug("Mappen {}", ReflectionToStringBuilder.toString(jsonContactPersoon, ToStringStyle.SHORT_PREFIX_STYLE));

        ContactPersoon contactPersoon = new ContactPersoon();
        if (jsonContactPersoon.getId() != null) {
            contactPersoon = (ContactPersoon) gebruikerService.lees(jsonContactPersoon.getId());
        }

        contactPersoon.setId(jsonContactPersoon.getId());
        contactPersoon.setAchternaam(jsonContactPersoon.getAchternaam());
        contactPersoon.setEmailadres(jsonContactPersoon.getEmailadres());
        contactPersoon.setFunctie(jsonContactPersoon.getFunctie());
        contactPersoon.setTussenvoegsel(jsonContactPersoon.getTussenvoegsel());
        contactPersoon.setVoornaam(jsonContactPersoon.getVoornaam());
        contactPersoon.setBedrijf(jsonContactPersoon.getBedrijf());


        //        contactPersoon.setTelefoonnummers(jsonTelefoonnummerNaarTelefoonnummerMapper.mapAllNaarSet(jsonContactPersoon.getTelefoonnummers(), jsonContactPersoon));

        return contactPersoon;
    }

    @Override
    boolean isVoorMij(Object object) {
        return object instanceof JsonContactPersoon;
    }
}
