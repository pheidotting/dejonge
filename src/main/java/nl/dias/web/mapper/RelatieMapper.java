package nl.dias.web.mapper;

import com.google.common.base.Predicate;
import nl.dias.domein.*;
import nl.dias.service.GebruikerService;
import nl.lakedigital.djfc.commons.json.JsonOnderlingeRelatie;
import nl.lakedigital.djfc.commons.json.JsonRekeningNummer;
import nl.lakedigital.djfc.commons.json.JsonRelatie;
import nl.lakedigital.djfc.commons.json.JsonTelefoonnummer;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.HashSet;
import java.util.Set;

import static com.google.common.collect.Iterables.filter;
import static com.google.common.collect.Iterables.getFirst;

@Component
public class RelatieMapper extends Mapper<Relatie, JsonRelatie> {
    private static final Logger LOGGER = LoggerFactory.getLogger(RelatieMapper.class);

    @Inject
    private TelefoonnummerMapper telefoonnummerMapper;
    @Inject
    private RekeningnummerMapper rekeningnummerMapper;
    @Inject
    private OpmerkingMapper opmerkingMapper;
    @Inject
    private AdresMapper adresMapper;
    @Inject
    private BijlageMapper bijlageMapper;
    @Inject
    private GebruikerService gebruikerService;

    @Override
    public Relatie mapVanJson(JsonRelatie jsonRelatie) {
        String patternDatum = "dd-MM-yyyy";

        Relatie relatie = new Relatie();
        if (jsonRelatie.getId() != null) {
            relatie = (Relatie) gebruikerService.lees(jsonRelatie.getId());
        }
        relatie.setId(jsonRelatie.getId());
        relatie.setRoepnaam(jsonRelatie.getRoepnaam());
        try {
            relatie.setIdentificatie(jsonRelatie.getIdentificatie());
        } catch (UnsupportedEncodingException e) {
            LOGGER.error("fout", e);
        } catch (NoSuchAlgorithmException e) {
            LOGGER.error("fout", e);
        }
        relatie.setVoornaam(jsonRelatie.getVoornaam());
        relatie.setTussenvoegsel(jsonRelatie.getTussenvoegsel());
        relatie.setAchternaam(jsonRelatie.getAchternaam());
        if (jsonRelatie.getOverlijdensdatum() != null && !"".equals(jsonRelatie.getOverlijdensdatum())) {
            relatie.setOverlijdensdatum(LocalDate.parse(jsonRelatie.getOverlijdensdatum(), DateTimeFormat.forPattern(patternDatum)));
        }
        if (jsonRelatie.getGeboorteDatum() != null && !"".equals(jsonRelatie.getGeboorteDatum())) {
            relatie.setGeboorteDatum(LocalDate.parse(jsonRelatie.getGeboorteDatum(), DateTimeFormat.forPattern(patternDatum)));
        }

        //        Set<Adres> adressen = new HashSet<>();
        //        for (final JsonAdres jsonAdres : jsonRelatie.getAdressen()) {
        //            Adres adres = getFirst(filter(relatie.getAdressen(), new Predicate<Adres>() {
        //                @Override
        //                public boolean apply(Adres adres) {
        //                    return adres.getId() == jsonAdres.getId();
        //                }
        //            }), new Adres());
        //
        //            adres.setToevoeging(jsonAdres.getToevoeging());
        //            adres.setHuisnummer(jsonAdres.getHuisnummer());
        //            adres.setPlaats(jsonAdres.getPlaats());
        //            adres.setPostcode(jsonAdres.getPostcode());
        //            adres.setSoortAdres(Adres.SoortAdres.valueOf(jsonAdres.getSoortAdres()));
        //            adres.setStraat(jsonAdres.getStraat());
        //            adres.setEntiteitId(relatie.getId());
        //            adres.setSoortEntiteit(SoortEntiteit.RELATIE);
        //
        //            adressen.add(adres);
        //        }
        //        relatie.setAdressen(adressen);

        Set<Telefoonnummer> telefoonnummers = new HashSet<>();
        for (final JsonTelefoonnummer jsonTelefoonnummer : jsonRelatie.getTelefoonnummers()) {
            Telefoonnummer telefoonnummer = getFirst(filter(relatie.getTelefoonnummers(), new Predicate<Telefoonnummer>() {
                @Override
                public boolean apply(Telefoonnummer telefoonnummer) {
                    return telefoonnummer.getId() == jsonTelefoonnummer.getId();
                }
            }), new Telefoonnummer());

            telefoonnummer.setOmschrijving(jsonTelefoonnummer.getOmschrijving());
            telefoonnummer.setSoort(TelefoonnummerSoort.valueOf(jsonTelefoonnummer.getSoort()));
            telefoonnummer.setTelefoonnummer(jsonTelefoonnummer.getTelefoonnummer());
            telefoonnummer.setRelatie(relatie);

            telefoonnummers.add(telefoonnummer);
        }
        relatie.setTelefoonnummers(telefoonnummers);

        Set<RekeningNummer> rekeningNummers = new HashSet<>();
        for (final JsonRekeningNummer jsonRekeningNummer : jsonRelatie.getRekeningnummers()) {
            RekeningNummer rekeningNummer = getFirst(filter(relatie.getRekeningnummers(), new Predicate<RekeningNummer>() {
                @Override
                public boolean apply(RekeningNummer rekeningNummer) {
                    return rekeningNummer.getId() == jsonRekeningNummer.getId();
                }
            }), new RekeningNummer());

            rekeningNummer.setBic(jsonRekeningNummer.getBic());
            rekeningNummer.setRekeningnummer(jsonRekeningNummer.getRekeningnummer());
            rekeningNummer.setRelatie(relatie);

            rekeningNummers.add(rekeningNummer);
        }
        relatie.setRekeningnummers(rekeningNummers);

        relatie.setBsn(jsonRelatie.getBsn());
        relatie.setGeslacht(Geslacht.valueOf(jsonRelatie.getGeslacht().substring(0, 1)));

        for (BurgerlijkeStaat bs : BurgerlijkeStaat.values()) {
            if (bs.getOmschrijving().equals(jsonRelatie.getBurgerlijkeStaat())) {
                relatie.setBurgerlijkeStaat(bs);
            }
        }

        return relatie;
    }

    @Override
    public JsonRelatie mapNaarJson(Relatie relatie) {
        LOGGER.debug("Mappen : {}", ReflectionToStringBuilder.toString(relatie, ToStringStyle.SHORT_PREFIX_STYLE));

        JsonRelatie jsonRelatie = new JsonRelatie();

        jsonRelatie.setId(relatie.getId());
        jsonRelatie.setRoepnaam(relatie.getRoepnaam());
        jsonRelatie.setIdentificatie(relatie.getIdentificatie());
        jsonRelatie.setVoornaam(relatie.getVoornaam());
        if (relatie.getTussenvoegsel() != null) {
            jsonRelatie.setTussenvoegsel(relatie.getTussenvoegsel());
        } else {
            jsonRelatie.setTussenvoegsel("");
        }
        jsonRelatie.setAchternaam(relatie.getAchternaam());
        //        jsonRelatie.setAdressen(adresMapper.mapAllNaarJson(relatie.getAdressen()));
        jsonRelatie.setTelefoonnummers(telefoonnummerMapper.mapAllNaarJson(relatie.getTelefoonnummers()));
        jsonRelatie.setBsn(relatie.getBsn());
        jsonRelatie.setRekeningnummers(rekeningnummerMapper.mapAllNaarJson(relatie.getRekeningnummers()));
        if (relatie.getKantoor() != null && relatie.getKantoor().getId() != null) {
            jsonRelatie.setKantoor(relatie.getKantoor().getId());
        }
        //        LOGGER.debug("Opmerkingen mappen");
        //        jsonRelatie.setOpmerkingen(opmerkingMapper.mapAllNaarJson(relatie.getOpmerkingen()));
        //        LOGGER.debug("Einde opmerkingen mappen");
        if (relatie.getGeboorteDatum() != null) {
            jsonRelatie.setGeboorteDatum(relatie.getGeboorteDatum().toString("dd-MM-yyyy"));
            jsonRelatie.setGeboorteDatumOpgemaakt(relatie.getGeboorteDatum().toString("dd-MM-yyyy"));
        } else {
            jsonRelatie.setGeboorteDatum("");
            jsonRelatie.setGeboorteDatumOpgemaakt("");
        }
        if (relatie.getOverlijdensdatum() != null) {
            jsonRelatie.setOverlijdensdatum(relatie.getOverlijdensdatum().toString("dd-MM-yyyy"));
            jsonRelatie.setOverlijdensdatumOpgemaakt(relatie.getOverlijdensdatum().toString("dd-MM-yyyy"));
        }
        if (relatie.getGeslacht() != null) {
            jsonRelatie.setGeslacht(relatie.getGeslacht().getOmschrijving());
        }
        if (relatie.getBurgerlijkeStaat() != null) {
            jsonRelatie.setBurgerlijkeStaat(relatie.getBurgerlijkeStaat().getOmschrijving());
        }
        for (OnderlingeRelatie ol : relatie.getOnderlingeRelaties()) {
            jsonRelatie.getOnderlingeRelaties().add(jsonOnderlingeRelatie(ol));
        }
        //        jsonRelatie.setBijlages(bijlageMapper.mapAllNaarJson(relatie.getBijlages()));

        return jsonRelatie;
    }

    private JsonOnderlingeRelatie jsonOnderlingeRelatie(OnderlingeRelatie onderlingeRelatie) {
        JsonOnderlingeRelatie jsonOnderlingeRelatie = new JsonOnderlingeRelatie();

        jsonOnderlingeRelatie.setIdRelatieMet(onderlingeRelatie.getRelatieMet().getId());

        StringBuffer naam = new StringBuffer();
        if (onderlingeRelatie.getRelatieMet().getRoepnaam() != null) {
            naam.append(onderlingeRelatie.getRelatieMet().getRoepnaam());
        } else {
            naam.append(onderlingeRelatie.getRelatieMet().getVoornaam());
        }
        naam.append(" ");
        if (onderlingeRelatie.getRelatieMet().getTussenvoegsel() != null) {
            naam.append(onderlingeRelatie.getRelatieMet().getTussenvoegsel());
            naam.append(" ");
        }
        naam.append(onderlingeRelatie.getRelatieMet().getAchternaam());

        jsonOnderlingeRelatie.setRelatieMet(naam.toString());
        jsonOnderlingeRelatie.setSoortRelatie(onderlingeRelatie.getOnderlingeRelatieSoort().getOmschrijving());

        return jsonOnderlingeRelatie;
    }
}
