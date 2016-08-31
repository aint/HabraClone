package ua.sg.academy.java2.habraclone.webController.rest.controller.advice;

import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.NoHandlerFoundException;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8;

@ControllerAdvice//(basePackages = "*.rest.controller")
public class RestExceptionController {

    @ExceptionHandler(NoHandlerFoundException.class)
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

