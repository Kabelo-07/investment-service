package com.investment.utils;

import com.investment.domain.valueobjects.ProductType;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class WithdrawalValidationCommandTest {

    @Test
    void willSuccessfullyCreateWithdrawalCommand() {
        WithdrawalValidationCommand command = WithdrawalValidationCommand.instanceOf(LocalDate.now(),
                BigDecimal.valueOf(150),
                ProductType.SAVINGS,
                BigDecimal.valueOf(10));

        assertNotNull(command);
        assertEquals(LocalDate.now(), command.getDateOfBirth());
        assertEquals(10L, command.getWithdrawalAmount().longValue());
        assertEquals(150L, command.getBalance().longValue());
        assertEquals(ProductType.SAVINGS, command.getProductType());
    }

    @Test
    void willThrowException_whenProductType_isNotSetOnCommand() {
        assertThrows(NullPointerException.class, () -> WithdrawalValidationCommand.instanceOf(LocalDate.now(),
                BigDecimal.valueOf(150), null, BigDecimal.valueOf(10)));
    }

    @Test
    void willThrowException_whenDateOfBirth_isNotSetOnCommand() {
        assertThrows(NullPointerException.class, () -> WithdrawalValidationCommand.instanceOf(null,
                BigDecimal.valueOf(150), ProductType.RETIREMENT, BigDecimal.valueOf(10)));
    }

    @Test
    void willThrowException_whenBalance_isNotSetOnCommand() {
        assertThrows(IllegalArgumentException.class, () -> WithdrawalValidationCommand.instanceOf(LocalDate.now(),
                BigDecimal.valueOf(0), ProductType.RETIREMENT, BigDecimal.valueOf(10)));
    }

    @Test
    void willThrowException_whenWithdrawalAmount_isNotSetOnCommand() {
        assertThrows(IllegalArgumentException.class, () -> WithdrawalValidationCommand.instanceOf(LocalDate.now(),
                BigDecimal.valueOf(20), ProductType.RETIREMENT, BigDecimal.valueOf(0)));
    }

}