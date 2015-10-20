package nl.dias.service;

import com.sun.jersey.api.core.InjectParam;
import com.sun.jersey.core.header.FormDataContentDisposition;
import nl.dias.domein.Bijlage;
import nl.dias.domein.Relatie;
import nl.dias.repository.BijlageRepository;
import nl.dias.utils.Utils;
<<<<<<< HEAD
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
=======
>>>>>>> 561c015bc16347b4be76e8f0
import org.joda.time.LocalDateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Named;
import java.io.*;
import java.util.List;
import java.util.UUID;

@Named
public class BijlageService {
    private static final Logger LOGGER = LoggerFactory.getLogger(BijlageService.class);
    @InjectParam
    private BijlageRepository bijlageRepository;

    public List<Bijlage> alleBijlagesBijRelatie(Relatie relatie) {
        return bijlageRepository.alleBijlagesBijRelatie(relatie);
    }

    public void verwijderBijlage(Long id) {
        Bijlage bijlage = bijlageRepository.lees(id);

        bijlageRepository.verwijder(bijlage);
    }

    public void opslaan(Bijlage bijlage) {
        bijlageRepository.opslaan(bijlage);
    }

    public Bijlage uploaden(InputStream uploadedInputStream, FormDataContentDisposition fileDetail) {
        String[] exp = fileDetail.getFileName().split("//.");
        String extensie = exp[exp.length - 1];

        String identificatie = UUID.randomUUID().toString().replace("-","");

        LOGGER.debug("Gevonden extensie " + extensie);

        Bijlage bijlage=new Bijlage();
        bijlage.setS3Identificatie(identificatie);
        bijlage.setBestandsNaam(fileDetail.getFileName());
        bijlage.setUploadMoment(LocalDateTime.now());

        bijlageRepository.opslaan(bijlage);

        writeToFile(uploadedInputStream, Utils.getUploadPad() + "/" + identificatie);

        return bijlage;
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


    public void setBijlageRepository(BijlageRepository bijlageRepository) {
        this.bijlageRepository = bijlageRepository;
    }
}
