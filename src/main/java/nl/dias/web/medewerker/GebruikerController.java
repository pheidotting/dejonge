package nl.dias.web.medewerker;

import nl.dias.domein.Gebruiker;
import nl.dias.domein.Medewerker;
import nl.dias.domein.Relatie;
import nl.dias.mapper.Mapper;
import nl.dias.repository.KantoorRepository;
import nl.dias.service.AuthorisatieService;
import nl.dias.service.GebruikerService;
import nl.dias.web.mapper.AdresMapper;
import nl.dias.web.mapper.RelatieMapper;
import nl.lakedigital.djfc.commons.json.*;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

@RequestMapping("/gebruiker")
@Controller
public class GebruikerController {
    private static final Logger LOGGER = LoggerFactory.getLogger(GebruikerController.class);

    @Inject
    private GebruikerService gebruikerService;
    @Inject
    private KantoorRepository kantoorRepository;
    @Inject
    private RelatieMapper relatieMapper;
    @Inject
    private AdresMapper adresMapper;
    @Inject
    private Mapper mapper;
    @Inject
    private AuthorisatieService authorisatieService;
    @Autowired
    private HttpServletRequest httpServletRequest;
    @Autowired
    private HttpServletResponse httpServletResponse;

    @RequestMapping(method = RequestMethod.GET, value = "/lees")
    @ResponseBody
    public JsonRelatie lees(@QueryParam("id") String id) {
        LOGGER.debug("Ophalen Relatie met id : " + id);

        JsonRelatie jsonRelatie = null;
        if (id != null && !"0".equals(id.trim())) {
            Relatie relatie = (Relatie) gebruikerService.lees(Long.parseLong(id));

            LOGGER.debug("Opgehaald : " + relatie);

            jsonRelatie = relatieMapper.mapNaarJson(relatie);
        } else {
            jsonRelatie = new JsonRelatie();
        }

        LOGGER.debug("Naar de front-end : " + jsonRelatie);

        return jsonRelatie;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/lijstRelaties")
    @ResponseBody
    public JsonLijstRelaties lijstRelaties(@QueryParam("weglaten") String weglaten) {
        LOGGER.debug("Ophalen lijst met alle Relaties");

        Long idWeglaten = null;
        if (weglaten != null) {
            LOGGER.debug("id " + weglaten + " moet worden weggelaten");
            idWeglaten = Long.parseLong(weglaten);
        }

        JsonLijstRelaties lijst = new JsonLijstRelaties();

        for (Gebruiker r : gebruikerService.alleRelaties(kantoorRepository.getIngelogdKantoor())) {
            if (idWeglaten == null || !idWeglaten.equals(r.getId())) {
                lijst.getJsonRelaties().add(relatieMapper.mapNaarJson((Relatie) r));
            }
        }
        LOGGER.debug("Opgehaald, lijst met " + lijst.getJsonRelaties().size() + " relaties");

        return lijst;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/opslaan")
    @ResponseBody
    public Response opslaan(@RequestBody JsonRelatie jsonRelatie) {
        LOGGER.info("Opslaan " + jsonRelatie);

        try {
            Relatie relatie = mapper.map(jsonRelatie, Relatie.class);
            LOGGER.debug("Uit mapper " + relatie);
            String sessie = null;
            if (httpServletRequest.getSession().getAttribute("sessie") != null && !"".equals(httpServletRequest.getSession().getAttribute("sessie"))) {
                sessie = httpServletRequest.getSession().getAttribute("sessie").toString();
            }

            Medewerker medewerker = (Medewerker) authorisatieService.getIngelogdeGebruiker(httpServletRequest, sessie, httpServletRequest.getRemoteAddr());

            LOGGER.debug(ReflectionToStringBuilder.toString(medewerker));

            relatie.setKantoor(kantoorRepository.lees(medewerker.getKantoor().getId()));

            LOGGER.debug("Opslaan Relatie met id " + relatie.getId());

            gebruikerService.opslaan(relatie);

            LOGGER.debug("Relatie met id " + relatie.getId() + " opgeslagen");
            return Response.status(202).entity(new JsonFoutmelding(relatie.getId().toString())).build();
        } catch (Exception e) {
            LOGGER.error("Fout bij opslaan Relatie", e);
            return Response.status(500).entity(new JsonFoutmelding(e.getMessage())).build();
        }
    }

    @RequestMapping(method = RequestMethod.GET, value = "/verwijderen")
    @ResponseBody
    public Response verwijderen(@QueryParam("id") Long id) {
        LOGGER.debug("Verwijderen Relatie met id " + id);

        gebruikerService.verwijder(id);

        return Response.status(200).build();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/zoekOpNaamAdresOfPolisNummer")
    @ResponseBody
    public JsonLijstRelaties zoekOpNaamAdresOfPolisNummer(@QueryParam("zoekTerm") String zoekTerm, @QueryParam("weglaten") String weglaten) {
        LOGGER.info("zoekOpNaamAdresOfPolisNummer met zoekterm " + zoekTerm);

        JsonLijstRelaties lijst = new JsonLijstRelaties();

        if (zoekTerm == null || "".equals(zoekTerm)) {
            // for (Gebruiker r :
            // gebruikerService.alleRelaties(kantoorRepository.getIngelogdKantoor()))
            // {
            // lijst.getJsonRelaties().add(relatieMapper.mapNaarJson((Relatie)
            // r));
            // }
        } else {
            Long idWeglaten = null;
            if (weglaten != null) {
                LOGGER.debug("id " + weglaten + " moet worden weggelaten");
                idWeglaten = Long.parseLong(weglaten);
            }

            for (Gebruiker r : gebruikerService.zoekOpNaamAdresOfPolisNummer(zoekTerm)) {
                if (idWeglaten == null || !idWeglaten.equals(r.getId())) {
                    lijst.getJsonRelaties().add(relatieMapper.mapNaarJson((Relatie) r));
                }
            }
        }
        return lijst;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/koppelenOnderlingeRelatie")
    @ResponseBody
    public void koppelenOnderlingeRelatie(@RequestBody JsonKoppelenOnderlingeRelatie jsonKoppelenOnderlingeRelatie) {

        gebruikerService.koppelenOnderlingeRelatie(jsonKoppelenOnderlingeRelatie.getRelatie(), jsonKoppelenOnderlingeRelatie.getRelatieMet(), jsonKoppelenOnderlingeRelatie.getSoortRelatie());
    }

    @RequestMapping(method = RequestMethod.POST, value = "/opslaanAdresBijRelatie")
    @ResponseBody
    public void opslaanAdresBijRelatie(@RequestBody JsonAdres jsonAdres) {
        gebruikerService.opslaanAdresBijRelatie(adresMapper.mapVanJson(jsonAdres), Long.valueOf(jsonAdres.getRelatie()));
    }

    public void setGebruikerService(GebruikerService gebruikerService) {
        this.gebruikerService = gebruikerService;
    }

    public void setRelatieMapper(RelatieMapper relatieMapper) {
        this.relatieMapper = relatieMapper;
    }

    public void setKantoorRepository(KantoorRepository kantoorRepository) {
        this.kantoorRepository = kantoorRepository;
    }

    public void setAuthorisatieService(AuthorisatieService authorisatieService) {
        this.authorisatieService = authorisatieService;
    }

    public void setHttpServletRequest(HttpServletRequest httpServletRequest) {
        this.httpServletRequest = httpServletRequest;
    }
}
