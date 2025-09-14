package com.whoami.milkcheque.dto.response;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import java.util.Map;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@JsonPropertyOrder({"customerId", "customerName", "orderItems"})
public class AllOrdersResponse {

  private Long customerId;
  private String customerName;
  Map<String, Long> orderItems;

  // total
  // item price

  public AllOrdersResponse(
      Long customerId, String customerFirstName, Map<String, Long> orderItems) {
    this.customerId = customerId;
    this.customerName = customerFirstName;
    this.orderItems = orderItems;
  }
}
