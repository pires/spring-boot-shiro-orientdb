package com.github.pires.example;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.UnauthenticatedException;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * TODO add description
 */
@ControllerAdvice
public class ApplicationExceptionHandler {

  @ResponseStatus(HttpStatus.FORBIDDEN)
  @ExceptionHandler(
      {AuthenticationException.class, UnknownAccountException.class,
        UnauthenticatedException.class, IncorrectCredentialsException.class})
  public void forbidden() {
  }

  @ResponseStatus(HttpStatus.UNAUTHORIZED)
  @ExceptionHandler(
      {UnauthorizedException.class})
  public void unauthorized() {
  }

}
