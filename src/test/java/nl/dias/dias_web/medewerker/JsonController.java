package nl.dias.dias_web.medewerker;

import nl.dias.domein.json.JsonLog4Javascript;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/overig")
public class JsonController {

    @GET
    @Path("/lijstVerzekeringsMaatschappijen")
    @Produces(MediaType.APPLICATION_JSON)
    public List<String> lijstVerzekeringsMaatschappijen() {

        List<String> ret = new ArrayList<String>();
        ret.add("Kies een maatschappij...");
        ret.add("ASR Verzekeringen");
        ret.add("Achmea");
        ret.add("Aegon");
        ret.add("Agis");
        ret.add("AllSecur");
        ret.add("Allianz Nederland");
        ret.add("Amersfoortse (de)");
        ret.add("Avero Achmea");
        ret.add("Crisper");
        ret.add("DAS");
        ret.add("Delta Lloyd");
        ret.add("Ditzo");
        ret.add("Erasmus");
        ret.add("Europeesche");
        ret.add("Generali");
        ret.add("Goudse (de)");
        ret.add("Kilometerverzekering (de)");
        ret.add("Klaverblad");
        ret.add("Kruidvat");
        ret.add("London Verzekeringen");
        ret.add("Monuta");
        ret.add("Nationale Nederlanden");
        ret.add("OHRA");
        ret.add("Onderlinge");
        ret.add("Polis Direct");
        ret.add("Reaal");
        ret.add("SNS Bank");
        ret.add("Turien \u0026 Co");
        ret.add("Unigarant");
        ret.add("Unive");
        ret.add("VKG");
        ret.add("Verzekeruzelf.nl");
        ret.add("Voogd en Voogd");
        ret.add("Zelf.nl");

        return ret;
    }

    @GET
    @Path("/extraInfo")
    @Produces(MediaType.TEXT_PLAIN)
    public String extraInfo() {
        return "DIAS Webtest";

    }

    @GET
    @Path("/soortenSchade")
    @Produces(MediaType.APPLICATION_JSON)
    public List<SoortSchade> soortenSchade(@QueryParam("query") String query) {
        List<SoortSchade> soorten = new ArrayList<SoortSchade>();

        soorten.add(new SoortSchade("Soort1"));
        soorten.add(new SoortSchade("Soort2"));
        soorten.add(new SoortSchade("Soort3"));
        soorten.add(new SoortSchade("Soort4"));
        soorten.add(new SoortSchade("Soort5"));

        return soorten;
    }

    private class SoortSchade {
        private final String value;

        public SoortSchade(String tekst) {
            this.value = tekst;
        }
    }

    @GET
    @Path("/lijstStatusSchade")
    @Produces(MediaType.APPLICATION_JSON)
    public List<String> lijstStatusSchade() {
        List<String> ret = new ArrayList<String>();

        ret.add("");
        ret.add("Status 1");
        ret.add("Status 2");

        return ret;
    }

    @POST
    @Path("/log4javascript")
    @Consumes(MediaType.APPLICATION_JSON)
    public void log4javascript(JsonLog4Javascript jsonLog4Javascript){
    }
}
