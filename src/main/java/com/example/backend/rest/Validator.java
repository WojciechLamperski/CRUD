package com.example.backend.rest;

import com.example.backend.exception.ValidationException;
import org.springframework.util.ObjectUtils;

public class Validator {

    public static void validateId(int id) {
        if (id <= 0) {
            throw new ValidationException("Id must be greater than 1");
        }
    }
    public static void validateObject(Object object) {
        if (object == null || ObjectUtils.isEmpty(object)) {
            throw new ValidationException("Object must not be null");
        }
    }
    public static void validateYear(int year) {
        if (year < 0) {
            throw new ValidationException("Year has to be a non-negative number");
        }
    }

}