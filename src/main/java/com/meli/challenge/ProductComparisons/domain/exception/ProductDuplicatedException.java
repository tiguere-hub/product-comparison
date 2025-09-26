package com.meli.challenge.ProductComparisons.domain.exception;

public class ProductDuplicatedException extends IllegalArgumentException {
    public ProductDuplicatedException(String message) {
        super(message);
    }
}
