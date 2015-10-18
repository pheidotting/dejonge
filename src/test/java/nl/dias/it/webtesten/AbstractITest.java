package nl.dias.it.webtesten;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.json.JSONConfiguration;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by patrickheidotting on 13-10-15.
 */
public class AbstractITest {
    private GsonBuilder builder = new GsonBuilder();
    protected Gson gson = new Gson();

    private final static Logger LOGGER = LoggerFactory.getLogger(AbstractITest.class);

    protected void aanroepenUrlPost(String adres, Object object, String sessieCode) {
        Gson gson = builder.create();

        Client client = Client.create();

        WebResource webResource = client.resource(adres);
        String verstuurObject = gson.toJson(object);
        LOGGER.info("Versturen {}", verstuurObject);

        webResource.accept("application/json").type("application/json").header("sessieCode", sessieCode).post(ClientResponse.class, verstuurObject);
    }

    protected String uitvoerenGet(String adres, String sessieCode) {
        //        adres = adres.replace("{{poort}}", bepaalPoort());
        LOGGER.info("Aanroepen via GET " + adres);

        ClientConfig clientConfig = new DefaultClientConfig();
        clientConfig.getFeatures().put(JSONConfiguration.FEATURE_POJO_MAPPING, Boolean.TRUE);
        Client client = Client.create(clientConfig);
        WebResource webResource = client.resource(adres);
        ClientResponse response = webResource.accept("application/json").type("application/json").header("sessieCode", sessieCode).get(ClientResponse.class);
        if (response.getStatus() != 200) {
            throw new RuntimeException("Failed : HTTP error code : " + response.getStatus());
        }
        return response.getEntity(String.class);
    }

    protected <T> T uitvoerenGet(String adres, String sessieCode, Class<T> clazz, String... args) {
        LOGGER.debug("uitvoerenGet");

        Gson gson = builder.create();

        if (args != null) {
            for (String arg : args) {
                adres = adres + "/" + arg;
            }
        }
        LOGGER.info("Aanroepen via GET " + adres);

        ClientConfig clientConfig = new DefaultClientConfig();
        clientConfig.getFeatures().put(JSONConfiguration.FEATURE_POJO_MAPPING, Boolean.TRUE);
        Client client = Client.create(clientConfig);
        WebResource webResource = client.resource(adres);
        ClientResponse response = webResource.accept("application/json").type("application/json").header("sessieCode", sessieCode).get(ClientResponse.class);
        if (response.getStatus() != 200) {
            throw new RuntimeException("Failed : HTTP error code : " + response.getStatus());
        }

        return gson.fromJson(response.getEntity(String.class), clazz);
    }

    protected <T> List<T> uitvoerenGetLijst(String adres, String sessieCode, Class<T> clazz, String... args) {
        Gson gson = builder.create();

        if (args != null) {
            for (String arg : args) {
                adres = adres + "/" + arg;
            }
        }
        LOGGER.info("Aanroepen via GET " + adres);

        ClientConfig clientConfig = new DefaultClientConfig();
        clientConfig.getFeatures().put(JSONConfiguration.FEATURE_POJO_MAPPING, Boolean.TRUE);
        Client client = Client.create(clientConfig);
        WebResource webResource = client.resource(adres);
        ClientResponse response = webResource.accept("application/json").type("application/json").get(ClientResponse.class);
        if (response.getStatus() != 200) {
            throw new RuntimeException("Failed : HTTP error code : " + response.getStatus());
        }

        Type listOfTestObject = new TypeToken<List<T>>() {
        }.getType();
        LOGGER.debug(ReflectionToStringBuilder.toString(response));
        return gson.fromJson(response.getEntity(String.class), listOfTestObject);
    }
}
