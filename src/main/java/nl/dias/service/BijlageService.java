package nl.dias.service;

import nl.dias.domein.Bijlage;
import nl.dias.repository.BijlageRepository;
import nl.dias.utils.Utils;
import nl.dias.web.SoortEntiteit;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.joda.time.LocalDateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.inject.Inject;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

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

    public List<Bijlage> alleBijlagesBijEntiteit(SoortEntiteit soortEntiteit, Long entiteitId) {
        return bijlageRepository.alleBijlagesBijEntiteit(soortEntiteit, entiteitId);
    }

    public void verwijderBijlage(Long id) {
        Bijlage bijlage = bijlageRepository.lees(id);

        LOGGER.debug("Verwijderen Bijlage {}", ReflectionToStringBuilder.toString(bijlage, ToStringStyle.SHORT_PREFIX_STYLE));

        //        if (bijlage.getBedrijf() != null) {
        //            Bedrijf bedrijf = bedrijfService.lees(bijlage.getBedrijf().getId());
        //            bedrijf.getBijlages().remove(bijlage);
        //        }

        bijlageRepository.verwijder(bijlage);

        //Bestand nog ff verwijderen
        LOGGER.debug("Bestand {} verwijderen", Utils.getUploadPad() + "/" + bijlage.getS3Identificatie());

        new File(Utils.getUploadPad() + "/" + bijlage.getS3Identificatie()).delete();
    }

    public void opslaan(Bijlage bijlage) {
        bijlageRepository.opslaan(bijlage);
    }

    protected String bepaalExtensie(String bestandsnaamn){
        String[] exp = bestandsnaamn.split("\\.");
        String extensie = exp[exp.length - 1];

        return extensie;
    };

    public List<Bijlage> uploaden(MultipartFile fileDetail, String uploadPad) {
        String extensie = bepaalExtensie(fileDetail.getOriginalFilename());

        List<Bijlage> bijlages =new ArrayList<>();
        LOGGER.debug(extensie);
        if(extensie.toLowerCase().equals("zip")){
            LOGGER.debug("zipfile");
            try {
                String bestand = uploadPad + "/" + fileDetail.getOriginalFilename();
                try {
                    writeToFile(fileDetail.getInputStream(), bestand);
                } catch (IOException e) {
                    LOGGER.error("Fout bij uploaden", e);
                }
                bijlages.addAll(verwerkZipFile(bestand, uploadPad));
                new File(bestand).delete();
            }catch (IOException e){

            }
        }else{
            LOGGER.debug("PDF");
         bijlages.add(schrijfWeg(fileDetail,uploadPad,extensie));
    }

        return bijlages;
    }

    private List<Bijlage> verwerkZipFile(String zipFile, String outputFolder) throws IOException{
        LOGGER.debug("Verwerken zip bestand {}",zipFile);

        List<Bijlage> bijlages = new ArrayList<>();
        byte[] buffer = new byte[1024];

        ZipInputStream zis =
                new ZipInputStream(new FileInputStream(zipFile));
        //get the zipped file list entry
        ZipEntry ze = zis.getNextEntry();

        while(ze!=null){
            String fileName = ze.getName();
            LOGGER.debug("Uitgepakt bestand: {}",fileName);

            Bijlage bijlage = maakBijlage(fileName);
            LOGGER.debug(ReflectionToStringBuilder.toString(bijlage,ToStringStyle.SHORT_PREFIX_STYLE));

            bijlages.add(bijlage);

            File newFile = new File(outputFolder + File.separator + bijlage.getS3Identificatie());

            //create all non exists folders
            //else you will hit FileNotFoundException for compressed folder
            new File(newFile.getParent()).mkdirs();

            FileOutputStream fos = new FileOutputStream(newFile);

            int len;
            while ((len = zis.read(buffer)) > 0) {
                fos.write(buffer, 0, len);
            }

            fos.close();
            ze = zis.getNextEntry();
        }

        zis.closeEntry();
        zis.close();

        return bijlages;
    }

    private Bijlage maakBijlage(String fileName){
        String identificatie = UUID.randomUUID().toString().replace("-", "");

        Bijlage bijlage = new Bijlage();
        bijlage.setS3Identificatie(identificatie);
        bijlage.setBestandsNaam(fileName);
        bijlage.setUploadMoment(LocalDateTime.now());

        return bijlage;
    }

        private Bijlage schrijfWeg(MultipartFile fileDetail, String uploadPad, String extensie){
            Bijlage bijlage=maakBijlage(fileDetail.getOriginalFilename());

        //        bijlageRepository.opslaan(bijlage);
        LOGGER.debug("Opslaan Bijlage {}", ReflectionToStringBuilder.toString(bijlage, ToStringStyle.SHORT_PREFIX_STYLE));

        try {
            LOGGER.debug("Opslaan bestand op schijf");
            writeToFile(fileDetail.getInputStream(), uploadPad + "/" + bijlage.getS3Identificatie());
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
