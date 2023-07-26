package com.investment.utils;

import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;

public enum DateUtil {
    INSTANCE;

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    private static final DateTimeFormatter LONG_DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");

    public static int calculateYearsBetween(LocalDate startDate, LocalDate endDate) {
        return Period.between(startDate, endDate).getYears();
    }

    public static String formatToShortDateTime(Instant instant) {
        return DATE_TIME_FORMATTER.format(instant.atZone(ZoneId.systemDefault()).toLocalDateTime());
    }

    /**
     *
     * @return current dateTime in yyyyMMddHHmmss string format
     */
    public static String currentDateTimeInLongFormat() {
        return LONG_DATE_TIME_FORMATTER.format(LocalDateTime.now());
    }
}
