package nl.dias.web;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Date;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

import nl.dias.domein.Bijlage;
import nl.dias.service.BijlageService;
import nl.dias.service.PolisService;
import nl.lakedigital.archief.domain.ArchiefBestand;
import nl.lakedigital.archief.service.ArchiefService;

import org.apache.log4j.Logger;

import com.sun.jersey.api.core.InjectParam;

@Path("/bijlage")
public class BijlageController {
    private final static Logger LOGGER = Logger.getLogger(BijlageController.class);

    @InjectParam
    private ArchiefService archiefService;
    @InjectParam
    private PolisService polisService;
    @InjectParam
    private BijlageService bijlageService;

    @GET
    @Path("/download")
    @Produces("application/pdf")
    public Response getFile(@QueryParam("bijlageId") String bijlageId) throws IOException {
        archiefService.setBucketName("dias");
        LOGGER.debug("Ophalen bijlage met id " + bijlageId);

        Bijlage bijlage = polisService.leesBijlage(Long.parseLong(bijlageId));
        ArchiefBestand archiefBestand = archiefService.ophalen(bijlage.getS3Identificatie(), false);

        File tmpFile = File.createTempFile("dias", "download");

        try {
            bijlageService.writeToFile(new FileInputStream(archiefBestand.getBestand()), tmpFile.toString());
        } catch (FileNotFoundException e1) {
            LOGGER.error(e1.getMessage());
        }

        Date fileDate = new Date(archiefBestand.getBestand().lastModified());
        try {
            return Response.ok(new FileInputStream(archiefBestand.getBestand())).lastModified(fileDate).build();
        } catch (FileNotFoundException e) {
            LOGGER.error(e.getMessage());
            return Response.noContent().build();
        }
    }

}
