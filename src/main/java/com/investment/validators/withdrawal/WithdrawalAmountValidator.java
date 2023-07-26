package com.investment.validators.withdrawal;

import com.investment.config.AppProperties;
import com.investment.exceptions.InvalidWithdrawalRequestException;
import com.investment.utils.MoneyUtil;
import com.investment.utils.WithdrawalValidationCommand;
import org.springframework.stereotype.Component;

@Component
public class WithdrawalAmountValidator extends WithdrawalValidator {

    public WithdrawalAmountValidator(AppProperties properties) {
        super(properties);
    }

    @Override
    public void validate(WithdrawalValidationCommand command) {
        long calculatedPercentage = MoneyUtil.calculatePercentageBetween(command.getWithdrawalAmount(), command.getBalance());

        final int maxWithdrawalPercentage = getProperties().getMaxWithdrawalPercentage();

        if (calculatedPercentage > maxWithdrawalPercentage) {
            throw new InvalidWithdrawalRequestException(
                    "invalid.withdrawal.amount",
                    String.format("Cannot withdraw more than %s percent of investment", maxWithdrawalPercentage)
            );
        }
    }

    @Override
    public int getOrder() {
        return 3;
    }
}
