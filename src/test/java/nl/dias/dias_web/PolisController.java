package nl.dias.dias_web;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import nl.dias.domein.json.OpslaanPolis;
import nl.dias.domein.polis.AutoVerzekering;
import nl.dias.domein.polis.MobieleApparatuurVerzekering;
import nl.dias.domein.polis.Polis;
import nl.dias.domein.polis.WoonhuisVerzekering;

import org.joda.time.LocalDate;

import com.google.gson.Gson;

@Path("/polis")
public class PolisController {// extends AbstractController {

    private final Gson gson = new Gson();

    @POST
    @Path("/opslaan")
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.APPLICATION_JSON)
    public String opslaan(OpslaanPolis opslaanPolis) {
        return gson.toJson("");
    }

    @GET
    @Path("/allePolissen")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Polis> allePolissen(@QueryParam("relatie") Long strRelatie) {

        List<Polis> lijst = new ArrayList<>();
        Polis polis1 = new AutoVerzekering();
        polis1.setId(1L);
        polis1.setIngangsDatum(new LocalDate());
        polis1.setPolisNummer("polisNummer1");
        polis1.setProlongatieDatum(new LocalDate());
        Polis polis2 = new WoonhuisVerzekering();
        polis2.setId(2L);
        polis2.setIngangsDatum(new LocalDate());
        polis2.setPolisNummer("polisNummer2");
        polis2.setProlongatieDatum(new LocalDate());
        Polis polis3 = new MobieleApparatuurVerzekering();
        polis3.setId(3L);
        polis3.setIngangsDatum(new LocalDate());
        polis3.setPolisNummer("polisNummer3");
        polis3.setProlongatieDatum(new LocalDate());

        lijst.add(polis1);
        lijst.add(polis2);
        lijst.add(polis3);

        return lijst;
    }

    private LocalDate stringNaarLocalDate(String datum) {
        String[] d = datum.split("-");

        LocalDate ld = new LocalDate(Integer.parseInt(d[2]), Integer.parseInt(d[1]), Integer.parseInt(d[0]));

        return ld;
    }
}
