package com.investment.exceptions;

public class InvestorViolationException extends RuntimeException {

    public InvestorViolationException() {
        super("Investor already exists");
    }

}
