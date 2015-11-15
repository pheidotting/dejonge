package nl.dias.mapper;


import nl.dias.domein.Bedrag;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public abstract class AbstractMapper<T, Z> {

    public abstract Z map(T object);

    //    public List<Z> mapAll(Set<T> objecten) {
    //        if (objecten != null) {
    //            List<Z> ret = new ArrayList<>();
    //            for (T obj : objecten) {
    //                ret.add(map(obj));
    //            }
    //            return ret;
    //        } else {
    //            return null;
    //        }
    //
    //    }
    //
    //    public List<Z> mapAll(List<T> objecten) {
    //        if (objecten != null) {
    //            List<Z> ret = new ArrayList<>();
    //            for (T obj : objecten) {
    //                ret.add(map(obj));
    //            }
    //            return ret;
    //        } else {
    //            return null;
    //        }
    //
    //    }
    abstract boolean isVoorMij(Object object);

    protected static String zetBedragOm(Bedrag bedrag) {
        String waarde = null;
        String[] x = bedrag.getBedrag().toString().split("\\.");
        if (x[1].length() == 1) {
            waarde = bedrag.getBedrag().toString() + "0";
        } else {
            waarde = bedrag.getBedrag().toString() + "";
        }
        return waarde;
    }

}