package com.whoami.milkcheque.exception;

public class OrdersRetrievalException extends RuntimeException {
  public OrdersRetrievalException() {}

  public OrdersRetrievalException(String message) {
    super(message);
  }
}
