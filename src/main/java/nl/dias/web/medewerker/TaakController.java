package nl.dias.web.medewerker;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import nl.dias.domein.json.JsonTaak;

@Path("/taak")
public class TaakController {

    @Context
    private HttpServletRequest request;

    @GET
    @Path("/lijst")
    @Produces(MediaType.APPLICATION_JSON)
    public List<JsonTaak> alleTaken() {
        List<JsonTaak> taken = (List<JsonTaak>) request.getSession().getAttribute("taken");

        if (taken == null) {
            taken = new ArrayList<JsonTaak>();

            for (int i = 10; i < 20; i++) {
                taken.add(maakTaak(i));
            }

            request.getSession().setAttribute("taken", taken);
        }

        return taken;
    }

    @GET
    @Path("/vrijgeven")
    @Produces(MediaType.APPLICATION_JSON)
    public Response vrijgeven(@QueryParam("id") Long id) {
        List<JsonTaak> taken = (List<JsonTaak>) request.getSession().getAttribute("taken");

        int i = (int) (id - 10);
        taken.get(i).setMijnTaak(false);

        return Response.ok().build();
    }

    @GET
    @Path("/oppakken")
    @Produces(MediaType.APPLICATION_JSON)
    public Response oppakken(@QueryParam("id") Long id) {
        List<JsonTaak> taken = (List<JsonTaak>) request.getSession().getAttribute("taken");

        int i = (int) (id - 10);
        taken.get(i).setMijnTaak(true);

        return Response.ok().build();
    }

    private JsonTaak maakTaak(int i) {
        JsonTaak jsonTaak = new JsonTaak();

        jsonTaak.setSoortTaak("Taak met Soort " + i);
        jsonTaak.setAangemaaktDoor("Medewerker " + i);
        jsonTaak.setDatumTijdAfgerond(i + "-" + i + "-" + i + "" + i + " " + i + ":" + i);
        jsonTaak.setDatumTijdCreatie(i + "-" + i + "-" + i + "" + i + " " + i + ":" + i);
        jsonTaak.setDatumTijdOppakken(i + "-" + i + "-" + i + "" + i + " " + i + ":" + i);
        jsonTaak.setEindDatum(i + "-" + i + "-" + i + i);
        jsonTaak.setId(Long.valueOf(i));
        if ((i & 1) == 0) {
            jsonTaak.setMijnTaak(true);
        } else {
            jsonTaak.setMijnTaak(false);
        }
        jsonTaak.setOmschrijving("Omschrijving " + i);
        jsonTaak.setOpgepaktDoor("Medewerker " + i);
        jsonTaak.setStatus("Status " + i);

        return jsonTaak;
    }
}
