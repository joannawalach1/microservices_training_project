package com.example.auth.exceptions;

public class ExistingUserWithName extends RuntimeException{
    public ExistingUserWithName(String message) {
        super(message);
    }

    public ExistingUserWithName(String message, Throwable cause) {
        super(message, cause);
    }

    public ExistingUserWithName(Throwable cause) {
        super(cause);
    }
}
