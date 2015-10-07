package nl.dias.domein.json.predicates;

import com.google.common.base.Predicate;
import nl.dias.domein.json.JsonAdres;

/**
 * Created by patrickheidotting on 29-09-15.
 */
public class JsonWoonAdresPredicate implements Predicate<JsonAdres> {
    @Override
    public boolean apply(JsonAdres adres) {
        return "WOONADRES".equals(adres.getSoortAdres());
    }
}
