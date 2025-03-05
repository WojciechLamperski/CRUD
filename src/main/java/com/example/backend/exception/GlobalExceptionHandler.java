package com.example.backend.exception;

import com.example.backend.repository.DistrictRepositoryImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.transaction.CannotCreateTransactionException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.Date;
import java.util.Objects;


@ControllerAdvice
public class GlobalExceptionHandler {

    private Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    // Custom error from Service implementations
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorObject> handleEntityNotFoundException(EntityNotFoundException ex) {
        logger.info("EntityNotFoundException is being handled");
        ErrorObject errorObject = new ErrorObject();

        errorObject.setStatusCode(HttpStatus.NOT_FOUND.value());
        errorObject.setMessage(ex.getMessage());
        errorObject.setTimestamp(new Date());

        return new ResponseEntity<>(errorObject, HttpStatus.NOT_FOUND);
    }

    // Handles incorrect types in URL (invalid parameter types)
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorObject> handleTypeMismatch(MethodArgumentTypeMismatchException ex) {
        logger.info("MethodArgumentTypeMismatchException is being handled");
        ErrorObject errorObject = new ErrorObject();
        errorObject.setStatusCode(HttpStatus.BAD_REQUEST.value());
        errorObject.setMessage("Invalid parameter type: " + ex.getName() +
                ". Expected type: " + Objects.requireNonNull(ex.getRequiredType()).getSimpleName());
        errorObject.setTimestamp(new Date());

        return new ResponseEntity<>(errorObject, HttpStatus.BAD_REQUEST);
    }

    // Handle Invalid Sort Field Exception
    @ExceptionHandler(InvalidSortFieldException.class)
    public ResponseEntity<ErrorObject> handleInvalidSortFieldException(InvalidSortFieldException ex) {
        logger.info("InvalidSortFieldException is being handled");
        ErrorObject errorObject = new ErrorObject();
        errorObject.setStatusCode(HttpStatus.BAD_REQUEST.value());
        errorObject.setMessage(ex.getMessage());
        errorObject.setTimestamp(new Date());

        return new ResponseEntity<>(errorObject, HttpStatus.BAD_REQUEST);
    }


    // Handles incorrect types in request body (like null or boolean errors) with a custom message
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorObject> handleHttpMessageNotReadable() {
        logger.info("HttpMessageNotReadableException is being handled");
        ErrorObject errorObject = new ErrorObject();

        errorObject.setStatusCode(HttpStatus.BAD_REQUEST.value());
        errorObject.setMessage("Invalid input. Please provide a JSON object with the required fields.");
        errorObject.setTimestamp(new Date());

        return new ResponseEntity<>(errorObject, HttpStatus.BAD_REQUEST);
    }

    // Handles validation errors from Jakarta validation
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorObject> handleValidationException(MethodArgumentNotValidException ex) {
        logger.info("MethodArgumentNotValidException is being handled");
        ErrorObject errorObject = new ErrorObject();

        errorObject.setStatusCode(HttpStatus.BAD_REQUEST.value());
        errorObject.setMessage(Objects.requireNonNull(ex.getBindingResult().getFieldError()).getDefaultMessage());
        errorObject.setTimestamp(new Date());

        return new ResponseEntity<>(errorObject, HttpStatus.BAD_REQUEST);
    }

    // Handles the case when a row doesn't exist during an update or creation requiring a reference
    @ExceptionHandler(ReferencedEntityNotFoundException.class)
    public ResponseEntity<ErrorObject> handleReferencedEntityNotFoundException(ReferencedEntityNotFoundException ex) {
        logger.info("ReferencedEntityNotFoundException is being handled");
        ErrorObject errorObject = new ErrorObject();

        errorObject.setStatusCode(HttpStatus.NOT_FOUND.value());
        errorObject.setMessage("Referenced entity not found: " + ex.getMessage());
        errorObject.setTimestamp(new Date());

        return new ResponseEntity<>(errorObject, HttpStatus.NOT_FOUND);
    }

    // Handles the case when a requested object/page does not exist (e.g., 404 for resources or pages)
    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<ErrorObject> handleNoHandlerFoundException(NoHandlerFoundException ex) {
        logger.info("NoHandlerFoundException is being handled");
        ErrorObject errorObject = new ErrorObject();

        errorObject.setStatusCode(HttpStatus.NOT_FOUND.value());
        errorObject.setMessage("Resource not found: " + ex.getRequestURL());
        errorObject.setTimestamp(new Date());

        return new ResponseEntity<>(errorObject, HttpStatus.NOT_FOUND);
    }

    // Handles database constraint violations (e.g., unique, foreign key, null constraint)
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorObject> handleDataIntegrityViolationException() {
        logger.info("DataIntegrityViolationException is being handled");
        ErrorObject errorObject = new ErrorObject();

        errorObject.setStatusCode(HttpStatus.CONFLICT.value());
        errorObject.setMessage("Database error: A constraint was violated. Check for unique, foreign key, or null constraints.");
        errorObject.setTimestamp(new Date());

        return new ResponseEntity<>(errorObject, HttpStatus.CONFLICT);
    }

    // Handles SQL Integrity Constraint Violations (e.g., trying to insert a duplicate key)
    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public ResponseEntity<ErrorObject> handleSQLIntegrityConstraintViolationException(SQLIntegrityConstraintViolationException ex) {
        logger.info("SQLIntegrityConstraintViolationException is being handled");
        ErrorObject errorObject = new ErrorObject();

        errorObject.setStatusCode(HttpStatus.CONFLICT.value());
        errorObject.setMessage("Database integrity error: " + ex.getMessage());
        errorObject.setTimestamp(new Date());

        return new ResponseEntity<>(errorObject, HttpStatus.CONFLICT);
    }

    // Handles cases where the database is unavailable (e.g., server down, connectivity issues)
    @ExceptionHandler(CannotCreateTransactionException.class)
    public ResponseEntity<ErrorObject> handleDatabaseUnavailable() {
        logger.info("CannotCreateTransactionException is being handled");
        ErrorObject errorObject = new ErrorObject();

        errorObject.setStatusCode(HttpStatus.SERVICE_UNAVAILABLE.value());
        errorObject.setMessage("Database service is currently unavailable. Please try again later.");
        errorObject.setTimestamp(new Date());

        return new ResponseEntity<>(errorObject, HttpStatus.SERVICE_UNAVAILABLE);
    }
}
