package nl.dias.dias_web.controller;

import java.io.InputStream;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import nl.dias.domein.json.JsonFoutmelding;
import nl.dias.domein.json.JsonPolis;

import com.sun.jersey.core.header.FormDataContentDisposition;
import com.sun.jersey.multipart.FormDataParam;

@Path("/polisss")
public class PolisController {

    @GET
    @Path("/lees")
    @Produces(MediaType.APPLICATION_JSON)
    public JsonPolis lees(@QueryParam("id") String id) {
        return new JsonPolis();
    }

    @GET
    @Path("/beeindigen")
    @Produces(MediaType.APPLICATION_JSON)
    public void beeindigen(@QueryParam("id") Long id) {
    }

    @GET
    @Path("/lijst")
    @Produces(MediaType.APPLICATION_JSON)
    public List<JsonPolis> lijst(@QueryParam("relatieId") String relatieId) {

        return null;
    }

    @POST
    @Path("/opslaan")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response opslaan(JsonPolis jsonPolis) {
        return Response.status(200).entity(new JsonFoutmelding()).build();
    }

    @POST
    @Path("/upload")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response uploadFile(@FormDataParam("file") InputStream uploadedInputStream, @FormDataParam("file") FormDataContentDisposition fileDetail, @FormDataParam("polisNummer") String polisNummer) {

        return Response.status(200).entity("").build();

    }

    @GET
    @Path("/verwijder")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response verwijder(@QueryParam("id") Long id) {
        return Response.status(202).entity(new JsonFoutmelding()).build();
    }

}
