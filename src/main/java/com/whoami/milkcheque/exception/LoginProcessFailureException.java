package com.whoami.milkcheque.exception;

import java.lang.RuntimeException;

public class LoginProcessFailureException extends AuthenticationFailureException {
    public LoginProcessFailureException(String code, String message) {
        super(code, message);
    }
}