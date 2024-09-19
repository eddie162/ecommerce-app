package com.eddie.ecommerce.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Catch all expected/handled errors
     * @param ex
     * @return
     */
    @ExceptionHandler(GenericAPIException.class)
    public ResponseEntity<Object> handleGenericAPIException(GenericAPIException ex) {
        return new ResponseEntity<>(generateErrorResponse(ex.getMessage()), HttpStatus.BAD_REQUEST);
    }

    // Unhandled exception Error handling
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleOtherExceptions(Exception ex) {
        return new ResponseEntity<>(generateErrorResponse(ex.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public static Map<String, String> generateErrorResponse(String message) {
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("error", message);
        return errorResponse;
    }
}