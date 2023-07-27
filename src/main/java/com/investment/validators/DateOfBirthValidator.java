package com.investment.validators;

import com.investment.utils.AppConstants;
import com.investment.utils.DateTimeUtil;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDate;

public class DateOfBirthValidator implements ConstraintValidator<DateOfBirth, LocalDate> {

    @Override
    public boolean isValid(LocalDate localDate, ConstraintValidatorContext context) {
        if (null == localDate) {
            return false;
        }

        return DateTimeUtil.calculateYearsBetween(localDate, LocalDate.now()) >= AppConstants.MIN_INVESTMENT_AGE;
    }

}
