package nl.dias.dias_web;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
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
import nl.dias.domein.json.OpslaanPolis;
import nl.dias.domein.polis.AutoVerzekering;
import nl.dias.domein.polis.MobieleApparatuurVerzekering;
import nl.dias.domein.polis.Polis;
import nl.dias.domein.polis.WoonhuisVerzekering;

import org.joda.time.LocalDate;

import com.sun.jersey.core.header.FormDataContentDisposition;
import com.sun.jersey.multipart.FormDataParam;

@Path("/polis")
public class PolisController {// extends AbstractController {

    @GET
    @Path("/verwijder")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response verwijder(@QueryParam("id") Long id) {
        System.out.println(id);
        return Response.status(500).entity(new JsonFoutmelding("Nieh vunn'n")).build();
    }

    @POST
    @Path("/opslaan")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response opslaan(OpslaanPolis opslaanPolis) {
        // return Response.status(500).entity(new
        // JsonFoutmelding("jadajada")).build();
        return Response.status(202).entity(new JsonFoutmelding()).build();
    }

    @POST
    @Path("/upload")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response uploadFile(@FormDataParam("file") InputStream uploadedInputStream, @FormDataParam("file") FormDataContentDisposition fileDetail, @FormDataParam("polisNummer") String polisNummer) {
        // System.out.println(uploadedInputStream);
        // System.out.println(fileDetail);
        System.out.println("a");
        System.out.println(polisNummer);

        String uploadedFileLocation = "d://uploaded/" + fileDetail.getFileName();

        // save it
        // writeToFile(uploadedInputStream, uploadedFileLocation);

        String output = "File uploaded to : " + uploadedFileLocation;

        return Response.status(200).entity(output).build();

    }

    private void writeToFile(InputStream uploadedInputStream, String uploadedFileLocation) {
        try {
            OutputStream out = new FileOutputStream(new File(uploadedFileLocation));
            int read = 0;
            byte[] bytes = new byte[1024];

            out = new FileOutputStream(new File(uploadedFileLocation));
            while ((read = uploadedInputStream.read(bytes)) != -1) {
                out.write(bytes, 0, read);
            }
            out.flush();
            out.close();
        } catch (IOException e) {

            e.printStackTrace();
        }

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
