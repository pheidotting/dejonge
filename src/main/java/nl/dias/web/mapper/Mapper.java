package nl.dias.web.mapper;

import java.util.List;
import java.util.Set;

public interface Mapper<T, Z> {
    T mapVanJson(Z json);

    Set<T> mapAllVanJson(List<Z> jsons);

    Z mapNaarJson(T object);

    List<Z> mapAllNaarJson(Set<T> objecten);
}
