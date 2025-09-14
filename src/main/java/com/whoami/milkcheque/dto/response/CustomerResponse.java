package com.whoami.milkcheque.dto.response;

import lombok.Data;

@Data
public class CustomerResponse {
  private Long customerId;
  private Long sessionId;
  private String code;
  private String message;
  private String token;

  public CustomerResponse(
      Long customerId, Long sessionId, String code, String message, String token) {
    this.customerId = customerId;
    this.sessionId = sessionId;
    this.code = code;
    this.message = message;
    this.token = token;
  }
}
