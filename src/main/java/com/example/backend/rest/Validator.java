package com.example.backend.rest;

import com.example.backend.exception.ValidationException;

public class Validator {

    public static void validateId(int id) {
        if (id < 0) {
            throw new ValidationException("Id must be greater than or equal to 0");
        }
    }
    public static void validateObject(Object object) {
        if (object == null) {
            throw new ValidationException("Object must not be null");
        }
    }
    public static void validateYear(int year) {
        if (year < 0) {
            throw new ValidationException("Year has to be a non-negative number");
        }
    }

}