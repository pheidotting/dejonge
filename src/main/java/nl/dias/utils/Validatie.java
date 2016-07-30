package nl.dias.utils;

import nl.dias.domein.Relatie;
import nl.dias.exception.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class Validatie {
    private static Pattern pattern;
    private static Matcher matcher;
    private static final int MAXLENGTE = 6;
    private static final int DEELEEN = 4;
    private static final int IBANNUMBERMINSIZE = 15;
    private static final int IBANNUMBERMAXSIZE = 34;
    private static final BigInteger IBANNUMBERMAGICNUMBER = new BigInteger("97");
    private static final String EMAILPATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    private static final Logger LOGGER = LoggerFactory.getLogger(Validatie.class);

    private Validatie() {
    }

    public static void valideer(Relatie relatie) throws TelefoonnummerNietGoedException, IbanNietGoedException, PostcodeNietGoedException, BsnNietGoedException {
        Validatie.checkBsn(relatie.getBsn());
    }

    public static void checkBsn(String bsn) throws BsnNietGoedException {
        int checksum = 0;

        try {
            Double.parseDouble(bsn);
        } catch (Exception e) {
            LOGGER.debug("Fout opgetreden", e);
            throw new BsnNietGoedException();
        }

        if (bsn.length() != 9) {
            throw new BsnNietGoedException();
        } else {
            for (int i = 0; i < 8; i++) {
                checksum += (Integer.parseInt(Character.toString(bsn.charAt(i))) * (9 - i));
            }
            checksum -= Integer.parseInt(Character.toString(bsn.charAt(8)));

            if (checksum % 11 != 0) {
                throw new BsnNietGoedException();
            }
        }
    }

    public static void checkIban(String accountNumber) throws IbanNietGoedException {

        String newAccountNumber = accountNumber.trim();

        // Check that the total IBAN length is correct as per the country. If
        // not, the IBAN is invalid. We could also check
        // for specific length according to country, but for now we won't
        if (newAccountNumber.length() < IBANNUMBERMINSIZE || newAccountNumber.length() > IBANNUMBERMAXSIZE) {
            throw new IbanNietGoedException();
        }

        // Move the four initial characters to the end of the string.
        newAccountNumber = newAccountNumber.substring(4) + newAccountNumber.substring(0, 4);

        // Replace each letter in the string with two digits, thereby expanding
        // the string, where A = 10, B = 11, ..., Z = 35.
        StringBuilder numericAccountNumber = new StringBuilder();
        for (int i = 0; i < newAccountNumber.length(); i++) {
            numericAccountNumber.append(Character.getNumericValue(newAccountNumber.charAt(i)));
        }

        // Interpret the string as a decimal integer and compute the remainder
        // of that number on division by 97.
        BigInteger ibanNumber = new BigInteger(numericAccountNumber.toString());
        if (!(ibanNumber.mod(IBANNUMBERMAGICNUMBER).intValue() == 1)) {
            throw new IbanNietGoedException();
        }
    }

    /**
     * Validate hex with regular expression
     * 
     * @param hex
     *            hex for validation
     * @return true valid hex, false invalid hex
     * @throws EmailAdresFoutiefException
     */
    public static void validateEmail(final String hex) throws EmailAdresFoutiefException {
        pattern = Pattern.compile(EMAILPATTERN);
        matcher = pattern.matcher(hex);

        if (!matcher.matches()) {
            throw new EmailAdresFoutiefException();
        }
    }
}
