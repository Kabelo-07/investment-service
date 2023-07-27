package com.investment.validators.withdrawal;

import com.investment.config.AppProperties;
import com.investment.utils.WithdrawalValidationCommand;
import com.investment.validators.Validator;
import lombok.Getter;
import org.springframework.validation.annotation.Validated;

import jakarta.validation.constraints.NotNull;

@Getter
public abstract class WithdrawalValidator extends Validator<WithdrawalValidationCommand> {

    protected WithdrawalValidator(AppProperties properties) {
        super(properties);
    }

    public abstract void validate(@Validated @NotNull WithdrawalValidationCommand command);
}
