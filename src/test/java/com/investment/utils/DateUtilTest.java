package com.investment.utils;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class DateUtilTest {

    @Test
    void calculateYearsBetween() {
        assertEquals(2, DateUtil.calculateYearsBetween(LocalDate.now().minusYears(2), LocalDate.now()));
        assertEquals(45, DateUtil.calculateYearsBetween(LocalDate.now().minusYears(45), LocalDate.now()));
        assertEquals(0, DateUtil.calculateYearsBetween(LocalDate.now(), LocalDate.now()));
    }
}