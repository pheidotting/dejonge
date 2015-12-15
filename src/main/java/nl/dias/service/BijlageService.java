package nl.dias.service;

import nl.dias.domein.Bijlage;
import nl.dias.domein.Relatie;
import nl.dias.repository.BijlageRepository;
import nl.dias.utils.Utils;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.joda.time.LocalDateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.inject.Inject;
import java.io.*;
import java.util.List;
import java.util.UUID;

@Service
public class BijlageService {
    private static final Logger LOGGER = LoggerFactory.getLogger(BijlageService.class);
    @Inject
    private BijlageRepository bijlageRepository;

    public List<Bijlage> alleBijlagesBijRelatie(Relatie relatie) {
        return bijlageRepository.alleBijlagesBijRelatie(relatie);
    }

    public void verwijderBijlage(Long id) {
        Bijlage bijlage = bijlageRepository.lees(id);

        LOGGER.debug("Verwijderen Bijlage {}", ReflectionToStringBuilder.toString(bijlage, ToStringStyle.SHORT_PREFIX_STYLE));

        bijlageRepository.verwijder(bijlage);
    }

    public void opslaan(Bijlage bijlage) {
        bijlageRepository.opslaan(bijlage);
    }

    public Bijlage uploaden(MultipartFile fileDetail) {
        String[] exp = fileDetail.getOriginalFilename().split("//.");
        String extensie = exp[exp.length - 1];

        String identificatie = UUID.randomUUID().toString().replace("-", "");

        LOGGER.debug("Gevonden extensie " + extensie);

        Bijlage bijlage = new Bijlage();
        bijlage.setS3Identificatie(identificatie);
        bijlage.setBestandsNaam(fileDetail.getOriginalFilename());
        bijlage.setUploadMoment(LocalDateTime.now());

        bijlageRepository.opslaan(bijlage);
        LOGGER.debug("Opslaan Bijlage {}", ReflectionToStringBuilder.toString(bijlage, ToStringStyle.SHORT_PREFIX_STYLE));

        try {
            LOGGER.debug("Opslaan bestand op schijf");
            writeToFile(fileDetail.getInputStream(), Utils.getUploadPad() + "/" + identificatie);
            LOGGER.debug("Bestand opgeslagen op schijft");
        } catch (IOException e) {
            LOGGER.error("Fout bij uploaden", e);
        }

        LOGGER.debug("{}", ReflectionToStringBuilder.toString(bijlage, ToStringStyle.SHORT_PREFIX_STYLE));

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
}
