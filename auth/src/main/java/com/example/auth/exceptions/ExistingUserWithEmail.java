package com.example.auth.exceptions;

public class ExistingUserWithEmail extends RuntimeException{
    public ExistingUserWithEmail(String message) {
        super(message);
    }

    public ExistingUserWithEmail(String message, Throwable cause) {
        super(message, cause);
    }

    public ExistingUserWithEmail(Throwable cause) {
        super(cause);
    }
}
