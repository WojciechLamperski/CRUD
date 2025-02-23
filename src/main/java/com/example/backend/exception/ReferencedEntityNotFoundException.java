package com.example.backend.exception;

import java.io.Serial;

public class ReferencedEntityNotFoundException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 1L;
    public ReferencedEntityNotFoundException(String message) {
        super(message);
    }
}