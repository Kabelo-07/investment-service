package com.investment.exceptions;

public class InvalidWithdrawalRequestException extends RuntimeException {

    private final String errorKey;

    public InvalidWithdrawalRequestException(String key, String message) {
        super(message);
        this.errorKey = key;
    }

    public String getErrorKey() {
        return errorKey;
    }
}
