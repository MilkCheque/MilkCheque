package com.whoami.milkcheque.exception;

public class AddOrderPatchFailureException extends RuntimeException {
  private final String code;
  private final String message;

  public AddOrderPatchFailureException(String code, String message) {
    super(message);
    this.code = code;
    this.message = message;
  }

  public String getCode() {
    return this.code;
  }
}
