package com.example.securityumarket.exception;

public class CloudinaryException extends RuntimeException {
    public CloudinaryException(String message) {
        super(message);
    }

    public CloudinaryException() {
        super();
    }
}