package nl.dias.dias_web.domein;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.joda.time.LocalDate;

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
		this.adresHuisnummer = kantoor.getAdres().getHuisnummer();
		this.adresPlaats = kantoor.getAdres().getPlaats();
		this.adresPostcode = kantoor.getAdres().getPostcode();
		this.adresStraat = kantoor.getAdres().getStraat();
		this.adresToevoeging = kantoor.getAdres().getToevoeging();

		this.factuurAdresHuisnummer = kantoor.getFactuurAdres().getHuisnummer();
		this.factuurAdresPlaats = kantoor.getFactuurAdres().getPlaats();
		this.factuurAdresPostcode = kantoor.getFactuurAdres().getPostcode();
		this.factuurAdresStraat = kantoor.getFactuurAdres().getStraat();
		this.factuurAdresToevoeging = kantoor.getFactuurAdres().getToevoeging();

		this.setBtwNummer(kantoor.getBtwNummer());
		this.setDatumOprichtingString(kantoor.getDatumOprichting().toString("dd-MM-yyyy"));
		this.setEmailadres(kantoor.getEmailadres());
		this.setId(kantoor.getId());
		this.setKvk(kantoor.getKvk());
		this.setNaam(kantoor.getNaam());
		this.setRechtsvorm(kantoor.getRechtsvorm());
		this.setSoortKantoor(kantoor.getSoortKantoor());
		this.setMedewerkers(kantoor.getMedewerkers());
		this.setOpmerkingen(kantoor.getOpmerkingen());
		this.setRekeningnummers(kantoor.getRekeningnummers());
	}

	public Kantoor clone(Kantoor kantoor) {
		Kantoor ret = new Kantoor();
		if (kantoor == null) {
			ret.setId(null);
		} else {
			ret.setId(kantoor.getId());
		}
		ret.getAdres().setHuisnummer(this.getAdresHuisnummer());
		ret.getAdres().setPlaats(this.getAdresPlaats());
		ret.getAdres().setPostcode(this.getAdresPostcode());
		ret.getAdres().setStraat(this.getAdresStraat());
		ret.getAdres().setToevoeging(this.getAdresToevoeging());

		ret.getFactuurAdres().setHuisnummer(this.getFactuurAdresHuisnummer());
		ret.getFactuurAdres().setPlaats(this.getFactuurAdresPlaats());
		ret.getFactuurAdres().setPostcode(this.getFactuurAdresPostcode());
		ret.getFactuurAdres().setStraat(this.getFactuurAdresStraat());
		ret.getFactuurAdres().setToevoeging(this.getFactuurAdresToevoeging());

		ret.setBtwNummer(this.getBtwNummer());
		if (this.getDatumOprichtingString() != null && !this.getDatumOprichtingString().equals("")) {
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
}
