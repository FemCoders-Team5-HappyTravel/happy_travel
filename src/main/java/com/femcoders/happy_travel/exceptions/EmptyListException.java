package com.femcoders.happy_travel.exceptions;

public class EmptyListException extends AppException {
    public EmptyListException(String message) {
        super("The list is empty");
    }
}
