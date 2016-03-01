package nl.dias.domein;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.joda.time.LocalDate;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class KantoorJson extends Kantoor {
    private static final long serialVersionUID = 78054457231283351L;

    private String adresStraat;
    private Long adresHuisnummer;
    private String adresToevoeging;
    private String adresPostcode;
    private String adresPlaats;

    private String factuurAdresStraat;
    private Long factuurAdresHuisnummer;
    private String factuurAdresToevoeging;
    private String factuurAdresPostcode;
    private String factuurAdresPlaats;

    private String datumOprichtingString;

    public KantoorJson(Kantoor kantoor) {
        //        Adres postAdres = kantoor.getPostAdres();
        //        Adres factuurAdres = kantoor.getFactuurAdres();

        //        this.adresHuisnummer = postAdres.getHuisnummer();
        //        this.adresPlaats = postAdres.getPlaats();
        //        this.adresPostcode = postAdres.getPostcode();
        //        this.adresStraat = postAdres.getStraat();
        //        this.adresToevoeging = postAdres.getToevoeging();
        //
        //        this.factuurAdresHuisnummer = factuurAdres.getHuisnummer();
        //        this.factuurAdresPlaats = factuurAdres.getPlaats();
        //        this.factuurAdresPostcode = factuurAdres.getPostcode();
        //        this.factuurAdresStraat = factuurAdres.getStraat();
        //        this.factuurAdresToevoeging = factuurAdres.getToevoeging();

        this.setBtwNummer(kantoor.getBtwNummer());
        this.setDatumOprichtingString(kantoor.getDatumOprichting().toString("dd-MM-yyyy"));
        this.setEmailadres(kantoor.getEmailadres());
        this.setId(kantoor.getId());
        this.setKvk(kantoor.getKvk());
        this.setNaam(kantoor.getNaam());
        this.setRechtsvorm(kantoor.getRechtsvorm());
        this.setSoortKantoor(kantoor.getSoortKantoor());
        this.setMedewerkers(kantoor.getMedewerkers());
    }

    public Kantoor clone(Kantoor kantoor) {
        Kantoor ret = new Kantoor();
        if (kantoor == null) {
            ret.setId(null);
        } else {
            ret.setId(kantoor.getId());
        }

        Adres postAdres = new Adres();

        postAdres.setHuisnummer(this.getAdresHuisnummer());
        postAdres.setPlaats(this.getAdresPlaats());
        postAdres.setPostcode(this.getAdresPostcode());
        postAdres.setStraat(this.getAdresStraat());
        postAdres.setToevoeging(this.getAdresToevoeging());
        postAdres.setSoortAdres(Adres.SoortAdres.POSTADRES);

        Adres factuurAdres = new Adres();
        factuurAdres.setHuisnummer(this.getFactuurAdresHuisnummer());
        factuurAdres.setPlaats(this.getFactuurAdresPlaats());
        factuurAdres.setPostcode(this.getFactuurAdresPostcode());
        factuurAdres.setStraat(this.getFactuurAdresStraat());
        factuurAdres.setToevoeging(this.getFactuurAdresToevoeging());
        factuurAdres.setSoortAdres(Adres.SoortAdres.FACTUURADRES);

        ret.setBtwNummer(this.getBtwNummer());
        if (this.getDatumOprichtingString() != null && !"".equals(this.getDatumOprichtingString())) {
            try {
                Date date = new SimpleDateFormat("dd-MM-yyyy", Locale.GERMANY).parse(this.getDatumOprichtingString());
                ret.setDatumOprichting(new LocalDate(date));
            } catch (ParseException e) {
            }
        }

        ret.setEmailadres(this.getEmailadres());
        ret.setId(this.getId());
        ret.setKvk(this.getKvk());
        ret.setNaam(this.getNaam());
        ret.setRechtsvorm(this.getRechtsvorm());
        ret.setSoortKantoor(this.getSoortKantoor());

        return ret;
    }

    public String getAdresStraat() {
        return adresStraat;
    }

    public void setAdresStraat(String adresStraat) {
        this.adresStraat = adresStraat;
    }

    public Long getAdresHuisnummer() {
        return adresHuisnummer;
    }

    public void setAdresHuisnummer(Long adresHuisnummer) {
        this.adresHuisnummer = adresHuisnummer;
    }

    public String getAdresToevoeging() {
        return adresToevoeging;
    }

    public void setAdresToevoeging(String adresToevoeging) {
        this.adresToevoeging = adresToevoeging;
    }

    public String getAdresPostcode() {
        return adresPostcode;
    }

    public void setAdresPostcode(String adresPostcode) {
        this.adresPostcode = adresPostcode;
    }

    public String getAdresPlaats() {
        return adresPlaats;
    }

    public void setAdresPlaats(String adresPlaats) {
        this.adresPlaats = adresPlaats;
    }

    public String getFactuurAdresStraat() {
        return factuurAdresStraat;
    }

    public void setFactuurAdresStraat(String factuurAdresStraat) {
        this.factuurAdresStraat = factuurAdresStraat;
    }

    public Long getFactuurAdresHuisnummer() {
        return factuurAdresHuisnummer;
    }

    public void setFactuurAdresHuisnummer(Long factuurAdresHuisnummer) {
        this.factuurAdresHuisnummer = factuurAdresHuisnummer;
    }

    public String getFactuurAdresToevoeging() {
        return factuurAdresToevoeging;
    }

    public void setFactuurAdresToevoeging(String factuurAdresToevoeging) {
        this.factuurAdresToevoeging = factuurAdresToevoeging;
    }

    public String getFactuurAdresPostcode() {
        return factuurAdresPostcode;
    }

    public void setFactuurAdresPostcode(String factuurAdresPostcode) {
        this.factuurAdresPostcode = factuurAdresPostcode;
    }

    public String getFactuurAdresPlaats() {
        return factuurAdresPlaats;
    }

    public void setFactuurAdresPlaats(String factuurAdresPlaats) {
        this.factuurAdresPlaats = factuurAdresPlaats;
    }

    public String getDatumOprichtingString() {
        return datumOprichtingString;
    }

    public void setDatumOprichtingString(String datumOprichtingString) {
        this.datumOprichtingString = datumOprichtingString;
    }

    @Override
    public int hashCode() {
        HashCodeBuilder builder = new HashCodeBuilder();
        builder.append(adresHuisnummer);
        builder.append(adresPlaats);
        builder.append(adresPostcode);
        builder.append(adresStraat);
        builder.append(adresToevoeging);
        builder.append(datumOprichtingString);
        builder.append(factuurAdresHuisnummer);
        builder.append(factuurAdresPlaats);
        builder.append(factuurAdresPostcode);
        builder.append(factuurAdresStraat);
        builder.append(factuurAdresToevoeging);
        return builder.toHashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (!super.equals(obj)) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        KantoorJson other = (KantoorJson) obj;
        return new EqualsBuilder().append(adresHuisnummer, other.adresHuisnummer).append(adresPlaats, other.adresPlaats).append(adresPostcode, other.adresPostcode)
                .append(adresStraat, other.adresStraat).append(adresToevoeging, other.adresToevoeging).append(datumOprichtingString, other.datumOprichtingString)
                .append(factuurAdresHuisnummer, other.factuurAdresHuisnummer).append(factuurAdresPlaats, other.factuurAdresPlaats).append(factuurAdresPostcode, other.factuurAdresPostcode)
                .append(factuurAdresStraat, other.factuurAdresStraat).append(factuurAdresToevoeging, other.factuurAdresToevoeging).isEquals();
    }
}
