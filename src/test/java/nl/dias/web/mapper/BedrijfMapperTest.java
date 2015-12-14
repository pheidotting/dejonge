package nl.dias.web.mapper;

import nl.dias.domein.Adres;
import nl.dias.domein.Bedrijf;
import nl.dias.domein.Relatie;
import nl.dias.domein.json.JsonBedrijf;
import org.easymock.EasyMockRunner;
import org.easymock.EasyMockSupport;
import org.easymock.Mock;
import org.easymock.TestSubject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertEquals;
@RunWith(EasyMockRunner.class)
public class BedrijfMapperTest extends EasyMockSupport{
@TestSubject
    private BedrijfMapper mapper= new BedrijfMapper();
    private Bedrijf bedrijf;
    private JsonBedrijf jsonBedrijf;
    private Set<Bedrijf> bedrijven;
    private List<JsonBedrijf> jsonBedrijven;

    @Mock
    private OpmerkingMapper opmerkingMapper;

    @Before
    public void setUp() throws Exception {
        bedrijf = new Bedrijf();
        Adres adres = new Adres();
        adres.setHuisnummer(41L);
        adres.setPlaats("Zwartemeer");
        adres.setPostcode("7894AB");
        adres.setStraat("Eemslandweg");
        adres.setToevoeging("toevoeging");
        bedrijf.getAdressen().add(adres);
        bedrijf.setKvk("kvk");
        bedrijf.setNaam("NaamBedrijf");
        bedrijf.setId(1L);
        Relatie relatie = new Relatie();
        relatie.setId(2L);

        jsonBedrijf = new JsonBedrijf();
        //        jsonBedrijf.setHuisnummer("41");
        //        jsonBedrijf.setPlaats("Zwartemeer");
        //        jsonBedrijf.setPostcode("7894AB");
        //        jsonBedrijf.setStraat("Eemslandweg");
        //        jsonBedrijf.setToevoeging("toevoeging");
        jsonBedrijf.setKvk("kvk");
        jsonBedrijf.setNaam("NaamBedrijf");
        jsonBedrijf.setId("1");
        //        jsonBedrijf.setRelatie("2");

        bedrijven = new HashSet<Bedrijf>();
        bedrijven.add(bedrijf);

        jsonBedrijven = new ArrayList<JsonBedrijf>();
        jsonBedrijven.add(jsonBedrijf);
    }

    @Test
    public void testMapVanJson() {
        Bedrijf bedrijfVanJson = mapper.mapVanJson(jsonBedrijf);
        assertEquals(bedrijf, bedrijfVanJson);
    }

    @Test
    public void testMapNaarJson() {
        assertEquals(jsonBedrijf, mapper.mapNaarJson(bedrijf));
    }

}
