package com.project.currency_converter.exception;

public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException (Long id) {
        super("User with id: " + id + " is not found");
    }
    public UserNotFoundException (String apiKey) {
        super("User with apiKey: " + apiKey + " is not found");
    }
}
