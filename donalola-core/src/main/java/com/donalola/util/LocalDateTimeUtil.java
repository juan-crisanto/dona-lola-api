package com.donalola.util;

import java.time.LocalDateTime;
import java.time.ZoneId;

public class LocalDateTimeUtil {

    public static final ZoneId PERU_ZONE_ID = ZoneId.of("America/Lima");

    public static LocalDateTime getFrom(LocalDateTime localDateTime, ZoneId zoneId) {
        ZoneId currentZoneId = ZoneId.systemDefault();
        LocalDateTime newLocalDateTime = localDateTime
                .atZone(currentZoneId)
                .withZoneSameInstant(zoneId)
                .toLocalDateTime();
        return newLocalDateTime;
    }


}
