package nl.dias.service;

import static org.junit.Assert.assertEquals;

import java.util.List;

import nl.dias.domein.VerzekeringsMaatschappij;

import org.junit.Before;
import org.junit.Test;

public class VerzekeringsMaatschappijServiceTest {
	private VerzekeringsMaatschappijService verzekeringsMaatschappijService;

	@Before
	public void setUp() throws Exception {
		verzekeringsMaatschappijService = new VerzekeringsMaatschappijService();
		verzekeringsMaatschappijService.setPersistenceContext("unittest");
	}

	@Test
	public void zoekOpNaam() {
		VerzekeringsMaatschappij maatschappij = new VerzekeringsMaatschappij();
		maatschappij.setNaam("naam1");
		VerzekeringsMaatschappij maatschappij1 = new VerzekeringsMaatschappij();
		maatschappij1.setNaam("naam2");

		verzekeringsMaatschappijService.opslaan(maatschappij);
		verzekeringsMaatschappijService.opslaan(maatschappij1);

		assertEquals(maatschappij.getId(), verzekeringsMaatschappijService.zoekOpNaam("naam1").getId());
		assertEquals(maatschappij1.getId(), verzekeringsMaatschappijService.zoekOpNaam("naam2").getId());
	}

	@Test
	public void alles() {
		VerzekeringsMaatschappij maatschappij = new VerzekeringsMaatschappij();
		maatschappij.setNaam("aa");
		VerzekeringsMaatschappij maatschappij1 = new VerzekeringsMaatschappij();
		maatschappij1.setNaam("cc");
		VerzekeringsMaatschappij maatschappij2 = new VerzekeringsMaatschappij();
		maatschappij2.setNaam("bb");

		verzekeringsMaatschappijService.opslaan(maatschappij1);
		verzekeringsMaatschappijService.opslaan(maatschappij);
		verzekeringsMaatschappijService.opslaan(maatschappij2);

		List<VerzekeringsMaatschappij> lijst = verzekeringsMaatschappijService.alles();
		assertEquals(3, lijst.size());
		assertEquals("aa", lijst.get(0).getNaam());
		assertEquals("bb", lijst.get(1).getNaam());
		assertEquals("cc", lijst.get(2).getNaam());

	}

}
