package nl.dias.service;

import nl.dias.domein.RekeningNummer;
import nl.dias.repository.RekeningNummerRepository;
import nl.dias.web.SoortEntiteit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

@Service
public class RekeningNummerService {
    private static final Logger LOGGER = LoggerFactory.getLogger(RekeningNummerService.class);

    @Inject
    private RekeningNummerRepository rekeningNummerRepository;

    public List<RekeningNummer> alles(SoortEntiteit soortEntiteit, Long parentId) {
        LOGGER.debug("alles soortEntiteit {} parentId {}", soortEntiteit, parentId);

        return rekeningNummerRepository.alles(soortEntiteit, parentId);
    }
}
