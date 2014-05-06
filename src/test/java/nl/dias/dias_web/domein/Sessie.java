package nl.dias.dias_web.domein;

import java.util.Date;

public class Sessie {

	private Long id;
	private String sessie;
	private String ipadres;
	private String cookieCode;
	private String browser;
	private Date datumGecreerd;
	private Date datumLaatstGebruikt;
	private Gebruiker gebruiker;

	public Sessie() {
		datumGecreerd = new Date();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getSessie() {
		return sessie;
	}

	public void setSessie(String sessie) {
		this.sessie = sessie;
	}

	public String getIpadres() {
		return ipadres;
	}

	public void setIpadres(String ipadres) {
		this.ipadres = ipadres;
	}

	public String getCookieCode() {
		return cookieCode;
	}

	public void setCookieCode(String cookieCode) {
		this.cookieCode = cookieCode;
	}

	public String getBrowser() {
		return browser;
	}

	public void setBrowser(String browser) {
		this.browser = browser;
	}

	public Date getDatumGecreerd() {
		return datumGecreerd;
	}

	public void setDatumGecreerd(Date datumGecreerd) {
		this.datumGecreerd = datumGecreerd;
	}

	public Date getDatumLaatstGebruikt() {
		return datumLaatstGebruikt;
	}

	public void setDatumLaatstGebruikt(Date datumLaatstGebruikt) {
		this.datumLaatstGebruikt = datumLaatstGebruikt;
	}

	public Gebruiker getGebruiker() {
		return gebruiker;
	}

	public void setGebruiker(Gebruiker gebruiker) {
		this.gebruiker = gebruiker;
	}
}
