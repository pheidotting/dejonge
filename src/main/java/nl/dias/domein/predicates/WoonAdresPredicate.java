package nl.dias.domein.predicates;

import com.google.common.base.Predicate;
import nl.dias.domein.Adres;

import static nl.dias.domein.Adres.SoortAdres.WOONADRES;

/**
 * Created by patrickheidotting on 29-09-15.
 */
public class WoonAdresPredicate implements Predicate<Adres> {
    @Override
    public boolean apply(Adres adres) {
        return WOONADRES.equals(adres.getSoortAdres());
    }
}
