package com.investment.exceptions;

public class InvalidInvestorAgeException extends RuntimeException {

    public InvalidInvestorAgeException() {
        super("Investor is not older to start an investment");
    }

}
