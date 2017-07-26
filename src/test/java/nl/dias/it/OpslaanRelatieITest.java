package nl.dias.it;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.google.gson.Gson;
import nl.lakedigital.djfc.commons.json.JsonRelatie;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;

import static com.github.tomakehurst.wiremock.client.WireMock.configureFor;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;


public class OpslaanRelatieITest extends AbstractITest {
    private final static Logger LOGGER = LoggerFactory.getLogger(OpslaanRelatieITest.class);
    public static Long id = 6L;

    @Test
    public void testOpslaanNieuweRelatieMetAlleenVoorEnAchternaam() {
        String uuid = UUID.randomUUID().toString();

        WireMockServer wireMockServer = new WireMockServer(8089);
        configureFor("localhost", 8089);

        wireMockServer.start();

        JsonRelatie relatie = new JsonRelatie();

        relatie.setAchternaam("Parker");
        relatie.setVoornaam("Peter");

        test(relatie, uuid, String.valueOf(OpslaanRelatieITest.id));
        OpslaanRelatieITest.id = OpslaanRelatieITest.id + 1;

        wireMockServer.stop();
    }

    @Test
    public void testOpslaanNieuweRelatieVolledigGevuld() {
        String uuid = UUID.randomUUID().toString();

        WireMockServer wireMockServer = new WireMockServer(8089);
        configureFor("localhost", 8089);

        wireMockServer.start();

        JsonRelatie relatie = new JsonRelatie();

        relatie.setAchternaam("Wayne");
        relatie.setVoornaam("Bruce");
        relatie.setTussenvoegsel("van");
        relatie.setGeboorteDatum("1979-09-06");
        relatie.setEmailadres("a@b.c");
        relatie.setRoepnaam("Batman");
        relatie.setBsn("123456789");
        relatie.setOverlijdensdatum("2011-02-03");
        relatie.setGeslacht("Man");
        relatie.setBurgerlijkeStaat("Ongehuwd");

        JsonRelatie result = test(relatie, uuid, String.valueOf(OpslaanRelatieITest.id));
        OpslaanRelatieITest.id = OpslaanRelatieITest.id + 1;

        wireMockServer.stop();

        assertThat(result.getTussenvoegsel(), is(relatie.getTussenvoegsel()));
        assertThat(result.getGeboorteDatum(), is(relatie.getGeboorteDatum()));
        //        assertThat(result.getEmailadres(), is(relatie.getEmailadres()));
        assertThat(result.getRoepnaam(), is(relatie.getRoepnaam()));
        assertThat(result.getBsn(), is(relatie.getBsn()));
        assertThat(result.getOverlijdensdatum(), is(relatie.getOverlijdensdatum()));
        assertThat(result.getGeslacht(), is(relatie.getGeslacht()));
        assertThat(result.getBurgerlijkeStaat(), is(relatie.getBurgerlijkeStaat()));
    }

    private JsonRelatie test(JsonRelatie relatie, String uuid, String relatieId) {
        Stub stubIdentificatieZoekOpSoortEnId = stubIdentificatieZoekOpSoortEnId(zoekIdentificatieResponse(uuid, relatieId));

        String id = doePost(relatie, GEBRUIKER_OPSLAAN, UUID.randomUUID().toString());

        assertThat(id, is(uuid));

        Stub stubIdentificatieZoekenOpCode = stubIdentificatieZoekenOpCode(id, zoekIdentificatieResponse(uuid, relatieId));
        Stub stubZoekAlleAdressen = stubZoekAlleAdressen(adressenResponse());
        Stub stubZoekAlleBijlages = stubZoekAlleBijlages(bijlageResponse());
        Stub stubGroepenBijlage = stubGroepenBijlage(groepenBijlageResponse());
        Stub stubRekeningnummer = stubRekeningnummer(rekeningnummerResponse());
        Stub stubTelefoonnummer = stubTelefoonnummer(telefoonnummerResponse());
        Stub stubOpmerking = stubOpmerking(opmerkingResponse());
        Stub stubPolis = stubPolis(polisResponse());

        String rel = doeGet(RELATIE_LEZEN + "/" + id);

        JsonRelatie result = new Gson().fromJson(rel, JsonRelatie.class);

        stubIdentificatieZoekOpSoortEnId.verifyStub(relatieId);
        stubIdentificatieZoekenOpCode.verifyStub(relatieId);
        stubZoekAlleAdressen.verifyStub(relatieId);
        stubZoekAlleBijlages.verifyStub(relatieId);
        stubGroepenBijlage.verifyStub(relatieId);
        stubRekeningnummer.verifyStub(relatieId);
        stubTelefoonnummer.verifyStub(relatieId);
        stubOpmerking.verifyStub(relatieId);
        stubPolis.verifyStub(relatieId);

        assertThat(result.getIdentificatie(), is(uuid));
        assertThat(result.getAchternaam(), is(relatie.getAchternaam()));
        assertThat(result.getVoornaam(), is(relatie.getVoornaam()));

        return result;
    }

    private String zoekIdentificatieResponse(String identificatieS, String id) {
        return "<ZoekIdentificatieResponse><identificaties><identificaties><id>63</id><identificatie>" + identificatieS + "</identificatie><soortEntiteit>RELATIE</soortEntiteit><entiteitId>" + id + "</entiteitId></identificaties></identificaties></ZoekIdentificatieResponse>";
    }

    private String adressenResponse() {
        return "<OpvragenAdressenResponse></OpvragenAdressenResponse>";
    }

    private String bijlageResponse() {
        return "<OpvragenBijlagesResponse></OpvragenBijlagesResponse>";
    }

    private String groepenBijlageResponse() {
        return "<OpvragenGroepBijlagesResponse></OpvragenGroepBijlagesResponse>";
    }

    private String rekeningnummerResponse() {
        return "<OpvragenRekeningNummersResponse></OpvragenRekeningNummersResponse>";
    }

    private String telefoonnummerResponse() {
        return "<OpvragenTelefoonnummersResponse></OpvragenTelefoonnummersResponse>";
    }

    private String opmerkingResponse() {
        return "<OpvragenOpmerkingenResponse></OpvragenOpmerkingenResponse>";
    }

    private String polisResponse() {
        return "<OpvragenPolissenResponse></OpvragenPolissenResponse>";
    }

    private String telefonieResponse() {
        return "<Map/>";
    }
}
