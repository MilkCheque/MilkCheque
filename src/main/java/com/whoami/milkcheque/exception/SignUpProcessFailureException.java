package com.whoami.milkcheque.exception;

public class SignUpProcessFailureException extends AuthenticationFailureException {
  public SignUpProcessFailureException(String code, String message) {
    super(code, message);
  }
}
