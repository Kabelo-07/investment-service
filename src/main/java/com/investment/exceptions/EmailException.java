package com.investment.exceptions;

public class EmailException extends RuntimeException {

    public EmailException(String message) {
        super(message);
    }

    public EmailException(Throwable throwable) {
        super(throwable);
    }
}
