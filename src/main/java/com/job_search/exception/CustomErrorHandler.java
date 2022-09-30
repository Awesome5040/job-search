package com.job_search.exception;

import net.minidev.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.persistence.EntityNotFoundException;
import javax.validation.ConstraintViolationException;


@ControllerAdvice
public class CustomErrorHandler {

    private static final String MESSAGE = "message";

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<String> handleConstraintViolationException(final ConstraintViolationException exception) {
        JSONObject response = new JSONObject();
        response.put(MESSAGE, exception.getMessage());
        return new ResponseEntity<>(response.toString(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DuplicateRecordException.class)
    public ResponseEntity<Object> handleDuplicateRecordException(final DuplicateRecordException exception) {
        JSONObject response = new JSONObject();
        response.put(MESSAGE, exception.getMessage());
        return new ResponseEntity<>(response.toString(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Object> handleDuplicateRecordException(final EntityNotFoundException exception) {
        JSONObject response = new JSONObject();
        response.put(MESSAGE, exception.getMessage());
        return new ResponseEntity<>(response.toString(), HttpStatus.NOT_FOUND);
    }

}