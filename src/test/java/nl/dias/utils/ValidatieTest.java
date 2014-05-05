package nl.dias.utils;

import static org.junit.Assert.*;

import nl.dias.exception.BsnNietGoedException;

import org.junit.Test;

public class ValidatieTest {

	@Test
	public void checkBsnTeKort() {
		try {
			Validatie.checkBsn("12345678");
			fail("te kort, exception verwacht");
		} catch (BsnNietGoedException e) {
		}
	}

	@Test
	public void checkBsnTeLang() {
		try {
			Validatie.checkBsn("1234567890");
			fail("te lang, exception verwacht");
		} catch (BsnNietGoedException e) {
		}
	}
	
	@Test
	public void checkBsnNietGoed() {
		try {
			Validatie.checkBsn("123456789");
			fail("niet goed, exception verwacht");
		} catch (BsnNietGoedException e) {
		}
	}
	
	@Test
	public void checkBsnCorrect() {
		try {
			Validatie.checkBsn("103127586");
		} catch (BsnNietGoedException e) {
			fail("deze moet goed gaan");
		}
	}

}
