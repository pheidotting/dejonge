package nl.dias.web.medewerker;

import nl.dias.domein.json.JsonRisicoAnalyse;
import nl.dias.mapper.Mapper;
import nl.dias.service.RisicoAnalyseService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.inject.Inject;
import javax.ws.rs.QueryParam;

@RequestMapping("/risicoanalyse")
@Controller
public class RisicoAnalyseController {
    @Inject
    private Mapper mapper;
    @Inject
    private RisicoAnalyseService risicoAnalyseService;

    @RequestMapping(method = RequestMethod.GET, value = "/lees")
    @ResponseBody
    public JsonRisicoAnalyse lees(@QueryParam("bedrijfsId") Long bedrijfsId) {
        return (JsonRisicoAnalyse) mapper.map(risicoAnalyseService.leesBijBedrijf(bedrijfsId));
    }
}
