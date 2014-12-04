package nl.dias.web.mapper;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

import nl.dias.domein.BurgerlijkeStaat;
import nl.dias.domein.Geslacht;
import nl.dias.domein.OnderlingeRelatie;
import nl.dias.domein.Relatie;
import nl.dias.domein.json.JsonRelatie;

import org.apache.log4j.Logger;
import org.joda.time.LocalDate;

import com.sun.jersey.api.core.InjectParam;

public class RelatieMapper extends Mapper<Relatie, JsonRelatie> {
    @InjectParam
    private TelefoonnummerMapper telefoonnummerMapper;
    @InjectParam
    private RekeningnummerMapper rekeningnummerMapper;
    @InjectParam
    private OpmerkingMapper opmerkingMapper;

    private final static Logger LOGGER = Logger.getLogger(RelatieMapper.class);

    @Override
    public Relatie mapVanJson(JsonRelatie jsonRelatie) {
        Relatie relatie = new Relatie();
        relatie.setId(jsonRelatie.getId());
        try {
            relatie.setIdentificatie(jsonRelatie.getIdentificatie());
        } catch (UnsupportedEncodingException e) {
            LOGGER.error(e);
        } catch (NoSuchAlgorithmException e) {
            LOGGER.error(e);
        }
        relatie.setVoornaam(jsonRelatie.getVoornaam());
        relatie.setTussenvoegsel(jsonRelatie.getTussenvoegsel());
        relatie.setAchternaam(jsonRelatie.getAchternaam());
        relatie.getAdres().setStraat(jsonRelatie.getStraat());
        if (jsonRelatie.getOverlijdensdatum() != null && !"".equals(jsonRelatie.getOverlijdensdatum())) {
            relatie.setOverlijdensdatum(new LocalDate(jsonRelatie.getOverlijdensdatum()));
        }
        if (jsonRelatie.getGeboorteDatum() != null && !"".equals(jsonRelatie.getGeboorteDatum())) {
            relatie.setGeboorteDatum(new LocalDate(jsonRelatie.getGeboorteDatum()));
        }
        if (jsonRelatie.getHuisnummer() != null && !jsonRelatie.getHuisnummer().equals("")) {
            try {
                relatie.getAdres().setHuisnummer(Long.valueOf(jsonRelatie.getHuisnummer()));
            } catch (NumberFormatException nfe) {
                throw new NumberFormatException("Huisnummer mag alleen cijfers bevatten");
            }
        }
        relatie.getAdres().setToevoeging(jsonRelatie.getToevoeging());
        relatie.getAdres().setPostcode(jsonRelatie.getPostcode());
        relatie.getAdres().setPlaats(jsonRelatie.getPlaats());
        relatie.setTelefoonnummers(telefoonnummerMapper.mapAllVanJson(jsonRelatie.getTelefoonnummers()));
        relatie.setBsn(jsonRelatie.getBsn());
        relatie.setRekeningnummers(rekeningnummerMapper.mapAllVanJson(jsonRelatie.getRekeningnummers()));
        relatie.setOpmerkingen(opmerkingMapper.mapAllVanJson(jsonRelatie.getOpmerkingen()));
        relatie.setGeslacht(Geslacht.valueOf(jsonRelatie.getGeslacht().substring(0, 1)));
        relatie.setBurgerlijkeStaat(BurgerlijkeStaat.valueOf(jsonRelatie.getBurgerlijkeStaat().substring(0, 1)));
        relatie.getBedrijven();
        return relatie;
    }

    @Override
    public JsonRelatie mapNaarJson(Relatie relatie) {
        JsonRelatie jsonRelatie = new JsonRelatie();

        jsonRelatie.setId(relatie.getId());
        jsonRelatie.setIdentificatie(relatie.getIdentificatie());
        jsonRelatie.setVoornaam(relatie.getVoornaam());
        jsonRelatie.setTussenvoegsel(relatie.getTussenvoegsel());
        jsonRelatie.setAchternaam(relatie.getAchternaam());
        if (relatie.getAdres() != null) {
            jsonRelatie.setStraat(relatie.getAdres().getStraat());
            if (relatie.getAdres().getHuisnummer() != null) {
                jsonRelatie.setHuisnummer(relatie.getAdres().getHuisnummer().toString());
            }
            jsonRelatie.setToevoeging(relatie.getAdres().getToevoeging());
            jsonRelatie.setPostcode(relatie.getAdres().getPostcode());
            jsonRelatie.setPlaats(relatie.getAdres().getPlaats());
        }
        StringBuilder sb = new StringBuilder();
        if (jsonRelatie.getStraat() != null) {
            sb.append(jsonRelatie.getStraat() + " ");
        }
        if (jsonRelatie.getHuisnummer() != null) {
            sb.append(jsonRelatie.getHuisnummer() + " ");
        }
        if (jsonRelatie.getToevoeging() != null && !"".equals(jsonRelatie.getToevoeging())) {
            sb.append(jsonRelatie.getToevoeging() + " ");
        }
        if (jsonRelatie.getPlaats() != null) {
            sb.append(", " + jsonRelatie.getPlaats());
        }

        jsonRelatie.setAdresOpgemaakt(sb.toString());
        jsonRelatie.setTelefoonnummers(telefoonnummerMapper.mapAllNaarJson(relatie.getTelefoonnummers()));
        jsonRelatie.setBsn(relatie.getBsn());
        jsonRelatie.setRekeningnummers(rekeningnummerMapper.mapAllNaarJson(relatie.getRekeningnummers()));
        if (relatie.getKantoor() != null && relatie.getKantoor().getId() != null) {
            jsonRelatie.setKantoor(relatie.getKantoor().getId());
        }
        jsonRelatie.setOpmerkingen(opmerkingMapper.mapAllNaarJson(relatie.getOpmerkingen()));
        if (relatie.getGeboorteDatum() != null) {
            jsonRelatie.setGeboorteDatum(relatie.getGeboorteDatum().toString("dd-MM-yyyy"));
            jsonRelatie.setGeboorteDatumOpgemaakt(relatie.getGeboorteDatum().toString("dd-MM-yyyy"));
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
            jsonRelatie.getOnderlingeRelaties().add(ol.getId());
        }

        return jsonRelatie;
    }

    public void setTelefoonnummerMapper(TelefoonnummerMapper telefoonnummerMapper) {
        this.telefoonnummerMapper = telefoonnummerMapper;
    }

    public void setRekeningnummerMapper(RekeningnummerMapper rekeningnummerMapper) {
        this.rekeningnummerMapper = rekeningnummerMapper;
    }

    public void setOpmerkingMapper(OpmerkingMapper opmerkingMapper) {
        this.opmerkingMapper = opmerkingMapper;
    }
}
