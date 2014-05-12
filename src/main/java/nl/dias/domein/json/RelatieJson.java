package nl.dias.domein.json;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import nl.dias.domein.OnderlingeRelatie;
import nl.dias.domein.Opmerking;
import nl.dias.domein.RekeningNummer;
import nl.dias.domein.Relatie;
import nl.dias.domein.Telefoonnummer;
import nl.dias.domein.polis.Polis;

import org.joda.time.LocalDate;

public class RelatieJson extends Relatie implements Cloneable {
    private static final long serialVersionUID = -4901599374023740553L;

    private String straat;
    private Long huisnummer;
    private String toevoeging;
    private String postcode;
    private String plaats;
    private String geboorteDatumString;
    private String overlijdensdatumString;
    private List<OnderlingeRelatieJson> onderlingeRelatieJsons;

    public RelatieJson(Relatie relatie) {
        this.setVoornaam(relatie.getVoornaam());
        this.setAchternaam(relatie.getAchternaam());
        this.setBsn(relatie.getBsn());
        this.setBurgerlijkeStaat(relatie.getBurgerlijkeStaat());
        this.setGeboorteDatumString(relatie.getGeboorteDatum().toString("dd-MM-yyyy"));
        this.setGeslacht(relatie.getGeslacht());
        this.setHuisnummer(relatie.getAdres().getHuisnummer());
        this.setId(relatie.getId());
        this.setIdentificatie(relatie.getIdentificatie());
        this.setKantoor(relatie.getKantoor());
        this.setOnderlingeRelaties(relatie.getOnderlingeRelaties());
        this.setOpmerkingen(relatie.getOpmerkingen());
        this.setOverlijdensdatumString(relatie.getOverlijdensdatum().toString("dd-MM-yyyy"));
        this.setPlaats(relatie.getAdres().getPlaats());
        this.setPostcode(relatie.getAdres().getPostcode());
        this.setRekeningnummers(relatie.getRekeningnummers());
        this.setStraat(relatie.getAdres().getStraat());
        this.setTelefoonnummers(relatie.getTelefoonnummers());
        this.setToevoeging(relatie.getAdres().getToevoeging());
        this.setTussenvoegsel(relatie.getTussenvoegsel());
        this.setVoornaam(relatie.getVoornaam());
        this.setSalt(null);
        this.setPolissen(relatie.getPolissen());

        for (RekeningNummer r : this.getRekeningnummers()) {
            r.setRelatie(null);
        }

        for (Telefoonnummer t : this.getTelefoonnummers()) {
            t.setRelatie(null);
        }

        for (Opmerking o : this.getOpmerkingen()) {
            o.setRelatie(null);
        }

        for (OnderlingeRelatie o : this.getOnderlingeRelaties()) {
            OnderlingeRelatieJson orj = new OnderlingeRelatieJson(o);
            this.getOnderlingeRelatieJsons().add(orj);
        }
        this.setOnderlingeRelaties(null);

        for (Polis p : this.getPolissen()) {
            p.setRelatie(null);
        }

        this.setBedrijven(relatie.getBedrijven());
    }

    public String getGeboorteDatumString() {
        return geboorteDatumString;
    }

    public void setGeboorteDatumString(String geboorteDatumString) {
        this.geboorteDatumString = geboorteDatumString;
    }

    public String getOverlijdensdatumString() {
        return overlijdensdatumString;
    }

    public void setOverlijdensdatumString(String overlijdensdatumString) {
        this.overlijdensdatumString = overlijdensdatumString;
    }

    public String getStraat() {
        return straat;
    }

    public void setStraat(String straat) {
        this.straat = straat;
    }

    public Long getHuisnummer() {
        return huisnummer;
    }

    public void setHuisnummer(Long huisnummer) {
        this.huisnummer = huisnummer;
    }

    public String getToevoeging() {
        return toevoeging;
    }

    public void setToevoeging(String toevoeging) {
        this.toevoeging = toevoeging;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public String getPlaats() {
        return plaats;
    }

    public void setPlaats(String plaats) {
        this.plaats = plaats;
    }

    public List<OnderlingeRelatieJson> getOnderlingeRelatieJsons() {
        if (onderlingeRelatieJsons == null) {
            onderlingeRelatieJsons = new ArrayList<>();
        }
        return onderlingeRelatieJsons;
    }

    public void setOnderlingeRelatieJsons(List<OnderlingeRelatieJson> onderlingeRelatieJsons) {
        this.onderlingeRelatieJsons = onderlingeRelatieJsons;
    }

    public Relatie clone() throws CloneNotSupportedException {
        Relatie relatie = new Relatie();
        relatie.setAchternaam(getAchternaam());
        relatie.setBsn(getBsn());
        relatie.setBurgerlijkeStaat(getBurgerlijkeStaat());
        relatie.setGeslacht(getGeslacht());
        relatie.setId(getId());
        relatie.setIdentificatie(getIdentificatie());
        relatie.setKantoor(getKantoor());
        relatie.setOnderlingeRelaties(getOnderlingeRelaties());
        relatie.setOpmerkingen(getOpmerkingen());
        relatie.setRekeningnummers(getRekeningnummers());
        relatie.setTelefoonnummers(getTelefoonnummers());
        relatie.setTussenvoegsel(getTussenvoegsel());
        relatie.setVoornaam(getVoornaam());
        relatie.getAdres().setHuisnummer(getHuisnummer());
        relatie.getAdres().setPlaats(getPlaats());
        relatie.getAdres().setPostcode(getPostcode());
        relatie.getAdres().setStraat(getStraat());
        relatie.getAdres().setToevoeging(getToevoeging());
        relatie.setPolissen(getPolissen());

        Date date = new Date();
        if (geboorteDatumString != null && !geboorteDatumString.equals("")) {
            try {
                date = new SimpleDateFormat("dd-MM-yyyy", Locale.GERMANY).parse(geboorteDatumString);
                relatie.setGeboorteDatum(new LocalDate(date));
            } catch (ParseException e) {
            }
        }

        date = new Date();
        if (overlijdensdatumString != null && !overlijdensdatumString.equals("")) {
            try {
                date = new SimpleDateFormat("dd-MM-yyyy", Locale.GERMANY).parse(overlijdensdatumString);
                relatie.setOverlijdensdatum(new LocalDate(date));
            } catch (ParseException e) {
            }
        }
        return relatie;
    }
}
