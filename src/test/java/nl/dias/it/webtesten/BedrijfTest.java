package nl.dias.it.webtesten;

import nl.dias.domein.json.JsonAdres;
import nl.dias.domein.json.JsonBedrijf;
import nl.dias.domein.json.JsonContactPersoon;
import nl.dias.domein.json.JsonTelefoonnummer;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class BedrijfTest extends AbstractITest {
    private static final Logger LOGGER = LoggerFactory.getLogger(BedrijfTest.class);

    private String sessieCode = "dcd4da00-aed8-4c60-9132-d39beaa2ad72";
    private final String BEDRIJF_OPSLAAN = "http://localhost:8080/dejonge/rest/medewerker/bedrijf/opslaan";
    private final String BEDRIJF_VERWIJDEREN = "http://localhost:8080/dejonge/rest/medewerker/bedrijf/verwijder";
    private final String BEDRIJF_LIJST = "http://localhost:8080/dejonge/rest/medewerker/bedrijf/lijst";

    @Test
    public void testMetTussentijdsOpslaan() {
        testOpslaanBedrijf(true);
    }

    @Test
    public void testZonderTussentijdsOpslaan() {
        testOpslaanBedrijf(false);
    }

    public void testOpslaanBedrijf(boolean tussentijdsOpslaan) {
        //We gaan eerst een bedrijf opslaan met alleen een naam
        JsonBedrijf jsonBedrijf = new JsonBedrijf();
        jsonBedrijf.setNaam(genereerRandomString(255));

        JsonBedrijf jsonBedrijfOpgehaald = null;

        if (tussentijdsOpslaan) {
            aanroepenUrlPost(BEDRIJF_OPSLAAN, jsonBedrijf, sessieCode);

            jsonBedrijfOpgehaald = leesBedrijf(jsonBedrijf.getNaam());

            jsonBedrijf.setId(jsonBedrijfOpgehaald.getId());
            assertEquals(jsonBedrijf, jsonBedrijfOpgehaald);
        }

        jsonBedrijf.setKvk(genereerRandomString(8));
        jsonBedrijf.setRechtsvorm(genereerRandomString(200));
        jsonBedrijf.setInternetadres(genereerRandomString(200));
        jsonBedrijf.setHoedanigheid(genereerRandomString(200));
        jsonBedrijf.setcAoVerplichtingen(genereerRandomString(200));
        jsonBedrijf.setEmail(genereerRandomString(200));

        if (tussentijdsOpslaan) {
            aanroepenUrlPost(BEDRIJF_OPSLAAN, jsonBedrijf, sessieCode);

            jsonBedrijfOpgehaald = leesBedrijf(jsonBedrijf.getNaam());
            assertEquals(jsonBedrijf, jsonBedrijfOpgehaald);
        }

        //Adres toevoegen
        JsonAdres jsonAdres = maakJsonAdres(jsonBedrijf.getId());
        jsonBedrijf.getAdressen().add(jsonAdres);
        if (tussentijdsOpslaan) {
            aanroepenUrlPost(BEDRIJF_OPSLAAN, jsonBedrijf, sessieCode);

            jsonBedrijfOpgehaald = leesBedrijf(jsonBedrijf.getNaam());
            LOGGER.debug(ReflectionToStringBuilder.toString(jsonBedrijfOpgehaald, ToStringStyle.SHORT_PREFIX_STYLE));
            assertEquals(jsonBedrijf, jsonBedrijfOpgehaald);
            assertEquals(1, jsonBedrijfOpgehaald.getAdressen().size());
        }

        //Nog een adres toevoegen
        JsonAdres jsonAdres2 = maakJsonAdres(jsonBedrijf.getId());
        jsonBedrijf.getAdressen().add(jsonAdres2);
        if (tussentijdsOpslaan) {
            aanroepenUrlPost(BEDRIJF_OPSLAAN, jsonBedrijf, sessieCode);

            jsonBedrijfOpgehaald = leesBedrijf(jsonBedrijf.getNaam());
            LOGGER.debug(ReflectionToStringBuilder.toString(jsonBedrijfOpgehaald, ToStringStyle.SHORT_PREFIX_STYLE));
            assertEquals(jsonBedrijf, jsonBedrijfOpgehaald);
            assertEquals(2, jsonBedrijfOpgehaald.getAdressen().size());
        }

        //Contactpersoon toevoegen
        JsonContactPersoon jsonContactPersoon = maakJsonContactPersoon(jsonBedrijf.getId());
        jsonBedrijf.getContactpersonen().add(jsonContactPersoon);
        if (tussentijdsOpslaan) {
            aanroepenUrlPost(BEDRIJF_OPSLAAN, jsonBedrijf, sessieCode);

            jsonBedrijfOpgehaald = leesBedrijf(jsonBedrijf.getNaam());
            jsonContactPersoon.setId(jsonBedrijfOpgehaald.getContactpersonen().get(0).getId());
            LOGGER.debug(ReflectionToStringBuilder.toString(jsonBedrijfOpgehaald, ToStringStyle.SHORT_PREFIX_STYLE));
            assertEquals(jsonBedrijf, jsonBedrijfOpgehaald);
            assertEquals(1, jsonBedrijfOpgehaald.getContactpersonen().size());
        }

        //Telefoonnummer bij Bedrijf
        JsonTelefoonnummer jsonTelefoonnummerBijBedrijf = maakJsonTelefoonnummer(jsonBedrijf.getId(), null);
        jsonBedrijf.getTelefoonnummers().add(jsonTelefoonnummerBijBedrijf);

        if (tussentijdsOpslaan) {
            aanroepenUrlPost(BEDRIJF_OPSLAAN, jsonBedrijf, sessieCode);

            jsonBedrijfOpgehaald = leesBedrijf(jsonBedrijf.getNaam());
            jsonBedrijf.getTelefoonnummers().get(0).setId(jsonBedrijfOpgehaald.getTelefoonnummers().get(0).getId());
            LOGGER.debug(ReflectionToStringBuilder.toString(jsonBedrijfOpgehaald, ToStringStyle.SHORT_PREFIX_STYLE));
            assertEquals(jsonBedrijf, jsonBedrijfOpgehaald);
            assertEquals(1, jsonBedrijfOpgehaald.getContactpersonen().size());
        }

        //Telefoonnummer opslaan bij een Contactpersoon
        JsonTelefoonnummer jsonTelefoonnummerBijContactPersoon = maakJsonTelefoonnummer(null, null);
        jsonContactPersoon.getTelefoonnummers().add(jsonTelefoonnummerBijContactPersoon);

        if (tussentijdsOpslaan) {
            aanroepenUrlPost(BEDRIJF_OPSLAAN, jsonBedrijf, sessieCode);

            jsonBedrijfOpgehaald = leesBedrijf(jsonBedrijf.getNaam());
            jsonBedrijf.getContactpersonen().get(0).getTelefoonnummers().get(0).setId(jsonBedrijfOpgehaald.getContactpersonen().get(0).getTelefoonnummers().get(0).getId());
            jsonBedrijf.getTelefoonnummers().get(0).setId(jsonBedrijfOpgehaald.getTelefoonnummers().get(0).getId());
            LOGGER.debug(ReflectionToStringBuilder.toString(jsonBedrijfOpgehaald, ToStringStyle.SHORT_PREFIX_STYLE));
            assertEquals(jsonBedrijf, jsonBedrijfOpgehaald);
            assertEquals(1, jsonBedrijfOpgehaald.getContactpersonen().size());
        }

        //Opslaan
        aanroepenUrlPost(BEDRIJF_OPSLAAN, jsonBedrijf, sessieCode);
        jsonBedrijfOpgehaald = leesBedrijf(jsonBedrijf.getNaam());
        jsonBedrijf.getTelefoonnummers().get(0).setId(jsonBedrijfOpgehaald.getTelefoonnummers().get(0).getId());
        LOGGER.debug(ReflectionToStringBuilder.toString(jsonBedrijfOpgehaald, ToStringStyle.SHORT_PREFIX_STYLE));
        jsonBedrijf.setId(jsonBedrijfOpgehaald.getId());
        assertEquals(jsonBedrijf, jsonBedrijfOpgehaald);
        assertEquals(1, jsonBedrijfOpgehaald.getTelefoonnummers().size());


        jsonContactPersoon.setAchternaam("Mijn gewijzigde achternaam");
        aanroepenUrlPost(BEDRIJF_OPSLAAN, jsonBedrijf, sessieCode);

        jsonBedrijfOpgehaald = leesBedrijf(jsonBedrijf.getNaam());
        assertEquals("Mijn gewijzigde achternaam", jsonBedrijfOpgehaald.getContactpersonen().get(0).getAchternaam());


        uitvoerenGet(BEDRIJF_VERWIJDEREN + "?id=" + jsonBedrijfOpgehaald.getId(), sessieCode);
    }

    private JsonBedrijf leesBedrijf(String naam) {
        List<JsonBedrijf> bedrijven = uitvoerenGetLijst(BEDRIJF_LIJST, sessieCode, "?zoekTerm=" + naam);

        return bedrijven.get(0);
    }

    private JsonAdres maakJsonAdres(String bedrijfsId) {
        JsonAdres jsonAdres = new JsonAdres();

        jsonAdres.setHuisnummer(3223L);
        jsonAdres.setPlaats(genereerRandomString(255));
        jsonAdres.setPostcode(genereerRandomString(6));
        jsonAdres.setSoortAdres("WOONADRES");
        jsonAdres.setStraat(genereerRandomString(255));
        jsonAdres.setToevoeging(genereerRandomString(255));
        jsonAdres.setBedrijf(bedrijfsId);

        return jsonAdres;
    }

    private JsonContactPersoon maakJsonContactPersoon(String bedrijfsId) {
        JsonContactPersoon jsonContactPersoon = new JsonContactPersoon();

        jsonContactPersoon.setVoornaam(genereerRandomString(255));
        jsonContactPersoon.setTussenvoegsel(genereerRandomString(255));
        jsonContactPersoon.setFunctie(genereerRandomString(200));
        jsonContactPersoon.setAchternaam(genereerRandomString(255));
        jsonContactPersoon.setEmailadres(genereerRandomString(255));

        if (bedrijfsId != null) {
            jsonContactPersoon.setBedrijf(Long.valueOf(bedrijfsId));
        }

        return jsonContactPersoon;
    }

    private JsonTelefoonnummer maakJsonTelefoonnummer(String bedrijfsId, String contactpersoonId) {
        JsonTelefoonnummer jsonTelefoonnummer = new JsonTelefoonnummer();

        jsonTelefoonnummer.setTelefoonnummer(genereerRandomString(11));
        jsonTelefoonnummer.setSoort("MOBIEL");
        jsonTelefoonnummer.setOmschrijving(genereerRandomString(2500));

        if (bedrijfsId != null) {
            jsonTelefoonnummer.setBedrijf(bedrijfsId);
        }
        if (contactpersoonId != null) {
            jsonTelefoonnummer.setContactpersoon(Long.valueOf(contactpersoonId));
        }

        return jsonTelefoonnummer;
    }
}
