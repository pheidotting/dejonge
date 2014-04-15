package nl.dias.domein.json;

import nl.dias.domein.Bijlage;

public class BijlageJson extends Bijlage {
	private static final long serialVersionUID = -5063913480307509239L;

	public BijlageJson(Bijlage bijlage) {
		this.setId(bijlage.getId());
	}
}
