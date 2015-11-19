package nl.dias.domein.json;

import nl.dias.domein.Opmerking;

import java.util.List;
import java.util.Set;

public interface ObjectMetJsonOpmerkingen {
    List<JsonOpmerking> getOpmerkingen();

    void setOpmerkingen(List<JsonOpmerking> opmerkingen);
}
