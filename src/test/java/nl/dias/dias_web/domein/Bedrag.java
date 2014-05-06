package nl.dias.dias_web.domein;

public class Bedrag {

	private Double bedrag;

	protected Bedrag() {
	}

	public Bedrag(String bedrag) {
		this(Long.parseLong(bedrag));
	}

	public Bedrag(Long bedrag) {
		this(bedrag.doubleValue());
	}

	public Bedrag(Double bedrag) {
		this.bedrag = bedrag;
	}

	public Double getBedrag() {
		return bedrag;
	}

	public void setBedrag(Double bedrag) {
		this.bedrag = bedrag;
	}

	// public String toString() {
	// return bedrag.toString();
	// }
}
