package com.investment.utils;

import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.Optional;

public enum MoneyUtil {
    INSTANCE;

    private static final NumberFormat NUMBER_FORMAT = NumberFormat.getCurrencyInstance(new Locale("en", "ZA"));

    public static long calculatePercentageBetween(BigDecimal value, BigDecimal value2) {
        return BigDecimal.valueOf(value.doubleValue() / value2.doubleValue() * 100)
                .setScale(0, RoundingMode.HALF_EVEN)
                .longValue();
    }

    public static BigDecimal roundedAmount(BigDecimal amount, int scale) {
        return Optional.ofNullable(amount)
                .map(bigDecimal -> bigDecimal.setScale(scale, RoundingMode.HALF_EVEN))
                .orElse(BigDecimal.ZERO);
    }

    public static String formatToCurrency(BigDecimal amount) {
        return Optional.ofNullable(amount)
                .map(bigDecimal -> NUMBER_FORMAT.format(amount.longValue()))
                .orElse(StringUtils.EMPTY);
    }
}
