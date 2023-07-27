package com.investment.validators;

import com.investment.config.AppProperties;
import com.investment.exceptions.InvalidInvestorAgeException;
import com.investment.utils.BirthDateValidationCommand;
import com.investment.utils.DateUtil;
import jakarta.validation.ConstraintValidatorContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.Month;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BirthDateValidatorTest {

    BirthDateValidator validator = null;

    @Mock
    AppProperties properties;

    @BeforeEach
    void setUp() {
        validator = new BirthDateValidator(properties);
    }

    @Test
    void testWillThrowException_whenInvestorAge_isLessThanMinimumInvestmentAge() {
        when(properties.getMinInvestmentAge()).thenReturn(19);

        BirthDateValidationCommand command = BirthDateValidationCommand.instanceOf(LocalDate.now());
        assertThrows(InvalidInvestorAgeException.class, () -> validator.validate(command));
    }

    @Test
    void testWillNotThrowException_whenInvestorAge_isGreaterThanMinimumInvestmentAge() {
        when(properties.getMinInvestmentAge()).thenReturn(19);

        BirthDateValidationCommand command = BirthDateValidationCommand.instanceOf(LocalDate.of(2002, Month.JANUARY, 28));
        assertDoesNotThrow(() -> validator.validate(command));
    }

    @Test
    void testWillNotThrowException_whenInvestorAge_isEqualToMinimumInvestmentAge() {
        when(properties.getMinInvestmentAge()).thenReturn(10);

        BirthDateValidationCommand command = BirthDateValidationCommand.instanceOf(LocalDate.now().minusYears(10));
        assertDoesNotThrow(() -> validator.validate(command));
    }

    @Test
    void testWillThrowException_whenInvestorAge_isNotPassed() {
        assertThrows(NullPointerException.class, () -> BirthDateValidationCommand.instanceOf(null));
    }
}