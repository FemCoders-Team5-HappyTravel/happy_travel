package com.femcoders.happy_travel.exceptions;

public class DestinationAlreadyExistsException extends AppException {
    public DestinationAlreadyExistsException(String city) {
        super("Destination with city " + city +  " already exists");;
    }
}
