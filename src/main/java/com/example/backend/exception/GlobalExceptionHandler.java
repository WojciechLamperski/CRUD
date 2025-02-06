package com.example.backend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.util.Date;


@ControllerAdvice
public class GlobalExceptionHandler {

    // Custom error from Service implementations
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorObject> handleEntityNotFoundException(EntityNotFoundException ex, WebRequest request) {
        ErrorObject errorObject = new ErrorObject();

        errorObject.setStatusCode(HttpStatus.NOT_FOUND.value());
        errorObject.setMessage(ex.getMessage());
        errorObject.setTimestamp(new Date());

        return new ResponseEntity<ErrorObject>(errorObject, HttpStatus.NOT_FOUND);
    }

    // Handles incorrect types in URL (invalid parameter types)
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorObject> handleTypeMismatch(MethodArgumentTypeMismatchException ex, WebRequest request) {
        ErrorObject errorObject = new ErrorObject();
        errorObject.setStatusCode(HttpStatus.BAD_REQUEST.value());
        errorObject.setMessage("Invalid parameter type: " + ex.getName() +
                ". Expected type: " + ex.getRequiredType().getSimpleName());
        errorObject.setTimestamp(new Date());

        return new ResponseEntity<ErrorObject>(errorObject, HttpStatus.BAD_REQUEST);
    }

    // Handles incorrect types in request body (like null or boolean errors) with a custom message
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorObject> handleHttpMessageNotReadable(HttpMessageNotReadableException ex) {
        ErrorObject errorObject = new ErrorObject();

        errorObject.setStatusCode(HttpStatus.BAD_REQUEST.value());
        errorObject.setMessage("Invalid input. Please provide a JSON object with the required fields.");
        errorObject.setTimestamp(new Date());

        return new ResponseEntity<ErrorObject>(errorObject, HttpStatus.BAD_REQUEST);
    }

    // Handles validation errors from Jakarta validation
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorObject> handleValidationException(MethodArgumentNotValidException ex) {
        ErrorObject errorObject = new ErrorObject();

        errorObject.setStatusCode(HttpStatus.BAD_REQUEST.value());
        errorObject.setMessage(ex.getBindingResult().getFieldError().getDefaultMessage());
        errorObject.setTimestamp(new Date());

        return new ResponseEntity<ErrorObject>(errorObject, HttpStatus.BAD_REQUEST);
    }

    // Handles the case when a row doesn't exist during an update or creation requiring a reference
    @ExceptionHandler(ReferencedEntityNotFoundException.class)
    public ResponseEntity<ErrorObject> handleReferencedEntityNotFoundException(ReferencedEntityNotFoundException ex) {
        ErrorObject errorObject = new ErrorObject();

        errorObject.setStatusCode(HttpStatus.NOT_FOUND.value());
        errorObject.setMessage("Referenced entity not found: " + ex.getMessage());
        errorObject.setTimestamp(new Date());

        return new ResponseEntity<ErrorObject>(errorObject, HttpStatus.NOT_FOUND);
    }

    // Handles the case when a requested object/page does not exist (e.g., 404 for resources or pages)
    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<ErrorObject> handleNoHandlerFoundException(NoHandlerFoundException ex) {
        ErrorObject errorObject = new ErrorObject();

        errorObject.setStatusCode(HttpStatus.NOT_FOUND.value());
        errorObject.setMessage("Resource not found: " + ex.getRequestURL());
        errorObject.setTimestamp(new Date());

        return new ResponseEntity<ErrorObject>(errorObject, HttpStatus.NOT_FOUND);
    }
}
