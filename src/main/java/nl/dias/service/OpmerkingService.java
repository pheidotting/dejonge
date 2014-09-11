package nl.dias.service;

import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;

import nl.dias.domein.Medewerker;
import nl.dias.domein.Opmerking;
import nl.dias.domein.Schade;
import nl.dias.repository.OpmerkingRepository;

import com.sun.jersey.api.core.InjectParam;

@Named
public class OpmerkingService {
    @InjectParam
    private OpmerkingRepository opmerkingRepository;
    @InjectParam
    private AuthorisatieService authorisatieService;
    @InjectParam
    private SchadeService schadeService;
    @Context
    private HttpServletRequest httpServletRequest;

    public void opslaan(Opmerking opmerking) {
        String sessie = null;
        if (httpServletRequest.getSession().getAttribute("sessie") != null && !httpServletRequest.getSession().getAttribute("sessie").equals("")) {
            sessie = httpServletRequest.getSession().getAttribute("sessie").toString();
        }

        Medewerker medewerker = (Medewerker) authorisatieService.getIngelogdeGebruiker(httpServletRequest, sessie, httpServletRequest.getRemoteAddr());

        opmerking.setMedewerker(medewerker);
        opmerkingRepository.opslaan(opmerking);

        Schade schade = schadeService.lees(opmerking.getSchade().getId());
        schade.getOpmerkingen().add(opmerking);

        schadeService.opslaan(schade);
    }

    public void setOpmerkingRepository(OpmerkingRepository opmerkingRepository) {
        this.opmerkingRepository = opmerkingRepository;
    }

}
