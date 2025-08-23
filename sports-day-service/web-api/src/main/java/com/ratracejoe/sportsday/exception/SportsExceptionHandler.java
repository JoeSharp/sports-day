package com.ratracejoe.sportsday.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class SportsExceptionHandler {
  @ExceptionHandler(ActivityNotFoundException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public void notFound() {}

  @ExceptionHandler(InvalidAuthException.class)
  @ResponseStatus(HttpStatus.UNAUTHORIZED)
  public void unauthorised() {}
}
