package com.github.pires.example.rest;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.PRECONDITION_FAILED)
public class EmaiAlreadyInUseException extends RuntimeException {

}
