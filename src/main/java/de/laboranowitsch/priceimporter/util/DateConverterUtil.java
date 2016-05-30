package de.laboranowitsch.priceimporter.util;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Converter for String Zoned Date Times (Energy data is coming from Australia so we are setting that timezone).
 *
 * @author christian@laboranowitsch.de
 */
public class DateConverterUtil {

    /**
     * Returns a DateTime from String.
     *
     * @param datePattern
     * @return DateTime object with {@link ZoneId} of Australia
     */
    public static ZonedDateTime convertString2ZoneDateTime(String datePattern) {
        return ZonedDateTime.parse(datePattern,
                DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss")
                        .withZone(ZoneId.of("Australia/NSW")));
    }

}
