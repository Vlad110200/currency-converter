package com.project.currency_converter.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationErrors (MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult()
                .getFieldErrors()
                .forEach(error -> {
                    String field = error.getField();
                    String defaultMessage = error.getDefaultMessage();
                    errors.put(field, defaultMessage);
                });
        return errors;
    }

    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, String> handleUserNotFound (UserNotFoundException userNotFoundException) {
        Map<String, String> response = new HashMap<>();
        response.put("error", "Not Found");
        response.put("message", userNotFoundException.getMessage());
        log.error(userNotFoundException.getMessage());
        return response;
    }

    @ExceptionHandler(CurrencyNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, String> handlerCurrencyNotFound ( CurrencyNotFoundException currencyNotFoundException) {
        Map<String, String> response = new HashMap<>();
        response.put("error", "Not Found");
        response.put("message", currencyNotFoundException.getMessage());
        log.error(currencyNotFoundException.getMessage());
        return response;
    }
}
