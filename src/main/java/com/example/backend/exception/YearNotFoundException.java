package com.example.backend.exception;

public class YearNotFoundException extends RuntimeException {
    private static final long serialVersionUID = 1L;
    public YearNotFoundException(String message) {
        super(message);
    }
}
