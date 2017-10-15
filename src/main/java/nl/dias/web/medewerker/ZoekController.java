package nl.dias.web.medewerker;

import com.google.gson.Gson;
import nl.dias.ZoekResultaat;
import nl.dias.domein.Bedrijf;
import nl.dias.domein.Gebruiker;
import nl.dias.domein.Relatie;
import nl.dias.repository.KantoorRepository;
import nl.dias.service.BedrijfService;
import nl.dias.service.GebruikerService;
import nl.dias.service.ZoekService;
import nl.lakedigital.djfc.client.identificatie.IdentificatieClient;
import nl.lakedigital.djfc.client.oga.AdresClient;
import nl.lakedigital.djfc.commons.json.*;
import nl.lakedigital.djfc.reflection.ReflectionToStringBuilder;
import nl.lakedigital.djfc.request.SoortEntiteit;
import nl.lakedigital.djfc.request.SoortEntiteitEnEntiteitId;
import org.joda.time.LocalDate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.inject.Inject;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Controller
@RequestMapping(value = "/zoeken")
public class ZoekController extends AbstractController {
    private static final Logger LOGGER = LoggerFactory.getLogger(ZoekController.class);

    @Inject
    private GebruikerService gebruikerService;
    @Inject
    private BedrijfService bedrijfService;
    @Inject
    private KantoorRepository kantoorRepository;
    @Inject
    private AdresClient adresClient;
    @Inject
    private IdentificatieClient identificatieClient;
    @Inject
    private ZoekService zoekService;

    @RequestMapping(method = RequestMethod.GET, value = "/zoeken", produces = MediaType.APPLICATION_JSON)
    @ResponseBody
    public ZoekResultaatResponse zoeken() {
        return zoeken("", 0L);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/zoeken/{zoekterm}", produces = MediaType.APPLICATION_JSON)
    @ResponseBody
    public ZoekResultaatResponse zoeken(@PathVariable("zoekterm") String zoekTerm) {
        return zoeken(zoekTerm, 0L);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/zoeken/{zoekterm}/{weglaten}", produces = MediaType.APPLICATION_JSON)
    @ResponseBody
    public ZoekResultaatResponse zoeken(@PathVariable("zoekterm") String zoekTerm, @QueryParam("weglaten") Long weglaten) {
        String decoded = new String(Base64.getDecoder().decode(zoekTerm));
        LOGGER.debug("Decoded zoekstring {}", decoded);
        ZoekVelden zoekVelden = new Gson().fromJson(decoded, ZoekVelden.class);

        List<Relatie> relaties = new ArrayList<>();
        List<Bedrijf> bedrijven = new ArrayList<>();

        if (zoekVelden != null && !zoekVelden.isEmpty()) {
            LOGGER.trace("We gaan zoeken");
            LocalDate geboortedatum = null;
            if (zoekVelden.getGeboortedatum() != null && !"".equals(zoekVelden.getGeboortedatum())) {
                geboortedatum = LocalDate.parse(zoekVelden.getGeboortedatum());
            }

            ZoekResultaat zoekResultaat = zoekService.zoek(zoekVelden.getNaam(), geboortedatum, zoekVelden.getTussenvoegsel(), zoekVelden.getPolisnummer(), zoekVelden.getVoorletters(), zoekVelden.getSchadenummer(), zoekVelden.getAdres(), zoekVelden.getPostcode(), zoekVelden.getWoonplaats(), zoekVelden.getBedrijf());
            relaties = zoekResultaat.getRelaties();
            bedrijven = zoekResultaat.getBedrijven();
        } else {
            LOGGER.trace("We laten alles zien");
            List<Relatie> lijst = gebruikerService.alleRelaties(kantoorRepository.getIngelogdKantoor());
            LOGGER.debug("Gevonden {} Relaties/Gebruikers", lijst.size());
            for (Gebruiker r : lijst) {
                LOGGER.trace("{}", r);
                relaties.add((Relatie) r);
            }
            LOGGER.trace("Dat waren de Relaties");
            List<Bedrijf> bedrijfjes = bedrijfService.alles();
            LOGGER.debug("En {} bedrijven gevonden", bedrijfjes.size());
            for (Bedrijf bedrijf : bedrijfjes) {
                LOGGER.trace("{}", bedrijf);
                bedrijven.add(bedrijf);
            }
            LOGGER.trace("En dat waren de bedrijven");
        }

        ZoekResultaatResponse zoekResultaatResponse = new ZoekResultaatResponse();

        List<SoortEntiteitEnEntiteitId> soortEntiteitEnEntiteitIds = new ArrayList<>();

        zoekResultaatResponse.getBedrijfOfRelatieList().addAll(relaties.stream()//
                .map(relatie -> {
                    RelatieZoekResultaat relatieZoekResultaat = new RelatieZoekResultaat();

                    SoortEntiteitEnEntiteitId soortEntiteitEnEntiteitId = new SoortEntiteitEnEntiteitId();
                    soortEntiteitEnEntiteitId.setSoortEntiteit(SoortEntiteit.RELATIE);
                    soortEntiteitEnEntiteitId.setEntiteitId(relatie.getId());
                    soortEntiteitEnEntiteitIds.add(soortEntiteitEnEntiteitId);

                    relatieZoekResultaat.setId(relatie.getId());
                    relatieZoekResultaat.setAchternaam(relatie.getAchternaam());
                    relatieZoekResultaat.setRoepnaam(relatie.getVoornaam());
                    relatieZoekResultaat.setTussenvoegsel(relatie.getTussenvoegsel());
                    relatieZoekResultaat.setAchternaam(relatie.getAchternaam());
                    if (relatie.getGeboorteDatum() != null) {
                        relatieZoekResultaat.setGeboortedatum(relatie.getGeboorteDatum().toString("dd-MM-yyyy"));
                    }

                    List<JsonAdres> adressenBijRelatie = adresClient.lijst("RELATIE", relatie.getId());
                    relatieZoekResultaat.setAdres(adressenBijRelatie.stream()//
                            .filter(adres -> adres.getEntiteitId().equals(relatie.getId()))//
                            .filter(adres -> "WOONADRES".equals(adres.getSoortAdres()))//
                            .findFirst().orElse(null));
                    if (relatieZoekResultaat.getAdres() == null) {
                        relatieZoekResultaat.setAdres(adressenBijRelatie.stream()//
                                .filter(adres -> adres.getEntiteitId().equals(relatie.getId()))//
                                .filter(adres -> "POSTADRES".equals(adres.getSoortAdres()))//
                                .findFirst().orElse(null));
                    }

                    return relatieZoekResultaat;
                })//
                .collect(Collectors.toList()));

        if (!soortEntiteitEnEntiteitIds.isEmpty()) {
            identificatieClient.zoekIdentificatieCodes(soortEntiteitEnEntiteitIds);
        }

        zoekResultaatResponse.getBedrijfOfRelatieList().addAll(bedrijven.stream()//
                .map(bedrijf -> {
                    BedrijfZoekResultaat bedrijfZoekResultaat = new BedrijfZoekResultaat();

                    SoortEntiteitEnEntiteitId soortEntiteitEnEntiteitId = new SoortEntiteitEnEntiteitId();
                    soortEntiteitEnEntiteitId.setSoortEntiteit(SoortEntiteit.BEDRIJF);
                    soortEntiteitEnEntiteitId.setEntiteitId(bedrijf.getId());
                    soortEntiteitEnEntiteitIds.add(soortEntiteitEnEntiteitId);

                    bedrijfZoekResultaat.setId(bedrijf.getId());
                    bedrijfZoekResultaat.setNaam(bedrijf.getNaam());

                    List<JsonAdres> adressenBijBedrijf = adresClient.lijst("BEDRIJF", bedrijf.getId());
                    bedrijfZoekResultaat.setAdres(adressenBijBedrijf.stream()//
                            .filter(adres -> adres.getEntiteitId().equals(bedrijf.getId()))//
                            .filter(adres -> "POSTADRES".equals(adres.getSoortAdres()))//
                            .findFirst().orElse(null));
                    if (bedrijfZoekResultaat.getAdres() == null) {
                        bedrijfZoekResultaat.setAdres(adressenBijBedrijf.stream()//
                                .filter(adres -> adres.getEntiteitId().equals(bedrijf.getId()))//
                                .filter(adres -> "WOONADRES".equals(adres.getSoortAdres()))//
                                .findFirst().orElse(null));
                    }

                    return bedrijfZoekResultaat;
                }).collect(Collectors.toList()));

        zoekResultaatResponse.getBedrijfOfRelatieList().sort((o1, o2) -> {
            String naam1 = bepaalNaam(o1);
            String naam2 = bepaalNaam(o2);

            return naam1.compareTo(naam2);
        });

        zoekResultaatResponse.setBedrijfOfRelatieList(zoekResultaatResponse.getBedrijfOfRelatieList().stream().map(new Function<BedrijfOfRelatie, BedrijfOfRelatie>() {
            @Override
            public BedrijfOfRelatie apply(BedrijfOfRelatie bedrijfOfRelatie) {
                if (bedrijfOfRelatie.getAdres() != null) {
                    SoortEntiteitEnEntiteitId soortEntiteitEnEntiteitId = new SoortEntiteitEnEntiteitId();
                    soortEntiteitEnEntiteitId.setSoortEntiteit(SoortEntiteit.ADRES);
                    soortEntiteitEnEntiteitId.setEntiteitId(bedrijfOfRelatie.getAdres().getId());
                    soortEntiteitEnEntiteitIds.add(soortEntiteitEnEntiteitId);
                }

                return bedrijfOfRelatie;
            }
        }).collect(Collectors.toList()));

        LOGGER.debug("{} Identificaties opzoeken", soortEntiteitEnEntiteitIds.size());
        if (!soortEntiteitEnEntiteitIds.isEmpty()) {
            identificatieClient.zoekIdentificatieCodes(soortEntiteitEnEntiteitIds).stream().forEach(identificatie -> zoekResultaatResponse.getBedrijfOfRelatieList().stream().forEach(bedrijfOfRelatie -> {
                if (bedrijfOfRelatie instanceof RelatieZoekResultaat && bedrijfOfRelatie.getId() == 2761 && identificatie.getId() == 4846) {
                    LOGGER.debug("Here i am!");
                    LOGGER.debug(ReflectionToStringBuilder.toString(identificatie));
                    LOGGER.debug(ReflectionToStringBuilder.toString(bedrijfOfRelatie));
                    LOGGER.debug("#####");
                    LOGGER.debug("{}", "RELATIE".equals(identificatie.getSoortEntiteit()));
                    LOGGER.debug("{}", bedrijfOfRelatie instanceof RelatieZoekResultaat);
                    LOGGER.debug("{}", bedrijfOfRelatie.getId() == identificatie.getEntiteitId());
                }
                LOGGER.trace("{} Identificaties gevonden", soortEntiteitEnEntiteitIds.size());
                boolean gevonden = false;
                if (identificatie != null) {
                    LOGGER.trace("identificatie niet nulll");
                    if ("ADRES".equals(identificatie.getSoortEntiteit()) && bedrijfOfRelatie.getAdres() != null && bedrijfOfRelatie.getAdres().getId() == identificatie.getEntiteitId()) {
                        LOGGER.trace("ADRES");
                        bedrijfOfRelatie.getAdres().setIdentificatie(identificatie.getIdentificatie());
                        bedrijfOfRelatie.getAdres().setId(null);
                        bedrijfOfRelatie.getAdres().setSoortEntiteit(null);
                        bedrijfOfRelatie.getAdres().setEntiteitId(null);
                        gevonden = true;
                    } else if ("BEDRIJF".equals(identificatie.getSoortEntiteit()) && bedrijfOfRelatie instanceof BedrijfZoekResultaat && bedrijfOfRelatie.getId() == identificatie.getEntiteitId()) {
                        LOGGER.trace("BEDRIJF");
                        bedrijfOfRelatie.setIdentificatie(identificatie.getIdentificatie());
                        gevonden = true;
                    } else if ("RELATIE".equals(identificatie.getSoortEntiteit()) && bedrijfOfRelatie instanceof RelatieZoekResultaat && bedrijfOfRelatie.getId() == identificatie.getEntiteitId()) {
                        LOGGER.trace("RELATIE");
                        bedrijfOfRelatie.setIdentificatie(identificatie.getIdentificatie());
                        gevonden = true;
                    } else {
                        LOGGER.trace(ReflectionToStringBuilder.toString(identificatie));
                    }
                }
                LOGGER.trace("{} - {}, gevonden : {}", identificatie.getSoortEntiteit(), identificatie.getEntiteitId(), gevonden);
            }));
        }

        zoekResultaatResponse.getBedrijfOfRelatieList().stream().forEach(bedrijfOfRelatie -> bedrijfOfRelatie.setId(null));

        return zoekResultaatResponse;
    }

    private String bepaalNaam(BedrijfOfRelatie bedrijfOfRelatie) {
        if (bedrijfOfRelatie instanceof BedrijfZoekResultaat) {
            return ((BedrijfZoekResultaat) bedrijfOfRelatie).getNaam();
        } else if (bedrijfOfRelatie instanceof RelatieZoekResultaat) {
            return ((RelatieZoekResultaat) bedrijfOfRelatie).getAchternaam();
        }
        return null;
    }

}
