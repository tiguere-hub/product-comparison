package com.meli.challenge.ProductComparisons.infrastructure.exception;

public record ErrorResponse(int status, String errorMessage, String exceptionType) {
}
