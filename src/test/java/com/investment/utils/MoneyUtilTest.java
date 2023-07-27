package com.investment.utils;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class MoneyUtilTest {

    @Test
    void calculatePercentageBetween() {
        assertEquals(0, MoneyUtil.calculatePercentageBetween(BigDecimal.valueOf(0), BigDecimal.valueOf(100)));
        assertEquals(10, MoneyUtil.calculatePercentageBetween(BigDecimal.valueOf(100), BigDecimal.valueOf(1000)));
        assertEquals(75, MoneyUtil.calculatePercentageBetween(BigDecimal.valueOf(75), BigDecimal.valueOf(100)));

        BigDecimal value = BigDecimal.valueOf(0);
        BigDecimal value1 = BigDecimal.valueOf(0);
        assertThrows(NumberFormatException.class, () -> MoneyUtil.calculatePercentageBetween(value, value1));
    }

    @Test
    void formatToCurrency() {
        assertEquals("R2 000,00", MoneyUtil.formatToCurrency(BigDecimal.valueOf(2000.00)));
        assertEquals(StringUtils.EMPTY, MoneyUtil.formatToCurrency(null));
    }
}