package com.harwex213.controllers;

import com.harwex213.exceptions.NotFoundException;
import com.harwex213.models.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class AdviceController {

    @ResponseBody
    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    ErrorResponse HandleNotFound(NotFoundException e) {
        return new ErrorResponse(e.getMessage());
    }


    @ResponseBody
    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ErrorResponse HandleIllegalArgument(IllegalArgumentException e) {
        return new ErrorResponse(e.getMessage());
    }
}
