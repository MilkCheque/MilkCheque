package com.whoami.milkcheque.exception;

import com.whoami.milkcheque.enums.AuthenticationStatus;
import org.springframework.http.HttpStatus;

import java.lang.RuntimeException;


public class AuthenticationFormatException extends RuntimeException {
    public HttpStatus httpStatus;
    public AuthenticationStatus authenticationStatus;

    public AuthenticationFormatException(String message, HttpStatus httpStatus, AuthenticationStatus authenticationStatus) {
        super(message);
        this.httpStatus = httpStatus;
        this.authenticationStatus = authenticationStatus;
    }
}
