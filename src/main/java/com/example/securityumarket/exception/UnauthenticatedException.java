package com.example.securityumarket.exception;

import lombok.extern.slf4j.Slf4j;

import java.time.Instant;

@Slf4j
public class UnauthenticatedException extends RuntimeException  {
    public UnauthenticatedException(String message) {
        super(message);
    }

    public UnauthenticatedException() {
        super("The user is not authenticated");
    }

}
