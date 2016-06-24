package nl.dias.service;

import com.google.common.base.Predicate;
import nl.dias.domein.Telefoonnummer;
import nl.dias.repository.TelefoonnummerRepository;
import nl.dias.web.SoortEntiteit;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

import static com.google.common.collect.Iterables.filter;


@Service
public class TelefoonnummerService {
    private static final Logger LOGGER = LoggerFactory.getLogger(TelefoonnummerService.class);

    @Inject
    private TelefoonnummerRepository telefoonnummerRepository;

    public List<Telefoonnummer> alles(SoortEntiteit soortEntiteit, Long parentId) {
        LOGGER.debug("alles soortEntiteit {} parentId {}", soortEntiteit, parentId);

        return telefoonnummerRepository.alles(soortEntiteit, parentId);
    }

    public void opslaan(Telefoonnummer telefoonnummer) {
        telefoonnummerRepository.opslaan(telefoonnummer);
    }

    public void opslaan(final List<Telefoonnummer> telefoonnummers, Long parentId, SoortEntiteit soortEntiteit) {
        List<Telefoonnummer> lijstBestaandeNummer = telefoonnummerRepository.alles(soortEntiteit, parentId);

        //Verwijderen wat niet (meer) voorkomt
        Iterable<Telefoonnummer> teVerwijderen = filter(lijstBestaandeNummer, new Predicate<Telefoonnummer>() {
            @Override
            public boolean apply(Telefoonnummer telefoonnummer) {
                return !telefoonnummers.contains(telefoonnummer);
            }
        });

        for (Telefoonnummer telefoonnummer : teVerwijderen) {
            telefoonnummerRepository.verwijder(telefoonnummer);
        }

        for (Telefoonnummer telefoonnummer : telefoonnummers) {
            LOGGER.debug("Opslaan " + ReflectionToStringBuilder.toString(telefoonnummer, ToStringStyle.SHORT_PREFIX_STYLE));
            telefoonnummerRepository.opslaan(telefoonnummer);
        }
    }

    public Telefoonnummer lees(Long id) {
        return telefoonnummerRepository.lees(id);
    }

    public void verwijderen(SoortEntiteit soortEntiteit, Long entiteitId) {
        List<Telefoonnummer> teVerwijderen = telefoonnummerRepository.alles(soortEntiteit, entiteitId);

        for (Telefoonnummer telefoonnummer : teVerwijderen) {
            telefoonnummerRepository.verwijder(telefoonnummer);
        }
    }
}