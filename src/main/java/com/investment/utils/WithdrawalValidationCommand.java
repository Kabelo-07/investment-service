package com.investment.utils;

import com.investment.domain.valueobjects.ProductType;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

@Getter
public class WithdrawalValidationCommand implements AbstractValidationCommand {

    private final LocalDate dateOfBirth;
    private final BigDecimal balance;
    private final ProductType productType;
    private final BigDecimal withdrawalAmount;

    public static WithdrawalValidationCommand instanceOf(LocalDate dateOfBirth, BigDecimal balance,
                                                         ProductType productType,
                                                         BigDecimal withdrawalAmount) {
        return new WithdrawalValidationCommand(dateOfBirth, balance, productType, withdrawalAmount);
    }

    private WithdrawalValidationCommand(LocalDate dateOfBirth, BigDecimal balance, ProductType productType,
                                        BigDecimal withdrawalAmount) {

        this.balance = Objects.requireNonNull(balance, "balance missing");
        this.withdrawalAmount = Objects.requireNonNull(withdrawalAmount, "withdrawal amount missing");

        validateAmountIsNotLessThanZero(balance);
        validateAmountIsNotLessThanZero(withdrawalAmount);

        this.productType = Objects.requireNonNull(productType, "productType missing");
        this.dateOfBirth = Objects.requireNonNull(dateOfBirth, "date of birth missing");
    }

    private void validateAmountIsNotLessThanZero(BigDecimal amount) {
        if (amount.longValue() <= 0) {
            throw new IllegalArgumentException("Invalid amount parsed");
        }
    }
}
