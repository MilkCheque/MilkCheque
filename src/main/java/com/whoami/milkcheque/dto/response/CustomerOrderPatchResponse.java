package com.whoami.milkcheque.dto.response;

import lombok.Data;

@Data
public class CustomerOrderPatchResponse {
  private String code;
  private String message;
  private Long orderId;

  public CustomerOrderPatchResponse(String code, String message, Long orderId) {
    this.code = code;
    this.message = message;
    this.orderId = orderId;
  }
}
