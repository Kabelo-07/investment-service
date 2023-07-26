package com.investment.validators.withdrawal;

import com.investment.config.AppProperties;
import com.investment.domain.valueobjects.ProductType;
import com.investment.exceptions.InvalidWithdrawalRequestException;
import com.investment.utils.DateUtil;
import com.investment.utils.WithdrawalValidationCommand;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class WithdrawalProductValidator extends WithdrawalValidator {

    public WithdrawalProductValidator(AppProperties properties) {
        super(properties);
    }

    @Override
    public void validate(WithdrawalValidationCommand command) {
        if (ProductType.RETIREMENT != command.getProductType()) {
            return;
        }

        int age = DateUtil.calculateYearsBetween(command.getDateOfBirth(), LocalDate.now());

        int retirementWithdrawalAge = getProperties().getRetirementWithdrawalAge();

        if (age < getProperties().getRetirementWithdrawalAge()) {
            throw new InvalidWithdrawalRequestException(
                    "invalid.withdrawal.age",
                    String.format("Investor must be %s years or older to make a Retirement withdrawal", retirementWithdrawalAge)
            );
        }
    }

    @Override
    public int getOrder() {
        return 1;
    }
}
