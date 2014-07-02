package nl.dias.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.inject.Named;

import nl.lakedigital.archief.domain.ArchiefBestand;
import nl.lakedigital.archief.service.ArchiefService;

import org.apache.log4j.Logger;

import com.sun.jersey.api.core.InjectParam;
import com.sun.jersey.core.header.FormDataContentDisposition;

@Named
public class BijlageService {
    private static final Logger LOGGER = Logger.getLogger(BijlageService.class);
    @InjectParam
    private ArchiefService archiefService;

    public String uploaden(InputStream uploadedInputStream, FormDataContentDisposition fileDetail) {
        String[] exp = fileDetail.getFileName().split("//.");
        String extensie = exp[exp.length - 1];

        String identificatie = null;

        LOGGER.debug("Gevonden extensie " + extensie);
        try {
            File tempFile = File.createTempFile("dias", "upload." + extensie);

            // save it
            writeToFile(uploadedInputStream, tempFile.getAbsolutePath());

            // File file = new File(uploadedFileLocation);
            ArchiefBestand archiefBestand = new ArchiefBestand();
            archiefBestand.setBestandsnaam(fileDetail.getFileName());
            archiefBestand.setBestand(tempFile);

            LOGGER.debug("naar s3");
            archiefService.setBucketName("dias");
            identificatie = archiefService.opslaan(archiefBestand);

            LOGGER.debug("Opgeslagen naar S3, identificatie terug : " + identificatie);
        } catch (IOException e) {
            LOGGER.error("Fout bij opslaan bijlage ", e);
        }

        return identificatie;
    }

    public void writeToFile(InputStream uploadedInputStream, String uploadedFileLocation) {
        int read = 0;
        byte[] bytes = new byte[1024];

        OutputStream out = null;
        try {
            out = new FileOutputStream(new File(uploadedFileLocation));
            while ((read = uploadedInputStream.read(bytes)) != -1) {
                out.write(bytes, 0, read);
            }
        } catch (Exception e) {
            LOGGER.error("fout bij opslaan bijlage nar schijf", e);
        } finally {
            try {
                if (out != null) {
                    out.flush();
                }
            } catch (IOException e) {
                LOGGER.error("fout bij opslaan bijlage nar schijf", e);
            } finally {
                try {
                    if (out != null) {
                        out.close();
                    }
                } catch (IOException e) {
                    LOGGER.error("fout bij opslaan bijlage nar schijf", e);
                }
            }
        }
    }
}
