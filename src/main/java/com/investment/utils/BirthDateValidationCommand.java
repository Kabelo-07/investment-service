package com.investment.utils;

import lombok.Getter;

import java.time.LocalDate;
import java.util.Objects;

@Getter
public class BirthDateValidationCommand implements AbstractValidationCommand {

    private final LocalDate dateOfBirth;

    public static BirthDateValidationCommand instanceOf(LocalDate dateOfBirth) {
        return new BirthDateValidationCommand(dateOfBirth);
    }

    private BirthDateValidationCommand(LocalDate dateOfBirth) {
        this.dateOfBirth = Objects.requireNonNull(dateOfBirth, "dateOfBirth is required");
    }
}
