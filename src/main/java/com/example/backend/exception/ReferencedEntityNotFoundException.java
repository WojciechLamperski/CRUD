package com.example.backend.exception;

public class ReferencedEntityNotFoundException extends RuntimeException {
    private static final long serialVersionUID = 1L;
    public ReferencedEntityNotFoundException(String message) {
        super(message);
    }
}