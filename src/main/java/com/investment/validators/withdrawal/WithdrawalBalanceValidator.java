package com.investment.validators.withdrawal;

import com.investment.config.AppProperties;
import com.investment.exceptions.InvalidWithdrawalRequestException;
import com.investment.utils.WithdrawalValidationCommand;
import org.springframework.stereotype.Component;

@Component
public class WithdrawalBalanceValidator extends WithdrawalValidator {

    public WithdrawalBalanceValidator(AppProperties properties) {
        super(properties);
    }

    @Override
    public void validate(WithdrawalValidationCommand command) {
        if (command.getBalance().longValue() < command.getWithdrawalAmount().longValue()) {
            throw new InvalidWithdrawalRequestException("insufficient.balance", "Balance is less than requested amount");
        }
    }

    @Override
    public int getOrder() {
        return 2;
    }
}
