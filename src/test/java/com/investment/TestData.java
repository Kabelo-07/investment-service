package com.investment;

import com.investment.domain.entity.Investor;
import com.investment.domain.valueobjects.Address;

import java.time.LocalDate;
import java.time.YearMonth;

public final class TestData {

    public static Investor buildNewInvestor(String firstName, String lastName, String mobileNo,
                                      String emailAddress) {
        return Investor.builder()
                .emailAddress(emailAddress)
                .firstName(firstName)
                .lastName(lastName)
                .mobileNumber(mobileNo)
                .address(Address.builder()
                        .addressLine1("123 Street")
                        .city("Johannesburg")
                        .suburb("Fordsburg")
                        .province("Gauteng")
                        .postalCode("2001")
                        .build())
                .dateOfBirth(LocalDate.of(YearMonth.now().getYear() - 20, YearMonth.now().getMonth(), 22))
                .build();
    }

    public static Investor buildNewInvestor(String firstName, String lastName, String mobileNo,
                                      String emailAddress, LocalDate dateOfBirth) {
        return Investor.builder()
                .emailAddress(emailAddress)
                .firstName(firstName)
                .lastName(lastName)
                .mobileNumber(mobileNo)
                .address(Address.builder()
                        .addressLine1("123 Street")
                        .city("Johannesburg")
                        .suburb("Fordsburg")
                        .province("Gauteng")
                        .postalCode("2001")
                        .build())
                .dateOfBirth(dateOfBirth)
                .build();
    }

}
