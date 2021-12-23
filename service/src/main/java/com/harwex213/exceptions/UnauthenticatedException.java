package com.harwex213.exceptions;

public class UnauthenticatedException extends Exception{
    public UnauthenticatedException() {
        super("Unauthenticated");
    }

    public UnauthenticatedException(String message) {
        super(message);
    }
}