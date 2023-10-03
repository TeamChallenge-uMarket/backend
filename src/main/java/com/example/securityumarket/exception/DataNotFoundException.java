package com.example.securityumarket.exception;

public class DataNotFoundException extends RuntimeException {
    public DataNotFoundException(String message) {
        super(message + " not found.");
    }
}