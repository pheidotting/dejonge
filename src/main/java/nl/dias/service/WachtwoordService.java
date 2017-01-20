package nl.dias.service;

import nl.dias.domein.Gebruiker;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

@Service
public class WachtwoordService {
    public String hashWachtwoord(String wachtwoord, Gebruiker gebruiker) {
        String ww = null;
        try {
            ww = wachtwoord + gebruiker.getSalt();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return ww;
    }
}
