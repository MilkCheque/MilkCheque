package com.whoami.milkcheque.exception;

import java.lang.RuntimeException;

public class SignUpProcessFailureException extends AuthenticationFailureException{
    public SignUpProcessFailureException(String code, String message) {
        super(code, message);
    }
}
