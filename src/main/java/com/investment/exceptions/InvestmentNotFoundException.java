package com.investment.exceptions;

import java.util.UUID;

public class InvestmentNotFoundException extends RuntimeException {

    public InvestmentNotFoundException(UUID id) {
        super("Invalid invest");
    }
}