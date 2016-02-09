//package nl.dias.web.mapper;
//
//import nl.dias.domein.Bijlage;
//import nl.dias.domein.polis.AutoVerzekering;
//import nl.lakedigital.djfc.commons.json.JsonBijlage;
//import org.easymock.EasyMockSupport;
//import org.junit.After;
//import org.junit.Before;
//import org.junit.Test;
//
//import java.util.ArrayList;
//import java.util.HashSet;
//import java.util.List;
//import java.util.Set;
//
//import static org.junit.Assert.assertEquals;
//
//public class BijlageMapperTest extends EasyMockSupport {
//    private BijlageMapper mapper;
//
//    private Bijlage bijlage;
//    private JsonBijlage jsonBijlage;
//    private Set<Bijlage> bijlages;
//    private List<JsonBijlage> jsonBijlages;
//
//    @Before
//    public void setUp() throws Exception {
//        mapper = new BijlageMapper();
//
//        bijlage = new Bijlage();
//        bijlage.setId(1L);
////        bijlage.setPolis(2L);//new AutoVerzekering());
////        bijlage.setS3Identificatie(null);
//        //        bijlage.setSoortBijlage(SoortBijlage.POLIS);
//
//        jsonBijlage = new JsonBijlage();
//        jsonBijlage.setBestandsNaam(null);
//        jsonBijlage.setId("1");
//        //        jsonBijlage.setSoortBijlage("Polis");
//        jsonBijlage.setUrl(null);
//
//        bijlages = new HashSet<Bijlage>();
//        jsonBijlages = new ArrayList<JsonBijlage>();
//
//        bijlages.add(bijlage);
//        jsonBijlages.add(jsonBijlage);
//    }
//
//    @After
//    public void tearDown() throws Exception {
//        verifyAll();
//    }
//
//    @Test
//    public void testMapVanJson() {
//        replayAll();
//
//        assertEquals(bijlage, mapper.mapVanJson(jsonBijlage));
//    }
//
//    @Test
//    public void testMapAllVanJson() {
//        replayAll();
//
//        assertEquals(bijlages, mapper.mapAllVanJson(jsonBijlages));
//    }
//
//    @Test
//    public void testMapNaarJson() {
//        replayAll();
//
//        assertEquals(jsonBijlage, mapper.mapNaarJson(bijlage));
//    }
//
//    @Test
//    public void testMapAllNaarJson() {
//        replayAll();
//
//        assertEquals(jsonBijlages, mapper.mapAllNaarJson(bijlages));
//    }
//
//}
