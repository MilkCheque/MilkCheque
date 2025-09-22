package com.whoami.milkcheque.exception;

public class OrdersRetrievalException extends RuntimeException {
  private final String code;

  public OrdersRetrievalException(String code, String message) {
    super(message);
    this.code = code;
  }

  public String getCode() {
    return code;
  }
}
