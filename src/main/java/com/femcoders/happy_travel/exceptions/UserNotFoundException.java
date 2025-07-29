package com.femcoders.happy_travel.exceptions;

public class UserNotFoundException extends AppException {
    public UserNotFoundException(Long id) {
        super("User not found with id: " + id);
    }
}
