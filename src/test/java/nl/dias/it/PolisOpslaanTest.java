package nl.dias.it;

import com.google.common.base.Function;
import com.google.common.base.Predicate;
import nl.dias.domein.Bedrag;
import nl.dias.domein.Medewerker;
import nl.dias.domein.Sessie;
import nl.dias.domein.StatusPolis;
import nl.dias.domein.polis.Betaalfrequentie;
import nl.dias.domein.polis.Polis;
import nl.dias.domein.polis.SoortVerzekering;
import nl.dias.repository.GebruikerRepository;
import nl.dias.web.mapper.PolisMapper;
import nl.lakedigital.djfc.client.polisadministratie.PolisClient;
import nl.lakedigital.djfc.commons.json.JsonPolis;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.inject.Inject;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static com.google.common.collect.Iterables.filter;
import static com.google.common.collect.Iterables.transform;
import static com.google.common.collect.Lists.newArrayList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext-it.xml")
public class PolisOpslaanTest {

    private final static Logger LOGGER = LoggerFactory.getLogger(PolisOpslaanTest.class);

    @Inject
    private List<Polis> polissen;
    @Inject
    private PolisMapper polisMapper;
    @Inject
    private PolisClient polisClient;
    @Inject
    private GebruikerRepository gebruikerRepository;

    @Test
    public void opslaanAllePolissen() {
        Medewerker medewerker = (Medewerker) gebruikerRepository.lees(3L);
        Sessie sessie = new Sessie();
        sessie.setSessie("abc");
        sessie.setIpadres("0:0:0:0:0:0:0:1");
        medewerker.getSessies().add(sessie);
        sessie.setGebruiker(medewerker);
        gebruikerRepository.opslaan(medewerker);


        for (Polis polis : polissen) {
            vulPolis(polis);

            JsonPolis jsonPolis = polisMapper.mapNaarJson(polis);

            LOGGER.debug(ReflectionToStringBuilder.toString(jsonPolis, ToStringStyle.SHORT_PREFIX_STYLE));

            polisClient.opslaan(jsonPolis, 3L, "tAtId", "abc");

            //            Polis opgeslagenPolis = polisMapper.mapVanJson(polisClient.lees(polis.getId().toString()));

            //            assertThat(opgeslagenPolis,is(polis));
            //            assertThat(polisClient.lees(polis.getId().toString()),is(jsonPolis));
        }

    }


    private void vulPolis(Polis polis) {
        polis.setKenmerk(randomString(200));
        polis.setBetaalfrequentie(Betaalfrequentie.H);
        polis.setPolisNummer(randomString(200));
        polis.setDekking(randomString(200));
        //        polis.setEindDatum(LocalDate.now());
        //        polis.setIngangsDatum(LocalDate.now());
        polis.setOmschrijvingVerzekering(randomString(200));
        polis.setPremie(new Bedrag("123"));
        //        polis.setProlongatieDatum(LocalDate.now());
        polis.setStatus(StatusPolis.ACT);
        polis.setVerzekerdeZaak(randomString(200));
        //        polis.setWijzigingsDatum(LocalDate.now());
        polis.setMaatschappij(1L);
    }

    private String randomString(int lengte) {
        StringBuilder stringBuilder = new StringBuilder();

        while (stringBuilder.toString().length() < lengte) {
            stringBuilder.append(UUID.randomUUID().toString().replace("-", ""));
        }

        return stringBuilder.toString().substring(0, lengte);
    }

    @Test
    @Ignore
    public void testLijstSoortenPolissen() {
        List<String> particulier = filterPolissenOpSoort(SoortVerzekering.PARTICULIER);
        List<String> zakelijk = filterPolissenOpSoort(SoortVerzekering.ZAKELIJK);

        Collections.sort(particulier);
        Collections.sort(zakelijk);

        List<String> particulierOpgehaald = polisClient.alleParticulierePolisSoorten();
        List<String> zakelijkOpgehaald = polisClient.alleZakelijkePolisSoorten();

        Collections.sort(particulierOpgehaald);
        Collections.sort(zakelijkOpgehaald);

        assertThat(particulierOpgehaald, is(particulier));
        assertThat(zakelijkOpgehaald, is(zakelijk));
    }


    private List<String> filterPolissenOpSoort(final SoortVerzekering soortVerzekering) {
        return newArrayList(transform(filter(polissen, new Predicate<Polis>() {
            @Override
            public boolean apply(Polis polis) {
                return polis.getSoortVerzekering().equals(soortVerzekering);
            }
        }), new Function<Polis, String>() {
            @Override
            public String apply(Polis polis) {
                return polis.getSchermNaam();
            }
        }));
    }
}
