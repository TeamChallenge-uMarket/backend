package com.example.securityumarket.exception;

public class EmailSendingException extends RuntimeException {
    public EmailSendingException() {
        super("An error occurred while sending the email. Please try again later.");
    }
}