package nl.dias.mapper;

import nl.dias.domein.RekeningNummer;
import nl.lakedigital.djfc.commons.json.JsonRekeningNummer;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class JsonRekeningNummerNaarRekeningNummerMapper extends AbstractMapper<JsonRekeningNummer, RekeningNummer> {
    private static final Logger LOGGER = LoggerFactory.getLogger(JsonRekeningNummerNaarRekeningNummerMapper.class);

    @Override
    public RekeningNummer map(JsonRekeningNummer jsonRekeningNummer, Object parent, Object bestaandOjbect) {
        LOGGER.debug(ReflectionToStringBuilder.toString(jsonRekeningNummer, ToStringStyle.SHORT_PREFIX_STYLE));
        LOGGER.debug(ReflectionToStringBuilder.toString(parent, ToStringStyle.SHORT_PREFIX_STYLE));
        LOGGER.debug(ReflectionToStringBuilder.toString(bestaandOjbect, ToStringStyle.SHORT_PREFIX_STYLE));

        RekeningNummer rekeningNummer = new RekeningNummer();
        if (bestaandOjbect != null) {
            rekeningNummer = (RekeningNummer) bestaandOjbect;
        }
        rekeningNummer.setRekeningnummer(jsonRekeningNummer.getRekeningnummer());
        rekeningNummer.setBic(jsonRekeningNummer.getBic());

        //        if (parent instanceof Relatie) {
        //            rekeningNummer.setRelatie((Relatie) parent);
        //            if (jsonRekeningNummer.getId() == null) {
        //                ((Relatie) parent).getRekeningnummers().add(rekeningNummer);
        //            }
        //        } else if (parent instanceof Kantoor) {
        //            rekeningNummer.setKantoor((Kantoor) parent);
        //            if (jsonRekeningNummer.getId() == null) {
        //                ((Kantoor) parent).getRekeningnummers().add(rekeningNummer);
        //            }
        //        }

        return rekeningNummer;
    }

    @Override
    boolean isVoorMij(Object object) {
        return object instanceof JsonRekeningNummer;
    }
}
