package com.example.securityumarket.exception;

public class InsufficientPermissionsException extends RuntimeException {
    public InsufficientPermissionsException(String message) {
        super("Insufficient permissions: " + message);
    }
}