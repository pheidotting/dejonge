package nl.dias.web.mapper;

import java.util.ArrayList;
import java.util.List;

import nl.dias.domein.OnderlingeRelatie;
import nl.dias.domein.Relatie;
import nl.dias.domein.json.JsonBijlage;
import nl.dias.domein.json.JsonRelatie;
import nl.dias.domein.json.JsonSchade;
import nl.dias.domein.polis.Polis;

import com.sun.jersey.api.core.InjectParam;

public class RelatieMapper extends Mapper<Relatie, JsonRelatie> {
    @InjectParam
    private TelefoonnummerMapper telefoonnummerMapper;
    @InjectParam
    private RekeningnummerMapper rekeningnummerMapper;
    @InjectParam
    private OpmerkingMapper opmerkingMapper;
    @InjectParam
    private PolisMapper polisMapper;
    @InjectParam
    private BedrijfMapper bedrijfMapper;
    @InjectParam
    private BijlageMapper bijlageMapper;
    @InjectParam
    private SchadeMapper schadeMapper;

    @Override
    public Relatie mapVanJson(JsonRelatie jsonRelatie) {
        // Relatie relatie = new Relatie();
        // relatie.setId(jsonRelatie.getId());
        // relatie.setIdentificatie(jsonRelatie.getIdentificatie());
        // relatie.setVoornaam(jsonRelatie.getVoornaam());
        // relatie.setTussenvoegsel(jsonRelatie.getTussenvoegsel());
        // relatie.setAchternaam(jsonRelatie.getAchternaam());
        // relatie.getAdres().setStraat(jsonRelatie.getStraat());
        // relatie.setOverlijdensdatum(new
        // LocalDate(jsonRelatie.getOverlijdensdatum()));
        // try {
        // relatie.getAdres().setHuisnummer(Long.valueOf(jsonRelatie.getHuisnummer()));
        // } catch (NumberFormatException nfe) {
        // throw new
        // NumberFormatException("Huisnummer mag alleen cijfers bevatten");
        // }
        // relatie.getAdres().setToevoeging(jsonRelatie.getToevoeging());
        // relatie.getAdres().setPostcode(jsonRelatie.getPostcode());
        // relatie.getAdres().setPlaats(jsonRelatie.getPlaats());
        // relatie.setTelefoonnummers(telefoonnummerMapper.mapAllVanJson(jsonRelatie.getTelefoonnummers()));
        // relatie.setBsn(jsonRelatie.getBsn());
        // relatie.setRekeningnummers(rekeningnummerMapper.mapAllVanJson(jsonRelatie.getRekeningnummers()));
        // relatie.setOpmerkingen(opmerkingMapper.mapAllVanJson(jsonRelatie.getOpmerkingen()));
        // // relatie.setGeboorteDatum(jsonRelatie.getGeboorteDatum());
        // // relatie.setOverlijdensdatum(jsonRelatie.getOverlijdensdatum());
        // relatie.setGeslacht(Geslacht.valueOf(jsonRelatie.getGeslacht().substring(0,
        // 1)));
        // relatie.setBurgerlijkeStaat(BurgerlijkeStaat.valueOf(jsonRelatie.getBurgerlijkeStaat().substring(0,
        // 1)));

        return null;
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
        if (jsonRelatie.getToevoeging() != null) {
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
        jsonRelatie.setBedrijven(bedrijfMapper.mapAllNaarJson(relatie.getBedrijven()));
        jsonRelatie.setPolissen(polisMapper.mapAllNaarJson(relatie.getPolissen()));

        List<JsonBijlage> bijlages = new ArrayList<JsonBijlage>();
        List<JsonSchade> schades = new ArrayList<>();
        for (Polis polis : relatie.getPolissen()) {
            bijlages.addAll(bijlageMapper.mapAllNaarJson(polis.getBijlages()));
            schades.addAll(schadeMapper.mapAllNaarJson(polis.getSchades()));
        }

        jsonRelatie.setLijstBijlages(bijlages);
        jsonRelatie.setSchades(schades);

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

    public void setPolisMapper(PolisMapper polisMapper) {
        this.polisMapper = polisMapper;
    }

    public void setBedrijfMapper(BedrijfMapper bedrijfMapper) {
        this.bedrijfMapper = bedrijfMapper;
    }

    public void setBijlageMapper(BijlageMapper bijlageMapper) {
        this.bijlageMapper = bijlageMapper;
    }
}
