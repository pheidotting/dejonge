package nl.dias.web.mapper;

import nl.dias.domein.Bedrijf;
import nl.dias.domein.BurgerlijkeStaat;
import nl.dias.domein.Geslacht;
import nl.dias.domein.OnderlingeRelatie;
import nl.dias.domein.Relatie;
import nl.dias.domein.json.JsonRelatie;

import com.sun.jersey.api.core.InjectParam;

public class RelatieMapper {
    @InjectParam
    private TelefoonnummerMapper telefoonnummerMapper;
    @InjectParam
    private RekeningnummerMapper rekeningnummerMapper;
    @InjectParam
    private OpmerkingMapper opmerkingMapper;

    public Relatie mapVanJson(JsonRelatie jsonRelatie) {
        Relatie relatie = new Relatie();
        relatie.setId(jsonRelatie.getId());
        relatie.setIdentificatie(jsonRelatie.getIdentificatie());
        relatie.setVoornaam(jsonRelatie.getVoornaam());
        relatie.setTussenvoegsel(jsonRelatie.getTussenvoegsel());
        relatie.setAchternaam(jsonRelatie.getAchternaam());
        relatie.getAdres().setStraat(jsonRelatie.getStraat());
        relatie.getAdres().setHuisnummer(jsonRelatie.getHuisnummer());
        relatie.getAdres().setToevoeging(jsonRelatie.getToevoeging());
        relatie.getAdres().setPostcode(jsonRelatie.getPostcode());
        relatie.getAdres().setPlaats(jsonRelatie.getPlaats());
        relatie.setTelefoonnummers(telefoonnummerMapper.mapAllVanJson(jsonRelatie.getTelefoonnummers()));
        relatie.setBsn(jsonRelatie.getBsn());
        relatie.setRekeningnummers(rekeningnummerMapper.mapAllVanJson(jsonRelatie.getRekeningnummers()));
        relatie.setOpmerkingen(opmerkingMapper.mapAllVanJson(jsonRelatie.getOpmerkingen()));
        relatie.setGeboorteDatum(jsonRelatie.getGeboorteDatum());
        relatie.setOverlijdensdatum(jsonRelatie.getOverlijdensdatum());
        relatie.setGeslacht(Geslacht.valueOf(jsonRelatie.getGeslacht()));
        relatie.setBurgerlijkeStaat(BurgerlijkeStaat.valueOf(jsonRelatie.getBurgerlijkeStaat()));

        return relatie;
    }

    public JsonRelatie mapNaarJson(Relatie relatie) {
        JsonRelatie jsonRelatie = new JsonRelatie();

        jsonRelatie.setId(relatie.getId());
        jsonRelatie.setIdentificatie(relatie.getIdentificatie());
        jsonRelatie.setVoornaam(relatie.getVoornaam());
        jsonRelatie.setTussenvoegsel(relatie.getTussenvoegsel());
        jsonRelatie.setAchternaam(relatie.getAchternaam());
        jsonRelatie.setStraat(relatie.getAdres().getStraat());
        jsonRelatie.setHuisnummer(relatie.getAdres().getHuisnummer());
        jsonRelatie.setToevoeging(relatie.getAdres().getToevoeging());
        jsonRelatie.setPostcode(relatie.getAdres().getPostcode());
        jsonRelatie.setPlaats(relatie.getAdres().getPlaats());

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
            sb.append(jsonRelatie.getPlaats() + " ");
        }

        jsonRelatie.setAdresOpgemaakt(sb.toString());
        jsonRelatie.setTelefoonnummers(telefoonnummerMapper.mapAllNaarJson(relatie.getTelefoonnummers()));
        jsonRelatie.setBsn(relatie.getBsn());
        jsonRelatie.setRekeningnummers(rekeningnummerMapper.mapAllNaarJson(relatie.getRekeningnummers()));
        jsonRelatie.setKantoor(relatie.getKantoor().getId());
        jsonRelatie.setOpmerkingen(opmerkingMapper.mapAllNaarJson(relatie.getOpmerkingen()));
        jsonRelatie.setGeboorteDatum(relatie.getGeboorteDatum());
        jsonRelatie.setGeboorteDatumOpgemaakt(relatie.getGeboorteDatum().toString("dd-MM-yyyy"));
        jsonRelatie.setOverlijdensdatum(relatie.getOverlijdensdatum());
        jsonRelatie.setOverlijdensdatumOpgemaakt(relatie.getOverlijdensdatum().toString("dd-MM-yyyy"));
        if (relatie.getGeslacht() != null) {
            jsonRelatie.setGeslacht(relatie.getGeslacht().getOmschrijving());
        }
        if (relatie.getBurgerlijkeStaat() != null) {
            jsonRelatie.setBurgerlijkeStaat(relatie.getBurgerlijkeStaat().getOmschrijving());
        }
        for (OnderlingeRelatie ol : relatie.getOnderlingeRelaties()) {
            jsonRelatie.getOnderlingeRelaties().add(ol.getId());
        }
        for (Bedrijf b : relatie.getBedrijven()) {
            jsonRelatie.getBedrijven().add(b.getId());
        }

        return jsonRelatie;
    }
}
