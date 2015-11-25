package nl.dias.domein.json.comperators;

import nl.dias.domein.json.JsonJaarCijfers;

import java.util.Comparator;

public class JsonJaarCijfersComparator implements Comparator<JsonJaarCijfers> {
    @Override
    public int compare(JsonJaarCijfers o1, JsonJaarCijfers o2) {
        return o2.getJaar().compareTo(o1.getJaar());
    }
}
