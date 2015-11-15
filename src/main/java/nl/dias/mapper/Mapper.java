package nl.dias.mapper;


import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.util.List;

@Component
public class Mapper {
    @Inject
    private List<AbstractMapper> mappers;

    public Object map(Object objectIn) {
        for (AbstractMapper mapper : mappers) {
            if (mapper.isVoorMij(objectIn)) {
                return mapper.map(objectIn);
            }
        }
        return null;
    }
}
