package nl.dias.mapper;

import nl.dias.builders.BedrijfBuilder;
import nl.dias.domein.Bedrijf;
import nl.dias.domein.ContactPersoon;
import nl.dias.domein.Telefoonnummer;
import nl.dias.domein.json.JsonBedrijf;
import nl.dias.domein.json.JsonContactPersoon;
import nl.dias.domein.json.JsonTelefoonnummer;
import nl.dias.service.BedrijfService;
import org.easymock.EasyMockRunner;
import org.easymock.EasyMockSupport;
import org.easymock.Mock;
import org.easymock.TestSubject;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.HashSet;

import static org.easymock.EasyMock.expect;
import static org.junit.Assert.*;

@RunWith(EasyMockRunner.class)
public class JsonBedrijfNaarBedrijfMapperTest extends EasyMockSupport {

    @TestSubject
    private JsonBedrijfNaarBedrijfMapper mapper = new JsonBedrijfNaarBedrijfMapper();

    @Mock
    private BedrijfService bedrijfService;
    @Mock
    private JsonTelefoonnummerNaarTelefoonnummerMapper jsonTelefoonnummerNaarTelefoonnummerMapper;
    @Mock
    private JsonContactPersoonNaarContactPersoonMapper jsonContactPersoonNaarContactPersoonMapper;

    @Test
    public void testMap() throws Exception {
        BedrijfBuilder bedrijfBuilder = new BedrijfBuilder().withId(2L).withNaam("Naam").withKvk("kvk").withCAoVerplichtingen("caoVerplichtingen").withEmail("email").withHoedanigheid("hoedanigheid").withInternetadres("internetadres").withRechtsvorm("rechtsvorm");

        Bedrijf bedrijf = bedrijfBuilder.buildBedrijf();
        JsonBedrijf jsonBedrijf = bedrijfBuilder.buildJsonBedrijf();

        expect(bedrijfService.lees(2L)).andReturn(bedrijf);

        expect(jsonTelefoonnummerNaarTelefoonnummerMapper.mapAllNaarSet(new ArrayList<JsonTelefoonnummer>(), bedrijf)).andReturn(new HashSet<Telefoonnummer>());
        expect(jsonContactPersoonNaarContactPersoonMapper.mapAllNaarSet(new ArrayList<JsonContactPersoon>(), bedrijf)).andReturn(new HashSet<ContactPersoon>());

        replayAll();

        assertEquals(bedrijf, mapper.map(jsonBedrijf));
    }

    @Test
    public void testIsVoorMij() throws Exception {
        assertTrue(mapper.isVoorMij(new JsonBedrijf()));
        assertFalse(mapper.isVoorMij(new Bedrijf()));
    }
}