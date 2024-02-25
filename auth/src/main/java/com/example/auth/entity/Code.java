package com.example.auth.entity;

public enum Code {
    SUCCESS("message"),
    A1("user logged in"),
    A2("user not found"),
    LOGIN_FAILED("login failed"),
    PERMIT("permitted");
    public String label;

    private Code(String label) {
        this.label = label;
    }
}
