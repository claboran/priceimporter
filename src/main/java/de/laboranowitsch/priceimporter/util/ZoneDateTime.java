package de.laboranowitsch.priceimporter.util;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Created by cla on 4/1/16.
 */
public class ZoneDateTime {

    public static ZonedDateTime of(String datePattern) {
        return ZonedDateTime.parse(datePattern,
                DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss")
                        .withZone(ZoneId.of("Australia/NSW")));
    }

}
