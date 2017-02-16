package nl.dias.messaging.reciever;

import nl.dias.messaging.sender.OpslaanEntiteitenRequestSender;
import nl.lakedigital.as.messaging.domain.Polis;
import nl.lakedigital.as.messaging.domain.SoortEntiteit;
import nl.lakedigital.as.messaging.request.OpslaanEntiteitenRequest;
import nl.lakedigital.as.messaging.request.PolisOpslaanRequest;
import nl.lakedigital.as.messaging.response.PolisOpslaanResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;

public class PolisOpslaanResponseReciever extends AbstractReciever<PolisOpslaanResponse> {
    private static final Logger LOGGER = LoggerFactory.getLogger(PolisOpslaanResponseReciever.class);

    public PolisOpslaanResponseReciever() {
        super(PolisOpslaanResponse.class, LOGGER);
    }

    @Inject
    private OpslaanEntiteitenRequestSender opslaanEntiteitenRequestSender;

    @Override
    public void verwerkMessage(PolisOpslaanResponse polisOpslaanResponse) {
        PolisOpslaanRequest polisOpslaanRequest = (PolisOpslaanRequest) polisOpslaanResponse.getAntwoordOp();

        OpslaanEntiteitenRequest opslaanEntiteitenRequest = new OpslaanEntiteitenRequest();

        polisOpslaanRequest.getPolissen().stream().forEach(polis -> {
            //id van de polis opzoeken
            Polis opgeslagenPolis = polisOpslaanResponse.getPolissen().stream().filter(p -> p.getIdentificatie()//
                    .equals(polis.getIdentificatie())).findFirst().orElse(null);

            polis.getOpmerkingen().stream().forEach(opmerking -> {
                opmerking.setEntiteitId(opgeslagenPolis.getId());
                opmerking.setSoortEntiteit(SoortEntiteit.POLIS);

                opslaanEntiteitenRequest.getLijst().add(opmerking);
            });
        });

        if (!opslaanEntiteitenRequest.getLijst().isEmpty()) {
            opslaanEntiteitenRequestSender.send(opslaanEntiteitenRequest);
        }
    }
}
