package com.example.backend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(YearNotFoundException.class)
    public ResponseEntity<ErrorObject> handleYearNotFoundException(YearNotFoundException ex, WebRequest request) {
        ErrorObject errorObject = new ErrorObject();

        errorObject.setStatusCode(HttpStatus.NOT_FOUND.value());
        errorObject.setMessage(ex.getMessage());
        errorObject.setTimestamp(new Date());

        return new ResponseEntity<>(errorObject, HttpStatus.NOT_FOUND);
    }

//    @ExceptionHandler(EmptyResultDataAccessException.class)
//    public ResponseEntity<String> handleEmptyResultDataAccess(EmptyResultDataAccessException ex) {
//        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Resource not found");
//    }
//
//    @ExceptionHandler(RuntimeException.class)
//    public ResponseEntity<String> handleRuntimeException(RuntimeException ex) {
//        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
//    }
//
//    @ExceptionHandler(ValidationException.class)
//    public ResponseEntity<String> handleValidationException(ValidationException ex) {
//        return ResponseEntity.badRequest().body(ex.getMessage());
//    }
//
//    @ExceptionHandler(org.springframework.http.converter.HttpMessageNotReadableException.class)
//    public final ResponseEntity<String> handleHttpMessageNotReadableException(
//            org.springframework.http.converter.HttpMessageNotReadableException ex) {
//
//        CustomErrorResponse error = new CustomErrorResponse(
//                "Invalid request body",
//                "The required request body is either missing or improperly formatted."
//        );
//
//        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
//    }

}

