package nl.dias.commons;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.List;

public class DJFCToStringBuilder extends ReflectionToStringBuilder {
    public DJFCToStringBuilder(Object object) {
        super(object);
    }

    public static String toString(Object object) {
        return toString(object, ToStringStyle.SHORT_PREFIX_STYLE);
    }

    public static String toString(Object object, ToStringStyle style) {
        StringBuilder sb = new StringBuilder();

        if (object instanceof List) {
            for (Object obj : (List) object) {
                sb.append(ReflectionToStringBuilder.toString(obj, style));
            }
        } else {
            sb.append(ReflectionToStringBuilder.toString(object, style));
        }

        return sb.toString();
    }
}
