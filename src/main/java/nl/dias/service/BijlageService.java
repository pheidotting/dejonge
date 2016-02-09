package nl.dias.service;

import nl.dias.domein.Bedrijf;
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
    private static final String FOUTMELDINGBIJOPSLAANBIJLAGE = "fout bij opslaan bijlage nar schijf\"   ";

    @Inject
    private BijlageRepository bijlageRepository;
    @Inject
    private BedrijfService bedrijfService;

    public void wijzigOmschrijvingBijlage(Long id, String nieuweNaam) {
        Bijlage bijlage = bijlageRepository.leesBijlage(id);

        bijlage.setOmschrijving(nieuweNaam);

        bijlageRepository.opslaan(bijlage);
    }

    public List<Bijlage> alleBijlagesBijRelatie(Relatie relatie) {
        return bijlageRepository.alleBijlagesBijRelatie(relatie);
    }

    public List<Bijlage> allesBijlagesBijPolis(Long polis) {
        return bijlageRepository.allesBijlagesBijPolis(polis);
    }

    public List<Bijlage> alleBijlagesBijSchade(Long schade) {
        return bijlageRepository.alleBijlagesBijSchade(schade);
    }

    public void verwijderBijlage(Long id) {
        Bijlage bijlage = bijlageRepository.lees(id);

        LOGGER.debug("Verwijderen Bijlage {}", ReflectionToStringBuilder.toString(bijlage, ToStringStyle.SHORT_PREFIX_STYLE));

        if (bijlage.getBedrijf() != null) {
            Bedrijf bedrijf = bedrijfService.lees(bijlage.getBedrijf().getId());
            bedrijf.getBijlages().remove(bijlage);
        }

        bijlageRepository.verwijder(bijlage);

        //Bestand nog ff verwijderen
        LOGGER.debug("Bestand {} verwijderen", Utils.getUploadPad() + "/" + bijlage.getS3Identificatie());

        new File(Utils.getUploadPad() + "/" + bijlage.getS3Identificatie()).delete();
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
            LOGGER.error(FOUTMELDINGBIJOPSLAANBIJLAGE, e);
        } finally {
            try {
                if (out != null) {
                    out.flush();
                }
            } catch (IOException e) {
                LOGGER.error(FOUTMELDINGBIJOPSLAANBIJLAGE, e);
            } finally {
                try {
                    if (out != null) {
                        out.close();
                    }
                } catch (IOException e) {
                    LOGGER.error(FOUTMELDINGBIJOPSLAANBIJLAGE, e);
                }
            }
        }
    }
}
