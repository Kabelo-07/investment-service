package com.investment.validators.withdrawal;

import com.investment.config.AppProperties;
import com.investment.utils.WithdrawalValidationCommand;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.core.Ordered;
import org.springframework.validation.annotation.Validated;

import jakarta.validation.constraints.NotNull;

@RequiredArgsConstructor
@Getter
public abstract class WithdrawalValidator implements Ordered {

    private final AppProperties properties;

    public abstract void validate(@Validated @NotNull WithdrawalValidationCommand command);
}
