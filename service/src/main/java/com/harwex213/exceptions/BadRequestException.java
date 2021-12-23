package com.harwex213.exceptions;

public class BadRequestException  extends Exception{
    public BadRequestException() {
        super("Bad request");
    }

    public BadRequestException(String message) {
        super(message);
    }
}
