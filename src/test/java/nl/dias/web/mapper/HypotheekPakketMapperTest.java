package nl.dias.web.mapper;

import static org.easymock.EasyMock.expect;
import static org.junit.Assert.assertEquals;
import nl.dias.domein.Hypotheek;
import nl.dias.domein.HypotheekPakket;
import nl.dias.domein.json.JsonHypotheek;
import nl.dias.domein.json.JsonHypotheekPakket;

import org.easymock.EasyMockSupport;
import org.junit.Before;
import org.junit.Test;

public class HypotheekPakketMapperTest extends EasyMockSupport {
    private HypotheekPakketMapper mapper;
    private HypotheekMapper hypotheekMapper;

    private HypotheekPakket hypotheekPakket;
    private Hypotheek hypotheek;
    private JsonHypotheekPakket jsonHypotheekPakket;
    private JsonHypotheek jsonHypotheek;

    @Before
    public void setUp() throws Exception {
        mapper = new HypotheekPakketMapper();

        hypotheekMapper = createMock(HypotheekMapper.class);
        mapper.setHypotheekMapper(hypotheekMapper);

        hypotheekPakket = new HypotheekPakket();
        hypotheek = createMock(Hypotheek.class);
        hypotheekPakket.getHypotheken().add(hypotheek);

        jsonHypotheekPakket = new JsonHypotheekPakket();
        jsonHypotheek = createMock(JsonHypotheek.class);
        jsonHypotheekPakket.getHypotheken().add(jsonHypotheek);
    }

    @Test
    public void testMapVanJsonJsonHypotheekPakket() {
    }

    @Test
    public void testMapNaarJsonHypotheekPakket() {
        expect(hypotheekMapper.mapAllNaarJson(hypotheekPakket.getHypotheken())).andReturn(jsonHypotheekPakket.getHypotheken());

        replayAll();

        assertEquals(jsonHypotheekPakket, mapper.mapNaarJson(hypotheekPakket));
    }

}
