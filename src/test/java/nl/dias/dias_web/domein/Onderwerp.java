package nl.dias.dias_web.domein;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

public abstract class Onderwerp {

	private Long id;
	private String identificatie;
	private String wachtwoord;
	private String salt;

	public String genereerNieuwWachtwoord() {
		this.wachtwoord = UUID.randomUUID().toString().replace("-", "");
		return this.wachtwoord;
	}

	public void setHashWachtwoord(String wachtwoord) {
		this.wachtwoord = hash(wachtwoord + getSalt());
	}

	public String hash(String tekst) {
		MessageDigest md = null;
		try {
			md = MessageDigest.getInstance("SHA-512");
			// Change this to "UTF-16" if needed
			md.update(tekst.getBytes("UTF-8"));
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		if (md != null) {
			byte[] digest = md.digest();
			BigInteger bigInt = new BigInteger(1, digest);
			return bigInt.toString(16);
		} else {
			return null;
		}
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getIdentificatie() {
		return identificatie;
	}

	public void setIdentificatie(String identificatie) {
		this.identificatie = identificatie;
		this.setSalt(null);
		this.getSalt();
	}

	public String getWachtwoord() {
		return wachtwoord;
	}

	public void setWachtwoord(String wachtwoord) {
		this.wachtwoord = wachtwoord;
		this.setSalt(null);
	}

	public String getSalt() {
		if (this.salt == null || this.salt.equals("")) {
			if (this.identificatie != null && !this.identificatie.equals("")) {
				this.salt = hash(this.identificatie);
			} else {
				String zout = UUID.randomUUID().toString();
				this.salt = hash(zout);
			}
		}
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}
}
