package com.investment.validators;

import com.investment.config.AppProperties;
import com.investment.utils.AbstractValidationCommand;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.core.Ordered;

@RequiredArgsConstructor
@Getter
public abstract class Validator<T extends AbstractValidationCommand> implements Ordered {

    private final AppProperties properties;

    public abstract void validate(T t);
}
