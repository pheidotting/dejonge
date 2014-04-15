package nl.dias.inloggen;

public final class Sessie {
	private Sessie() {
	}

	// Zodat Hibernate Envers deze uit kan lezen..
	private static Long ingelogdeGebruiker;

	public static Long getIngelogdeGebruiker() {
		return ingelogdeGebruiker;
	}

	public static void setIngelogdeGebruiker(Long ingelogdeGebruiker) {
		Sessie.ingelogdeGebruiker = ingelogdeGebruiker;
	}
}
