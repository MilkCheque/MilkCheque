package com.whoami.milkcheque.exception;

public class PaymobException extends RuntimeException {
  private final String code;

  public PaymobException(String code, String message) {
    super(message);
    this.code = code;
  }

  public String getCode() {
    return code;
  }
}
