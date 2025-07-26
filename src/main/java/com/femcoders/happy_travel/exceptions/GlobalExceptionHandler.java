package com.femcoders.happy_travel.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler{

  @ExceptionHandler(EmptyListException.class)
  public ResponseEntity<String> handleEmptyList(EmptyListException e){
    return new ResponseEntity<>(e.getMessage(), HttpStatus.NO_CONTENT);
  }

  @ExceptionHandler(UserNotFoundException.class)
  public ResponseEntity<String>handleUserNotFoundException(UserNotFoundException e){
    return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
  }
  @ExceptionHandler(UserAlreadyExistsException.class)
  public ResponseEntity<String> handleUserAlreadyExistsException(UserAlreadyExistsException exception) {
    return new ResponseEntity<>(exception.getMessage(), HttpStatus.CONFLICT);
  }
}
