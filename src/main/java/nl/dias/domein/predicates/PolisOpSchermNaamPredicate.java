package nl.dias.domein.predicates;

import com.google.common.base.Predicate;
import nl.dias.domein.polis.Polis;

public class PolisOpSchermNaamPredicate implements Predicate<Polis> {
    private String schermNaam;

    public PolisOpSchermNaamPredicate(String schermNaam) {
        this.schermNaam = schermNaam;
    }

    @Override
    public boolean apply(Polis polis) {
        return polis.getSchermNaam().equals(schermNaam);
    }
}
