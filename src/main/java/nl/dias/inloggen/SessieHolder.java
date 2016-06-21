package nl.dias.inloggen;

public final class SessieHolder {
    private ThreadLocal<Sessie> sessie = new ThreadLocal<Sessie>();
    public static AuditContextSession get() {
		return AuditContextSessionHolder.auditContext.get();
	}

	public static void set(AuditContextSession auditContext) {
		AuditContextSessionHolder.auditContext.set(auditContext);
	}

	public static void clear() {
		AuditContextSessionHolder.auditContext.remove();
	}
}
