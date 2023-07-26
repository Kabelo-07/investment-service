package com.investment.exceptions;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
@SuperBuilder
public class ErrorResponse {
    private String message;

    @JsonProperty("error_key")
    private String errorKey;

    private List<String> errors = new ArrayList<>(1);


    public ErrorResponse(String message) {
        this.message = message;
    }

    public ErrorResponse(String message, String errorKey) {
        this.message = message;
        this.errorKey = errorKey;
    }

    public ErrorResponse(String message, List<String> errors) {
        this.message = message;
        this.errors = errors;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<String> getErrors() {
        return errors;
    }

    public void setErrors(List<String> errors) {
        this.errors = errors;
    }

    public String getErrorKey() {
        return errorKey;
    }

    public void setErrorKey(String errorKey) {
        this.errorKey = errorKey;
    }
}
