package nl.dias.dias_web.grizzly;

import java.io.IOException;

import com.sun.grizzly.http.embed.GrizzlyWebServer;
import com.sun.grizzly.http.servlet.ServletAdapter;
import com.sun.jersey.spi.container.servlet.ServletContainer;

public class GrizzlyStart {
    private GrizzlyWebServer gws;
    private int poort;
    private String jerseyPad;

    public static void main(String[] args) {
        GrizzlyStart grizzlyStart = new GrizzlyStart();

        GrizzlyWebServer gws = grizzlyStart.getServer();

        try {
            System.out.println("Starten GrizzlyWebServer");
            gws.start();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
        System.out.println("GrizzlyWebServer draait");
        // gws.stop();
    }

    public GrizzlyWebServer getServer() {
        if (gws == null) {
            // Maak een nieuwe server
            System.out.println("Maak GrizzlyWebServer op poort " + getPoort());
            gws = new GrizzlyWebServer(getPoort());

            // Maak een adapter voor Jersey en plak deze aan de server
            ServletAdapter jerseyAdapterMedewerker = new ServletAdapter();
            jerseyAdapterMedewerker.addInitParameter("com.sun.jersey.config.property.packages", "nl.dias.dias_web.medewerker");
            jerseyAdapterMedewerker.setContextPath(getJerseyPad() + "medewerker");
            jerseyAdapterMedewerker.setServletInstance(new ServletContainer());
            jerseyAdapterMedewerker.addContextParameter("com.sun.jersey.api.json.POJOMappingFeature", "true");

            System.out.println("Jerseypad : " + getJerseyPad() + "medewerker");
            gws.addGrizzlyAdapter(jerseyAdapterMedewerker, new String[] { getJerseyPad() + "medewerker" });

            // Adapter maken voor de statische WebContent en plak deze aan de
            // server
            ServletAdapter staticContentAdapter = new ServletAdapter("WebContent");
            staticContentAdapter.setContextPath("/dias-web");
            staticContentAdapter.setHandleStaticResources(true);

            gws.addGrizzlyAdapter(staticContentAdapter, new String[] { "/dias-web" });

            // System.getProperties().put("java.naming.factory.initial",
            // "com.sun.jndi.cosnaming.CNCtxFactory");
            // MysqlDataSource mysqlDs = new MysqlDataSource();
            // String mysqlDataSourceDriver =
            // "com.mysql.jdbc.jdbc2.optional.MysqlDataSource";
            // System.out.println(mysqlDataSourceDriver);
            // Properties properties = new Properties();
            // properties.put("java.naming.factory.initial",
            // "com.sun.jndi.cosnaming.CNCtxFactory");
            // properties.put(Context.INITIAL_CONTEXT_FACTORY,
            // mysqlDataSourceDriver);
            // properties.put(Context.PROVIDER_URL, mysqlDataSourceDriver);//
            // "jdbc:mysql://89.18.180.239:3306/barbero_nw");
            // System.getProperties().put("java.naming.factory.initial",
            // mysqlDataSourceDriver);
            // Context ctx = null;
            // try {
            // ctx = new InitialContext(properties);
            // } catch (NamingException e) {
            // System.out.println("Som Ting Wong! 1");
            // System.out.println(e.getMessage());
            // return null;
            // }
            // try {
            // ctx.bind("jdbc/wczasy", mysqlDs);
            // } catch (NamingException e) {
            // System.out.println("Som Ting Wong! 2");
            // System.out.println(e.getMessage());
            // }
        }

        return gws;
    }

    public void setPoort(int poort) {
        this.poort = poort;
    }

    public int getPoort() {
        if (poort == 0) {
            poort = 7070;
        }
        return poort;
    }

    public String getJerseyPad() {
        if (jerseyPad == null) {
            jerseyPad = "/dejonge/rest/";
        }
        return jerseyPad;
    }

    public void setJerseyPad(String jerseyPad) {
        this.jerseyPad = jerseyPad;
    }
}
