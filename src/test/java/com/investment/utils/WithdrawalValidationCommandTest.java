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
        LocalDate localDate = LocalDate.now();
        BigDecimal balance = BigDecimal.valueOf(150);
        BigDecimal withdrawalAmount = BigDecimal.valueOf(10);
        assertThrows(NullPointerException.class, () -> WithdrawalValidationCommand.instanceOf(localDate,
                balance, null, withdrawalAmount));
    }

    @Test
    void willThrowException_whenDateOfBirth_isNotSetOnCommand() {
        BigDecimal balance = BigDecimal.valueOf(150);
        BigDecimal withdrawalAmount = BigDecimal.valueOf(10);
        assertThrows(NullPointerException.class, () -> WithdrawalValidationCommand.instanceOf(null,
                balance, ProductType.RETIREMENT, withdrawalAmount));
    }

    @Test
    void willThrowException_whenBalance_isNotSetOnCommand() {
        BigDecimal balance = BigDecimal.valueOf(0);
        BigDecimal withdrawalAmount = BigDecimal.valueOf(10);
        LocalDate localDate = LocalDate.now();

        assertThrows(IllegalArgumentException.class, () -> WithdrawalValidationCommand.instanceOf(localDate,
                balance, ProductType.RETIREMENT, withdrawalAmount));
    }

    @Test
    void willThrowException_whenWithdrawalAmount_isNotSetOnCommand() {
        BigDecimal balance = BigDecimal.valueOf(20);
        BigDecimal withdrawalAmount = BigDecimal.valueOf(0);
        LocalDate localDate = LocalDate.now();

        assertThrows(IllegalArgumentException.class, () -> WithdrawalValidationCommand.instanceOf(localDate,
                balance, ProductType.RETIREMENT, withdrawalAmount));
    }

}