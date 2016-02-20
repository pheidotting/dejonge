package nl.dias.web.medewerker;

import nl.dias.domein.Adres;
import nl.dias.domein.Bedrijf;
import nl.dias.domein.ContactPersoon;
import nl.dias.domein.Telefoonnummer;
import nl.dias.mapper.Mapper;
import nl.dias.service.AdresService;
import nl.dias.service.BedrijfService;
import nl.dias.service.GebruikerService;
import nl.dias.service.TelefoonnummerService;
import nl.dias.web.SoortEntiteit;
import nl.dias.web.mapper.BedrijfMapper;
import nl.lakedigital.djfc.commons.json.JsonAdres;
import nl.lakedigital.djfc.commons.json.JsonBedrijf;
import nl.lakedigital.djfc.commons.json.JsonFoutmelding;
import nl.lakedigital.djfc.commons.json.JsonTelefoonnummer;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.inject.Inject;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

@RequestMapping("/bedrijf")
@Controller
public class BedrijfController {
    private static final Logger LOGGER = LoggerFactory.getLogger(BedrijfController.class);

    @Inject
    private BedrijfService bedrijfService;
    @Inject
    private GebruikerService gebruikerService;
    @Inject
    private Mapper mapper;
    @Inject
    private BedrijfMapper bedrijfMapper;
    @Inject
    private AdresService adresService;
    @Inject
    private TelefoonnummerService telefoonnummerService;

    @RequestMapping(method = RequestMethod.POST, value = "/opslaan")
    @ResponseBody
    public Response opslaanBedrijf(@RequestBody JsonBedrijf jsonBedrijf) {
        LOGGER.debug("Opslaan {}", ReflectionToStringBuilder.toString(jsonBedrijf, ToStringStyle.SHORT_PREFIX_STYLE));

        try {
            Bedrijf bedrijf = mapper.map(jsonBedrijf, Bedrijf.class);
            bedrijfService.opslaan(bedrijf);

            List<Adres> adressen = new ArrayList<>();
            for (JsonAdres jsonAdres : jsonBedrijf.getAdressen()) {
                adressen.add(mapper.map(jsonAdres, Adres.class));
            }
            adresService.opslaan(adressen, bedrijf.getId());

            List<Telefoonnummer> telefoonnummers = new ArrayList<>();
            List<ContactPersoon> contactPersoons = new ArrayList<>();
            for (JsonTelefoonnummer jsonTelefoonnummer : jsonBedrijf.getTelefoonnummers()) {
                telefoonnummers.add(mapper.map(jsonTelefoonnummer, Telefoonnummer.class));
            }
            telefoonnummerService.opslaan(telefoonnummers, bedrijf.getId(), SoortEntiteit.BEDRIJF);

            gebruikerService.opslaan(jsonBedrijf.getContactpersonen(), bedrijf.getId());

            return Response.status(200).entity(new JsonFoutmelding()).build();
        } catch (Exception e) {
            LOGGER.error("Fout bij opslaan Bedrijf", e);
            return Response.status(500).entity(new JsonFoutmelding(e.getMessage())).build();
        }
    }

    @RequestMapping(method = RequestMethod.GET, value = "/lees")
    @ResponseBody
    public JsonBedrijf lees(@QueryParam("id") String id) {
        JsonBedrijf bedrijf = null;
        if (id == null || "0".equals(id)) {
            bedrijf = new JsonBedrijf();
        } else {
            bedrijf = mapper.map(bedrijfService.lees(Long.valueOf(id)), JsonBedrijf.class);
        }
        return bedrijf;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/lijst")
    @ResponseBody
    public List<JsonBedrijf> lijst(@QueryParam("zoekTerm") String zoekTerm) {
        List<JsonBedrijf> bedrijven = new ArrayList<>();

        if (zoekTerm == null) {
            for (Bedrijf bedrijf : bedrijfService.alles()) {
                bedrijven.add(mapper.map(bedrijf, JsonBedrijf.class));
            }
        } else {
            for (Bedrijf bedrijf : bedrijfService.zoekOpNaam(zoekTerm)) {
                bedrijven.add(mapper.map(bedrijf, JsonBedrijf.class));
            }

        }
        return bedrijven;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/verwijder")
    @ResponseBody
    public Response verwijder(@QueryParam("id") Long id) {
        LOGGER.debug("verwijderen Bedrijf met id " + id);
        try {
            bedrijfService.verwijder(id);
        } catch (IllegalArgumentException e) {
            LOGGER.error("Fout bij verwijderen Polis", e);
            return Response.status(500).entity(new JsonFoutmelding(e.getMessage())).build();
        }
        return Response.status(202).entity(new JsonFoutmelding()).build();
    }

}
