package com.investment.validators;

import com.investment.config.AppProperties;
import com.investment.exceptions.InvalidInvestorAgeException;
import com.investment.utils.BirthDateValidationCommand;
import com.investment.utils.DateTimeUtil;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class BirthDateValidator extends Validator<BirthDateValidationCommand> {

    public BirthDateValidator(AppProperties properties) {
        super(properties);
    }

    @Override
    public void validate(BirthDateValidationCommand command) {
        int investmentAge = getProperties().getMinInvestmentAge();

        boolean isValidAge = DateTimeUtil.calculateYearsBetween(command.getDateOfBirth(), LocalDate.now()) >= investmentAge;
        if (!isValidAge) {
            throw new InvalidInvestorAgeException();
        }
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
