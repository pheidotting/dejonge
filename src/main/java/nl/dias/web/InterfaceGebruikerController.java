package nl.dias.web;

import javax.ws.rs.core.Response;

import nl.dias.domein.json.JsonBedrijf;
import nl.dias.domein.json.JsonLijstRelaties;
import nl.dias.domein.json.JsonRelatie;

public interface InterfaceGebruikerController {
    JsonRelatie lees(String id);

    JsonLijstRelaties lijstRelaties(String weglaten);

    Response opslaan(JsonRelatie jsonRelatie);

    Response opslaanBedrijf(JsonBedrijf jsonBedrijf);

    Response verwijderen(Long id);

    String toevoegenRelatieRelatie(String sidAanToevoegen, String sidToeTeVoegen, String soortRelatie);

    String isIngelogd();
}
