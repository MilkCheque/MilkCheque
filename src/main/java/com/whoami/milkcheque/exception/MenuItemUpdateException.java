package com.whoami.milkcheque.exception;

public class MenuItemUpdateException extends RuntimeException {
  private final String code;
  private final String message;

  public MenuItemUpdateException(String code, String message) {
    super(message);
    this.code = code;
    this.message = message;
  }

  public String getCode() {
    return this.code;
  }
}
