package com.investment.config;

import com.investment.exceptions.ErrorResponse;
import com.investment.exceptions.InvalidRequestException;
import com.investment.exceptions.InvalidWithdrawalRequestException;
import com.investment.exceptions.InvestorNotFoundException;
import com.investment.model.WithdrawalResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.StreamSupport;

@ControllerAdvice
public class ExceptionTranslator extends ResponseEntityExceptionHandler {

    /**
     *
     * @param ex InvestorNotFoundException
     * @return errorResponse body with failure details, HTTP 404
     */
    @ExceptionHandler(value = {InvestorNotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<Object> handleInvestorNotFound(InvestorNotFoundException ex) {
        return new ResponseEntity<>(new ErrorResponse(ex.getMessage()), HttpStatus.NOT_FOUND);
    }

    /**
     *
     * @param ex Exception
     * @return Http 500 response code with an errorResponse body with failure details
     */
    @ExceptionHandler(value = {Exception.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<Object> handleException(Exception ex) {
        return new ResponseEntity<>(new ErrorResponse(ex.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(value = {InvalidRequestException.class})
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public ResponseEntity<Object> handleInvalidRequest(RuntimeException ex) {
        return new ResponseEntity<>(new ErrorResponse(ex.getMessage()), HttpStatus.UNPROCESSABLE_ENTITY);
    }

    /**
     *
     * @param ex InvalidWithdrawalRequestException
     * @return UNPROCESSABLE_ENTITY(422) Http status with a ErrorResponse body
     */
    @ExceptionHandler(value = {InvalidWithdrawalRequestException.class})
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public ResponseEntity<Object> handleInvalidWithdrawalRequest(InvalidWithdrawalRequestException ex) {
        ErrorResponse response = ErrorResponse.builder()
                .message(ex.getMessage())
                .errorKey(ex.getErrorKey())
                .build();

        return new ResponseEntity<>(response, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(value = {IllegalArgumentException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Object> handleIllegalArgumentException(IllegalArgumentException ex) {
        return new ResponseEntity<>(ErrorResponse.builder()
                .message(ex.getMessage())
                .build(), HttpStatus.BAD_REQUEST);
    }

    /**
     *
     * @param ex MethodArgumentNotValidException
     * @param headers HttpHeaders
     * @param status httpStatusCode
     * @param request WebRequest
     * @return built errorResponse with a list of fieldErrors
     */
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers,
                                                                  HttpStatusCode status, WebRequest request) {
        List<String> errors = new ArrayList<>();

        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            errors.add(error.getField() + ": " + error.getDefaultMessage());
        }

        for (ObjectError error : ex.getBindingResult().getGlobalErrors()) {
            errors.add(error.getObjectName() + ": " + error.getDefaultMessage());
        }

        ErrorResponse response = new ErrorResponse("Invalid request sent", errors);

        return handleExceptionInternal(ex, response, headers, HttpStatus.BAD_REQUEST, request);
    }


    /**
     * Constraint violation exception handler
     *
     * @param ex ConstraintViolationException
     * @return List<ErrorResponse> - list of ValidationError built
     * from set of ConstraintViolation
     */
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    @ExceptionHandler({ConstraintViolationException.class})
    public List<ErrorResponse> handleConstraintViolation(ConstraintViolationException ex) {
        return buildValidationErrors(ex.getConstraintViolations());
    }

    private List<ErrorResponse> buildValidationErrors(Set<ConstraintViolation<?>> violations) {
        return violations.stream().map(this::buildErrorResponse).toList();
    }

    private ErrorResponse buildErrorResponse(ConstraintViolation<?> violation) {
        String value = String.valueOf(StreamSupport.stream(violation.getPropertyPath().spliterator(), false).
                reduce((first, second) -> second).
                orElse(null));
        return new ErrorResponse(value, violation.getMessage());
    }
}
