package com.meli.challenge.ProductComparisons.infrastructure.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.support.WebExchangeBindException;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class ProductExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(ProductExceptionHandler.class);

    @ExceptionHandler(WebExchangeBindException.class)
    public ResponseEntity<List<FieldError>> handleConstraintViolationException(WebExchangeBindException ex) {
        List<FieldError> errors = ex.getBindingResult().getFieldErrors()
                .stream()
                .map(error -> new FieldError(error.getField(), error.getDefaultMessage()))
                .collect(Collectors.toList());

        log.warn("Validation Error: {}", errors);
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgumentException(IllegalArgumentException ex) {
        ErrorResponse error = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), ex.getMessage(),
                IllegalArgumentException.class.getName());
        log.warn("Illegal Argument Error: {}", error);
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }
}
