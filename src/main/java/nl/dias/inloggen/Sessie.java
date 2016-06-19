package nl.dias.inloggen;

public final class Sessie {
	private Sessie() {
	}

	// Zodat Hibernate Envers deze uit kan lezen..
	private static Long ingelogdeGebruiker;
	private static String trackAndTraceId;

	public static Long getIngelogdeGebruiker() {
		return ingelogdeGebruiker;
	}

	public static void setIngelogdeGebruiker(Long ingelogdeGebruiker) {
		Sessie.ingelogdeGebruiker = ingelogdeGebruiker;
	}

	public static String getTrackAndTraceId() {
		return trackAndTraceId;
	}

	public static void setTrackAndTraceId(String trackAndTraceId) {
		Sessie.trackAndTraceId = trackAndTraceId;
	}
}
