package com.whoami.milkcheque.exception;

public class LoginProcessFailureException extends AuthenticationFailureException {
  public LoginProcessFailureException(String code, String message) {
    super(code, message);
  }
}
