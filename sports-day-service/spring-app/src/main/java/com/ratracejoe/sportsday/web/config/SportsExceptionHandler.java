package com.ratracejoe.sportsday.web.config;

import com.ratracejoe.sportsday.domain.exception.NotFoundException;
import com.ratracejoe.sportsday.rest.auth.exception.InvalidAuthException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class SportsExceptionHandler {
  @ExceptionHandler(NotFoundException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public void notFound() {}

  @ExceptionHandler(InvalidAuthException.class)
  @ResponseStatus(HttpStatus.UNAUTHORIZED)
  public void unauthorised() {}
}
