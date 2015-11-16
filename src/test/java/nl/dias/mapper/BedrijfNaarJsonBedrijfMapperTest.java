package nl.dias.mapper;

import nl.dias.domein.Adres;
import nl.dias.domein.Bedrijf;
import nl.dias.domein.json.JsonBedrijf;
import org.easymock.EasyMockRunner;
import org.easymock.EasyMockSupport;
import org.easymock.Mock;
import org.easymock.TestSubject;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

@RunWith(EasyMockRunner.class)
public class BedrijfNaarJsonBedrijfMapperTest extends EasyMockSupport {

    @TestSubject
    private BedrijfNaarJsonBedrijfMapper mapper = new BedrijfNaarJsonBedrijfMapper();

    @Mock
    private AdresNaarJsonAdresMapper adresMapper;

    @Test
    public void testMap() throws Exception {
        Bedrijf bedrijf = new Bedrijf();
        bedrijf.setId(2L);
        bedrijf.setNaam("Naam");
        bedrijf.setKvk("kvk");

        JsonBedrijf jsonBedrijf = new JsonBedrijf();
        jsonBedrijf.setId("2");
        jsonBedrijf.setNaam("Naam");
        jsonBedrijf.setKvk("kvk");

        assertEquals(jsonBedrijf, mapper.map(bedrijf));
    }

    @Test
    public void testIsVoorMij() throws Exception {
        assertTrue(mapper.isVoorMij(new Bedrijf()));
        assertFalse(mapper.isVoorMij(new Adres()));
    }
}