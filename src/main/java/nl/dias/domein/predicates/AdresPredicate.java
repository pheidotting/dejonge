package nl.dias.domein.predicates;

import com.google.common.base.Predicate;
import nl.dias.domein.Adres;

import static nl.dias.domein.Adres.SoortAdres;

public class AdresPredicate implements Predicate<Adres> {
    private SoortAdres soortAdres;

    public AdresPredicate(SoortAdres soortAdres) {
        this.soortAdres = soortAdres;
    }

    @Override
    public boolean apply(Adres adres) {
        return soortAdres.equals(adres.getSoortAdres());
    }
}
