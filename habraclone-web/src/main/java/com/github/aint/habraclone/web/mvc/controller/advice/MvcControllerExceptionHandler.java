package com.github.aint.habraclone.web.mvc.controller.advice;

import com.github.aint.habraclone.web.ResourceNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.NoSuchElementException;

@ControllerAdvice("com.github.aint.habraclone.web.mvc.controller")
public class MvcControllerExceptionHandler {

    @ExceptionHandler({ ResourceNotFoundException.class, NoSuchElementException.class })
    public String handleError404() {
        return "404";
    }

}

