package com.setupproject.setup.utility;

import com.setupproject.setup.common.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @Author Dibakar Chaudhary
 * 03/12/2024
 **/
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Object> handleIllegalArgumentException(IllegalArgumentException ex) {
        return new ResponseEntity<>(new Response<>("01", ex.getMessage(), null), HttpStatus.BAD_REQUEST);
    }
}
