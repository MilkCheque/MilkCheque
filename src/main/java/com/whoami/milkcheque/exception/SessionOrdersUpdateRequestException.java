package com.whoami.milkcheque.exception;

public class SessionOrdersUpdateRequestException extends RuntimeException {
  private final String code;
  private final String message;

  public SessionOrdersUpdateRequestException(String code, String message) {
    super(message);
    this.code = code;
    this.message = message;
  }

  public String getCode() {
    return this.code;
  }
}
