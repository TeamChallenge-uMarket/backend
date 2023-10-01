package com.example.securityumarket.handler;

import com.example.securityumarket.exception.RegistrationException;
import com.example.securityumarket.exception.UAutoException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class ApplicationExceptionHandler {

    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleInvalidArgument(MethodArgumentNotValidException exception) {
        return createErrorResponse(exception.getBindingResult().getFieldErrors());
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(RegistrationException.class)
    public Map<String, String> handleBusinessException(RegistrationException exception) {
        return createErrorResponse(exception.getMessage());
    }

    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    @ExceptionHandler(UAutoException.class)
    public Map<String, String> handleBusinessException(UAutoException exception) {
        return createErrorResponse(exception.getMessage());
    }

    private Map<String, String> createErrorResponse(String errorMessage) {
        Map<String, String> errorMap = new HashMap<>();
        errorMap.put("errorMessage", errorMessage);
        return errorMap;
    }

    private Map<String, String> createErrorResponse(List<FieldError> fieldErrors) {
        Map<String, String> errorMap = new HashMap<>();
        fieldErrors.forEach(error -> errorMap.put(error.getField(), error.getDefaultMessage()));
        return errorMap;
    }
}
