package nl.dias.web.medewerker;

import nl.dias.domein.Medewerker;
import nl.dias.service.GebruikerService;
import nl.dias.service.RelatieService;
import nl.lakedigital.djfc.client.identificatie.IdentificatieClient;
import nl.lakedigital.djfc.client.oga.*;
import nl.lakedigital.djfc.commons.json.*;
import nl.lakedigital.djfc.domain.response.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.inject.Inject;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Controller
@RequestMapping("relatie")
public class RelatieController {
    @Inject
    private AdresClient adresClient;
    @Inject
    private BijlageClient bijlageClient;
    @Inject
    private GroepBijlagesClient groepBijlagesClient;
    @Inject
    private OpmerkingClient opmerkingClient;
    @Inject
    private RekeningClient rekeningClient;
    @Inject
    private TelefoonnummerClient telefoonnummerClient;
    @Inject
    private TelefonieBestandClient telefonieBestandClient;
    @Inject
    private GebruikerService gebruikerService;
    @Inject
    private IdentificatieClient identificatieClient;
    @Inject
    private RelatieService relatieService;

    @RequestMapping(method = RequestMethod.GET, value = "/lees/{id}", produces = MediaType.APPLICATION_JSON)
    @ResponseBody
    public Relatie leesRelatie(@PathVariable("id") String identificatie) {
        //        Identificatie identificatieGelezen = identificatieClient.zoekIdentificatieCode(identificatie);
        //        Long id = identificatieGelezen.getEntiteitId();
        //
        //        Gebruiker gebruiker = gebruikerService.lees(id);
        nl.dias.domein.Relatie relatieDomain = relatieService.zoekRelatie(identificatie);
        Relatie relatie = new Relatie();

        //        if (gebruiker instanceof nl.dias.domein.Relatie) {
        //            nl.dias.domein.Relatie relatieDomain = (nl.dias.domein.Relatie) gebruiker;
            String datumFormaat = "yyyy-MM-dd";

            relatie.setAchternaam(relatieDomain.getAchternaam());
            relatie.setIdentificatie(identificatie);
            relatie.setRoepnaam(relatieDomain.getRoepnaam());
            relatie.setVoornaam(relatieDomain.getVoornaam());
            relatie.setTussenvoegsel(relatieDomain.getTussenvoegsel());
            relatie.setBsn(relatieDomain.getBsn());
            relatie.setGeboorteDatum(relatieDomain.getGeboorteDatum().toString(datumFormaat));
            if (relatieDomain.getOverlijdensdatum() != null) {
                relatie.setOverlijdensdatum(relatieDomain.getOverlijdensdatum().toString(datumFormaat));
            }
            relatie.setGeslacht(relatieDomain.getGeslacht().getOmschrijving());
            relatie.setBurgerlijkeStaat(relatieDomain.getBurgerlijkeStaat().getOmschrijving());
            relatie.setEmailadres(relatieDomain.getEmailadres());

        relatie.setAdressen(adresClient.lijst("RELATIE", relatieDomain.getId()).stream().map(new Function<JsonAdres, Adres>() {
                @Override
                public Adres apply(JsonAdres jsonAdres) {
                    Adres adres = new Adres();

                    Identificatie adresIdentificatie = identificatieClient.zoekIdentificatie("ADRES", jsonAdres.getId());

                    adres.setHuisnummer(jsonAdres.getHuisnummer());
                    adres.setIdentificatie(adresIdentificatie.getIdentificatie());
                    adres.setHuisnummer(jsonAdres.getHuisnummer());
                    adres.setPlaats(jsonAdres.getPlaats());
                    adres.setPostcode(jsonAdres.getPostcode());
                    adres.setStraat(jsonAdres.getStraat());
                    adres.setToevoeging(jsonAdres.getToevoeging());
                    adres.setSoortAdres(jsonAdres.getSoortAdres());
                    return adres;
                }
            }).collect(Collectors.toList()));

        relatie.setBijlages(bijlageClient.lijst("RELATIE", relatieDomain.getId()).stream().map(new Function<JsonBijlage, Bijlage>() {
                @Override
                public Bijlage apply(JsonBijlage jsonBijlage) {
                    Bijlage bijlage = new Bijlage();

                    Identificatie bijlageIdentificatie = identificatieClient.zoekIdentificatie("BIJLAGE", jsonBijlage.getId());

                    bijlage.setIdentificatie(bijlageIdentificatie.getIdentificatie());
                    bijlage.setDatumUpload(jsonBijlage.getDatumUpload());
                    bijlage.setOmschrijving(jsonBijlage.getOmschrijving());
                    bijlage.setBestandsNaam(jsonBijlage.getBestandsNaam());

                    return bijlage;
                }
            }).collect(Collectors.toList()));

        relatie.setGroepBijlages(groepBijlagesClient.lijstGroepen("RELATIE", relatieDomain.getId()).stream().map(new Function<JsonGroepBijlages, GroepBijlages>() {
                @Override
                public GroepBijlages apply(JsonGroepBijlages jsonGroepBijlages) {
                    GroepBijlages groepBijlages = new GroepBijlages();

                    Identificatie groepBijlageIdentificatie = identificatieClient.zoekIdentificatie("GROEPBIJLAGE", jsonGroepBijlages.getId());
                    //zou niet voor mogen komen, maar GROEPBIJLAGE zit nog niet in de identificatie
                    if (groepBijlageIdentificatie != null) {
                        groepBijlages.setIdentificatie(groepBijlageIdentificatie.getIdentificatie());
                    }

                    groepBijlages.setNaam(jsonGroepBijlages.getNaam());
                    groepBijlages.setBijlages(jsonGroepBijlages.getBijlages().stream().map(new Function<JsonBijlage, Bijlage>() {
                        @Override
                        public Bijlage apply(JsonBijlage jsonBijlage) {
                            Bijlage bijlage = new Bijlage();

                            Identificatie bijlageIdentificatie = identificatieClient.zoekIdentificatie("BIJLAGE", jsonBijlage.getId());

                            bijlage.setIdentificatie(bijlageIdentificatie.getIdentificatie());
                            bijlage.setDatumUpload(jsonBijlage.getDatumUpload());
                            bijlage.setOmschrijving(jsonBijlage.getOmschrijving());
                            bijlage.setBestandsNaam(jsonBijlage.getBestandsNaam());

                            return bijlage;
                        }
                    }).collect(Collectors.toList()));

                    return groepBijlages;
                }
            }).collect(Collectors.toList()));

        relatie.setRekeningNummers(rekeningClient.lijst("RELATIE", relatieDomain.getId()).stream().map(new Function<JsonRekeningNummer, RekeningNummer>() {
                @Override
                public RekeningNummer apply(JsonRekeningNummer jsonRekeningNummer) {
                    RekeningNummer rekeningNummer = new RekeningNummer();

                    Identificatie rekeningNummerIdentificatie = identificatieClient.zoekIdentificatie("REKENINGNUMMER", jsonRekeningNummer.getId());

                    rekeningNummer.setIdentificatie(rekeningNummerIdentificatie.getIdentificatie());
                    rekeningNummer.setBic(jsonRekeningNummer.getBic());
                    rekeningNummer.setRekeningnummer(jsonRekeningNummer.getRekeningnummer());

                    return rekeningNummer;
                }
            }).collect(Collectors.toList()));

        relatie.setTelefoonnummers(telefoonnummerClient.lijst("RELATIE", relatieDomain.getId()).stream().map(new Function<JsonTelefoonnummer, Telefoonnummer>() {
                @Override
                public Telefoonnummer apply(JsonTelefoonnummer jsonTelefoonnummer) {
                    Telefoonnummer telefoonnummer = new Telefoonnummer();

                    Identificatie telefoonnummerIdentificatie = identificatieClient.zoekIdentificatie("TELEFOONNUMMER", jsonTelefoonnummer.getId());

                    telefoonnummer.setIdentificatie(telefoonnummerIdentificatie.getIdentificatie());
                    telefoonnummer.setTelefoonnummer(jsonTelefoonnummer.getTelefoonnummer());
                    telefoonnummer.setSoort(jsonTelefoonnummer.getSoort());
                    telefoonnummer.setOmschrijving(jsonTelefoonnummer.getOmschrijving());

                    return telefoonnummer;
                }
            }).collect(Collectors.toList()));

        relatie.setOpmerkingen(opmerkingClient.lijst("RELATIE", relatieDomain.getId()).stream().map(new Function<JsonOpmerking, Opmerking>() {
                @Override
                public Opmerking apply(JsonOpmerking jsonOpmerking) {
                    Opmerking opmerking = new Opmerking();

                    Identificatie opmerkingIdentificatie = identificatieClient.zoekIdentificatie("OPMERKING", jsonOpmerking.getId());

                    opmerking.setIdentificatie(opmerkingIdentificatie.getIdentificatie());
                    opmerking.setTijd(jsonOpmerking.getTijd());
                    opmerking.setOpmerking(jsonOpmerking.getOpmerking());
                    opmerking.setMedewerkerId(jsonOpmerking.getMedewerkerId());

                    Medewerker medewerker = (Medewerker) gebruikerService.lees(jsonOpmerking.getMedewerkerId());
                    StringBuilder sb = new StringBuilder();
                    sb.append(medewerker.getVoornaam());
                    sb.append(" ");
                    if (medewerker.getTussenvoegsel() != null) {
                        sb.append(medewerker.getTussenvoegsel());
                        sb.append(" ");
                    }
                    sb.append(medewerker.getAchternaam());
                    opmerking.setMedewerker(sb.toString());

                    return opmerking;
                }
            }).collect(Collectors.toList()));

            List<String> telefoonnummers = relatie.getTelefoonnummers().stream().map(new Function<Telefoonnummer, String>() {
                @Override
                public String apply(Telefoonnummer telefoonnummer) {
                    return telefoonnummer.getTelefoonnummer();
                }
            }).collect(Collectors.toList());

            Map<String, List<String>> telefonieResult = telefonieBestandClient.getRecordingsAndVoicemails(telefoonnummers);
            for (String nummer : telefonieResult.keySet()) {
                TelefoonnummerMetGesprekken telefoonnummerMetGesprekken = new TelefoonnummerMetGesprekken();
                telefoonnummerMetGesprekken.setTelefoonnummer(nummer);
                telefoonnummerMetGesprekken.setTelefoongesprekken(telefonieResult.get(nummer).stream().map(new Function<String, Telefoongesprek>() {
                    @Override
                    public Telefoongesprek apply(String s) {
                        Telefoongesprek telefoongesprek = new Telefoongesprek();
                        telefoongesprek.setBestandsnaam(s);

                        return telefoongesprek;
                    }
                }).collect(Collectors.toList()));

                relatie.getTelefoonnummerMetGesprekkens().add(telefoonnummerMetGesprekken);
            }
        //        }

        return relatie;
    }
}
