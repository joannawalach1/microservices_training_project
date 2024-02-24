package com.example.auth.entity;

public enum Code {
    SUCCESS("message");
    public String label;

    private Code(String label) {
        this.label = label;
    }
}
