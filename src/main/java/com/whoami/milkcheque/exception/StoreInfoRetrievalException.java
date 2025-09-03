package com.whoami.milkcheque.exception;

public class StoreInfoRetrievalException extends RuntimeException {
  private final String code;
  private final String message;

  public StoreInfoRetrievalException(String message) {
    super(message);
    this.code = "22"; // TODO: Edit constructor
    this.message = message;
  }

  public String getCode() {
    return this.code;
  }
}
