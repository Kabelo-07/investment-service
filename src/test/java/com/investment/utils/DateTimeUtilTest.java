package com.investment.utils;

import org.junit.jupiter.api.Test;

import java.time.*;

import static org.junit.jupiter.api.Assertions.*;

class DateTimeUtilTest {

    @Test
    void calculateYearsBetween() {
        assertEquals(2, DateTimeUtil.calculateYearsBetween(LocalDate.now().minusYears(2), LocalDate.now()));
        assertEquals(45, DateTimeUtil.calculateYearsBetween(LocalDate.now().minusYears(45), LocalDate.now()));
        assertEquals(0, DateTimeUtil.calculateYearsBetween(LocalDate.now(), LocalDate.now()));
    }

    @Test
    void formatToShortDateTime() {
        ZoneId zoneId = ZoneId.of("Africa/Johannesburg");
        assertEquals("2023-07-27 22:50", DateTimeUtil.formatToShortDateTime(
                LocalDateTime.of(2023, Month.JULY, 27, 22, 50)
                        .atZone(zoneId).toInstant()
        ));
    }
}