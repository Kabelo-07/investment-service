package com.investment.validators;

import jakarta.validation.ConstraintValidatorContext;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.time.LocalDate;
import java.time.Month;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class DateOfBirthValidatorTest {

    @Mock
    ConstraintValidatorContext context;

    @Test
    void willReturnFalseWhenAgeIsLessThan16Years() {
        DateOfBirthValidator validator = new DateOfBirthValidator();

        boolean valid = validator.isValid(LocalDate.now(), context);
        assertFalse(valid);
    }

    @Test
    void willReturnFalseWhenAgePassedIsNull() {
        DateOfBirthValidator validator = new DateOfBirthValidator();

        boolean valid = validator.isValid(null, context);
        assertFalse(valid);
    }

    @Test
    void willReturnTrueWhenAgeIsGreaterThan16Years() {
        DateOfBirthValidator validator = new DateOfBirthValidator();

        boolean valid = validator.isValid(LocalDate.of(2001, Month.JANUARY, 1), context);
        assertTrue(valid);
    }
}