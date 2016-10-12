package com.github.aint.habraclone.web.rest.controller.advice;

import com.github.aint.habraclone.web.ResourceNotFoundException;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.NoHandlerFoundException;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8;

@ControllerAdvice("com.github.aint.habraclone.web.rest.controller")
public class RestControllerExceptionHandler {

    @ExceptionHandler({ NoHandlerFoundException.class, ResourceNotFoundException.class })
    public ResponseEntity<String> handleError404() {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .contentType(APPLICATION_JSON_UTF8)
                .body(new JSONObject().put("message", "Not found").toString());
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<String> handleError405()   {
        return ResponseEntity
                .status(HttpStatus.METHOD_NOT_ALLOWED)
                .contentType(APPLICATION_JSON_UTF8)
                .body(new JSONObject().put("message", "Request method 'GET' not supported").toString());
    }

}

