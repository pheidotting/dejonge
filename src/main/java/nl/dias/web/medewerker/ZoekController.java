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

import static com.google.common.collect.Lists.newArrayList;

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
            LOGGER.debug("We gaan zoeken");
            LocalDate geboortedatum = null;
            if (zoekVelden.getGeboortedatum() != null && !"".equals(zoekVelden.getGeboortedatum())) {
                geboortedatum = LocalDate.parse(zoekVelden.getGeboortedatum());
            }

            ZoekResultaat zoekResultaat = zoekService.zoek(zoekVelden.getNaam(), geboortedatum, zoekVelden.getTussenvoegsel(), zoekVelden.getPolisnummer(), zoekVelden.getVoorletters(), zoekVelden.getSchadenummer(), zoekVelden.getAdres(), zoekVelden.getPostcode(), zoekVelden.getWoonplaats(), zoekVelden.getBedrijf());
            relaties = zoekResultaat.getRelaties();
            bedrijven = zoekResultaat.getBedrijven();
        } else {
            LOGGER.debug("We laten alles zien");
            for (Gebruiker r : gebruikerService.alleRelaties(kantoorRepository.getIngelogdKantoor())) {
                relaties.add((Relatie) r);
            }
            for (Bedrijf bedrijf : bedrijfService.alles()) {
                bedrijven.add(bedrijf);
            }
        }

        List<JsonAdres> adressenBijRelaties = relaties.isEmpty() ? newArrayList() : adresClient.alleAdressenBijLijstMetEntiteiten(relaties.stream()//
                .map(relatie -> relatie.getId())//
                .collect(Collectors.toList()), "RELATIE");

        List<JsonAdres> adressenBijBedrijven = bedrijven.isEmpty() ? newArrayList() : adresClient.alleAdressenBijLijstMetEntiteiten(bedrijven.stream()//
                .map(bedrijf -> bedrijf.getId())//
                .collect(Collectors.toList()), "BEDRIJF");

        ZoekResultaatResponse zoekResultaatResponse = new ZoekResultaatResponse();

        zoekResultaatResponse.getBedrijfOfRelatieList().addAll(relaties.stream()//
                .map(relatie -> {
                    RelatieZoekResultaat relatieZoekResultaat = new RelatieZoekResultaat();

                    Identificatie identificatie = identificatieClient.zoekIdentificatie("RELATIE", relatie.getId());

                    if (identificatie != null) {
                        relatieZoekResultaat.setIdentificatie(identificatie.getIdentificatie());
                    }
                    relatieZoekResultaat.setId(relatie.getId());
                    relatieZoekResultaat.setAchternaam(relatie.getAchternaam());
                    relatieZoekResultaat.setRoepnaam(relatie.getVoornaam());
                    relatieZoekResultaat.setTussenvoegsel(relatie.getTussenvoegsel());
                    relatieZoekResultaat.setAchternaam(relatie.getAchternaam());
                    if (relatie.getGeboorteDatum() != null) {
                        relatieZoekResultaat.setGeboortedatum(relatie.getGeboorteDatum().toString("dd-MM-yyyy"));
                    }

                    relatieZoekResultaat.setAdres(adressenBijRelaties.stream()//
                            .filter(adres -> adres.getEntiteitId().equals(relatie.getId()))//
                            .filter(adres -> "WOONADRES".equals(adres.getSoortAdres()))//
                            .findFirst().orElse(null));
                    if (relatieZoekResultaat.getAdres() == null) {
                        relatieZoekResultaat.setAdres(adressenBijRelaties.stream()//
                                .filter(adres -> adres.getEntiteitId().equals(relatie.getId()))//
                                .filter(adres -> "POSTADRES".equals(adres.getSoortAdres()))//
                                .findFirst().orElse(null));
                    }

                    return relatieZoekResultaat;
                })//
                .collect(Collectors.toList()));

        zoekResultaatResponse.getBedrijfOfRelatieList().addAll(bedrijven.stream()//
                .map(bedrijf -> {
                    BedrijfZoekResultaat bedrijfZoekResultaat = new BedrijfZoekResultaat();

                    Identificatie identificatie = identificatieClient.zoekIdentificatie("BEDRIJF", bedrijf.getId());

                    if (identificatie != null) {
                        bedrijfZoekResultaat.setIdentificatie(identificatie.getIdentificatie());
                    }
                    bedrijfZoekResultaat.setId(bedrijf.getId());
                    bedrijfZoekResultaat.setNaam(bedrijf.getNaam());

                    bedrijfZoekResultaat.setAdres(adressenBijBedrijven.stream()//
                            .filter(adres -> adres.getEntiteitId().equals(bedrijf.getId()))//
                            .filter(adres -> "POSTADRES".equals(adres.getSoortAdres()))//
                            .findFirst().orElse(null));
                    if (bedrijfZoekResultaat.getAdres() == null) {
                        bedrijfZoekResultaat.setAdres(adressenBijBedrijven.stream()//
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
                bedrijfOfRelatie.setId(null);

                if (bedrijfOfRelatie.getAdres() != null) {
                    String identificatie = identificatieClient.zoekIdentificatie("ADRES", bedrijfOfRelatie.getAdres().getId()).getIdentificatie();

                    bedrijfOfRelatie.getAdres().setIdentificatie(identificatie);
                    bedrijfOfRelatie.getAdres().setId(null);
                    bedrijfOfRelatie.getAdres().setSoortEntiteit(null);
                    bedrijfOfRelatie.getAdres().setEntiteitId(null);
                }

                return bedrijfOfRelatie;
            }
        }).collect(Collectors.toList()));

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
