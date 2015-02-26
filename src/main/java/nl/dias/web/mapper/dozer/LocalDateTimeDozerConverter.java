package nl.dias.web.mapper.dozer;

import org.apache.log4j.Logger;
import org.dozer.DozerConverter;
import org.joda.time.LocalDateTime;
import org.joda.time.format.DateTimeFormat;

public class LocalDateTimeDozerConverter extends DozerConverter<LocalDateTime, String> {
    private static final Logger LOGGER = Logger.getLogger(LocalDateTimeDozerConverter.class);

    public LocalDateTimeDozerConverter() {
        super(LocalDateTime.class, String.class);
    }

    @Override
    public LocalDateTime convertFrom(String arg0, LocalDateTime arg1) {
        String patternDatum = "dd-MM-yyyy hh:mm";

        if (arg0 != null && !"".equals(arg0)) {
            return LocalDateTime.parse(arg0, DateTimeFormat.forPattern(patternDatum));
        } else {
            return null;
        }
    }

    @Override
    public String convertTo(LocalDateTime datumTijdIn, String arg1) {
        String geconverteerd = datumTijdIn.toString("dd-MM-yyyy HH:mm");

        LOGGER.debug("Converteren " + datumTijdIn + " naar " + geconverteerd);

        return geconverteerd;
    }

}
