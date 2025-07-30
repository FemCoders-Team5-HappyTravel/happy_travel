package com.femcoders.happy_travel.exceptions;

public class UserAlreadyExistsException extends AppException {
    public UserAlreadyExistsException(String email)    {
        super("User with email " + email +  " already exists");

    }
}