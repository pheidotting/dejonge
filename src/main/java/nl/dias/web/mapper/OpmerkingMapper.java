package nl.dias.web.mapper;

import nl.dias.domein.*;
import nl.dias.domein.json.JsonOpmerking;
import nl.dias.domein.polis.Polis;
import nl.dias.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.inject.Inject;

@Component
public class OpmerkingMapper extends Mapper<Opmerking, JsonOpmerking> {
    private final static Logger LOGGER = LoggerFactory.getLogger(OpmerkingMapper.class);

    @Inject
    private SchadeService schadeService;
    @Inject
    private HypotheekService hypotheekService;
    @Inject
    private PolisService polisService;
    @Inject
    private GebruikerService gebruikerService;
    @Inject
    private BedrijfService bedrijfService;
    @Inject
    private AangifteService aangifteService;
    @Inject
    private JaarCijfersService jaarCijfersService;
    @Inject
    private RisicoAnalyseService risicoAnalyseService;

    @Override
    public Opmerking mapVanJson(JsonOpmerking jsonOpmerking) {
        Opmerking opmerking = new Opmerking();

        opmerking.setId(jsonOpmerking.getId());
        opmerking.setOpmerking(jsonOpmerking.getOpmerking());

        if (jsonOpmerking.getSchade() != null) {
            Schade schade = schadeService.lees(Long.valueOf(jsonOpmerking.getSchade()));
            opmerking.setSchade(schade);
        }
        if (jsonOpmerking.getHypotheek() != null) {
            Hypotheek hypotheek = hypotheekService.leesHypotheek(Long.valueOf(jsonOpmerking.getHypotheek()));
            opmerking.setHypotheek(hypotheek);
        }
        if (jsonOpmerking.getPolis() != null) {
            Polis polis = polisService.lees(Long.valueOf(jsonOpmerking.getPolis()));
            opmerking.setPolis(polis);
        }

        if (jsonOpmerking.getRelatie() != null) {
            Relatie relatie = gebruikerService.leesRelatie(Long.valueOf(jsonOpmerking.getRelatie()));
            opmerking.setRelatie(relatie);
        }

        if (jsonOpmerking.getBedrijf() != null) {
            Bedrijf bedrijf = bedrijfService.lees((Long.valueOf(jsonOpmerking.getBedrijf())));
            opmerking.setBedrijf(bedrijf);
        }

        if (jsonOpmerking.getAangifte() != null) {
            Aangifte aangifte = aangifteService.lees((Long.valueOf(jsonOpmerking.getAangifte())));
            opmerking.setAangifte(aangifte);
        }

        if (jsonOpmerking.getJaarcijfers() != null) {
            JaarCijfers jaarCijfers = jaarCijfersService.lees(Long.valueOf(jsonOpmerking.getJaarcijfers()));
            opmerking.setJaarCijfers(jaarCijfers);
        }

        if (jsonOpmerking.getRisicoAnalyse() != null) {
            RisicoAnalyse risicoAnalyse = risicoAnalyseService.lees(Long.valueOf(jsonOpmerking.getRisicoAnalyse()));
            opmerking.setRisicoAnalyse(risicoAnalyse);
        }

        LOGGER.debug("Opzoeken Medewerker met id {}", jsonOpmerking.getMedewerkerId());
        opmerking.setMedewerker((Medewerker) gebruikerService.lees(Long.valueOf(jsonOpmerking.getMedewerkerId())));

        LOGGER.debug("gemapte opmerking {}", opmerking);

        return opmerking;
    }

    @Override
    public JsonOpmerking mapNaarJson(Opmerking opmerking) {
        JsonOpmerking jsonOpmerking = new JsonOpmerking();

        jsonOpmerking.setId(opmerking.getId());
        jsonOpmerking.setOpmerking(opmerking.getOpmerking());
        jsonOpmerking.setTijd(opmerking.getTijd().toString("dd-MM-yyyy HH:mm"));
        jsonOpmerking.setMedewerker(opmerking.getMedewerker().getNaam());
        jsonOpmerking.setMedewerkerId(opmerking.getMedewerker().getId().toString());
        if (opmerking.getSchade() != null) {
            jsonOpmerking.setSchade(opmerking.getSchade().getId().toString());
        }
        if (opmerking.getPolis() != null) {
            jsonOpmerking.setPolis(opmerking.getPolis().getId().toString());
        }
        if (opmerking.getHypotheek() != null) {
            jsonOpmerking.setHypotheek(opmerking.getHypotheek().getId().toString());
        }
        if (opmerking.getBedrijf() != null) {
            jsonOpmerking.setBedrijf(opmerking.getBedrijf().getId().toString());
        }
        if (jsonOpmerking.getAangifte() != null) {
            jsonOpmerking.setAangifte(opmerking.getAangifte().getId().toString());
        }

        return jsonOpmerking;
    }

    public void setSchadeService(SchadeService schadeService) {
        this.schadeService = schadeService;
    }

}
