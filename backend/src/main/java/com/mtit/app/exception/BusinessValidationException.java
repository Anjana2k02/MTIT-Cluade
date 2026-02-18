package com.mtit.app.exception;

import java.util.Map;

public class BusinessValidationException extends RuntimeException {

    private final Map<String, String> errors;

    public BusinessValidationException(Map<String, String> errors) {
        super("Business validation failed");
        this.errors = errors;
    }

    public BusinessValidationException(String field, String message) {
        super("Business validation failed");
        this.errors = Map.of(field, message);
    }

    public Map<String, String> getErrors() {
        return errors;
    }
}
