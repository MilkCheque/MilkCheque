package com.whoami.milkcheque.exception;

public class AuthenticationFailureException extends RuntimeException {
  private final String code;
  private final String message;

  public AuthenticationFailureException(String code, String message) {
    super(message);
    this.code = code;
    this.message = message;
  }

  public String getCode() {
    return this.code;
  }
}
