package com.investment.exceptions;

import java.util.UUID;

public class InvestorNotFoundException extends RuntimeException {

    public InvestorNotFoundException(UUID id) {
        super(String.format("Investor not found with given id: %s", id));
    }
}
